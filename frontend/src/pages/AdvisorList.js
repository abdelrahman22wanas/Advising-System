import React, { useEffect, useState } from 'react';
import { advisorAPI } from '../services/api';
import '../styles/AdvisorList.css';

export default function AdvisorList() {
  const [advisors, setAdvisors] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [searchTerm, setSearchTerm] = useState('');

  useEffect(() => {
    const fetchAdvisors = async () => {
      try {
        const data = await advisorAPI.getAll();
        setAdvisors(data);
      } catch (err) {
        setError(err.displayMessage);
      } finally {
        setLoading(false);
      }
    };
    fetchAdvisors();
  }, []);

  const filtered = advisors.filter(a => {
    if (!searchTerm) return true;
    const term = searchTerm.toLowerCase();
    return (
      a.advisorId?.toLowerCase().includes(term) ||
      a.firstName?.toLowerCase().includes(term) ||
      a.lastName?.toLowerCase().includes(term) ||
      a.email?.toLowerCase().includes(term) ||
      a.office?.toLowerCase().includes(term) ||
      a.phoneNumber?.toLowerCase().includes(term) ||
      a.specializations?.some(s => s.toLowerCase().includes(term))
    );
  });

  if (loading) return <div className="loading">Loading advisors...</div>;

  return (
    <div className="advisor-list">
      <h1>👨‍🏫 Advisors</h1>

      {error && <div className="error">{error}</div>}

      <div className="filter-bar">
        <input
          type="text"
          placeholder="Search any field..."
          value={searchTerm}
          onChange={(e) => setSearchTerm(e.target.value)}
        />
      </div>

      <div className="advisors-grid">
        {filtered.map(advisor => (
          <div key={advisor.advisorId} className="advisor-card">
            <h3>{advisor.firstName} {advisor.lastName}</h3>
            <p><strong>ID:</strong> {advisor.advisorId}</p>
            <p><strong>Office:</strong> {advisor.office || 'N/A'}</p>
            <p><strong>Phone:</strong> {advisor.phoneNumber || 'N/A'}</p>
            <p><strong>Specialization:</strong> {advisor.specializations?.join(', ') || 'N/A'}</p>
            <p><strong>Students:</strong> {advisor.assignedStudentIds?.length || 0}</p>
          </div>
        ))}
      </div>

      {filtered.length === 0 && (
        <div className="no-data">No advisors found</div>
      )}
    </div>
  );
}
