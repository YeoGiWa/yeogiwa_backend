package com.example.yeogiwa.domain.user;

import com.example.yeogiwa.domain.user.dto.RegisterDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Optional;
import java.util.UUID;

@Service
//@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @jakarta.transaction.Transactional
    public Optional<UserEntity> getUser(Long id) {
//        if (userRepository.existsByOauth2Id(email))
            return userRepository.findById(id);
//        return null;
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
