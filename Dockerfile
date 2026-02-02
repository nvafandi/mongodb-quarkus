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

# Build Quarkus runner jar + validate
RUN mvn package -DskipTests \
    && ls -l target/quarkus-app

# Stage 2: Run Quarkus app
FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app

# Copy quarkus-run.jar dari stage build
COPY --from=build /app/target/quarkus-run.jar quarkus-run.jar

# Validate jar exists di image final
RUN test -f quarkus-run.jar

EXPOSE 5000
CMD ["java", "-jar", "quarkus-run.jar"]