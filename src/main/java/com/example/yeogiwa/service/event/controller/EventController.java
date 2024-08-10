package com.example.yeogiwa.service.event.controller;

import com.example.yeogiwa.enums.Region;
import com.example.yeogiwa.enums.Sort;
import com.example.yeogiwa.service.event.controller.request.CreateEventRequest;
import com.example.yeogiwa.service.event.controller.request.UpdateEventRequest;
import com.example.yeogiwa.service.event.controller.response.GetEventResponse;
import com.example.yeogiwa.service.event.controller.response.ListEventResponse;
import com.example.yeogiwa.service.event.dto.EventDto;
import com.example.yeogiwa.service.event.entity.EventEntity;
import com.example.yeogiwa.service.event.service.EventService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    @Operation(summary = "특정 이벤트 정보 조회", description = "특정 이벤트의 상세 정보를 반환")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "이벤트의 정보를 정상적으로 반환", content = @Content(schema = @Schema(implementation = EventEntity.class))),
        @ApiResponse(responseCode = "400", description = "오류로 인해 이벤트의 정보를 반환하지 못함", content = @Content(schema = @Schema(implementation = HttpClientErrorException.BadRequest.class))),
        @ApiResponse(responseCode = "404", description = "해당 event_id를 갖는 행사가 존재하지 않음", content = @Content(schema = @Schema(implementation = HttpClientErrorException.NotFound.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<GetEventResponse> getEventById(@PathVariable(value = "id") String id) {
        GetEventResponse event = eventService.getEventById(id);

        return ResponseEntity.status(200).body(event);
    }

    @Operation(summary = "이벤트 정보 검색", description = "검색한 이벤트들의 상세 정보를 반환")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "이벤트의 정보를 정상적으로 반환", content = @Content(schema = @Schema(implementation = EventEntity.class))),
        @ApiResponse(responseCode = "400", description = "오류로 인해 이벤트의 정보를 반환하지 못함", content = @Content(schema = @Schema(implementation = HttpClientErrorException.BadRequest.class))),
    })
    @GetMapping("/list")
    public ResponseEntity<ListEventResponse> listEvents(@RequestParam("numOfRows") int numOfRows, @RequestParam("pageNo") int pageNo, @RequestParam("sort") Sort sort, @RequestParam("region") Region region, @RequestParam(name = "keyword", required = false) String keyword, @RequestParam(name = "eventStartDate", required = false) String eventStartDate, @RequestParam(name = "eventEndDate", required = false) String eventEndDate) {
        if(keyword == null) {
            if (eventStartDate == null || eventStartDate.length() != 8 || (eventEndDate != null && eventEndDate.length() != 8) || (eventEndDate != null && eventStartDate.compareTo(eventEndDate) > 0)) {
                return ResponseEntity.status(400).body(null);
            }
        }

        ListEventResponse event = eventService.listEvents(numOfRows, pageNo, sort, region, keyword, eventStartDate, eventEndDate);

        return ResponseEntity.status(200).body(event);
    }

    @Operation(summary = "근처 이벤트 정보 검색", description = "특정 장소 근처에 있는 이벤트들의 상세 정보를 반환")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "이벤트의 정보를 정상적으로 반환", content = @Content(schema = @Schema(implementation = EventEntity.class))),
        @ApiResponse(responseCode = "400", description = "오류로 인해 이벤트의 정보를 반환하지 못함", content = @Content(schema = @Schema(implementation = HttpClientErrorException.BadRequest.class)))
    })
    @GetMapping("/nearby")
    public ResponseEntity<ListEventResponse> listEventsNearby(@RequestParam("numOfRows") int numOfRows, @RequestParam("pageNo") int pageNo, @RequestParam("sort") Sort sort, @RequestParam("mapX") String mapX, @RequestParam("mapY") String mapY, @RequestParam("radius") String radius) {
        ListEventResponse event = eventService.listEventsNearby(numOfRows, pageNo, sort, mapX, mapY, radius);

        return ResponseEntity.status(200).body(event);
    }

    // 호스트 혹은 관리자 전용
    @Operation(summary = "이벤트 생성", description = "축제 API의 id로 새로운 이벤트를 생성")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "이벤트를 정상적으로 생성", content = @Content(schema = @Schema(implementation = EventEntity.class))),
        @ApiResponse(responseCode = "400", description = "오류로 인해 이벤트를 생성하지 못함", content = @Content(schema = @Schema(implementation = HttpClientErrorException.BadRequest.class)))
    })
    @PostMapping
    public ResponseEntity<EventDto> createEvent(@RequestBody CreateEventRequest request) {
        EventDto createdEventDto = eventService.createEvent(request);

        return ResponseEntity.status(200).body(createdEventDto);
    }

    // 호스트 혹은 관리자 전용
    @Operation(summary = "이벤트 수정", description = "이벤트의 정보를 수정")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "이벤트를 정상적으로 수정", content = @Content(schema = @Schema(implementation = EventEntity.class))),
        @ApiResponse(responseCode = "400", description = "오류로 인해 이벤트를 수정하지 못함", content = @Content(schema = @Schema(implementation = HttpClientErrorException.BadRequest.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<EventDto> updateEvent(@PathVariable String id, @RequestBody UpdateEventRequest request) {
        EventDto updatedEventDto = eventService.updateEvent(id, request);

        return ResponseEntity.status(200).body(updatedEventDto);
    }

    // TODO: 삭제가 필요할까? 비즈니스 적으로 삭제는 없어야 하지 않을까?
}