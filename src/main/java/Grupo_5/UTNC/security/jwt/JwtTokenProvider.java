package Grupo_5.UTNC.security.jwt;

import Grupo_5.UTNC.security.SecurityConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtTokenProvider {

    /**
     * Genera un token JWT para un usuario autenticado.
     */
    public String generateToken(Authentication authentication) {
        String username = authentication.getName();
        Date currentDate = new Date();
        Date expireDate = new Date(currentDate.getTime() + SecurityConstants.JWT_EXPIRATION_TIME);

        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(expireDate)
                .signWith(SecurityConstants.JWT_SECRET_KEY)
                .compact();
    }

    /**
     * Extrae el nombre de usuario (email) del token JWT.
     */
    public String getUsernameFromJWT(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(SecurityConstants.JWT_SECRET_KEY)
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return claims.getSubject();
    }

    /**
     * Valida si un token JWT es correcto y no ha expirado.
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(SecurityConstants.JWT_SECRET_KEY)
                    .build()
                    .parse(token);
            return true;
        } catch (Exception ex) {
            // Aquí se podrían manejar diferentes tipos de excepciones de token inválido
            // (expirado, malformado, etc.) y loggearlas.
            return false;
        }
    }
}
