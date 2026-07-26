package himafor_project.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.time.LocalDate;

/**
 * DTO Request untuk Tambah/Edit Kegiatan (Event).
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventRequest {

    @NotBlank(message = "Judul kegiatan tidak boleh kosong")
    private String title;

    private String description;

    @NotNull(message = "Tanggal kegiatan tidak boleh kosong")
    private LocalDate date;

    @NotBlank(message = "Lokasi kegiatan tidak boleh kosong")
    private String location;

    private String status;
}
