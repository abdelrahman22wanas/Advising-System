# React Frontend Setup Guide

## Quick Start

### 1. Create React App
```bash
cd Advising-System
npx create-react-app frontend
cd frontend
npm install axios react-router-dom
```

### 2. Create Folder Structure
```bash
mkdir src/{pages,components,services,styles}
```

### 3. Create API Service (src/services/api.js)

```javascript
const API_URL = process.env.REACT_APP_API_URL || 'http://localhost:8080/api';

export const api = {
  // Dashboard
  getDashboardStats: () => 
    fetch(`${API_URL}/dashboard/stats`).then(r => r.json()),
  
  // Students
  getStudents: () => 
    fetch(`${API_URL}/students`).then(r => r.json()),
  getStudent: (id) => 
    fetch(`${API_URL}/students/${id}`).then(r => r.json()),
  addStudent: (student) => 
    fetch(`${API_URL}/students`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(student)
    }).then(r => r.json()),
  deleteStudent: (id) =>
    fetch(`${API_URL}/students/${id}`, { method: 'DELETE' }),
  
  // Courses
  getCourses: () => 
    fetch(`${API_URL}/courses`).then(r => r.json()),
  getCourse: (id) => 
    fetch(`${API_URL}/courses/${id}`).then(r => r.json()),
  
  // Advisors
  getAdvisors: () => 
    fetch(`${API_URL}/advisors`).then(r => r.json()),
  
  // Enrollments
  enrollStudent: (studentId, courseId) =>
    fetch(`${API_URL}/enrollments/student/${studentId}/course/${courseId}`, 
      { method: 'POST' }).then(r => r.json()),
  dropCourse: (studentId, courseId) =>
    fetch(`${API_URL}/enrollments/student/${studentId}/course/${courseId}`, 
      { method: 'DELETE' }),
  
  // Grades
  recordGrade: (grade) =>
    fetch(`${API_URL}/grades`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(grade)
    }).then(r => r.json()),
  getStudentGPA: (studentId) =>
    fetch(`${API_URL}/grades/student/${studentId}/gpa`).then(r => r.json()),
  
  // Sessions
  getSessions: () =>
    fetch(`${API_URL}/sessions`).then(r => r.json()),
  addSession: (session) =>
    fetch(`${API_URL}/sessions`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(session)
    }).then(r => r.json()),
};
```

### 4. Sample Pages

#### src/pages/Dashboard.js
```javascript
import React, { useEffect, useState } from 'react';
import { api } from '../services/api';

export default function Dashboard() {
  const [stats, setStats] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    api.getDashboardStats()
      .then(setStats)
      .finally(() => setLoading(false));
  }, []);

  if (loading) return <div>Loading...</div>;
  if (!stats) return <div>Error loading stats</div>;

  return (
    <div className="dashboard">
      <h1>Dashboard</h1>
      <div className="stats-grid">
        <div className="stat-card">
          <h3>{stats.totalStudents}</h3>
          <p>Students</p>
        </div>
        <div className="stat-card">
          <h3>{stats.totalCourses}</h3>
          <p>Courses</p>
        </div>
        <div className="stat-card">
          <h3>{stats.totalAdvisors}</h3>
          <p>Advisors</p>
        </div>
        <div className="stat-card">
          <h3>{stats.totalSessions}</h3>
          <p>Sessions</p>
        </div>
      </div>
    </div>
  );
}
```

#### src/pages/StudentList.js
```javascript
import React, { useEffect, useState } from 'react';
import { api } from '../services/api';

export default function StudentList() {
  const [students, setStudents] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    api.getStudents()
      .then(setStudents)
      .finally(() => setLoading(false));
  }, []);

  const handleDelete = (id) => {
    api.deleteStudent(id).then(() => {
      setStudents(students.filter(s => s.studentId !== id));
    });
  };

  if (loading) return <div>Loading...</div>;

  return (
    <div className="student-list">
      <h1>Students</h1>
      <table>
        <thead>
          <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Major</th>
            <th>GPA</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          {students.map(student => (
            <tr key={student.studentId}>
              <td>{student.studentId}</td>
              <td>{student.firstName} {student.lastName}</td>
              <td>{student.major}</td>
              <td>{student.gpa.toFixed(2)}</td>
              <td>
                <button onClick={() => handleDelete(student.studentId)}>
                  Delete
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}
```

### 5. Environment Files

**`.env.development`**:
```
REACT_APP_API_URL=http://localhost:8080/api
REACT_APP_APP_NAME=Advising System
```

**`.env.production`**:
```
REACT_APP_API_URL=https://your-railway-backend.com/api
REACT_APP_APP_NAME=Advising System
```

### 6. Main App Component (src/App.js)

```javascript
import React, { useState } from 'react';
import './App.css';
import Dashboard from './pages/Dashboard';
import StudentList from './pages/StudentList';

function App() {
  const [currentPage, setCurrentPage] = useState('dashboard');

  return (
    <div className="app">
      <nav>
        <button onClick={() => setCurrentPage('dashboard')}>Dashboard</button>
        <button onClick={() => setCurrentPage('students')}>Students</button>
        <button onClick={() => setCurrentPage('courses')}>Courses</button>
      </nav>
      
      <main>
        {currentPage === 'dashboard' && <Dashboard />}
        {currentPage === 'students' && <StudentList />}
      </main>
    </div>
  );
}

export default App;
```

### 7. Run Locally
```bash
npm start
```

### 8. Build for Production
```bash
npm run build
```

### 9. Deploy to Vercel
```bash
npm install -g vercel
vercel
```

---

## Next Steps

1. Create the API service
2. Build sample pages (Dashboard, StudentList, CourseList)
3. Add styling with CSS or Tailwind
4. Test with backend running locally
5. Deploy to Vercel

See `DEPLOY_TO_VERCEL.md` for complete deployment instructions.
