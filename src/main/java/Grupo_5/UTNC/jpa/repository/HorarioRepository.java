package Grupo_5.UTNC.jpa.repository;

import Grupo_5.UTNC.jpa.entity.Horario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface HorarioRepository extends JpaRepository<Horario, Integer> {
    List<Horario> findByCurso_IdCursoAndCicloAcademico(Integer idCurso, String cicloAcademico);
}
