package Grupo_5.UTNC.service;

import Grupo_5.UTNC.jpa.entity.Matricula;
import java.util.List;
import java.util.Optional;

public interface MatriculaService {
    List<Matricula> listar();
    Optional<Matricula> obtener(Integer id);
    Matricula crear(Matricula matricula);
    List<Matricula> listarPorEstudianteCiclo(Integer idEstudiante, String ciclo);
    List<Matricula> listarPorCurso(Integer idCurso);
}
