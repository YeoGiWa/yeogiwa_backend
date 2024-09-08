package com.example.yeogiwa.auth.oauth;

import com.example.yeogiwa.auth.oauth.registration.KakaoUserInfo;
import com.example.yeogiwa.domain.user.UserEntity;
import com.example.yeogiwa.domain.user.UserRepository;
import com.example.yeogiwa.enums.Role;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest request) throws OAuth2AuthenticationException {
        log.info("loadUser: {}", request);
        OAuth2User oAuth2User = super.loadUser(request);
        KakaoUserInfo kakaoUserInfo = new KakaoUserInfo(oAuth2User.getAttributes());

        UserEntity user = userRepository.findByOauth2Id(
            kakaoUserInfo.getProvider() + " " + kakaoUserInfo.getProviderId()
        ).orElseGet(() -> {
            SecureRandom randomPassword = new SecureRandom();
            return userRepository.save(
                UserEntity.builder()
                    .email(kakaoUserInfo.getEmail())
                    .password(bCryptPasswordEncoder.encode(randomPassword.toString()))
                    .name(kakaoUserInfo.getName())
                    .oauth2Id(kakaoUserInfo.getProvider() + " " + kakaoUserInfo.getProviderId())
                    .build()
            );
            }
        );

        PrincipalDetails customOAuth2User = new PrincipalDetails(user, oAuth2User.getAttributes());
        log.info("getAttributes: {}", oAuth2User.getAttributes());
        Authentication authentication = new UsernamePasswordAuthenticationToken(customOAuth2User, null, customOAuth2User.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return customOAuth2User;
    }
}
