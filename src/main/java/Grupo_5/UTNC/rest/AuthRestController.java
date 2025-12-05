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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
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

    public AuthRestController(UsuarioService usuarioService, PasswordEncoder passwordEncoder, JWTAuthenticationConfig jwtConfig, EstudianteService estudianteService, CarreraService carreraService, MatriculaService matriculaService) {
        this.usuarioService = usuarioService;
        this.passwordEncoder = passwordEncoder;
        this.jwtConfig = jwtConfig;
        this.estudianteService = estudianteService;
        this.carreraService = carreraService;
        this.matriculaService = matriculaService;
    }

    public static class LoginRequest {
        @NotBlank
        public String username;
        @NotBlank
        public String password;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest req) {
        return usuarioService.buscarPorUsername(req.username)
                .filter(u -> passwordEncoder.matches(req.password, u.getContrasenaHash()))
                .map(u -> ResponseEntity.ok(Map.of("token", jwtConfig.createToken(u.getUsername()))))
                .orElseGet(() -> ResponseEntity.status(401).build());
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
}
