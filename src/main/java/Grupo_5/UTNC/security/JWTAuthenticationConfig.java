package Grupo_5.UTNC.security;

import org.springframework.stereotype.Component;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Instant;
import java.util.Base64;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

@Component
public class JWTAuthenticationConfig {
    private final String secret = "change-this-secret";
    private final long expirationSeconds = 3600;

    public String createToken(String subject) {
        String headerJson = "{\"alg\":\"HS256\",\"typ\":\"JWT\"}";
        long now = Instant.now().getEpochSecond();
        long exp = now + expirationSeconds;
        String payloadJson = "{\"sub\":\"" + subject + "\",\"iat\":" + now + ",\"exp\":" + exp + "}";
        String header = base64UrlEncode(headerJson.getBytes(StandardCharsets.UTF_8));
        String payload = base64UrlEncode(payloadJson.getBytes(StandardCharsets.UTF_8));
        String unsignedToken = header + "." + payload;
        String signature = sign(unsignedToken);
        return unsignedToken + "." + signature;
    }

    public String validateTokenAndGetSubject(String token) {
        String[] parts = token.split("\\.");
        if (parts.length != 3) return null;
        String unsigned = parts[0] + "." + parts[1];
        if (!constantTimeEquals(sign(unsigned), parts[2])) return null;
        String payloadJson = new String(base64UrlDecode(parts[1]), StandardCharsets.UTF_8);
        String sub = extract(payloadJson, "sub");
        long exp = Long.parseLong(extract(payloadJson, "exp"));
        if (Instant.now().getEpochSecond() > exp) return null;
        return sub;
    }

    private String sign(String data) {
        try {
            Key key = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(key);
            return base64UrlEncode(mac.doFinal(data.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception e) {
            return null;
        }
    }

    private String extract(String json, String field) {
        String key = "\"" + field + "\":";
        int i = json.indexOf(key);
        if (i < 0) return null;
        int s = i + key.length();
        char c = json.charAt(s);
        if (c == '"') {
            int e = json.indexOf('"', s + 1);
            return json.substring(s + 1, e);
        } else {
            int e = s;
            while (e < json.length() && Character.isDigit(json.charAt(e))) e++;
            return json.substring(s, e);
        }
    }

    private String base64UrlEncode(byte[] bytes) {
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }

    private byte[] base64UrlDecode(String str) {
        return Base64.getUrlDecoder().decode(str);
    }

    private boolean constantTimeEquals(String a, String b) {
        if (a == null || b == null) return false;
        if (a.length() != b.length()) return false;
        int r = 0;
        for (int i = 0; i < a.length(); i++) r |= a.charAt(i) ^ b.charAt(i);
        return r == 0;
    }
}
