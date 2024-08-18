package com.example.yeogiwa.openapi;

import com.example.yeogiwa.enums.Region;
import com.example.yeogiwa.enums.Sort;
import com.example.yeogiwa.openapi.dto.response.FestivalResponse;
import com.example.yeogiwa.openapi.dto.FestivalInfoDto;
import com.example.yeogiwa.openapi.dto.FestivalImageDto;
import com.example.yeogiwa.openapi.dto.FestivalIntroDto;
import com.example.yeogiwa.openapi.dto.FestivalDto;
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

    public List<FestivalDto> listFestivalDetails(int numOfRows, int pageNo, Sort sort, Region region, String eventStartDate, String eventEndDate) {
        FestivalResponse<FestivalDto> response = null;
        try {
            String regionCode = region == Region.ALL ? null : region.code;

            response = openApiClient.listFestival(numOfRows, pageNo, "ETC", "test", "json",
                "Y", sort.type, regionCode, eventStartDate, eventEndDate, serviceKey);
        } catch (DecodeException e) {
            return null;
        }

        if (response != null && response.getResponse().getBody() != null && response.getResponse().getBody().getItems() != null) {
            return response.getResponse().getBody().getItems().getItem();
        }

        return null;
    }

    public List<FestivalDto> listFestivalDetailsByKeyword(int numOfRows, int pageNo, Sort sort, Region region, String keyword) {
        FestivalResponse<FestivalDto> response = null;
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

    public List<FestivalDto> listNearbyFestival(int numOfRows, int pageNo, Sort sort, String mapX, String mapY, String radius) {
        FestivalResponse<FestivalDto> response = null;
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

    public FestivalDto getFestivalDetail(String contentId) {
        FestivalResponse<FestivalDto> response = null;
        try {
            response = openApiClient.getFestivalDetail("ETC", "test", "json", contentId,"Y","Y","Y","Y","Y","Y","Y", "15", serviceKey);
        } catch (DecodeException e) {
            e.printStackTrace();
            throw new RuntimeException("Invalid format");
        }

        if (response != null && response.getResponse().getBody() != null && response.getResponse().getBody().getItems() != null) {
            List<FestivalDto> item = response.getResponse().getBody().getItems().getItem();

            return item.get(0);
        }

        return null;
    }

    public FestivalIntroDto getFestivalDetailIntro(String contentId) {
        FestivalResponse<FestivalIntroDto> response = null;
        try {
            response = openApiClient.getFestivalDetailIntro("ETC", "test", "json", contentId,"15", serviceKey);
        } catch (DecodeException e) {
            return null;
        }

        if (response != null && response.getResponse().getBody() != null && response.getResponse().getBody().getItems() != null) {
            List<FestivalIntroDto> item = response.getResponse().getBody().getItems().getItem();

            return item.get(0);
        }

        return null;
    }

    public List<FestivalInfoDto> getFestivalDetailInfo(String contentId) {
        FestivalResponse<FestivalInfoDto> response = null;
        try {
            response = openApiClient.getFestivalDetailInfo("ETC", "test", "json", contentId,"15", serviceKey);
        } catch (DecodeException e) {
            return null;
        }

        if (response != null && response.getResponse().getBody() != null && response.getResponse().getBody().getItems() != null) {
            List<FestivalInfoDto> item = response.getResponse().getBody().getItems().getItem();

            return item;
        }

        return null;
    }

    public List<FestivalImageDto> getFestivalDetailImage(String contentId) {
        FestivalResponse<FestivalImageDto> response = null;
        try {
            response = openApiClient.getFestivalDetailImage("ETC", "test", "json", "Y", "Y", contentId, serviceKey);
        } catch (DecodeException e) {
            return null;
        }

        if (response != null && response.getResponse().getBody() != null && response.getResponse().getBody().getItems() != null) {
            List<FestivalImageDto> item = response.getResponse().getBody().getItems().getItem();

            return item;
        }

        return null;
    }
}