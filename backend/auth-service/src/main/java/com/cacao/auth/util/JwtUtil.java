package com.cacao.auth.util;

import java.util.Date;

/**
 * Utilidad para manejo de JWT tokens.
 * En producción, usar una librería como io.jsonwebtoken:jjwt
 */
public class JwtUtil {
    private static final long TOKEN_EXPIRATION = 86400000; // 24 horas en ms

    /**
     * Genera un token JWT simple (versión mock para desarrollo)
     */
    public static String generateToken(String username) {
        // En producción, usar JwtBuilder de JJWT
        String token = Base64Util.encode(String.format(
            "{\"username\":\"%s\",\"iat\":%d,\"exp\":%d}",
            username,
            System.currentTimeMillis(),
            System.currentTimeMillis() + TOKEN_EXPIRATION
        ));
        return token;
    }

    /**
     * Valida un token JWT
     */
    public static boolean validateToken(String token) {
        try {
            String decoded = Base64Util.decode(token);
            // Validación simple. En producción, validar firma y expiración
            return decoded.contains("username") && decoded.contains("exp");
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Extrae el username del token
     */
    public static String getUsernameFromToken(String token) {
        try {
            String decoded = Base64Util.decode(token);
            // Extracción simple. En producción, usar parser de JJWT
            int start = decoded.indexOf("\"username\":\"") + 12;
            int end = decoded.indexOf("\"", start);
            return decoded.substring(start, end);
        } catch (Exception e) {
            return null;
        }
    }
}
