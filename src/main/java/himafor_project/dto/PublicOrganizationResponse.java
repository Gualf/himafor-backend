package himafor_project.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import java.util.List;

/**
 * DTO Response untuk Halaman Struktur Organisasi Publik.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PublicOrganizationResponse {

    private LeaderInfo chairman;

    @JsonProperty("vice_chairman")
    private LeaderInfo viceChairman;

    private List<DepartmentInfo> departments;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class LeaderInfo {
        private String name;
        private String position;
        private String photo;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class DepartmentInfo {
        @JsonProperty("department_name")
        private String departmentName;
        private String head;

        @JsonProperty("members_count")
        private Integer membersCount;
    }
}
