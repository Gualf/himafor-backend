package himafor_project.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * DTO Response data Event / Kegiatan.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventResponse {

    private Long id;
    private String title;
    private String description;
    private LocalDate date;
    private String location;
    private String status;

    @JsonProperty("created_at")
    private LocalDateTime createdAt;
}
