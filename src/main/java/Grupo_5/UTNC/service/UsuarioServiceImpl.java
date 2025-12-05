package Grupo_5.UTNC.service;

import Grupo_5.UTNC.jpa.entity.Usuario;
import Grupo_5.UTNC.jpa.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService {
    private final UsuarioRepository usuarioRepository;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public List<Usuario> listar() {
        return usuarioRepository.findAll();
    }

    @Override
    public Optional<Usuario> obtener(Integer id) {
        return usuarioRepository.findById(id);
    }

    @Override
    @Transactional
    public Usuario crear(Usuario usuario) {
        usuario.setFechaCreacion(LocalDateTime.now());
        return usuarioRepository.save(usuario);
    }

    @Override
    @Transactional
    public Usuario actualizar(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    @Override
    public Optional<Usuario> buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    @Override
    public Optional<Usuario> buscarPorUsername(String username) {
        return usuarioRepository.findByUsername(username);
    }
}
