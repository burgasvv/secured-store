package org.burgas.employeeservice.controller;

import jakarta.servlet.http.HttpServletRequest;
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
    public ResponseEntity<List<EmployeeResponse>> getAllEmployees(HttpServletRequest request) {
        return ResponseEntity.ok(employeeService.findAll(request));
    }

    @GetMapping(value = "/{employee-id}")
    public ResponseEntity<EmployeeResponse> getEmployeeById(
            @PathVariable("employee-id") Long employeeId, HttpServletRequest request
    ) {
        return ResponseEntity.ok(employeeService.findById(employeeId, request));
    }

    @GetMapping("/identity/{identity-id}")
    public ResponseEntity<EmployeeResponse> getEmployeeByIdentityId(
            @PathVariable(name = "identity-id") Long identityId, HttpServletRequest request
    ) {
        return ResponseEntity.ok(employeeService.findByIdentityId(identityId, request));
    }

    @PostMapping("/create")
    public ResponseEntity<EmployeeResponse> createEmployee(
            @RequestBody EmployeeRequest employeeRequest, HttpServletRequest request
    ) {
        return ResponseEntity.ok(employeeService.createOrUpdate(employeeRequest, request));
    }

    @PutMapping("/edit")
    public ResponseEntity<EmployeeResponse> editEmployee(
            @RequestBody EmployeeRequest employeeRequest, HttpServletRequest request
    ) {
        return ResponseEntity.ok(employeeService.createOrUpdate(employeeRequest, request));
    }

    @DeleteMapping("/delete/{employee-id}")
    public ResponseEntity<String> deleteEmployee(
            @PathVariable("employee-id") Long employeeId, HttpServletRequest request
    ) {
        return ResponseEntity.ok(employeeService.deleteById(employeeId, request));
    }
}
