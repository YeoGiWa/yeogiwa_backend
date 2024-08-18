package com.example.yeogiwa.domain.event.dto;

import com.example.yeogiwa.openapi.dto.FestivalInfoDto;
import com.example.yeogiwa.openapi.dto.FestivalImageDto;
import com.example.yeogiwa.openapi.dto.FestivalDto;
import com.example.yeogiwa.openapi.dto.FestivalIntroDto;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
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
    private Date createdAt;


    public GetEventResponse(FestivalDto festivalDto, FestivalIntroDto festivalIntroDto, List<FestivalInfoDto> festivalInfoDtos, List<FestivalImageDto> festivalImageDtos, Integer ratio, LocalDate startAt, LocalDate endAt, Boolean isValid, Date createdAt) {
        this.festivalDto = festivalDto;
        this.festivalIntroDto = festivalIntroDto;
        this.festivalInfoDtos = festivalInfoDtos;
        this.festivalImageDtos = festivalImageDtos;
        this.ratio = ratio;
        this.startAt = startAt;
        this.endAt = endAt;
        this.isValid = isValid;
        this.createdAt = createdAt;
    }
}