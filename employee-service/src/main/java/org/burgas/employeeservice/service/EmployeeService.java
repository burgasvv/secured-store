package org.burgas.employeeservice.service;

import lombok.RequiredArgsConstructor;
import org.burgas.employeeservice.dto.EmployeeRequest;
import org.burgas.employeeservice.dto.EmployeeResponse;
import org.burgas.employeeservice.mapper.EmployeeMapper;
import org.burgas.employeeservice.repository.EmployeeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.springframework.transaction.annotation.Isolation.SERIALIZABLE;
import static org.springframework.transaction.annotation.Propagation.REQUIRED;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;

    public List<EmployeeResponse> findAll() {
        return employeeRepository.findAll()
                .stream().map(employeeMapper::toEmployeeResponse)
                .toList();
    }

    public EmployeeResponse findById(Long employeeId) {
        return employeeRepository.findById(employeeId)
                .map(employeeMapper::toEmployeeResponse)
                .orElseGet(EmployeeResponse::new);
    }

    public EmployeeResponse findByIdentityId(Long identityId) {
        return employeeRepository.findEmployeeByIdentityId(identityId)
                .map(employeeMapper::toEmployeeResponse)
                .orElseGet(EmployeeResponse::new);
    }

    @Transactional(
            isolation = SERIALIZABLE,
            propagation = REQUIRED,
            rollbackFor = RuntimeException.class
    )
    public EmployeeResponse create(EmployeeRequest employeeRequest) {
        return employeeMapper.toEmployeeResponse(
                employeeRepository.save(
                        employeeMapper.toEmployee(employeeRequest)
                )
        );
    }

    @Transactional(
            isolation = SERIALIZABLE,
            propagation = REQUIRED,
            rollbackFor = RuntimeException.class
    )
    public EmployeeResponse update(EmployeeRequest employeeRequest) {
        return employeeMapper.toEmployeeResponse(
                employeeRepository.save(
                        employeeMapper.toEmployee(employeeRequest)
                )
        );
    }

    @Transactional(
            isolation = SERIALIZABLE,
            propagation = REQUIRED,
            rollbackFor = RuntimeException.class
    )
    public Long deleteById(Long employeeId) {
        employeeRepository.deleteById(employeeId);
        return employeeId;
    }
}
