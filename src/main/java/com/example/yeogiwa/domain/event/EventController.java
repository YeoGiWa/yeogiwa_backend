package com.example.yeogiwa.domain.event;

import com.example.yeogiwa.domain.event.dto.*;
import com.example.yeogiwa.enums.Region;
import com.example.yeogiwa.enums.EventSort;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;
    private final SessionService sessionService;

    @Operation(summary = "특정 이벤트 정보 조회", description = "특정 이벤트의 상세 정보를 반환")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "이벤트의 정보를 정상적으로 반환", content = {
            @Content(schema = @Schema(implementation = GetEventResponse.class))
        }),
        @ApiResponse(responseCode = "400", description = "오류로 인해 이벤트의 정보를 반환하지 못함", content = {
            @Content(schema = @Schema(implementation = HttpClientErrorException.BadRequest.class))
        }),
        @ApiResponse(responseCode = "404", description = "해당 event_id를 갖는 행사가 존재하지 않음", content = {
            @Content(schema = @Schema(implementation = HttpClientErrorException.NotFound.class))
        })
    })
    @GetMapping("/{id}")
    public ResponseEntity<GetEventResponse> getEventById(@Parameter(description = "행사 ID입니다.") @PathVariable("id") String id) {
        GetEventResponse event = eventService.getEventById(id);

        return ResponseEntity.status(200).body(event);
    }

    @Operation(summary = "이벤트 정보 검색", description = "검색한 이벤트들의 상세 정보를 반환")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "이벤트의 정보를 정상적으로 반환", content = {
            @Content(array = @ArraySchema(schema = @Schema(implementation = GetEventResponse.class)))
        }),
        @ApiResponse(responseCode = "400", description = "오류로 인해 이벤트의 정보를 반환하지 못함", content = {
            @Content(schema = @Schema(implementation = HttpClientErrorException.BadRequest.class))
        }),
    })
    @GetMapping("/list")
    public ResponseEntity<List<GetEventResponse>> listEvents(
        @Parameter(description = "[필수] 페이지에 보여줄 결과의 갯수 입니다. 기본 값은 10입니다.") @RequestParam(name = "numOfRows", defaultValue = "10") int numOfRows,
        @Parameter(description = "[필수] 페이지 번호입니다. 1부터 시작합니다.") @RequestParam("pageNo") int pageNo,
        @Parameter(description = "[필수] 조회할 축제의 지역입니다.") @RequestParam("region") Region region,
        @Parameter(description = "축제의 시작 날짜입니다. yyyymmdd 형식으로 보내주세요.") @RequestParam(name = "eventStartDate", required = false) String eventStartDate,
        @Parameter(description = "축제의 종료 날짜입니다. 단독으로 보낼 수 없습니다. yyyymmdd 형식으로 보내주세요.") @RequestParam(name = "eventEndDate", required = false) String eventEndDate,
        @Parameter(description = "[필수] 축제의 활성화 여부입니다. false는 전체 조회입니다.") @RequestParam(name = "isValid") Boolean isValid
    ) {
        if ((eventStartDate != null && eventStartDate.length() != 8) || (eventEndDate != null && eventEndDate.length() != 8) || (eventEndDate != null && eventStartDate == null) || (eventEndDate != null && eventStartDate.compareTo(eventEndDate) > 0)) {
            return ResponseEntity.status(400).body(null);
        }

        if (eventStartDate == null) {
            eventStartDate = "19000101";
        }

        List<GetEventResponse> event = eventService.listEvents(numOfRows, pageNo, region, eventStartDate, eventEndDate, isValid);

        return ResponseEntity.status(200).body(event);
    }

    @Operation(summary = "근처 이벤트 정보 검색", description = "특정 장소 근처에 있는 이벤트들의 상세 정보를 반환")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "이벤트의 정보를 정상적으로 반환", content = {
            @Content(array = @ArraySchema(schema = @Schema(implementation = GetEventResponse.class)))
        }),
        @ApiResponse(responseCode = "400", description = "오류로 인해 이벤트의 정보를 반환하지 못함", content = {
            @Content(schema = @Schema(implementation = HttpClientErrorException.BadRequest.class))
        })
    })
    @GetMapping("/nearby")
    public ResponseEntity<List<GetEventResponse>> listEventsNearby(
        @Parameter(description = "[필수] 페이지에 보여줄 결과의 갯수 입니다. 기본 값은 10입니다.") @RequestParam(name = "numOfRows", defaultValue = "10") int numOfRows,
        @Parameter(description = "[필수] 페이지 번호입니다. 1부터 시작합니다.") @RequestParam("pageNo") int pageNo,
        @Parameter(description = "[필수] 검색할 지도의 경도입니다. xxx.xxxxxx 형식으로 보내주세요.") @RequestParam("mapX") String mapX,
        @Parameter(description = "[필수] 검색할 지도의 위도입니다. xxx.xxxxxx 형식으로 보내주세요.") @RequestParam("mapY") String mapY,
        @Parameter(description = "[필수] 검색할 지도의 범위입니다. m 단위로 보내주세요.") @RequestParam("radius") String radius
    ) {
        List<GetEventResponse> event = eventService.listEventsNearby(numOfRows, pageNo, mapX, mapY, radius);

        return ResponseEntity.status(200).body(event);
    }

    // 호스트 혹은 관리자 전용
    @Operation(summary = "이벤트 생성", description = "축제 API의 id로 새로운 이벤트를 생성")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "이벤트를 정상적으로 생성", content = {
            @Content(schema = @Schema(implementation = EventDto.class))
        }),
        @ApiResponse(responseCode = "400", description = "오류로 인해 이벤트를 생성하지 못함", content = {
            @Content(schema = @Schema(implementation = HttpClientErrorException.BadRequest.class))
        })
    })
    @PostMapping
    public ResponseEntity<EventDto> createEvent(@Valid @RequestBody CreateEventRequest request) {
        // TODO: 관리자만 생성 가능하도록 수정
        EventDto createdEventDto = eventService.createEvent(request);

        return ResponseEntity.status(200).body(createdEventDto);
    }

    // 호스트 혹은 관리자 전용
    @Operation(summary = "이벤트 수정", description = "이벤트의 정보를 수정")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "이벤트를 정상적으로 수정", content = {
            @Content(schema = @Schema(implementation = EventDto.class))
        }),
        @ApiResponse(responseCode = "400", description = "오류로 인해 이벤트를 수정하지 못함", content = {
            @Content(schema = @Schema(implementation = HttpClientErrorException.BadRequest.class))
        })
    })
    @PutMapping("/{id}")
    public ResponseEntity<EventDto> updateEvent(@PathVariable("id") String id, @RequestBody UpdateEventRequest request) {
        // TODO: 관리자만 수정 가능하도록 수정
        EventDto updatedEventDto = eventService.updateEvent(id, request);

        return ResponseEntity.status(200).body(updatedEventDto);
    }

    @Operation(summary = "이벤트 삭제", description = "이벤트의 등록 정보를 삭제")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "이벤트를 정상적으로 삭제", content = {
            @Content(schema = @Schema(implementation = EventDto.class))
        }),
        @ApiResponse(responseCode = "400", description = "오류로 인해 이벤트를 삭제하지 못함", content = {
            @Content(schema = @Schema(implementation = HttpClientErrorException.BadRequest.class))
        })
    })
    @DeleteMapping("/{id}")
    public void deleteEvent(@PathVariable("id") String id) {
        // TODO: 관리자만 삭제 가능하도록 수정
        eventService.deleteEvent(id);
    }

    @Operation(summary = "회차 추가", description = "관리자가 등록한 축제에 회차 추가하기")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "회차를 정상적으로 생성", content = {
            @Content(schema = @Schema(implementation = SessionEntity.class))
        }),
        @ApiResponse(responseCode = "400", description = "오류로 인해 회차를 생성하지 못함", content = {
            @Content(schema = @Schema(implementation = HttpClientErrorException.BadRequest.class))
        })
    })
    @PostMapping("/session")
    public ResponseEntity<SessionDto> createSession(@Valid @RequestBody CreateSessionRequest request) {
        SessionDto session = sessionService.createSession(request);

        return ResponseEntity.status(200).body(session);
    }

    @Operation(summary = "회차 수정", description = "관리자가 등록한 축제의 회차 수정하기")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "회차를 정상적으로 수정", content = {
            @Content(schema = @Schema(implementation = EventDto.class))
        }),
        @ApiResponse(responseCode = "400", description = "오류로 인해 회차를 수정하지 못함", content = {
            @Content(schema = @Schema(implementation = HttpClientErrorException.BadRequest.class))
        })
    })
    @PutMapping("/session/{id}")
    public ResponseEntity<SessionDto> updateSession(@PathVariable("id") UUID id, @Valid @RequestBody UpdateSessionRequest request) {
        SessionDto session = sessionService.updateSession(id, request);

        return ResponseEntity.status(200).body(session);
    }

    @Operation(summary = "회차 삭제", description = "관리자가 등록한 축제의 회차 삭제하기")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "회차를 정상적으로 삭제", content = {
            @Content(schema = @Schema(implementation = EventDto.class))
        }),
        @ApiResponse(responseCode = "400", description = "오류로 인해 회차를 삭제하지 못함", content = {
            @Content(schema = @Schema(implementation = HttpClientErrorException.BadRequest.class))
        })
    })
    @DeleteMapping("/session/{id}")
    public void deleteSession(@PathVariable("id") UUID id) {
        sessionService.deleteSession(id);
    }

    @GetMapping("/ambassadors")
    @Operation(summary = "앰배서더 홍보 목록 조회", description = "해당 유저가 홍보하고 있는 행사/축제 목록 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "유저가 홍보중인 행사/축제 목록을 성공적으로 조회한 경우", content = @Content(schema = @Schema(implementation = EventEntity.class))),
            @ApiResponse(responseCode = "400", description = "오류가 발생해 유저가 홍보중인 행사/축제 목록을 조회하지 못한 경우", content = @Content(schema = @Schema(implementation = HttpClientErrorException.BadRequest.class))),
            @ApiResponse(responseCode = "401", description = "로그인 하지 않은 유저의 요청인 경우", content = @Content(schema = @Schema(implementation = HttpClientErrorException.Unauthorized.class))),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 유저이거나 탈퇴한 유저인 경우", content = @Content(schema = @Schema(implementation = HttpClientErrorException.NotFound.class)))
    })
    public ResponseEntity<List<EventDto>> getUserAmbassadorList(@Parameter(description = "[필수] 진행중 여부입니다.") @RequestParam(name = "isValid") Boolean isValid) {
        // TODO: 토큰에서 가져오기
        String email = "test@gmail.com";

        List<EventDto> events = eventService.listEventsByAmbassador(email, isValid);

        return ResponseEntity.status(200).body(events);
    }
}