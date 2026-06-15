import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { dashboardAPI, gradeAPI, enrollmentAPI, warningAPI } from '../services/api';
import '../styles/Dashboard.css';

const statCards = [
  { key: 'totalStudents', label: 'Students', icon: '\uD83C\uDF93', path: '/students' },
  { key: 'totalCourses', label: 'Courses', icon: '\uD83D\uDCDA', path: '/courses' },
  { key: 'totalAdvisors', label: 'Advisors', icon: '\uD83C\uDFEB', path: '/advisors' },
  { key: 'totalSessions', label: 'Sessions', icon: '\uD83D\uDCAC', path: '/sessions' },
  { key: 'totalEnrollments', label: 'Enrollments', icon: '\uD83D\uDCDD', path: '/enrollments' },
  { key: 'totalGrades', label: 'Grades', icon: '\uD83C\uDF93', path: '/grades' },
];

const infoCards = [
  { key: 'averageStudentGPA', label: 'Average GPA', format: (v) => v != null ? Number(v).toFixed(2) : 'N/A' },
  { key: 'sessionsCompleted', label: 'Sessions Completed', format: (v) => v ?? 0 },
  { key: 'sessionsScheduled', label: 'Sessions Scheduled', format: (v) => v ?? 0 },
  { key: 'totalWarnings', label: 'Active Warnings', format: (v) => v ?? 0 },
];

const GRADE_COLORS = {
  'A': { bg: 'rgba(16,185,129,0.15)', text: 'var(--color-success)' },
  'B': { bg: 'rgba(59,130,246,0.15)', text: 'var(--color-info)' },
  'C': { bg: 'rgba(245,158,11,0.15)', text: 'var(--color-warning)' },
  'D': { bg: 'rgba(239,68,68,0.12)', text: 'var(--color-error)' },
  'F': { bg: 'rgba(239,68,68,0.2)', text: 'var(--color-error)' },
};

export default function Dashboard() {
  const [stats, setStats] = useState(null);
  const [gradeCounts, setGradeCounts] = useState({});
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const [statsData, gradeData, enrollmentData, warningData] = await Promise.all([
          dashboardAPI.getStats(),
          gradeAPI.getAll(),
          enrollmentAPI.getAll(),
          warningAPI.getAll(),
        ]);

        statsData.totalEnrollments = enrollmentData?.length || 0;
        statsData.totalGrades = gradeData?.length || 0;
        statsData.totalWarnings = warningData?.length || 0;

        setStats(statsData);

        const grades = {};
        gradeData.forEach(g => {
          const letter = g.letterGrade?.toUpperCase() || 'Unknown';
          grades[letter] = (grades[letter] || 0) + 1;
        });
        setGradeCounts(grades);
      } catch (err) {
        setError(err.displayMessage);
      } finally {
        setLoading(false);
      }
    };
    fetchData();
  }, []);

  if (loading) return <div className="dashboard-loading">Loading...</div>;
  if (error) return <div className="dashboard-error">Error: {error}</div>;
  if (!stats) return <div className="dashboard-error">No data</div>;

  return (
    <div className="dashboard">
      <h1>Dashboard</h1>

      <div className="stats-grid">
        {statCards.map(card => {
          const value = stats[card.key];
          return (
            <Link key={card.key} to={card.path} className="stat-card">
              <h3>{card.icon}</h3>
              <p className="stat-number">{value != null ? value : '\u2014'}</p>
              <p className="stat-label">{card.label}</p>
            </Link>
          );
        })}
      </div>

      <div className="additional-stats">
        {infoCards.map(card => {
          const value = stats[card.key];
          return (
            <div key={card.key} className="info-card">
              <h4>{card.label}</h4>
              <p>{card.format ? card.format(value) : value ?? '\u2014'}</p>
            </div>
          );
        })}
      </div>

      <div className="grade-section">
        <h2>Grade Distribution</h2>
        <div className="grade-grid">
          {Object.entries(gradeCounts).length === 0 && (
            <p className="grade-empty">No grades recorded</p>
          )}
          {Object.entries(GRADE_COLORS).map(([letter, colors]) => {
            const count = gradeCounts[letter];
            return (
              <div key={letter} className="grade-item" style={{ background: colors.bg }}>
                <span className="grade-letter-big" style={{ color: colors.text }}>{letter}</span>
                <span className="grade-count" style={{ color: colors.text }}>{count || 0}</span>
              </div>
            );
          })}
        </div>
      </div>
    </div>
  );
}
