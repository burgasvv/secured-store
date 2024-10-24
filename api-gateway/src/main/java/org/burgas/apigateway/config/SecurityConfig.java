package org.burgas.apigateway.config;

import lombok.RequiredArgsConstructor;
import org.burgas.apigateway.service.CustomUserDetailsService;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpCookie;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.WebFilter;

import java.time.Duration;
import java.util.UUID;

@Configuration
@EnableWebFluxSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {

        http
                .httpBasic(Customizer.withDefaults())
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(
                        exchanges -> exchanges
                                .pathMatchers(
                                        "/is-authenticated","/tabs/tab/unauthorized","/purchases/make-unauthorized-account-purchase",
                                        "/stores", "/stores/{store-id}", "/positions", "/positions/{position-id}",
                                        "/products", "/products/{product-id}", "/product-types",
                                        "/product-types/{productType-id}", "/identities/create"
                                )
                                .permitAll()
                                .pathMatchers(
                                        "/employees/**", "/identities/**", "/positions/**", "/stores/**",
                                        "/products/**", "/product-types/**", "/tabs/**", "/purchases/**"
                                )
                                .hasAnyAuthority("USER", "ADMIN")
                )
                .addFilterAfter(unauthorizedCookieFilter(), SecurityWebFiltersOrder.FIRST)
                .formLogin(Customizer.withDefaults())
                .logout(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    public WebFilter unauthorizedCookieFilter() {
        return (exchange, chain) -> {
            MultiValueMap<String, HttpCookie> cookies = exchange.getRequest().getCookies();
            if (!cookies.containsKey("unauthorized-cookie")) {
                exchange.getResponse().addCookie(
                        ResponseCookie.from("unauthorized-cookie", UUID.randomUUID().toString())
                                .maxAge(Duration.ofHours(1)).build()
                );
            }
            return chain.filter(exchange);
        };
    }

    @Bean
    public ReactiveAuthenticationManager reactiveAuthenticationManager() {
        UserDetailsRepositoryReactiveAuthenticationManager manager =
                new UserDetailsRepositoryReactiveAuthenticationManager(customUserDetailsService);
        manager.setPasswordEncoder(passwordEncoder());
        return manager;
    }

    @Bean
    public HttpMessageConverters httpMessageConverters() {
        return new HttpMessageConverters();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
