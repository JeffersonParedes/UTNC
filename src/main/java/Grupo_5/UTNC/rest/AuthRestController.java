package Grupo_5.UTNC.rest;

import Grupo_5.UTNC.dto.EstudianteCreateDTO;
import Grupo_5.UTNC.dto.ApiResponse;
import Grupo_5.UTNC.dto.InscripcionDTO;
import Grupo_5.UTNC.dto.UsuarioCreateDTO;
import Grupo_5.UTNC.jpa.entity.Carrera;
import Grupo_5.UTNC.jpa.entity.Estudiante;
import Grupo_5.UTNC.jpa.entity.Usuario;
import Grupo_5.UTNC.security.JWTAuthenticationConfig;
import Grupo_5.UTNC.service.CarreraService;
import Grupo_5.UTNC.service.EstudianteService;
import Grupo_5.UTNC.service.MatriculaService;
import Grupo_5.UTNC.service.UsuarioService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/auth")
public class AuthRestController {
    private static final Logger logger = LoggerFactory.getLogger(AuthRestController.class);
    private final UsuarioService usuarioService;
    private final PasswordEncoder passwordEncoder;
    private final JWTAuthenticationConfig jwtConfig;
    private final EstudianteService estudianteService;
    private final CarreraService carreraService;
    private final MatriculaService matriculaService;
    private final AuthenticationManager authenticationManager;

    public AuthRestController(UsuarioService usuarioService, PasswordEncoder passwordEncoder, 
                            JWTAuthenticationConfig jwtConfig, EstudianteService estudianteService, 
                            CarreraService carreraService, MatriculaService matriculaService,
                            AuthenticationManager authenticationManager) {
        this.usuarioService = usuarioService;
        this.passwordEncoder = passwordEncoder;
        this.jwtConfig = jwtConfig;
        this.estudianteService = estudianteService;
        this.carreraService = carreraService;
        this.matriculaService = matriculaService;
        this.authenticationManager = authenticationManager;
    }

    public static class LoginRequest {
        @NotBlank
        public String username;
        @NotBlank
        public String password;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest req) {
        try {
            logger.info("Intento de login para usuario: {}", req.username);
            
            // Usar AuthenticationManager para autenticación robusta
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.username, req.password)
            );
            
            // Obtener detalles del usuario autenticado
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String username = userDetails.getUsername();
            
            // Actualizar último acceso
            usuarioService.buscarPorUsername(username).ifPresent(usuario -> {
                usuario.setUltimoAcceso(LocalDateTime.now());
                usuarioService.actualizar(usuario);
            });
            
            // Generar token JWT
            String token = jwtConfig.createToken(username);
            
            // Obtener información adicional del usuario
            Usuario usuario = usuarioService.buscarPorUsername(username).orElse(null);
            
            logger.info("Login exitoso para usuario: {} con roles: {}", username, userDetails.getAuthorities());
            
            // Respuesta exitosa con información del usuario
            return ResponseEntity.ok(Map.of(
                "success", true,
                "token", token,
                "user", Map.of(
                    "username", username,
                    "email", usuario != null ? usuario.getEmail() : "",
                    "estado", usuario != null ? usuario.getEstado() : "",
                    "roles", userDetails.getAuthorities().stream()
                            .map(auth -> auth.getAuthority())
                            .toList()
                )
            ));
            
        } catch (BadCredentialsException e) {
            logger.warn("Credenciales incorrectas para usuario: {}", req.username);
            return ResponseEntity.status(401).body(Map.of(
                "success", false,
                "message", "Credenciales incorrectas"
            ));
        } catch (Exception e) {
            logger.error("Error durante el login para usuario {}: {}", req.username, e.getMessage());
            return ResponseEntity.status(500).body(Map.of(
                "success", false,
                "message", "Error interno del servidor"
            ));
        }
    }
    @PostMapping("/register")
    public ResponseEntity<?> registrar(@Valid @RequestBody InscripcionDTO dto) {
        UsuarioCreateDTO usuarioDTO = dto.getUsuario();
        EstudianteCreateDTO estudianteDTO = dto.getEstudiante();

        if (usuarioService.buscarPorUsername(usuarioDTO.getUsername()).isPresent() ||
            usuarioService.buscarPorEmail(usuarioDTO.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body(Map.of("success", false, "message", "El usuario ya existe"));
        }

        if (estudianteService.buscarPorDni(estudianteDTO.getDni()).isPresent()) {
            return ResponseEntity.badRequest().body(Map.of("success", false, "message", "El DNI ya está registrado"));
        }

        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setUsername(usuarioDTO.getUsername());
        nuevoUsuario.setEmail(usuarioDTO.getEmail());
        nuevoUsuario.setContrasenaHash(passwordEncoder.encode(usuarioDTO.getPassword()));
        nuevoUsuario.setEstado(usuarioDTO.getEstado() != null ? usuarioDTO.getEstado() : "ACTIVO");
        Usuario usuarioGuardado = usuarioService.crear(nuevoUsuario);

        Carrera carrera = carreraService.buscarPorNombre(dto.getCareerName());
        if (carrera == null) {
            return ResponseEntity.badRequest().body(Map.of("success", false, "message", "Carrera no encontrada"));
        }

        estudianteDTO.setIdUsuario(usuarioGuardado.getIdUsuario());
        estudianteDTO.setIdCarrera(carrera.getIdCarrera());
        Estudiante estudiante = estudianteService.crearDesdeDTO(estudianteDTO);
        matriculaService.matricularEstudianteInicial(estudiante, "2026-I");

        String token = jwtConfig.createToken(usuarioGuardado.getUsername());

        logger.info("Nuevo estudiante registrado: {} {} (id={})", estudiante.getNombres(), estudiante.getApellidos(), estudiante.getIdEstudiante());

        return ResponseEntity.status(201).body(
            new ApiResponse(true, "Registro exitoso", Map.of(
                "idEstudiante", estudiante.getIdEstudiante(),
                "nombreCompleto", estudiante.getNombres() + " " + estudiante.getApellidos(),
                "carrera", carrera.getNombreCarrera(),
                "token", token
            ))
        );
    }
    
    /**
     * Endpoint para obtener información del usuario actualmente autenticado
     */
    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(Authentication authentication) {
        try {
            if (authentication == null || !authentication.isAuthenticated()) {
                return ResponseEntity.status(401).body(Map.of(
                    "success", false,
                    "message", "No autenticado"
                ));
            }
            
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String username = userDetails.getUsername();
            
            // Obtener información completa del usuario
            Usuario usuario = usuarioService.buscarPorUsername(username).orElse(null);
            if (usuario == null) {
                return ResponseEntity.status(404).body(Map.of(
                    "success", false,
                    "message", "Usuario no encontrado"
                ));
            }
            
            return ResponseEntity.ok(Map.of(
                "success", true,
                "user", Map.of(
                    "id", usuario.getIdUsuario(),
                    "username", usuario.getUsername(),
                    "email", usuario.getEmail(),
                    "estado", usuario.getEstado(),
                    "fechaCreacion", usuario.getFechaCreacion(),
                    "ultimoAcceso", usuario.getUltimoAcceso(),
                    "roles", userDetails.getAuthorities().stream()
                            .map(auth -> auth.getAuthority())
                            .toList()
                )
            ));
            
        } catch (Exception e) {
            logger.error("Error obteniendo información del usuario actual: {}", e.getMessage());
            return ResponseEntity.status(500).body(Map.of(
                "success", false,
                "message", "Error interno del servidor"
            ));
        }
    }
    
    /**
     * Endpoint para cerrar sesión (opcional, ya que JWT es stateless)
     */
    @PostMapping("/logout")
    public ResponseEntity<?> logout(Authentication authentication) {
        try {
            if (authentication != null && authentication.isAuthenticated()) {
                String username = authentication.getName();
                logger.info("Usuario {} cerró sesión", username);
                
                // En una implementación completa, aquí podrías:
                // - Agregar el token a una blacklist
                // - Invalidar refresh tokens
                // - Registrar el evento de logout
            }
            
            return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "Sesión cerrada exitosamente"
            ));
            
        } catch (Exception e) {
            logger.error("Error durante logout: {}", e.getMessage());
            return ResponseEntity.status(500).body(Map.of(
                "success", false,
                "message", "Error interno del servidor"
            ));
        }
    }
}
