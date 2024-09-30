package com.example.yeogiwa.domain.user.dto;

import lombok.Getter;

@Getter
public class AppleDto {
    private String access_token;
    private String token_type;
    private Integer expires_in;
    private String id_token;
}
