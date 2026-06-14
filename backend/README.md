# Backend - Spring Boot REST API

Spring Boot 3.2 REST API for the Advising System with 30+ endpoints.

## Quick Start

### Prerequisites
- Java 21 LTS
- Maven 3.6+

### Build
```bash
mvn clean package -DskipTests
```

### Run Locally
```bash
java -jar target/advising-system-1.0.0.jar
```

API available at: http://localhost:8080/api

### Test
```bash
# Health check
curl http://localhost:8080/api/dashboard/health

# Get all students
curl http://localhost:8080/api/students

# Get all courses
curl http://localhost:8080/api/courses
```

---

## Project Structure

```
backend/
├── src/main/java/com/advisingsystem/
│   ├── AdvisingSystemApiApp.java      (Spring Boot entry point)
│   ├── config/
│   │   └── ApplicationConfig.java      (Bean configuration)
│   ├── controller/                     (7 REST Controllers)
│   │   ├── StudentController.java
│   │   ├── CourseController.java
│   │   ├── AdvisorController.java
│   │   ├── EnrollmentController.java
│   │   ├── GradeController.java
│   │   ├── SessionController.java
│   │   └── DashboardController.java
│   ├── service/                        (7 Services - PRESERVED)
│   │   ├── StudentService.java
│   │   ├── CourseService.java
│   │   ├── AdvisorService.java
│   │   ├── EnrollmentService.java
│   │   ├── AdvicingSessionService.java
│   │   ├── GradeService.java
│   │   └── CurriculumService.java
│   ├── model/                          (Entity classes - PRESERVED)
│   │   ├── Student.java
│   │   ├── Course.java
│   │   ├── Advisor.java
│   │   └── ... (more models)
│   ├── data/                           (Data loading - PRESERVED)
│   │   └── CSECurriculumLoader.java
│   ├── javafx/                         (Legacy UI - PRESERVED)
│   │   ├── AdvisingSystemFXApp.java
│   │   └── SampleDataLoader.java
│   └── ui/                             (Legacy console UI - PRESERVED)
│       └── AdvisingSystemUI.java
│
├── src/main/resources/
│   └── application.yml                 (Spring configuration)
│
├── src/test/
│   └── java/com/advisingsystem/        (Unit tests)
│
├── pom.xml                             (Maven configuration)
└── README.md                           (This file)
```

---

## 🎯 API Endpoints

### Dashboard
```
GET  /api/dashboard/stats       → Dashboard statistics
GET  /api/dashboard/health      → API health check
```

### Students (7 endpoints)
```
GET    /api/students                    → List all students
GET    /api/students/{id}               → Get student by ID
POST   /api/students                    → Create new student
DELETE /api/students/{id}               → Delete student
GET    /api/students/major/{major}      → Filter by major
GET    /api/students/gpa-range/{min}/{max}
GET    /api/students/graduation/{year}
```

### Courses (6 endpoints)
```
GET  /api/courses                    → List all courses
GET  /api/courses/{id}               → Get course by ID
GET  /api/courses/department/{dept}  → Filter by department
GET  /api/courses/semester/{sem}     → Filter by semester
GET  /api/courses/available          → Available courses only
POST /api/courses                    → Create new course
```

### Advisors (5 endpoints)
```
GET  /api/advisors              → List all advisors
GET  /api/advisors/{id}         → Get advisor by ID
POST /api/advisors              → Create new advisor
POST /api/advisors/{aid}/students/{sid}  → Assign student
GET  /api/advisors/{id}/students → Get assigned students
```

### Enrollments (7 endpoints)
```
GET    /api/enrollments              → List all enrollments
POST   /api/enrollments/student/{sid}/course/{cid}  → Enroll
DELETE /api/enrollments/student/{sid}/course/{cid}  → Drop
GET    /api/enrollments/student/{sid}  → Get enrollments
GET    /api/enrollments/course/{cid}   → Get by course
GET    /api/enrollments/student/{sid}/available-courses
GET    /api/enrollments/student/{sid}/credits
```

### Grades (6 endpoints)
```
GET  /api/grades                       → List all grades
POST /api/grades                       → Record grade
GET  /api/grades/student/{sid}         → Get student grades
GET  /api/grades/student/{sid}/gpa     → Calculate GPA
GET  /api/grades/course/{cid}/average  → Course average
GET  /api/grades/student/{sid}/semester/{sem}
```

### Sessions (8 endpoints)
```
GET  /api/sessions              → List all sessions
GET  /api/sessions/{id}         → Get session by ID
POST /api/sessions              → Create session
DELETE /api/sessions/{id}       → Delete session
GET  /api/sessions/student/{sid} → Get student sessions
GET  /api/sessions/advisor/{aid} → Get advisor sessions
GET  /api/sessions/status/{status}
PUT  /api/sessions/{id}/status/{newStatus}
```

---

## 💾 Sample Data

Automatically loaded on startup:

**Students** (15 total, STU001-STU015)
- Various majors: Computer Science, Engineering
- GPA range: 3.2 - 3.92
- Graduation years: 2024-2027

**Courses** (71 total, complete CSE curriculum)
- University Requirements: 21 credits
- College Requirements: 15 credits
- Major Core: 81 credits
- Capstone: 6 credits
- Electives: 15+ credits
- Prerequisites fully configured

**Advisors** (6 total)
- Dr. Anderson, Prof. Brown, Dr. Davis
- Dr. Miller, Prof. Martinez, Dr. Thompson
- 2-3 students assigned each

**Sessions** (15 total)
- Various statuses: Completed, In Progress, Scheduled
- Date range: Past and future sessions

---

## 🛠 Build & Configuration

### Maven
```bash
# Clean
mvn clean

# Build (with tests)
mvn clean install

# Build (skip tests)
mvn clean package -DskipTests

# Run tests
mvn test

# View dependencies
mvn dependency:tree
```

### Configuration (application.yml)
```yaml
server:
  port: 8080

spring:
  application:
    name: advising-system-api
```

---

## 🚀 Deployment

See [../docs/DEPLOY_TO_VERCEL.md](../docs/DEPLOY_TO_VERCEL.md) for deployment instructions.

### Quick Deploy
```bash
# Build JAR
mvn clean package -DskipTests

# Deploy to Railway
npm install -g @railway/cli
railway init
railway up

# Or deploy to Render
# (Connect GitHub repo, auto-deploys)
```

---

## 📦 Dependencies

**Core**:
- Spring Boot Web 3.2.0
- Spring Boot Validation 3.2.0
- Spring Boot DevTools 3.2.0

**Testing**:
- Spring Boot Test 3.2.0

See `pom.xml` for complete list.

---

## ✨ Features

✅ RESTful API design  
✅ CORS enabled for frontend integration  
✅ Automatic data initialization  
✅ In-memory data storage (no database)  
✅ Input validation  
✅ Error handling  
✅ Comprehensive logging  

---

## 🔧 Troubleshooting

### Build fails
```bash
mvn clean install -U
```

### Port 8080 in use
```bash
java -jar target/advising-system-1.0.0.jar --server.port=9090
```

### CORS errors
- Already enabled globally in `AdvisingSystemApiApp.java`
- Check frontend API URL matches backend URL

---

## 📝 Code Examples

### Using the API with curl

**Get all students**:
```bash
curl http://localhost:8080/api/students
```

**Create a student**:
```bash
curl -X POST http://localhost:8080/api/students \
  -H "Content-Type: application/json" \
  -d '{"studentId":"NEW001","firstName":"John","lastName":"Doe","major":"CS"}'
```

**Enroll student in course**:
```bash
curl -X POST http://localhost:8080/api/enrollments/student/STU001/course/CS111
```

---

## 📚 Documentation

- **Backend Docs**: This file
- **Deployment**: [../docs/DEPLOY_TO_VERCEL.md](../docs/DEPLOY_TO_VERCEL.md)
- **API Reference**: [../docs/DEPLOYMENT_READY.md](../docs/DEPLOYMENT_READY.md)
- **Frontend Setup**: [../docs/REACT_SETUP.md](../docs/REACT_SETUP.md)

---

**Ready to deploy?** See [../docs/DEPLOY_TO_VERCEL.md](../docs/DEPLOY_TO_VERCEL.md) 🚀
