package Grupo_5.UTNC.rest;

import Grupo_5.UTNC.dto.JwtResponseDTO;
import Grupo_5.UTNC.dto.LoginDTO;
import Grupo_5.UTNC.dto.RegistroDTO;
import Grupo_5.UTNC.service.AuthService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
@Slf4j
public class AuthRestController {

    private final AuthService authService;

    @PostMapping("/registro")
    public ResponseEntity<String> registrarUsuario(@Valid @RequestBody RegistroDTO registroDTO) {
        log.info("Solicitud de registro recibida para email: {}", registroDTO.getEmail());
        String respuesta = authService.registrarUsuario(registroDTO);
        return new ResponseEntity<>(respuesta, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponseDTO> iniciarSesion(@Valid @RequestBody LoginDTO loginDTO) {
        log.info("Solicitud de login recibida para email: {}", loginDTO.getEmail());
        String token = authService.iniciarSesion(loginDTO);
        JwtResponseDTO jwtResponse = new JwtResponseDTO(token);
        return new ResponseEntity<>(jwtResponse, HttpStatus.OK);
    }
}
