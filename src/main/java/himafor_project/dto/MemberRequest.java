package himafor_project.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

/**
 * DTO Request untuk Tambah/Edit Data Anggota.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberRequest {

    @NotBlank(message = "Nama anggota tidak boleh kosong")
    private String name;

    @NotBlank(message = "Jabatan tidak boleh kosong")
    private String position;

    private String photo;

    @NotBlank(message = "Periode kepengurusan tidak boleh kosong")
    private String period;
}
