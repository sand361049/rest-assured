package com.restassured.tests;

import com.restassured.models.User;
import io.restassured.http.ContentType;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class PutRequestTests extends BaseTest {
    
    @Test(description = "Verify PUT request with JSON body")
    public void testPutRequestWithJson() {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("name", "Updated Name");
        requestBody.put("email", "updated@example.com");
        requestBody.put("status", "active");
        
        given()
            .log().all()
            .contentType(ContentType.JSON)
            .body(requestBody)
        .when()
            .put("/put")
        .then()
            .log().all()
            .statusCode(200)
            .body("json.name", equalTo("Updated Name"))
            .body("json.email", equalTo("updated@example.com"))
            .body("json.status", equalTo("active"));
    }
    
    @Test(description = "Verify PUT request with POJO")
    public void testPutRequestWithPOJO() {
        User user = User.builder()
                .name("Updated User")
                .email("updated.user@example.com")
                .age(35)
                .city("San Francisco")
                .build();
        
        given()
            .log().all()
            .contentType(ContentType.JSON)
            .body(user)
        .when()
            .put("/put")
        .then()
            .log().all()
            .statusCode(200)
            .body("json.name", equalTo("Updated User"))
            .body("json.email", equalTo("updated.user@example.com"))
            .body("json.age", equalTo(35))
            .body("json.city", equalTo("San Francisco"));
    }
    
    @Test(description = "Verify PUT request updates specific fields")
    public void testPutRequestPartialUpdate() {
        String jsonBody = """
                {
                    "id": 123,
                    "name": "Partial Update",
                    "description": "Only updating specific fields"
                }
                """;
        
        given()
            .log().all()
            .contentType(ContentType.JSON)
            .body(jsonBody)
        .when()
            .put("/put")
        .then()
            .log().all()
            .statusCode(200)
            .body("json.id", equalTo(123))
            .body("json.name", equalTo("Partial Update"))
            .body("json.description", equalTo("Only updating specific fields"));
    }
}

