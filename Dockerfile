# --- Stage 1: Build ---
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app

# Step 1: Cache dependencies (only re-runs if pom.xml changes)
COPY pom.xml .
RUN mvn -B dependency:go-offline

# Step 2: Build the application
COPY src ./src
RUN mvn -B -DskipTests clean package

# --- Stage 2: Runtime ---
FROM tomcat:10.1-jdk17-temurin
WORKDIR /usr/local/tomcat

# Remove default Tomcat apps and copy your WAR
RUN rm -rf webapps/*
COPY --from=build /app/target/*.war webapps/ROOT.war

# Copy the entrypoint script and make it executable
COPY entrypoint.sh /usr/local/bin/entrypoint.sh
RUN chmod +x /usr/local/bin/entrypoint.sh

# If your project doesn't bundle the MySQL driver in the WAR,
# you'll need this line (ensure the path matches your Maven versioning):
# COPY --from=build /root/.m2/repository/com/mysql/mysql-connector-j/9.6.0/mysql-connector-j-9.6.0.jar lib/

EXPOSE 8080

ENTRYPOINT ["/usr/local/bin/entrypoint.sh"]