package Grupo_5.UTNC.service;

import Grupo_5.UTNC.jpa.entity.Curso;
import java.util.List;
import java.util.Optional;

public interface CursoService {
    List<Curso> listar();
    Optional<Curso> obtener(Integer id);
    Curso crear(Curso curso);
    List<Curso> listarPorCarreraSemestre(Integer idCarrera, Integer semestre);
}
