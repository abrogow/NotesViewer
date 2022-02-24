package com.interfaces

import com.domain.Note
import com.domain.NoteNotFoundException
import com.domain.NoteService
import com.interfaces.config.GlobalExceptionHandler
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

import java.time.Instant

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest
@ContextConfiguration(classes = [NoteController, NoteDtoFactory, GlobalExceptionHandler])
@AutoConfigureMockMvc(addFilters = false)
class NoteControllerUT extends Specification {

    @SpringBean
    NoteService serviceMock = Mock()

    @Autowired
    NoteDtoFactory factory

    @Autowired
    MockMvc mockMvc

    def 'Should return status 201 CREATED when adding new note'() {
        given:
            def requestBody = """{
                                            "content": "test-content"
                                        }"""
            def note = new Note(1, 'test-content', Instant.parse("2022-02-22T00:00:00Z"))
        and:
            serviceMock.addNote(*_) >> note
        expect:
            mockMvc.perform(post("/notes")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(requestBody))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath('id').value(1))
                    .andExpect(jsonPath('content').value('test-content'))
    }

    def 'Should return status 400 BAD REQUEST when trying to add note with invalid data'() {
        given:
            def requestBody = """{
                                                "content": "test-content"
                                                "lifeLengthInMin": 0
                                            }"""
        expect:
            mockMvc.perform(post("/notes")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(requestBody))
                    .andExpect(status().isBadRequest())
    }

    def 'Should return status 200 OK when trying to update existing note'() {
        expect:
            mockMvc.perform(put("/notes?noteId=1&lifeLengthInMin=1"))
                    .andExpect(status().isOk())
    }

    def 'Should return status 404 NOT FOUND when trying to update non-existing note'() {
        given:
            serviceMock.updateNoteLifetime(*_) >> {throw new NoteNotFoundException()}
        expect:
            mockMvc.perform(put("/notes?noteId=1&lifeLengthInMin=1"))
                    .andExpect(status().isNotFound())
    }

    def 'Should return status 200 OK when fetching note'() {
        given:
            def note = new Note(1, 'test-content', Instant.parse("2022-02-22T00:00:00Z"))
        and:
            serviceMock.getNote(1) >> note
        expect:
            mockMvc.perform(get("/notes/1"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath('id').value(1))
                    .andExpect(jsonPath('content').value('test-content'))
    }

    def 'Should return status 404 NOT FOUND when fetching non-existing note'() {
        given:
            serviceMock.getNote(1) >> {throw new NoteNotFoundException()}
        expect:
            mockMvc.perform(get("/notes/1"))
                    .andExpect(status().isNotFound())
    }
}
