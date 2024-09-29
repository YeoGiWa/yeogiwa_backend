package com.example.yeogiwa.domain.favorite;

import com.example.yeogiwa.auth.oauth.PrincipalDetails;
import com.example.yeogiwa.domain.favorite.dto.FavoriteDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/favorite")
@RequiredArgsConstructor
@Tag(name = "⭐️ 즐겨찾기 API", description = "즐겨찾기 관련 기능을 제공하는 API")
public class FavoriteController {

    private final FavoriteService favoriteService;

    @Operation(summary = "즐겨찾기 추가", description = "사용자와 이벤트 정보를 받아 즐겨찾기 추가, 즐겨찾기 ID 반환")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "즐겨찾기 추가 성공", content = @Content(schema = @Schema(implementation = Long.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "404", description = "사용자 또는 이벤트를 찾을 수 없음", content = @Content(schema = @Schema(implementation = String.class)))
    })
    @PostMapping()
    public Long addFavorite(Authentication authentication, @RequestParam Long eventId) {
        PrincipalDetails user = (PrincipalDetails) authentication.getPrincipal();
        FavoriteDto favorite = favoriteService.addFavorite(user.getUserId(), eventId);
        return favorite.getId();
    }

    @Operation(summary = "즐겨찾기 삭제", description = "즐겨찾기 ID를 받아 즐겨찾기 삭제")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "즐겨찾기 삭제 성공", content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "404", description = "즐겨찾기를 찾을 수 없음", content = @Content(schema = @Schema(implementation = String.class)))
    })
    @DeleteMapping("/{favoriteId}")
    public void removeFavorite(@PathVariable Long favoriteId) {
        favoriteService.removeFavorite(favoriteId);
    }

    @Operation(summary = "즐겨찾기 목록 조회", description = "사용자의 즐겨찾기 목록 조회")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "즐겨찾기 목록 조회 성공", content = @Content(array = @ArraySchema(schema = @Schema(implementation = FavoriteDto.class)))),
            @ApiResponse(responseCode = "404", description = "사용자를 찾을 수 없음", content = @Content(schema = @Schema(implementation = String.class)))
    })
    @GetMapping("/list")
    public ResponseEntity<List<FavoriteDto>> getFavoritesByUser(Authentication authentication) {
        PrincipalDetails user = (PrincipalDetails) authentication.getPrincipal();
        List<FavoriteDto> favoriteList = favoriteService.getFavoritesByUser(user.getUserId());
        return ResponseEntity.status(200).body(favoriteList);
    }

    @Operation(summary = "즐겨찾기 단일 조회", description = "이벤트 ID를 받아 해당 이벤트의 즐겨찾기 정보를 조회")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "즐겨찾기 단일 조회 성공", content = @Content(schema = @Schema(implementation = FavoriteDto.class))),
            @ApiResponse(responseCode = "404", description = "즐겨찾기를 찾을 수 없음", content = @Content(schema = @Schema(implementation = String.class)))
    })
    @GetMapping("/event/{eventId}")
    public ResponseEntity<FavoriteDto> getFavorite(Authentication authentication, @PathVariable Long eventId) {
        PrincipalDetails user = (PrincipalDetails) authentication.getPrincipal();
        FavoriteDto favorite = favoriteService.getFavorite(user.getUserId(), eventId);
        return ResponseEntity.status(200).body(favorite);
    }
}

