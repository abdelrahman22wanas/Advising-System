import React, { useEffect, useState } from 'react';
import { advisorAPI } from '../services/api';
import '../styles/AdvisorList.css';

export default function AdvisorList() {
  const [advisors, setAdvisors] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchAdvisors = async () => {
      try {
        const data = await advisorAPI.getAll();
        setAdvisors(data);
      } catch (err) {
        setError(err.message);
      } finally {
        setLoading(false);
      }
    };
    fetchAdvisors();
  }, []);

  if (loading) return <div className="loading">Loading advisors...</div>;

  return (
    <div className="advisor-list">
      <h1>👨‍🏫 Advisors</h1>

      {error && <div className="error">{error}</div>}

      <div className="advisors-grid">
        {advisors.map(advisor => (
          <div key={advisor.advisorId} className="advisor-card">
            <h3>{advisor.firstName} {advisor.lastName}</h3>
            <p><strong>ID:</strong> {advisor.advisorId}</p>
            <p><strong>Office:</strong> {advisor.office || 'N/A'}</p>
            <p><strong>Phone:</strong> {advisor.phone || 'N/A'}</p>
            <p><strong>Specialization:</strong> {advisor.specialization || 'N/A'}</p>
            <p><strong>Students:</strong> {advisor.studentIds?.length || 0}</p>
          </div>
        ))}
      </div>

      {advisors.length === 0 && (
        <div className="no-data">No advisors found</div>
      )}
    </div>
  );
}
