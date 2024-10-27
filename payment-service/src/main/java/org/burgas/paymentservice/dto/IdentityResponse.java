package org.burgas.paymentservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IdentityResponse {

    private Long id;
    private String username;
    private String email;
    private String password;
    private Boolean enabled;
    private AuthorityResponse authorityResponse;
    private EmployeeResponse employeeResponse;
}
