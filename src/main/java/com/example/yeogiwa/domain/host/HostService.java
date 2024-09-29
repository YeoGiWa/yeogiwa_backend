package com.example.yeogiwa.domain.host;

import com.example.yeogiwa.domain.host.dto.CreateHostBody;
import com.example.yeogiwa.domain.host.dto.HostDto;
import com.example.yeogiwa.domain.user.UserEntity;
import com.example.yeogiwa.domain.user.UserRepository;
import com.example.yeogiwa.enums.Role;
import com.example.yeogiwa.openapi.OpenApiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class HostService {
    private final HostRepository hostRepository;
    private final UserRepository userRepository;
    private final OpenApiService openApiService;

    public HostDto createHost(Long userId, CreateHostBody body) {
        Boolean isValidBusiness = openApiService.isValidBusiness(body.getBusinessNumber());

        if (!isValidBusiness) throw new HttpClientErrorException(HttpStatusCode.valueOf(400));

        UserEntity user = userRepository.findById(userId).get();
        HostEntity host = HostEntity.builder()
            .user(user)
            .name(body.getName())
            .delegateName(body.getDelegateName())
            .chargerName(body.getChargerName())
            .chargerEmail(body.getChargerEmail())
            .chargerPhoneNumber(body.getChargerPhoneNumber())
            .businessNumber(body.getBusinessNumber())
            .build();
        try {
            hostRepository.save(host);

            user.setRole(Role.ROLE_ADMIN);
            userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            throw new HttpClientErrorException(HttpStatusCode.valueOf(409));
        }

        return HostDto.from(host);
    }

    public List<Long> createHostEvents(Long userId, List<Long> eventIds) {
        Boolean isHostExist = hostRepository.existsByUser_Id(userId);
        if (!isHostExist) throw new HttpClientErrorException(HttpStatusCode.valueOf(404));
        return null;
    }
}
