# Stage 1: Build Quarkus app
FROM eclipse-temurin:21-jdk-alpine AS build
WORKDIR /app

# Install Maven
ENV MAVEN_VERSION=3.9.12
RUN apk add --no-cache curl tar bash \
    && curl -fsSL https://archive.apache.org/dist/maven/maven-3/${MAVEN_VERSION}/binaries/apache-maven-${MAVEN_VERSION}-bin.tar.gz \
       | tar xzf - -C /opt \
    && ln -s /opt/apache-maven-${MAVEN_VERSION} /opt/maven
ENV PATH="/opt/maven/bin:${PATH}"

# Copy project files
COPY pom.xml .
COPY src ./src

# Build Quarkus runner jar
RUN mvn package -DskipTests

# Stage 2: Run Quarkus app
FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app

# Copy runner jar from build stage
COPY --from=build /app/target/*-runner.jar app.jar

EXPOSE 8080
CMD ["java", "-jar", "app.jar"]