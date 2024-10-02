package com.example.yeogiwa.domain.event;

import com.example.yeogiwa.domain.event.dto.response.EventDetailResponse;
import com.example.yeogiwa.domain.event.dto.response.EventsResponse;
import com.example.yeogiwa.domain.user.UserRepository;
import com.example.yeogiwa.enums.Region;
import com.example.yeogiwa.openapi.OpenApiService;
import lombok.RequiredArgsConstructor;
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
public class EventService {
    private final OpenApiService openApiService;
    private final EventRepository eventRepository;

    public EventDetailResponse getEventDetailById(Long eventId) {
        return openApiService.getFestival(eventId);
    }

    public List<EventsResponse> getEvents(Integer numOfRows, Integer pageNo, String keyword, Region region, String eventStartDate, String eventEndDate) {
        return openApiService.getFestivals(numOfRows, pageNo, keyword, region, eventStartDate, eventEndDate);
    }

    public List<EventsResponse> getNearbyEvents(Integer numofRows, Integer pageNo, Double mapX, Double mapY, Double radius) {
        return openApiService.getNearbyFestivals(numofRows, pageNo, mapX, mapY, radius);
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
