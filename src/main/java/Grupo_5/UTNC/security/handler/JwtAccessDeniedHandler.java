package Grupo_5.UTNC.security.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

    /**
     * Este método se invoca cuando un usuario autenticado intenta acceder a un recurso
     * para el cual no tiene los permisos necesarios.
     */
    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException {
        
        // Enviamos una respuesta de error 403 Forbidden con un mensaje.
        response.sendError(HttpServletResponse.SC_FORBIDDEN, "Acceso denegado. No tienes los permisos necesarios para acceder a este recurso.");
    }
}
