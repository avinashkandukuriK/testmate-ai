FROM maven:3.9.9-eclipse-temurin-17

WORKDIR /app

COPY pom.xml .
RUN mvn -B -DskipTests dependency:go-offline

COPY . .
RUN mvn -B -DskipTests package

ENV PORT=8080
EXPOSE 8080

CMD ["sh", "-c", "java -jar target/testmate-ai-1.0.0-SNAPSHOT.jar"]
