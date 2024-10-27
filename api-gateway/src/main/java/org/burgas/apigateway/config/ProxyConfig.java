package org.burgas.apigateway.config;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@RequiredArgsConstructor
public class ProxyConfig {

    @Bean
    @LoadBalanced
    public WebClient.Builder loadBalancedWebClientBuilder() {
        return WebClient.builder();
    }

    @Bean
    public RouteLocator getRouteLocator(RouteLocatorBuilder builder) {

        return builder.routes()
                .route(
                        "identities",
                        predicateSpec -> predicateSpec
                                .path("/identities/**")
                                .uri("lb:http://identity-service")
                )
                .route(
                        "employees",
                        predicateSpec -> predicateSpec
                                .path("/employees/**", "/positions/**")
                                .uri("lb:http://employee-service")
                )
                .route(
                        "stores",
                        predicateSpec -> predicateSpec
                                .path("/stores/**")
                                .uri("lb:http://store-service")
                )
                .route(
                        "products",
                        predicateSpec -> predicateSpec
                                .path("/products/**","/product-types/**")
                                .uri("lb:http://product-service")
                )
                .route(
                        "orders",
                        predicateSpec -> predicateSpec
                                .path("/tabs/**", "/purchases/**")
                                .uri("lb:http://order-service")
                )
                .route(
                        "payments",
                        predicateSpec -> predicateSpec
                                .path("/payments/**", "/payment-types/**")
                                .uri("lb:http://payment-service")
                )
                .route(
                        "notifications",
                        predicateSpec -> predicateSpec
                                .path("/notifications/**")
                                .uri("lb:http://notification-server")
                )
                .build();
    }
}
