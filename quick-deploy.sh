#!/bin/bash
# Quick Deploy Script for Advising System
# This script helps you deploy to Vercel quickly

echo "🚀 Advising System - Quick Deploy Assistant"
echo "=========================================="
echo ""

# Check prerequisites
echo "📋 Checking prerequisites..."

# Check Java
if command -v java &> /dev/null; then
    JAVA_VERSION=$(java -version 2>&1 | grep "version" | awk '{print $3}' | tr -d '"')
    echo "✅ Java: $JAVA_VERSION"
else
    echo "❌ Java not found. Install from: https://www.oracle.com/java/technologies/downloads/"
    exit 1
fi

# Check Maven
if command -v mvn &> /dev/null; then
    MVN_VERSION=$(mvn -version 2>&1 | grep "Apache Maven" | awk '{print $3}')
    echo "✅ Maven: $MVN_VERSION"
else
    echo "❌ Maven not found. Install from: https://maven.apache.org/download.cgi"
    echo "   And add to PATH"
    exit 1
fi

# Check Node
if command -v node &> /dev/null; then
    NODE_VERSION=$(node --version)
    echo "✅ Node.js: $NODE_VERSION"
else
    echo "⚠️  Node.js not found. Need for React frontend. Install from: https://nodejs.org/"
fi

echo ""
echo "📦 Building Backend JAR..."
mvn clean package -DskipTests

if [ $? -eq 0 ]; then
    echo "✅ Build successful!"
    echo ""
    echo "🚀 Next steps:"
    echo ""
    echo "1. Test locally:"
    echo "   java -jar target/advising-system-1.0.0.jar"
    echo ""
    echo "2. Deploy backend to Railway:"
    echo "   npm install -g @railway/cli"
    echo "   railway login"
    echo "   railway init"
    echo "   railway up"
    echo ""
    echo "3. Create React frontend:"
    echo "   npx create-react-app frontend"
    echo "   cd frontend"
    echo "   npm install axios react-router-dom"
    echo ""
    echo "4. Deploy to Vercel:"
    echo "   npm install -g vercel"
    echo "   cd frontend"
    echo "   vercel"
    echo ""
    echo "See DEPLOY_TO_VERCEL.md for complete instructions!"
else
    echo "❌ Build failed. Check errors above."
    exit 1
fi
