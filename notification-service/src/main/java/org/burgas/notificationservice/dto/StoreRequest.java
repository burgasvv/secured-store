package org.burgas.notificationservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoreRequest {

    private Long id;
    private String name;
    private String address;
    private Integer productAmount;
}
