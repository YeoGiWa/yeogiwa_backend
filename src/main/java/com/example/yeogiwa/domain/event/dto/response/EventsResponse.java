package com.example.yeogiwa.domain.event.dto.response;

import com.example.yeogiwa.openapi.festival.dto.FestivalCommonDto;
import com.example.yeogiwa.openapi.festival.dto.FestivalDto;
import com.example.yeogiwa.openapi.festival.dto.FestivalIntroDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventsResponse {
    private Long eventId;
    private String thumbnailImage;
    private String title;
    private String address;
    private String eventStartDate;
    private String eventEndDate;
    private Double mapX;
    private Double mapY;
    private Double dist;
    private Boolean isApplicable;

    public static EventsResponse from(FestivalDto festival, Map<Long, Boolean> isApplicable) {
        return EventsResponse.builder()
            .eventId(festival.getContentid())
            .thumbnailImage(festival.getFirstimage())
            .title(festival.getTitle())
            .address(festival.getAddr1())
            .eventStartDate(festival.getEventstartdate())
            .eventEndDate(festival.getEventenddate())
            .mapX(festival.getMapx())
            .mapY(festival.getMapy())
            .dist(festival.getDist())
            .isApplicable(isApplicable.get(festival.getContentid()))
            .build();
    }

    public static EventsResponse from(FestivalCommonDto festival, Map<Long, Boolean> isApplicable, FestivalIntroDto detailResult) {
        return EventsResponse.builder()
            .eventId(festival.getContentid())
            .thumbnailImage(festival.getFirstimage())
            .title(festival.getTitle())
            .address(festival.getAddr1())
            .eventStartDate(detailResult.getEventstartdate())
            .eventEndDate(detailResult.getEventenddate())
            .mapX(festival.getMapx())
            .mapY(festival.getMapy())
            .isApplicable(isApplicable.get(festival.getContentid()))
            .build();
    }
}
