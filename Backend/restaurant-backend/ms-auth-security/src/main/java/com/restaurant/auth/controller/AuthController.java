package com.restaurant.auth.controller;

import com.restaurant.auth.dto.request.LoginRequest;
import com.restaurant.auth.dto.request.RegisterRequest;
import com.restaurant.auth.dto.response.AuthResponse;
import com.restaurant.auth.service.AuthService;
import com.restaurant.common.dto.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Autenticación", description = "Login y registro de usuarios")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Registrar nuevo usuario")
    public ApiResponse<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        return ApiResponse.ok("Usuario registrado correctamente", authService.register(request));
    }

    @PostMapping("/login")
    @Operation(summary = "Iniciar sesión y obtener token JWT")
    public ApiResponse<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        return ApiResponse.ok("Login exitoso", authService.login(request));
    }
}
