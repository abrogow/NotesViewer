package com.interfaces

import com.domain.Note
import com.domain.NoteService
import org.junit.Before
import org.junit.runner.RunWith
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.security.test.context.support.WithUserDetails
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import org.springframework.test.context.web.WebAppConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.*
import spock.lang.Specification

import java.time.Instant

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*

@WebMvcTest
@ContextConfiguration(classes = [NoteController, NoteDtoFactory])
@RunWith(SpringJUnit4ClassRunner.class)
class NoteControllerUT extends Specification {

    @SpringBean
    NoteService serviceMock = Mock()

    @Autowired
    NoteDtoFactory factory

    @Autowired
    MockMvc mockMvc

    @Autowired
    private WebApplicationContext context;


    def 'Should return status 201 CREATED when adding new note'() {
        given:
            def requestBody = """{
                                            "content": "test-content"
                                        }"""
            def content = 'test-content'
            def note = new Note(1, content, Instant.parse("2022-02-22T00:00:00Z"))
        and:
            serviceMock.addNote(_) >> note
        expect:
            mockMvc.perform(post("/notes")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(requestBody))
                    //.with(httpBasic("user","password")))
//                    .with(user("user").password("password").roles("ADMIN")))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath('id').value(1))
                    .andExpect(jsonPath('content').value('test-content'))
    }

    static getUserDetails() {
        user("testuser").password("testpassword").roles("ADMIN")
    }

}
