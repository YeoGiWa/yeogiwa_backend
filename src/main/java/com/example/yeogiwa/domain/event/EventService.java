package com.example.yeogiwa.domain.event;

import com.example.yeogiwa.domain.event.dto.response.EventDetailResponse;
import com.example.yeogiwa.domain.event.dto.response.EventsResponse;
import com.example.yeogiwa.domain.favorite.FavoriteEntity;
import com.example.yeogiwa.domain.favorite.FavoriteRepository;
import com.example.yeogiwa.enums.Region;
import com.example.yeogiwa.openapi.OpenApiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class EventService {
    private final OpenApiService openApiService;
    private final EventRepository eventRepository;
    private final FavoriteRepository favoriteRepository;

    public EventDetailResponse getEventDetailById(Long eventId) {
        return openApiService.getFestival(eventId);
    }

    public List<EventsResponse> getEvents(Integer numOfRows, Integer pageNo, String keyword, Region region, String eventStartDate, String eventEndDate) {
        return openApiService.getFestivals(numOfRows, pageNo, keyword, region, eventStartDate, eventEndDate);
    }

    public List<EventsResponse> getNearbyEvents(Integer numofRows, Integer pageNo, Double mapX, Double mapY, Double radius) {
        return openApiService.getNearbyFestivals(numofRows, pageNo, mapX, mapY, radius);
    }

    public List<EventsResponse> getFavoriteEvents(Long userId, Integer numofRows, Integer pageNo) {
        if (pageNo<0) throw new HttpClientErrorException(HttpStatusCode.valueOf(400));

        Pageable pageable = PageRequest.of(pageNo, numofRows, Sort.by("createdAt").descending());
        Page<FavoriteEntity> favorites = favoriteRepository.findAllByUser_Id(userId, pageable);
        log.info("{} {}", userId, favorites.getContent());
//        log.info("{}", favorites.getContent().size());
        return favorites.map((favorite) -> {
            EventDetailResponse festival = openApiService.getFestival(favorite.getEventId());
            log.info("{}", favorite);
            return EventsResponse.from(festival);
        }).toList();
    }

    public EventEntity changeApplicable(Long userId, Long eventId, Boolean applicable) {
        Optional<EventEntity> eventOpt = eventRepository.findById(eventId);
        if (eventOpt.isEmpty()) throw new HttpClientErrorException(HttpStatusCode.valueOf(404));
        EventEntity event = eventOpt.get();
        Long getUserId = event.getHost().getUser().getId();
        if (!Objects.equals(userId, getUserId)) throw new HttpClientErrorException(HttpStatusCode.valueOf(403));
        event.setIsApplicable(applicable);
        return eventRepository.save(event);
    }

}
