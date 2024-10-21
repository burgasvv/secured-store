package org.burgas.orderservice;

import org.burgas.orderservice.dto.PurchaseRequest;
import org.burgas.orderservice.service.PurchaseService;
import org.burgas.orderservice.service.TabService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableDiscoveryClient
public class OrderServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderServiceApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public CommandLineRunner initDatabase(TabService tabService, PurchaseService purchaseService) {
        return _ -> {

            purchaseService.makePurchase(
                    PurchaseRequest.builder().identityId(1L).storeId(1L).productId(1L).amount(2).build()
            );
            purchaseService.makePurchase(
                    PurchaseRequest.builder().identityId(1L).storeId(1L).productId(2L).amount(3).build()
            );
            purchaseService.makePurchase(
                    PurchaseRequest.builder().identityId(1L).storeId(1L).productId(3L).amount(1).build()
            );
            tabService.closeTab(1L);

            purchaseService.makePurchase(
                    PurchaseRequest.builder().identityId(1L).storeId(2L).productId(4L).amount(2).build()
            );
            purchaseService.makePurchase(
                    PurchaseRequest.builder().identityId(1L).storeId(2L).productId(5L).amount(4).build()
            );
            purchaseService.makePurchase(
                    PurchaseRequest.builder().identityId(1L).storeId(2L).productId(6L).amount(1).build()
            );
            tabService.closeTab(2L);
        };
    }
}
