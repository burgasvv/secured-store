package org.burgas.employeeservice.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.burgas.employeeservice.dto.EmployeeRequest;
import org.burgas.employeeservice.dto.EmployeeResponse;
import org.burgas.employeeservice.entity.Employee;
import org.burgas.employeeservice.exception.EmployeeNotFoundException;
import org.burgas.employeeservice.exception.IdentityNotAuthenticatedException;
import org.burgas.employeeservice.exception.WrongIdentityException;
import org.burgas.employeeservice.handler.RestTemplateHandler;
import org.burgas.employeeservice.mapper.EmployeeMapper;
import org.burgas.employeeservice.repository.EmployeeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

import static org.springframework.transaction.annotation.Isolation.SERIALIZABLE;
import static org.springframework.transaction.annotation.Propagation.REQUIRED;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;
    private final RestTemplateHandler restTemplateHandler;

    public List<EmployeeResponse> findAll(HttpServletRequest request) {
        return employeeRepository.findAll()
                .stream()
                .map(
                        employee -> employeeMapper.toEmployeeResponse(employee, request)
                )
                .toList();
    }

    public EmployeeResponse findById(Long employeeId, HttpServletRequest request) {
        return employeeRepository.findById(employeeId)
                .map(
                        employee -> employeeMapper.toEmployeeResponse(employee, request)
                )
                .orElseGet(EmployeeResponse::new);
    }

    public EmployeeResponse findByIdentityId(Long identityId, HttpServletRequest request) {
        return employeeRepository.findEmployeeByIdentityId(identityId)
                .map(
                        employee -> employeeMapper.toEmployeeResponse(employee, request)
                )
                .orElseGet(EmployeeResponse::new);
    }

    @Transactional(
            isolation = SERIALIZABLE,
            propagation = REQUIRED,
            rollbackFor = RuntimeException.class
    )
    public EmployeeResponse createOrUpdate(EmployeeRequest employeeRequest, HttpServletRequest request) {

        if (Boolean.TRUE.equals(restTemplateHandler.isAuthenticated(request).getBody())) {
            Long authenticatedIdentityId = restTemplateHandler.getAuthenticationCredentialId(request).getBody();

            if (Objects.equals(employeeRequest.getIdentityId(), authenticatedIdentityId)) {
                return employeeMapper.toEmployeeResponse(
                        employeeRepository.save(
                                employeeMapper.toEmployee(employeeRequest)
                        ),
                        request
                );

            } else throw new WrongIdentityException("Попытка создания или изменения данных сотрудника с чужого аккаунта");

        } else throw new IdentityNotAuthenticatedException("Пользователь не авторизован");
    }

    @Transactional(
            isolation = SERIALIZABLE,
            propagation = REQUIRED,
            rollbackFor = RuntimeException.class
    )
    public String deleteById(Long employeeId, HttpServletRequest request) {

        if (Boolean.TRUE.equals(restTemplateHandler.isAuthenticated(request).getBody())) {
            Long authenticatedIdentityId = restTemplateHandler.getAuthenticationCredentialId(request).getBody();

            Employee employee = employeeRepository.findEmployeeByIdentityId(authenticatedIdentityId)
                    .orElseThrow(
                            () -> new EmployeeNotFoundException("Сотрудник по идентификатору пользователя не найден")
                    );
            if (Objects.equals(employeeId, employee.getId())) {
                employeeRepository.deleteById(employeeId);
                return "Сотрудник с идентификатором " + employeeId + " удален";

            } else throw new EmployeeNotFoundException("Ошибка удаления сотрудника, идентификаторы не совпадают");

        } else throw new IdentityNotAuthenticatedException("Пользователь не авторизован");
    }
}
