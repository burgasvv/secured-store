package org.burgas.orderservice.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
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
            @CookieValue(name = "unauthorized-cookie") Cookie unauthorizedCookie, HttpServletRequest request
    ) {
        return ResponseEntity.ok(tabService.findUnauthorizedAccountTab(unauthorizedCookie, request));
    }

    @PostMapping("/finish-unauthorized-account-tab")
    public ResponseEntity<String> closeUnauthorizedAccountTab(
            @CookieValue(name = "unauthorized-cookie") Cookie unauthorizedCookie, HttpServletRequest request
    ) {
        return ResponseEntity.ok(tabService.closeUnauthorizedAccountTab(unauthorizedCookie, request));
    }

    @PostMapping("/finish-tab")
    public ResponseEntity<String> closeTab(@RequestParam Long tabId, HttpServletRequest request) {
        return ResponseEntity.ok(tabService.closeTab(tabId, request));
    }

    @DeleteMapping("/delete-tab")
    public ResponseEntity<String> deleteTabIfNotFinished(@RequestParam Long tabId, HttpServletRequest request) {
        return ResponseEntity.ok(tabService.deleteTabByIdIfNotFinished(tabId, request));
    }

    @GetMapping("/identity/{identity-id}")
    public ResponseEntity<List<TabResponse>> getTabsByIdentityId(
            @PathVariable(name = "identity-id") Long identityId, HttpServletRequest request
    ) {
        return ResponseEntity.ok(tabService.findTabsByIdentityId(identityId, request));
    }

    @GetMapping("/identity/{identity-id}/{tab-id}")
    public ResponseEntity<TabResponse> getTabByIdentityAndTab(
            @PathVariable(name = "identity-id") Long identityId,
            @PathVariable(name = "tab-id") Long tabId, HttpServletRequest request
    ) {
        return ResponseEntity.ok(tabService.findTabByIdentityIdAndTabId(identityId, tabId, request));
    }
}
