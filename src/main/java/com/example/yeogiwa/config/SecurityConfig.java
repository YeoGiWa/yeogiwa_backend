package com.example.yeogiwa.config;

import com.example.yeogiwa.auth.jwt.JwtFilter;
import com.example.yeogiwa.auth.jwt.JwtUtil;
import com.example.yeogiwa.auth.oauth.CustomOAuth2AuthenticationFailureHandler;
import com.example.yeogiwa.auth.oauth.CustomOAuth2AuthenticationSuccessHandler;
import com.example.yeogiwa.auth.oauth.CustomOAuth2UserService;
import com.example.yeogiwa.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final CustomOAuth2UserService customOAuth2UserService;

    public static String[] AllowedURLsToPublic = {
        "/error",
        "/favicon.ico",
        "/api-docs*",
        "/swagger-ui/**",
        "/v3/api-docs/**",
        "/oauth2/authorization/kakao",
        "/login/oauth2/code/kakao",
        "/redis/**"
    };

    public static String[] AllowedGetMethodURLsToPublic = {
        "/events/**"
    };

    public static String[] AllowedURLsToAdmin = {

    };


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable)
            .cors((cors) -> cors
                .configurationSource(corsConfigurationSource()))
            .formLogin(AbstractHttpConfigurer::disable)
            .httpBasic(AbstractHttpConfigurer::disable)
            .oauth2Login((oAuth2) ->
                oAuth2
                    .userInfoEndpoint((userInfoEndpointConfig) -> userInfoEndpointConfig
                        .userService(customOAuth2UserService))
                    .successHandler(successHandler())
                    .failureHandler(failureHandler())
            )
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

    private CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Collections.singletonList("/**"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
//        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(Collections.singletonList("*"));
        configuration.setExposedHeaders(Arrays.asList("Authorization", "Set-Cookie"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public CustomOAuth2AuthenticationSuccessHandler successHandler() { return new CustomOAuth2AuthenticationSuccessHandler(jwtUtil); }

    @Bean
    public CustomOAuth2AuthenticationFailureHandler failureHandler() { return new CustomOAuth2AuthenticationFailureHandler(); }
}
