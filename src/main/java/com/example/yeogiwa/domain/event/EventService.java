package com.example.yeogiwa.domain.event;

import com.example.yeogiwa.domain.event.dto.*;
import com.example.yeogiwa.enums.Region;
import com.example.yeogiwa.openapi.dto.FestivalInfoDto;
import com.example.yeogiwa.openapi.dto.FestivalImageDto;
import com.example.yeogiwa.openapi.dto.FestivalDto;
import com.example.yeogiwa.openapi.dto.FestivalIntroDto;
import com.example.yeogiwa.openapi.OpenApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventService {
    private final OpenApiService openApiService;
    private final EventRepository eventRepository;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

    @Transactional(readOnly = true)
    public GetEventResponse getEventById(String id) {
        FestivalDto festivalDto = openApiService.getFestivalDetail(id);
        FestivalIntroDto festivalIntroDto = openApiService.getFestivalDetailIntro(id);
        List<FestivalInfoDto> festivalInfoDtos = openApiService.getFestivalDetailInfo(id);
        List<FestivalImageDto> festivalImageDtos = openApiService.getFestivalDetailImage(id);

        Optional<EventEntity> event = eventRepository.findById(id);

        LocalDate startAt = LocalDate.parse(festivalIntroDto.getEventstartdate(), formatter);
        LocalDate endAt = LocalDate.parse(festivalIntroDto.getEventenddate(), formatter);
        Boolean isValid = startAt.isBefore(LocalDate.now()) && endAt.isAfter(LocalDate.now());

        return event.map(eventEntity -> {
            List<SessionDto> sessionDtos = eventEntity.getSessions().stream()
                    .map(SessionDto::of)
                    .collect(Collectors.toList());

            return new GetEventResponse(
                    festivalDto,
                    festivalIntroDto,
                    festivalInfoDtos,
                    festivalImageDtos,
                    eventEntity.getRatio(),
                    eventEntity.getStartAt(),
                    eventEntity.getEndAt(),
                    isValid,
                    eventEntity.getCreatedAt(),
                    sessionDtos  // 변환된 List<SessionDto>를 전달
            );
        }).orElseGet(() ->
                new GetEventResponse(
                        festivalDto,
                        festivalIntroDto,
                        festivalInfoDtos,
                        festivalImageDtos,
                        null,
                        LocalDate.parse(festivalIntroDto.getEventstartdate(), formatter),
                        LocalDate.parse(festivalIntroDto.getEventenddate(), formatter),
                        isValid,
                        null,
                        null
                )
        );
    }

    @Transactional(readOnly = true)
    public List<GetEventResponse> listEvents(int numOfRows, int pageNo, Region region, String eventStartDate, String eventEndDate, Boolean isValid) {
        HashMap<Long, EventEntity> eventMap;
        if (isValid) {
            pageNo = pageNo - 1;
            PageRequest pageable = PageRequest.of(pageNo, 10, Sort.by("startAt").descending());
            if (eventStartDate == null || eventEndDate == null)
                if(eventStartDate == null && eventEndDate == null)
                    if(region != Region.ALL)
                        eventMap = eventRepository.findAllByStartAtBetweenAndRegionOrderByStartAtDesc(pageable, LocalDate.of(1900, 1, 1), LocalDate.of(2099, 12, 31), region.code)
                            .stream()
                            .collect(HashMap::new, (map, event) -> map.put(event.getId(), event), HashMap::putAll);
                    else
                        eventMap = eventRepository.findAllByStartAtBetweenOrderByStartAtDesc(pageable, LocalDate.of(1900, 1, 1), LocalDate.of(2099, 12, 31))
                            .stream()
                            .collect(HashMap::new, (map, event) -> map.put(event.getId(), event), HashMap::putAll);
                else {
                    if(eventEndDate == null)
                        eventEndDate = "20991231";

                    assert eventStartDate != null;
                    if(region != Region.ALL)
                        eventMap = eventRepository.findAllByStartAtBetweenAndRegionOrderByStartAtDesc(pageable, LocalDate.parse(eventStartDate, formatter), LocalDate.of(2099, 12, 31), region.code)
                            .stream()
                            .collect(HashMap::new, (map, event) -> map.put(event.getId(), event), HashMap::putAll);
                    else
                        eventMap = eventRepository.findAllByStartAtBetweenOrderByStartAtDesc(pageable, LocalDate.parse(eventStartDate, formatter), LocalDate.parse(eventEndDate, formatter))
                            .stream()
                            .collect(HashMap::new, (map, event) -> map.put(event.getId(), event), HashMap::putAll);
                }
            else {
                if(region != Region.ALL)
                    eventMap = eventRepository.findAllByStartAtBetweenAndRegionOrderByStartAtDesc(pageable, LocalDate.parse(eventStartDate, formatter), LocalDate.parse(eventEndDate, formatter), region.code)
                        .stream()
                        .collect(HashMap::new, (map, event) -> map.put(event.getId(), event), HashMap::putAll);
                else
                    eventMap = eventRepository.findAllByStartAtBetweenOrderByStartAtDesc(pageable, LocalDate.parse(eventStartDate, formatter), LocalDate.parse(eventEndDate, formatter))
                        .stream()
                        .collect(HashMap::new, (map, event) -> map.put(event.getId(), event), HashMap::putAll);
            }

            return eventMap.values().stream()
                .map(eventEntity -> {
                    FestivalDto festivalDto = FestivalDto.builder()
                        .contentid(eventEntity.getId())
                        .title(eventEntity.getName())
                        .firstimage(eventEntity.getImageUrl())
                        .addr1(eventEntity.getPlace())
                        .build();

                    List<SessionDto> sessionDtos = eventEntity.getSessions().stream()
                        .map(SessionDto::of)  // SessionDto.of(entity) 사용하여 변환
                        .toList();

                    return new GetEventResponse(festivalDto, null, null, null, eventEntity.getRatio(), eventEntity.getStartAt(), eventEntity.getEndAt(), true, eventEntity.getCreatedAt(), sessionDtos);
                })
                .toList();
        } else {
            List<FestivalDto> festivals = openApiService.listFestivalDetails(numOfRows, pageNo, region, eventStartDate, eventEndDate);

            if (festivals == null)
                return null;

            eventMap = eventRepository.findAllByIdIn(festivals.stream()
                .map(FestivalDto::getContentid)
                .toList())
                .stream()
                .collect(HashMap::new, (map, event) -> map.put(event.getId(), event), HashMap::putAll);

            return festivals.stream()
                .map(festival -> {
                    Optional<EventEntity> event = Optional.ofNullable(eventMap.get(festival.getContentid()));

                    Boolean isValid1 = event.map(eventEntity -> eventEntity.getStartAt().isBefore(LocalDate.now()) && eventEntity.getEndAt().isAfter(LocalDate.now())).orElse(false);

                    return event.map(eventEntity -> {
                        List<SessionDto> sessionDtos = eventEntity.getSessions().stream()
                            .map(SessionDto::of)
                            .toList();

                        return new GetEventResponse(festival, null, null, null, eventEntity.getRatio(), eventEntity.getStartAt(), eventEntity.getEndAt(), isValid1, eventEntity.getCreatedAt(), sessionDtos);
                    }).orElseGet(() ->
                            new GetEventResponse(festival, null, null, null, null, null, null, false, null, null));
                })
                .toList();
        }
    }

    @Transactional(readOnly = true)
    public List<GetEventResponse> listEventsNearby(int numOfRows, int pageNo, String mapX, String mapY, String radius) {
        List<FestivalDto> festivals = openApiService.listNearbyFestival(numOfRows, pageNo, mapX, mapY, radius);

        if (festivals == null)
            return null;

        HashMap<Long, EventEntity> eventMap = eventRepository.findAllByIdIn(festivals.stream()
            .map(FestivalDto::getContentid)
            .toList())
            .stream()
            .collect(HashMap::new, (map, event) -> map.put(event.getId(), event), HashMap::putAll);

        return festivals.stream()
            .map(festival -> {
                Optional<EventEntity> event = Optional.ofNullable(eventMap.get(festival.getContentid()));

                Boolean isValid1 = event.map(eventEntity -> eventEntity.getStartAt().isBefore(LocalDate.now()) && eventEntity.getEndAt().isAfter(LocalDate.now())).orElse(false);

                return event.map(eventEntity -> {
                    List<SessionDto> sessionDtos = eventEntity.getSessions().stream()
                        .map(SessionDto::of)
                        .toList();

                    return new GetEventResponse(festival, null, null, null, eventEntity.getRatio(), eventEntity.getStartAt(), eventEntity.getEndAt(), isValid1, eventEntity.getCreatedAt(), sessionDtos);
                }).orElseGet(() ->
                        new GetEventResponse(festival, null, null, null, null, null, null, false, null, null));
            })
            .toList();
    }

    @Transactional
    public EventDto createEvent(CreateEventRequest request) {
        FestivalDto festivalDto = openApiService.getFestivalDetail(request.getId());

        FestivalIntroDto festivalIntroDto = openApiService.getFestivalDetailIntro(request.getId());

        EventEntity event = EventEntity.builder()
            .id(festivalDto.getContentid())
            .name(request.getName())
            .place(festivalDto.getAddr1())
            .ratio(request.getRatio())
            .region(festivalDto.getAreacode().toString())
            .startAt(LocalDate.parse(festivalIntroDto.getEventstartdate(), formatter))
            .endAt(LocalDate.parse(festivalIntroDto.getEventenddate(), formatter))
            .imageUrl(festivalDto.getFirstimage())
            .build();

        EventEntity savedEvent = eventRepository.save(event);

        return EventDto.from(savedEvent);
    }

    @Transactional
    public EventDto updateEvent(String id, UpdateEventRequest request) {
        EventEntity event = eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        event.setName(request.getName());
        event.setPlace(request.getPlace());
        event.setRatio(request.getRatio());
        event.setCreatedAt(LocalDateTime.now());

        EventEntity updatedEvent = eventRepository.save(event);

        return EventDto.from(updatedEvent);
    }

    @Transactional
    public void deleteEvent(String id) {
        EventEntity event = eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        eventRepository.delete(event);
    }
}
