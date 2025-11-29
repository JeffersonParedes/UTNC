package Grupo_5.UTNC.jpa.repository;

import Grupo_5.UTNC.jpa.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Optional<Usuario> findByEmail(String email);
    Optional<Usuario> findByUsername(String username);
}
