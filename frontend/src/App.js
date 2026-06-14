import React, { useState } from 'react';
import { BrowserRouter as Router, Routes, Route, Link } from 'react-router-dom';
import Dashboard from './pages/Dashboard';
import StudentList from './pages/StudentList';
import CourseList from './pages/CourseList';
import AdvisorList from './pages/AdvisorList';
import './styles/App.css';

function App() {
  const [sidebarOpen, setSidebarOpen] = useState(true);

  return (
    <Router>
      <div className="app">
        <header className="navbar">
          <div className="navbar-content">
            <button 
              className="menu-toggle"
              onClick={() => setSidebarOpen(!sidebarOpen)}
            >
              ☰
            </button>
            <h1>🎓 Academic Advising System</h1>
          </div>
        </header>

        <div className="main-container">
          <aside className={`sidebar ${sidebarOpen ? 'open' : 'closed'}`}>
            <nav className="nav-menu">
              <h2>Menu</h2>
              <ul>
                <li>
                  <Link to="/" onClick={() => setSidebarOpen(false)}>
                    📊 Dashboard
                  </Link>
                </li>
                <li>
                  <Link to="/students" onClick={() => setSidebarOpen(false)}>
                    👨‍🎓 Students
                  </Link>
                </li>
                <li>
                  <Link to="/courses" onClick={() => setSidebarOpen(false)}>
                    📚 Courses
                  </Link>
                </li>
                <li>
                  <Link to="/advisors" onClick={() => setSidebarOpen(false)}>
                    👨‍🏫 Advisors
                  </Link>
                </li>
              </ul>
            </nav>
          </aside>

          <main className="content">
            <Routes>
              <Route path="/" element={<Dashboard />} />
              <Route path="/students" element={<StudentList />} />
              <Route path="/courses" element={<CourseList />} />
              <Route path="/advisors" element={<AdvisorList />} />
            </Routes>
          </main>
        </div>

        <footer className="footer">
          <p>Academic Advising System | Powered by Spring Boot & React</p>
        </footer>
      </div>
    </Router>
  );
}

export default App;
