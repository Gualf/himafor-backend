package himafor_project.service;

import himafor_project.dto.NewsRequest;
import himafor_project.dto.NewsResponse;
import himafor_project.exception.ResourceNotFoundException;
import himafor_project.model.News;
import himafor_project.repository.NewsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.text.Normalizer;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Service untuk manajemen CRUD data Berita & Artikel dengan dukungan upload file thumbnail langsung.
 */
@Service
@RequiredArgsConstructor
public class NewsService {

    private final NewsRepository newsRepository;
    private final FileStorageService fileStorageService;

    private static final Pattern NONLATIN = Pattern.compile("[^\\w-]");
    private static final Pattern WHITESPACE = Pattern.compile("[\\s]");

    public List<NewsResponse> getAllNews(int page, int limit, String search) {
        Pageable pageable = PageRequest.of(page - 1, limit);
        Page<News> newsPage;

        if (search != null && !search.trim().isEmpty()) {
            newsPage = newsRepository.findByTitleContainingIgnoreCase(search.trim(), pageable);
        } else {
            newsPage = newsRepository.findAll(pageable);
        }

        return newsPage.getContent().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public NewsResponse getNewsById(Long id) {
        News news = newsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Berita dengan ID " + id + " tidak ditemukan"));
        return mapToResponse(news);
    }

    public NewsResponse getNewsBySlug(String slug) {
        News news = newsRepository.findBySlug(slug)
                .orElseThrow(() -> new ResourceNotFoundException("Berita dengan slug '" + slug + "' tidak ditemukan"));
        return mapToResponse(news);
    }

    public NewsResponse createNews(NewsRequest request, MultipartFile thumbnailFile) {
        String thumbnailUrl = request.getThumbnail();
        if (thumbnailFile != null && !thumbnailFile.isEmpty()) {
            thumbnailUrl = fileStorageService.storeFileAndGetUrl(thumbnailFile);
        }

        String baseSlug = generateSlug(request.getTitle());
        String slug = baseSlug;
        int count = 1;

        while (newsRepository.existsBySlug(slug)) {
            slug = baseSlug + "-" + count++;
        }

        News news = News.builder()
                .title(request.getTitle())
                .slug(slug)
                .content(request.getContent())
                .thumbnail(thumbnailUrl)
                .build();

        News savedNews = newsRepository.save(news);
        return mapToResponse(savedNews);
    }

    public NewsResponse updateNews(Long id, NewsRequest request, MultipartFile thumbnailFile) {
        News news = newsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Berita dengan ID " + id + " tidak ditemukan"));

        if (thumbnailFile != null && !thumbnailFile.isEmpty()) {
            news.setThumbnail(fileStorageService.storeFileAndGetUrl(thumbnailFile));
        } else if (request.getThumbnail() != null) {
            news.setThumbnail(request.getThumbnail());
        }

        if (request.getTitle() != null && !request.getTitle().isEmpty() && !news.getTitle().equalsIgnoreCase(request.getTitle())) {
            String baseSlug = generateSlug(request.getTitle());
            String slug = baseSlug;
            int count = 1;

            while (newsRepository.existsBySlug(slug) && !slug.equals(news.getSlug())) {
                slug = baseSlug + "-" + count++;
            }
            news.setSlug(slug);
            news.setTitle(request.getTitle());
        }

        if (request.getContent() != null) {
            news.setContent(request.getContent());
        }

        News updatedNews = newsRepository.save(news);
        return mapToResponse(updatedNews);
    }

    public void deleteNews(Long id) {
        News news = newsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Berita dengan ID " + id + " tidak ditemukan"));
        newsRepository.delete(news);
    }

    public NewsResponse mapToResponse(News news) {
        String snippet = news.getContent();
        if (snippet != null && snippet.length() > 150) {
            snippet = snippet.substring(0, 150) + "...";
        }

        return NewsResponse.builder()
                .id(news.getId())
                .title(news.getTitle())
                .slug(news.getSlug())
                .content(news.getContent())
                .snippet(snippet)
                .thumbnail(news.getThumbnail())
                .createdAt(news.getCreatedAt())
                .build();
    }

    private String generateSlug(String input) {
        if (input == null) return "";
        String nowhitespace = WHITESPACE.matcher(input).replaceAll("-");
        String normalized = Normalizer.normalize(nowhitespace, Normalizer.Form.NFD);
        String slug = NONLATIN.matcher(normalized).replaceAll("");
        return slug.toLowerCase(Locale.ENGLISH);
    }
}
