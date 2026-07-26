package himafor_project.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

/**
 * DTO Response untuk Statistik Dashboard Admin.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DashboardResponse {

    @JsonProperty("total_members")
    private Long totalMembers;

    @JsonProperty("total_news")
    private Long totalNews;

    @JsonProperty("total_events")
    private Long totalEvents;
}
