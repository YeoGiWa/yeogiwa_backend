package com.example.yeogiwa.openapi;

import com.example.yeogiwa.domain.event.EventEntity;
import com.example.yeogiwa.domain.event.EventRepository;
import com.example.yeogiwa.domain.event.dto.response.EventDetailResponse;
import com.example.yeogiwa.domain.event.dto.response.EventsResponse;
import com.example.yeogiwa.enums.Region;
import com.example.yeogiwa.enums.EventSort;
import com.example.yeogiwa.openapi.business.BusinessApiClient;
import com.example.yeogiwa.openapi.business.dto.response.BusinessRequestBody;
import com.example.yeogiwa.openapi.business.dto.response.BusinessResponse;
import com.example.yeogiwa.openapi.business.dto.response.BusinessStatus;
import com.example.yeogiwa.openapi.festival.FestivalApiClient;
import com.example.yeogiwa.openapi.festival.dto.*;
import feign.codec.DecodeException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class OpenApiService {

    private final FestivalApiClient festivalApiClient;
    private final BusinessApiClient businessApiClient;
    private final EventRepository eventRepository;
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");

    @Value("${openapi.festival-secret-key}") private String festivalServiceKey;
    @Value("${openapi.business-secret-key}") private String businessServiceKey;

    public EventDetailResponse getFestival(Long eventId) {
        if (eventId == null) throw new HttpClientErrorException(HttpStatusCode.valueOf(400));

        try {
            FestivalCommonDto festivalCommonDto = festivalApiClient.detailCommon("ETC", "test", "json", eventId.toString(),"Y","Y","Y","Y","Y","Y","Y", "15", festivalServiceKey)
                .getResponse().getBody().getItems().getItem().get(0);
            FestivalIntroDto festivalIntroDto = festivalApiClient.detailIntro("ETC", "test", "json", eventId.toString(), "15", festivalServiceKey)
                .getResponse().getBody().getItems().getItem().get(0);
            EventEntity event = eventRepository.findById(eventId).get();
            return EventDetailResponse.from(eventId, festivalCommonDto, festivalIntroDto, event.getIsApplicable());
        } catch (DecodeException e) {
            throw new HttpClientErrorException(HttpStatusCode.valueOf(404));
        }
    }

    public List<EventsResponse> getFestivals(Integer numOfRows, Integer pageNo, String keyword, Region region, String eventStartDate, String eventEndDate) {
        // searchFestival: 날짜 + 지역 검색 지원
        // searchKeyword: 검색어 + 지역 검색 지원

        // 0. 아무 필터도 없을 때
        // 1. 검색어
        // 2. 검색어(무시) + 지역
        // 3. 검색어(무시) + 날짜
        // 4. 검색어(무시) + 지역 + 날짜
        // 5. 지역
        // 6. 지역 + 날짜
        // 7. 날짜
        if (numOfRows==null || pageNo==null) throw new HttpClientErrorException(HttpStatusCode.valueOf(400));
        if (keyword != null && region == null && eventStartDate == null && eventEndDate == null) {
            // 1. 검색어 - Logic
            // 1) searchKeyword로 검색어 필터링 해 축제 목록 가져오기
            // 2) 축제 목록에서 ID만 뽑아내기
            // 3) DB에 해당 ID를 가진 event들이 있는지 가져오기
            // 4) detailIntro로 개별 축제 정보 가져오기: startDate, endDate 가져오기
            // 5) 데이터를 필요한 형태로 가공해 return 하기
            List<FestivalCommonDto> result;
            try {
                result = festivalApiClient.searchKeyword(numOfRows, pageNo, "ETC", "test", "json", "Y", EventSort.CREATE_AT.name(), keyword, "15", festivalServiceKey)
                    .getResponse().getBody().getItems().getItem();
            } catch (DecodeException e) {
                throw new HttpClientErrorException(HttpStatusCode.valueOf(204));
            }
            List<Long> ids = result.stream().map(FestivalCommonDto::getContentid).collect(Collectors.toList());
            Map<Long, Boolean> isApplicable = new HashMap<>();
            eventRepository.findAllById(ids).stream().map(event -> isApplicable.put(event.getId(), event.getIsApplicable()));
            return result.stream().map(
                festival -> {
                    FestivalIntroDto detailResult = festivalApiClient.detailIntro("ETC", "test", "json", festival.getContentid().toString(), "15", festivalServiceKey)
                        .getResponse().getBody().getItems().getItem().get(0);
                    return EventsResponse.from(festival, isApplicable, detailResult);
                }
            ).toList();
        } else {
            // 0., 2.~7. - Logic
            // 1) searchFesival로 지역/날짜 필터링 해 축제 목록 가져오기
            // 2) 축제 목록에서 ID만 뽑아내기
            // 3) DB에 해당 ID를 가진 event들이 있는지 가져오기
            // 4) 데이터를 필요한 형태로 가공해 return 하기
            List<FestivalDto> result;
            try {
                result = festivalApiClient.searchFestival(numOfRows, pageNo, "ETC", "test", "json", "Y", EventSort.CREATE_AT.name(), region != null ? region.code : null, eventStartDate != null ? eventStartDate : LocalDate.now().format(dateTimeFormatter), eventEndDate, festivalServiceKey)
                    .getResponse().getBody().getItems().getItem();
            } catch (DecodeException e) {
                throw new HttpClientErrorException(HttpStatusCode.valueOf(204));
            }
            List<Long> ids = result.stream().map(FestivalDto::getContentid).collect(Collectors.toList());
            Map<Long, Boolean> isApplicable = new HashMap<>();
            eventRepository.findAllById(ids).stream().map(event -> isApplicable.put(event.getId(), event.getIsApplicable()));
            return result.stream().map(
                festival -> EventsResponse.from(festival, isApplicable)
            ).toList();
        }
    }

    public List<EventsResponse> getNearbyFestivals(Integer numofRows, Integer pageNo, Double mapX, Double mapY, Double radius) {
        if (numofRows==null || pageNo==null || mapX==null || mapY==null || radius==null || 0>radius || radius>20000) {
            throw new HttpClientErrorException(HttpStatusCode.valueOf(400));
        }
        List<FestivalDto> results;
        try {
            results = festivalApiClient.locationBasedList(numofRows, pageNo, "ETC", "test", "json", "Y", EventSort.CREATE_AT.name(), mapX.toString(), mapY.toString(), radius.toString(), "15", festivalServiceKey)
                .getResponse().getBody().getItems().getItem();
        } catch (DecodeException e) {
            throw new HttpClientErrorException(HttpStatusCode.valueOf(204));
        }

        List<Long> ids = results.stream().map(FestivalDto::getContentid).toList();
        Map<Long, Boolean> isApplicable = new HashMap<>();
        eventRepository.findAllById(ids).stream().map(event -> isApplicable.put(event.getId(), event.getIsApplicable()));
        return results.stream().map(
            festival -> EventsResponse.from(festival, isApplicable)
        ).toList();
    }

    public Boolean isValidBusiness(String businessNumber) {
        List<BusinessStatus> businesses;
        try {
            List<String> reqBusinesses = new ArrayList<>();
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