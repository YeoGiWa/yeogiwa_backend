package com.example.yeogiwa.auth;

import com.example.yeogiwa.auth.oauth.PrincipalDetails;
import com.example.yeogiwa.domain.user.UserEntity;
import com.example.yeogiwa.domain.user.UserRepository;
import com.example.yeogiwa.util.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Base64;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Slf4j
public class JwtFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String accessToken = request.getHeader("Authorization");
        String refreshToken = null;
        if (request.getCookies() != null) {
            for (Cookie cookie: request.getCookies()) {
                if (cookie.getName().equals("refresh") && cookie.getValue()!=null) {
                    refreshToken = new String(Base64.getDecoder().decode(cookie.getValue()));
                }
            }
        }
        log.info("requestURL: {}", request.getRequestURL());

        if (accessToken == null) {
            filterChain.doFilter(request, response);
            return;
        }

        accessToken = jwtUtil.substringToken(accessToken);
        refreshToken = jwtUtil.substringToken(refreshToken);
        if (accessToken==null || refreshToken==null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            jwtUtil.removeJwtCookie(response);
            return;
        }

        boolean newTokenNeeded = false;
        try {
            boolean isValid = jwtUtil.validateToken(accessToken);
            if (!isValid) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                jwtUtil.removeJwtCookie(response);
                return;
            }
        } catch (ExpiredJwtException accessTokenExpired) {
            try {
                boolean isValid = jwtUtil.validateToken(refreshToken);
                if (!isValid) {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    jwtUtil.removeJwtCookie(response);
                    return;
                } else { newTokenNeeded = true; }
            } catch (ExpiredJwtException refreshTokenExpired) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                jwtUtil.removeJwtCookie(response);
                return;
            }
        }

        Long id;
        if (newTokenNeeded) {
            id = jwtUtil.getId(refreshToken);
        } else {
            id = jwtUtil.getId(accessToken);
        }

        UserEntity user;
        try {
            user = userRepository.findById(id).get();
        } catch (NoSuchElementException e) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            jwtUtil.removeJwtCookie(response);
            return;
        }
        if (user == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            jwtUtil.removeJwtCookie(response);
            return;
        }

        if (newTokenNeeded) {
            /* Issue tokens */
            String newAccessToken = jwtUtil.createAccessToken(user.getId(), user.getRole().name());
            String newRefreshToken = jwtUtil.createRefreshToken(user.getId(), user.getRole().name());
            ResponseCookie responseCookie = ResponseCookie.from(
                    "refresh",
                    Base64.getEncoder().encodeToString(newRefreshToken.getBytes())
                )
                .httpOnly(true)
                .secure(true)
                .maxAge(14 * 24 * 60 * 60 * 1000L) // 2 weeks
                .build();
            jwtUtil.addJwtToHeader(newAccessToken, response);
            jwtUtil.addJwtToCookie(newRefreshToken, response);
        }

        PrincipalDetails principal = new PrincipalDetails(user, null);
        Authentication authentication = new UsernamePasswordAuthenticationToken(principal, accessToken, principal.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }
}
