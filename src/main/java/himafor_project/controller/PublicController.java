package himafor_project.controller;

import himafor_project.dto.*;
import himafor_project.service.EventService;
import himafor_project.service.NewsService;
import himafor_project.service.PublicService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller untuk Halaman Publik (Tanpa Otentikasi JWT).
 */
@RestController
@RequestMapping("/public")
@RequiredArgsConstructor
public class PublicController {

    private final PublicService publicService;
    private final NewsService newsService;
    private final EventService eventService;

    @GetMapping("/home")
    public ResponseEntity<ApiResponse<PublicHomeResponse>> getHome() {
        PublicHomeResponse data = publicService.getHomeData();
        return ResponseEntity.ok(ApiResponse.success("Data beranda berhasil diambil", data));
    }

    @GetMapping("/profile")
    public ResponseEntity<ApiResponse<PublicProfileResponse>> getProfile() {
        PublicProfileResponse data = publicService.getProfileData();
        return ResponseEntity.ok(ApiResponse.success("Profil HIMAFOR berhasil diambil", data));
    }

    @GetMapping("/organization")
    public ResponseEntity<ApiResponse<PublicOrganizationResponse>> getOrganization() {
        PublicOrganizationResponse data = publicService.getOrganizationData();
        return ResponseEntity.ok(ApiResponse.success("Struktur organisasi berhasil diambil", data));
    }

    @GetMapping("/news")
    public ResponseEntity<ApiResponse<List<NewsResponse>>> getPublicNews(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int limit,
            @RequestParam(required = false) String search) {
        List<NewsResponse> data = newsService.getAllNews(page, limit, search);
        return ResponseEntity.ok(ApiResponse.success("Daftar berita publik berhasil diambil", data));
    }

    @GetMapping("/news/{slug}")
    public ResponseEntity<ApiResponse<NewsResponse>> getNewsBySlug(@PathVariable String slug) {
        NewsResponse data = newsService.getNewsBySlug(slug);
        return ResponseEntity.ok(ApiResponse.success("Detail berita berhasil diambil", data));
    }

    @GetMapping("/events")
    public ResponseEntity<ApiResponse<List<EventResponse>>> getPublicEvents() {
        List<EventResponse> data = eventService.getAllEvents();
        return ResponseEntity.ok(ApiResponse.success("Daftar event publik berhasil diambil", data));
    }

    @GetMapping("/events/{id}")
    public ResponseEntity<ApiResponse<EventResponse>> getPublicEventById(@PathVariable Long id) {
        EventResponse data = eventService.getEventById(id);
        return ResponseEntity.ok(ApiResponse.success("Detail event berhasil diambil", data));
    }

    @GetMapping("/contact")
    public ResponseEntity<ApiResponse<PublicContactResponse>> getContact() {
        PublicContactResponse data = publicService.getContactData();
        return ResponseEntity.ok(ApiResponse.success("Informasi kontak berhasil diambil", data));
    }
}
