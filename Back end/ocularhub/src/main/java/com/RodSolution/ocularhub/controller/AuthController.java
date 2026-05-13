package com.RodSolution.ocularhub.controller;

import com.RodSolution.ocularhub.model.dto.auth.LoginRequestDto;
import com.RodSolution.ocularhub.model.dto.auth.LoginResponseDto;
import com.RodSolution.ocularhub.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping
    public ResponseEntity<LoginResponseDto> login(@Valid @RequestBody LoginRequestDto loginDto) {
        LoginResponseDto loginResponse = authService.login(loginDto);
        return ResponseEntity.ok(loginResponse);
    }

}
