package jpasecurity.jpasecurity.model.dto.note;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NoteDto {
    private Long id;
    private String name;
    private String description;
}
