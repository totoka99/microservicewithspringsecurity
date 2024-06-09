package jpasecurity.jpasecurity.service.model;

import jpasecurity.jpasecurity.controller.note.NoteDto;
import jpasecurity.jpasecurity.controller.user.UserDto;
import jpasecurity.jpasecurity.service.model.entity.Note;
import jpasecurity.jpasecurity.service.model.entity.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ModelMapper {
    NoteDto toNoteDto(Note note);

    List<NoteDto> toNoteDto(List<Note> note);

    UserDto toUserDto(User user);

    List<UserDto> toUserDto(List<User> user);
}
