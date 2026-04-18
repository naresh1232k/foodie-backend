package com.example.FoodieBackend.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.FoodieBackend.Dto.AuthResponse;
import com.example.FoodieBackend.Dto.LoginRequest;
import com.example.FoodieBackend.Dto.Registerrequest;
import com.example.FoodieBackend.Dto.Userdto;
import com.example.FoodieBackend.Models.User;
import com.example.FoodieBackend.Repository.UserRepository;
import com.example.FoodieBackend.Security.JwtUtil;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthResponse register(Registerrequest req) {
        if (userRepository.existsByEmail(req.getEmail())) {
            throw new RuntimeException("Email already registered");
        }

        User user = User.builder()
                .name(req.getName())
                .email(req.getEmail())
                .password(passwordEncoder.encode(req.getPassword()))
                .role(User.Role.USER)
                .active(true)
                .build();

        user = userRepository.save(user);

        String token = jwtUtil.generateToken(
                user.getEmail(),
                user.getRole().name(),
                user.getId()
        );

        return AuthResponse.builder()
                .user(Userdto.from(user))
                .token(token)
                .build();
    }

    public AuthResponse login(LoginRequest req) {
        User user = userRepository.findByEmail(req.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        if (!passwordEncoder.matches(req.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }

        if (!user.isActive()) {
            throw new RuntimeException("Account has been deactivated. Contact support.");
        }

        String token = jwtUtil.generateToken(
                user.getEmail(),
                user.getRole().name(),
                user.getId()
        );

        return AuthResponse.builder()
                .user(Userdto.from(user))
                .token(token)
                .build();
    }

    public AuthResponse adminLogin(LoginRequest req) {
        AuthResponse response = login(req);

        if (!"ADMIN".equals(response.getUser().getRole())) {
            throw new RuntimeException("Access denied: not an admin account");
        }

        return response;
    }
}