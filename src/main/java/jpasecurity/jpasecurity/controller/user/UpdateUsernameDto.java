package jpasecurity.jpasecurity.controller.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUsernameDto {
    @NotBlank
    private String password;

    public UpdateUsernameDto(String password) {
        this.password = password;
    }
}
