package com.example.yeogiwa.service;

import com.example.yeogiwa.dto.user.RegisterDTO;
import com.example.yeogiwa.entity.UserEntity;
import com.example.yeogiwa.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserEntity getUser(String email) {
        if (userRepository.existsByEmail(email))
            return userRepository.findByEmail(email);
        return null;
    }

    public UUID createUser(RegisterDTO registerDTO) throws HttpClientErrorException {
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
}
