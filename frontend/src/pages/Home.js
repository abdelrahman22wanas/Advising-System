import React from 'react';
import { Link } from 'react-router-dom';
import '../styles/Home.css';

const pages = [
  { label: 'Students', icon: '\uD83D\uDC68\u200D\uD83C\uDF93', path: '/students', color: '#6c63ff' },
  { label: 'Courses', icon: '\uD83D\uDCDA', path: '/courses', color: '#00d4ff' },
  { label: 'Advisors', icon: '\uD83D\uDC68\u200D\uD83C\uDFEB', path: '/advisors', color: '#a855f7' },
  { label: 'Enrollments', icon: '\uD83D\uDCDD', path: '/enrollments', color: '#14b8a6' },
  { label: 'Sessions', icon: '\uD83D\uDCAC', path: '/sessions', color: '#f59e0b' },
  { label: 'Grades', icon: '\uD83C\uDF93', path: '/grades', color: '#3b82f6' },
  { label: 'Degree Progress', icon: '\uD83D\uDCCA', path: '/degree-progress', color: '#10b981' },
  { label: 'Dashboard', icon: '\uD83D\uDCC8', path: '/dashboard', color: '#ef4444' },
];

export default function Home() {
  return (
    <div className="home">
      <div className="home-hero">
        <svg className="home-logo" viewBox="0 0 24 24" width="80" height="80" fill="none" stroke="currentColor" strokeWidth="1.5" strokeLinecap="round" strokeLinejoin="round">
          <path d="M22 10v6M2 10l10-5 10 5-10 5z" />
          <path d="M6 12v5c3 3 9 3 12 0v-5" />
        </svg>
        <h1 className="home-title">Academic Advising System</h1>
        <p className="home-tagline">
          A comprehensive platform for managing student academic progress,
          course enrollment, and advisor sessions.
        </p>
      </div>

      <div className="home-nav">
        {pages.map(p => (
          <Link key={p.path} to={p.path} className="home-nav-card" style={{ '--card-accent': p.color }}>
            <span className="home-nav-icon">{p.icon}</span>
            <span className="home-nav-label">{p.label}</span>
          </Link>
        ))}
      </div>
    </div>
  );
}
