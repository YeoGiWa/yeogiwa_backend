package com.example.yeogiwa.domain.ambassador;

import com.example.yeogiwa.domain.ambassador.dto.AmbassadorDto;
import com.example.yeogiwa.domain.event.EventEntity;
import com.example.yeogiwa.domain.event.EventRepository;
import com.example.yeogiwa.util.QRUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class AmbassadorService {
    private final AmbassadorRepository ambassadorRepository;
    private final EventRepository eventRepository;
    private final QRUtil qrUtil;

    public Long createAmbassador(Long userId, Long eventId) {
        Boolean isAmbassadorExists = ambassadorRepository.existsByUser_IdAndEvent_Id(userId, eventId);
        Optional<EventEntity> eventOpt = eventRepository.findById(eventId);
        if (isAmbassadorExists) throw new HttpClientErrorException(HttpStatusCode.valueOf(409));
        if (eventOpt.isEmpty()) throw new HttpClientErrorException(HttpStatusCode.valueOf(404));
        EventEntity event = eventOpt.get();
        if (!event.getIsApplicable()) throw new HttpClientErrorException(HttpStatusCode.valueOf(403));
        AmbassadorEntity newAmbassador = AmbassadorEntity.builder()
            .userId(userId)
            .eventId(eventId)
            .build();
        ambassadorRepository.save(newAmbassador);
        newAmbassador.setQr(qrUtil.createAmbassadorQR(newAmbassador.getId()));
        ambassadorRepository.save(newAmbassador);
        return newAmbassador.getId();
    }


    public AmbassadorDto getAmbassadorById(Long id) {
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
