package jpasecurity.jpasecurity.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jpasecurity.jpasecurity.model.dto.note.CreateNoteDto;
import jpasecurity.jpasecurity.model.dto.note.NoteDto;
import jpasecurity.jpasecurity.model.dto.note.UpdateNoteDto;
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
    @Operation(summary = "Return example's data")
    @ResponseStatus(HttpStatus.OK)
    @ApiResponse(responseCode = "200")
    public NoteDto getExampleById(@PathVariable Long exampleId) {
        return noteService.findById(exampleId);
    }

    @GetMapping
    public List<NoteDto> getAllNote (){
        return noteService.findAllNote();
    }

    @PostMapping
    @Operation(summary = "Create new example")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponse(responseCode = "201")
    public NoteDto createExample(@RequestBody CreateNoteDto createNoteDto) {
        return noteService.saveNewExample(createNoteDto);
    }

    @PatchMapping("/name/{noteId}")
    @Operation(summary = "Update example's name")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @ApiResponse(responseCode = "202")
    public void updateExampleName(@RequestBody UpdateNoteDto updateNoteDto, @PathVariable Long exampleId) {
        noteService.updateExampleName(updateNoteDto, exampleId);
    }

    @PatchMapping("/description/{noteId}")
    @Operation(summary = "Update example's description")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @ApiResponse(responseCode = "202")
    public void updateExampleDescription(@RequestBody UpdateNoteDto updateNoteDto, @PathVariable Long exampleId) {
        noteService.updateExampleDescription(updateNoteDto, exampleId);
    }
}
