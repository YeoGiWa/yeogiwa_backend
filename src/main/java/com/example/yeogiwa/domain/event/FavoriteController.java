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

    @Operation(summary = "즐겨찾기 추가", description = "사용자와 이벤트 정보를 받아 즐겨찾기 추가")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "즐겨찾기 추가 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "404", description = "사용자 또는 이벤트를 찾을 수 없음")
    })
    @PostMapping("/add")
    public FavoriteEntity addFavorite(@RequestParam Long userId, @RequestParam String eventId) {
        return favoriteService.addFavorite(userId, eventId);
    }

    @Operation(summary = "즐겨찾기 삭제", description = "사용자와 이벤트 ID를 받아 해당 즐겨찾기 삭제")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "즐겨찾기 삭제 성공"),
            @ApiResponse(responseCode = "404", description = "즐겨찾기를 찾을 수 없음")
    })
    @DeleteMapping("/remove")
    public void removeFavorite(@RequestParam Long userId, @RequestParam String eventId) {
        favoriteService.removeFavorite(userId, eventId);
    }

    @Operation(summary = "즐겨찾기 목록 조회", description = "사용자 ID를 받아 해당 사용자의 즐겨찾기 목록 조회")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "즐겨찾기 목록 조회 성공"),
            @ApiResponse(responseCode = "404", description = "사용자를 찾을 수 없음")
    })
    @GetMapping("/user/{userId}")
    public List<FavoriteEntity> getFavoritesByUser(@PathVariable Long userId) {
        return favoriteService.getFavoritesByUser(userId);
    }
}

