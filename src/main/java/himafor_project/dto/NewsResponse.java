package himafor_project.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import java.time.LocalDateTime;

/**
 * DTO Response data Berita.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NewsResponse {

    private Long id;
    private String title;
    private String slug;
    private String content;
    private String snippet;
    private String thumbnail;

    @JsonProperty("created_at")
    private LocalDateTime createdAt;
}
