package com.restassured.tests;

import com.restassured.models.User;
import io.restassured.http.ContentType;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class PostRequestTests extends BaseTest {
    
    @Test(description = "Verify POST request with JSON body using Map")
    public void testPostRequestWithMap() {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("name", "John Doe");
        requestBody.put("email", "john.doe@example.com");
        requestBody.put("age", 30);
        
        given()
            .log().all()
            .contentType(ContentType.JSON)
            .body(requestBody)
        .when()
            .post("/post")
        .then()
            .log().all()
            .statusCode(200)
            .body("json.name", equalTo("John Doe"))
            .body("json.email", equalTo("john.doe@example.com"))
            .body("json.age", equalTo(30));
    }
    
    @Test(description = "Verify POST request with POJO")
    public void testPostRequestWithPOJO() {
        User user = User.builder()
                .name("Jane Smith")
                .email("jane.smith@example.com")
                .age(25)
                .city("New York")
                .build();
        
        given()
            .log().all()
            .contentType(ContentType.JSON)
            .body(user)
        .when()
            .post("/post")
        .then()
            .log().all()
            .statusCode(200)
            .body("json.name", equalTo("Jane Smith"))
            .body("json.email", equalTo("jane.smith@example.com"))
            .body("json.age", equalTo(25))
            .body("json.city", equalTo("New York"));
    }
    
    @Test(description = "Verify POST request with JSON string")
    public void testPostRequestWithJsonString() {
        String jsonBody = """
                {
                    "username": "testuser",
                    "password": "testpass123",
                    "active": true
                }
                """;
        
        given()
            .log().all()
            .contentType(ContentType.JSON)
            .body(jsonBody)
        .when()
            .post("/post")
        .then()
            .log().all()
            .statusCode(200)
            .body("json.username", equalTo("testuser"))
            .body("json.password", equalTo("testpass123"))
            .body("json.active", equalTo(true));
    }
    
    @Test(description = "Verify POST request with form data")
    public void testPostRequestWithFormData() {
        given()
            .log().all()
            .contentType(ContentType.URLENC)
            .formParam("firstName", "John")
            .formParam("lastName", "Doe")
            .formParam("email", "john@example.com")
        .when()
            .post("/post")
        .then()
            .log().all()
            .statusCode(200)
            .body("form.firstName", equalTo("John"))
            .body("form.lastName", equalTo("Doe"))
            .body("form.email", equalTo("john@example.com"));
    }
}

