Migration to Gradle

This project was originally Maven-based and has been migrated to Gradle.

Quick start (using Gradle wrapper if generated):

- Generate the wrapper (if not present):
  ./gradlew wrapper

- Build and run tests:
  ./gradlew clean test

- Run the application:
  ./gradlew bootRun

- Apply Spotless formatting:
  ./gradlew spotlessApply

Notes:
- The Gradle build uses Spring Boot plugin 3.5.9 to match the current pom.xml.
- Java toolchain is configured to use Java 21.
- If your environment relies on Maven-only CI, update CI to use Gradle or keep Maven as secondary configuration.

If you want, I can generate the Gradle wrapper files and run a test build here — tell me to proceed and I'll attempt to generate the wrapper and run the build.
