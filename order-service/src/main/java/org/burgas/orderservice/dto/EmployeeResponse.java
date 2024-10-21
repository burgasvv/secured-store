package org.burgas.orderservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeResponse {

    private Long id;
    private Long identityId;
    private String firstName;
    private String lastName;
    private String patronymic;
    private String email;
    private PositionResponse positionResponse;
    private StoreResponse storeResponse;
}
