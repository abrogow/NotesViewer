package com.interfaces;

import com.domain.Note;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class NoteDtoFactory {

    public NoteDto create(Note note) {
        return new NoteDto(note.getContent());
    }
}
