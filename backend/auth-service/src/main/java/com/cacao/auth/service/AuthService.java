package com.cacao.auth.service;

import com.cacao.auth.dto.LoginRequest;
import com.cacao.auth.dto.LoginResponse;
import com.cacao.auth.exception.AuthenticationException;
import com.cacao.auth.model.User;
import com.cacao.auth.repository.UserRepository;
import com.cacao.auth.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public LoginResponse authenticate(LoginRequest request) {
        // Buscar usuario por username
        User user = userRepository.findByUsername(request.username())
            .orElseThrow(() -> new AuthenticationException("Usuario o contraseña inválidos"));

        // Validar contraseña
        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new AuthenticationException("Usuario o contraseña inválidos");
        }

        // Validar si el usuario está activo
        if (!user.getIsActive()) {
            throw new AuthenticationException("La cuenta de usuario está desactivada");
        }

        // Generar token
        String token = JwtUtil.generateToken(user.getUsername());

        return new LoginResponse(token, "Bearer", user.getUsername(), List.of("ROLE_USER"));
    }

    public void logout(String token) {
        // En implementación con Redis o BD, aquí se invalidaría el token
    }

    public boolean validateToken(String token) {
        return JwtUtil.validateToken(token);
    }
}
