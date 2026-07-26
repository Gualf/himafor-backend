package himafor_project.controller;

import himafor_project.dto.ApiResponse;
import himafor_project.dto.NewsRequest;
import himafor_project.dto.NewsResponse;
import himafor_project.service.NewsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Controller untuk CRUD Berita & Artikel dengan dukungan upload file thumbnail langsung.
 */
@RestController
@RequestMapping("/news")
@RequiredArgsConstructor
public class NewsController {

    private final NewsService newsService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<NewsResponse>>> getAllNews(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int limit,
            @RequestParam(required = false) String search) {

        List<NewsResponse> list = newsService.getAllNews(page, limit, search);
        return ResponseEntity.ok(ApiResponse.success("Daftar berita berhasil diambil", list));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<NewsResponse>> getNewsById(@PathVariable Long id) {
        NewsResponse response = newsService.getNewsById(id);
        return ResponseEntity.ok(ApiResponse.success("Detail berita berhasil diambil", response));
    }

    /**
     * Tambah Berita baru dengan upload thumbnail file langsung (multipart/form-data).
     */
    @PostMapping(consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<ApiResponse<NewsResponse>> createNewsWithFile(
            @RequestParam("title") String title,
            @RequestParam("content") String content,
            @RequestParam(value = "thumbnail", required = false) MultipartFile thumbnailFile) {

        NewsRequest request = NewsRequest.builder()
                .title(title)
                .content(content)
                .build();

        NewsResponse response = newsService.createNews(request, thumbnailFile);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Berita berhasil dibuat", response));
    }

    /**
     * Tambah Berita baru via Payload JSON.
     */
    @PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<ApiResponse<NewsResponse>> createNewsJson(@Valid @RequestBody NewsRequest request) {
        NewsResponse response = newsService.createNews(request, null);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Berita berhasil dibuat", response));
    }

    /**
     * Edit Berita dengan upload thumbnail file baru (multipart/form-data).
     */
    @PatchMapping(value = "/{id}", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<ApiResponse<NewsResponse>> updateNewsWithFile(
            @PathVariable Long id,
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "content", required = false) String content,
            @RequestParam(value = "thumbnail", required = false) MultipartFile thumbnailFile) {

        NewsRequest request = NewsRequest.builder()
                .title(title)
                .content(content)
                .build();

        NewsResponse response = newsService.updateNews(id, request, thumbnailFile);
        return ResponseEntity.ok(ApiResponse.success("Berita berhasil diperbarui", response));
    }

    /**
     * Edit Berita via Payload JSON.
     */
    @PatchMapping(value = "/{id}", consumes = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<ApiResponse<NewsResponse>> updateNewsJson(
            @PathVariable Long id,
            @RequestBody NewsRequest request) {
        NewsResponse response = newsService.updateNews(id, request, null);
        return ResponseEntity.ok(ApiResponse.success("Berita berhasil diperbarui", response));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Object>> deleteNews(@PathVariable Long id) {
        newsService.deleteNews(id);
        return ResponseEntity.ok(ApiResponse.successMessage("Berita berhasil dihapus"));
    }
}
