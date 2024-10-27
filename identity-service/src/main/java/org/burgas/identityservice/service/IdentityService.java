package org.burgas.identityservice.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.burgas.identityservice.dto.IdentityRequest;
import org.burgas.identityservice.dto.IdentityRequestEdit;
import org.burgas.identityservice.dto.IdentityResponse;
import org.burgas.identityservice.exception.IdentityNotAuthenticatedException;
import org.burgas.identityservice.exception.WrongIdentityException;
import org.burgas.identityservice.handler.RestTemplateHandler;
import org.burgas.identityservice.mapper.IdentityMapper;
import org.burgas.identityservice.repository.IdentityRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

import static org.springframework.transaction.annotation.Isolation.SERIALIZABLE;
import static org.springframework.transaction.annotation.Propagation.REQUIRED;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class IdentityService {

    private final IdentityRepository identityRepository;
    private final IdentityMapper identityMapper;
    private final RestTemplateHandler restTemplateHandler;

    public List<IdentityResponse> findAll(HttpServletRequest request) {
        return identityRepository.findAll()
                .stream()
                .map(
                        identity -> identityMapper.toIdentityResponse(identity, request)
                )
                .toList();
    }

    public IdentityResponse findByUserName(String username, HttpServletRequest request) {
        return identityRepository.findIdentityByUsername(username)
                .map(
                        identity -> identityMapper.toIdentityResponse(identity, request)
                )
                .orElseGet(
                        () -> IdentityResponse.builder().build()
                );
    }

    @Transactional(
            isolation = SERIALIZABLE,
            propagation = REQUIRED,
            rollbackFor = RuntimeException.class
    )
    public IdentityResponse create(IdentityRequest identityRequest, HttpServletRequest request) {
        return identityMapper.toIdentityResponse(
                identityRepository.save(
                        identityMapper.toIdentity(identityRequest)
                ),
                request
        );
    }

    @Transactional(
            isolation = SERIALIZABLE,
            propagation = REQUIRED,
            rollbackFor = RuntimeException.class
    )
    public IdentityResponse update(IdentityRequestEdit identityRequestEdit, HttpServletRequest request) {

        if (Boolean.TRUE.equals(restTemplateHandler.isAuthenticated(request).getBody())) {

            Long authenticatedIdentityId = restTemplateHandler.getAuthenticationCredentialId(request).getBody();

            if (Objects.equals(authenticatedIdentityId, identityRequestEdit.getId())) {
                return identityMapper.toIdentityResponse(
                        identityRepository.save(
                                identityMapper.toIdentity(identityRequestEdit)
                        ),
                        request
                );

            } else throw new WrongIdentityException("Пользователь пытается изменить данные чужого аккаунта");

        } else throw new IdentityNotAuthenticatedException("Пользователь не авторизован");
    }

    @Transactional(
            isolation = SERIALIZABLE,
            propagation = REQUIRED,
            rollbackFor = RuntimeException.class
    )
    public String delete(Long identityId, HttpServletRequest request) {

        if (Boolean.TRUE.equals(restTemplateHandler.isAuthenticated(request).getBody())) {

            Long authenticatedIdentityId = restTemplateHandler.getAuthenticationCredentialId(request).getBody();

            if (Objects.equals(authenticatedIdentityId, identityId)) {
                identityRepository.updateEmployeeByIdentityIdSetNull(identityId);
                identityRepository.deleteById(identityId);
                return "Пользователь с идентификатором " + identityId + " успешно удален";

            } else throw new WrongIdentityException("Пользователь пытается удалить данные чужого аккаунта");


        } else throw new IdentityNotAuthenticatedException("Пользователь не авторизован");
    }

    public IdentityResponse findIdentityByTabId(Long tabId, HttpServletRequest request) {
        return identityRepository.findIdentityByTabId(tabId)
                .map(
                        identity -> identityMapper.toIdentityResponse(identity, request)
                )
                .orElseGet(IdentityResponse::new);
    }

    public IdentityResponse findIdentityByPurchaseId(Long purchaseId, HttpServletRequest request) {
        return identityRepository.findIdentityByPurchaseId(purchaseId)
                .map(
                        identity -> identityMapper.toIdentityResponse(identity, request)
                )
                .orElseGet(IdentityResponse::new);
    }

    public IdentityResponse findById(Long identityId, HttpServletRequest request) {
        return identityRepository.findById(identityId)
                .map(
                        identity -> identityMapper.toIdentityResponse(identity, request)
                )
                .orElseGet(IdentityResponse::new);
    }
}
