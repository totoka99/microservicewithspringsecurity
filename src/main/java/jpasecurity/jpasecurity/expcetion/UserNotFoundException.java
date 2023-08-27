package jpasecurity.jpasecurity.expcetion;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public class UserNotFoundException extends RuntimeException {
    private Long id;
    private String name;

    public UserNotFoundException(String name) {
        super();
        this.name = name;
    }

    public UserNotFoundException(Long id) {
        super();
        this.id = id;
    }
}
