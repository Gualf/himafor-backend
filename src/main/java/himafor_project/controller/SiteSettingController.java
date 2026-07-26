package himafor_project.controller;

import himafor_project.dto.*;
import himafor_project.model.*;
import himafor_project.service.SiteSettingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller Admin untuk Mengelola (CRUD/Update) Beranda Publik, Profil HIMAFOR, dan Kontak.
 */
@RestController
@RequestMapping("/settings")
@RequiredArgsConstructor
public class SiteSettingController {

    private final SiteSettingService siteSettingService;

    // --- MANAJEMEN BERANDA PUBLIK ---
    @GetMapping("/home")
    public ResponseEntity<ApiResponse<HomeSetting>> getHomeSetting() {
        HomeSetting setting = siteSettingService.getHomeSetting();
        return ResponseEntity.ok(ApiResponse.success("Pengaturan Beranda berhasil diambil", setting));
    }

    @PatchMapping("/home")
    public ResponseEntity<ApiResponse<HomeSetting>> updateHomeSetting(@Valid @RequestBody HomeSettingRequest request) {
        HomeSetting updated = siteSettingService.updateHomeSetting(request);
        return ResponseEntity.ok(ApiResponse.success("Pengaturan Beranda berhasil diperbarui", updated));
    }

    // --- MANAJEMEN PROFIL HIMAFOR ---
    @GetMapping("/profile")
    public ResponseEntity<ApiResponse<ProfileSetting>> getProfileSetting() {
        ProfileSetting setting = siteSettingService.getProfileSetting();
        return ResponseEntity.ok(ApiResponse.success("Pengaturan Profil berhasil diambil", setting));
    }

    @PatchMapping("/profile")
    public ResponseEntity<ApiResponse<ProfileSetting>> updateProfileSetting(@Valid @RequestBody ProfileSettingRequest request) {
        ProfileSetting updated = siteSettingService.updateProfileSetting(request);
        return ResponseEntity.ok(ApiResponse.success("Pengaturan Profil berhasil diperbarui", updated));
    }

    // --- MANAJEMEN KONTAK HIMAFOR ---
    @GetMapping("/contact")
    public ResponseEntity<ApiResponse<ContactSetting>> getContactSetting() {
        ContactSetting setting = siteSettingService.getContactSetting();
        return ResponseEntity.ok(ApiResponse.success("Pengaturan Kontak berhasil diambil", setting));
    }

    @PatchMapping("/contact")
    public ResponseEntity<ApiResponse<ContactSetting>> updateContactSetting(@Valid @RequestBody ContactSettingRequest request) {
        ContactSetting updated = siteSettingService.updateContactSetting(request);
        return ResponseEntity.ok(ApiResponse.success("Pengaturan Kontak berhasil diperbarui", updated));
    }
}
