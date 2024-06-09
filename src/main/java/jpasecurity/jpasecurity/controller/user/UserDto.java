package jpasecurity.jpasecurity.controller.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {
    private long id;
    private String username;
    private String email;

    public UserDto() {
    }

    public UserDto(long id, String username, String email) {
        this.id = id;
        this.username = username;
        this.email = email;
    }
}
