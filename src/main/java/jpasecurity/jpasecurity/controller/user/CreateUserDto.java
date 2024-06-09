package jpasecurity.jpasecurity.controller.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateUserDto {
    private String username;
    private String password;
    private String email;
    private String roles;

    public CreateUserDto() {
    }

    public CreateUserDto(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }
}
