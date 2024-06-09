package jpasecurity.jpasecurity.controller;

import jpasecurity.jpasecurity.controller.note.CreateNoteDto;
import jpasecurity.jpasecurity.controller.note.NoteDto;
import jpasecurity.jpasecurity.controller.note.UpdateNoteDto;
import jpasecurity.jpasecurity.service.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/note")
@RequiredArgsConstructor
public class NoteController {
    private final NoteService noteService;

    @GetMapping("/{noteId}")
    @ResponseStatus(HttpStatus.OK)
    public NoteDto getExampleById(@PathVariable Long noteId) {
        return noteService.findById(noteId);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<NoteDto> getAllNote() {
        return noteService.findAllNote();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public NoteDto createNote(@RequestBody CreateNoteDto createNoteDto) {
        return noteService.saveNewNote(createNoteDto);
    }


    @PutMapping()
    public NoteDto updateNote(@RequestBody UpdateNoteDto updateNoteDto) {
        return noteService.updateNote(updateNoteDto);
    }


    @DeleteMapping("/{id}")
    public void deleteNoteById(@PathVariable Long id) {
        System.out.println(id);
        noteService.deleteNoteById(id);
    }
}
