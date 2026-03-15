package com.restassured.tests;

import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class GetRequestTests extends BaseTest {
    
    @Test(description = "Verify simple GET request")
    public void testSimpleGetRequest() {
        given()
            .log().all()
        .when()
            .get("/get")
        .then()
            .log().all()
            .statusCode(200)
            .body("url", equalTo("https://httpbin.org/get"));
    }
    
    @Test(description = "Verify GET request with query parameters")
    public void testGetRequestWithQueryParams() {
        given()
            .log().all()
            .queryParam("name", "John")
            .queryParam("age", "30")
        .when()
            .get("/get")
        .then()
            .log().all()
            .statusCode(200)
            .body("args.name", equalTo("John"))
            .body("args.age", equalTo("30"));
    }
    
    @Test(description = "Verify GET request with headers")
    public void testGetRequestWithHeaders() {
        given()
            .log().all()
            .header("Custom-Header", "CustomValue")
            .header("User-Agent", "RestAssured-Test")
        .when()
            .get("/headers")
        .then()
            .log().all()
            .statusCode(200)
            .body("headers.Custom-Header", equalTo("CustomValue"))
            .body("headers.User-Agent", equalTo("RestAssured-Test"));
    }
    
    @Test(description = "Verify response time is acceptable")
    public void testResponseTime() {
        given()
            .log().all()
        .when()
            .get("/get")
        .then()
            .log().all()
            .statusCode(200)
            .time(lessThan(5000L)); // Response time should be less than 5 seconds
    }
    
    @Test(description = "Extract and validate response data")
    public void testExtractResponseData() {
        Response response = given()
            .log().all()
            .queryParam("test", "value")
        .when()
            .get("/get")
        .then()
            .log().all()
            .statusCode(200)
            .extract()
            .response();
        
        String url = response.path("url");
        System.out.println("URL from response: " + url);
        assert url.contains("httpbin.org");
    }
}

