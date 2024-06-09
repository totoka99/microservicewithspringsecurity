package jpasecurity.jpasecurity.service.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "notes")
@Getter
@Setter
@NoArgsConstructor
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private boolean checked;
    @ManyToOne
    private User user;

    public Note(String name, String description) {
        this.name = name;
        this.description = description;
    }
}

