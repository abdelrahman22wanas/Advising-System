@echo off
REM Build and Run Script for Advising System (Windows)

echo.
echo ==========================================
echo    ACADEMIC ADVISING SYSTEM
echo    Build and Run Script (Windows)
echo ==========================================
echo.

REM Check if Maven is installed
where mvn >nul 2>nul
if %errorlevel% neq 0 (
    echo ERROR: Maven is not installed or not in PATH
    echo Please install Maven first: https://maven.apache.org/download.cgi
    exit /b 1
)

REM Check if Java is installed
where java >nul 2>nul
if %errorlevel% neq 0 (
    echo ERROR: Java is not installed or not in PATH
    echo Please install Java 17 or higher
    exit /b 1
)

echo Maven version:
mvn -v
echo.
echo Java version:
java -version
echo.

REM Build the project
echo Building the project...
mvn clean install

if %errorlevel% neq 0 (
    echo ERROR: Build failed!
    exit /b 1
)

echo.
echo ==========================================
echo    Build Successful!
echo ==========================================
echo.

REM Ask user if they want to run the application
set /p RUN="Do you want to run the application now? (y/n): "

if /i "%RUN%"=="y" (
    echo Starting Academic Advising System...
    echo.
    mvn exec:java -Dexec.mainClass="com.advisingsystem.AdvisingSystemApp"
) else (
    echo To run the application later, use:
    echo   mvn exec:java -Dexec.mainClass="com.advisingsystem.AdvisingSystemApp"
    echo.
    echo Or compile and run directly:
    echo   javac -d bin src\main\java\com\advisingsystem\**\*.java
    echo   java -cp bin com.advisingsystem.AdvisingSystemApp
)

pause
