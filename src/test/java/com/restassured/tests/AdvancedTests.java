package com.restassured.tests;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class AdvancedTests extends BaseTest {
    
    @Test(description = "Verify authentication with Basic Auth")
    public void testBasicAuthentication() {
        given()
            .log().all()
            .auth().basic("user", "passwd")
        .when()
            .get("/basic-auth/user/passwd")
        .then()
            .log().all()
            .statusCode(200)
            .body("authenticated", equalTo(true))
            .body("user", equalTo("user"));
    }
    
    @Test(description = "Verify status code endpoints")
    public void testStatusCodeEndpoint() {
        given()
            .log().all()
        .when()
            .get("/status/201")
        .then()
            .log().all()
            .statusCode(201);
    }
    
    @Test(description = "Verify delay endpoint")
    public void testDelayEndpoint() {
        long startTime = System.currentTimeMillis();
        
        given()
            .log().all()
        .when()
            .get("/delay/2")
        .then()
            .log().all()
            .statusCode(200)
            .time(greaterThan(2000L));
        
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        System.out.println("Request took: " + duration + "ms");
    }
    
    @Test(description = "Verify response headers")
    public void testResponseHeaders() {
        given()
            .log().all()
        .when()
            .get("/response-headers?Custom-Header=TestValue&Another-Header=AnotherValue")
        .then()
            .log().all()
            .statusCode(200)
            .header("Custom-Header", "TestValue")
            .header("Another-Header", "AnotherValue");
    }
    
    @Test(description = "Verify cookies")
    public void testCookies() {
        given()
            .log().all()
            .cookie("session_id", "abc123")
            .cookie("user_token", "xyz789")
        .when()
            .get("/cookies")
        .then()
            .log().all()
            .statusCode(200)
            .body("cookies.session_id", equalTo("abc123"))
            .body("cookies.user_token", equalTo("xyz789"));
    }
    
    @Test(description = "Verify setting cookies via endpoint")
    public void testSetCookies() {
        Response response = given()
            .log().all()
        .when()
            .get("/cookies/set?name=value&another=test")
        .then()
            .log().all()
            .statusCode(200)
            .extract()
            .response();
        
        System.out.println("Cookies: " + response.getCookies());
    }
    
    @Test(description = "Verify UUID generation")
    public void testUuidEndpoint() {
        given()
            .log().all()
        .when()
            .get("/uuid")
        .then()
            .log().all()
            .statusCode(200)
            .body("uuid", matchesPattern("[a-f0-9]{8}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{12}"));
    }
    
    @Test(description = "Verify JSON response")
    public void testJsonEndpoint() {
        given()
            .log().all()
        .when()
            .get("/json")
        .then()
            .log().all()
            .statusCode(200)
            .contentType(ContentType.JSON)
            .body("slideshow.title", notNullValue());
    }
}

