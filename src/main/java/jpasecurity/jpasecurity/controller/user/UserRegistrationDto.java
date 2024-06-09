package jpasecurity.jpasecurity.controller.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRegistrationDto {
    private String username;
    private String password;
    private String email;
}
