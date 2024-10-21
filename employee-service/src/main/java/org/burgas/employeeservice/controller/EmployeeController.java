package org.burgas.employeeservice.controller;

import lombok.RequiredArgsConstructor;
import org.burgas.employeeservice.dto.EmployeeRequest;
import org.burgas.employeeservice.dto.EmployeeResponse;
import org.burgas.employeeservice.service.EmployeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping
    public ResponseEntity<List<EmployeeResponse>> getAllEmployees() {
        return ResponseEntity.ok(employeeService.findAll());
    }

    @GetMapping(value = "/{employee-id}")
    public ResponseEntity<EmployeeResponse> getEmployeeById(
            @PathVariable("employee-id") Long employeeId
    ) {
        return ResponseEntity.ok(employeeService.findById(employeeId));
    }

    @GetMapping("/identity/{identity-id}")
    public ResponseEntity<EmployeeResponse> getEmployeeByIdentityId(
            @PathVariable(name = "identity-id") Long identityId
    ) {
        return ResponseEntity.ok(employeeService.findByIdentityId(identityId));
    }

    @PostMapping("/create")
    public ResponseEntity<EmployeeResponse> createEmployee(
            @RequestBody EmployeeRequest employeeRequest
    ) {
        return ResponseEntity.ok(employeeService.create(employeeRequest));
    }

    @PutMapping("/edit")
    public ResponseEntity<EmployeeResponse> editEmployee(
            @RequestBody EmployeeRequest employeeRequest
    ) {
        return ResponseEntity.ok(employeeService.update(employeeRequest));
    }

    @DeleteMapping("/delete/{employee-id}")
    public ResponseEntity<Long> deleteEmployee(
            @PathVariable("employee-id") Long employeeId
    ) {
        return ResponseEntity.ok(employeeService.deleteById(employeeId));
    }
}
