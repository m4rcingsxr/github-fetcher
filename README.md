# GitHub Repository Fetcher

## Overview

This project is a Quarkus 3-based application that fetches non-fork GitHub repositories for a given user, along with
their branches and latest commit SHA. The solution is built using Java and follows a reactive approach with Mutiny.

## Technologies Used

- **Java** (JDK 17+)
- **Quarkus 3**
- **Jakarta RESTful Web Services (JAX-RS)**
- **Mutiny (Reactive programming with Uni & Multi)**
- **JUnit 5** (Integration Testing)
- **RestAssured** (Testing HTTP endpoints)

## API Endpoints

### Fetch User Repositories

```
GET /github/{username}
```

- **Path Parameter:** `username` - GitHub username
- **Response:** Stream of non-fork repositories including their branches and latest commit SHA
- **Response Type:** `application/json`
- **Error Handling:**
    - `404` - GitHub user not found
    - `403` - Rate limit exceeded or unauthorized access
    - `500` - Internal Server Error

## Example Response

```json
{
  "name": "my-repo",
  "owner": {
    "login": "example-user"
  },
  "branches": [
    {
      "name": "main",
      "commit": {
        "sha": "abc123"
      }
    }
  ]
}
```

## Running the Application

### Prerequisites

- Java 17+
- Maven 3.8+

### Build & Run

```
mvn clean package
java -jar target/quarkus-app/quarkus-run.jar
```

### Running Integration Tests

```
mvn failsafe:integration-test failsafe:verify -DskipITs=false
```

To run integration tests with a custom GitHub username:

```
mvn failsafe:integration-test failsafe:verify -DskipITs=false -Dgithub.test.username=your-github-username
```

