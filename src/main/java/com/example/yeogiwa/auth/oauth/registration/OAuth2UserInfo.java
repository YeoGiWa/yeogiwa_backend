package com.example.yeogiwa.auth.oauth.registration;

public interface OAuth2UserInfo {
    String getProviderId();
    String getProvider();
    String getEmail();
    String getName();
}
