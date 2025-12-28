#!/bin/bash
# Build and Run Script for Advising System

echo "=========================================="
echo "   ACADEMIC ADVISING SYSTEM"
echo "   Build and Run Script"
echo "=========================================="
echo ""

# Check if Maven is installed
if ! command -v mvn &> /dev/null; then
    echo "ERROR: Maven is not installed or not in PATH"
    echo "Please install Maven first: https://maven.apache.org/download.cgi"
    exit 1
fi

# Check if Java is installed
if ! command -v java &> /dev/null; then
    echo "ERROR: Java is not installed or not in PATH"
    echo "Please install Java 17 or higher"
    exit 1
fi

echo "Maven version:"
mvn -v
echo ""
echo "Java version:"
java -version
echo ""

# Build the project
echo "Building the project..."
mvn clean install

if [ $? -ne 0 ]; then
    echo "ERROR: Build failed!"
    exit 1
fi

echo ""
echo "=========================================="
echo "   Build Successful!"
echo "=========================================="
echo ""

# Ask user if they want to run the application
read -p "Do you want to run the application now? (y/n) " -n 1 -r
echo ""

if [[ $REPLY =~ ^[Yy]$ ]]; then
    echo "Starting Academic Advising System..."
    echo ""
    mvn exec:java -Dexec.mainClass="com.advisingsystem.AdvisingSystemApp"
else
    echo "To run the application later, use:"
    echo "  mvn exec:java -Dexec.mainClass=\"com.advisingsystem.AdvisingSystemApp\""
    echo ""
    echo "Or compile and run directly:"
    echo "  javac -d bin src/main/java/com/advisingsystem/**/*.java"
    echo "  java -cp bin com.advisingsystem.AdvisingSystemApp"
fi
