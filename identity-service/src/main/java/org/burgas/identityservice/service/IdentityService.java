package org.burgas.identityservice.service;

import lombok.RequiredArgsConstructor;
import org.burgas.identityservice.dto.IdentityRequest;
import org.burgas.identityservice.dto.IdentityRequestEdit;
import org.burgas.identityservice.dto.IdentityResponse;
import org.burgas.identityservice.mapper.IdentityMapper;
import org.burgas.identityservice.repository.IdentityRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.springframework.transaction.annotation.Isolation.SERIALIZABLE;
import static org.springframework.transaction.annotation.Propagation.REQUIRED;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class IdentityService {

    private final IdentityRepository identityRepository;
    private final IdentityMapper identityMapper;

    public List<IdentityResponse> findAll() {
        return identityRepository.findAll()
                .stream().map(identityMapper::toIdentityResponse)
                .toList();
    }

    public IdentityResponse findByUserName(String username) {
        return identityRepository.findIdentityByUsername(username)
                .map(identityMapper::toIdentityResponse)
                .orElseGet(
                        () -> IdentityResponse.builder().build()
                );
    }

    @Transactional(
            isolation = SERIALIZABLE,
            propagation = REQUIRED,
            rollbackFor = RuntimeException.class
    )
    public IdentityResponse create(IdentityRequest identityRequest) {
        return identityMapper.toIdentityResponse(
                identityRepository.save(
                        identityMapper.toIdentity(identityRequest)
                )
        );
    }

    @Transactional(
            isolation = SERIALIZABLE,
            propagation = REQUIRED,
            rollbackFor = RuntimeException.class
    )
    public IdentityResponse update(IdentityRequestEdit identityRequestEdit) {
        return identityMapper.toIdentityResponse(
                identityRepository.save(
                        identityMapper.toIdentity(identityRequestEdit)
                )
        );
    }

    @Transactional(
            isolation = SERIALIZABLE,
            propagation = REQUIRED,
            rollbackFor = RuntimeException.class
    )
    public String delete(Long identityId) {
        identityRepository.updateEmployeeByIdentityIdSetNull(identityId);
        identityRepository.deleteById(identityId);
        return "Пользователь с идентификатором " + identityId + " успешно удален";
    }

    public IdentityResponse findIdentityByTabId(Long tabId) {
        return identityRepository.findIdentityByTabId(tabId)
                .map(identityMapper::toIdentityResponse)
                .orElseGet(IdentityResponse::new);
    }

    public IdentityResponse findById(Long identityId) {
        return identityRepository.findById(identityId)
                .map(identityMapper::toIdentityResponse)
                .orElseGet(IdentityResponse::new);
    }
}
