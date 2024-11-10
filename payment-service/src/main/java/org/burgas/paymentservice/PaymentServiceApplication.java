package org.burgas.paymentservice;

import lombok.RequiredArgsConstructor;
import org.burgas.paymentservice.entity.PaymentType;
import org.burgas.paymentservice.repository.PaymentTypeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestClient;

import java.util.List;

@SpringBootApplication
@EnableDiscoveryClient
@RequiredArgsConstructor
public class PaymentServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PaymentServiceApplication.class, args);
    }

    @Bean
    public RestClient restClient() {
        return RestClient.builder().build();
    }

    @Bean
    public CommandLineRunner initDatabase(PaymentTypeRepository paymentTypeRepository) {

        return _ -> {

            List<PaymentType> paymentTypes = List.of(
                    PaymentType.builder().name("Наличные").build(),
                    PaymentType.builder().name("Карта").build(),
                    PaymentType.builder().name("Рассрочка").build()
            );

            paymentTypeRepository.saveAll(paymentTypes);
        };
    }
}
