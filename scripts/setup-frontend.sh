#!/bin/bash

# Advising System Frontend Setup Script
# This script creates a React frontend for the Advising System API

echo "🚀 Creating React Frontend..."

# Check if Node.js is installed
if ! command -v node &> /dev/null; then
    echo "❌ Node.js is not installed. Please install Node.js 16+ from https://nodejs.org/"
    exit 1
fi

echo "✅ Node.js found: $(node --version)"

# Create React app
npx create-react-app frontend

cd frontend

# Install additional dependencies
npm install axios react-router-dom chart.js react-chartjs-2

# Create directories
mkdir -p src/pages src/components src/services src/styles

# Create environment file
cat > .env.development << 'EOF'
REACT_APP_API_URL=http://localhost:8080/api
REACT_APP_APP_NAME=Advising System
EOF

cat > .env.production << 'EOF'
REACT_APP_API_URL=https://your-api-domain.com/api
REACT_APP_APP_NAME=Advising System
EOF

echo "✅ Frontend created successfully!"
echo ""
echo "📝 Next steps:"
echo "1. Update REACT_APP_API_URL in .env.production with your backend URL"
echo "2. Implement React components in src/pages and src/components"
echo "3. Create API service methods in src/services/api.js"
echo "4. Test locally: npm start"
echo "5. Build for production: npm run build"
echo "6. Deploy to Vercel: vercel"
