package com.interfaces;

import com.domain.NoteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;

import javax.validation.Valid;
import javax.validation.constraints.Min;

@Tag(name = "Note controller")
@RestController
@RequestMapping(path = "/notes")
@RequiredArgsConstructor
@SecurityRequirement(name = "api-auth")
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
    public NoteDto addNote(@RequestBody @Valid NoteForm noteForm) {
        return factory.create(service.addNote(noteForm.getContent(), noteForm.getLifeLengthInMin()));
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
    public NoteDto getNote(@PathVariable Long noteId) {
        return factory.create(service.getNote(noteId));
    }
}
