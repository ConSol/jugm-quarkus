package de.consol.dus.boundary.endpoints;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.Is.is;

import io.quarkus.test.junit.QuarkusTest;
import javax.ws.rs.core.MediaType;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

@QuarkusTest
class UserResourceTest {

  @Test
  public void testPostingFetchingAndDeletingUser() {
    String janeName = "Jane Doe";
    String jane = String.format("{\"name\":\"%s\",\"email\":\"jane@doe.com\"}", janeName);
    given()
        .header("Content-Type", MediaType.APPLICATION_JSON)
        .body(jane)
        .when().post("/users")
        .then()
            .statusCode(HttpStatus.SC_OK)
            .body(is(jane));
    given()
        .pathParam("name", "Jane Doe")
        .when().get("/users/{name}")
        .then()
            .statusCode(HttpStatus.SC_OK)
            .body(is(jane));
    given()
        .when().get("/users")
        .then()
            .statusCode(HttpStatus.SC_OK)
            .body(is("[" + jane + "]"));
    given()
        .pathParam("name", janeName)
        .when().delete("/users/{name}")
        .then()
            .statusCode(HttpStatus.SC_OK)
            .body(is(jane));
  }
}