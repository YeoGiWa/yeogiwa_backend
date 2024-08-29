package com.example.yeogiwa.domain.event.dto;

import com.example.yeogiwa.domain.event.SessionEntity;
import com.example.yeogiwa.openapi.dto.FestivalInfoDto;
import com.example.yeogiwa.openapi.dto.FestivalImageDto;
import com.example.yeogiwa.openapi.dto.FestivalDto;
import com.example.yeogiwa.openapi.dto.FestivalIntroDto;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.Session;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
public class GetEventResponse {
    private FestivalDto festivalDto;
    private FestivalIntroDto festivalIntroDto;
    private List<FestivalInfoDto> festivalInfoDtos;
    private List<FestivalImageDto> festivalImageDtos;
    private Integer ratio;
    private LocalDate startAt;
    private LocalDate endAt;
    private Boolean isValid;
    private LocalDateTime createdAt;
    private List<SessionDto> sessionEntities;


    public GetEventResponse(FestivalDto festivalDto, FestivalIntroDto festivalIntroDto, List<FestivalInfoDto> festivalInfoDtos, List<FestivalImageDto> festivalImageDtos, Integer ratio, LocalDate startAt, LocalDate endAt, Boolean isValid, LocalDateTime createdAt, List<SessionDto> sessionEntities) {
        this.festivalDto = festivalDto;
        this.festivalIntroDto = festivalIntroDto;
        this.festivalInfoDtos = festivalInfoDtos;
        this.festivalImageDtos = festivalImageDtos;
        this.ratio = ratio;
        this.startAt = startAt;
        this.endAt = endAt;
        this.isValid = isValid;
        this.createdAt = createdAt;
        this.sessionEntities = sessionEntities;
    }
}