package org.burgas.storeservice.repository;

import org.burgas.storeservice.entity.Store;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(
        replace = AutoConfigureTestDatabase.Replace.NONE
)
class StoreRepositoryTest {

    @Autowired
    StoreRepository storeRepository;

    @Test
    void handleGetAllStores() {

        List<Store> testStores = List.of(
                Store.builder().id(1L).name("Магазин на Ленина").address("Ленина 65").build(),
                Store.builder().id(2L).name("Магазин на Красном").address("Красный проспект 99").build(),
                Store.builder().id(3L).name("Магазин на Кирова").address("Кирова 76").build()
        );
        List<Store> stores = storeRepository.findAll();

        Assertions.assertNotNull(stores);
        Assertions.assertEquals(testStores, stores);
    }

    @Test
    void handleGetStoreById() {

        Store testStore = Store.builder().id(1L).name("Магазин на Ленина").address("Ленина 65").build();
        Store store = storeRepository.findById(testStore.getId()).orElse(null);

        Assertions.assertNotNull(store);
        Assertions.assertEquals(testStore, store);
    }

    @Test
    void handleCreateStore() {

        Store testStore = Store.builder().name("Магазин на Октябрьской").address("Октябрьская 90").build();
        Store store = storeRepository.save(testStore);

        Assertions.assertNotNull(store);
        Assertions.assertEquals(testStore.getName(), store.getName());
    }

    @Test
    void handleUpdateStore() {

        Store testStore = Store.builder().id(1L).name("Магазин на Октябрьской").address("Октябрьская 90").build();
        Store store = storeRepository.save(testStore);

        Assertions.assertNotNull(store);
        Assertions.assertEquals(testStore, store);
    }

    @Test
    void handleDeleteStoreById() {

        storeRepository.deleteById(1L);
        Store store = storeRepository.findById(1L).orElse(null);

        Assertions.assertNull(store);
    }
}