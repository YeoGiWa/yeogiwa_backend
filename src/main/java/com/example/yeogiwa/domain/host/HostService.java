package com.example.yeogiwa.domain.host;

import com.example.yeogiwa.domain.event.EventEntity;
import com.example.yeogiwa.domain.event.EventRepository;
import com.example.yeogiwa.domain.host.dto.CreateHostRequest;
import com.example.yeogiwa.domain.host.dto.HostDto;
import com.example.yeogiwa.domain.user.UserEntity;
import com.example.yeogiwa.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class HostService {
    private final HostRepository hostRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    @Transactional
    public HostDto createHost(String email, CreateHostRequest request) {
        UserEntity user = userRepository.findByEmail(email);

        EventEntity event = eventRepository.findById(request.getEventId())
            .orElseThrow(() -> new RuntimeException("Event not found"));

        HostEntity host = HostEntity.builder()
            .user(user)
            .build();

        hostRepository.save(host);

        return HostDto.from(host);
    }
}
