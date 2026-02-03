FROM eclipse-temurin:21-jdk-alpine AS build
WORKDIR /app

ENV MAVEN_VERSION=3.9.12
RUN apk add --no-cache curl tar bash \
    && curl -fsSL https://archive.apache.org/dist/maven/maven-3/${MAVEN_VERSION}/binaries/apache-maven-${MAVEN_VERSION}-bin.tar.gz \
       | tar xzf - -C /opt \
    && ln -s /opt/apache-maven-${MAVEN_VERSION} /opt/maven
ENV PATH="/opt/maven/bin:${PATH}"

COPY pom.xml .
COPY src ./src

RUN mvn package -DskipTests \
    && ls -l target/quarkus-app

FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app

COPY --from=build /app/target/quarkus-app/ ./quarkus-app/
COPY entrypoint.sh ./entrypoint.sh

RUN chmod +x ./entrypoint.sh \
    && test -f quarkus-app/quarkus-run.jar

EXPOSE 5000
ENTRYPOINT ["./entrypoint.sh"]