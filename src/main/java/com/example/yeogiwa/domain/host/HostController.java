package com.example.yeogiwa.domain.host;

import com.example.yeogiwa.auth.oauth.PrincipalDetails;
import com.example.yeogiwa.domain.event.dto.EventDto;
import com.example.yeogiwa.domain.host.dto.*;
import com.example.yeogiwa.domain.host.dto.request.CreateHostDto;
import com.example.yeogiwa.domain.host.dto.request.CreateHostEventDto;
import com.example.yeogiwa.domain.host.dto.request.CreateHostRoundDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Null;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/host")
@ResponseBody
@RequiredArgsConstructor
@Tag(name = "🦹‍ 호스트 API", description = "호스트 관련 API")
public class HostController {
    private final HostService hostService;

    // 유저가 관리자 신청하면 운영자가 확인 후 호스트로 등록 -> 심사를 위해 관리자 신청시 바로 호스트로 등록
    @PostMapping
    @Operation(summary = "호스트 등록", description = "사업자 번호가 유효하고 휴업/폐업자가 아닐 때, 새로운 호스트와 신청 가능한 행사들을 등록하고 해당 유저의 Role을 ADMIN으로 변경합니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "호스트로 등록 성공", content = @Content(schema = @Schema(implementation = HostDto.class))),
        @ApiResponse(responseCode = "400", description = "호스트로 등록 실패", content = @Content(schema = @Schema(implementation = Null.class))),
        @ApiResponse(responseCode = "401", description = "로그인 하지 않은 유저의 요청인 경우", content = @Content(schema = @Schema(implementation = Null.class))),
        @ApiResponse(responseCode = "404", description = "존재하지 않는 행사/축제인 경우", content = @Content(schema = @Schema(implementation = Null.class))),
        @ApiResponse(responseCode = "409", description = "이미 호스트로 등록한 경우", content = @Content(schema = @Schema(implementation = Null.class)))
    })
    public ResponseEntity<HostDto> registerHost(Authentication authentication, @RequestBody CreateHostDto body) {
        PrincipalDetails user = (PrincipalDetails) authentication.getPrincipal();
        HostDto host = hostService.createHost(user.getUserId(), body);
        return ResponseEntity.status(200).body(host);
    }

    @GetMapping("/events")
    @Operation(summary = "호스트의 행사 목록 조회", description = "호스트가 등록한 행사/축제 목록 조회")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "호스트가 등록한 행사/축제 목록을 성공적으로 조회한 경우", content = @Content(array = @ArraySchema(schema = @Schema(implementation = EventDto.class)))),
        @ApiResponse(responseCode = "400", description = "오류가 발생해 호스트가 등록한 행사/축제 목록을 조회하지 못한 경우", content = @Content(schema = @Schema(implementation = HttpClientErrorException.BadRequest.class))),
        @ApiResponse(responseCode = "401", description = "로그인 하지 않은 유저의 요청인 경우", content = @Content(schema = @Schema(implementation = HttpClientErrorException.Unauthorized.class))),
        @ApiResponse(responseCode = "404", description = "존재하지 않는 호스트인 경우", content = @Content(schema = @Schema(implementation = HttpClientErrorException.NotFound.class)))
    })
    public ResponseEntity<List<EventDto>> getHostEvents(Authentication authentication) {
        PrincipalDetails user = (PrincipalDetails) authentication.getPrincipal();
        List<EventDto> events = hostService.getHostEvents(user.getUserId());
        return ResponseEntity.status(200).body(events);
    }

    @GetMapping("/{eventId}/rounds")
    @Operation(summary = "호스트의 행사의 회차 목록 조회", description = "해당 호스트의 행사/축제 회차 목록 조회")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "호스트의 행사/축제 회차 목록을 성공적으로 조회한 경우", content = @Content(array = @ArraySchema(schema = @Schema(implementation = EventDto.class)))),
        @ApiResponse(responseCode = "400", description = "오류가 발생해 호스트의 행사/축제 회차 목록을 조회하지 못한 경우", content = @Content(schema = @Schema(implementation = HttpClientErrorException.BadRequest.class))),
        @ApiResponse(responseCode = "401", description = "로그인 하지 않은 유저의 요청인 경우", content = @Content(schema = @Schema(implementation = HttpClientErrorException.Unauthorized.class))),
        @ApiResponse(responseCode = "404", description = "호스트나 행사가 존재하지 않는 경우", content = @Content(schema = @Schema(implementation = HttpClientErrorException.NotFound.class)))
    })
    public ResponseEntity<List<EventDto>> getHostEventRounds(Authentication authentication, @PathVariable Long eventId) {
        PrincipalDetails user = (PrincipalDetails) authentication.getPrincipal();
        List<EventDto> events = hostService.getHostEventRounds(user.getUserId(), eventId);
        if (events.isEmpty()) throw new HttpClientErrorException(HttpStatusCode.valueOf(404));
        return ResponseEntity.status(200).body(events);
    }

    @PostMapping("/event")
    @Operation(summary = "호스트의 행사 생성", description = "호스트의 행사/축제 등록")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "호스트가 행사/축제 목록을 성공적으로 등록한 경우", content = @Content(array = @ArraySchema(schema = @Schema(implementation = EventDto.class)))),
        @ApiResponse(responseCode = "400", description = "오류가 발생해 호스트가 등록한 행사/축제 목록을 등록하지 못한 경우", content = @Content(schema = @Schema(implementation = HttpClientErrorException.BadRequest.class))),
        @ApiResponse(responseCode = "401", description = "로그인 하지 않은 유저의 요청인 경우", content = @Content(schema = @Schema(implementation = HttpClientErrorException.Unauthorized.class))),
        @ApiResponse(responseCode = "404", description = "호스트가 존재하지 않는 경우", content = @Content(schema = @Schema(implementation = HttpClientErrorException.NotFound.class)))
    })
    public ResponseEntity<Long> createHostEvent(Authentication authentication, @RequestBody CreateHostEventDto eventsDto) {
        PrincipalDetails user = (PrincipalDetails) authentication.getPrincipal();
        if (
            eventsDto.getEventId() == null ||
                eventsDto.getTitle() == null ||
                eventsDto.getRatio() == null || eventsDto.getRatio()<0 || eventsDto.getRatio()>100
        ) {
            throw new HttpClientErrorException(HttpStatusCode.valueOf(400));
        }
        List<CreateHostEventDto> eventsDtos = new ArrayList<>();
        eventsDtos.add(eventsDto);
        Long newEventId = hostService.createHostEvents(user.getUserId(), eventsDtos).get(0);
        return ResponseEntity.status(201).body(newEventId);
    }

    @PostMapping("/events")
    @Operation(summary = "호스트의 행사 여러개 생성", description = "호스트의 행사/축제 여러개 등록")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "호스트가 행사/축제 목록을 성공적으로 등록한 경우", content = @Content(array = @ArraySchema(schema = @Schema(implementation = EventDto.class)))),
        @ApiResponse(responseCode = "400", description = "오류가 발생해 호스트가 등록한 행사/축제 목록을 등록하지 못한 경우", content = @Content(schema = @Schema(implementation = HttpClientErrorException.BadRequest.class))),
        @ApiResponse(responseCode = "401", description = "로그인 하지 않은 유저의 요청인 경우", content = @Content(schema = @Schema(implementation = HttpClientErrorException.Unauthorized.class))),
        @ApiResponse(responseCode = "404", description = "호스트가 존재하지 않는 경우", content = @Content(schema = @Schema(implementation = HttpClientErrorException.NotFound.class)))
    })
    public ResponseEntity<List<Long>> createHostEvents(Authentication authentication, @RequestBody List<CreateHostEventDto> eventsDtos) {
        PrincipalDetails user = (PrincipalDetails) authentication.getPrincipal();
        for (CreateHostEventDto eventDto: eventsDtos) {
            if (
                eventDto.getEventId() == null ||
                    eventDto.getTitle() == null ||
                    eventDto.getRatio() == null || eventDto.getRatio()<0 || eventDto.getRatio()>100
            ) {
                throw new HttpClientErrorException(HttpStatusCode.valueOf(400));
            }
        }
        List<Long> newEventIds = hostService.createHostEvents(user.getUserId(), eventsDtos);
        return ResponseEntity.status(201).body(newEventIds);
    }

    @PostMapping("/{eventId}/round")
    @Operation(summary = "호스트의 행사 내 회차 생성", description = "호스트의 행사/축제 회차 여러개 등록")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "호스트가 행사/축제 회차를 성공적으로 등록한 경우", content = @Content(array = @ArraySchema(schema = @Schema(implementation = EventDto.class)))),
        @ApiResponse(responseCode = "400", description = "오류가 발생해 호스트가 등록한 행사/축제 회차를 등록하지 못한 경우", content = @Content(schema = @Schema(implementation = HttpClientErrorException.BadRequest.class))),
        @ApiResponse(responseCode = "401", description = "로그인 하지 않은 유저의 요청인 경우", content = @Content(schema = @Schema(implementation = HttpClientErrorException.Unauthorized.class))),
        @ApiResponse(responseCode = "404", description = "호스트나 행사가 존재하지 않는 경우", content = @Content(schema = @Schema(implementation = HttpClientErrorException.NotFound.class)))
    })
    public ResponseEntity<Long> createHostRound(Authentication authentication, @PathVariable Long eventId, @RequestBody CreateHostRoundDto eventDto) {
        PrincipalDetails user = (PrincipalDetails) authentication.getPrincipal();
        if (
            eventDto.getEventId() == null ||
            eventDto.getTitle() == null ||
            eventDto.getRound() == null ||
            eventDto.getRatio() == null || eventDto.getRatio()<0 || eventDto.getRatio()>100
        ) {
            throw new HttpClientErrorException(HttpStatusCode.valueOf(400));
        }
        List<CreateHostRoundDto> eventsDtos = new ArrayList<>();
        eventsDtos.add(eventDto);
        Long newRoundId = hostService.createHostRounds(user.getUserId(), eventId, eventsDtos).get(0);
        return ResponseEntity.status(201).body(newRoundId);
    }

    @PostMapping("/{eventId}/rounds")
    @Operation(summary = "호스트의 행사 내 회차 여러개 생성", description = "호스트의 행사/축제 회차 여러개 등록")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "호스트가 행사/축제 회차를 성공적으로 등록한 경우", content = @Content(array = @ArraySchema(schema = @Schema(implementation = EventDto.class)))),
        @ApiResponse(responseCode = "400", description = "오류가 발생해 호스트가 등록한 행사/축제 회차를 등록하지 못한 경우", content = @Content(schema = @Schema(implementation = HttpClientErrorException.BadRequest.class))),
        @ApiResponse(responseCode = "401", description = "로그인 하지 않은 유저의 요청인 경우", content = @Content(schema = @Schema(implementation = HttpClientErrorException.Unauthorized.class))),
        @ApiResponse(responseCode = "404", description = "호스트나 행사가 존재하지 않는 경우", content = @Content(schema = @Schema(implementation = HttpClientErrorException.NotFound.class)))
    })
    public ResponseEntity<List<Long>> createHostRounds(Authentication authentication, @PathVariable Long eventId, @RequestBody List<CreateHostRoundDto> eventDtos) {
        PrincipalDetails user = (PrincipalDetails) authentication.getPrincipal();
        for (CreateHostRoundDto eventDto: eventDtos) {
            if (
                eventDto.getEventId() == null ||
                eventDto.getTitle() == null ||
                eventDto.getRound() == null ||
                eventDto.getRatio() == null || eventDto.getRatio()<0 || eventDto.getRatio()>100
            ) {
                throw new HttpClientErrorException(HttpStatusCode.valueOf(400));
            }
        }
        List<Long> newRoundIds = hostService.createHostRounds(user.getUserId(), eventId, eventDtos);
        return ResponseEntity.status(201).body(newRoundIds);
    }
}
