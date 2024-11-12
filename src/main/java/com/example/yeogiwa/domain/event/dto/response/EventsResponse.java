package com.example.yeogiwa.domain.event.dto.response;

import com.example.yeogiwa.domain.event.dto.EventEtc;
import com.example.yeogiwa.openapi.festival.dto.FestivalCommonDto;
import com.example.yeogiwa.openapi.festival.dto.FestivalDto;
import com.example.yeogiwa.openapi.festival.dto.FestivalIntroDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private Integer round;
    private Double mapX;
    private Double mapY;
    private Double dist;
    private Boolean isApplicable;

    public static EventsResponse from(FestivalDto festival, Map<Long, EventEtc> eventEtc) {
        return EventsResponse.builder()
            .eventId(festival.getContentid())
            .thumbnailImage(festival.getFirstimage())
            .title(festival.getTitle())
            .address(festival.getAddr1())
            .eventStartDate(festival.getEventstartdate())
            .eventEndDate(festival.getEventenddate())
            .round(eventEtc.getOrDefault(festival.getContentid(), EventEtc.builder().build()).getRound())
            .mapX(festival.getMapx())
            .mapY(festival.getMapy())
            .dist(festival.getDist())
            .isApplicable(eventEtc.get(festival.getContentid()) != null ? eventEtc.get(festival.getContentid()).getIsApplicable() : false)
            .build();
    }

    public static EventsResponse from(FestivalCommonDto festival, FestivalIntroDto detailResult, Map<Long, EventEtc> eventEtc) {
        return EventsResponse.builder()
            .eventId(festival.getContentid())
            .thumbnailImage(festival.getFirstimage())
            .title(festival.getTitle())
            .address(festival.getAddr1())
            .eventStartDate(detailResult.getEventstartdate())
            .eventEndDate(detailResult.getEventenddate())
            .round(eventEtc.getOrDefault(festival.getContentid(), EventEtc.builder().build()).getRound())
            .mapX(festival.getMapx())
            .mapY(festival.getMapy())
            .isApplicable(eventEtc.get(festival.getContentid())!=null ? eventEtc.get(festival.getContentid()).getIsApplicable() : false)
            .build();
    }

    public static EventsResponse from(EventDetailResponse eventDetail) {
        return EventsResponse.builder()
            .eventId(eventDetail.getEventId())
            .thumbnailImage(eventDetail.getFirstImageThumbnail())
            .title(eventDetail.getTitle())
            .address(eventDetail.getAddress())
            .eventStartDate(eventDetail.getEventStartDate())
            .eventEndDate(eventDetail.getEventEndDate())
            .round(eventDetail.getRound())
            .isApplicable(eventDetail.getIsApplicable())
            .build();
    }
}
