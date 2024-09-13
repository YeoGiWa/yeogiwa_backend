package com.example.yeogiwa.domain.event;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/favorites")
@RequiredArgsConstructor
@Tag(name = "⭐️ 즐겨찾기 API", description = "즐겨찾기 관련 기능을 제공하는 API")
public class FavoriteController {

    private final FavoriteService favoriteService;

    @Operation(summary = "즐겨찾기 추가", description = "호스트와 이벤트 정보를 받아 즐겨찾기를 추가")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "즐겨찾기 추가 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "404", description = "호스트 또는 이벤트를 찾을 수 없음")
    })
    @PostMapping("/add")
    public FavoriteEntity addFavorite(@RequestParam Long hostId, @RequestParam String eventId) {
        return favoriteService.addFavorite(hostId, eventId);
    }

    @Operation(summary = "즐겨찾기 삭제", description = "즐겨찾기 ID를 받아 해당 즐겨찾기를 삭제")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "즐겨찾기 삭제 성공"),
            @ApiResponse(responseCode = "404", description = "즐겨찾기를 찾을 수 없음")
    })
    @DeleteMapping("/remove/{favoriteId}")
    public void removeFavorite(@PathVariable Long favoriteId) {
        favoriteService.removeFavorite(favoriteId);
    }

    @Operation(summary = "즐겨찾기 목록 조회", description = "호스트 ID를 받아 해당 호스트의 즐겨찾기 목록을 조회")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "즐겨찾기 목록 조회 성공"),
            @ApiResponse(responseCode = "404", description = "호스트를 찾을 수 없음")
    })
    @GetMapping("/host/{hostId}")
    public List<FavoriteEntity> getFavoritesByHost(@PathVariable UUID hostId) {
        return favoriteService.getFavoritesByHost(hostId);
    }
}

