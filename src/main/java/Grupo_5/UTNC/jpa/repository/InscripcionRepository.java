package Grupo_5.UTNC.jpa.repository;

import Grupo_5.UTNC.jpa.entity.Inscripcion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InscripcionRepository extends JpaRepository<Inscripcion, Long> {
    // Por ahora, no necesitamos métodos adicionales.
    // Spring Data JPA nos proporciona automáticamente los métodos CRUD básicos:
    // save(), findById(), findAll(), delete(), etc.
}
