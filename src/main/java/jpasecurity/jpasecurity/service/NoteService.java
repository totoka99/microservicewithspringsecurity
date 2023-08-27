package jpasecurity.jpasecurity.service;

import jpasecurity.jpasecurity.expcetion.NoteNotFoundException;
import jpasecurity.jpasecurity.model.ModelMapper;
import jpasecurity.jpasecurity.model.dto.note.NoteDto;
import jpasecurity.jpasecurity.model.entity.Note;
import jpasecurity.jpasecurity.model.dto.note.CreateNoteDto;
import jpasecurity.jpasecurity.model.dto.note.UpdateNoteDto;
import jpasecurity.jpasecurity.repository.NoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NoteService {
    private final NoteRepository noteRepository;
    private final ModelMapper mapper;

    public NoteDto findById(Long id) {
        return mapper.toNoteDto(noteRepository.findById(id).orElseThrow(() -> new NoteNotFoundException(id)));
    }

    public NoteDto saveNewExample(CreateNoteDto createNoteDto) {
        Note note = new Note();
        note.setDescription(createNoteDto.getDescription());
        note.setName(createNoteDto.getName());
        return mapper.toNoteDto(noteRepository.save(note));
    }

    @Transactional
    public void updateExampleName(UpdateNoteDto updateNoteDto, Long id) {
        Note note = noteRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Not found, id: " + id));
        note.setName(updateNoteDto.getName());
    }

    @Transactional
    public void updateExampleDescription(UpdateNoteDto updateNoteDto, Long id) {
        Note note = noteRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Not found, id: " + id));
        note.setDescription(updateNoteDto.getDescription());
    }

    public List<NoteDto> findAllNote() {
        return mapper.toNoteDto(noteRepository.findAll());
    }
}
