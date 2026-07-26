package himafor_project.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import java.util.List;

/**
 * DTO Response untuk Halaman Beranda Publik.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PublicHomeResponse {

    private HeroSection hero;

    @JsonProperty("latest_news")
    private List<NewsResponse> latestNews;

    @JsonProperty("upcoming_events")
    private List<EventResponse> upcomingEvents;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class HeroSection {
        private String title;
        private String subtitle;
    }
}
