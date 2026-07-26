package himafor_project.config;

import himafor_project.model.*;
import himafor_project.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Data Seeder untuk mengisi data awal Administrator dan Setting Publik jika belum ada di database.
 */
@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final HomeSettingRepository homeSettingRepository;
    private final ProfileSettingRepository profileSettingRepository;
    private final ContactSettingRepository contactSettingRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (!userRepository.existsByEmail("admin@himafor.ac.id")) {
            User admin = User.builder()
                    .name("Administrator HIMAFOR")
                    .email("admin@himafor.ac.id")
                    .password(passwordEncoder.encode("password123"))
                    .build();

            userRepository.save(admin);
            System.out.println("Data Seeder: User Admin 'admin@himafor.ac.id' berhasil dibuat!");
        }

        if (homeSettingRepository.count() == 0) {
            HomeSetting homeSetting = HomeSetting.builder()
                    .heroTitle("Selamat Datang di Website Resmi HIMAFOR")
                    .heroSubtitle("Wadah Aspirasi dan Pengembangan Mahasiswa Informatika")
                    .build();
            homeSettingRepository.save(homeSetting);
            System.out.println("Data Seeder: HomeSetting awal berhasil dibuat!");
        }

        if (profileSettingRepository.count() == 0) {
            ProfileSetting profileSetting = ProfileSetting.builder()
                    .name("HIMAFOR (Himpunan Mahasiswa Informatika)")
                    .vision("Menjadi himpunan mahasiswa yang solutif, prestatif, dan berintegritas.")
                    .mission("Meningkatkan kualitas akademik dan keterampilan mahasiswa.;Mempererat rasa kekeluargaan antar mahasiswa informatika.")
                    .history("HIMAFOR didirikan pada tahun 2015 sebagai organisasi kemahasiswaan jurusan Informatika.")
                    .build();
            profileSettingRepository.save(profileSetting);
            System.out.println("Data Seeder: ProfileSetting awal berhasil dibuat!");
        }

        if (contactSettingRepository.count() == 0) {
            ContactSetting contactSetting = ContactSetting.builder()
                    .email("himafor@kampus.ac.id")
                    .instagram("@himafor_official")
                    .address("Gedung Student Center Lt. 2, Jl. Kampus Utama No. 1")
                    .build();
            contactSettingRepository.save(contactSetting);
            System.out.println("Data Seeder: ContactSetting awal berhasil dibuat!");
        }
    }
}
