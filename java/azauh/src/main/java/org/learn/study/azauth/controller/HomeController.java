package org.learn.study.azauth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
public class HomeController {

    @GetMapping("/home")
    public ResponseEntity<Map<String, String>> home(@AuthenticationPrincipal OAuth2User principal) {
        // Use 'name', 'preferred_username', or 'sub' as fallback
        String username = principal.getAttribute("name");
        if (username == null) {
            username = principal.getAttribute("preferred_username");
        }
        if (username == null) {
            username = principal.getAttribute("sub"); // 'sub' is always present
        }

        // Create a JSON response
        Map<String, String> response = new HashMap<>();
        response.put("message", "Hello, " + (username != null ? username : "User"));

        return ResponseEntity.ok(response);
    }

    @GetMapping("/public/hello")
    public ResponseEntity<Map<String, String>> publicHello() {
        // Create a JSON response
        Map<String, String> response = new HashMap<>();
        response.put("message", "This is a public endpoint");

        return ResponseEntity.ok(response);
    }

    @GetMapping("/api/secure")
    public ResponseEntity<Map<String, Object>> secureEndpoint(@AuthenticationPrincipal OAuth2User principal) {
        // Return user details from the token
        return ResponseEntity.ok(Collections.singletonMap("userDetails", principal.getAttributes()));
    }

}