# GitHub Repos Browser

The **GitHub Repos Browser** is a backend application developed as part of a recruiting task. It allows downloading and displaying user's repositories from GitHub, along with branches and SHA of recent commits. The project was implemented using Java 21 and Spring Boot 3.5, and is an example of integration with GitHub's public REST API.

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
- Returns a list of all **unforked** repositories of the selected GitHub user, along with branches and SHA of recent commits,
- For a non-existent user, returns a 404 error with a readable message in JSON format.

The project serves as an example of good programming practice - full error handling, clean code, segregated DTOs and documentation.

---

## Technologies

- Java 21
- Spring Boot 3.5
- Maven
- Jackson (serialization/deserialization JSON)
- JDK HttpClient (communication with GitHub API)

---

## Installation and start-up

TODO Installation and start-up

---

## Configuration

You can find the basic settings in the file [`application.yml`](src/github-repo-service/src/main/resources/application.yml):

---

## Usage

TODO Usage


TODO RESTRICT GITHUB !

---

## Testing

TODO Testing

---

## Author

- [RafZab](https://github.com/RafZab)

---

## License

An open-source project under the MIT license.

---
