package com.example.yeogiwa.openapi;

import com.example.yeogiwa.openapi.dto.response.FestivalResponse;
import com.example.yeogiwa.openapi.dto.FestivalInfoDto;
import com.example.yeogiwa.openapi.dto.FestivalImageDto;
import com.example.yeogiwa.openapi.dto.FestivalDto;
import com.example.yeogiwa.openapi.dto.FestivalIntroDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "openApiClient", url = "https://apis.data.go.kr/B551011/KorService1")
public interface OpenApiClient {
    @GetMapping("/searchFestival1")
    FestivalResponse<FestivalDto> listFestival(
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

    @GetMapping("/searchKeyword1")
    FestivalResponse<FestivalDto> listFestivalByKeyword(
        @RequestParam("numOfRows") int numOfRows,
        @RequestParam("pageNo") int pageNo,
        @RequestParam("MobileOS") String mobileOS,
        @RequestParam("MobileApp") String mobileApp,
        @RequestParam("_type") String responseType,
        @RequestParam("listYN") String listYN,
        @RequestParam("arrange") String arrange,
        @RequestParam("areaCode") String areaCode,
        @RequestParam("keyword") String keyword,
        @RequestParam("contentTypeId") String contentTypeId,
        @RequestParam("serviceKey") String serviceKey
    );

    @GetMapping("/locationBasedList1")
    FestivalResponse<FestivalDto> listNearbyFestival(
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

    @GetMapping("/detailCommon1")
    FestivalResponse<FestivalDto> getFestivalDetail(
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
    FestivalResponse<FestivalIntroDto> getFestivalDetailIntro(
        @RequestParam("MobileOS") String mobileOS,
        @RequestParam("MobileApp") String mobileApp,
        @RequestParam("_type") String responseType,
        @RequestParam("contentId") String contentId,
        @RequestParam("contentTypeId") String contentTypeId,
        @RequestParam("serviceKey") String serviceKey
    );

    @GetMapping("/detailInfo1") FestivalResponse<FestivalInfoDto> getFestivalDetailInfo(
        @RequestParam("MobileOS") String mobileOS,
        @RequestParam("MobileApp") String mobileApp,
        @RequestParam("_type") String responseType,
        @RequestParam("contentId") String contentId,
        @RequestParam("contentTypeId") String contentTypeId,
        @RequestParam("serviceKey") String serviceKey
    );

    @GetMapping("/detailImage1")
    FestivalResponse<FestivalImageDto> getFestivalDetailImage(
        @RequestParam("MobileOS") String mobileOS,
        @RequestParam("MobileApp") String mobileApp,
        @RequestParam("_type") String responseType,
        @RequestParam("imageYN") String imageYN,
        @RequestParam("subImageYN") String subImageYN,
        @RequestParam("contentId") String contentId,
        @RequestParam("serviceKey") String serviceKey
    );
}