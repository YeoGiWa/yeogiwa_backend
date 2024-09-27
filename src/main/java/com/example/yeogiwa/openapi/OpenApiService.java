package com.example.yeogiwa.openapi;

import com.example.yeogiwa.domain.host.dto.CreateHostBody;
import com.example.yeogiwa.enums.Region;
import com.example.yeogiwa.enums.EventSort;
import com.example.yeogiwa.openapi.business.BusinessApiClient;
import com.example.yeogiwa.openapi.business.dto.response.BusinessRequestBody;
import com.example.yeogiwa.openapi.business.dto.response.BusinessResponse;
import com.example.yeogiwa.openapi.business.dto.response.BusinessStatus;
import com.example.yeogiwa.openapi.festival.FestivalApiClient;
import com.example.yeogiwa.openapi.festival.dto.response.FestivalResponse;
import com.example.yeogiwa.openapi.festival.dto.FestivalInfoDto;
import com.example.yeogiwa.openapi.festival.dto.FestivalImageDto;
import com.example.yeogiwa.openapi.festival.dto.FestivalIntroDto;
import com.example.yeogiwa.openapi.festival.dto.FestivalDto;
import feign.codec.DecodeException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OpenApiService {

    private final FestivalApiClient festivalApiClient;
    private final BusinessApiClient businessApiClient;

    @Value("${openapi.festival-secret-key}") private String festivalServiceKey;
    @Value("${openapi.business-secret-key}") private String businessServiceKey;

    public List<FestivalDto> listFestivalDetails(int numOfRows, int pageNo, Region region, String eventStartDate, String eventEndDate) {
        FestivalResponse<FestivalDto> response = null;
        try {
            String regionCode = region == Region.ALL ? null : region.code;

            response = festivalApiClient.listFestival(numOfRows, pageNo, "ETC", "test", "json",
                "Y", EventSort.CREATING.type, regionCode, eventStartDate, eventEndDate, festivalServiceKey);
        } catch (DecodeException e) {
            return null;
        }

        if (response != null && response.getResponse().getBody() != null && response.getResponse().getBody().getItems() != null) {
            return response.getResponse().getBody().getItems().getItem();
        }

        return null;
    }

    public List<FestivalDto> listFestivalDetailsByKeyword(int numOfRows, int pageNo, EventSort eventSort, String keyword) {
        FestivalResponse<FestivalDto> response = null;
        try {
            response = festivalApiClient.listFestivalByKeyword(numOfRows, pageNo, "ETC", "test", "json",
                "Y", eventSort.type, keyword, "15", festivalServiceKey);
        } catch(DecodeException e) {
            return null;
        }

        if (response != null && response.getResponse().getBody() != null && response.getResponse().getBody().getItems() != null) {
            return response.getResponse().getBody().getItems().getItem();
        }

        return null;
    }

    public List<FestivalDto> listNearbyFestival(int numOfRows, int pageNo, String mapX, String mapY, String radius) {
        FestivalResponse<FestivalDto> response = null;
        try {
            response = festivalApiClient.listNearbyFestival(numOfRows, pageNo, "ETC", "test", "json",
                "Y", EventSort.CREATING.type, mapX, mapY, radius, "15", festivalServiceKey);
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
            response = festivalApiClient.getFestivalDetail("ETC", "test", "json", contentId,"Y","Y","Y","Y","Y","Y","Y", "15", festivalServiceKey);
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
            response = festivalApiClient.getFestivalDetailIntro("ETC", "test", "json", contentId,"15", festivalServiceKey);
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
            response = festivalApiClient.getFestivalDetailInfo("ETC", "test", "json", contentId,"15", festivalServiceKey);
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
            response = festivalApiClient.getFestivalDetailImage("ETC", "test", "json", "Y", "Y", contentId, festivalServiceKey);
        } catch (DecodeException e) {
            return null;
        }

        if (response != null && response.getResponse().getBody() != null && response.getResponse().getBody().getItems() != null) {
            List<FestivalImageDto> item = response.getResponse().getBody().getItems().getItem();

            return item;
        }

        return null;
    }

    public Boolean isValidBusiness(String businessNumber) {
        List<BusinessStatus> businesses = new ArrayList<>();
        try {
            List<String> reqBusinesses = new ArrayList<String>();
            reqBusinesses.add(businessNumber);
            BusinessRequestBody requestBody = new BusinessRequestBody(reqBusinesses);
            BusinessResponse response = businessApiClient.businessStatus(businessServiceKey, requestBody);
            businesses = response.getData();
        } catch (DecodeException e) {
            return false;
        }
        for (BusinessStatus business: businesses) {
            if (!business.getB_stt_cd().equals("01")) return false;
        }
        return true;
    }
}