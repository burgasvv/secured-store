package org.burgas.identityservice.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.burgas.identityservice.dto.IdentityRequest;
import org.burgas.identityservice.dto.IdentityRequestEdit;
import org.burgas.identityservice.dto.IdentityResponse;
import org.burgas.identityservice.service.IdentityService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.TEXT_PLAIN_VALUE;

@RestController
@RequiredArgsConstructor
@RequestMapping("/identities")
public class IdentityController {

    private final IdentityService identityService;

    @GetMapping(
            produces = APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<IdentityResponse>> getAllIdentities(HttpServletRequest request) {
        return ResponseEntity.ok(identityService.findAll(request));
    }

    @GetMapping(
            value = "/identity/{identity-id}",
            produces = APPLICATION_JSON_VALUE,
            consumes = TEXT_PLAIN_VALUE
    )
    public ResponseEntity<IdentityResponse> getIdentityByIdentityId(
            @PathVariable(name = "identity-id") Long identityId, HttpServletRequest request
    ) {
        return ResponseEntity.ok(identityService.findById(identityId, request));
    }

    @GetMapping("/{username}")
    public ResponseEntity<IdentityResponse> getIdentityByUsername(
            @PathVariable String username, HttpServletRequest request
    ) {
        return ResponseEntity.ok(identityService.findByUserName(username, request));
    }

    @PostMapping("/create")
    public ResponseEntity<IdentityResponse> createIdentity(
            @RequestBody IdentityRequest identityRequest, HttpServletRequest request
    ) {
        return ResponseEntity.ok(identityService.create(identityRequest, request));
    }

    @PutMapping("/edit")
    public ResponseEntity<IdentityResponse> updateIdentity(
            @RequestBody IdentityRequestEdit identityRequestEdit, HttpServletRequest request
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(identityService.update(identityRequestEdit, request));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteIdentity(@RequestParam Long identityId, HttpServletRequest request) {
        return ResponseEntity.ok(identityService.delete(identityId, request));
    }

    @GetMapping("/tab/{tab-id}")
    public ResponseEntity<IdentityResponse> getIdentityByTabId(
            @PathVariable(name = "tab-id") Long tabId, HttpServletRequest request
    ) {
        return ResponseEntity.ok(identityService.findIdentityByTabId(tabId, request));
    }
}
