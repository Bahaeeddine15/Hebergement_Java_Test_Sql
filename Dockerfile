RUN mvn -B dependency:go-offlineCOPY src ./srcRUN mvn -B -DskipTests clean packageFROM tomcat:10.1-jdk17-temurinWORKDIR /usr/local/tomcatRUN rm -rf webapps/*COPY --from=build /app/target/*.war webapps/ROOT.warCOPY --from=build /root/.m2/repository/com/mysql/mysql-connector-j/9.6.0/mysql-connector-j-9.6.0.jar lib/

RUN mkdir -p /usr/local/bin /usr/local/tomcat/conf/Catalina/localhost && \
 printf '%s\n' \
 '#!/bin/sh' \
 'set -e' \
 '' \
 'PORT_TO_USE="${PORT:-8080}"' \
 'sed -i "s/port=\"8080\"/port=\"${PORT_TO_USE}\"/" /usr/local/tomcat/conf/server.xml' \
 '' \
 'cat > /usr/local/tomcat/conf/Catalina/localhost/ROOT.xml <<EOF' \
 '<Context>' \
 ' <Resource name="jdbc/myDatabase"' \
 ' auth="Container"' \
 ' type="javax.sql.DataSource"' \
 ' driverClassName="com.mysql.cj.jdbc.Driver"' \
 ' url="jdbc:mysql://${MYSQLHOST}:${MYSQLPORT:-3306}/${MYSQLDATABASE}?useSSL=false&amp;allowPublicKeyRetrieval=true&amp;serverTimezone=UTC"' \
 ' username="${MYSQLUSER}"' \
 ' password="${MYSQLPASSWORD}"' \
 ' maxTotal="10"' \
 ' maxIdle="5"' \
 ' maxWaitMillis="10000"/>' \
 '</Context>' \
 'EOF' \
 '' \
 'exec catalina.sh run' \
 > /usr/local/bin/startup.sh && \
 chmod +x /usr/local/bin/startup.shEXPOSE8080CMD ["/usr/local/bin/startup.sh"]
