#!/bin/sh
set -e

# Default to 8080 if PORT isn't provided
PORT_TO_USE="${PORT:-8080}"

# Replace the default port in Tomcat's server.xml
sed -i "s/port=\"8080\"/port=\"${PORT_TO_USE}\"/" /usr/local/tomcat/conf/server.xml

# Generate the Tomcat Resource configuration (ROOT.xml)
# Note: Using jakarta.sql.DataSource for Tomcat 10+
cat > /usr/local/tomcat/conf/Catalina/localhost/ROOT.xml <<EOF
<Context>
  <Resource name="jdbc/myDatabase"
    auth="Container"
    type="jakarta.sql.DataSource"
    driverClassName="com.mysql.cj.jdbc.Driver"
    url="jdbc:mysql://${MYSQLHOST}:${MYSQLPORT:-3306}/${MYSQLDATABASE}?useSSL=false&amp;allowPublicKeyRetrieval=true&amp;serverTimezone=UTC"
    username="${MYSQLUSER}"
    password="${MYSQLPASSWORD}"
    maxTotal="10"
    maxIdle="5"
    maxWaitMillis="10000"/>
</Context>
EOF

# Hand off execution to Tomcat
exec catalina.sh run