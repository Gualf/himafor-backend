package himafor_project.dto;

import lombok.*;

/**
 * DTO Response untuk Halaman Kontak Publik.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PublicContactResponse {

    private String email;
    private String instagram;
    private String address;
}
