package jpasecurity.jpasecurity.model;

import jpasecurity.jpasecurity.model.dto.note.NoteDto;
import jpasecurity.jpasecurity.model.dto.user.UserDto;
import jpasecurity.jpasecurity.model.entity.Note;
import jpasecurity.jpasecurity.model.entity.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ModelMapper {
    NoteDto toNoteDto(Note note);

    List<NoteDto> toNoteDto(List<Note> note);

    UserDto toUserDto(User user);

    List<UserDto> toUserDto(List<User> user);
}
