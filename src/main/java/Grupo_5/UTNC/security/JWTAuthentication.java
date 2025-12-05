package Grupo_5.UTNC.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JWTAuthentication extends OncePerRequestFilter {
    
    private static final Logger logger = LoggerFactory.getLogger(JWTAuthentication.class);
    
    private final JWTAuthenticationConfig jwtConfig;
    private final UserDetailsService userDetailsService;

    public JWTAuthentication(JWTAuthenticationConfig jwtConfig, UserDetailsService userDetailsService) {
        this.jwtConfig = jwtConfig;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        
        try {
            String auth = request.getHeader("Authorization");
            
            // Verificar si el token está presente y bien formateado
            if (auth != null && auth.startsWith("Bearer ")) {
                String token = auth.substring(7);
                
                // Validar token y extraer username
                String username = jwtConfig.validateTokenAndGetSubject(token);
                
                // Si el token es válido y no hay autenticación previa
                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    
                    try {
                        // Cargar usuario completo con roles desde la base de datos
                        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                        
                        // Verificar que el usuario esté habilitado
                        if (userDetails.isEnabled() && userDetails.isAccountNonLocked() && 
                            userDetails.isAccountNonExpired() && userDetails.isCredentialsNonExpired()) {
                            
                            // Crear autenticación con las autoridades reales del usuario
                            UsernamePasswordAuthenticationToken authentication = 
                                new UsernamePasswordAuthenticationToken(
                                    userDetails, 
                                    null, 
                                    userDetails.getAuthorities()
                                );
                            
                            // Agregar detalles de la request
                            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                            
                            // Establecer autenticación en el contexto de seguridad
                            SecurityContextHolder.getContext().setAuthentication(authentication);
                            
                            logger.debug("Usuario autenticado exitosamente: {} con roles: {}", 
                                       username, userDetails.getAuthorities());
                        } else {
                            logger.warn("Usuario {} no está habilitado o su cuenta está bloqueada", username);
                        }
                        
                    } catch (UsernameNotFoundException e) {
                        logger.warn("Usuario no encontrado para token válido: {}", username);
                        // Token válido pero usuario no existe - posible token comprometido
                    }
                }
            }
        } catch (Exception e) {
            logger.error("Error procesando token JWT: {}", e.getMessage());
            // Limpiar contexto de seguridad en caso de error
            SecurityContextHolder.clearContext();
        }
        
        // Continuar con la cadena de filtros
        chain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getServletPath();
        return path.startsWith("/api/auth/");
    }
}
