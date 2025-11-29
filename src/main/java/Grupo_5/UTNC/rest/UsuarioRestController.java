package Grupo_5.UTNC.rest;

import Grupo_5.UTNC.dto.UsuarioCreateDTO;
import Grupo_5.UTNC.dto.UsuarioDTO;
import Grupo_5.UTNC.jpa.entity.Usuario;
import Grupo_5.UTNC.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioRestController {
    private final UsuarioService usuarioService;
    private final PasswordEncoder passwordEncoder;

    public UsuarioRestController(UsuarioService usuarioService, PasswordEncoder passwordEncoder) {
        this.usuarioService = usuarioService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public List<UsuarioDTO> listar() {
        return usuarioService.listar().stream().map(this::toDTO).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> obtener(@PathVariable Integer id) {
        return usuarioService.obtener(id).map(u -> ResponseEntity.ok(toDTO(u))).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<UsuarioDTO> crear(@Valid @RequestBody UsuarioCreateDTO dto) {
        Usuario usuario = new Usuario();
        usuario.setUsername(dto.getUsername());
        usuario.setEmail(dto.getEmail());
        usuario.setContrasenaHash(passwordEncoder.encode(dto.getPassword()));
        usuario.setEstado(dto.getEstado());
        Usuario creado = usuarioService.crear(usuario);
        return ResponseEntity.created(URI.create("/api/usuarios/" + creado.getIdUsuario())).body(toDTO(creado));
    }

    private UsuarioDTO toDTO(Usuario u) {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setId(u.getIdUsuario());
        dto.setUsername(u.getUsername());
        dto.setEmail(u.getEmail());
        dto.setEstado(u.getEstado());
        dto.setFechaCreacion(u.getFechaCreacion());
        dto.setUltimoAcceso(u.getUltimoAcceso());
        return dto;
    }
}
