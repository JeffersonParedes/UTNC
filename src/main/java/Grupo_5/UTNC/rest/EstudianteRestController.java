package Grupo_5.UTNC.rest;

import Grupo_5.UTNC.dto.EstudianteCreateDTO;
import Grupo_5.UTNC.dto.EstudianteDTO;
import Grupo_5.UTNC.jpa.entity.Carrera;
import Grupo_5.UTNC.jpa.entity.Estudiante;
import Grupo_5.UTNC.jpa.entity.Usuario;
import Grupo_5.UTNC.service.CarreraService;
import Grupo_5.UTNC.service.EstudianteService;
import Grupo_5.UTNC.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/estudiantes")
public class EstudianteRestController {
    private final EstudianteService estudianteService;
    private final UsuarioService usuarioService;
    private final CarreraService carreraService;

    public EstudianteRestController(EstudianteService estudianteService, UsuarioService usuarioService, CarreraService carreraService) {
        this.estudianteService = estudianteService;
        this.usuarioService = usuarioService;
        this.carreraService = carreraService;
    }

    @GetMapping
    public List<EstudianteDTO> listar() {
        return estudianteService.listar().stream().map(this::toDTO).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EstudianteDTO> obtener(@PathVariable Integer id) {
        return estudianteService.obtener(id).map(e -> ResponseEntity.ok(toDTO(e))).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<EstudianteDTO> crear(@Valid @RequestBody EstudianteCreateDTO dto) {
        Usuario usuario = usuarioService.obtener(dto.getIdUsuario()).orElse(null);
        Carrera carrera = carreraService.obtener(dto.getIdCarrera()).orElse(null);
        if (usuario == null || carrera == null) {
            return ResponseEntity.badRequest().build();
        }
        Estudiante e = new Estudiante();
        e.setUsuario(usuario);
        e.setCarrera(carrera);
        e.setNombres(dto.getNombres());
        e.setApellidos(dto.getApellidos());
        e.setDni(dto.getDni());
        e.setFechaNacimiento(dto.getFechaNacimiento());
        e.setGenero(dto.getGenero());
        e.setTelefono(dto.getTelefono());
        e.setColegio(dto.getColegio());
        e.setAnioEgreso(dto.getAnioEgreso());
        e.setSemestreActual(dto.getSemestreActual());
        e.setDiscapacidad(dto.getDiscapacidad());
        e.setDiscapacidadDetalle(dto.getDiscapacidadDetalle());
        e.setContactoEmergencia(dto.getContactoEmergencia());
        e.setTelefonoEmergencia(dto.getTelefonoEmergencia());
        e.setAceptaTerminos(dto.getAceptaTerminos());
        e.setEstadoAcademico(dto.getEstadoAcademico());
        Estudiante creado = estudianteService.crear(e);
        return ResponseEntity.created(URI.create("/api/estudiantes/" + creado.getIdEstudiante())).body(toDTO(creado));
    }

    private EstudianteDTO toDTO(Estudiante e) {
        EstudianteDTO dto = new EstudianteDTO();
        dto.setId(e.getIdEstudiante());
        dto.setUsuarioId(e.getUsuario() != null ? e.getUsuario().getIdUsuario() : null);
        dto.setCarreraId(e.getCarrera() != null ? e.getCarrera().getIdCarrera() : null);
        dto.setNombres(e.getNombres());
        dto.setApellidos(e.getApellidos());
        dto.setDni(e.getDni());
        dto.setGenero(e.getGenero());
        dto.setSemestreActual(e.getSemestreActual());
        dto.setEstadoAcademico(e.getEstadoAcademico());
        return dto;
    }
}
