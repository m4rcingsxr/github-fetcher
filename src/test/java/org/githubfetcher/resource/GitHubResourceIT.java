package org.githubfetcher.resource;

import io.restassured.http.ContentType;
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
        given()
                .when()
                .get("/github/" + GITHUB_USER)
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("size()", greaterThan(0))
                .body("[0].name", not(emptyOrNullString()))
                .body("[0].owner.login", equalTo(GITHUB_USER))
                .body("[0].branches", notNullValue())
                .body("[0].branches.size()", greaterThan(0))
                .body("[0].branches[0].name", not(emptyOrNullString()))
                .body("[0].branches[0].commit.sha", not(emptyOrNullString()));
    }
}