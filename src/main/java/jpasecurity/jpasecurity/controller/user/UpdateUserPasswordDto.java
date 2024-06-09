package jpasecurity.jpasecurity.controller.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUserPasswordDto {
    @NotBlank
    private String password;

    public UpdateUserPasswordDto(String password) {
        this.password = password;
    }
}
