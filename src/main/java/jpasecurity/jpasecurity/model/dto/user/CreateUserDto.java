package jpasecurity.jpasecurity.model.dto.user;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserDto {
    @NotBlank(message = "username cant be empty")
    private String username;
    @NotBlank(message = "password cant be empty")
    private String password;
    @NotBlank(message = "roles must be given options: ROLE_USER,ROLE_ADMIN")
    private String roles;
}
