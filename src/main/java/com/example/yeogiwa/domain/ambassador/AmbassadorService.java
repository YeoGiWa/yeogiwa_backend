package com.example.yeogiwa.domain.ambassador;

import com.example.yeogiwa.domain.ambassador.dto.AmbassadorDto;
import com.example.yeogiwa.domain.ambassador.dto.CreateAmbassadorRequest;
import com.example.yeogiwa.domain.event.EventEntity;
import com.example.yeogiwa.domain.event.EventRepository;
import com.example.yeogiwa.domain.user.UserEntity;
import com.example.yeogiwa.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AmbassadorService {
    private final AmbassadorRepository ambassadorRepository;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    @Transactional
    public AmbassadorDto createAmbassador(String email, CreateAmbassadorRequest request) {
        UserEntity user = userRepository.findByEmail(email);

        EventEntity event = eventRepository.findById(request.getEventId())
                .orElseThrow(() -> new RuntimeException("Event not found"));

        // TODO: QR 코드 생성
        AmbassadorEntity ambassador = AmbassadorEntity.builder()
                .user(user)
                .event(event)
                .qr("test")
                .build();

        ambassadorRepository.save(ambassador);

        return AmbassadorDto.from(ambassador);
    }

    @Transactional(readOnly = true)
    public AmbassadorDto getAmbassadorById(UUID id) {
        AmbassadorEntity ambassador = ambassadorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ambassador not found"));

        return AmbassadorDto.from(ambassador);
    }

    @Transactional(readOnly = true)
    public List<AmbassadorDto> listAmbassadorsByEvent(Long eventId) {
        EventEntity event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        List<AmbassadorEntity> ambassadorEntities = ambassadorRepository.findAllByEvent_Id(eventId);

        return ambassadorEntities.stream()
                .map(AmbassadorDto::from)
                .toList();
    }
}
