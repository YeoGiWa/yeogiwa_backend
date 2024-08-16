package com.example.yeogiwa.service.ambassador.controller;

import com.example.yeogiwa.service.ambassador.controller.request.CreateAmbassadorRequest;
import com.example.yeogiwa.service.ambassador.entity.AmbassadorEntity;
import com.example.yeogiwa.service.ambassador.service.AmbassadorService;
import com.example.yeogiwa.service.event.entity.EventEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/ambassador")
@ResponseBody
@RequiredArgsConstructor
public class AmbassadorController {
    private final AmbassadorService ambassadorService;

    @GetMapping
    @Operation(summary = "앰배서더 조회", description = "해당 앰배서더의 정보 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "앰배서더 정보를 성공적으로 조회한 경우", content = @Content(schema = @Schema(implementation = AmbassadorEntity.class))),
            @ApiResponse(responseCode = "400", description = "오류가 발생해 앰배서더 정보를 조회하지 못한 경우", content = @Content(schema = @Schema(implementation = HttpClientErrorException.BadRequest.class))),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 앰배서더인 경우", content = @Content(schema = @Schema(implementation = HttpClientErrorException.NotFound.class)))
    })
    public ResponseEntity<AmbassadorEntity> getAmbassador(@RequestParam UUID id) {
        AmbassadorEntity ambassador = ambassadorService.getAmbassadorById(id);

        return ResponseEntity.status(200).body(ambassador);
    }

    @GetMapping("/events")
    @Operation(summary = "앰배서더 홍보 목록 조회", description = "해당 유저가 홍보하고 있는 행사/축제 목록 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "유저가 홍보중인 행사/축제 목록을 성공적으로 조회한 경우", content = @Content(schema = @Schema(implementation = EventEntity.class))),
            @ApiResponse(responseCode = "400", description = "오류가 발생해 유저가 홍보중인 행사/축제 목록을 조회하지 못한 경우", content = @Content(schema = @Schema(implementation = HttpClientErrorException.BadRequest.class))),
            @ApiResponse(responseCode = "401", description = "로그인 하지 않은 유저의 요청인 경우", content = @Content(schema = @Schema(implementation = HttpClientErrorException.Unauthorized.class))),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 유저이거나 탈퇴한 유저인 경우", content = @Content(schema = @Schema(implementation = HttpClientErrorException.NotFound.class)))
    })
    public ResponseEntity<List<EventEntity>> getUserAmbassadorList() {
        // TODO: 토큰에서 가져오기
        String email = "";

        List<EventEntity> events = ambassadorService.listEventsByAmbassador(email);

        return ResponseEntity.status(200).body(events);
    }

    @GetMapping("/list")
    @Operation(summary = "앰배서더 목록 조회", description = "해당 행사/축제의 앰배서더 목록 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "행사/축제의 앰배서더 목록을 성공적으로 조회한 경우", content = @Content(schema = @Schema(implementation = AmbassadorEntity.class))),
            @ApiResponse(responseCode = "400", description = "오류가 발생해 행사/축제의 앰배서더 목록을 조회하지 못한 경우", content = @Content(schema = @Schema(implementation = HttpClientErrorException.BadRequest.class))),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 행사/축제인 경우", content = @Content(schema = @Schema(implementation = HttpClientErrorException.NotFound.class)))
    })
    public ResponseEntity<List<AmbassadorEntity>> getAmbassadorList(@RequestParam String eventId) {
        List<AmbassadorEntity> ambassadors = ambassadorService.listAmbassadorsByEvent(eventId);

        return ResponseEntity.status(200).body(ambassadors);
    }

    @PostMapping
    @Operation(summary = "앰배서더 등록", description = "행사/축제의 앰배서더로 등록")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "앰배서더로 등록 성공", content = @Content(schema = @Schema(implementation = AmbassadorEntity.class))),
            @ApiResponse(responseCode = "400", description = "앰배서더로 등록 실패", content = @Content(schema = @Schema(implementation = HttpClientErrorException.BadRequest.class))),
            @ApiResponse(responseCode = "401", description = "로그인 하지 않은 유저의 요청인 경우", content = @Content(schema = @Schema(implementation = HttpClientErrorException.Unauthorized.class))),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 행사/축제인 경우", content = @Content(schema = @Schema(implementation = HttpClientErrorException.NotFound.class)))
    })
    public ResponseEntity<AmbassadorEntity> registerAmbassador(@RequestBody CreateAmbassadorRequest request) {
        // TODO: 토큰에서 가져오기
        String email = "";

        AmbassadorEntity ambassador = ambassadorService.createAmbassador(email, request);

        return ResponseEntity.status(200).body(ambassador);
    }
}
