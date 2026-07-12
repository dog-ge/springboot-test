package org.example.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;

@Component
public class JwtUtil {

    private static SecretKey ACCESS_KEY;
    private static SecretKey REFRESH_KEY;
    private static long ACCESS_EXPIRATION;
    private static long REFRESH_EXPIRATION;

    @Value("${jwt.secret}")
    public void setSecret(String secret) {
        ACCESS_KEY = Keys.hmacShaKeyFor(("ACCESS_" + secret).getBytes(StandardCharsets.UTF_8));
        REFRESH_KEY = Keys.hmacShaKeyFor(("REFRESH_" + secret).getBytes(StandardCharsets.UTF_8));
    }

    @Value("${jwt.access-expiration}")
    public void setAccessExpiration(long expiration) {
        ACCESS_EXPIRATION = expiration;
    }

    @Value("${jwt.refresh-expiration}")
    public void setRefreshExpiration(long expiration) {
        REFRESH_EXPIRATION = expiration;
    }

    public static String generateAccessToken(String userId, String username, Map<String, Object> claims) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + ACCESS_EXPIRATION);

        return Jwts.builder()
                .setSubject(userId)
                .claim("username", username)
                .claim("tokenType", "access")
                .addClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiry)
                .signWith(ACCESS_KEY, SignatureAlgorithm.HS256)
                .compact();
    }

    public static String generateRefreshToken(String userId) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + REFRESH_EXPIRATION);

        return Jwts.builder()
                .setSubject(userId)
                .claim("tokenType", "refresh")
                .setIssuedAt(now)
                .setExpiration(expiry)
                .signWith(REFRESH_KEY, SignatureAlgorithm.HS256)
                .compact();
    }

    public static Claims parseAccessToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(ACCESS_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public static Claims parseRefreshToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(REFRESH_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public static long getExpiration(String token) {
        try {
            Claims claims = parseAccessToken(token);
            return claims.getExpiration().getTime() - System.currentTimeMillis();
        } catch (Exception e) {
            return 0;
        }
    }
}