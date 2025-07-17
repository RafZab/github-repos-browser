# GitHub Repos Browser

The **GitHub Repos Browser** is a backend application developed as part of a recruiting task. It allows downloading and
displaying user's repositories from GitHub, along with branches and SHA of recent commits. The project was implemented
using Java 21 and Spring Boot 3.5, and is an example of integration with GitHub's public REST API.

---

## 🧩 Table of contents

- [Description](#description)
- [Technologies](#technologies)
- [Installation and start-up](#installation-and-start-up)
- [Configuration](#configuration)
- [Usage](#usage)
- [Testing](#testing)
- [Author](#author)
- [License](#license)

---

## Description

The application provides a REST endpoint that:

- Returns a list of all **unforked** repositories of the selected GitHub user, along with branches and SHA of recent
  commits,
- For a non-existent user, returns a 404 error with a readable message in JSON format.

The project serves as an example of good programming practice - full error handling, clean code, segregated DTOs and
documentation.

---

## Technologies

- Java 21
- Spring Boot 3.5
- Maven
- Jackson (serialization/deserialization JSON)
- JDK HttpClient (communication with GitHub API)

---

## Installation and start-up

### Prerequisites

- **Java 21** lub latest
- **Maven 3.8+**
- Internet access (the application integrates with the GitHub API)


### Step 1: Cloning a repository

```bash
git clone https://github.com/RafZab/github-repos-browser.git
cd github-repos-browser
```


### Step 2: Building the project

```bash
mvn clean install
```


### Step 3: Running the application

```bash
mvn spring-boot:run
```

The application listens on port **8080** by default.

### Step 4: Functional check

Once running, test the application by sending an HTTP request, such as using `curl`:

```bash
curl http://localhost:8080/api/v1/users/RafZab/repositories
```

Or open in your browser:

```
http://localhost:8080/api/v1/users/RafZab/repositories
```

---

## Configuration

You can find the basic settings in the file [
`application.yml`](src/github-repo-service/src/main/resources/application.yml):

---

## Usage

| Method | Endpoint                                | Description                                | Example of a call                                             |
|:-------|:----------------------------------------|:-------------------------------------------|:--------------------------------------------------------------|
| GET    | `/api/v1/users/{username}/repositories` | Download user repositories (without forks) | `GET http://localhost:8080/api/v1/users/octocat/repositories` |

- **{username}** – The name of the GitHub user for whom you are downloading repositories. (required, not empty)

### Response

| Field  | Type                | Description     | Example       |
|:-------|:--------------------|:----------------|:--------------|
| status | String              | Request status  | "OK"          |
| data   | List<RepositoryDTO> | Repository list | RepositoryDTO |

**RepositoryDTO**

| Field          | Type            | Description                        | Example                |
|:---------------|:----------------|:-----------------------------------|:-----------------------|
| repositoryName | String          | Repository lidy                    | "GitHub-Repos-Browser" |
| ownerLogin     | String          | Name of repository owner           | "RafZab"               |
| branches       | List<BranchDTO> | List of branches in the repository | BranchDTO              |

**BranchDTO**

| Field         | Type   | Description                           | Example                                    |
|:--------------|:-------|:--------------------------------------|:-------------------------------------------|
| branchName    | String | Branch name                           | "main"                                     |
| lastCommitSha | String | SHA of the last commit on this branch | "e5bd3914e2e596debea16f433f57875b5b90bcd6" |

##### Example JSON

```json
{
  "status": "OK",
  "data": [
    {
      "repositoryName": "Spoon-Knife",
      "ownerLogin": "RafZab",
      "branches": [
        {
          "branchName": "main",
          "lastCommitSha": "e5bd3914e2e596debea16f433f57875b5b90bcd6"
        }
      ]
    }
  ]
}
```

#### Error response

| Field   | Type    | Description                             | Example                   |
|:--------|:--------|:----------------------------------------|:--------------------------|
| status  | Integer | HTTP status code                        | 404                       |
| message | String  | Description of the reason for the error | "The user does not exist" |


##### Example JSON

```json
{
  "status": 404,
  "message": "The user does not exist"
}
```

### GitHub restrictions (2025)

- 60 request per hour from IP (significantly higher limits for logged-in users and tokenized applications)
- By default, the API returns only 30 (default 30, max 100) results per page, even if there are many more results
- public data only

---

## Testing

The project includes an **integration test** that checks the happy path for the endpoint that downloads repositories.

Fire it up with the command `mvn test`

This test uses the GitHub public API, so an Internet connection is required

---

## Author

- [RafZab](https://github.com/RafZab)

---

## License

An open-source project under the MIT license.

---
