package jpasecurity.jpasecurity.service.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private String email;
    private String roles;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Note> noteList;
    private String pwResetCode;
    private LocalDateTime pwResetCodeValidUntil;

    public void addNote(Note note) {
        note.setUser(this);
        if (noteList != null) {
            noteList.add(note);
        } else {
            noteList = new LinkedList<>();
            noteList.add(note);
        }
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
