package Grupo_5.UTNC.jpa.repository;

import Grupo_5.UTNC.jpa.entity.Curso;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CursoRepository extends JpaRepository<Curso, Integer> {
    List<Curso> findByCarrera_IdCarreraAndSemestre(Integer idCarrera, Integer semestre);
    List<Curso> findByCodigoCurso(String codigoCurso);
}
