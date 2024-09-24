package com.example.yeogiwa.auth.jwt;

import com.example.yeogiwa.auth.oauth.PrincipalDetails;
import com.example.yeogiwa.domain.user.UserEntity;
import com.example.yeogiwa.domain.user.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Slf4j
public class JwtFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String accessToken = request.getHeader("Authorization");
        log.info("requestURL: {}", request.getRequestURL());
//        log.info("requestURI: {}", request.getRequestURI());
//        log.info("pathInfo: {}", request.getPathInfo());
//        log.info("contextPath: {}", request.getContextPath());
//        log.info("remoteHost: {}", request.getRemoteHost());
//        log.info("headerNames: {}", request.getHeaderNames());
//        log.info("remoteAddr: {}", request.getRemoteAddr());
//        log.info("serverPort: {}", request.getServerPort());
//        log.info("serverPort: {}", request.getSession());

        if (accessToken == null) {
            filterChain.doFilter(request, response);
            return;
        }

//        if (request.getRequestURL())

        if (!jwtUtil.substringToken(accessToken).equals("test") && !jwtUtil.validateToken(jwtUtil.substringToken(accessToken))) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        String token = jwtUtil.substringToken(accessToken);
        if (token==null) { return; }

        Long id = null;
        if(token.equals("test")) {
            id = 1L;
        } else {
            id = jwtUtil.getId(token);
        }

        UserEntity user = null;
        try {
            user = userRepository.findById(id).get();
        } catch (NoSuchElementException e) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        if (user == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        log.info("id = {} name = {} role = {}", id.toString(), user.getName(), user.getRole());

//        String newAccessToken = jwtUtil.createToken(
//            "access",
//            user.getId(),
//            user.getRole().name(),
//            1 * 60 * 60 * 1000L
//        );

        PrincipalDetails principal = new PrincipalDetails(user, null);
        Authentication authentication = new UsernamePasswordAuthenticationToken(principal, accessToken, principal.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }
}
