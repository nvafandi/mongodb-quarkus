#!/bin/sh
# Read secrets froms /run/secrets then export to environment variable

if [ -f /run/secrets/mongodb_uri ]; then
  export MONGODB_URI=$(cat /run/secrets/mongodb_uri)
fi

if [ -f /run/secrets/mongodb_db ]; then
  export MONGODB_DB=$(cat /run/secrets/mongodb_db)
fi

# Running docker
exec java -jar /app/quarkus-app/quarkus-run.jar