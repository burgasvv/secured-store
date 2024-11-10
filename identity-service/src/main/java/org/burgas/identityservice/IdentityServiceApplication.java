package org.burgas.identityservice;

import lombok.RequiredArgsConstructor;
import org.burgas.identityservice.entity.Authority;
import org.burgas.identityservice.entity.Identity;
import org.burgas.identityservice.repository.AuthorityRepository;
import org.burgas.identityservice.repository.IdentityRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@SpringBootApplication
@EnableDiscoveryClient
@RequiredArgsConstructor
@EnableFeignClients
public class IdentityServiceApplication {

    private final PasswordEncoder passwordEncoder;

    public static void main(String[] args) {
        SpringApplication.run(IdentityServiceApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public CommandLineRunner initData(
            IdentityRepository identityRepository,
            AuthorityRepository authorityRepository
    ) {

        return _ -> {

            List<Authority> authorities = List.of(
                    Authority.builder().name("USER").build(),
                    Authority.builder().name("ADMIN").build()
            );

            List<Identity> identities = List.of(
                    Identity.builder().username("admin")
                            .email("burgassme@gmail.com")
                            .password(passwordEncoder.encode("admin"))
                            .authority(authorities.get(1))
                            .enabled(true).build()
            );

            authorityRepository.saveAll(authorities);
            identityRepository.saveAll(identities);
        };
    }
}
