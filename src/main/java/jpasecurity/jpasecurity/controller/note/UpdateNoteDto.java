package jpasecurity.jpasecurity.controller.note;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateNoteDto {
    private Long id;
    private String name;
    private String description;
    @JsonProperty("isChecked")
    private boolean checked;
}
