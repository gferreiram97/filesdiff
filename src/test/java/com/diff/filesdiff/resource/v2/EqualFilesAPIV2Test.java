package com.diff.filesdiff.resource.v2;

import static org.hamcrest.Matchers.containsString;

import com.diff.filesdiff.annotation.RestAssuredTest;
import com.diff.filesdiff.factory.FileFormFactory;
import io.restassured.RestAssured;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

@RestAssuredTest
class EqualFilesAPIV2Test {

    private final FileFormFactory fileFormFactory;

    public EqualFilesAPIV2Test(final FileFormFactory fileFormFactory) {
        this.fileFormFactory = fileFormFactory;
    }

    @Test
    @Order(1)
    void shouldWhenPostInLeftReturnStatusCreatedWithLocation() {
        RestAssured
                .given()
                .log().all()
                .when()
                .body(fileFormFactory.createFileApiV1One())
                .post("/v2/diff/test/left")
                .then()
                .log().all()
                .statusCode(HttpStatus.SC_CREATED)
                .header("Location", Matchers.containsString("/v2/diff/test/left/1"));
    }

    @Test
    @Order(2)
    void shouldWhenPostInRightReturnStatusCreatedWithLocation() {
        RestAssured
                .given()
                .log().all()
                .when()
                .body(fileFormFactory.createFileApiV1Two())
                .post("/v2/diff/test/right")
                .then()
                .log().all()
                .statusCode(HttpStatus.SC_CREATED)
                .header("Location", Matchers.containsString("/v2/diff/test/right/2"));
    }

    @Test
    @Order(3)
    void shouldReturnStatusOKAndMessageEqualFiles() {
        RestAssured
                .given()
                .log().all()
                .when()
                .get("/v2/diff/{identifier}", "test")
                .then()
                .log().all()
                .statusCode(HttpStatus.SC_OK)
                .body(containsString("Documentos<test> são identificos"));
    }
}

