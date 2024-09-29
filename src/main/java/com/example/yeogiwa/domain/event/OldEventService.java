package com.example.yeogiwa.domain.event;

import com.example.yeogiwa.domain.ambassador.AmbassadorEntity;
import com.example.yeogiwa.domain.ambassador.AmbassadorRepository;
import com.example.yeogiwa.domain.event.dto.*;
import com.example.yeogiwa.domain.host.HostEntity;
import com.example.yeogiwa.domain.host.HostRepository;
import com.example.yeogiwa.domain.user.UserEntity;
import com.example.yeogiwa.domain.user.UserRepository;
import com.example.yeogiwa.enums.EventSort;
import com.example.yeogiwa.enums.Region;
import com.example.yeogiwa.openapi.festival.dto.FestivalInfoDto;
import com.example.yeogiwa.openapi.festival.dto.FestivalImageDto;
import com.example.yeogiwa.openapi.festival.dto.FestivalCommonDto;
import com.example.yeogiwa.openapi.festival.dto.FestivalIntroDto;
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

//@Service
@RequiredArgsConstructor
public class OldEventService {
    private final OpenApiService openApiService;
    private final UserRepository userRepository;
    private final HostRepository hostRepository;
    private final AmbassadorRepository ambassadorRepository;
    private final OldEventRepository oldEventRepository;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

//    @Transactional(readOnly = true)
//    public GetEventResponse getEventById(String id) {
//        FestivalCommonDto festivalCommonDto = openApiService.getFestivalDetailCommon(id);
//        FestivalIntroDto festivalIntroDto = openApiService.getFestivalDetailIntro(id);
//        List<FestivalInfoDto> festivalInfoDtos = openApiService.getFestivalDetailInfo(id);
//        List<FestivalImageDto> festivalImageDtos = openApiService.getFestivalDetailImage(id);
//
//        Optional<OldEventEntity> event = oldEventRepository.findById(id);
//
//        LocalDate startAt = LocalDate.parse(festivalIntroDto.getEventstartdate(), formatter);
//        LocalDate endAt = LocalDate.parse(festivalIntroDto.getEventenddate(), formatter);
//        Boolean isValid = startAt.isBefore(LocalDate.now()) && endAt.isAfter(LocalDate.now());
//
//        return event.map(eventEntity -> {
//            List<SessionDto> sessionDtos = eventEntity.getSessions().stream()
//                    .map(SessionDto::from)
//                    .collect(Collectors.toList());
//
//            return new GetEventResponse(
//                festivalCommonDto,
//                    festivalIntroDto,
//                    festivalInfoDtos,
//                    festivalImageDtos,
//                    EventDto.from(eventEntity),
//                    isValid
//            );
//        }).orElseGet(() ->
//                new GetEventResponse(
//                    festivalCommonDto,
//                        festivalIntroDto,
//                        festivalInfoDtos,
//                        festivalImageDtos,
//                        null,
//                        isValid
//                )
//        );
//    }

//    @Transactional(readOnly = true)
//    public List<GetEventResponse> listEvents(int numOfRows, int pageNo, Region region, String eventStartDate, String eventEndDate, Boolean isValid) {
//        HashMap<Long, OldEventEntity> eventMap;
//        if (isValid) {
//            pageNo = pageNo - 1;
//            PageRequest pageable = PageRequest.of(pageNo, 10, Sort.by("startAt").descending());
//            if (eventStartDate == null || eventEndDate == null)
//                if(eventStartDate == null && eventEndDate == null)
//                    if(region != Region.ALL)
//                        eventMap = oldEventRepository.findAllByStartAtBetweenAndRegionOrderByStartAtDesc(pageable, LocalDate.of(1900, 1, 1), LocalDate.of(2099, 12, 31), region.code)
//                            .stream()
//                            .collect(HashMap::new, (map, event) -> map.put(event.getId(), event), HashMap::putAll);
//                    else
//                        eventMap = oldEventRepository.findAllByStartAtBetweenOrderByStartAtDesc(pageable, LocalDate.of(1900, 1, 1), LocalDate.of(2099, 12, 31))
//                            .stream()
//                            .collect(HashMap::new, (map, event) -> map.put(event.getId(), event), HashMap::putAll);
//                else {
//                    if(eventEndDate == null)
//                        eventEndDate = "20991231";
//
//                    assert eventStartDate != null;
//                    if(region != Region.ALL)
//                        eventMap = oldEventRepository.findAllByStartAtBetweenAndRegionOrderByStartAtDesc(pageable, LocalDate.parse(eventStartDate, formatter), LocalDate.of(2099, 12, 31), region.code)
//                            .stream()
//                            .filter(event -> event.getTotalFund() > 0)
//                            .collect(HashMap::new, (map, event) -> map.put(event.getId(), event), HashMap::putAll);
//                    else
//                        eventMap = oldEventRepository.findAllByStartAtBetweenOrderByStartAtDesc(pageable, LocalDate.parse(eventStartDate, formatter), LocalDate.parse(eventEndDate, formatter))
//                            .stream()
//                            .filter(event -> event.getTotalFund() > 0)
//                            .collect(HashMap::new, (map, event) -> map.put(event.getId(), event), HashMap::putAll);
//                }
//            else {
//                if(region != Region.ALL)
//                    eventMap = oldEventRepository.findAllByStartAtBetweenAndRegionOrderByStartAtDesc(pageable, LocalDate.parse(eventStartDate, formatter), LocalDate.parse(eventEndDate, formatter), region.code)
//                        .stream()
//                        .filter(event -> event.getTotalFund() > 0)
//                        .collect(HashMap::new, (map, event) -> map.put(event.getId(), event), HashMap::putAll);
//                else
//                    eventMap = oldEventRepository.findAllByStartAtBetweenOrderByStartAtDesc(pageable, LocalDate.parse(eventStartDate, formatter), LocalDate.parse(eventEndDate, formatter))
//                        .stream()
//                        .filter(event -> event.getTotalFund() > 0)
//                        .collect(HashMap::new, (map, event) -> map.put(event.getId(), event), HashMap::putAll);
//            }
//
//            return eventMap.values().stream()
//                .map(eventEntity -> {
//                    FestivalCommonDto festivalCommonDto = FestivalCommonDto.builder()
//                        .contentid(eventEntity.getId())
//                        .title(eventEntity.getName())
//                        .firstimage(eventEntity.getImageUrl())
//                        .addr1(eventEntity.getPlace())
//                        .build();
//
//                    return new GetEventResponse(festivalCommonDto, null, null, null, EventDto.from(eventEntity), true);
//                })
//                .toList();
//        } else {
//            List<FestivalCommonDto> festivals = openApiService.listFestivalDetails(numOfRows, pageNo, region, eventStartDate, eventEndDate);
//
//            if (festivals == null)
//                return null;
//
//            eventMap = oldEventRepository.findAllByIdIn(festivals.stream()
//                .map(FestivalCommonDto::getContentid)
//                .toList())
//                .stream()
//                .collect(HashMap::new, (map, event) -> map.put(event.getId(), event), HashMap::putAll);
//
//            return festivals.stream()
//                .map(festival -> {
//                    Optional<OldEventEntity> event = Optional.ofNullable(eventMap.get(festival.getContentid()));
//
//                    Boolean isValid1 = event.map(eventEntity -> eventEntity.getStartAt().isBefore(LocalDate.now()) && eventEntity.getEndAt().isAfter(LocalDate.now())).orElse(false);
//
//                    return event.map(eventEntity -> {
//                        List<SessionDto> sessionDtos = eventEntity.getSessions().stream()
//                            .map(SessionDto::from)
//                            .toList();
//
//                        return new GetEventResponse(festival, null, null, null, EventDto.from(eventEntity), isValid1);
//                    }).orElseGet(() ->
//                        new GetEventResponse(festival, null, null, null, null, false));
//                })
//                .toList();
//        }
//    }

//    @Transactional(readOnly = true)
//    public List<FestivalCommonDto> listFestivalsByKeyword(int numOfRows, int pageNo, String keyword) {
//        List<FestivalCommonDto> festivals = openApiService.listFestivalDetailsByKeyword(numOfRows, pageNo, EventSort.TITLE, keyword);
//
//        return festivals;
//    }

//    @Transactional(readOnly = true)
//    public List<GetEventResponse> listEventsNearby(int numOfRows, int pageNo, String mapX, String mapY, String radius) {
//        List<FestivalCommonDto> festivals = openApiService.listNearbyFestival(numOfRows, pageNo, mapX, mapY, radius);
//
//        if (festivals == null)
//            return null;
//
//        HashMap<Long, OldEventEntity> eventMap = oldEventRepository.findAllByIdIn(festivals.stream()
//            .map(FestivalCommonDto::getContentid)
//            .toList())
//            .stream()
//            .collect(HashMap::new, (map, event) -> map.put(event.getId(), event), HashMap::putAll);
//
//        return festivals.stream()
//            .map(festival -> {
//                Optional<OldEventEntity> event = Optional.ofNullable(eventMap.get(festival.getContentid()));
//
//                Boolean isValid1 = event.map(
//                        eventEntity -> (eventEntity.getStartAt().isBefore(LocalDate.now()) && eventEntity.getEndAt().isAfter(LocalDate.now())) || eventEntity.getTotalFund() > 0)
//                        .orElse(false);
//
//                return event.map(eventEntity -> {
//                    return new GetEventResponse(festival, null, null, null, EventDto.from(eventEntity), isValid1);
//                }).orElseGet(() ->
//                        new GetEventResponse(festival, null, null, null, null, false));
//            })
//            .toList();
//    }

//    @Transactional
//    public EventDto createEvent(String email, CreateEventRequest request) {
//        HostEntity host = hostRepository.findAllByUserAndId(userRepository.findByEmail(email), request.getHostId())
//            .orElseThrow(() -> new IllegalArgumentException("호스트로 등록되지 않은 유저입니다."));
//
//        oldEventRepository.findById(request.getId())
//            .ifPresent(event -> {
//                throw new IllegalArgumentException("이미 등록된 이벤트입니다.");
//            });
//
//        FestivalCommonDto festivalCommonDto = openApiService.getFestivalDetailCommon(request.getId());
//
//        FestivalIntroDto festivalIntroDto = openApiService.getFestivalDetailIntro(request.getId());
//
//        OldEventEntity event = OldEventEntity.builder()
//            .id(festivalCommonDto.getContentid())
//            .host(host)
//            .name(request.getName())
//            .place(festivalCommonDto.getAddr1())
//            .ratio(request.getRatio())
//            .region(festivalCommonDto.getAreacode().toString())
//            .totalFund(0)
//            .startAt(LocalDate.parse(festivalIntroDto.getEventstartdate(), formatter))
//            .endAt(LocalDate.parse(festivalIntroDto.getEventenddate(), formatter))
//            .imageUrl(festivalCommonDto.getFirstimage())
//            .build();
//
//        OldEventEntity savedEvent = oldEventRepository.save(event);
//
//        return EventDto.from(savedEvent);
//    }

//    @Transactional
//    public EventDto updateEvent(String id, UpdateEventRequest request) {
//        OldEventEntity event = oldEventRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Event not found"));
//
//        event.setName(request.getName());
//        event.setPlace(request.getPlace());
//        event.setRatio(request.getRatio());
//        event.setCreatedAt(LocalDateTime.now());
//
//        if (request.getFund() != null)
//            event.setTotalFund(event.getTotalFund() + request.getFund());
//
//        OldEventEntity updatedEvent = oldEventRepository.save(event);
//
//        return EventDto.from(updatedEvent);
//    }

//    @Transactional
//    public void deleteEvent(String id) {
//        OldEventEntity event = oldEventRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Event not found"));
//
//        oldEventRepository.delete(event);
//    }

//    @Transactional(readOnly = true)
//    public List<EventDto> listEventsByAmbassador(String email, Boolean isValid) {
//        UserEntity user = userRepository.findByEmail(email);
//        List<AmbassadorEntity> ambassadors = ambassadorRepository.findAllByUser(user);
//        List<Long> eventIds = ambassadors.stream().map(ambassador -> ambassador.getEvent().getId()).toList();
//
//        List<OldEventEntity> events;
//        if(isValid)
//            events = oldEventRepository.findAllByIdIn(eventIds)
//                    .stream()
//                    .filter(event -> event.getEndAt().isAfter(LocalDate.now()))
//                    .filter(event -> event.getTotalFund() > 0)
//                    .toList();
//        else
//            events = oldEventRepository.findAllByIdIn(eventIds)
//                    .stream()
//                    .filter(event -> event.getEndAt().isBefore(LocalDate.now()) || event.getTotalFund() <= 0)
//                    .toList();
//
//        return events.stream()
//                .map(EventDto::from)
//                .toList();
//    }

//    @Transactional(readOnly = true)
//    public List<EventDto> listEventsByHost(String email) {
//        UserEntity user = userRepository.findByEmail(email);
//        List<HostEntity> hosts = hostRepository.findAllByUser(user);
//
//        List<OldEventEntity> events = oldEventRepository.findAllByHostIn(hosts);
//
//        return events.stream()
//                .map(EventDto::from)
//                .toList();
//    }

}
