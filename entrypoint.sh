#!/bin/sh
# Baca secret dari /run/secrets lalu export ke environment variable

if [ -f /run/secrets/mongodb_uri ]; then
  export MONGODB_URI=$(cat /run/secrets/mongodb_uri)
fi

if [ -f /run/secrets/mongodb_db ]; then
  export MONGODB_DB=$(cat /run/secrets/mongodb_db)
fi

# Jalankan aplikasi Quarkus
exec java -jar /app/quarkus-app/quarkus-run.jar