package org.burgas.storeservice.service;

import lombok.RequiredArgsConstructor;
import org.burgas.storeservice.dto.StoreRequest;
import org.burgas.storeservice.dto.StoreResponse;
import org.burgas.storeservice.mapper.StoreMapper;
import org.burgas.storeservice.repository.StoreRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.springframework.transaction.annotation.Isolation.*;
import static org.springframework.transaction.annotation.Propagation.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StoreService {

    private final StoreRepository storeRepository;
    private final StoreMapper storeMapper;

    public List<StoreResponse> findAll() {
        return storeRepository.findAll()
                .stream().map(storeMapper::toStoreResponse)
                .toList();
    }

    public StoreResponse findById(Long storeId) {
        return storeRepository.findById(storeId)
                .map(storeMapper::toStoreResponse)
                .orElseGet(StoreResponse::new);
    }

    public StoreResponse findStoreByEmployeeId(Long employeeId) {
        return storeRepository.findStoreByEmployeeId(employeeId)
                .map(storeMapper::toStoreResponse)
                .orElseGet(StoreResponse::new);
    }

    public List<StoreResponse> findStoresByProductId(Long productId) {
        return storeRepository.findStoresByProductId(productId)
                .stream().map(storeMapper::toStoreResponse)
                .toList();
    }

    @Transactional(
            isolation = SERIALIZABLE,
            propagation = REQUIRED,
            rollbackFor = RuntimeException.class
    )
    public StoreResponse createOrUpdate(StoreRequest storeRequest) {
        return storeMapper.toStoreResponse(
                storeRepository.save(
                        storeMapper.toStore(storeRequest)
                )
        );
    }

    @Transactional(
            isolation = SERIALIZABLE,
            propagation = REQUIRED,
            rollbackFor = RuntimeException.class
    )
    public Long delete(Long storeId) {
        storeRepository.deleteById(storeId);
        return storeId;
    }
}
