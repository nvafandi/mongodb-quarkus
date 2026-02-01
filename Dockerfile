# Use a base image with Java 21
FROM eclipse-temurin:21-jdk-alpine

# Set environment variables
ENV MAVEN_VERSION=3.9.12
ENV MAVEN_HOME=/opt/maven

# Install Maven
RUN apk add --no-cache curl && \
    curl -fsSL https://archive.apache.org/dist/maven/maven-3/${MAVEN_VERSION}/binaries/apache-maven-${MAVEN_VERSION}-bin.tar.gz | tar xzf - -C /opt && \
    ln -s /opt/apache-maven-${MAVEN_VERSION} ${MAVEN_HOME} && \
    rm -rf /opt/apache-maven-${MAVEN_VERSION}/src

# Set Maven environment variables
ENV PATH="${MAVEN_HOME}/bin:${PATH}"

# Set the working directory
WORKDIR /app

# Copy the project files
COPY pom.xml .
COPY src ./src

# Build the project
RUN mvn clean package -DskipTests

# Expose the port
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "target/mongodb-quarkus-1.0.0-runner.jar"]