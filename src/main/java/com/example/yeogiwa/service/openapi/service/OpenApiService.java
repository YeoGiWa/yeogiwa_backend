package com.example.yeogiwa.service.openapi.service;

import com.example.yeogiwa.enums.Region;
import com.example.yeogiwa.enums.Sort;
import com.example.yeogiwa.service.openapi.client.OpenApiClient;
import com.example.yeogiwa.service.openapi.dto.defatilinfo.FestivalDetailInfoDto;
import com.example.yeogiwa.service.openapi.dto.defatilinfo.GetFestivalDetailInfoResponse;
import com.example.yeogiwa.service.openapi.dto.detailImage.FestivalDetailImageDto;
import com.example.yeogiwa.service.openapi.dto.detailImage.GetFestivalDetailImageResponse;
import com.example.yeogiwa.service.openapi.dto.detailcommon.FestivalDetailDto;
import com.example.yeogiwa.service.openapi.dto.detailcommon.GetFestivalDetailResponse;
import com.example.yeogiwa.service.openapi.dto.detailintro.FestivalDetailIntroDto;
import com.example.yeogiwa.service.openapi.dto.detailintro.GetFestivalDetailIntroResponse;
import feign.codec.DecodeException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OpenApiService {

    private final OpenApiClient openApiClient;

    @Value("${openapi.secret-key}") private String serviceKey;

    public List<FestivalDetailDto> listFestivalDetails(int numOfRows, int pageNo, Sort sort, Region region, String eventStartDate, String eventEndDate) {
        GetFestivalDetailResponse response = null;
        try {
            response = openApiClient.listFestival(numOfRows, pageNo, "ETC", "test", "json",
                "Y", sort.type, region.code, eventStartDate, eventEndDate, serviceKey);
        } catch (DecodeException e) {
            return null;
        }

        if (response != null && response.getResponse().getBody() != null && response.getResponse().getBody().getItems() != null) {
            return response.getResponse().getBody().getItems().getItem();
        }

        return null;
    }

    public List<FestivalDetailDto> listFestivalDetailsByKeyword(int numOfRows, int pageNo, Sort sort, Region region, String keyword) {
        GetFestivalDetailResponse response = null;
        try {
            response = openApiClient.listFestivalByKeyword(numOfRows, pageNo, "ETC", "test", "json",
                "Y", sort.type, region.code, keyword, "15", serviceKey);
        } catch(DecodeException e) {
            return null;
        }

        if (response != null && response.getResponse().getBody() != null && response.getResponse().getBody().getItems() != null) {
            return response.getResponse().getBody().getItems().getItem();
        }

        return null;
    }

    public List<FestivalDetailDto> listNearbyFestival(int numOfRows, int pageNo, Sort sort, String mapX, String mapY, String radius) {
        GetFestivalDetailResponse response = null;
        try {
            response = openApiClient.listNearbyFestival(numOfRows, pageNo, "ETC", "test", "json",
                "Y", sort.type, mapX, mapY, radius, "15", serviceKey);
        } catch(DecodeException e) {
            return null;
        }

        if (response != null && response.getResponse().getBody() != null && response.getResponse().getBody().getItems() != null) {
            return response.getResponse().getBody().getItems().getItem();
        }

        return null;
    }

    public FestivalDetailDto getFestivalDetail(String contentId) {
        GetFestivalDetailResponse response = null;
        try {
            response = openApiClient.getFestivalDetail("ETC", "test", "json", contentId,"Y","Y","Y","Y","Y","Y","Y", "15", serviceKey);
        } catch (DecodeException e) {
            e.printStackTrace();
            throw new RuntimeException("Invalid format");
        }

        if (response != null && response.getResponse().getBody() != null && response.getResponse().getBody().getItems() != null) {
            List<FestivalDetailDto> item = response.getResponse().getBody().getItems().getItem();

            return item.get(0);
        }

        return null;
    }

    public FestivalDetailIntroDto getFestivalDetailIntro(String contentId) {
        GetFestivalDetailIntroResponse response = null;
        try {
            response = openApiClient.getFestivalDetailIntro("ETC", "test", "json", contentId,"15", serviceKey);
        } catch (DecodeException e) {
            return null;
        }

        if (response != null && response.getResponse().getBody() != null && response.getResponse().getBody().getItems() != null) {
            List<FestivalDetailIntroDto> item = response.getResponse().getBody().getItems().getItem();

            return item.get(0);
        }

        return null;
    }

    public List<FestivalDetailInfoDto> getFestivalDetailInfo(String contentId) {
        GetFestivalDetailInfoResponse response = null;
        try {
            response = openApiClient.getFestivalDetailInfo("ETC", "test", "json", contentId,"15", serviceKey);
        } catch (DecodeException e) {
            return null;
        }

        if (response != null && response.getResponse().getBody() != null && response.getResponse().getBody().getItems() != null) {
            List<FestivalDetailInfoDto> item = response.getResponse().getBody().getItems().getItem();

            return item;
        }

        return null;
    }

    public List<FestivalDetailImageDto> getFestivalDetailImage(String contentId) {
        GetFestivalDetailImageResponse response = null;
        try {
            response = openApiClient.getFestivalDetailImage("ETC", "test", "json", "Y", "Y", contentId, serviceKey);
        } catch (DecodeException e) {
            return null;
        }

        if (response != null && response.getResponse().getBody() != null && response.getResponse().getBody().getItems() != null) {
            List<FestivalDetailImageDto> item = response.getResponse().getBody().getItems().getItem();

            return item;
        }

        return null;
    }
}