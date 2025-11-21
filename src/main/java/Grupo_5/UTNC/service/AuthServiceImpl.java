package Grupo_5.UTNC.service;

import Grupo_5.UTNC.dto.LoginDTO;
import Grupo_5.UTNC.dto.RegistroDTO;
import Grupo_5.UTNC.exception.UserAlreadyExistsException;
import Grupo_5.UTNC.jpa.entity.Usuario;
import Grupo_5.UTNC.jpa.repository.UsuarioRepository;
import Grupo_5.UTNC.security.jwt.JwtTokenProvider;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public String registrarUsuario(RegistroDTO registroDTO) {
        log.info("Iniciando registro de usuario con email: {}", registroDTO.getEmail());
        
        // Verificar si el email ya existe
        if (usuarioRepository.findByEmail(registroDTO.getEmail()).isPresent()) {
            log.warn("Intento de registro con email ya existente: {}", registroDTO.getEmail());
            throw UserAlreadyExistsException.forEmail(registroDTO.getEmail());
        }

        try {
            Usuario usuario = new Usuario();
            usuario.setNombre(registroDTO.getNombre());
            usuario.setEmail(registroDTO.getEmail());
            usuario.setPassword(passwordEncoder.encode(registroDTO.getPassword()));
            // Se puede asignar un rol por defecto aquí si es necesario
            // usuario.setRol("USER");

            usuarioRepository.save(usuario);
            
            log.info("Usuario registrado exitosamente con email: {}", registroDTO.getEmail());
            return "Usuario registrado exitosamente!";
            
        } catch (Exception e) {
            log.error("Error al registrar usuario: ", e);
            throw new RuntimeException("Error interno al registrar el usuario. Inténtelo de nuevo.");
        }
    }

    @Override
    public String iniciarSesion(LoginDTO loginDTO) {
        log.info("Intento de inicio de sesión para el usuario: {}", loginDTO.getEmail());
        
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    loginDTO.getEmail(),
                    loginDTO.getPassword()
                )
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = jwtTokenProvider.generateToken(authentication);
            
            log.info("Inicio de sesión exitoso para el usuario: {}", loginDTO.getEmail());
            return token;
            
        } catch (BadCredentialsException e) {
            log.warn("Intento de inicio de sesión fallido para el usuario: {} - Credenciales incorrectas", loginDTO.getEmail());
            throw new BadCredentialsException("Email o contraseña incorrectos");
            
        } catch (AuthenticationException e) {
            log.warn("Error de autenticación para el usuario: {} - {}", loginDTO.getEmail(), e.getMessage());
            throw new BadCredentialsException("Error en la autenticación. Verifique sus credenciales.");
            
        } catch (Exception e) {
            log.error("Error interno durante el inicio de sesión para el usuario: {}", loginDTO.getEmail(), e);
            throw new RuntimeException("Error interno del servidor. Inténtelo de nuevo más tarde.");
        }
    }
}
