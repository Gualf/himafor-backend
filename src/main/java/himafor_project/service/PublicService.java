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
 * Service untuk menyajikan data halaman publik yang diambil secara dinamis dari database.
 */
@Service
@RequiredArgsConstructor
public class PublicService {

    private final NewsRepository newsRepository;
    private final EventRepository eventRepository;
    private final HomeSettingRepository homeSettingRepository;
    private final ProfileSettingRepository profileSettingRepository;
    private final ContactSettingRepository contactSettingRepository;

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

    public PublicOrganizationResponse getOrganizationData() {
        PublicOrganizationResponse.LeaderInfo chairman = PublicOrganizationResponse.LeaderInfo.builder()
                .name("Arya Radi")
                .position("Ketua HIMAFOR")
                .photo("/uploads/arya.jpg")
                .build();

        PublicOrganizationResponse.LeaderInfo viceChairman = PublicOrganizationResponse.LeaderInfo.builder()
                .name("Budi Santoso")
                .position("Wakil Ketua")
                .photo("/uploads/budi.jpg")
                .build();

        List<PublicOrganizationResponse.DepartmentInfo> departments = Arrays.asList(
                PublicOrganizationResponse.DepartmentInfo.builder()
                        .departmentName("Divisi PSDM")
                        .head("Siti Rahma")
                        .membersCount(5)
                        .build(),
                PublicOrganizationResponse.DepartmentInfo.builder()
                        .departmentName("Divisi Humas & Kominfo")
                        .head("Rahmat Hidayat")
                        .membersCount(4)
                        .build()
        );

        return PublicOrganizationResponse.builder()
                .chairman(chairman)
                .viceChairman(viceChairman)
                .departments(departments)
                .build();
    }

    public PublicContactResponse getContactData() {
        ContactSetting contactSetting = contactSettingRepository.findAll().stream().findFirst().orElse(
                ContactSetting.builder()
                        .email("himafor@kampus.ac.id")
                        .instagram("@himafor_official")
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
