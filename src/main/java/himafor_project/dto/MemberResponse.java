package himafor_project.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import java.time.LocalDateTime;

/**
 * DTO Response data Anggota.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberResponse {

    private Long id;
    private String name;
    private String position;
    private String photo;
    private String period;

    @JsonProperty("created_at")
    private LocalDateTime createdAt;
}
