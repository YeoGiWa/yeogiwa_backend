package com.example.yeogiwa.domain.ambassador;

import com.example.yeogiwa.domain.ambassador.dto.AmbassadorDto;
import com.example.yeogiwa.domain.ambassador.dto.CreateAmbassadorRequest;
import com.example.yeogiwa.domain.event.EventEntity;
import com.example.yeogiwa.domain.event.EventRepository;
import com.example.yeogiwa.domain.user.UserEntity;
import com.example.yeogiwa.domain.user.UserRepository;
import com.example.yeogiwa.util.QRUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class AmbassadorService {
    private final AmbassadorRepository ambassadorRepository;
    private final EventRepository eventRepository;
    private final QRUtil qrUtil;

    public Long createAmbassador(Long userId, Long eventId) {
        Boolean isAmbassadorExists = ambassadorRepository.existsByUser_IdAndEvent_Id(userId, eventId);
        if (isAmbassadorExists) throw new HttpClientErrorException(HttpStatusCode.valueOf(409));
        AmbassadorEntity newAmbassador = AmbassadorEntity.builder()
            .userId(userId)
            .eventId(eventId)
            .qr(qrUtil.createAmbassadorQR())
            .build();
        return ambassadorRepository.save(newAmbassador).getId();
    }


    public AmbassadorDto getAmbassadorById(UUID id) {
        AmbassadorEntity ambassador = ambassadorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ambassador not found"));

        return AmbassadorDto.from(ambassador);
    }

    public List<AmbassadorDto> listAmbassadorsByEvent(Long eventId) {
        EventEntity event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        List<AmbassadorEntity> ambassadorEntities = ambassadorRepository.findAllByEvent_Id(eventId);

        return ambassadorEntities.stream()
                .map(AmbassadorDto::from)
                .toList();
    }
}
