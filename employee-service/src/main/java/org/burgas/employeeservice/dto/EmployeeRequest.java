package org.burgas.employeeservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeRequest {

    private Long id;
    private Long identityId;
    private String firstName;
    private String lastName;
    private String patronymic;
    private String email;
    private Long positionId;
    private Long storeId;
}
