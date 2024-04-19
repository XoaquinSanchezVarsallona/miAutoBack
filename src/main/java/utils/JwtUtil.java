package utils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtil {
    private static final String SECRET_KEY = "llaveSecreta";

    public static String generateToken(String username, String userID) {
        return Jwts.builder()
                .setSubject(username)
                .claim("userId", userID) // Add the user ID as a custom claim
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 2 * 60 * 60 * 1000)) // 2 hours
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public static String basicValidateToken(String token) {
        try {
            return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody().getSubject();
        } catch (SignatureException e) {
            return null;
        }
    }

    public static Map<String, String> validateToken(String token) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token)
                    .getBody();

            Map<String, String> userInfo = new HashMap<>();
            userInfo.put("username", claims.getSubject());
            userInfo.put("userId", claims.get("userId", String.class)); // Ensure that you handle possible class cast issues

            return userInfo;
        } catch (SignatureException e) {
            return null; // In production, you might want to handle different types of exceptions differently
        }
    }
}
