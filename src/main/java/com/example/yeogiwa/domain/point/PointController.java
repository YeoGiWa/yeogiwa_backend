package com.example.yeogiwa.domain.point;

import com.example.yeogiwa.auth.jwt.JwtUtil;
import com.example.yeogiwa.domain.point.dto.PointDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

@RestController
@Tag(name = "🪙 포인트 API", description = "포인트 관련 API")
@RequestMapping("/point")
@RequiredArgsConstructor
public class PointController {

    private final PointService pointService;
    private final JwtUtil jwtUtil;

    @Operation(summary = "유저의 포인트 내역 조회", description = "유저의 적립 및 사용 내역을 10개씩 반환합니다.(무한 스크롤), amount가 양수면 적립, 음수면 사용을 의미합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "포인트 내역을 정상적으로 반환", content = {
                    @Content(array = @ArraySchema(schema = @Schema (implementation = PointDto.class)))
            }),
            @ApiResponse(responseCode = "204", description = "포인트 내역이 존재하지 않음", content = @Content),
            @ApiResponse(responseCode = "404", description = "해당 유저를 찾을 수 없음", content = {
                    @Content(schema = @Schema(implementation = HttpClientErrorException.NotFound.class))
            })
    })
    @GetMapping("/list")
    public ResponseEntity<Page<PointDto>> getPointList(
            @RequestParam(defaultValue = "0") int pageNo, HttpServletRequest request) {

        String token = jwtUtil.substringToken(request.getHeader("Authorization"));

        Pageable pageable = PageRequest.of(pageNo, 10, Sort.by("createdAt").descending());

        Page<PointDto> pointList = pointService.getPointList(token, pageable);

        if (pointList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.status(200).body(pointList);
    }

    @Operation(summary = "유저의 총 포인트 조회", description = "유저의 총 포인트 합계를 반환 합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "총 포인트를 정상적으로 반환", content = {
                    @Content(schema = @Schema(implementation = Integer.class))
            }),
            @ApiResponse(responseCode = "404", description = "해당 유저를 찾을 수 없음", content = {
                    @Content(schema = @Schema(implementation = HttpClientErrorException.NotFound.class))
            })
    })
    @GetMapping("/total")
    public ResponseEntity<Integer> getTotalPoints(HttpServletRequest request) {

        String token = jwtUtil.substringToken(request.getHeader("Authorization"));

        Integer totalPoints = pointService.getTotalPoints(token);

        return new ResponseEntity<>(totalPoints == null ? 0 : totalPoints, HttpStatus.OK);
    }
}
