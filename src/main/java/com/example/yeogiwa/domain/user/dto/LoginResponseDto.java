package com.example.yeogiwa.domain.user.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponseDto {
    private Integer status;
    private Long userId;
    private String accessToken;
    private String refreshToken;
}
