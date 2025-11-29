package Grupo_5.UTNC.jpa.repository;

import Grupo_5.UTNC.jpa.entity.Estudiante;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.List;

public interface EstudianteRepository extends JpaRepository<Estudiante, Integer> {
    Optional<Estudiante> findByDni(String dni);
    List<Estudiante> findByCarrera_IdCarreraAndSemestreActual(Integer idCarrera, Integer semestreActual);
}
