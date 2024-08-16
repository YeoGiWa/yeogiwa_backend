package com.example.yeogiwa.service.event.controller.response;

import com.example.yeogiwa.service.openapi.dto.defatilinfo.FestivalDetailInfoDto;
import com.example.yeogiwa.service.openapi.dto.detailImage.FestivalDetailImageDto;
import com.example.yeogiwa.service.openapi.dto.detailcommon.FestivalDetailDto;
import com.example.yeogiwa.service.openapi.dto.detailintro.FestivalDetailIntroDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
public class GetEventResponse {
    private FestivalDetailDto festivalDetailDto;
    private FestivalDetailIntroDto festivalDetailIntroDto;
    private List<FestivalDetailInfoDto> festivalDetailInfoDtos;
    private List<FestivalDetailImageDto> festivalDetailImageDtos;
    private Integer ratio;
    private LocalDate startAt;
    private LocalDate endAt;
    private Boolean isValid;
    private Date createdAt;


    public GetEventResponse(FestivalDetailDto festivalDetailDto, FestivalDetailIntroDto festivalDetailIntroDto, List<FestivalDetailInfoDto> festivalDetailInfoDtos, List<FestivalDetailImageDto> festivalDetailImageDtos, Integer ratio, LocalDate startAt, LocalDate endAt, Boolean isValid, Date createdAt) {
        this.festivalDetailDto = festivalDetailDto;
        this.festivalDetailIntroDto = festivalDetailIntroDto;
        this.festivalDetailInfoDtos = festivalDetailInfoDtos;
        this.festivalDetailImageDtos = festivalDetailImageDtos;
        this.ratio = ratio;
        this.startAt = startAt;
        this.endAt = endAt;
        this.isValid = isValid;
        this.createdAt = createdAt;
    }
}