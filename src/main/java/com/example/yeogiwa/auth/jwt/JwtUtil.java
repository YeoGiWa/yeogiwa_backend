package com.example.yeogiwa.auth.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Base64;
import java.util.Date;

@Component
@Slf4j
public class JwtUtil {
    private final SecretKey key;
    public static final String BEARER_PREFIX = "Bearer ";

    public JwtUtil(@Value("${spring.jwt.secret}") String secretKey) {
        byte[] keyBytes = Decoders.BASE64URL.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public String createToken(String type, Long id, String role, Long expTime) {
        Long now = System.currentTimeMillis();
        return BEARER_PREFIX + Jwts.builder()
            .claim("type", type) // access | refresh
            .claim("id", id) // user_id
            .claim("role", role) // USER | ADMIN
            .issuedAt(new Date(now))
            .expiration(new Date(now + expTime))
            .signWith(key, Jwts.SIG.HS512)
            .compact();
    }

    public String createAccessToken(Long userId, String role) {
        return createToken(
            "access",
            userId,
            role,
            1 * 60 * 60 * 1000L // 1 hour
        );
    }

    public String createRefreshToken(Long userId, String role) {
        return createToken(
            "refresh",
            userId,
            role,
            14 * 24 * 60 * 60 * 1000L // 2 weeks
        );
    }

    public boolean validateToken(String token) throws JwtException {
        try {
            Jwts.parser().verifyWith(key).build().parseSignedClaims(token);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT Token", e);
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT Token", e);
        } catch (IllegalArgumentException e) {
            log.info("JWT claims string is empty", e);
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT Token", e);
            throw new ExpiredJwtException(e.getHeader(), e.getClaims(), e.getMessage(), e.getCause());
        }
        return false;
    }

    public Claims parseClaims(String token) {
        try {
            return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

    public void addJwtToHeader(String token, HttpServletResponse res) {
        res.addHeader(HttpHeaders.AUTHORIZATION, token);
    }

    public void addJwtToCookie(String token, HttpServletResponse res) {
        Cookie cookie = new Cookie(
            "refresh",
            Base64.getEncoder().encodeToString(token.getBytes())
        );
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setMaxAge(14 * 24 * 60 * 60 * 1000);
        res.addCookie(cookie);
    }

    public void removeJwtCookie(HttpServletResponse res) {
        Cookie cookie = new Cookie(
            "refresh",
            null
        );
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setMaxAge(0);
        res.addCookie(cookie);
    }

    public String substringToken(String tokenValue) {
        if (StringUtils.hasText(tokenValue) && tokenValue.startsWith(BEARER_PREFIX)) {
            return tokenValue.substring(7);
        }
        return null;
    }

    public Long getId(String token) {
        return parseClaims(token).get("id", Long.class);
    }
}
