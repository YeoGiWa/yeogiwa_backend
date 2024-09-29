package com.example.yeogiwa.domain.event.dto.response;

import com.example.yeogiwa.domain.event.EventEntity;
import com.example.yeogiwa.openapi.festival.dto.FestivalCommonDto;
import com.example.yeogiwa.openapi.festival.dto.FestivalIntroDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventDetailResponse {
    private Long eventId;
    private String title;
    private String firstImageOriginal;
    private String firstImageThumbnail;
    private String address;
    private String eventStartDate;
    private String eventEndDate;
    private String playtime;
    private String tel;
    private String telName;
    private String overview;
    private Boolean isApplicable;

    public static EventDetailResponse from(Long eventId, FestivalCommonDto festivalCommonDto, FestivalIntroDto festivalIntroDto, Optional<EventEntity> event) {
        return EventDetailResponse.builder()
            .eventId(eventId)
            .title(festivalCommonDto.getTitle())
            .firstImageOriginal(festivalCommonDto.getFirstimage())
            .firstImageThumbnail(festivalCommonDto.getFirstimage2())
            .address(festivalCommonDto.getAddr1())
            .eventStartDate(festivalIntroDto.getEventstartdate())
            .eventEndDate(festivalIntroDto.getEventenddate())
            .playtime(festivalIntroDto.getPlaytime())
            .tel(festivalCommonDto.getTel())
            .telName(festivalCommonDto.getTelname())
            .overview(festivalCommonDto.getOverview())
            .isApplicable(event.isPresent())
            .build();
    }
}
