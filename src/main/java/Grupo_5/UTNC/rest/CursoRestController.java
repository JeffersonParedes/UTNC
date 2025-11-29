package Grupo_5.UTNC.rest;

import Grupo_5.UTNC.jpa.entity.Curso;
import Grupo_5.UTNC.service.CursoService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/cursos")
public class CursoRestController {
    private final CursoService cursoService;

    public CursoRestController(CursoService cursoService) {
        this.cursoService = cursoService;
    }

    @GetMapping
    public List<Curso> listar() {
        return cursoService.listar();
    }

    @GetMapping("/carrera/{idCarrera}/semestre/{semestre}")
    public List<Curso> listarPorCarreraSemestre(@PathVariable Integer idCarrera, @PathVariable Integer semestre) {
        return cursoService.listarPorCarreraSemestre(idCarrera, semestre);
    }
}
