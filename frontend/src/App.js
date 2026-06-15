import React, { useState } from 'react';
import { BrowserRouter as Router, Routes, Route, NavLink } from 'react-router-dom';
import Dashboard from './pages/Dashboard';
import StudentList from './pages/StudentList';
import CourseList from './pages/CourseList';
import AdvisorList from './pages/AdvisorList';
import ThemeToggle from './components/ThemeToggle';
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
            <h1>Academic Advising System</h1>
            <div className="navbar-spacer" />
            <ThemeToggle />
          </div>
        </header>

        <div className="main-container">
          <aside className={`sidebar ${sidebarOpen ? '' : 'closed'}`}>
            <h2>Menu</h2>
            <nav className="nav-menu">
              <ul>
                <li><NavLink to="/" end>Dashboard</NavLink></li>
                <li><NavLink to="/students">Students</NavLink></li>
                <li><NavLink to="/courses">Courses</NavLink></li>
                <li><NavLink to="/advisors">Advisors</NavLink></li>
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
          <p>&copy; 2024 Academic Advising System</p>
        </footer>
      </div>
    </Router>
  );
}

export default App;
