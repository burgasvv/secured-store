package org.burgas.apigateway.service;

import lombok.RequiredArgsConstructor;
import org.burgas.apigateway.handler.RestClientHandler;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements ReactiveUserDetailsService {

    private final RestClientHandler restClientHandler;

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        return Mono.create(
                userDetailsMonoSink -> userDetailsMonoSink.success(
                        restClientHandler.getIdentityByUsername(username).getBody()
                )
        );
    }
}
