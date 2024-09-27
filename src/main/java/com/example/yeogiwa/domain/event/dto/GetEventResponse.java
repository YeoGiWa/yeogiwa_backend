package com.example.yeogiwa.domain.event.dto;

import com.example.yeogiwa.openapi.festival.dto.FestivalInfoDto;
import com.example.yeogiwa.openapi.festival.dto.FestivalImageDto;
import com.example.yeogiwa.openapi.festival.dto.FestivalDto;
import com.example.yeogiwa.openapi.festival.dto.FestivalIntroDto;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
public class GetEventResponse {
    private FestivalDto festivalDto;
    private FestivalIntroDto festivalIntroDto;
    private List<FestivalInfoDto> festivalInfoDtos;
    private List<FestivalImageDto> festivalImageDtos;
    private EventDto event;
    private Boolean isValid;

    public GetEventResponse(FestivalDto festivalDto, FestivalIntroDto festivalIntroDto, List<FestivalInfoDto> festivalInfoDtos, List<FestivalImageDto> festivalImageDtos, EventDto event, Boolean isValid) {
        this.festivalDto = festivalDto;
        this.festivalIntroDto = festivalIntroDto;
        this.festivalInfoDtos = festivalInfoDtos;
        this.festivalImageDtos = festivalImageDtos;
        this.event = event;
        this.isValid = isValid;
    }
}