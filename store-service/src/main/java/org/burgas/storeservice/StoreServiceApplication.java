package org.burgas.storeservice;

import lombok.extern.slf4j.Slf4j;
import org.burgas.storeservice.entity.Store;
import org.burgas.storeservice.repository.StoreRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.task.ThreadPoolTaskExecutorBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.List;
import java.util.concurrent.Executor;

@Slf4j
@EnableAsync
@EnableFeignClients
@SpringBootApplication
@EnableDiscoveryClient
public class StoreServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(StoreServiceApplication.class, args);
    }

    @Bean(name = "taskExecutor")
    public Executor asyncExecutor() {
        return new ThreadPoolTaskExecutorBuilder()
                .corePoolSize(4)
                .maxPoolSize(4)
                .queueCapacity(100)
                .threadNamePrefix("storeThread-")
                .build();
    }

    @Bean
    public CommandLineRunner getData(StoreRepository storeRepository) {

        return _ -> {

            List<Store> stores = List.of(
                    Store.builder().name("Магазин на Ленина").address("Ленина 65").build(),
                    Store.builder().name("Магазин на Красном").address("Красный проспект 99").build(),
                    Store.builder().name("Магазин на Кирова").address("Кирова 76").build()
            );

            log.info("Stores added: {}", storeRepository.saveAll(stores));
        };
    }
}
