#!/bin/bash

echo "--- Building project and downloading dependencies with Maven ---"

# Compile and package the project (downloads all JARs and puts compiled classes in target/classes)
mvn compile

if [ $? -ne 0 ]; then
    echo "Maven Build FAILED. Check pom.xml and code errors."
    exit 1
fi

echo "Maven Build successful. ✅"

echo "--- Running Game Hub ---"

# Execute the main class using the Maven exec plugin
# Maven automatically manages the classpath using all downloaded dependencies.
mvn exec:java -Dexec.mainClass="gamehub.GAME_HUB"