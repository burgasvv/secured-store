package org.burgas.employeeservice.controller;

import lombok.RequiredArgsConstructor;
import org.burgas.employeeservice.dto.PositionRequest;
import org.burgas.employeeservice.dto.PositionResponse;
import org.burgas.employeeservice.service.PositionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/positions")
public class PositionController {

    private final PositionService positionService;

    @GetMapping
    public ResponseEntity<List<PositionResponse>> getAllPositions() {
        return ResponseEntity.ok(positionService.findAll());
    }

    @GetMapping("/{position-id}")
    public ResponseEntity<PositionResponse> getPositionById(
            @PathVariable("position-id") Long positionId
    ) {
        return ResponseEntity.ok(positionService.findById(positionId));
    }

    @PostMapping("/create")
    public ResponseEntity<PositionResponse> createPosition(
            @RequestBody PositionRequest positionRequest
    ) {
        return ResponseEntity.ok(positionService.createOrUpdate(positionRequest));
    }

    @PutMapping("/edit")
    public ResponseEntity<PositionResponse> editPosition(
            @RequestBody PositionRequest positionRequest
    ) {
        return ResponseEntity.ok(positionService.createOrUpdate(positionRequest));
    }

    @DeleteMapping("/delete/{position-id}")
    public ResponseEntity<String> deletePosition(
            @PathVariable("position-id") Long positionId
    ) {
        return ResponseEntity.ok(positionService.delete(positionId));
    }
}
