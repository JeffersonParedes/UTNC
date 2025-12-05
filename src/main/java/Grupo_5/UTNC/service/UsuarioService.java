package Grupo_5.UTNC.service;

import Grupo_5.UTNC.jpa.entity.Usuario;
import java.util.List;
import java.util.Optional;

public interface UsuarioService {
    List<Usuario> listar();

    Optional<Usuario> obtener(Integer id);

    Usuario crear(Usuario usuario);

    Usuario actualizar(Usuario usuario);

    Optional<Usuario> buscarPorEmail(String email);

    Optional<Usuario> buscarPorUsername(String username);
}
