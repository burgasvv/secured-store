package org.burgas.employeeservice.mapper;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.burgas.employeeservice.dto.EmployeeRequest;
import org.burgas.employeeservice.dto.EmployeeResponse;
import org.burgas.employeeservice.entity.Employee;
import org.burgas.employeeservice.entity.Position;
import org.burgas.employeeservice.handler.RestClientHandler;
import org.burgas.employeeservice.repository.PositionRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmployeeMapper {

    private final PositionRepository positionRepository;
    private final PositionMapper positionMapper;
    private final RestClientHandler restClientHandler;

    public EmployeeResponse toEmployeeResponse(Employee employee, HttpServletRequest request) {

        return EmployeeResponse.builder()
                .id(employee.getId())
                .identityId(employee.getIdentityId())
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .patronymic(employee.getPatronymic())
                .email(employee.getEmail())
                .positionResponse(
                        positionMapper.toPositionResponse(
                                positionRepository.findPositionByEmployeeId(employee.getId())
                                        .orElseGet(Position::new)
                        )
                )
                .storeResponse(
                        restClientHandler.getStoreById(employee.getStoreId(), request).getBody()
                )
                .build();
    }

    public Employee toEmployee(EmployeeRequest employeeRequest) {
        return Employee.builder()
                .id(employeeRequest.getId())
                .identityId(employeeRequest.getIdentityId())
                .positionId(employeeRequest.getPositionId())
                .storeId(employeeRequest.getStoreId())
                .firstName(employeeRequest.getFirstName())
                .lastName(employeeRequest.getLastName())
                .patronymic(employeeRequest.getPatronymic())
                .email(employeeRequest.getEmail())
                .build();
    }
}
