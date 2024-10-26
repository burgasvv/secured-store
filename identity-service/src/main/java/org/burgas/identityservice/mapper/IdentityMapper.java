package org.burgas.identityservice.mapper;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.burgas.identityservice.dto.IdentityRequest;
import org.burgas.identityservice.dto.IdentityRequestEdit;
import org.burgas.identityservice.dto.IdentityResponse;
import org.burgas.identityservice.entity.Authority;
import org.burgas.identityservice.entity.Identity;
import org.burgas.identityservice.exception.IdentityNotFoundException;
import org.burgas.identityservice.handler.RestTemplateHandler;
import org.burgas.identityservice.repository.AuthorityRepository;
import org.burgas.identityservice.repository.IdentityRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IdentityMapper {

    private final PasswordEncoder passwordEncoder;
    private final AuthorityMapper authorityMapper;
    private final IdentityRepository identityRepository;
    private final AuthorityRepository authorityRepository;
    private final RestTemplateHandler restTemplateHandler;

    public Identity toIdentity(IdentityRequest identityRequest) {
        return Identity.builder()
                .id(identityRequest.getId())
                .username(identityRequest.getUsername())
                .email(identityRequest.getEmail())
                .password(
                        passwordEncoder.encode(identityRequest.getPassword())
                )
                .enabled(true)
                .authority(
                        authorityRepository.findById(1L).orElseGet(() -> Authority.builder().build())
                )
                .build();
    }

    public Identity toIdentity(IdentityRequestEdit identityRequestEdit) {
        return Identity.builder()
                .id(identityRequestEdit.getId())
                .username(identityRequestEdit.getUsername())
                .email(identityRequestEdit.getEmail())
                .password(
                        identityRepository.findById(identityRequestEdit.getId())
                                .orElseThrow(
                                        () -> new IdentityNotFoundException(
                                                "Пользователь с идентификатором " + identityRequestEdit.getId() + " не найден"
                                        )
                                ).getPassword()
                )
                .enabled(true)
                .authority(
                        authorityRepository.findById(1L).orElseGet(() -> Authority.builder().build())
                )
                .build();
    }

    public IdentityResponse toIdentityResponse(Identity identity, HttpServletRequest request) {

        return IdentityResponse.builder()
                .id(identity.getId())
                .employeeResponse(
                        restTemplateHandler.getEmployeeByIdentityId(identity.getId(), request).getBody()
                )
                .username(identity.getUsername())
                .email(identity.getEmail())
                .password(identity.getPassword())
                .enabled(identity.getEnabled())
                .authorityResponse(
                        authorityMapper.toAuthorityResponse(
                                identity.getAuthority()
                        )
                )
                .build();
    }
}
