package com.hxun.neuromanagement.controller;

import com.hxun.neuromanagement.dto.AuthResponseDto;
import com.hxun.neuromanagement.dto.UserLoginDto;
import com.hxun.neuromanagement.dto.UserRegistrationDto;
import com.hxun.neuromanagement.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AuthController {
    
    private final UserService userService;
    
    @PostMapping("/register")
    public ResponseEntity<AuthResponseDto> register(@Valid @RequestBody UserRegistrationDto registrationDto) {
        AuthResponseDto response = userService.registerUser(registrationDto);
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@Valid @RequestBody UserLoginDto loginDto) {
        AuthResponseDto response = userService.loginUser(loginDto);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Neuro-Management Auth Service is running!");
    }
} 