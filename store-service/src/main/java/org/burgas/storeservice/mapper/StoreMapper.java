package org.burgas.storeservice.mapper;

import lombok.RequiredArgsConstructor;
import org.burgas.storeservice.dto.StoreRequest;
import org.burgas.storeservice.dto.StoreResponse;
import org.burgas.storeservice.entity.Store;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StoreMapper {

    public StoreResponse toStoreResponse(Store store) {
        return StoreResponse.builder()
                .id(store.getId())
                .name(store.getName())
                .address(store.getAddress())
                .build();
    }

    public Store toStore(StoreRequest storeRequest) {
        return Store.builder()
                .id(storeRequest.getId())
                .name(storeRequest.getName())
                .address(storeRequest.getAddress())
                .build();
    }
}
