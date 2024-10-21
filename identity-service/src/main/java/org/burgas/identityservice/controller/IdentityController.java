package org.burgas.identityservice.controller;

import lombok.RequiredArgsConstructor;
import org.burgas.identityservice.dto.IdentityRequest;
import org.burgas.identityservice.dto.IdentityRequestEdit;
import org.burgas.identityservice.dto.IdentityResponse;
import org.burgas.identityservice.service.IdentityService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/identities")
public class IdentityController {

    private final IdentityService identityService;

    @GetMapping
    public ResponseEntity<List<IdentityResponse>> getAllIdentities() {
        return ResponseEntity.ok(identityService.findAll());
    }

    @GetMapping("/identity/{identity-id}")
    public ResponseEntity<IdentityResponse> getIdentityByIdentityId(
            @PathVariable(name = "identity-id") Long identityId
    ) {
        return ResponseEntity.ok(identityService.findById(identityId));
    }

    @GetMapping("/{username}")
    public ResponseEntity<IdentityResponse> getIdentityByUsername(
            @PathVariable String username
    ) {
        return ResponseEntity.ok(identityService.findByUserName(username));
    }

    @PostMapping("/create")
    public ResponseEntity<IdentityResponse> createIdentity(
            @RequestBody IdentityRequest identityRequest
    ) {
        return ResponseEntity.ok(identityService.create(identityRequest));
    }

    @PutMapping("/edit")
    public ResponseEntity<IdentityResponse> updateIdentity(
            @RequestBody IdentityRequestEdit identityRequestEdit
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(identityService.update(identityRequestEdit));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteIdentity(@RequestParam Long identityId) {
        return ResponseEntity.ok(identityService.delete(identityId));
    }

    @GetMapping("/tab/{tab-id}")
    public ResponseEntity<IdentityResponse> getIdentityByTabId(
            @PathVariable(name = "tab-id") Long tabId
    ) {
        return ResponseEntity.ok(identityService.findIdentityByTabId(tabId));
    }
}
