package com.domain;

import java.util.Optional;

public interface NoteRepository {

    Note save(Note note);

    Optional<Note> findById(Long noteId);
}
