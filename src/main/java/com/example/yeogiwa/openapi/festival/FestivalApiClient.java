package com.example.yeogiwa.openapi.festival;

import com.example.yeogiwa.openapi.festival.dto.*;
import com.example.yeogiwa.openapi.festival.dto.response.FestivalResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "festivalApiClient", url = "https://apis.data.go.kr/B551011/KorService1")
public interface FestivalApiClient {

    @GetMapping("/locationBasedList1")
    FestivalResponse<FestivalDto> locationBasedList(
        @RequestParam int numOfRows,
        @RequestParam int pageNo,
        @RequestParam("MobileOS") String mobileOS,
        @RequestParam("MobileApp") String mobileApp,
        @RequestParam("_type") String responseType,
        @RequestParam String listYN,
        @RequestParam String arrange,
        @RequestParam String mapX,
        @RequestParam String mapY,
        @RequestParam String radius,
        @RequestParam String contentTypeId,
        @RequestParam String serviceKey
    );

    @GetMapping("/searchKeyword1")
    FestivalResponse<FestivalCommonDto> searchKeyword(
        @RequestParam int numOfRows,
        @RequestParam int pageNo,
        @RequestParam("MobileOS") String mobileOS,
        @RequestParam("MobileApp") String mobileApp,
        @RequestParam("_type") String responseType,
        @RequestParam String listYN,
        @RequestParam String arrange,
        @RequestParam String keyword,
        @RequestParam String contentTypeId,
        @RequestParam String serviceKey
    );

    @GetMapping("/searchFestival1")
    FestivalResponse<FestivalDto> searchFestival(
        @RequestParam int numOfRows,
        @RequestParam int pageNo,
        @RequestParam("MobileOS") String mobileOS,
        @RequestParam("MobileApp") String mobileApp,
        @RequestParam("_type") String responseType,
        @RequestParam String listYN,
        @RequestParam String arrange,
        @RequestParam String areaCode,
        @RequestParam String eventStartDate,
        @RequestParam String eventEndDate,
        @RequestParam String serviceKey
    );

    @GetMapping("/detailCommon1")
    FestivalResponse<FestivalCommonDto> detailCommon(
        @RequestParam("MobileOS") String mobileOS,
        @RequestParam("MobileApp") String mobileApp,
        @RequestParam("_type") String responseType,
        @RequestParam String contentId,
        @RequestParam String defaultYN,
        @RequestParam String firstImageYN,
        @RequestParam String areacodeYN,
        @RequestParam String catcodeYN,
        @RequestParam String addrinfoYN,
        @RequestParam String mapinfoYN,
        @RequestParam String overviewYN,
        @RequestParam String contentTypeId,
        @RequestParam String serviceKey
    );

    @GetMapping("/detailIntro1")
    FestivalResponse<FestivalIntroDto> detailIntro(
        @RequestParam("MobileOS") String mobileOS,
        @RequestParam("MobileApp") String mobileApp,
        @RequestParam("_type") String responseType,
        @RequestParam String contentId,
        @RequestParam String contentTypeId,
        @RequestParam String serviceKey
    );
}