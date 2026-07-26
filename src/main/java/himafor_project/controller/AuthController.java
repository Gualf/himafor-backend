package himafor_project.controller;

import himafor_project.dto.ApiResponse;
import himafor_project.dto.AuthRequest;
import himafor_project.dto.AuthResponse;
import himafor_project.dto.UserProfileResponse;
import himafor_project.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

/**
 * Controller untuk Modul Otentikasi Admin.
 */
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@Valid @RequestBody AuthRequest request) {
        AuthResponse response = authService.login(request);
        return ResponseEntity.ok(ApiResponse.success("Login berhasil", response));
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Object>> logout() {
        return ResponseEntity.ok(ApiResponse.successMessage("Logout berhasil"));
    }

    @GetMapping("/profile")
    public ResponseEntity<ApiResponse<UserProfileResponse>> getProfile(Authentication authentication) {
        UserProfileResponse response = authService.getProfile(authentication.getName());
        return ResponseEntity.ok(ApiResponse.success("Data profil berhasil diambil", response));
    }
}
