package Grupo_5.UTNC.security;

import io.jsonwebtoken.Jwts;
import javax.crypto.SecretKey;

public class SecurityConstants {

    // Tiempo de vida del token JWT en milisegundos (ej. 1 hora)
    public static final long JWT_EXPIRATION_TIME = 3600000; 

    // Prefijo que se espera en el encabezado de autorización
    public static final String TOKEN_PREFIX = "Bearer ";

    // Nombre del encabezado HTTP donde se enviará el token
    public static final String HEADER_STRING = "Authorization";

    // Clave secreta para firmar el token. 
    // En una aplicación real, esto DEBE ser más complejo y almacenado de forma segura.
    public static final SecretKey JWT_SECRET_KEY = Jwts.SIG.HS512.key().build();
    
}