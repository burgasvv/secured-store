package org.burgas.orderservice.controller;

import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import org.burgas.orderservice.dto.TabResponse;
import org.burgas.orderservice.service.TabService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tabs")
public class TabController {

    private final TabService tabService;

    @GetMapping("/tab/unauthorized")
    public ResponseEntity<TabResponse> getUnauthorizedAccountTab(
            @CookieValue(name = "unauthorized-cookie") Cookie unauthorizedCookie
    ) {
        return ResponseEntity.ok(tabService.findUnauthorizedAccountTab(unauthorizedCookie));
    }

    @PostMapping("/close-unauthorized-account-tab")
    public ResponseEntity<String> closeUnauthorizedAccountTab(
            @CookieValue(name = "unauthorized-cookie") Cookie unauthorizedCookie
    ) {
        return ResponseEntity.ok(tabService.closeUnauthorizedAccountTab(unauthorizedCookie));
    }

    @PostMapping("/close-tab")
    public ResponseEntity<String> closeTab(@RequestParam Long tabId) {
        return ResponseEntity.ok(tabService.closeTab(tabId));
    }

    @GetMapping("/identity/{identity-id}")
    public ResponseEntity<List<TabResponse>> getTabsByIdentityId(
            @PathVariable(name = "identity-id") Long identityId
    ) {
        return ResponseEntity.ok(tabService.findTabsByIdentityId(identityId));
    }

    @GetMapping("/identity/{identity-id}/{tab-id}")
    public ResponseEntity<TabResponse> getTabByIdentityAndTab(
            @PathVariable(name = "identity-id") Long identityId,
            @PathVariable(name = "tab-id") Long tabId
    ) {
        return ResponseEntity.ok(tabService.findTabByIdentityIdAndTabId(identityId, tabId));
    }
}
