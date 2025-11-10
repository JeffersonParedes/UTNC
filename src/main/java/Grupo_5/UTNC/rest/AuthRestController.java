package Grupo_5.UTNC.rest;

import Grupo_5.UTNC.dto.JwtResponseDTO;
import Grupo_5.UTNC.dto.LoginDTO;
import Grupo_5.UTNC.dto.RegistroDTO;
import Grupo_5.UTNC.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthRestController {

    private final AuthService authService;

    @PostMapping("/registro")
    public ResponseEntity<String> registrarUsuario(@RequestBody RegistroDTO registroDTO) {
        String respuesta = authService.registrarUsuario(registroDTO);
        return new ResponseEntity<>(respuesta, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponseDTO> iniciarSesion(@RequestBody LoginDTO loginDTO) {
        String token = authService.iniciarSesion(loginDTO);
        JwtResponseDTO jwtResponse = new JwtResponseDTO(token);
        return new ResponseEntity<>(jwtResponse, HttpStatus.OK);
    }
}
