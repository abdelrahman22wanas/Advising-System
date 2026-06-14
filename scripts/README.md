# 🛠️ Build & Deployment Scripts

Helper scripts for building and deploying the application.

## Quick Deploy Scripts

### Windows: `quick-deploy.bat`
Automated build script for Windows:
```bash
cd scripts
quick-deploy.bat
```

**Does**:
- Checks for Java installation
- Checks for Maven installation
- Builds the backend: `mvn clean package -DskipTests`
- Provides next steps

### Linux/Mac: `quick-deploy.sh`
Automated build script for Unix-like systems:
```bash
cd scripts
bash quick-deploy.sh
```

**Does**:
- Checks for Java installation
- Checks for Maven installation
- Checks for Node.js installation
- Builds the backend
- Provides next steps

## Legacy Build Scripts

### Windows: `build-and-run.bat`
Old desktop build script (archived)
- For reference only
- Use `quick-deploy.bat` instead

### Linux/Mac: `build-and-run.sh`
Old desktop build script (archived)
- For reference only
- Use `quick-deploy.sh` instead

## Frontend Setup Script

### Linux/Mac: `setup-frontend.sh`
Creates a React frontend with dependencies:
```bash
cd scripts
bash setup-frontend.sh
```

**Does**:
- Creates React app with `npx create-react-app`
- Installs dependencies
- Creates folder structure
- Sets up environment files
- Provides next steps

## Manual Build Steps

### Backend Only
```bash
cd backend
mvn clean package -DskipTests
java -jar target/advising-system-1.0.0.jar
```

### Frontend Only
```bash
cd frontend
npm install
npm start
```

### Both (Development)
**Terminal 1** (Backend):
```bash
cd backend
mvn clean package -DskipTests
java -jar target/advising-system-1.0.0.jar
```

**Terminal 2** (Frontend):
```bash
cd frontend
npm start
```

## Deployment Commands

### Deploy to Railway (Backend)
```bash
npm install -g @railway/cli
railway login
cd .. (back to root)
railway init
railway up
```

### Deploy to Vercel (Frontend)
```bash
npm install -g vercel
cd frontend
vercel
```

## Available Scripts Summary

| Script | Platform | Purpose |
|--------|----------|---------|
| quick-deploy.bat | Windows | Build backend |
| quick-deploy.sh | Linux/Mac | Build backend |
| setup-frontend.sh | Linux/Mac | Create frontend |
| build-and-run.bat | Windows | Legacy - Desktop build |
| build-and-run.sh | Linux/Mac | Legacy - Desktop build |

## Environment Setup

### Prerequisites for Scripts

**All Platforms**:
- Java 21 LTS
- Maven 3.6+
- Node.js 18+ (for frontend)
- Git (optional)

### Install Prerequisites

**Windows**:
1. Java: https://www.oracle.com/java/technologies/downloads/
2. Maven: https://maven.apache.org/download.cgi
3. Node.js: https://nodejs.org/

**Mac** (using Homebrew):
```bash
brew install java@21
brew install maven
brew install node
```

**Linux** (Ubuntu/Debian):
```bash
sudo apt-get install openjdk-21-jdk
sudo apt-get install maven
sudo apt-get install nodejs npm
```

## Troubleshooting Scripts

### Script Not Found
```bash
# Make sure you're in the scripts directory
cd scripts

# For bash scripts, might need to make executable
chmod +x *.sh
bash script-name.sh
```

### Maven Not Found
```bash
# Check installation
mvn -version

# Add to PATH if needed (Windows)
# Or set MAVEN_HOME environment variable
```

### Scripts Directory

All scripts should be run from the `scripts/` directory or updated to reference correct paths.

## What Each Script Does

### quick-deploy.bat (Windows)
```batch
1. Check Java is installed
2. Check Maven is installed
3. Run: mvn clean package -DskipTests
4. Show next steps
```

### quick-deploy.sh (Linux/Mac)
```bash
1. Check Java is installed
2. Check Maven is installed
3. Check Node.js is installed
4. Run: mvn clean package -DskipTests
5. Show next steps for deployment
```

### setup-frontend.sh (Linux/Mac)
```bash
1. Check Node.js is installed
2. Create React app
3. Install dependencies
4. Create folder structure
5. Create .env files
6. Show next steps
```

## Next Steps After Running Scripts

After running a script, follow the provided instructions:

1. **Build Successful?** → Follow script output
2. **Test Locally** → Run the JAR file
3. **Create Frontend** → Use setup-frontend.sh
4. **Deploy** → Follow [../docs/DEPLOY_TO_VERCEL.md](../docs/DEPLOY_TO_VERCEL.md)

## Manual Verification

After running scripts, verify manually:

```bash
# Check backend built
ls -la backend/target/advising-system-1.0.0.jar

# Check frontend created (if applicable)
ls -la frontend/package.json
```

## Support

- **Build Issues** → Check Maven installation
- **Frontend Issues** → Check Node.js installation
- **Deployment** → See [../docs/DEPLOY_TO_VERCEL.md](../docs/DEPLOY_TO_VERCEL.md)

---

**Need help?** Check [../docs/DEPLOY_TO_VERCEL.md](../docs/DEPLOY_TO_VERCEL.md) 🚀
