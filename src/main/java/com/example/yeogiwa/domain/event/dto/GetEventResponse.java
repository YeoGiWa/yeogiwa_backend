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