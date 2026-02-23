package com.cacao.auth.dto;

import java.util.List;

public record LoginResponse(
    String token,
    String tokenType,
    String username,
    List<String> roles
) {
    public LoginResponse {
        if (token == null || token.isEmpty()) {
            throw new IllegalArgumentException("Token no puede ser nulo o vacío");
        }
        if (username == null || username.isEmpty()) {
            throw new IllegalArgumentException("Usuario no puede ser nulo o vacío");
        }
    }
}
