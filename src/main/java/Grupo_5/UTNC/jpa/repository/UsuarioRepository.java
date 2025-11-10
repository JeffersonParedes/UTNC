package Grupo_5.UTNC.jpa.repository;

import Grupo_5.UTNC.jpa.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    /**
     * Busca un usuario por su dirección de correo electrónico.
     * Spring Data JPA implementará este método automáticamente.
     *
     * @param email El email del usuario a buscar.
     * @return Un Optional que contiene al usuario si se encuentra, o un Optional vacío si no.
     */
    Optional<Usuario> findByEmail(String email);

}
