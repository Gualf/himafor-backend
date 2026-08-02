package himafor_project.service;

import himafor_project.dto.*;
import himafor_project.model.*;
import himafor_project.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service untuk menyajikan data halaman publik yang diambil secara dinamis dari database[cite: 2].
 */
@Service
@RequiredArgsConstructor
public class PublicService {

    private final NewsRepository newsRepository;
    private final EventRepository eventRepository;
    private final HomeSettingRepository homeSettingRepository;
    private final ProfileSettingRepository profileSettingRepository;
    private final ContactSettingRepository contactSettingRepository;
    private final MemberRepository memberRepository;

    // 1. DATA BERANDA (Tadi sempat terhapus, sekarang sudah dikembalikan)[cite: 2]
    public PublicHomeResponse getHomeData() {
        List<NewsResponse> latestNews = newsRepository.findTop5ByOrderByCreatedAtDesc().stream()
                .map(news -> NewsResponse.builder()
                        .id(news.getId())
                        .title(news.getTitle())
                        .slug(news.getSlug())
                        .thumbnail(news.getThumbnail())
                        .createdAt(news.getCreatedAt())
                        .build())
                .collect(Collectors.toList());

        List<EventResponse> upcomingEvents = eventRepository.findTop5ByOrderByDateDesc().stream()
                .map(event -> EventResponse.builder()
                        .id(event.getId())
                        .title(event.getTitle())
                        .date(event.getDate())
                        .location(event.getLocation())
                        .status(event.getStatus())
                        .build())
                .collect(Collectors.toList());

        HomeSetting homeSetting = homeSettingRepository.findAll().stream().findFirst().orElse(
                HomeSetting.builder()
                        .heroTitle("Selamat Datang di Website Resmi HIMAFOR")
                        .heroSubtitle("Wadah Aspirasi dan Pengembangan Mahasiswa Informatika")
                        .build()
        );

        PublicHomeResponse.HeroSection hero = PublicHomeResponse.HeroSection.builder()
                .title(homeSetting.getHeroTitle())
                .subtitle(homeSetting.getHeroSubtitle())
                .build();

        return PublicHomeResponse.builder()
                .hero(hero)
                .latestNews(latestNews)
                .upcomingEvents(upcomingEvents)
                .build();
    }

    // 2. DATA PROFIL[cite: 2]
    public PublicProfileResponse getProfileData() {
        ProfileSetting profileSetting = profileSettingRepository.findAll().stream().findFirst().orElse(
                ProfileSetting.builder()
                        .name("HIMAFOR (Himpunan Mahasiswa Informatika)")
                        .vision("Menjadi himpunan mahasiswa yang solutif, prestatif, dan berintegritas.")
                        .mission("Meningkatkan kualitas akademik dan keterampilan mahasiswa.;Mempererat rasa kekeluargaan antar mahasiswa informatika.")
                        .history("HIMAFOR didirikan sebagai wadah pengorganisasian dan aspirasi mahasiswa rumpun informatika...")
                        .build()
        );

        List<String> missionList = Arrays.asList(profileSetting.getMission().split(";"));

        return PublicProfileResponse.builder()
                .name(profileSetting.getName())
                .vision(profileSetting.getVision())
                .mission(missionList)
                .history(profileSetting.getHistory())
                .build();
    }

    // 3. DATA ORGANISASI (Hanya pakai versi final yang dinamis ini)
    public PublicOrganizationResponse getOrganizationData() {
        List<Member> allMembers = memberRepository.findAll();

        // 1. Kenali semua nama divisi yang ada (Contoh: "psdm", "puskominfo")
        List<String> divNames = allMembers.stream()
                .filter(m -> m.getPosition().toLowerCase().contains("kadiv") || m.getPosition().toLowerCase().contains("kepala"))
                .map(m -> m.getPosition().replaceAll("(?i)kadiv|kepala", "").trim().toLowerCase())
                .collect(Collectors.toList());

        // 2. Ketua & Wakil Ketua
        PublicOrganizationResponse.LeaderInfo chairman = allMembers.stream()
                .filter(m -> m.getPosition().toLowerCase().contains("ketua") && !m.getPosition().toLowerCase().contains("wakil"))
                .findFirst()
                .map(m -> PublicOrganizationResponse.LeaderInfo.builder().name(m.getName()).position(m.getPosition()).photo(m.getPhoto()).build())
                .orElse(null); 

        PublicOrganizationResponse.LeaderInfo viceChairman = allMembers.stream()
                .filter(m -> m.getPosition().toLowerCase().contains("wakil") || m.getPosition().toLowerCase().contains("wakahim"))
                .findFirst()
                .map(m -> PublicOrganizationResponse.LeaderInfo.builder().name(m.getName()).position(m.getPosition()).photo(m.getPhoto()).build())
                .orElse(null);

        // 3. Sekretaris BPH (PASTIKAN bukan sekretaris divisi)
        List<PublicOrganizationResponse.LeaderInfo> secretaries = allMembers.stream()
                .filter(m -> m.getPosition().toLowerCase().contains("sekretaris"))
                .filter(m -> {
                    String pos = m.getPosition().toLowerCase();
                    // Buang anggota jika jabatannya mengandung nama divisi (misal: "Sekretaris PSDM")
                    return divNames.stream().noneMatch(pos::contains);
                })
                .map(m -> PublicOrganizationResponse.LeaderInfo.builder().name(m.getName()).position(m.getPosition()).photo(m.getPhoto()).build())
                .collect(Collectors.toList());

        // 4. Bendahara BPH (Sama seperti sekretaris)
        List<PublicOrganizationResponse.LeaderInfo> treasurers = allMembers.stream()
                .filter(m -> m.getPosition().toLowerCase().contains("bendahara"))
                .filter(m -> {
                    String pos = m.getPosition().toLowerCase();
                    return divNames.stream().noneMatch(pos::contains);
                })
                .map(m -> PublicOrganizationResponse.LeaderInfo.builder().name(m.getName()).position(m.getPosition()).photo(m.getPhoto()).build())
                .collect(Collectors.toList());

        // 5. Struktur Divisi
        List<PublicOrganizationResponse.DepartmentInfo> departments = allMembers.stream()
                .filter(m -> m.getPosition().toLowerCase().contains("kadiv") || m.getPosition().toLowerCase().contains("kepala"))
                .map(m -> {
                    String divName = m.getPosition().replaceAll("(?i)kadiv|kepala", "").trim();
                    String displayName = "Divisi " + divName;
                    String divNameLower = divName.toLowerCase();

                    // AMBIL SEKRETARIS DIVISI
                    List<PublicOrganizationResponse.LeaderInfo> divSecretaries = allMembers.stream()
                            .filter(member -> member.getPosition().toLowerCase().contains(divNameLower) 
                                    && member.getPosition().toLowerCase().contains("sekretaris"))
                            .map(member -> PublicOrganizationResponse.LeaderInfo.builder()
                                    .name(member.getName()).position(member.getPosition()).photo(member.getPhoto()).build())
                            .collect(Collectors.toList());

                    // AMBIL ANGGOTA BIASA (Kecualikan Kepala dan Sekretaris)
                    List<PublicOrganizationResponse.LeaderInfo> divisionMembers = allMembers.stream()
                            .filter(member -> member.getPosition().toLowerCase().contains(divNameLower) 
                                    && !member.getPosition().toLowerCase().contains("kadiv") 
                                    && !member.getPosition().toLowerCase().contains("kepala")
                                    && !member.getPosition().toLowerCase().contains("sekretaris"))
                            .map(member -> PublicOrganizationResponse.LeaderInfo.builder()
                                    .name(member.getName()).position(member.getPosition()).photo(member.getPhoto()).build())
                            .collect(Collectors.toList());

                    return PublicOrganizationResponse.DepartmentInfo.builder()
                            .departmentName(displayName)
                            .head(m.getName())
                            .headPhoto(m.getPhoto())
                            .membersCount(divisionMembers.size())
                            .divisionSecretaries(divSecretaries)
                            .members(divisionMembers)
                            .build();
                })
                .collect(Collectors.toList());

        return PublicOrganizationResponse.builder()
                .chairman(chairman)
                .viceChairman(viceChairman)
                .secretaries(secretaries)
                .treasurers(treasurers)
                .departments(departments)
                .build();
    }

    // 4. DATA KONTAK
    public PublicContactResponse getContactData() {
        ContactSetting contactSetting = contactSettingRepository.findAll().stream().findFirst().orElse(
                ContactSetting.builder()
                        .email("himafor@kampus.ac.id")
                        .instagram("@himafor_unimus")
                        .address("Gedung Student Center Lt. 2, Jl. Kampus Utama No. 1")
                        .build()
        );

        return PublicContactResponse.builder()
                .email(contactSetting.getEmail())
                .instagram(contactSetting.getInstagram())
                .address(contactSetting.getAddress())
                .build();
    }
}