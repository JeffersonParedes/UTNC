package Grupo_5.UTNC.security;

import Grupo_5.UTNC.jpa.entity.Usuario;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

/**
 * Implementación personalizada de UserDetails que envuelve la entidad Usuario.
 * Proporciona información adicional sobre el usuario para Spring Security.
 * 
 * Esta clase permite:
 * - Acceder a datos adicionales del Usuario (ID, email, fechas)
 * - Personalizar la lógica de validación de cuenta
 * - Mantener información de estado más granular
 */
public class CustomUserPrincipal implements UserDetails {

    private final Usuario usuario;
    private final Collection<? extends GrantedAuthority> authorities;

    public CustomUserPrincipal(Usuario usuario, Collection<? extends GrantedAuthority> authorities) {
        this.usuario = usuario;
        this.authorities = authorities;
    }

    /**
     * Factory method para crear un CustomUserPrincipal desde un Usuario
     */
    public static CustomUserPrincipal create(Usuario usuario) {
        // Determinar autoridades basadas en el estado del usuario
        Collection<GrantedAuthority> authorities = mapearRoles(usuario.getEstado());
        return new CustomUserPrincipal(usuario, authorities);
    }

    /**
     * Mapea el estado del usuario a autoridades de Spring Security
     */
    private static Collection<GrantedAuthority> mapearRoles(String estado) {
        if (estado == null) {
            return Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
        }
        
        String rol;
        switch (estado.toLowerCase()) {
            case "admin":
            case "administrador":
                rol = "ROLE_ADMIN";
                break;
            case "docente":
            case "profesor":
                rol = "ROLE_DOCENTE";
                break;
            case "estudiante":
            case "activo":
                rol = "ROLE_ESTUDIANTE";
                break;
            default:
                rol = "ROLE_USER";
        }
        
        return Collections.singletonList(new SimpleGrantedAuthority(rol));
    }

    // =================== UserDetails Implementation ===================

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return usuario.getContrasenaHash();
    }

    @Override
    public String getUsername() {
        return usuario.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        // Por ahora, las cuentas no expiran
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // Verificar si la cuenta no está bloqueada
        return usuario.getEstado() != null && 
               !usuario.getEstado().equalsIgnoreCase("bloqueado");
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // Por ahora, las credenciales no expiran
        // TODO: Implementar lógica de expiración de contraseñas
        return true;
    }

    @Override
    public boolean isEnabled() {
        // Verificar si la cuenta está activa
        return usuario.getEstado() != null && 
               !usuario.getEstado().equalsIgnoreCase("inactivo") &&
               !usuario.getEstado().equalsIgnoreCase("suspendido");
    }

    // =================== Métodos de acceso al Usuario ===================

    /**
     * Obtiene el ID del usuario
     */
    public Integer getId() {
        return usuario.getIdUsuario();
    }

    /**
     * Obtiene el email del usuario
     */
    public String getEmail() {
        return usuario.getEmail();
    }

    /**
     * Obtiene el estado del usuario
     */
    public String getEstado() {
        return usuario.getEstado();
    }

    /**
     * Obtiene la entidad Usuario completa
     */
    public Usuario getUsuario() {
        return usuario;
    }

    /**
     * Verifica si el usuario tiene un rol específico
     */
    public boolean hasRole(String role) {
        return authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(auth -> auth.equals("ROLE_" + role.toUpperCase()));
    }

    /**
     * Verifica si el usuario es administrador
     */
    public boolean isAdmin() {
        return hasRole("ADMIN");
    }

    /**
     * Verifica si el usuario es docente
     */
    public boolean isDocente() {
        return hasRole("DOCENTE");
    }

    /**
     * Verifica si el usuario es estudiante
     */
    public boolean isEstudiante() {
        return hasRole("ESTUDIANTE");
    }

    @Override
    public String toString() {
        return "CustomUserPrincipal{" +
                "username='" + usuario.getUsername() + '\'' +
                ", email='" + usuario.getEmail() + '\'' +
                ", estado='" + usuario.getEstado() + '\'' +
                ", authorities=" + authorities +
                '}';
    }
}