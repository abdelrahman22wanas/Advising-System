# Advising System Web Deployment Guide

## Prerequisites

### 1. Install Java 21
- Download from: https://www.oracle.com/java/technologies/downloads/
- Verify: `java -version`

### 2. Install Maven 3.6+
- Download from: https://maven.apache.org/download.cgi
- Extract to a folder
- Add `MAVEN_HOME/bin` to PATH
- Verify: `mvn -v`

### 3. Install Git & Node.js
- Git: https://git-scm.com/download/win
- Node.js: https://nodejs.org/ (includes npm)
- Verify: `git --version` and `node --version`

---

## Backend Deployment (Spring Boot API)

### Option 1: Deploy to Railway (Recommended)

1. **Create a Railway account**: https://railway.app

2. **Initialize Railway project**:
   ```bash
   npm i -g @railway/cli
   railway login
   railway init
   ```

3. **Add `vercel.json` for build**:
   ```json
   {
     "buildCommand": "mvn clean package -DskipTests",
     "outputDirectory": "target"
   }
   ```

4. **Deploy**:
   ```bash
   railway up
   ```

### Option 2: Deploy to Render

1. **Create a Render account**: https://render.com

2. **Create a new Web Service**:
   - Connect your GitHub repository
   - Runtime: Java 21
   - Build Command: `mvn clean package -DskipTests`
   - Start Command: `java -jar target/advising-system-1.0.0.jar`
   - Expose port: 8080

3. **Deploy** - Render auto-deploys on push

### Option 3: Deploy to Heroku (with free tier alternatives)

1. **Create account**: https://www.heroku.com

2. **Install Heroku CLI**:
   ```bash
   npm install -g heroku
   heroku login
   ```

3. **Create Procfile**:
   ```
   web: java -Dserver.port=$PORT $JAVA_OPTS -jar target/advising-system-1.0.0.jar
   ```

4. **Deploy**:
   ```bash
   heroku create your-app-name
   git push heroku main
   ```

---

## Frontend Deployment (React to Vercel)

### 1. Create React Frontend

```bash
npx create-react-app frontend
cd frontend
```

### 2. Create Environment Configuration

**Create `.env.production`**:
```
REACT_APP_API_URL=https://your-backend-domain.com
```

### 3. Update API Service

**`src/services/api.js`**:
```javascript
const API_URL = process.env.REACT_APP_API_URL || 'http://localhost:8080/api';

export const api = {
  // Students
  getStudents: () => fetch(`${API_URL}/students`).then(r => r.json()),
  getStudent: (id) => fetch(`${API_URL}/students/${id}`).then(r => r.json()),
  addStudent: (student) => fetch(`${API_URL}/students`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(student)
  }).then(r => r.json()),
  
  // Add similar methods for courses, advisors, etc.
};
```

### 4. Build React App

```bash
npm run build
```

### 5. Deploy to Vercel

**Option A: Using Vercel CLI**:
```bash
npm install -g vercel
vercel
```

**Option B: Using GitHub**:
1. Push to GitHub
2. Go to https://vercel.com
3. Import your GitHub repository
4. Set environment variables
5. Deploy

---

## Quick Start (Local Development)

### 1. Build Backend
```bash
cd Advising-System
mvn clean package -DskipTests
```

### 2. Run Backend
```bash
java -jar target/advising-system-1.0.0.jar
```

### 3. Run Frontend (in new terminal)
```bash
cd frontend
npm start
```

### 4. Access Application
- Backend API: http://localhost:8080
- Frontend: http://localhost:3000
- Dashboard Stats: http://localhost:8080/api/dashboard/stats
- Health Check: http://localhost:8080/api/dashboard/health

---

## API Endpoints Reference

### Dashboard
- `GET /api/dashboard/stats` - Get dashboard statistics
- `GET /api/dashboard/health` - Health check

### Students
- `GET /api/students` - List all students
- `GET /api/students/{id}` - Get student details
- `POST /api/students` - Add student
- `DELETE /api/students/{id}` - Remove student
- `GET /api/students/major/{major}` - Filter by major
- `GET /api/students/gpa-range/{min}/{max}` - Filter by GPA

### Courses
- `GET /api/courses` - List all courses
- `GET /api/courses/{id}` - Get course details
- `POST /api/courses` - Add course
- `GET /api/courses/available` - Get available courses

### Enrollments
- `POST /api/enrollments/student/{studentId}/course/{courseId}` - Enroll
- `DELETE /api/enrollments/student/{studentId}/course/{courseId}` - Drop
- `GET /api/enrollments/student/{studentId}` - Get student enrollments

### Grades
- `POST /api/grades` - Record grade
- `GET /api/grades/student/{studentId}` - Get student grades
- `GET /api/grades/student/{studentId}/gpa` - Calculate GPA

### Sessions
- `GET /api/sessions` - List sessions
- `POST /api/sessions` - Create session
- `GET /api/sessions/student/{studentId}` - Get student sessions

### Advisors
- `GET /api/advisors` - List advisors
- `POST /api/advisors` - Add advisor
- `GET /api/advisors/{id}/students` - Get assigned students

---

## Troubleshooting

### Maven not found
```bash
# Set MAVEN_HOME
export MAVEN_HOME=/path/to/maven
export PATH=$MAVEN_HOME/bin:$PATH
```

### Port 8080 already in use
```bash
java -jar target/advising-system-1.0.0.jar --server.port=9090
```

### CORS errors
- Backend has CORS enabled in `AdvisingSystemApiApp.java`
- Ensure frontend `REACT_APP_API_URL` matches backend URL

### Build fails
```bash
# Clean and rebuild
mvn clean
mvn install
```

---

## Next Steps

1. ✅ Set up Java 21 and Maven
2. ✅ Build Spring Boot backend: `mvn clean package`
3. ✅ Create React frontend: `npx create-react-app frontend`
4. ✅ Implement React UI components
5. ✅ Test locally
6. ✅ Deploy to Railway/Render (backend)
7. ✅ Deploy to Vercel (frontend)
8. ✅ Configure environment variables
9. ✅ Monitor and troubleshoot

