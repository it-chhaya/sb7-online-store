package com.devkh.onlinestore.api.auth.web;

import com.devkh.onlinestore.api.auth.AuthService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/register")
    public Map<String, String> register(@Valid @RequestBody RegisterDto registerDto) throws MessagingException {
        authService.register(registerDto);
        return Map.of("message", "Please check email and verify..!");
    }

    @PostMapping("/verify")
    public Map<String, String> verify(@RequestBody VerifyDto verifyDto) {
        authService.verify(verifyDto);
        return Map.of("message", "Congratulation! Email has been verified..!");
    }

}
