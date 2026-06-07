package com.restaurant.auth.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AuthResponse {

    private String token;
    private String tipo;
    private String username;
    private String rol;
    private Long expiresIn;
}
