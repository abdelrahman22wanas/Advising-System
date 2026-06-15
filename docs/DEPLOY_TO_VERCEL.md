# 🚀 Deploy Advising System to Vercel

> **Note:** Vercel is for the React frontend. The Spring Boot backend should stay on Railway, Render, Docker, or another long-running host.

## What Was Done ✅

This project has been successfully **converted from JavaFX desktop app to a Spring Boot REST API**. Here's what's ready:

### ✅ Backend (Spring Boot REST API)
- `AdvisingSystemApiApp.java` - Spring Boot entry point
- 6 REST Controllers:
  - `StudentController` - Student management endpoints
  - `CourseController` - Course management endpoints
  - `AdvisorController` - Advisor management endpoints
  - `EnrollmentController` - Enrollment operations
  - `GradeController` - Grade management
  - `SessionController` - Advising sessions
  - `DashboardController` - Dashboard stats & health check
- `ApplicationConfig.java` - Spring configuration with auto-initialization
- `application.yml` - Spring Boot configuration
- Updated `pom.xml` - Maven configuration with Spring Boot 3.2.0

### ✅ All Business Logic Preserved
- All existing services remain unchanged
- Curriculum loading (71 CSE courses)
- Sample data loading (15 students, 6 advisors, 15 sessions)
- Auto-initialization on startup

---

## 📋 Deployment Steps

### Step 1: Install Prerequisites

#### On Windows:
1. **Java 21 LTS**
   - Download: https://www.oracle.com/java/technologies/downloads/
   - Verify: `java -version`

2. **Maven 3.6+**
   - Download: https://maven.apache.org/download.cgi
   - Extract to folder (e.g., `C:\Maven`)
   - Add `C:\Maven\bin` to System PATH
   - Verify: `mvn -version`

3. **Git** (optional)
   - Download: https://git-scm.com/download/win

4. **Node.js 18+** (for frontend)
   - Download: https://nodejs.org/
   - Verify: `node --version` and `npm --version`

---

### Step 2: Build the Backend

```bash
cd E:\Advising-System

# Clean build
mvn clean install

# Package as JAR
mvn package -DskipTests
```

**Expected output**: `target/advising-system-1.0.0.jar` (created successfully)

---

### Step 3: Test Backend Locally

```bash
java -jar target/advising-system-1.0.0.jar
```

Then test endpoints:
- Health: http://localhost:8080/api/dashboard/health
- Stats: http://localhost:8080/api/dashboard/stats
- Students: http://localhost:8080/api/students
- Courses: http://localhost:8080/api/courses

---

### Step 4: Create React Frontend

```bash
cd E:\Advising-System

# Create React app
npx create-react-app frontend

cd frontend

# Install dependencies
npm install axios react-router-dom chart.js react-chartjs-2
```

---

### Step 5: Create React Components (Sample)

**`frontend/src/services/api.js`**:
```javascript
const API_URL = process.env.REACT_APP_API_URL || 'http://localhost:8080/api';

export const api = {
  getStudents: () => fetch(`${API_URL}/students`).then(r => r.json()),
  getCourses: () => fetch(`${API_URL}/courses`).then(r => r.json()),
  getAdvisors: () => fetch(`${API_URL}/advisors`).then(r => r.json()),
  getDashboardStats: () => fetch(`${API_URL}/dashboard/stats`).then(r => r.json()),
};
```

**`frontend/src/pages/Dashboard.js`**:
```javascript
import React, { useEffect, useState } from 'react';
import { api } from '../services/api';

export default function Dashboard() {
  const [stats, setStats] = useState(null);

  useEffect(() => {
    api.getDashboardStats().then(setStats);
  }, []);

  if (!stats) return <div>Loading...</div>;

  return (
    <div>
      <h1>Dashboard</h1>
      <p>Students: {stats.totalStudents}</p>
      <p>Courses: {stats.totalCourses}</p>
      <p>Advisors: {stats.totalAdvisors}</p>
      <p>Sessions: {stats.totalSessions}</p>
    </div>
  );
}
```

---

### Step 6: Deploy Backend to Railway (Recommended)

#### Option A: Railway (Free tier available)

1. **Create Railway account**: https://railway.app

2. **Install Railway CLI**:
   ```bash
   npm install -g @railway/cli
   railway login
   ```

3. **Deploy from project root**:
   ```bash
   cd E:\Advising-System
   railway init
   railway up
   ```

4. **Get your backend URL**:
   ```bash
   railway environment
   ```

#### Option B: Render (Alternative)

1. **Create account**: https://render.com

2. **Create Web Service**:
   - Connect GitHub repo
   - Runtime: Java 21
   - Build: `mvn clean package -DskipTests`
   - Start: `java -jar target/advising-system-1.0.0.jar`
   - Port: 8080

3. **Deploy** - Auto-deploys on git push

#### Option C: Heroku

1. **Install Heroku CLI**:
   ```bash
   npm install -g heroku
   heroku login
   ```

2. **Create `Procfile`**:
   ```
   web: java -Dserver.port=$PORT $JAVA_OPTS -jar target/advising-system-1.0.0.jar
   ```

3. **Deploy**:
   ```bash
   heroku create your-app-name
   git push heroku main
   ```

---

### Step 7: Deploy Frontend to Vercel

#### Option A: Using Vercel CLI

```bash
cd E:\Advising-System\frontend
npm install -g vercel

# Update .env.production with backend URL first
# Then deploy
vercel
```

If you deploy from the repository root, the root `vercel.json` points Vercel at `frontend/`.

#### Option B: Using GitHub

1. Push your code to GitHub:
   ```bash
   git push origin main
   ```

2. Go to https://vercel.com

3. Click "Import Project"

4. Select your GitHub repository

5. Set environment variables:
   ```
   REACT_APP_API_URL = https://your-railway-url.com/api
   ```

6. Click "Deploy"

### If you want the backend on the web too
- Keep the backend on Railway/Render/Docker/Heroku
- Point `REACT_APP_API_URL` to that backend URL
- Vercel should only host the React app

---

## 🎯 API Endpoints Available

### Dashboard
- `GET /api/dashboard/stats` - Get statistics
- `GET /api/dashboard/health` - Health check

### Students
- `GET /api/students` - List all
- `GET /api/students/{id}` - Get one
- `POST /api/students` - Create
- `DELETE /api/students/{id}` - Delete
- `GET /api/students/major/{major}` - Filter
- `GET /api/students/gpa-range/{min}/{max}` - Filter

### Courses
- `GET /api/courses` - List all
- `GET /api/courses/{id}` - Get one
- `GET /api/courses/available` - Get available

### Advisors
- `GET /api/advisors` - List all
- `GET /api/advisors/{id}` - Get one
- `GET /api/advisors/{id}/students` - Get assigned students

### Enrollments
- `GET /api/enrollments/student/{studentId}` - Get enrollments
- `POST /api/enrollments/student/{sid}/course/{cid}` - Enroll
- `DELETE /api/enrollments/student/{sid}/course/{cid}` - Drop

### Grades
- `POST /api/grades` - Record grade
- `GET /api/grades/student/{studentId}` - Get grades
- `GET /api/grades/student/{studentId}/gpa` - Calculate GPA

### Sessions
- `GET /api/sessions` - List all
- `GET /api/sessions/{id}` - Get one
- `GET /api/sessions/student/{studentId}` - Get student sessions
- `POST /api/sessions` - Create session

---

## 🧪 Testing the Deployment

After deployment, test these URLs:

1. **Health Check**:
   ```
   GET https://your-backend-url.com/api/dashboard/health
   ```
   Expected: `{"status":"UP","message":"Advising System API is running"}`

2. **Get Students**:
   ```
   GET https://your-backend-url.com/api/students
   ```
   Expected: Array of 15 students with sample data

3. **Get Courses**:
   ```
   GET https://your-backend-url.com/api/courses
   ```
   Expected: Array of 71 CSE courses

4. **Dashboard Stats**:
   ```
   GET https://your-backend-url.com/api/dashboard/stats
   ```
   Expected: Statistics object with student count, courses, advisors, sessions

---

## 📊 Sample Data Loaded Automatically

When the application starts, it loads:
- **15 Students** (STU001-STU015) with various majors, GPA ranges
- **71 Courses** (Complete CSE curriculum)
- **6 Advisors** with assigned students
- **15 Sessions** (various statuses)
- Full enrollment, grade, and prerequisite data

---

## 🔧 Troubleshooting

### Maven not found
```bash
# Check installation
mvn -version

# If not found, add to PATH or use full path
C:\Maven\bin\mvn clean package
```

### Port 8080 already in use
```bash
# Use different port
java -jar target/advising-system-1.0.0.jar --server.port=9090
```

### CORS errors in browser
- ✅ Backend already has CORS enabled
- Check that `REACT_APP_API_URL` in frontend matches backend URL

### Build fails
```bash
# Clean and retry
mvn clean
mvn install
```

---

## 📝 Files Added/Modified

### New Controllers
- `/src/main/java/com/advisingsystem/controller/StudentController.java`
- `/src/main/java/com/advisingsystem/controller/CourseController.java`
- `/src/main/java/com/advisingsystem/controller/AdvisorController.java`
- `/src/main/java/com/advisingsystem/controller/EnrollmentController.java`
- `/src/main/java/com/advisingsystem/controller/GradeController.java`
- `/src/main/java/com/advisingsystem/controller/SessionController.java`
- `/src/main/java/com/advisingsystem/controller/DashboardController.java`

### New Configuration
- `/src/main/java/com/advisingsystem/AdvisingSystemApiApp.java`
- `/src/main/java/com/advisingsystem/config/ApplicationConfig.java`
- `/src/main/resources/application.yml`

### Modified
- `pom.xml` - Updated with Spring Boot 3.2.0

---

## 🎉 You're Ready to Deploy!

Follow the steps above to:
1. ✅ Build backend JAR
2. ✅ Deploy to Railway/Render/Heroku
3. ✅ Create React frontend
4. ✅ Deploy to Vercel
5. ✅ Test live API

**Need help?** Check `DEPLOYMENT_GUIDE.md` for detailed instructions.

