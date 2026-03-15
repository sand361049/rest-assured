package com.restassured.tests;

import io.restassured.http.ContentType;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class DeleteRequestTests extends BaseTest {
    
    @Test(description = "Verify simple DELETE request")
    public void testSimpleDeleteRequest() {
        given()
            .log().all()
        .when()
            .delete("/delete")
        .then()
            .log().all()
            .statusCode(200)
            .body("url", equalTo("https://httpbin.org/delete"));
    }
    
    @Test(description = "Verify DELETE request with query parameters")
    public void testDeleteRequestWithQueryParams() {
        given()
            .log().all()
            .queryParam("id", "123")
            .queryParam("reason", "obsolete")
        .when()
            .delete("/delete")
        .then()
            .log().all()
            .statusCode(200)
            .body("args.id", equalTo("123"))
            .body("args.reason", equalTo("obsolete"));
    }
    
    @Test(description = "Verify DELETE request with JSON body")
    public void testDeleteRequestWithBody() {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("id", 456);
        requestBody.put("confirm", true);
        
        given()
            .log().all()
            .contentType(ContentType.JSON)
            .body(requestBody)
        .when()
            .delete("/delete")
        .then()
            .log().all()
            .statusCode(200)
            .body("json.id", equalTo(456))
            .body("json.confirm", equalTo(true));
    }
    
    @Test(description = "Verify DELETE request with headers")
    public void testDeleteRequestWithHeaders() {
        given()
            .log().all()
            .header("Authorization", "Bearer token123")
            .header("X-Request-ID", "delete-001")
        .when()
            .delete("/delete")
        .then()
            .log().all()
            .statusCode(200)
            .body("headers.Authorization", equalTo("Bearer token123"))
            .body("headers.X-Request-Id", equalTo("delete-001"));
    }
}

