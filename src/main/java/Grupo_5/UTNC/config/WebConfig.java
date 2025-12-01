package Grupo_5.UTNC.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Aplica la configuración a todos los endpoints de la API
                
                // Orígenes Permitidos: Incluye el dominio final y los dominios de desarrollo locales
                .allowedOrigins(
                    "http://frontend.com", 
                    "http://localhost:8080", 
                    "http://localhost:3000", 
                    "http://127.0.0.1:8080",
                    "http://127.0.0.1:3000"
                )
                
                // Métodos Permitidos: Añadimos OPTIONS, necesario para las peticiones 'preflight' de CORS
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                
                // Cabeceras Permitidas: Permite todas las cabeceras
                .allowedHeaders("*") 
                
                // Credenciales: Permite el envío de cookies y encabezados de autorización (como JWT)
                .allowCredentials(true);
    }
}
