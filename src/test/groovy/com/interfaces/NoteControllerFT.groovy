package com.interfaces

import io.restassured.RestAssured
import io.restassured.authentication.PreemptiveBasicAuthScheme
import org.springframework.boot.web.server.LocalServerPort
import spock.lang.Specification

import static io.restassured.RestAssured.given

@FunctionalTest
class NoteControllerFT extends Specification {

    @LocalServerPort
    int localServerPort

    def 'Should correctly run application and do some sample call to API'() {
        given:
            PreemptiveBasicAuthScheme authScheme = new PreemptiveBasicAuthScheme();
            authScheme.setUserName("user");
            authScheme.setPassword("password");
            RestAssured.authentication = authScheme

        expect:
            given().port(localServerPort)
                    .when()
                    .get("/notes/1")
                    .then()
                    .statusCode(404)
    }
}
