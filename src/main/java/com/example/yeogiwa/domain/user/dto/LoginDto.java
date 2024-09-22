package com.example.yeogiwa.domain.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "로그인 DTO")
public class LoginDto {
    @Schema(description = "인증받는 기관", defaultValue = "kakao", allowableValues = {"kakao", "apple"}, requiredMode = Schema.RequiredMode.REQUIRED)
    private String registration;
    @Schema(description = "기관에서 발급한 user ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private String registrationId;
    private String email;
    private String name;
    @Schema(description = "기관에서 발급한 token", requiredMode = Schema.RequiredMode.REQUIRED)
    private String token;
}
