package jpasecurity.jpasecurity.expcetion;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ExampleNotFoundException extends RuntimeException {
    private final Long id;
}
