package himafor_project.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

/**
 * DTO Request untuk Mengubah Konfigurasi Beranda Utama.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HomeSettingRequest {

    @NotBlank(message = "Hero title tidak boleh kosong")
    @JsonProperty("hero_title")
    private String heroTitle;

    @NotBlank(message = "Hero subtitle tidak boleh kosong")
    @JsonProperty("hero_subtitle")
    private String heroSubtitle;
}
