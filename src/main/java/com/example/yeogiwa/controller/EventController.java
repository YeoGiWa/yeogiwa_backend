package com.example.yeogiwa.controller;

import com.example.yeogiwa.entity.EventEntity;
import com.example.yeogiwa.service.EventService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.constraints.Null;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/event")
@ResponseBody
@RequiredArgsConstructor
public class EventController {
    private final EventService eventService;

    @GetMapping("/{event_id}")
    @Operation(summary = "특정 행사/축제의 정보 조회", description = "특정 행사/축제의 상세 정보를 반환")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "행사/축제의 정보를 정상적으로 반환", content = @Content(schema = @Schema(implementation = EventEntity.class))),
            @ApiResponse(responseCode = "400", description = "오류로 인해 행사/축제의 정보를 반환하지 못함", content = @Content(schema = @Schema(implementation = HttpClientErrorException.BadRequest.class))),
            @ApiResponse(responseCode = "404", description = "해당 event_id를 갖는 행사가 존재하지 않음", content = @Content(schema = @Schema(implementation = HttpClientErrorException.NotFound.class)))
    })
    public ResponseEntity<EventEntity> getEvent(@PathVariable UUID event_id) {
        return ResponseEntity.status(200).body(null);
    }


    @GetMapping("/list")
    @Operation(summary = "행사/축제 목록 조회", description = "검색창에 보여질 행사/축제 목록을 반환")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "행사/축제 목록을 정상적으로 반환한 경우"),
            @ApiResponse(responseCode = "400", description = "에러가 발생해 행사/축제 목록을 반환하지 못한 경우", content = @Content(schema = @Schema(implementation = HttpClientErrorException.BadRequest.class)))
    })
    @Parameters({
            @Parameter(name = "searchBy", description = "어떤 검색어를 포함할 지"),
            @Parameter(name = "limit", description = "상위 몇 개를 보일지"),
            @Parameter(name = "sortBy", description = "어떤 속성을 기준으로 정렬할 지[날짜 ASC/DESC]"),
            @Parameter(name = "filterBy", description = "필터링 할 속성[날짜/지역]")
    })
    public ResponseEntity<List<EventEntity>> getEventList() {
        return ResponseEntity.status(200).body(null);
    }

    @GetMapping("/nearby/list")
    @Operation(summary = "근처 행사/축제 목록 조회", description = "특정 장소 근처에 있는 행사/축제 목록을 반환")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "행사/축제 목록을 정상적으로 반환한 경우"),
            @ApiResponse(responseCode = "400", description = "에러가 발생해 행사/축제 목록을 반환하지 못한 경우", content = @Content(schema = @Schema(implementation = HttpClientErrorException.BadRequest.class)))
    })
    @Parameters({
            @Parameter(name = "latitude", required = true, description = "특정 장소의 위도"),
            @Parameter(name = "longitude", required = true, description = "특정 장소의 경도"),
            @Parameter(name = "bound", description = "주변 반경 어디까지 표시할 지")
    })
    public ResponseEntity<List<EventEntity>> getNearbyEventList() {
        return ResponseEntity.status(200).body(null);
    }

}
