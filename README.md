# Help Desk

Learning project built with Java 17, Spring Boot, Spring MVC, Spring Security, Spring Data JPA, Thymeleaf and PostgreSQL.

## Features

- public pages: home, about, contacts, FAQ
- public ticket creation form
- secured admin ticket list
- filtering by status and customer name
- PostgreSQL database with demo seed data
- unit and integration tests

## Run With PostgreSQL

1. Install JDK 17 and Docker. Check that `java -version` shows 17.
2. Start PostgreSQL:

   ```powershell
   docker compose up -d
   ```

3. Build the application:

   ```powershell
   .\mvnw.cmd clean package
   ```

4. Run the packaged jar:

   ```powershell
   java -jar target\helpdesk-0.0.1-SNAPSHOT.jar
   ```

   If another Java version is first in `PATH`, run the jar with the full path to JDK 17, for example:

   ```powershell
   & "C:\Program Files\Java\jdk-17\bin\java.exe" -jar target\helpdesk-0.0.1-SNAPSHOT.jar
   ```

5. Open:

   ```text
   http://localhost:8080
   ```

Admin credentials:

```text
admin / admin
```

The default database settings can be overridden with `SPRING_DATASOURCE_URL`, `SPRING_DATASOURCE_USERNAME` and `SPRING_DATASOURCE_PASSWORD`.

By default the compose database is exposed on host port `5433` to avoid conflicts with a locally installed PostgreSQL on `5432`.
