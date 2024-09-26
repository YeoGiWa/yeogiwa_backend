package com.example.yeogiwa.domain.host;

import com.example.yeogiwa.domain.event.EventEntity;
import com.example.yeogiwa.domain.event.EventRepository;
import com.example.yeogiwa.domain.host.dto.CreateHostRequest;
import com.example.yeogiwa.domain.host.dto.HostDto;
import com.example.yeogiwa.domain.user.UserEntity;
import com.example.yeogiwa.domain.user.UserRepository;
import com.example.yeogiwa.enums.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class HostService {
    private final HostRepository hostRepository;
    private final UserRepository userRepository;

    @Transactional
    public HostDto createHost(String email, CreateHostRequest request) {
        UserEntity user = userRepository.findByEmail(email);

        HostEntity host = HostEntity.builder()
            .user(user)
            .build();

        // TODO: 실제로는 request 내용 저장하고 있어야 함
        user.setRole(Role.ROLE_ADMIN);

        userRepository.save(user);
        hostRepository.save(host);

        return HostDto.from(host);
    }
}
