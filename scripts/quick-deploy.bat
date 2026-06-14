@echo off
REM Quick Deploy Script for Advising System (Windows)

setlocal enabledelayedexpansion

echo.
echo 🚀 Advising System - Quick Deploy Assistant (Windows)
echo =========================================================
echo.

REM Check Java
java -version >nul 2>&1
if %errorlevel% neq 0 (
    echo ❌ Java not found. Install from: https://www.oracle.com/java/technologies/downloads/
    pause
    exit /b 1
)
echo ✅ Java found
for /f "tokens=*" %%i in ('java -version 2^>^&1') do set JAVA_VER=%%i
echo %JAVA_VER%
echo.

REM Check Maven
mvn -version >nul 2>&1
if %errorlevel% neq 0 (
    echo ❌ Maven not found. Install from: https://maven.apache.org/download.cgi
    echo    And add to System PATH
    pause
    exit /b 1
)
echo ✅ Maven found
echo.

REM Build the project
echo 📦 Building Backend JAR...
echo.
mvn clean package -DskipTests

if %errorlevel% equ 0 (
    echo.
    echo ✅ Build successful!
    echo.
    echo 🚀 Next steps:
    echo.
    echo 1. Test locally:
    echo    java -jar target\advising-system-1.0.0.jar
    echo.
    echo 2. Deploy backend to Railway:
    echo    npm install -g @railway/cli
    echo    railway login
    echo    railway init
    echo    railway up
    echo.
    echo 3. Create React frontend:
    echo    npx create-react-app frontend
    echo    cd frontend
    echo    npm install axios react-router-dom
    echo.
    echo 4. Deploy to Vercel:
    echo    npm install -g vercel
    echo    cd frontend
    echo    vercel
    echo.
    echo See DEPLOY_TO_VERCEL.md for complete instructions!
    echo.
    pause
) else (
    echo.
    echo ❌ Build failed. Check errors above.
    pause
    exit /b 1
)

endlocal
