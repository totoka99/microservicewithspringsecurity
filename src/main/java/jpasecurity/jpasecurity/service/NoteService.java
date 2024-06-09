package jpasecurity.jpasecurity.service;

import jpasecurity.jpasecurity.config.jwt.JwtTokenDetailsService;
import jpasecurity.jpasecurity.controller.note.CreateNoteDto;
import jpasecurity.jpasecurity.controller.note.NoteDto;
import jpasecurity.jpasecurity.controller.note.UpdateNoteDto;
import jpasecurity.jpasecurity.expcetion.NoAuthorizationException;
import jpasecurity.jpasecurity.expcetion.NoteNotFoundException;
import jpasecurity.jpasecurity.repository.NoteRepository;
import jpasecurity.jpasecurity.service.model.ModelMapper;
import jpasecurity.jpasecurity.service.model.entity.Note;
import jpasecurity.jpasecurity.service.model.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NoteService {
    private final JwtTokenDetailsService jwtTokenDetailsService;
    private final UserService userService;
    private final NoteRepository noteRepository;
    private final ModelMapper mapper;

    public NoteDto findById(Long id) {
        return mapper.toNoteDto(getById(id));
    }

    @Transactional
    public Note getById(Long id) {
        Note note = noteRepository.findById(id).orElseThrow(() -> new NoteNotFoundException(id));
        Long userId = jwtTokenDetailsService.getUserIdFromJWTToken();
        if (!userId.equals(note.getUser().getId())) {
            throw new NoAuthorizationException("No authorization,you are not the owner of the Note");
        }
        return note;
    }

    public NoteDto saveNewNote(CreateNoteDto createNoteDto) {
        User owner = getOwnerUser();
        Note note = new Note();
        note.setDescription(createNoteDto.getDescription());
        note.setName(createNoteDto.getName());
        owner.addNote(note);
        return mapper.toNoteDto(noteRepository.save(note));
    }

    public List<NoteDto> findAllNote() {
        return mapper.toNoteDto(noteRepository.findByUser_Id(jwtTokenDetailsService.getUserIdFromJWTToken()));
    }

    public User getOwnerUser() {
        return userService.findUserIfPresent(jwtTokenDetailsService.getUserIdFromJWTToken());
    }

    @Transactional
    public NoteDto updateNote(UpdateNoteDto updateNoteDto) {
        Note toUpdate = getById(updateNoteDto.getId());
        toUpdate.setName(updateNoteDto.getName());
        toUpdate.setDescription(updateNoteDto.getDescription());
        toUpdate.setChecked(updateNoteDto.isChecked());
        return mapper.toNoteDto(toUpdate);
    }

    public void deleteNoteById(Long id) {
        noteRepository.deleteById(id);
    }
}
