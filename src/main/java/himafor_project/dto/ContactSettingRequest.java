package himafor_project.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

/**
 * DTO Request untuk Mengubah Konfigurasi Kontak HIMAFOR.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContactSettingRequest {

    @NotBlank(message = "Email tidak boleh kosong")
    @Email(message = "Format email tidak valid")
    private String email;

    @NotBlank(message = "Instagram tidak boleh kosong")
    private String instagram;

    @NotBlank(message = "Alamat tidak boleh kosong")
    private String address;
}
