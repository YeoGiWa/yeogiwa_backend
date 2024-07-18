package com.example.yeogiwa.service;

import com.example.yeogiwa.dto.RegisterDTO;
import com.example.yeogiwa.entity.UserEntity;
import com.example.yeogiwa.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserEntity getUser(int id) {
        if (userRepository.existsById(id))
            return userRepository.findById(id);
        return UserEntity.builder()
                .id(-1)
                .build();
    }

    public int createUser(RegisterDTO registerDTO) {
        String email = registerDTO.getEmail();
        String password = registerDTO.getPassword();

        // Check if already exists
        Boolean isExist = userRepository.existsByEmail(email);

        if (isExist) return -1;
        //
        UserEntity user = UserEntity.builder()
                .email(email)
                .password(bCryptPasswordEncoder.encode(password))
                .build();

        return userRepository.save(user).getId();
    }
}
