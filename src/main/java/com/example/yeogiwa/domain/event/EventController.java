package com.example.yeogiwa.domain.event;

import com.example.yeogiwa.auth.oauth.PrincipalDetails;
import com.example.yeogiwa.domain.ambassador.AmbassadorEntity;
import com.example.yeogiwa.domain.ambassador.dto.AmbassadorDto;
import com.example.yeogiwa.domain.event.dto.EventDto;
import com.example.yeogiwa.domain.event.dto.response.EventDetailResponse;
import com.example.yeogiwa.domain.event.dto.response.EventsResponse;
import com.example.yeogiwa.enums.Region;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Null;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/event")
@RequiredArgsConstructor
@Tag(name = "🎆 이벤트 API", description = "이벤트 관련 API")
public class EventController {
    private final EventService eventService;

    @Operation(summary = "특정 이벤트 정보 조회", description = "특정 이벤트의 상세 정보를 반환")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "이벤트의 정보를 정상적으로 반환", content = {
            @Content(schema = @Schema(implementation = EventDetailResponse.class))
        }),
        @ApiResponse(responseCode = "400", description = "오류로 인해 이벤트의 정보를 반환하지 못함", content = {
            @Content(schema = @Schema(implementation = Null.class))
        }),
        @ApiResponse(responseCode = "404", description = "해당 event_id를 갖는 이벤트가 존재하지 않음", content = {
            @Content(schema = @Schema(implementation = Null.class))
        })
    })
    @GetMapping("/{eventId}")
    public ResponseEntity<EventDetailResponse> getEventById(@Parameter(description = "행사 ID입니다.", example = "2541883") @PathVariable Long eventId) {
        EventDetailResponse event = eventService.getEventDetailById(eventId);
        return ResponseEntity.status(200).body(event);
    }

    @Operation(summary = "축제 정보 검색", description = "검색한 축제들의 상세 정보를 반환")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "축제의 정보를 정상적으로 반환", content = {
            @Content(array = @ArraySchema(schema = @Schema(implementation = EventsResponse.class)))
        }),
        @ApiResponse(responseCode = "204", description = "더 이상 축제 정보가 없음", content = {
            @Content(schema = @Schema(implementation = Null.class))
        }),
        @ApiResponse(responseCode = "400", description = "오류로 인해 축제의 정보를 반환하지 못함", content = {
            @Content(schema = @Schema(implementation = Null.class))
        }),
    })
    @GetMapping("/list")
    public ResponseEntity<List<EventsResponse>> getEvents(
        @Parameter(description = "페이지에 보여줄 결과의 갯수 입니다. 기본 값은 10입니다.", example = "10") @RequestParam(defaultValue = "10") Integer numOfRows,
        @Parameter(description = "페이지 번호입니다. 1부터 시작합니다.", example = "1") @RequestParam Integer pageNo,
        @Parameter(description = "[필터] 검색어. 다른 필터가 설정될 경우 무시됩니다.") @RequestParam(required = false) String keyword,
        @Parameter(description = "[필터] 조회할 축제의 지역", example = "ALL") @RequestParam(required = false) Region region,
        @Parameter(description = "[필터] 시작 날짜(yyyymmdd). 오늘 날짜를 기본값으로 갖습니다.") @RequestParam(required = false) String eventStartDate,
        @Parameter(description = "[필터] 종료 날짜(yyyymmdd)") @RequestParam(required = false) String eventEndDate
    ) {
        if (region == Region.ALL) region = null;
        List<EventsResponse> events = eventService.getEvents(numOfRows, pageNo, keyword, region, eventStartDate, eventEndDate);
        return ResponseEntity.status(200).body(events);
    }

    @Operation(summary = "근처 축제 정보 검색", description = "특정 장소 근처에 있는 축제들의 상세 정보를 반환")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "축제의 정보를 정상적으로 반환", content = {
            @Content(array = @ArraySchema(schema = @Schema(implementation = EventsResponse.class)))
        }),
        @ApiResponse(responseCode = "204", description = "더 이상 축제 정보가 없음", content = @Content(schema = @Schema(implementation = Null.class))),
        @ApiResponse(responseCode = "400", description = "오류로 인해 축제의 정보를 반환하지 못함", content = {
            @Content(schema = @Schema(implementation = Null.class))
        })
    })
    @GetMapping("/nearby")
    public ResponseEntity<List<EventsResponse>> getNearbyEvents(
        @Parameter(description = "페이지에 보여줄 결과의 갯수 입니다. 기본 값은 10입니다.", example = "10") @RequestParam(defaultValue = "10") Integer numOfRows,
        @Parameter(description = "페이지 번호입니다. 1부터 시작합니다.", example = "1") @RequestParam Integer pageNo,
        @Parameter(description = "검색할 지도의 경도입니다. xxx.xxxxxx 형식으로 보내주세요.", example = "127.12345") @RequestParam Double mapX,
        @Parameter(description = "검색할 지도의 위도입니다. xxx.xxxxxx 형식으로 보내주세요.", example = "37.12345") @RequestParam Double mapY,
        @Parameter(description = "검색할 지도의 범위입니다. m 단위로 보내주세요. 최대 20000m 입니다.", example = "10000.1234") @RequestParam Double radius
    ) {
        List<EventsResponse> results = eventService.getNearbyEvents(numOfRows, pageNo, mapX, mapY, radius);
        return ResponseEntity.status(200).body(results);
    }

    @Operation(summary = "행사 활성화", description = "앰배서더가 신청할 수 있도록 행사를 활성화 상태로 변경합니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "행사를 정상적으로 활성화", content = @Content(schema = @Schema(implementation = EventDto.class))),
        @ApiResponse(responseCode = "400", description = "오류가 발생해 행사를 활성화하지 못함", content = @Content(schema = @Schema(implementation = Null.class))),
        @ApiResponse(responseCode = "403", description = "호스트 본인의 행사가 아닌 경우", content = @Content(schema = @Schema(implementation = Null.class))),
        @ApiResponse(responseCode = "404", description = "해당 ID를 갖는 행사가 없음", content = @Content(schema = @Schema(implementation = Null.class)))
    })
    @PatchMapping("/{eventId}/applicable")
    public ResponseEntity<EventDto> makeEventApplicable(Authentication authentication, @PathVariable Long eventId) {
        PrincipalDetails user = (PrincipalDetails) authentication.getPrincipal();
        EventEntity event = eventService.changeApplicable(user.getUserId(), eventId, true);
        return ResponseEntity.status(200).body(EventDto.from(event));
    }

    @Operation(summary = "행사 비활성화", description = "앰배서더가 신청할 수 없도록 행사를 비활성화 상태로 변경합니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "행사를 정상적으로 비활성화", content = @Content(schema = @Schema(implementation = EventDto.class))),
        @ApiResponse(responseCode = "400", description = "오류가 발생해 행사를 비활성화하지 못함", content = @Content(schema = @Schema(implementation = Null.class))),
        @ApiResponse(responseCode = "403", description = "호스트 본인의 행사가 아닌 경우", content = @Content(schema = @Schema(implementation = Null.class))),
        @ApiResponse(responseCode = "404", description = "해당 ID를 갖는 행사가 없음", content = @Content(schema = @Schema(implementation = Null.class)))
    })
    @PatchMapping("/{eventId}/unapplicable")
    public ResponseEntity<EventDto> makeEventUnapplicable(Authentication authentication, @PathVariable Long eventId) {
        PrincipalDetails user = (PrincipalDetails) authentication.getPrincipal();
        EventEntity event = eventService.changeApplicable(user.getUserId(), eventId, false);
        return ResponseEntity.status(200).body(EventDto.from(event));
    }

    @Operation(summary = "행사 삭제", description = "행사의 등록 정보를 삭제")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "행사를 정상적으로 삭제", content = {
            @Content(schema = @Schema(implementation = EventDto.class))
        }),
        @ApiResponse(responseCode = "400", description = "오류로 인해 행사를 삭제하지 못함", content = {
            @Content(schema = @Schema(implementation = Null.class))
        })
    })
    @DeleteMapping("/{eventId}")
    public ResponseEntity<Long> deleteEvent(@PathVariable String eventId) {
        return null;
    }

    @GetMapping("/ambassadors")
    @Operation(summary = "특정 축제의 앰배서더 목록 조회", description = "해당 행사/축제의 앰배서더 목록 조회")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "행사/축제의 앰배서더 목록을 성공적으로 조회한 경우", content = @Content(array = @ArraySchema(schema = @Schema(implementation = AmbassadorEntity.class)))),
        @ApiResponse(responseCode = "400", description = "오류가 발생해 행사/축제의 앰배서더 목록을 조회하지 못한 경우", content = @Content(schema = @Schema(implementation = Null.class))),
        @ApiResponse(responseCode = "404", description = "존재하지 않는 행사/축제인 경우", content = @Content(schema = @Schema(implementation = Null.class)))
    })
    public ResponseEntity<List<AmbassadorDto>> getAmbassadorList(@Parameter(description = "앰버서더를 조회할 행사의 ID입니다.", example = "12345678") @RequestParam(defaultValue = "12345678") String eventId) {
//        List<AmbassadorDto> ambassadors = ambassadorService.listAmbassadorsByEvent(eventId);

//        return ResponseEntity.status(200).body(ambassadors);
        return null;
    }
}
