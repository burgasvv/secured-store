package org.burgas.paymentservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IdentityPrincipal {

    private Long id;
    private String username;
    private List<String> authorities;
    private Boolean isAuthenticated;
}
