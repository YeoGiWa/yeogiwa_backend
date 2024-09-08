package com.example.yeogiwa.domain.point;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

@RestController
@RequestMapping("/point")
@ResponseBody
@RequiredArgsConstructor
@Tag(name = "🪙 포인트 API", description = "포인트 관련 API")
public class PointController {
    private final PointService pointService;

    @GetMapping("/list")
    @Operation(summary = "포인트 목록 조회", description = "해당 유저의 point를 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "포인트 목록을 성공적으로 조회한 경우"),
            @ApiResponse(responseCode = "400", description = "오류가 발생해 포인트 목록을 조회하지 못한 경우", content = @Content(schema = @Schema(implementation = HttpClientErrorException.BadRequest.class))),
            @ApiResponse(responseCode = "401", description = "로그인 하지 않은 유저의 요청인 경우", content = @Content(schema = @Schema(implementation = HttpClientErrorException.Unauthorized.class))),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 유저이거나 탈퇴한 유저인 경우", content = @Content(schema = @Schema(implementation = HttpClientErrorException.NotFound.class)))
    })
    public ResponseEntity<List<PointEntity>> getPointList() {
        return ResponseEntity.status(200).body(null);
    }
}
