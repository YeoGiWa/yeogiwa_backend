package com.example.yeogiwa.service;

import com.example.yeogiwa.domain.user.UserEntity;
import com.example.yeogiwa.domain.user.UserRepository;
import com.example.yeogiwa.enums.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@Rollback(false) // DB에 데이터가 남아있도록 하고 싶다면 주석 해제
class UserServiceTest {

    @Autowired
    UserRepository userRepository;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Test
    void createUser() {
        UserEntity user1 = UserEntity.builder()
                .email("test@example.com")
                .password(bCryptPasswordEncoder.encode("test1"))
                .build();
        UserEntity user2 = UserEntity.builder()
                .email("test1@example.com")
                .password(bCryptPasswordEncoder.encode("test2"))
                .role(Role.ROLE_ADMIN)
                .build();
        user1 = userRepository.save(user1);
        user2 = userRepository.save(user2);

        UserEntity result = userRepository.findById(user1.getId());
//
        assertThat(result.getId()).isInstanceOf(UUID.class);
    }
}