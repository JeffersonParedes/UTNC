package Grupo_5.UTNC.jpa.repository;

import Grupo_5.UTNC.jpa.entity.Carrera;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CarreraRepository extends JpaRepository<Carrera, Integer> {
    Optional<Carrera> findByNombreCarreraIgnoreCase(String nombreCarrera);
}
