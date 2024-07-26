package com.example.yeogiwa.controller;

import com.example.yeogiwa.dto.user.RegisterDTO;
import com.example.yeogiwa.entity.UserEntity;
import com.example.yeogiwa.security.CustomUserDetails;
import com.example.yeogiwa.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.constraints.Null;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.UUID;

@RestController
@RequestMapping("/user")
@ResponseBody
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/")
    @Operation(summary = "유저 정보 조회", description = "유저의 상세 정보를 반환")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "유저의 상세 정보를 정상적으로 반환한 경우"),
            @ApiResponse(responseCode = "400", description = "에러가 발생해 유저의 상세 정보를 반환하지 못한 경우", content = @Content(schema = @Schema(implementation = HttpClientErrorException.BadRequest.class))),
            @ApiResponse(responseCode = "401", description = "로그인 하지 않은 유저의 요청인 경우", content = @Content(schema = @Schema(implementation = HttpClientErrorException.Unauthorized.class))),
            @ApiResponse(responseCode = "404", description = "이미 탈퇴한 유저인 경우", content = @Content(schema = @Schema(implementation = HttpClientErrorException.NotFound.class)))
    })
    public ResponseEntity<UserEntity> getUser(Authentication authentication) {
        CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();
        return ResponseEntity.status(200).body(userService.getUser(user.getUsername()));
    }

    @PostMapping("/register")
    @Operation(summary = "회원가입", description = "새로운 유저 생성")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "새로운 유저를 성공적으로 생성한 경우"),
            @ApiResponse(responseCode = "400", description = "에러가 발생해 유저를 생성하지 못한 경우", content = @Content(schema = @Schema(implementation = HttpClientErrorException.BadRequest.class))),
            @ApiResponse(responseCode = "409", description = "해당 이메일로 이미 가입된 유저가 존재하는 경우", content = @Content(schema = @Schema(implementation = HttpClientErrorException.Conflict.class)))
    })
    public ResponseEntity<UUID> register(@RequestBody RegisterDTO registerDTO) {
        return ResponseEntity.status(201).body(userService.createUser(registerDTO));
    }

    @DeleteMapping("/")
    @Operation(summary = "유저 탈퇴", description = "해당 유저의 isDeleted를 true로 설정(실제로 삭제하지 않음)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "유저가 성공적으로 탈퇴한 경우"),
            @ApiResponse(responseCode = "400", description = "존재하지 않는 유저거나 에러가 발생해 유저가 탈퇴하지 못한 경우", content = @Content(schema = @Schema(implementation = HttpClientErrorException.BadRequest.class))),
            @ApiResponse(responseCode = "401", description = "로그인 하지 않은 유저의 요청인 경우", content = @Content(schema = @Schema(implementation = HttpClientErrorException.Unauthorized.class))),
            @ApiResponse(responseCode = "404", description = "이미 탈퇴한 유저인 경우", content = @Content(schema = @Schema(implementation = HttpClientErrorException.NotFound.class)))
    })
    public ResponseEntity<Null> deleteUser(Authentication authentication) {
        return ResponseEntity.status(200).body(null);
    }
}
