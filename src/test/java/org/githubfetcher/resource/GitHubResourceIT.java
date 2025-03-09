package org.githubfetcher.resource;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

class GitHubResourceIT {

    private static String GITHUB_USER;

    @BeforeAll
    static void setup() {
        GITHUB_USER = System.getProperty("github.test.username", "m4rcingsxr");
    }

    @Test
    void givenValidGitHubUser_whenFetchingRepositories_thenReturnsNonForkedRepositoriesWithBranches() {
        String response = given()
                .when()
                .get("/github/" + GITHUB_USER)
                .then()
                .statusCode(200)
                .contentType("text/event-stream")
                .extract().asString();

        String json = response.replace("data: ", "").trim();

        // Validate extracted JSON
        given()
                .body(json)
                .then()
                .body("size()", greaterThan(0))
                .body("[0].name", not(emptyOrNullString()))
                .body("[0].owner.login", equalTo(GITHUB_USER))
                .body("[0].fork", is(false))
                .body("[0].branches", notNullValue())
                .body("[0].branches.size()", greaterThan(0))
                .body("[0].branches[0].name", not(emptyOrNullString()))
                .body("[0].branches[0].commit.sha", not(emptyOrNullString()));
    }
}