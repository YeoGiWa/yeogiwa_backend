package com.example.yeogiwa.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.*;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
    info = @Info(
        title = "Yeogiwa API",
        description = "\"여기와\" API 문서",
        version = "v1.0.0"
    ),
    security = {
        @SecurityRequirement(name = "Authorization"),
        @SecurityRequirement(name = "Kakao", scopes = {
            "profile_nickname",
            "profile_image",
            "account_email"
        })
    },
    servers = @Server(
        url = "/"
    )
)
@SecuritySchemes({
    @SecurityScheme(
        name = "Kakao",
        type = SecuritySchemeType.OAUTH2,
        flows = @OAuthFlows(
            authorizationCode = @OAuthFlow(
                authorizationUrl = "https://kauth.kakao.com/oauth/authorize",
                tokenUrl = "https://kauth.kakao.com/oauth/token",
                scopes = {
                    @OAuthScope(name = "profile_nickname", description = "[동의항목] 카카오톡 프로필 이름"),
                    @OAuthScope(name = "profile_image", description = "[동의항목] 카카오톡 프로필 이미지"),
                    @OAuthScope(name = "account_email", description = "[동의항목] 카카오톡 계정 이메일"),
                })
        )
    ),
    @SecurityScheme(
        name = "Authorization",
        in = SecuritySchemeIn.HEADER,
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT"
    )
})
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI() { return new OpenAPI(); }

}
