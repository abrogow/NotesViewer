package com.domain

import org.junit.rules.ExternalResource

import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicInteger

class InMemoryNoteRepository extends ExternalResource implements NoteRepository {

    final Map<Long, Note> data = new ConcurrentHashMap<>()
    final AtomicInteger id = new AtomicInteger(1)

    @Override
    protected void before() {
        data.clear()
    }

    @Override
    Note save(Note note) {
        if(note.id == null) {
            note.id = id.getAndIncrement().longValue()
            data.put(note.id, note)
            data.get(note.id)
        } else {
            data.put(note.id, note)
            data.get(note.id)
        }
    }

    @Override
    Optional<Note> findById(Long noteId) {
        return Optional.ofNullable(data.get(noteId))
    }
}
