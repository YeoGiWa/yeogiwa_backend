package com.example.yeogiwa.service.openapi.client;

import com.example.yeogiwa.service.openapi.dto.defatilinfo.GetFestivalDetailInfoResponse;
import com.example.yeogiwa.service.openapi.dto.detailImage.GetFestivalDetailImageResponse;
import com.example.yeogiwa.service.openapi.dto.detailcommon.GetFestivalDetailResponse;
import com.example.yeogiwa.service.openapi.dto.detailintro.GetFestivalDetailIntroResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "openApiClient", url = "https://apis.data.go.kr/B551011/KorService1")
public interface OpenApiClient {
    @GetMapping("/searchFestival1")
    GetFestivalDetailResponse listFestival(
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
    GetFestivalDetailResponse listFestivalByKeyword(
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
    GetFestivalDetailResponse listNearbyFestival(
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
    GetFestivalDetailResponse getFestivalDetail(
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
    GetFestivalDetailIntroResponse getFestivalDetailIntro(
        @RequestParam("MobileOS") String mobileOS,
        @RequestParam("MobileApp") String mobileApp,
        @RequestParam("_type") String responseType,
        @RequestParam("contentId") String contentId,
        @RequestParam("contentTypeId") String contentTypeId,
        @RequestParam("serviceKey") String serviceKey
    );

    @GetMapping("/detailInfo1")
    GetFestivalDetailInfoResponse getFestivalDetailInfo(
        @RequestParam("MobileOS") String mobileOS,
        @RequestParam("MobileApp") String mobileApp,
        @RequestParam("_type") String responseType,
        @RequestParam("contentId") String contentId,
        @RequestParam("contentTypeId") String contentTypeId,
        @RequestParam("serviceKey") String serviceKey
    );

    @GetMapping("/detailImage1")
    GetFestivalDetailImageResponse getFestivalDetailImage(
        @RequestParam("MobileOS") String mobileOS,
        @RequestParam("MobileApp") String mobileApp,
        @RequestParam("_type") String responseType,
        @RequestParam("imageYN") String imageYN,
        @RequestParam("subImageYN") String subImageYN,
        @RequestParam("contentId") String contentId,
        @RequestParam("serviceKey") String serviceKey
    );
}