package com.integration.db

import com.domain.Note
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import spock.lang.Specification

import java.time.Instant

@DataJpaTest
class JpaNoteRepositoryIT extends Specification {

    @Autowired
    private JpaNoteRepository noteRepository

    def 'Should correctly save new note'() {
        given:
            def note = new Note( 'test-content', Instant.parse("2022-02-22T00:00:00Z"))
        when:
            def savedNote = noteRepository.save(note)
        then:
            savedNote.id != null
    }

    def 'Should correctly update existing note'() {
        given:
            def note = new Note(3, 'test-content', Instant.parse("2022-02-22T00:00:00Z"))
            def updatingNote = new Note(3, 'updated-content', Instant.parse("2022-02-22T00:00:00Z"))
        and:
            noteRepository.save(note)
        when:
            def updatedNote = noteRepository.save(updatingNote)
        then:
            updatedNote.id == 3
            updatedNote.content == 'updated-content'
    }

    def 'Should correctly find note by id'() {
        given:
            def note = new Note(4, 'test-content', Instant.parse("2022-02-22T00:00:00Z"))
        and:
            noteRepository.save(note)
        when:
            def result = noteRepository.findById(4)
        then:
            result.get().id == note.id
    }
}
