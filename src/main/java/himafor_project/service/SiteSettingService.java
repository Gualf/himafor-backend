package himafor_project.service;

import himafor_project.dto.*;
import himafor_project.model.*;
import himafor_project.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * Service untuk mengelola (CRUD/Update) Konfigurasi Halaman Beranda, Profil, dan Kontak oleh Admin.
 */
@Service
@RequiredArgsConstructor
public class SiteSettingService {

    private final HomeSettingRepository homeSettingRepository;
    private final ProfileSettingRepository profileSettingRepository;
    private final ContactSettingRepository contactSettingRepository;

    // --- HOME SETTING ---
    public HomeSetting getHomeSetting() {
        return homeSettingRepository.findAll().stream().findFirst().orElseGet(() ->
                homeSettingRepository.save(HomeSetting.builder()
                        .heroTitle("Selamat Datang di Website Resmi HIMAFOR")
                        .heroSubtitle("Wadah Aspirasi dan Pengembangan Mahasiswa Informatika")
                        .build())
        );
    }

    public HomeSetting updateHomeSetting(HomeSettingRequest request) {
        HomeSetting setting = getHomeSetting();
        setting.setHeroTitle(request.getHeroTitle());
        setting.setHeroSubtitle(request.getHeroSubtitle());
        return homeSettingRepository.save(setting);
    }

    // --- PROFILE SETTING ---
    public ProfileSetting getProfileSetting() {
        return profileSettingRepository.findAll().stream().findFirst().orElseGet(() ->
                profileSettingRepository.save(ProfileSetting.builder()
                        .name("HIMAFOR (Himpunan Mahasiswa Informatika)")
                        .vision("Menjadi himpunan mahasiswa yang solutif, prestatif, dan berintegritas.")
                        .mission("Meningkatkan kualitas akademik dan keterampilan mahasiswa.;Mempererat rasa kekeluargaan antar mahasiswa informatika.")
                        .history("HIMAFOR didirikan pada tahun 2015...")
                        .build())
        );
    }

    public ProfileSetting updateProfileSetting(ProfileSettingRequest request) {
        ProfileSetting setting = getProfileSetting();
        if (request.getName() != null) setting.setName(request.getName());
        if (request.getVision() != null) setting.setVision(request.getVision());
        if (request.getMission() != null) {
            setting.setMission(String.join(";", request.getMission()));
        }
        if (request.getHistory() != null) setting.setHistory(request.getHistory());

        return profileSettingRepository.save(setting);
    }

    // --- CONTACT SETTING ---
    public ContactSetting getContactSetting() {
        return contactSettingRepository.findAll().stream().findFirst().orElseGet(() ->
                contactSettingRepository.save(ContactSetting.builder()
                        .email("himafor@kampus.ac.id")
                        .instagram("@himafor_official")
                        .address("Gedung Student Center Lt. 2, Jl. Kampus Utama No. 1")
                        .build())
        );
    }

    public ContactSetting updateContactSetting(ContactSettingRequest request) {
        ContactSetting setting = getContactSetting();
        if (request.getEmail() != null) setting.setEmail(request.getEmail());
        if (request.getInstagram() != null) setting.setInstagram(request.getInstagram());
        if (request.getAddress() != null) setting.setAddress(request.getAddress());

        return contactSettingRepository.save(setting);
    }
}
