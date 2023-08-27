package jpasecurity.jpasecurity.model.dto.note;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateNoteDto {
    private String name;
    private String description;
}
