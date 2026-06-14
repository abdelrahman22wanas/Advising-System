import React, { useEffect, useState } from 'react';
import { dashboardAPI } from '../services/api';
import '../styles/Dashboard.css';

export default function Dashboard() {
  const [stats, setStats] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchStats = async () => {
      try {
        const data = await dashboardAPI.getStats();
        setStats(data);
      } catch (err) {
        setError(err.message);
      } finally {
        setLoading(false);
      }
    };
    fetchStats();
  }, []);

  if (loading) return <div className="dashboard-loading">Loading...</div>;
  if (error) return <div className="dashboard-error">Error: {error}</div>;
  if (!stats) return <div className="dashboard-error">No data</div>;

  return (
    <div className="dashboard">
      <h1>📊 Dashboard</h1>
      <div className="stats-grid">
        <div className="stat-card">
          <h3>👨‍🎓</h3>
          <p className="stat-number">{stats.totalStudents}</p>
          <p className="stat-label">Students</p>
        </div>
        <div className="stat-card">
          <h3>📚</h3>
          <p className="stat-number">{stats.totalCourses}</p>
          <p className="stat-label">Courses</p>
        </div>
        <div className="stat-card">
          <h3>👨‍🏫</h3>
          <p className="stat-number">{stats.totalAdvisors}</p>
          <p className="stat-label">Advisors</p>
        </div>
        <div className="stat-card">
          <h3>💬</h3>
          <p className="stat-number">{stats.totalSessions}</p>
          <p className="stat-label">Sessions</p>
        </div>
      </div>
      
      <div className="additional-stats">
        <div className="info-card">
          <h4>Average Student GPA</h4>
          <p>{stats.averageStudentGPA?.toFixed(2) || 'N/A'}</p>
        </div>
        <div className="info-card">
          <h4>Sessions Completed</h4>
          <p>{stats.sessionsCompleted || 0}</p>
        </div>
        <div className="info-card">
          <h4>Sessions Scheduled</h4>
          <p>{stats.sessionsScheduled || 0}</p>
        </div>
      </div>
    </div>
  );
}
