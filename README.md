# 🎓 Academic Advising System - Web Application

A modern, fully responsive Academic Advising System built with **Spring Boot REST API** and **React** frontend, ready for deployment to Vercel.

## 📦 Project Structure (Reorganized)

```
Advising-System/
├── backend/                    Spring Boot REST API (Java 21)
│   ├── src/main/java/         Java source code
│   ├── src/main/resources/     Configuration files
│   ├── pom.xml                 Maven configuration
│   └── README.md               Backend documentation
│
├── frontend/                   React Application (to be created)
│   ├── src/                    React source code
│   ├── public/                 Static assets
│   └── package.json            NPM dependencies
│
├── docs/                       Documentation (14 files)
│   ├── DEPLOY_TO_VERCEL.md    ⭐ Deployment guide
│   ├── REACT_SETUP.md          Frontend setup
│   ├── DEPLOYMENT_READY.md     Quick reference
│   └── ... (more docs)
│
├── scripts/                    Build and deployment scripts
│   ├── quick-deploy.bat        Windows build
│   ├── quick-deploy.sh         Linux/Mac build
│   └── ... (more scripts)
│
└── archive/                    Reference materials
    └── PDFs, spreadsheets, etc.
```

## 🚀 Quick Start

### 1️⃣ Backend (Spring Boot)
```bash
cd backend
mvn clean package -DskipTests
java -jar target/advising-system-1.0.0.jar
```
**API**: http://localhost:8080/api

### 2️⃣ Frontend (React)
```bash
npx create-react-app frontend
cd frontend
npm install axios react-router-dom
npm start
```
**App**: http://localhost:3000

### 3️⃣ Deploy to Production
See **[docs/DEPLOY_TO_VERCEL.md](docs/DEPLOY_TO_VERCEL.md)** for complete instructions.

---

## 📚 Documentation

| Document | Purpose |
|----------|---------|
| **[docs/DEPLOY_TO_VERCEL.md](docs/DEPLOY_TO_VERCEL.md)** | ⭐ **START HERE** - Deployment guide |
| [docs/REACT_SETUP.md](docs/REACT_SETUP.md) | Frontend setup & code examples |
| [docs/DEPLOYMENT_READY.md](docs/DEPLOYMENT_READY.md) | Architecture & API reference |
| [docs/DEPLOYMENT_GUIDE.md](docs/DEPLOYMENT_GUIDE.md) | Detailed setup instructions |
| [backend/README.md](backend/README.md) | Backend documentation |

---

## 🎯 API Endpoints

Base URL: `/api`

**Dashboard**:
- `GET /dashboard/stats` - Statistics
- `GET /dashboard/health` - Health check

**Students**: `/students` (CRUD + filters)
**Courses**: `/courses` (CRUD + filters)
**Advisors**: `/advisors` (CRUD + assignments)
**Enrollments**: `/enrollments` (enroll, drop, track)
**Grades**: `/grades` (record, calculate GPA)
**Sessions**: `/sessions` (create, track, update status)

See [docs/DEPLOYMENT_READY.md#-api-endpoints-quick-reference](docs/DEPLOYMENT_READY.md#-api-endpoints-quick-reference) for full list.

---

## 💾 Sample Data

Auto-loaded on startup:
- **15 Students** (STU001-STU015)
- **71 CSE Courses** (complete curriculum)
- **6 Advisors** with assignments
- **15 Sessions** (various statuses)

No database required - data is in-memory!

---

## 🛠 Tech Stack

| Layer | Technology |
|-------|-----------|
| **Backend** | Java 21 LTS + Spring Boot 3.2 |
| **Frontend** | React 18+ + Axios |
| **Build** | Maven 3.6+ |
| **Deployment** | Vercel + Railway/Render/Heroku |

---

## 🚀 Deployment Options

| Option | Backend | Frontend | Setup Time | Cost |
|--------|---------|----------|-----------|------|
| ⭐ **Recommended** | Railway | Vercel | 5 min | Free |
| **Alternative** | Render | Vercel | 10 min | Free |
| **Traditional** | Heroku | Vercel | 15 min | Paid |

See [docs/DEPLOY_TO_VERCEL.md](docs/DEPLOY_TO_VERCEL.md) for detailed instructions.

---

## ✨ Features

✅ 30+ REST API endpoints  
✅ CORS enabled for frontend  
✅ Automatic data initialization  
✅ Complete CSE curriculum (71 courses)  
✅ GPA calculations  
✅ Prerequisite validation  
✅ Student enrollment tracking  
✅ Advisor assignment system  
✅ Session scheduling  
✅ Grade management  

---

## 📂 What's Where?

```
Source Code:        backend/src/main/java/
Configuration:      backend/src/main/resources/
Deployment Docs:    docs/DEPLOY_TO_VERCEL.md
API Reference:      docs/DEPLOYMENT_READY.md
Build Scripts:      scripts/
Reference Files:    archive/
```

---

## 🔧 Requirements

- **Java 21** (for backend)
- **Maven 3.6+** (for building)
- **Node.js 18+** (for frontend)
- **Git** (optional)

---

## 📖 Next Steps

1. **Read Documentation**
   - Start with [docs/DEPLOY_TO_VERCEL.md](docs/DEPLOY_TO_VERCEL.md)

2. **Build Backend**
   - `cd backend && mvn clean package -DskipTests`

3. **Test Locally**
   - `java -jar backend/target/advising-system-1.0.0.jar`

4. **Create Frontend**
   - `npx create-react-app frontend`
   - Follow [docs/REACT_SETUP.md](docs/REACT_SETUP.md)

5. **Deploy**
   - Backend → Railway/Render
   - Frontend → Vercel

---

## 🎊 Project Reorganized!

This project has been restructured into a modern monorepo:
- ✅ **Backend** and **Frontend** clearly separated
- ✅ **Documentation** organized in `docs/` folder
- ✅ **Scripts** in dedicated `scripts/` folder
- ✅ **Reference materials** archived for later reference

See [docs/FILES_REFERENCE.md](docs/FILES_REFERENCE.md) for complete file structure details.

---

## 📞 Help & Support

- **Deployment Issues?** → [docs/DEPLOY_TO_VERCEL.md](docs/DEPLOY_TO_VERCEL.md#-troubleshooting)
- **Frontend Questions?** → [docs/REACT_SETUP.md](docs/REACT_SETUP.md)
- **API Questions?** → [docs/DEPLOYMENT_READY.md](docs/DEPLOYMENT_READY.md)
- **Backend Questions?** → [backend/README.md](backend/README.md)

---

**Ready to deploy?** Start with **[docs/DEPLOY_TO_VERCEL.md](docs/DEPLOY_TO_VERCEL.md)** 🚀
