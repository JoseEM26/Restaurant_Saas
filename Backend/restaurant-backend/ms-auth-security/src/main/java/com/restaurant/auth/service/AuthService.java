package com.restaurant.auth.service;

import com.restaurant.auth.dto.request.LoginRequest;
import com.restaurant.auth.dto.request.RegisterRequest;
import com.restaurant.auth.dto.response.AuthResponse;
import com.restaurant.auth.entity.Usuario;
import com.restaurant.auth.repository.UsuarioRepository;
import com.restaurant.auth.util.JwtUtil;
import com.restaurant.common.exception.BusinessException;
import com.restaurant.common.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Transactional
    public AuthResponse register(RegisterRequest request) {
        if (usuarioRepository.existsByUsername(request.getUsername())) {
            throw new BusinessException("El username ya está en uso");
        }
        if (usuarioRepository.existsByEmail(request.getEmail())) {
            throw new BusinessException("El email ya está registrado");
        }

        Usuario usuario = Usuario.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .rol(request.getRol())
                .build();

        usuarioRepository.save(usuario);
        String token = jwtUtil.generateToken(usuario.getUsername(), usuario.getRol().name());

        return buildResponse(usuario, token);
    }

    @Transactional(readOnly = true)
    public AuthResponse login(LoginRequest request) {
        Usuario usuario = usuarioRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        if (!passwordEncoder.matches(request.getPassword(), usuario.getPassword())) {
            throw new BusinessException("Credenciales inválidas");
        }

        String token = jwtUtil.generateToken(usuario.getUsername(), usuario.getRol().name());
        return buildResponse(usuario, token);
    }

    private AuthResponse buildResponse(Usuario usuario, String token) {
        return AuthResponse.builder()
                .token(token)
                .tipo("Bearer")
                .username(usuario.getUsername())
                .rol(usuario.getRol().name())
                .expiresIn(86400000L)
                .build();
    }
}
