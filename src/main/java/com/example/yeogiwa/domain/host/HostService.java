package com.example.yeogiwa.domain.host;

import com.example.yeogiwa.domain.event.EventEntity;
import com.example.yeogiwa.domain.event.EventRepository;
import com.example.yeogiwa.domain.event.dto.EventDto;
import com.example.yeogiwa.domain.host.dto.request.CreateHostDto;
import com.example.yeogiwa.domain.host.dto.request.CreateHostEventDto;
import com.example.yeogiwa.domain.host.dto.request.CreateHostRoundDto;
import com.example.yeogiwa.domain.host.dto.HostDto;
import com.example.yeogiwa.domain.user.UserEntity;
import com.example.yeogiwa.domain.user.UserRepository;
import com.example.yeogiwa.enums.Role;
import com.example.yeogiwa.openapi.OpenApiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class HostService {
    private final HostRepository hostRepository;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final OpenApiService openApiService;

    public HostDto createHost(Long userId, CreateHostDto body) {
        Boolean isValidBusiness = openApiService.isValidBusiness(body.getBusinessNumber());

        if (!isValidBusiness) throw new HttpClientErrorException(HttpStatusCode.valueOf(400));

        Optional<UserEntity> userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) throw new HttpClientErrorException(HttpStatusCode.valueOf(404));
        UserEntity user = userOpt.get();
        HostEntity host = HostEntity.builder()
            .user(user)
            .name(body.getName())
            .delegateName(body.getDelegateName())
            .chargerName(body.getChargerName())
            .chargerEmail(body.getChargerEmail())
            .chargerPhoneNumber(body.getChargerPhoneNumber())
            .businessNumber(body.getBusinessNumber())
            .build();
        try {
            hostRepository.save(host);

            user.setRole(Role.ROLE_ADMIN);
            userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            throw new HttpClientErrorException(HttpStatusCode.valueOf(409));
        }

        return HostDto.from(host);
    }

    public List<EventDto> getHostEvents(Long userId) {
        HostEntity host = hostRepository.findByUser_Id(userId);
        return eventRepository.findAllByHost_Id(host.getId()).stream().map(
            EventDto::from
        ).toList();
    }

    public List<EventDto> getHostEventRounds(Long userId, Long upperEventId) {
        HostEntity host = hostRepository.findByUser_Id(userId);
        if (host == null) throw new HttpClientErrorException(HttpStatusCode.valueOf(404));
        return eventRepository.findAllByHost_IdAndUpperEvent_Id(host.getId(), upperEventId).stream().map(
            EventDto::from
        ).toList();
    }

    public List<Long> createHostEvents(Long userId, List<CreateHostEventDto> eventDtos) {
        HostEntity host = hostRepository.findByUser_Id(userId);
        if (host == null) throw new HttpClientErrorException(HttpStatusCode.valueOf(404));
        List<EventEntity> events = eventDtos.stream().map(
            eventDto -> EventEntity.builder()
                .id(eventDto.getEventId())
                .hostId(host.getId())
                .title(eventDto.getTitle())
                .ratio(eventDto.getRatio())
            .build()
        ).toList();
        return eventRepository.saveAllAndFlush(events).stream().map(
            EventEntity::getId
        ).toList();
    }

    public List<Long> createHostRounds(Long userId, Long upperEventId, List<CreateHostRoundDto> eventsDtos) {
        HostEntity host = hostRepository.findByUser_Id(userId);
        if (host == null) throw new HttpClientErrorException(HttpStatusCode.valueOf(404));
        List<EventEntity> events = eventsDtos.stream().map(
            eventDto -> EventEntity.builder()
                .id(eventDto.getEventId())
                .hostId(host.getId())
                .upperEventId(upperEventId)
                .title(eventDto.getTitle())
                .round(eventDto.getRound())
                .ratio(eventDto.getRatio())
                .build()
        ).toList();
        try {
            return eventRepository.saveAllAndFlush(events).stream().map(
                EventEntity::getId
            ).toList();
        } catch (DataIntegrityViolationException e) {
            throw new HttpClientErrorException(HttpStatusCode.valueOf(404));
        }
    }
}
