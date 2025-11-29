package Grupo_5.UTNC.jpa.repository;

import Grupo_5.UTNC.jpa.entity.Pago;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PagoRepository extends JpaRepository<Pago, Integer> {
    List<Pago> findByEstudiante_IdEstudianteAndCicloAcademico(Integer idEstudiante, String cicloAcademico);
}
