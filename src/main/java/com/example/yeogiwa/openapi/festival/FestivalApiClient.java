package com.example.yeogiwa.openapi.festival;

import com.example.yeogiwa.openapi.festival.dto.*;
import com.example.yeogiwa.openapi.festival.dto.response.FestivalResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "festivalApiClient", url = "https://apis.data.go.kr/B551011/KorService1")
public interface FestivalApiClient {

    @GetMapping("/locationBasedList1")
    FestivalResponse<FestivalDto> locationBasedList(
        @RequestParam("numOfRows") int numOfRows,
        @RequestParam("pageNo") int pageNo,
        @RequestParam("MobileOS") String mobileOS,
        @RequestParam("MobileApp") String mobileApp,
        @RequestParam("_type") String responseType,
        @RequestParam("listYN") String listYN,
        @RequestParam("arrange") String arrange,
        @RequestParam("mapX") String mapX,
        @RequestParam("mapY") String mapY,
        @RequestParam("radius") String radius,
        @RequestParam("contentTypeId") String contentTypeId,
        @RequestParam("serviceKey") String serviceKey
    );

    @GetMapping("/searchKeyword1")
    FestivalResponse<FestivalCommonDto> searchKeyword(
        @RequestParam("numOfRows") int numOfRows,
        @RequestParam("pageNo") int pageNo,
        @RequestParam("MobileOS") String mobileOS,
        @RequestParam("MobileApp") String mobileApp,
        @RequestParam("_type") String responseType,
        @RequestParam("listYN") String listYN,
        @RequestParam("arrange") String arrange,
        @RequestParam("keyword") String keyword,
        @RequestParam("contentTypeId") String contentTypeId,
        @RequestParam("serviceKey") String serviceKey
    );

    @GetMapping("/searchFestival1")
    FestivalResponse<FestivalDto> searchFestival(
        @RequestParam("numOfRows") int numOfRows,
        @RequestParam("pageNo") int pageNo,
        @RequestParam("MobileOS") String mobileOS,
        @RequestParam("MobileApp") String mobileApp,
        @RequestParam("_type") String responseType,
        @RequestParam("listYN") String listYN,
        @RequestParam("arrange") String arrange,
        @RequestParam("areaCode") String areaCode,
        @RequestParam("eventStartDate") String eventStartDate,
        @RequestParam("eventEndDate") String eventEndDate,
        @RequestParam("serviceKey") String serviceKey
    );

    @GetMapping("/detailCommon1")
    FestivalResponse<FestivalCommonDto> detailCommon(
        @RequestParam("MobileOS") String mobileOS,
        @RequestParam("MobileApp") String mobileApp,
        @RequestParam("_type") String responseType,
        @RequestParam("contentId") String contentId,
        @RequestParam("defaultYN") String defaultYN,
        @RequestParam("firstImageYN") String firstImageYN,
        @RequestParam("areacodeYN") String areacodeYN,
        @RequestParam("catcodeYN") String catcodeYN,
        @RequestParam("addrinfoYN") String addrinfoYN,
        @RequestParam("mapinfoYN") String mapinfoYN,
        @RequestParam("overviewYN") String overviewYN,
        @RequestParam("contentTypeId") String contentTypeId,
        @RequestParam("serviceKey") String serviceKey
    );

    @GetMapping("/detailIntro1")
    FestivalResponse<FestivalIntroDto> detailIntro(
        @RequestParam("MobileOS") String mobileOS,
        @RequestParam("MobileApp") String mobileApp,
        @RequestParam("_type") String responseType,
        @RequestParam("contentId") String contentId,
        @RequestParam("contentTypeId") String contentTypeId,
        @RequestParam("serviceKey") String serviceKey
    );

    @GetMapping("/detailInfo1")
    FestivalResponse<FestivalInfoDto> detailInfo(
        @RequestParam("MobileOS") String mobileOS,
        @RequestParam("MobileApp") String mobileApp,
        @RequestParam("_type") String responseType,
        @RequestParam("contentId") String contentId,
        @RequestParam("contentTypeId") String contentTypeId,
        @RequestParam("serviceKey") String serviceKey
    );

    @GetMapping("/detailImage1")
    FestivalResponse<FestivalImageDto> detailImage(
        @RequestParam("MobileOS") String mobileOS,
        @RequestParam("MobileApp") String mobileApp,
        @RequestParam("_type") String responseType,
        @RequestParam("imageYN") String imageYN,
        @RequestParam("subImageYN") String subImageYN,
        @RequestParam("contentId") String contentId,
        @RequestParam("serviceKey") String serviceKey
    );

    @GetMapping("/areaBasedList1")
    FestivalResponse<FestivalCommonDto> areaBasedList(
        @RequestParam String MobileOS,
        @RequestParam String MobileApp,
        @RequestParam String _type,
        @RequestParam String serviceKey,
        @RequestParam int numOfRows,
        @RequestParam int pageNo,
        @RequestParam String listYN,
        @RequestParam String arrange,
        @RequestParam String contentTypeId,
        @RequestParam String areaCode
    );
}