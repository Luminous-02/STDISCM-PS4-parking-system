package com.parkingsystem.auth.controller;

import com.parkingsystem.auth.dto.LoginRequest;
import com.parkingsystem.auth.dto.LoginResponse;
import com.parkingsystem.auth.model.User;
import com.parkingsystem.auth.model.UserRole;
import com.parkingsystem.auth.service.AuthService;
import com.parkingsystem.auth.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {
    
    @Autowired
    private AuthService authService;
    
    @Autowired
    private UserService userService;
    
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        LoginResponse response = authService.login(loginRequest);
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/refresh")
    public ResponseEntity<LoginResponse> refreshToken(@RequestBody Map<String, String> request) {
        String refreshToken = request.get("refreshToken");
        LoginResponse response = authService.refreshToken(refreshToken);
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/logout")
    public ResponseEntity<Map<String, String>> logout(@RequestBody Map<String, String> request) {
        String refreshToken = request.get("refreshToken");
        authService.logout(refreshToken);
        
        Map<String, String> response = new HashMap<>();
        response.put("message", "Logged out successfully");
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam String email,
            @RequestParam UserRole role) {
        
        User user = userService.registerUser(username, password, email, role);
        
        Map<String, String> response = new HashMap<>();
        response.put("message", "User registered successfully");
        response.put("userId", user.getId().toString());
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/test")
    public String test() {
        return "Parking Auth Service is working!";
    }
}