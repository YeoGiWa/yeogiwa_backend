package com.example.yeogiwa.auth.oauth;

import com.example.yeogiwa.auth.jwt.JwtUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;


import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class CustomOAuth2AuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private final JwtUtil jwtUtil;
    private static final String redirectURL = "/";


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("onAuthenticationSuccess");
        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();

        String accessToken = jwtUtil.createToken(
            "access",
            principal.getUserId(),
            principal.getRole(),
            1 * 60 * 60 * 1000L // 1 hour
        );
        String refreshToken = jwtUtil.createToken(
            "refresh",
            principal.getUserId(),
            principal.getRole(),
            14 * 24 * 60 * 60 * 1000L // 2 weeks
        );
        jwtUtil.addJwtToHeader(accessToken, response);
        jwtUtil.addJwtToCookie(refreshToken, response);

        log.info("requestURL: {}", request.getRequestURL());
        log.info("request: {}", request.getHeader("Referer"));
//        if (prevPage != null) {
//            request.getSession().removeAttribute("prevPage");
//        }

//        String redirectUrl = UriComponentsBuilder.fromUriString(REDIRECT_URL).build().toUriString();
//        response.sendRedirect(redirectUrl);
//        response.sendRedirect();
    }
}
