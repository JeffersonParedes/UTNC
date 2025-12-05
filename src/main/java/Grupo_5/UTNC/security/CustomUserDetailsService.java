package Grupo_5.UTNC.security;

import Grupo_5.UTNC.jpa.entity.Usuario;
import Grupo_5.UTNC.jpa.repository.UsuarioRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;

/**
 * Implementación personalizada de UserDetailsService para cargar usuarios desde
 * la base de datos
 * y manejar la autenticación con Spring Security.
 * 
 * Esta implementación:
 * - Carga usuarios por username desde la BD
 * - Maneja roles básicos basados en el campo 'estado'
 * - Actualiza el último acceso del usuario
 * - Valida el estado activo del usuario
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    public CustomUserDetailsService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Buscar usuario por username o email
        Usuario usuario = usuarioRepository.findByUsername(username)
                .or(() -> usuarioRepository.findByEmail(username))
                .orElseThrow(() -> new UsernameNotFoundException(
                        "Usuario no encontrado: " + username));

        // Validar estado del usuario
        if (!isAccountActive(usuario)) {
            throw new UsernameNotFoundException("Usuario inactivo: " + username);
        }

        // Actualizar último acceso (opcional, puedes moverlo a después de login
        // exitoso)
        actualizarUltimoAcceso(usuario);

        // Obtener autoridades/roles del usuario
        Collection<? extends GrantedAuthority> authorities = getUserAuthorities(usuario);

        // Crear UserDetails con los datos del usuario
        return User.withUsername(usuario.getUsername())
                .password(usuario.getContrasenaHash())
                .authorities(authorities)
                .disabled(!isAccountActive(usuario))
                .build();
    }

    /**
     * Determina las autoridades/roles del usuario.
     * Por ahora usa el campo 'estado' como rol básico.
     * TODO: Reemplazar con tabla de roles cuando se implemente.
     */
    private Collection<? extends GrantedAuthority> getUserAuthorities(Usuario usuario) {
        // Mapeo básico: estado -> rol
        String rol = determinarRolPorEstado(usuario.getEstado());
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + rol));
    }

    /**
     * Mapea el estado del usuario a un rol básico.
     * Esta es una implementación temporal.
     */
    private String determinarRolPorEstado(String estado) {
        if (estado == null) {
            return "USER";
        }

        switch (estado.toLowerCase()) {
            case "admin":
            case "administrador":
                return "ADMIN";
            case "docente":
            case "profesor":
                return "DOCENTE";
            case "estudiante":
            case "activo":
                return "ESTUDIANTE";
            default:
                return "USER";
        }
    }

    /**
     * Verifica si la cuenta del usuario está activa.
     */
    private boolean isAccountActive(Usuario usuario) {
        return usuario.getEstado() != null &&
                !usuario.getEstado().equalsIgnoreCase("inactivo") &&
                !usuario.getEstado().equalsIgnoreCase("bloqueado") &&
                !usuario.getEstado().equalsIgnoreCase("suspendido");
    }

    /**
     * Actualiza la fecha de último acceso del usuario.
     */
    private void actualizarUltimoAcceso(Usuario usuario) {
        try {
            usuario.setUltimoAcceso(LocalDateTime.now());
            usuarioRepository.save(usuario);
        } catch (Exception e) {
            // Log del error pero no fallar la autenticación
            System.err.println("Error actualizando último acceso para usuario: " + usuario.getUsername());
        }
    }

    /**
     * Método público para obtener un usuario por username.
     * Útil para otros servicios.
     */
    public Usuario findUsuarioByUsername(String username) {
        return usuarioRepository.findByUsername(username).orElse(null);
    }

    /**
     * Método público para obtener un usuario por email.
     * Útil para otros servicios.
     */
    public Usuario findUsuarioByEmail(String email) {
        return usuarioRepository.findByEmail(email).orElse(null);
    }
}