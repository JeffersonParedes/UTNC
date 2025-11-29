package Grupo_5.UTNC.rest;

import Grupo_5.UTNC.jpa.entity.Usuario;
import Grupo_5.UTNC.security.JWTAuthenticationConfig;
import Grupo_5.UTNC.service.UsuarioService;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthRestController {
    private final UsuarioService usuarioService;
    private final PasswordEncoder passwordEncoder;
    private final JWTAuthenticationConfig jwtConfig;

    public AuthRestController(UsuarioService usuarioService, PasswordEncoder passwordEncoder, JWTAuthenticationConfig jwtConfig) {
        this.usuarioService = usuarioService;
        this.passwordEncoder = passwordEncoder;
        this.jwtConfig = jwtConfig;
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
}
