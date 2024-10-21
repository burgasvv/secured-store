package org.burgas.employeeservice.service;

import lombok.RequiredArgsConstructor;
import org.burgas.employeeservice.dto.PositionRequest;
import org.burgas.employeeservice.dto.PositionResponse;
import org.burgas.employeeservice.mapper.PositionMapper;
import org.burgas.employeeservice.repository.PositionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.springframework.transaction.annotation.Isolation.SERIALIZABLE;
import static org.springframework.transaction.annotation.Propagation.REQUIRED;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PositionService {

    private final PositionRepository positionRepository;
    private final PositionMapper positionMapper;

    public List<PositionResponse> findAll() {
        return positionRepository.findAll()
                .stream().map(positionMapper::toPositionResponse)
                .toList();
    }

    public PositionResponse findById(Long positionId) {
        return positionRepository.findById(positionId)
                .map(positionMapper::toPositionResponse)
                .orElseGet(PositionResponse::new);
    }

    @Transactional(
            isolation = SERIALIZABLE,
            propagation = REQUIRED,
            rollbackFor = RuntimeException.class
    )
    public PositionResponse createOrUpdate(PositionRequest positionRequest) {
        return positionMapper.toPositionResponse(
                positionRepository.save(
                        positionMapper.toPosition(positionRequest)
                )
        );
    }

    @Transactional(
            isolation = SERIALIZABLE,
            propagation = REQUIRED,
            rollbackFor = RuntimeException.class
    )
    public String delete(Long positionId) {
        positionRepository.deleteById(positionId);
        return "Должность с идентификатором " + positionId + " удалена";
    }
}
