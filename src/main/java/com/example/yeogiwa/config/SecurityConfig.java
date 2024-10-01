package com.example.yeogiwa.config;

import com.example.yeogiwa.auth.JwtFilter;
import com.example.yeogiwa.util.JwtUtil;
import com.example.yeogiwa.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    public static String[] AllowedURLsToPublic = {
        "/error",
        "/favicon.ico",
        "/api-docs*",
        "/swagger-ui/**",
        "/v3/api-docs/**",
        "/oauth2/authorization/kakao",
        "/login/oauth2/code/kakao",
        "/redis/**",
        "/user/login"
    };

    public static String[] AllowedGetMethodURLsToPublic = {
        "/event/**"
    };

    public static String[] AllowedURLsToAdmin = {

    };


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable)
            .formLogin(AbstractHttpConfigurer::disable)
            .httpBasic(AbstractHttpConfigurer::disable)
            .addFilterBefore(new JwtFilter(jwtUtil, userRepository),
                UsernamePasswordAuthenticationFilter.class)
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(HttpMethod.GET, AllowedGetMethodURLsToPublic).permitAll()
                .requestMatchers(AllowedURLsToPublic).permitAll()
                .requestMatchers(AllowedURLsToAdmin).hasRole("ADMIN")
                .anyRequest().authenticated()
            )
            .sessionManagement(sessionManagementConfigure ->
                sessionManagementConfigure.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // 세션 사용하지 않음

        ;
        return http.build();
    }
}
