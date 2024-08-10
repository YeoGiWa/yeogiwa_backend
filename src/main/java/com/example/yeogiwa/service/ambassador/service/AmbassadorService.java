package com.example.yeogiwa.service.ambassador.service;

import com.example.yeogiwa.entity.UserEntity;
import com.example.yeogiwa.repository.UserRepository;
import com.example.yeogiwa.service.ambassador.controller.request.CreateAmbassadorRequest;
import com.example.yeogiwa.service.ambassador.entity.AmbassadorEntity;
import com.example.yeogiwa.service.ambassador.repository.AmbassadorRepository;
import com.example.yeogiwa.service.event.controller.response.ListEventResponse;
import com.example.yeogiwa.service.event.entity.EventEntity;
import com.example.yeogiwa.service.event.repository.EventRepository;
import com.example.yeogiwa.service.event.service.EventService;
import jdk.jfr.Event;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AmbassadorService {
    private final AmbassadorRepository ambassadorRepository;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    @Transactional
    public AmbassadorEntity createAmbassador(String email, CreateAmbassadorRequest request) {
        UserEntity user = userRepository.findByEmail(email);

        EventEntity event = eventRepository.findById(request.getEventId())
                .orElseThrow(() -> new RuntimeException("Event not found"));

        AmbassadorEntity ambassador = AmbassadorEntity.builder()
                .user(user)
                .event(event)
                .qr(null)
                .createdAt(new Date())
                .build();

        return ambassadorRepository.save(ambassador);
    }

    public AmbassadorEntity getAmbassadorById(UUID id) {
        return ambassadorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ambassador not found"));
    }

    public List<AmbassadorEntity> listAmbassadorsByEvent(String eventId) {
        EventEntity event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        return ambassadorRepository.findAllByEvent(event);
    }

    public List<EventEntity> listEventsByAmbassador(String email) {
        UserEntity user = userRepository.findByEmail(email);
        List<AmbassadorEntity> ambassadors = ambassadorRepository.findAllByUser(user);
        List<String> eventIds = ambassadors.stream().map(ambassador -> ambassador.getEvent().getId()).toList();

        List<EventEntity> events = eventRepository.findAllByIdIn(eventIds);

        return events;
    }
}
