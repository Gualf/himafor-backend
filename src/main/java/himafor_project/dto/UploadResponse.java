package himafor_project.dto;

import lombok.*;

/**
 * DTO Response hasil Upload File.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UploadResponse {

    private String filename;
    private String url;
    private Long size;
}
