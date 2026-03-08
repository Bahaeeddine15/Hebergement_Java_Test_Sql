# Stage 1: Build
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app

# Copy pom and fetch dependencies (caching layer)
COPY pom.xml .
RUN mvn -B dependency:go-offline

# Copy source and build
COPY src ./src
RUN mvn -B -DskipTests clean package

# Stage 2: Runtime
FROM tomcat:10.1-jdk17-temurin
WORKDIR /usr/local/tomcat

# Clean default apps and copy the built WAR
RUN rm -rf webapps/*
COPY --from=build /app/target/*.war webapps/ROOT.war

# Create the startup script
RUN mkdir -p /usr/local/bin /usr/local/tomcat/conf/Catalina/localhost && \
    printf '%s\n' \
    '#!/bin/sh' \
    'set -e' \
    'PORT_TO_USE="${PORT:-8080}"' \
    'sed -i "s/port=\"8080\"/port=\"${PORT_TO_USE}\"/" /usr/local/tomcat/conf/server.xml' \
    'cat > /usr/local/tomcat/conf/Catalina/localhost/ROOT.xml <<EOF' \
    '<Context>' \
    '  <Resource name="jdbc/myDatabase"' \
    '    auth="Container"' \
    '    type="jakarta.sql.DataSource"' \
    '    driverClassName="com.mysql.cj.jdbc.Driver"' \
    '    url="jdbc:mysql://${MYSQLHOST}:${MYSQLPORT:-3306}/${MYSQLDATABASE}?useSSL=false&amp;allowPublicKeyRetrieval=true&amp;serverTimezone=UTC"' \
    '    username="${MYSQLUSER}"' \
    '    password="${MYSQLPASSWORD}"' \
    '    maxTotal="10"' \
    '    maxIdle="5"' \
    '    maxWaitMillis="10000"/>' \
    '</Context>' \
    'EOF' \
    'exec catalina.sh run' \
    > /usr/local/bin/startup.sh && \
    chmod +x /usr/local/bin/startup.sh

EXPOSE 8080
CMD ["/usr/local/bin/startup.sh"]