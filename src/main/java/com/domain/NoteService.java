package com.domain;

import com.shared.TimeProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class NoteService {

    private final NoteRepository noteRepository;
    private final DefaultNoteExpirationTimeProvider defaultNoteExpirationTimeProvider;
    private final TimeProvider timeProvider;

    public Note addNote(String content, Integer lifeLengthInMin) {
        log.info("Start creating new note");

        Note note = new Note(content, getExpirationTime(lifeLengthInMin));
        return noteRepository.save(note);
    }

    public void updateNoteLifetime(Long noteId, Integer lifeLengthInMin) {
        log.info("Start updating note with id {}", noteId);

        Note note = noteRepository.findById(noteId)
                .orElseThrow(NoteNotFoundException::new);
        Instant expirationTime = getExpirationTime(lifeLengthInMin);
        note.setExpirationTime(expirationTime);
        noteRepository.save(note);

        log.info("Finished updating expiration time for note with id {}", noteId);
    }

    public Note getNote(Long noteId) {
        log.info("Start fetching note with id {}", noteId);

        Note note = noteRepository.findById(noteId)
                .orElseThrow(NoteNotFoundException::new);

        if(noteValid(note)) {
            log.info("Fetched note with id {}", noteId);
            return note;
        } else {
            log.error("Note with id {} is not valid", noteId);
            throw new ExpirationTimeExceededException();
        }
    }

    private Instant getExpirationTime(Integer lifeLengthInMin) {
        Instant now = timeProvider.now();

        return lifeLengthInMin != null
                ? now.plus(lifeLengthInMin, ChronoUnit.MINUTES)
                : getDefaultExpirationTime();
    }

    private Instant getDefaultExpirationTime() {
        Instant now = timeProvider.now();
        Integer defaultExpirationTime = defaultNoteExpirationTimeProvider.getDefaultExpirationTime();

        return defaultExpirationTime != null
                ? now.plus(defaultExpirationTime, ChronoUnit.MINUTES)
                : null;
    }

    private boolean noteValid(Note note) {
        Instant now = timeProvider.now();
        return now.isBefore(note.getExpirationTime());
    }
}
