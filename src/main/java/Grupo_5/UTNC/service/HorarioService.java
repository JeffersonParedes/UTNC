package Grupo_5.UTNC.service;

import Grupo_5.UTNC.jpa.entity.Horario;
import java.util.List;
import java.util.Optional;

public interface HorarioService {
    List<Horario> listar();
    Optional<Horario> obtener(Integer id);
    Horario crear(Horario horario);
    List<Horario> listarPorCursoYCiclo(Integer idCurso, String ciclo);
}
