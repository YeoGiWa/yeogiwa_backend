package com.example.yeogiwa.domain.ambassador;

import com.example.yeogiwa.auth.oauth.PrincipalDetails;
import com.example.yeogiwa.domain.ambassador.dto.AmbassadorDto;
import com.example.yeogiwa.domain.event.dto.EventDto;
import io.swagger.v3.oas.annotations.Operation;
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

@RestController
@RequestMapping("/ambassador")
@ResponseBody
@RequiredArgsConstructor
@Tag(name = "🤵‍ 앰배서더 API", description = "앰배서더 관련 API")
public class AmbassadorController {
    private final AmbassadorService ambassadorService;

    @PostMapping("/event/{eventId}")
    @Operation(summary = "특정 행사의 앰배서더 생성", description = "해당 행사의 앰배서더 생성")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "행사의 앰배서더를 성공적으로 생성한 경우", content = @Content(schema = @Schema(implementation = Long.class))),
        @ApiResponse(responseCode = "400", description = "오류가 발생해 행사의 앰배서더를 생성하지 못한 경우", content = @Content(schema = @Schema(implementation = Null.class))),
        @ApiResponse(responseCode = "401", description = "로그인하지 않은 사용자인 경우", content = @Content(schema = @Schema(implementation = Null.class))),
        @ApiResponse(responseCode = "403", description = "해당 행사가 활성화되지 않은 경우", content = @Content(schema = @Schema(implementation = Null.class))),
        @ApiResponse(responseCode = "404", description = "해당 ID의 행사가 존재하지 않는 경우", content = @Content(schema = @Schema(implementation = Null.class))),
        @ApiResponse(responseCode = "409", description = "이미 해당 행사의 앰배서더가 된 경우", content = @Content(schema = @Schema(implementation = Null.class)))
    })
    public ResponseEntity<Long> createAmbassador(Authentication authentication, @PathVariable Long eventId) {
        PrincipalDetails user = (PrincipalDetails) authentication.getPrincipal();
        Long newAmbassadorId = ambassadorService.createAmbassador(user.getUserId(), eventId);
        return ResponseEntity.status(201).body(newAmbassadorId);
    }

    @GetMapping("/{ambassadorId}")
    @Operation(summary = "앰배서더 조회", description = "해당 앰배서더의 정보 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "앰배서더 정보를 성공적으로 조회한 경우", content = @Content(schema = @Schema(implementation = AmbassadorEntity.class))),
            @ApiResponse(responseCode = "400", description = "오류가 발생해 앰배서더 정보를 조회하지 못한 경우", content = @Content(schema = @Schema(implementation = Null.class))),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 앰배서더인 경우", content = @Content(schema = @Schema(implementation = Null.class)))
    })
    public ResponseEntity<AmbassadorDto> getAmbassador(@PathVariable Long ambassadorId) {
        AmbassadorDto ambassador = ambassadorService.getAmbassadorById(ambassadorId);

        return ResponseEntity.status(200).body(ambassador);
    }

    @GetMapping("/events")
    @Operation(summary = "앰배서더 홍보 목록 조회", description = "해당 유저가 홍보하고 있는 행사/축제 목록 조회")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "유저가 홍보중인 행사/축제 목록을 성공적으로 조회한 경우", content = @Content(array = @ArraySchema(schema = @Schema(implementation = EventDto.class)))),
        @ApiResponse(responseCode = "400", description = "오류가 발생해 유저가 홍보중인 행사/축제 목록을 조회하지 못한 경우", content = @Content(schema = @Schema(implementation = Null.class))),
        @ApiResponse(responseCode = "401", description = "로그인 하지 않은 유저의 요청인 경우", content = @Content(schema = @Schema(implementation = Null.class))),
        @ApiResponse(responseCode = "404", description = "존재하지 않는 유저이거나 탈퇴한 유저인 경우", content = @Content(schema = @Schema(implementation = Null.class)))
    })
    public ResponseEntity<?> getAmbassadorsOfEvent(Authentication authentication) {
        return null;
    }
}
