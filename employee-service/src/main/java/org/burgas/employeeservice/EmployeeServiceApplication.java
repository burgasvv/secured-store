package org.burgas.employeeservice;

import lombok.extern.slf4j.Slf4j;
import org.burgas.employeeservice.entity.Employee;
import org.burgas.employeeservice.entity.Position;
import org.burgas.employeeservice.repository.EmployeeRepository;
import org.burgas.employeeservice.repository.PositionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestClient;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class EmployeeServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmployeeServiceApplication.class, args);
    }

    @Bean
    public RestClient restClient() {
        return RestClient.builder().build();
    }

    @Bean
    public CommandLineRunner getData(
            PositionRepository positionRepository,
            EmployeeRepository employeeRepository
    ) {

        return _ -> {

            List<Position> positions = new ArrayList<>(
                    List.of(
                            Position.builder().name("Стажер").build(),
                            Position.builder().name("Младший продавец").build(),
                            Position.builder().name("Старший продавец").build()
                    )
            );

            List<Employee> employees = new ArrayList<>(
                    List.of(
                            Employee.builder()
                                    .firstName("Олег").lastName("Сергеев").patronymic("Павлович")
                                    .email("pavlovich@email.ru").storeId(2L).positionId(1L)
                                    .build(),
                            Employee.builder()
                                    .firstName("Сергей").lastName("Краснов").patronymic("Леонович")
                                    .email("leonovich@email.ru").storeId(1L).positionId(3L)
                                    .build(),
                            Employee.builder()
                                    .firstName("Владимир").lastName("Капилев").patronymic("Вячеславович")
                                    .email("мнфср@email.ru").storeId(3L).positionId(2L)
                                    .build(),
                            Employee.builder()
                                    .firstName("Алексей").lastName("Петровичев").patronymic("Рустамович")
                                    .email("petrovich@email.ru").storeId(2L).positionId(1L)
                                    .build(),
                            Employee.builder()
                                    .firstName("Петр").lastName("Суртаев").patronymic("Васильевич")
                                    .email("surtaevvv@email.ru").storeId(3L).positionId(2L)
                                    .build(),
                            Employee.builder()
                                    .firstName("Вячеслав").lastName("Бургас").patronymic("Васильевич")
                                    .email("burgasvv@gmail.com").storeId(1L).positionId(2L).identityId(1L)
                                    .build()
                    )
            );

            log.info("Positions added: {}", positionRepository.saveAll(positions));
            log.info("Employees added: {}", employeeRepository.saveAll(employees));
        };
    }
}
