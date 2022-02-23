package com.domain

import com.shared.TimeProvider
import spock.lang.Specification
import spock.lang.Subject

import java.time.Instant
import java.time.temporal.ChronoUnit

class NoteServiceUT extends Specification {

    NoteRepository repositoryMock = new InMemoryNoteRepository()

    DefaultNoteExpirationTimeProvider defaultNoteExpirationTimeProviderMock = Mock() {
        getDefaultExpirationTime() >> 10
    }

    TimeProvider timeProviderMock = Mock() {
        now() >> Instant.parse("2022-02-22T00:00:00Z")
    }

    @Subject
    NoteService service = new NoteService(repositoryMock, defaultNoteExpirationTimeProviderMock, timeProviderMock)

    def 'Should correctly add new note with specified time length' () {
        given:
            def content = 'test content'
            def lifeLength = 12
        when:
            def note = service.addNote(content, lifeLength)
        then:
            note.id != null
            note.content == content
            note.expirationTime == timeProviderMock.now().plus(lifeLength, ChronoUnit.MINUTES)
    }

    def 'Should correctly add new note with default time length' () {
        given:
            def content = 'test content'
        when:
            def note = service.addNote(content, null)
        then:
            note.id != null
            note.content == content
            note.expirationTime == timeProviderMock.now().plus(10, ChronoUnit.MINUTES)
    }

    def 'Should correctly update existing note lifetime' () {
        given:
            def existingNoteLifetime = Instant.parse("2022-02-22T00:00:00Z")
            def existingNote = new Note(34, "note", existingNoteLifetime)
        and:
            repositoryMock.save(existingNote)
        when:
            service.updateNoteLifetime(34, 123)
        then:
            def updatedNote = repositoryMock.findById(34)
            updatedNote.get().expirationTime == existingNoteLifetime.plus(123, ChronoUnit.MINUTES)
    }

    def 'Should throw exception when trying to update non-existing note' () {
        when:
            service.updateNoteLifetime(34, 1234)
        then:
            def e = thrown(NoteNotFoundException)
    }

    def 'Should correctly get valid note with given noteId' () {
        given:
            def existingNoteLifetime = Instant.parse("2022-02-22T01:00:00Z")
            def existingNote = new Note(36, "note", existingNoteLifetime)
        and:
            repositoryMock.save(existingNote)
        when:
            def note = service.getNote(36)
        then:
            note != null
    }

    def 'Should throw exception when fetching note with invalid lifetime' () {
        given:
            def existingNoteLifetime = Instant.parse("2022-02-22T00:00:00Z")
            def existingNote = new Note(35, "note", existingNoteLifetime)
        and:
            repositoryMock.save(existingNote)
        when:
            service.getNote(35)
        then:
            thrown(ExpirationTimeExceededException)
    }

    def 'Should throw exception when fetching non-existing note' () {
        when:
            service.getNote(37)
        then:
            thrown(NoteNotFoundException)
    }
}
