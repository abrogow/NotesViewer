package com.interfaces;

import com.domain.NoteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;

@Tag(name = "Note controller")
@RestController
@RequestMapping(path = "/notes")
@RequiredArgsConstructor
public class NoteController {

    private final NoteService service;
    private final NoteDtoFactory factory;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Create new note",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Successfully created new note")
            }
    )
    @ResponseStatus(code = HttpStatus.CREATED)
    public void addNote(@RequestBody @Valid NoteForm noteForm) {
        service.addNote(noteForm.getContent(), noteForm.getLifeLengthInMin());
    }

    @PutMapping
    @Operation(
            summary = "Update note life length",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully updated note life length"),
                    @ApiResponse(responseCode = "400", description = "Could not find note for given id")
            }
    )
    @ResponseStatus(code = HttpStatus.OK)
    public void updateNoteLifetime(@RequestParam Long noteId,
                                   @RequestParam @Min(value = 0, message = "The value must be positive") Integer lifeLengthInMin) {
        service.updateNoteLifetime(noteId, lifeLengthInMin);
    }

    @GetMapping(path = "/{noteId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Get note with given id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully fetched note with given id"),
                    @ApiResponse(responseCode = "400", description = "Could not find note for given id")
            }
    )
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<NoteDto> getNote(@PathVariable Long noteId) {
        factory.create(service.getNote(noteId));
        return null;
    }
}
