package com.example.yeogiwa.domain.host;

import com.example.yeogiwa.domain.host.dto.CreateHostRequest;
import com.example.yeogiwa.domain.host.dto.HostDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

@RestController
@RequestMapping("/host")
@ResponseBody
@RequiredArgsConstructor
@Tag(name = "🦹‍ 호스트 API", description = "호스트 관련 API")
public class HostController {
    private final HostService hostService;

    // 유저가 관리자 신청하면 운영자가 확인 후 호스트로 등록 -> 심사를 위해 관리자 신청시 바로 호스트로 등록
    @PostMapping
    @Operation(summary = "호스트 등록", description = "행사/축제의 호스트로 등록")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "호스트로 등록 성공", content = @Content(schema = @Schema(implementation = HostEntity.class))),
        @ApiResponse(responseCode = "400", description = "호스트로 등록 실패", content = @Content(schema = @Schema(implementation = HttpClientErrorException.BadRequest.class))),
        @ApiResponse(responseCode = "401", description = "로그인 하지 않은 유저의 요청인 경우", content = @Content(schema = @Schema(implementation = HttpClientErrorException.Unauthorized.class))),
        @ApiResponse(responseCode = "404", description = "존재하지 않는 행사/축제인 경우", content = @Content(schema = @Schema(implementation = HttpClientErrorException.NotFound.class)))
    })
    public ResponseEntity<HostDto> registerHost(@RequestBody CreateHostRequest request) {
        // TODO: 토큰에서 가져오기
        String email = "test@gmail.com";

        HostDto host = hostService.createHost(email, request);

        return ResponseEntity.status(200).body(host);
    }
}
