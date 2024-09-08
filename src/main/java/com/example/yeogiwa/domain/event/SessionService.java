package com.example.yeogiwa.domain.event;

import com.example.yeogiwa.domain.event.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SessionService {
    private final EventRepository eventRepository;
    private final SessionRepository sessionRepository;

    @Transactional
    public SessionDto createSession(CreateSessionRequest request) {
        EventEntity event = eventRepository.findById(request.getEventId())
            .orElseThrow(() -> new IllegalArgumentException("해당 이벤트를 찾을 수 없습니다."));

        SessionEntity session = SessionEntity.builder()
            .event(event)
            .count(request.getCount())
            .startDate(request.getStartDate())
            .startTime(request.getStartTime())
            .build();

        sessionRepository.save(session);

        return SessionDto.from(session);
    }

    @Transactional
    public SessionDto updateSession(UUID id, UpdateSessionRequest request) {
        SessionEntity session = sessionRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("해당 세션을 찾을 수 없습니다."));

        session.setCount(request.getCount());
        session.setStartDate(request.getStartDate());
        session.setStartTime(request.getStartTime());

        sessionRepository.save(session);

        return SessionDto.from(session);
    }

    @Transactional
    public void deleteSession(UUID id) {
        SessionEntity session = sessionRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("해당 세션을 찾을 수 없습니다."));

        sessionRepository.delete(session);
    }
}
