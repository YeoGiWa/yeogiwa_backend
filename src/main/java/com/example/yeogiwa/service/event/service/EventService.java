package com.example.yeogiwa.service.event.service;

import com.example.yeogiwa.enums.Region;
import com.example.yeogiwa.enums.Sort;
import com.example.yeogiwa.service.event.controller.request.CreateEventRequest;
import com.example.yeogiwa.service.event.controller.request.UpdateEventRequest;
import com.example.yeogiwa.service.event.controller.response.GetEventResponse;
import com.example.yeogiwa.service.event.controller.response.ListEventResponse;
import com.example.yeogiwa.service.event.dto.EventDto;
import com.example.yeogiwa.service.event.entity.EventEntity;
import com.example.yeogiwa.service.event.repository.EventRepository;
import com.example.yeogiwa.service.openapi.dto.defatilinfo.FestivalDetailInfoDto;
import com.example.yeogiwa.service.openapi.dto.detailImage.FestivalDetailImageDto;
import com.example.yeogiwa.service.openapi.dto.detailcommon.FestivalDetailDto;
import com.example.yeogiwa.service.openapi.dto.detailintro.FestivalDetailIntroDto;
import com.example.yeogiwa.service.openapi.service.OpenApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EventService {
    private final OpenApiService openApiService;
    private final EventRepository eventRepository;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

    public GetEventResponse getEventById(String id) {
        FestivalDetailDto festivalDetailDto = openApiService.getFestivalDetail(id);
        FestivalDetailIntroDto festivalDetailIntroDto = openApiService.getFestivalDetailIntro(id);
        List<FestivalDetailInfoDto> festivalDetailInfoDtos = openApiService.getFestivalDetailInfo(id);
        List<FestivalDetailImageDto> festivalDetailImageDtos = openApiService.getFestivalDetailImage(id);

        Optional<EventEntity> event = eventRepository.findById(id);

        LocalDate startAt = LocalDate.parse(festivalDetailIntroDto.getEventstartdate(), formatter);
        LocalDate endAt = LocalDate.parse(festivalDetailIntroDto.getEventenddate(), formatter);
        Boolean isValid = startAt.isBefore(LocalDate.now()) && endAt.isAfter(LocalDate.now());

        return event.map(eventEntity ->
            new GetEventResponse(festivalDetailDto, festivalDetailIntroDto, festivalDetailInfoDtos, festivalDetailImageDtos, eventEntity.getRatio(), festivalDetailIntroDto.getEventstartdate(), festivalDetailIntroDto.getEventenddate(), isValid, eventEntity.getCreatedAt()))
            .orElseGet(() ->
                new GetEventResponse(festivalDetailDto, festivalDetailIntroDto, festivalDetailInfoDtos, festivalDetailImageDtos, null, festivalDetailIntroDto.getEventstartdate(), festivalDetailIntroDto.getEventenddate(), isValid, null));
    }

    public ListEventResponse listEvents(int numOfRows, int pageNo, Sort sort, Region region, String keyword, String eventStartDate, String eventEndDate) {
        List<FestivalDetailDto> festivals = null;
        if (keyword != null) {
            festivals = openApiService.listFestivalDetailsByKeyword(numOfRows, pageNo, sort, region, keyword);
        } else {
            festivals = openApiService.listFestivalDetails(numOfRows, pageNo, sort, region, eventStartDate, eventEndDate);
//            TODO: 의미가 없다
//            if (keyword != null) {
//                festivals = festivals.stream()
//                    .filter(festival -> festival.getTitle().contains(keyword))
//                    .toList();
//            }
        }

        if (festivals == null)
            return null;

        HashMap<String, EventEntity> eventMap = eventRepository.findAllByIdIn(festivals.stream()
            .map(FestivalDetailDto::getContentid)
            .toList())
            .stream()
            .collect(HashMap::new, (map, event) -> map.put(event.getId(), event), HashMap::putAll);

        List<GetEventResponse> results = festivals.stream()
            .map(festival -> {
                // TODO: 시작 및 종료 일자가 상세 조회 API에만 들어있어서 한 번 더 조회가 필요함 -> 전체 조회에서 너무 느림
                FestivalDetailIntroDto festivalDetailIntroDto = openApiService.getFestivalDetailIntro(festival.getContentid());

                LocalDate startAt = LocalDate.parse(festivalDetailIntroDto.getEventstartdate(), formatter);
                LocalDate endAt = LocalDate.parse(festivalDetailIntroDto.getEventenddate(), formatter);
                Boolean isValid = startAt.isBefore(LocalDate.now()) && endAt.isAfter(LocalDate.now());

                Optional<EventEntity> event = Optional.ofNullable(eventMap.get(festival.getContentid()));

                return event.map(eventEntity ->
                    new GetEventResponse(festival, festivalDetailIntroDto, null, null, eventEntity.getRatio(), festivalDetailIntroDto.getEventstartdate(), festivalDetailIntroDto.getEventenddate(), isValid, eventEntity.getCreatedAt()))
                    .orElseGet(() ->
                        new GetEventResponse(festival, festivalDetailIntroDto, null, null, null, festivalDetailIntroDto.getEventstartdate(), festivalDetailIntroDto.getEventenddate(), isValid, null));
            })
            .toList();

        return new ListEventResponse(results);
    }

    public ListEventResponse listEventsNearby(int numOfRows, int pageNo, Sort sort, String mapX, String mapY, String radius) {
        List<FestivalDetailDto> festivals = openApiService.listNearbyFestival(numOfRows, pageNo, sort, mapX, mapY, radius);

        if (festivals == null)
            return null;

        HashMap<String, EventEntity> eventMap = eventRepository.findAllByIdIn(festivals.stream()
                .map(FestivalDetailDto::getContentid)
                .toList())
            .stream()
            .collect(HashMap::new, (map, event) -> map.put(event.getId(), event), HashMap::putAll);

        List<GetEventResponse> results = festivals.stream()
            .map(festival -> {
                // TODO: 시작 및 종료 일자가 상세 조회 API에만 들어있어서 한 번 더 조회가 필요함 -> 전체 조회에서 너무 느림
                FestivalDetailIntroDto festivalDetailIntroDto = openApiService.getFestivalDetailIntro(festival.getContentid());

                LocalDate startAt = LocalDate.parse(festivalDetailIntroDto.getEventstartdate(), formatter);
                LocalDate endAt = LocalDate.parse(festivalDetailIntroDto.getEventenddate(), formatter);
                Boolean isValid = startAt.isBefore(LocalDate.now()) && endAt.isAfter(LocalDate.now());

                Optional<EventEntity> event = Optional.ofNullable(eventMap.get(festival.getContentid()));

                return event.map(eventEntity ->
                        new GetEventResponse(festival, festivalDetailIntroDto, null, null, eventEntity.getRatio(), festivalDetailIntroDto.getEventstartdate(), festivalDetailIntroDto.getEventenddate(), isValid, eventEntity.getCreatedAt()))
                    .orElseGet(() ->
                        new GetEventResponse(festival, festivalDetailIntroDto, null, null, null, festivalDetailIntroDto.getEventstartdate(), festivalDetailIntroDto.getEventenddate(), isValid, null));
            })
            .toList();

        return new ListEventResponse(results);
    }

    @Transactional
    public EventDto createEvent(CreateEventRequest request) {
        FestivalDetailDto festivalDetailDto = openApiService.getFestivalDetail(request.getId());

        FestivalDetailIntroDto festivalDetailIntroDto = openApiService.getFestivalDetailIntro(request.getId());

        EventEntity event = EventEntity.builder()
            .id(festivalDetailDto.getContentid())
            .name(request.getName())
            .place(request.getPlace())
            .ratio(request.getRatio())
            .startAt(festivalDetailIntroDto.getEventstartdate())
            .endAt(festivalDetailIntroDto.getEventenddate())
            .address(festivalDetailDto.getAddr1())
            .imageUrl(festivalDetailDto.getFirstimage())
            .createdAt(request.getCreatedAt())
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
        event.setCreatedAt(request.getCreatedAt());

        EventEntity updatedEvent = eventRepository.save(event);

        return EventDto.from(updatedEvent);
    }

    // TODO: 삭제가 필요할까? 비즈니스 적으로 삭제는 없어야 하지 않을까?
}
