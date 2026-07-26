package himafor_project.dto;

import lombok.*;
import java.util.List;

/**
 * DTO Response untuk Halaman Profil HIMAFOR (Visi, Misi & Sejarah).
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PublicProfileResponse {

    private String name;
    private String vision;
    private List<String> mission;
    private String history;
}
