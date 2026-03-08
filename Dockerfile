# Stage 1: Build
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn -B dependency:go-offline
COPY src ./src
RUN mvn -B -DskipTests clean package

# Stage 2: Runtime
FROM tomcat:10.1-jdk17-temurin
WORKDIR /usr/local/tomcat

RUN rm -rf webapps/*
COPY --from=build /app/target/*.war webapps/ROOT.war

# Copy the MySQL driver to Tomcat's global lib folder
COPY --from=build /root/.m2/repository/com/mysql/mysql-connector-j/9.6.0/mysql-connector-j-9.6.0.jar lib/

# Copy and setup entrypoint
COPY entrypoint.sh /usr/local/bin/entrypoint.sh
RUN chmod +x /usr/local/bin/entrypoint.sh

# Important: Run as root to ensure mkdir permissions work on Railway
USER root

ENTRYPOINT ["/usr/local/bin/entrypoint.sh"]