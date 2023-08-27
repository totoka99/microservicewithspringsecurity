package jpasecurity.jpasecurity.expcetion;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class NoteNotFoundException extends RuntimeException {
    private final Long id;
}
