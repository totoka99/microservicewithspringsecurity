package jpasecurity.jpasecurity.expcetion;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class EmailAddressIsTakenException extends RuntimeException {
    private final String username;
}
