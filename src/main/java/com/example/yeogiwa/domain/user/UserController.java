package com.example.yeogiwa.domain.user;

import com.example.yeogiwa.auth.jwt.JwtUtil;
import com.example.yeogiwa.auth.oauth.PrincipalDetails;
import com.example.yeogiwa.domain.user.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Null;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/user")
@ResponseBody
@RequiredArgsConstructor
@Tag(name = "👤 유저 API", description = "유저 관련 API")
@Slf4j
public class UserController {
    private final UserService userService;
    private final JwtUtil jwtUtil;

    @GetMapping("/")
    @Operation(summary = "유저 정보 조회", description = "유저의 상세 정보를 반환")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "유저의 상세 정보를 정상적으로 반환한 경우"),
            @ApiResponse(responseCode = "400", description = "에러가 발생해 유저의 상세 정보를 반환하지 못한 경우", content = @Content(schema = @Schema(implementation = HttpClientErrorException.BadRequest.class))),
            @ApiResponse(responseCode = "401", description = "로그인 하지 않은 유저의 요청인 경우", content = @Content(schema = @Schema(implementation = HttpClientErrorException.Unauthorized.class))),
            @ApiResponse(responseCode = "404", description = "이미 탈퇴한 유저인 경우", content = @Content(schema = @Schema(implementation = HttpClientErrorException.NotFound.class)))
    })
    public ResponseEntity<UserDto> getUser(Authentication authentication) {
        PrincipalDetails user = (PrincipalDetails) authentication.getPrincipal();
        Optional<UserEntity> opUser = userService.getUser(user.getUserId());
        UserDto userDto = UserDto.from(opUser);
        return ResponseEntity.status(200).body(userDto);
    }

    @PostMapping("/login")
    @Operation(summary = "로그인 및 회원가입", description = "")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "로그인 성공", content = @Content(schema = @Schema(implementation = Long.class))),
        @ApiResponse(responseCode = "201", description = "로그인 및 회원가입 성공", content = @Content(schema = @Schema(implementation = Long.class))),
        @ApiResponse(responseCode = "401", description = "잘못된 registration access 토큰", content = @Content(schema = @Schema(implementation = HttpClientErrorException.Unauthorized.class)))
    })
    public ResponseEntity<Long> login(@RequestBody LoginDto loginDto) {

        /* Check if token is valid */
        switch (loginDto.getRegistration()) {
            case "kakao": {
                RestTemplate request = new RestTemplate();
                String url = "https://kapi.kakao.com/v1/user/access_token_info";
                HttpHeaders requestHeader = new HttpHeaders();
                requestHeader.add(HttpHeaders.AUTHORIZATION, "Bearer " + new String(Base64.getDecoder().decode(loginDto.getToken())));
                try {
                    ResponseEntity<KakaoDto> kakaoDto = request.exchange(
                        url,
                        HttpMethod.GET,
                        new HttpEntity<>(requestHeader),
                        KakaoDto.class
                    );
                } catch (HttpClientErrorException e) {
                    // if token is not valid, return 401
                    return ResponseEntity.status(401).body(null);
                }
                break;
            }
            case "apple": {
                RestTemplate request = new RestTemplate();
                String url = "https://appleid.apple.com/auth/oauth2/v2/token";
                HttpHeaders requestHeader = new HttpHeaders();
                requestHeader.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
                String appleClientSecret = jwtUtil.createAppleClientSecret();
                AppleRequestBodyDto requestBody = new AppleRequestBodyDto(jwtUtil.getClientId(), appleClientSecret, loginDto.getToken());
                log.info("apple token: {}", requestBody.getRefresh_token());
                try {
                    ResponseEntity<AppleDto> appleDto = request.exchange(
                        url,
                        HttpMethod.POST,
                        new HttpEntity<>(requestBody, requestHeader),
                        AppleDto.class
                    );
                } catch (HttpClientErrorException e) {
                    // if token is not valid, return 401
                    return ResponseEntity.status(401).body(null);
                }
            }
            default: return ResponseEntity.status(401).body(null);
        }

        /* Do login */
        LoginResponseDto result = userService.login(loginDto);

        /* Set header & cookie */
        HttpHeaders headers = new HttpHeaders();
        ResponseCookie responseCookie = ResponseCookie.from(
            "refresh",
                Base64.getEncoder().encodeToString(result.getRefreshToken().getBytes())
            )
            .path("/")
            .httpOnly(true)
            .secure(true)
            .maxAge(14 * 24 * 60 * 60 * 1000L) // 2 weeks
            .build();
        headers.add(HttpHeaders.AUTHORIZATION, result.getAccessToken());
        headers.add(HttpHeaders.SET_COOKIE, responseCookie.toString());

        return ResponseEntity.status(result.getStatus()).headers(headers).body(result.getUserId());
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
        PrincipalDetails user = (PrincipalDetails) authentication.getPrincipal();
        userService.deleteUser(user.getUserId());
        return ResponseEntity.status(200).body(null);
    }
}
