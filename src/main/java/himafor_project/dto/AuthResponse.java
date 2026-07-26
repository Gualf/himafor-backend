package himafor_project.dto;

import lombok.*;

/**
 * DTO Response hasil Login Admin beserta Token JWT.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthResponse {

    private Long id;
    private String name;
    private String email;
    private String token;
}
