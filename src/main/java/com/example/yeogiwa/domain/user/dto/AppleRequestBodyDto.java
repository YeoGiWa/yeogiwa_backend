package com.example.yeogiwa.domain.user.dto;

import lombok.*;

@Getter @Setter
@NoArgsConstructor
@JsonFormData
public class AppleRequestBodyDto {
    private String client_id;
    private String client_secret;
    private String grant_type;
    private String refresh_token;

    public AppleRequestBodyDto (String clientId, String clientSecret, String refreshToken) {
        this.client_id = clientId;
        this.client_secret = clientSecret;
        this.grant_type = "refresh_token";
        this.refresh_token = refreshToken;
    }
}
