package himafor_project.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import java.util.List;

/**
 * DTO Request untuk Mengubah Konfigurasi Profil HIMAFOR.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProfileSettingRequest {

    @NotBlank(message = "Nama organisasi tidak boleh kosong")
    private String name;

    @NotBlank(message = "Visi tidak boleh kosong")
    private String vision;

    private List<String> mission;

    @NotBlank(message = "Sejarah tidak boleh kosong")
    private String history;
}
