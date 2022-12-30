package jpasecurity.jpasecurity.expcetion;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UsernameIsTakenException extends RuntimeException {
    private final String username;
}
