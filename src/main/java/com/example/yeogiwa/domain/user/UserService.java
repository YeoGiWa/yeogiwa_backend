package com.example.yeogiwa.domain.user;

import com.example.yeogiwa.auth.jwt.JwtUtil;
import com.example.yeogiwa.domain.user.dto.LoginDto;
import com.example.yeogiwa.domain.user.dto.LoginResponseDto;
import com.example.yeogiwa.domain.user.dto.RegisterDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.security.SecureRandom;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public Optional<UserEntity> getUser(Long id) {
            return userRepository.findById(id);
    }

    public LoginResponseDto login(LoginDto loginDto) {
        LoginResponseDto loginResponseDto = new LoginResponseDto();
        loginResponseDto.setStatus(200);

        /* Make user if doesn't exist */
        UserEntity user = userRepository.findByOauth2Id(
            loginDto.getRegistration() + " " + loginDto.getRegistrationId()
        ).orElseGet(() -> {
                SecureRandom randomPassword = new SecureRandom();
                loginResponseDto.setStatus(201);
                return userRepository.save(
                    UserEntity.builder()
                        .email(loginDto.getEmail())
                        .password(bCryptPasswordEncoder.encode(randomPassword.toString()))
                        .name(loginDto.getName())
                        .oauth2Id(loginDto.getRegistration() + " " + loginDto.getRegistrationId())
                        .build()
                );
            }
        );

        /* Issue tokens */
        String accessToken = jwtUtil.createAccessToken(user.getId(), user.getRole().name());
        String refreshToken = jwtUtil.createRefreshToken(user.getId(), user.getRole().name());

        loginResponseDto.setUserId(user.getId());
        loginResponseDto.setAccessToken(accessToken);
        loginResponseDto.setRefreshToken(refreshToken);
        return loginResponseDto;
    }

    public Long createUser(RegisterDTO registerDTO) throws HttpClientErrorException {
        String email = registerDTO.getEmail();
        String password = registerDTO.getPassword();

        // Check if already exists
        Boolean isExist = userRepository.existsByEmail(email);

        if (isExist) throw new HttpClientErrorException(HttpStatusCode.valueOf(409));
        //
        UserEntity user = UserEntity.builder()
                .email(email)
                .password(bCryptPasswordEncoder.encode(password))
                .build();

        return userRepository.save(user).getId();
    }

    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }
}
