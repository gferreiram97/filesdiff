package com.diff.filesdiff.config;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.TestExecutionListener;

public class RestAssuredExecutionListener implements TestExecutionListener {

    private static final String SERVER_PORT = "local.server.port";

    @Override
    public void beforeTestMethod(final TestContext testContext) throws Exception {
        final var port = testContext.getApplicationContext()
                .getEnvironment().getProperty(SERVER_PORT, Integer.class, 8080);
        RestAssured.requestSpecification = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .setPort(port)
                .build();
    }
}
