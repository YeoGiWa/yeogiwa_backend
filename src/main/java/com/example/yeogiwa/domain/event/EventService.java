package com.example.yeogiwa.domain.event;

import com.example.yeogiwa.domain.event.dto.response.EventDetailResponse;
import com.example.yeogiwa.domain.event.dto.response.EventsResponse;
import com.example.yeogiwa.enums.Region;
import com.example.yeogiwa.openapi.OpenApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class EventService {
    private final EventRepository eventRepository;
    private final OpenApiService openApiService;

    public EventDetailResponse getEventDetailById(Long eventId) {
        return openApiService.getFestival(eventId);
    }

    public List<EventsResponse> getEvents(Integer numOfRows, Integer pageNo, String keyword, Region region, String eventStartDate, String eventEndDate) {
        return openApiService.getFestivals(numOfRows, pageNo, keyword, region, eventStartDate, eventEndDate);
    }

    public List<EventsResponse> getNearbyEvents(Integer numofRows, Integer pageNo, Double mapX, Double mapY, Double radius) {
        return openApiService.getNearbyFestivals(numofRows, pageNo, mapX, mapY, radius);
    }
}
