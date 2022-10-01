# ai-guess-game

[![Build](https://github.com/yonatankarp/ai-guess-game/actions/workflows/ci.yml/badge.svg)](https://github.com/yonatankarp/ai-guess-game/actions/workflows/ci.yml)
[![Linters](https://github.com/yonatankarp/ai-guess-game/actions/workflows/linting.yml/badge.svg)](https://github.com/yonatankarp/ai-guess-game/actions/workflows/linting.yml)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=yonatankarp_ai-guess-game&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=yonatankarp_ai-guess-game)
[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=yonatankarp_ai-guess-game&metric=sqale_rating)](https://sonarcloud.io/summary/new_code?id=yonatankarp_ai-guess-game)
[![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=yonatankarp_ai-guess-game&metric=security_rating)](https://sonarcloud.io/summary/new_code?id=yonatankarp_ai-guess-game)
[![Technical Debt](https://sonarcloud.io/api/project_badges/measure?project=yonatankarp_ai-guess-game&metric=sqale_index)](https://sonarcloud.io/summary/new_code?id=yonatankarp_ai-guess-game)
[![Vulnerabilities](https://sonarcloud.io/api/project_badges/measure?project=yonatankarp_ai-guess-game&metric=vulnerabilities)](https://sonarcloud.io/summary/new_code?id=yonatankarp_ai-guess-game)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

## Getting Started

These instructions will get you a copy of the project up and running on your
local machine for development and testing purposes. See deployment for notes on
how to deploy the project on a live system.

### Prerequisites

To run the project you need to install the following:

- JDK 17 or newer
- Docker


### Building the application

The project uses [Gradle](https://gradle.org) as a build tool. It already contains
`./gradlew` wrapper script, so there's no need to install gradle.

To build the project execute the following command:

```shell
  ./gradlew build
```

### Running the application

Create the image of the application by executing the following command:

```shell
  ./gradlew assemble
```

You can run this project directly from Gradle by executing the following
command:

```shell
./gradlew bootRun
```

Otherwise, you can create docker image:

```shell
  docker compose build
```

For Apple M1 processor run the following instead:

```shell
DOCKER_BUILDKIT=0 docker compose build
```

Run the distribution (created in `ai-guess-game/build/install/ai-guess-game`
directory) by executing the following command:

```shell
  docker compose up
```

This will start the API container exposing the application's port
(set to `80` in this app).

In order to test if the application is up, you can call its health endpoint:

```shell
  curl http://localhost:80/health
```

You should get a response similar to this:

```json
  {"status":"UP","diskSpace":{"status":"UP","total":249644974080,"free":137188298752,"threshold":10485760}}
```

### Alternative script to run application

To skip all the setting up and building, just execute the following command:

```shell
./bin/run-in-docker.sh
```

For more information:

```shell
./bin/run-in-docker.sh --help
```

## Running the tests

You can run the project tests via Gradle by executing the following command:

```shell
./gradlew test
```

### And coding style tests

This project uses [Spotless Gradle plugin](https://github.com/diffplug/spotless)
to enforce its code style. The plugin will run automatically after every
successful build, test, and assemble stage. However, if you would like to run
it manually you can do so by running the following commands:

To apply the code style to the project run:

```shell
./gradlew spotlessApply
```

To check your code without applying any changes you can execute:

```shell
./gradlew spotlessCheck
```

## Plugins

To read more about the plugins included in this project click
[here](docs/plugins.md).

## Built With

- [OpenJdk 17](https://openjdk.java.net/projects/jdk/17/)
- [Kotlin](https://kotlinlang.org/)
- [SpringBoot](https://spring.io/projects/spring-boot) - The web framework used
- [Gradle](https://gradle.org/) - Dependency Management
- [GitHub Actions](https://docs.github.com/en/actions) - Continuous Integration
- [Docker](https://www.docker.com/) - Container handling

## Versioning

We use [SemVer](http://semver.org/) for versioning. For the versions available,
see the [tags on this repository](https://github.com/your/project/tags).

## Authors

- **Yonatan Karp-Rudin** - *Initial work* - [yonatankarp](https://github.com/yonatankarp)
