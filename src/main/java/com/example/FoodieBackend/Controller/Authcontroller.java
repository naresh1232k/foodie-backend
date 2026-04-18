package com.example.FoodieBackend.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.FoodieBackend.Dto.*;
import com.example.FoodieBackend.Repository.UserRepository;
import com.example.FoodieBackend.Service.AuthService;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class Authcontroller {

    private final AuthService authService;
    private final UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody Registerrequest req) {
        try {
            return ResponseEntity.ok(authService.register(req));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500)
                    .body(Map.of("message", e.getMessage()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest req) {
        try {
            AuthResponse response = authService.login(req);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            e.printStackTrace(); // 🔥 shows real error in backend console

            return ResponseEntity
                    .status(500)
                    .body(Map.of(
                            "message", e.getMessage()
                    ));
        }
    }

    @PostMapping("/admin/login")
    public ResponseEntity<AuthResponse> adminLogin(@Valid @RequestBody LoginRequest req) {
        return ResponseEntity.ok(authService.adminLogin(req));
    }

    @GetMapping("/me")
    public ResponseEntity<?> me(org.springframework.security.core.Authentication auth) {

        if (auth == null) {
            return ResponseEntity.status(401)
                    .body(Map.of("message", "Not authenticated"));
        }

        String email = auth.getName();

        var user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return ResponseEntity.ok(Userdto.from(user));
    }
}