package com.interfaces

import com.domain.Note
import spock.lang.Specification
import spock.lang.Subject

import java.time.Instant

class NoteDtoFactoryUT extends Specification {

    @Subject
    NoteDtoFactory factory = new NoteDtoFactory()

    def 'Should correctly create NoteDto from Note'() {
        given:
            def note = new Note(1, 'test-content', Instant.parse("2022-02-22T00:00:00Z"))
        when:
            def dto = factory.create(note)
        then:
            dto.id == note.id
            dto.content == note.content
    }
}
