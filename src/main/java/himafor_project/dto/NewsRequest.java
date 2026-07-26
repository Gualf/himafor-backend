package himafor_project.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

/**
 * DTO Request untuk Tambah/Edit Berita.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NewsRequest {

    @NotBlank(message = "Judul berita tidak boleh kosong")
    private String title;

    @NotBlank(message = "Isi berita tidak boleh kosong")
    private String content;

    private String thumbnail;
}
