package Grupo_5.UTNC.rest;

import Grupo_5.UTNC.dto.MatriculaCreateDTO;
import Grupo_5.UTNC.jpa.entity.Curso;
import Grupo_5.UTNC.jpa.entity.Estudiante;
import Grupo_5.UTNC.jpa.entity.Matricula;
import Grupo_5.UTNC.service.CursoService;
import Grupo_5.UTNC.service.EstudianteService;
import Grupo_5.UTNC.service.MatriculaService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/matriculas")
public class MatriculaRestController {
    private final MatriculaService matriculaService;
    private final EstudianteService estudianteService;
    private final CursoService cursoService;

    public MatriculaRestController(MatriculaService matriculaService, EstudianteService estudianteService, CursoService cursoService) {
        this.matriculaService = matriculaService;
        this.estudianteService = estudianteService;
        this.cursoService = cursoService;
    }

    @GetMapping
    public List<Matricula> listar() {
        return matriculaService.listar();
    }

    @PostMapping
    public ResponseEntity<Matricula> crear(@Valid @RequestBody MatriculaCreateDTO dto) {
        Estudiante estudiante = estudianteService.obtener(dto.getIdEstudiante()).orElse(null);
        Curso curso = cursoService.obtener(dto.getIdCurso()).orElse(null);
        if (estudiante == null || curso == null) {
            return ResponseEntity.badRequest().build();
        }
        Matricula m = new Matricula();
        m.setEstudiante(estudiante);
        m.setCurso(curso);
        m.setCicloAcademico(dto.getCicloAcademico());
        m.setEstado(dto.getEstado());
        Matricula creada = matriculaService.crear(m);
        return ResponseEntity.created(URI.create("/api/matriculas/" + creada.getIdMatricula())).body(creada);
    }
}
