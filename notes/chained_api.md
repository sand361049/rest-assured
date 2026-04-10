# What is chained API call ?

suppose you have to delete a user with username "bekar_user"
 
and there is two APIs
Get user by username .. which takes username as input and returns all user details like user id , email , name etc
and Delete user by userID ::: which takes userID as input and deteltes the user
 
 
and you know only username not user id so you can't directly use delete API
 
- so first you will have to call get user by username
- then extract user id from response
- then use this user id to make api call to delete user
- then again use fist API to check if user is actually deleted from backend or not
 
 
 
so here next API call depends on response of first API call
 
so it is called chained API call

syntax
``` java
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class DeleteUserTest {

    public void deleteUserFlow() {

        // Step 1: Get user by username → extract userId
        String userId =
        given()
            .baseUri("https://api.example.com")
            .queryParam("username", "john_doe")
        .when()
            .get("/users")
        .then()
            .statusCode(200)
            .extract()
            .path("data[0].id");   // adjust based on response

        // Step 2: Delete user using extracted userId
        given()
            .baseUri("https://api.example.com")
            .pathParam("id", userId)
        .when()
            .delete("/users/{id}")
        .then()
            .statusCode(204); // or 200 depending on API

        // Step 3: Verify user is deleted
        given()
            .baseUri("https://api.example.com")
            .pathParam("id", userId)
        .when()
            .get("/users/{id}")
        .then()
            .statusCode(404); // user should not exist
    }
}

```
 