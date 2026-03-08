#!/bin/sh
set -e

# Ensure the directory exists before writing to it
mkdir -p /usr/local/tomcat/conf/Catalina/localhost/

PORT_TO_USE="${PORT:-8080}"
echo "--- Starting Tomcat on Port: $PORT_TO_USE ---"

sed -i "s/port=\"8080\"/port=\"${PORT_TO_USE}\"/" /usr/local/tomcat/conf/server.xml

# Now this will work because the directory exists
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

exec catalina.sh run