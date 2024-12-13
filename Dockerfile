FROM eclipse-temurin:23-noble AS builder

WORKDIR /src

COPY mvnw .
COPY mvnw.cmd .
COPY pom.xml .
COPY .mvn .mvn
COPY src src

RUN ./mvnw package -Dmaven.test.skip=true

# don't need this anymore
# as app will run in second stage
# ENTRYPOINT java -jar target/vttp5b-ssf-day18l-0.0.1-SNAPSHOT.jar

# day 18 - slide 13
# second stage
FROM eclipse-temurin:23-jre-noble

WORKDIR /app

COPY --from=builder /src/target/noticeboard-0.0.1-SNAPSHOT.jar app.jar

# Check if curl command is available
RUN apt update && apt install -y curl

ENV PORT=8080

ENV NOTICEBOARD_DB_HOST=localhost NOTICEBOARD_DB_PORT=6379
ENV NOTICEBOARD_DB_USERNAME="" NOTICEBOARD_DB_PASSWORD=""

EXPOSE ${PORT}

SHELL [ "/bin/sh", "-c" ]

ENTRYPOINT SERVER_PORT=${PORT} java -jar app.jar

HEALTHCHECK --interval=60s --start-period=120s CMD curl -s -f http:/localhost:${PORT}/health || exit 1