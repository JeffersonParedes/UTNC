package Grupo_5.UTNC.jpa.repository;

import Grupo_5.UTNC.jpa.entity.Contacto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactoRepository extends JpaRepository<Contacto, Long> {
}
