package org.burgas.apigateway.controller;

import lombok.RequiredArgsConstructor;
import org.burgas.apigateway.dto.IdentityPrincipal;
import org.burgas.apigateway.dto.IdentityResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    @GetMapping("/is-authenticated")
    public ResponseEntity<Boolean> isAuthenticated(Authentication authentication)  {
        return ResponseEntity.ok(
                authentication != null && authentication.isAuthenticated()
        );
    }

    @GetMapping("/authentication-data")
    public ResponseEntity<Long> getAuthenticationPrincipalId(Authentication authentication) {
        return ResponseEntity.ok(
                authentication != null ? ((IdentityResponse) authentication.getPrincipal()).getId() : -500
        );
    }

    @GetMapping("/principal")
    public ResponseEntity<IdentityPrincipal> getPrincipal(Authentication authentication) {
        if (authentication != null) {
            IdentityResponse identity = (IdentityResponse) authentication.getPrincipal();
            return ResponseEntity.ok(
                    IdentityPrincipal.builder()
                            .id(identity.getId())
                            .username(identity.getUsername())
                            .isAuthenticated(authentication.isAuthenticated())
                            .authorities(
                                    identity.getAuthorities()
                                            .stream()
                                            .map(GrantedAuthority::getAuthority)
                                            .toList()
                            )
                            .build()
            );

        } else return ResponseEntity
                .ok(IdentityPrincipal.builder().isAuthenticated(false).build());
    }
}
