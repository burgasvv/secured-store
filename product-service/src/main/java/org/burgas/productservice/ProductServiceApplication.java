package org.burgas.productservice;

import org.burgas.productservice.entity.Product;
import org.burgas.productservice.entity.ProductStore;
import org.burgas.productservice.entity.ProductType;
import org.burgas.productservice.repository.ProductRepository;
import org.burgas.productservice.repository.ProductStoreRepository;
import org.burgas.productservice.repository.ProductTypeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestClient;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class ProductServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductServiceApplication.class, args);
    }

    @Bean
    public RestClient restClient() {
        return RestClient.builder().build();
    }

    @Bean
    public CommandLineRunner getData(
            ProductRepository productRepository,
            ProductTypeRepository productTypeRepository,
            ProductStoreRepository productStoreRepository
    ) {

        return _ -> {

            ArrayList<ProductType> productTypes = new ArrayList<>(
                    List.of(
                            ProductType.builder().name("Видеокарты").build(),
                            ProductType.builder().name("Процессоры").build(),
                            ProductType.builder().name("Оперативная память").build()
                    )
            );

            ArrayList<Product> products = new ArrayList<>(
                    List.of(
                            Product.builder()
                                    .name("RTX 4090").productTypeId(1L).price(150000).amount(50)
                                    .description("New Top video card")
                                    .build(),
                            Product.builder()
                                    .name("RTX 4080").productTypeId(1L).price(100000).amount(70)
                                    .description("New middle video card")
                                    .build(),
                            Product.builder()
                                    .name("Intel i9").productTypeId(2L).price(120000).amount(30)
                                    .description("New Top processor")
                                    .build(),
                            Product.builder()
                                    .name("Intel i11").productTypeId(2L).price(90000).amount(60)
                                    .description("New middle processor")
                                    .build(),
                            Product.builder()
                                    .name("DDR5").productTypeId(3L).price(60000).amount(80)
                                    .description("New Top video card")
                                    .build(),
                            Product.builder()
                                    .name("DDR4").productTypeId(3L).price(40000).amount(90)
                                    .description("New Top video card")
                                    .build()
                    )
            );

            ArrayList<ProductStore> productStores = new ArrayList<>(
                    List.of(
                            ProductStore.builder().productId(1L).storeId(1L).amount(20).build(),
                            ProductStore.builder().productId(1L).storeId(2L).amount(20).build(),
                            ProductStore.builder().productId(1L).storeId(3L).amount(10).build(),
                            ProductStore.builder().productId(2L).storeId(1L).amount(20).build(),
                            ProductStore.builder().productId(2L).storeId(2L).amount(20).build(),
                            ProductStore.builder().productId(2L).storeId(3L).amount(30).build(),
                            ProductStore.builder().productId(3L).storeId(1L).amount(10).build(),
                            ProductStore.builder().productId(3L).storeId(2L).amount(10).build(),
                            ProductStore.builder().productId(3L).storeId(3L).amount(10).build(),
                            ProductStore.builder().productId(4L).storeId(1L).amount(20).build(),
                            ProductStore.builder().productId(4L).storeId(2L).amount(20).build(),
                            ProductStore.builder().productId(4L).storeId(3L).amount(20).build(),
                            ProductStore.builder().productId(5L).storeId(1L).amount(30).build(),
                            ProductStore.builder().productId(5L).storeId(2L).amount(30).build(),
                            ProductStore.builder().productId(5L).storeId(3L).amount(20).build(),
                            ProductStore.builder().productId(6L).storeId(1L).amount(30).build(),
                            ProductStore.builder().productId(6L).storeId(2L).amount(30).build(),
                            ProductStore.builder().productId(6L).storeId(3L).amount(30).build()
                    )
            );

            productTypeRepository.saveAll(productTypes);
            productRepository.saveAll(products);
            productStoreRepository.saveAll(productStores);
        };
    }
}
