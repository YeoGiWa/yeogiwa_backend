package com.example.yeogiwa.domain.favorite;

import com.example.yeogiwa.auth.oauth.PrincipalDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.Explode;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.enums.ParameterStyle;
import io.swagger.v3.oas.annotations.media.*;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Null;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/favorite")
@RequiredArgsConstructor
@Tag(name = "⭐️ 즐겨찾기 API", description = "즐겨찾기 관련 기능을 제공하는 API")
public class FavoriteController {

    private final FavoriteService favoriteService;

    @PostMapping("/event/{eventId}")
    @Operation(summary = "특정 축제의 즐겨찾기 생성", description = "해당 행사/축제의 즐겨찾기 생성")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "행사의 앰배서더를 성공적으로 생성한 경우", content = @Content(schema = @Schema(implementation = UUID.class))),
        @ApiResponse(responseCode = "400", description = "오류가 발생해 행사의 앰배서더를 생성하지 못한 경우", content = @Content(schema = @Schema(implementation = Null.class))),
        @ApiResponse(responseCode = "401", description = "로그인하지 않은 사용자인 경우", content = @Content(schema = @Schema(implementation = Null.class))),
        @ApiResponse(responseCode = "404", description = "해당 ID의 행사가 존재하지 않는 경우", content = @Content(schema = @Schema(implementation = Null.class))),
        @ApiResponse(responseCode = "409", description = "이미 해당 행사를 즐겨찾기 한 경우", content = @Content(schema = @Schema(implementation = Null.class)))
    })
    public ResponseEntity<UUID> createFavorite(Authentication authentication, @Schema(example = "2541883") @PathVariable Long eventId) {
        PrincipalDetails user = (PrincipalDetails) authentication.getPrincipal();
        UUID newFavoriteId = favoriteService.addFavorite(user.getUserId(), eventId);
        return ResponseEntity.status(201).body(newFavoriteId);
    }

    @DeleteMapping("/event/{eventId}")
    @Operation(summary = "특정 축제의 즐겨찾기 삭제", description = "해당 행사/축제의 즐겨찾기 제거")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "행사의 앰배서더를 성공적으로 제거한 경우", content = @Content(schema = @Schema(implementation = Null.class))),
        @ApiResponse(responseCode = "400", description = "오류가 발생해 행사의 앰배서더를 생성하지 못한 경우", content = @Content(schema = @Schema(implementation = Null.class))),
        @ApiResponse(responseCode = "401", description = "로그인하지 않은 사용자인 경우", content = @Content(schema = @Schema(implementation = Null.class))),
        @ApiResponse(responseCode = "404", description = "해당 ID의 행사가 존재하지 않는 경우", content = @Content(schema = @Schema(implementation = Null.class))),
    })
    public ResponseEntity<Null> removeFavorite(Authentication authentication, @Schema(example = "2541883") @PathVariable Long eventId) {
        PrincipalDetails user = (PrincipalDetails) authentication.getPrincipal();
        favoriteService.removeFavorite(user.getUserId(), eventId);
        return ResponseEntity.status(200).body(null);
    }

    @Operation(summary = "이벤트의 즐겨찾기 여부 조회", description = "사용자가 해당 이벤트에 즐겨찾기를 했는지 여부 조회")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "즐겨찾기 여부 목록 조회 성공", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Boolean.class))))
    })
    @GetMapping("/event")
    public ResponseEntity<Boolean> getIsFavorite(Authentication authentication, @Schema(example = "2541883") @RequestParam Long eventId) {
        PrincipalDetails user = (PrincipalDetails) authentication.getPrincipal();
        Boolean isFavorite = favoriteService.getIsFavorite(user.getUserId(), eventId);
        return ResponseEntity.status(200).body(isFavorite);
    }


    @Operation(summary = "이벤트들의 즐겨찾기 여부 조회", description = "사용자가 해당 이벤트들에 즐겨찾기를 했는지 여부 조회", parameters = {
            @Parameter(in = ParameterIn.QUERY, name = "eventIds", explode = Explode.FALSE, style = ParameterStyle.SIMPLE)
    })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "즐겨찾기 여부 목록 조회 성공", content = @Content(schemaProperties = {
                @SchemaProperty(name = "eventId", schema = @Schema(implementation = Boolean.class))
            }))
    })
    @GetMapping(value = "/events")
    public ResponseEntity<Map<Long, Boolean>> getIsFavorites(
        Authentication authentication,
        @Parameter(
            example ="[2541883, 1234567]",
            array =  @ArraySchema(schema = @Schema(implementation = Long.class)))
        @RequestParam List<Long> eventIds
    ) {
        PrincipalDetails user = (PrincipalDetails) authentication.getPrincipal();
        Map<Long, Boolean> isFavorites = favoriteService.getIsFavorites(user.getUserId(), eventIds);
        return ResponseEntity.status(200).body(isFavorites);
    }
}

