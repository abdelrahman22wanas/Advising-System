import React from 'react';
import { BrowserRouter as Router, Routes, Route, NavLink, useLocation } from 'react-router-dom';
import Home from './pages/Home';
import Dashboard from './pages/Dashboard';
import StudentList from './pages/StudentList';
import CourseList from './pages/CourseList';
import AdvisorList from './pages/AdvisorList';
import Enrollments from './pages/Enrollments';
import Sessions from './pages/Sessions';
import Grades from './pages/Grades';
import DegreeProgress from './pages/DegreeProgress';
import Warnings from './pages/Warnings';
import ThemeToggle from './components/ThemeToggle';
import WarningBadge from './components/WarningBadge';
import './styles/App.css';
import './styles/Modal.css';
import './styles/Home.css';

function AppContent() {
  const location = useLocation();
  const isHome = location.pathname === '/';

  return (
    <div className="app">
      <header className="navbar">
        <div className="navbar-content">
          <h1>Academic Advising System</h1>
          <div className="navbar-spacer" />
          {!isHome && <WarningBadge />}
          <ThemeToggle />
        </div>
      </header>

      <div className="main-container">
        {!isHome && (
          <aside className="sidebar">
            <NavLink to="/" className="sidebar-brand" end>
              <svg className="sidebar-logo" viewBox="0 0 24 24" width="32" height="32" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round">
                <path d="M22 10v6M2 10l10-5 10 5-10 5z" />
                <path d="M6 12v5c3 3 9 3 12 0v-5" />
                <path d="M2 10l10-5 10 5" />
              </svg>
              <span className="sidebar-brand-text">
                <span className="sidebar-brand-title">Academic Advising</span>
                <span className="sidebar-brand-subtitle">System</span>
              </span>
            </NavLink>
            <div className="sidebar-divider" />
            <nav className="nav-menu">
              <ul>
                <li><NavLink to="/dashboard">Dashboard</NavLink></li>
                <li><NavLink to="/students">Students</NavLink></li>
                <li><NavLink to="/courses">Courses</NavLink></li>
                <li><NavLink to="/advisors">Advisors</NavLink></li>
                <li><NavLink to="/enrollments">Enrollments</NavLink></li>
                <li><NavLink to="/sessions">Sessions</NavLink></li>
                <li><NavLink to="/grades">Grades</NavLink></li>
                <li><NavLink to="/degree-progress">Degree Progress</NavLink></li>
                <li><NavLink to="/warnings">Warnings</NavLink></li>
              </ul>
            </nav>
          </aside>
        )}

        <main className={`content ${isHome ? 'content-home' : ''}`}>
          <Routes>
            <Route path="/" element={<Home />} />
            <Route path="/dashboard" element={<Dashboard />} />
            <Route path="/students" element={<StudentList />} />
            <Route path="/courses" element={<CourseList />} />
            <Route path="/advisors" element={<AdvisorList />} />
            <Route path="/enrollments" element={<Enrollments />} />
            <Route path="/sessions" element={<Sessions />} />
            <Route path="/grades" element={<Grades />} />
            <Route path="/degree-progress" element={<DegreeProgress />} />
            <Route path="/warnings" element={<Warnings />} />
          </Routes>
        </main>
      </div>

      <footer className="footer">
        <p>&copy; 2024 Academic Advising System</p>
      </footer>
    </div>
  );
}

function App() {
  return (
    <Router>
      <AppContent />
    </Router>
  );
}

export default App;
