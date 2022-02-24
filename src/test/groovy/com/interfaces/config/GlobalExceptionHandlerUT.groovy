package com.interfaces.config

import com.domain.ExpirationTimeExceededException
import com.domain.NoteNotFoundException
import org.springframework.http.HttpStatus
import spock.lang.Specification
import spock.lang.Subject

class GlobalExceptionHandlerUT extends Specification {

    @Subject
    GlobalExceptionHandler exceptionHandler = new GlobalExceptionHandler()

    def 'Should return 404 NOT_FOUND in case of NoteNotFoundException'() {
        when:
            def call = exceptionHandler.handle(new NoteNotFoundException())
        then:
            call.statusCode == HttpStatus.NOT_FOUND
    }

    def 'Should return 404 NOT_FOUND in case of ExpirationTimeExceededException'() {
        when:
            def call = exceptionHandler.handle(new ExpirationTimeExceededException())
        then:
            call.statusCode == HttpStatus.NOT_FOUND
    }
}
