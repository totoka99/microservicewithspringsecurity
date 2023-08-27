package jpasecurity.jpasecurity.model.dto.user.update;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUsernameDto {
    @NotBlank
    private String username;
}
