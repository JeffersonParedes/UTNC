package Grupo_5.UTNC.jpa.repository;

import Grupo_5.UTNC.jpa.entity.Matricula;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MatriculaRepository extends JpaRepository<Matricula, Integer> {
    List<Matricula> findByEstudiante_IdEstudianteAndCicloAcademico(Integer idEstudiante, String cicloAcademico);
    List<Matricula> findByCurso_IdCurso(Integer idCurso);
}
