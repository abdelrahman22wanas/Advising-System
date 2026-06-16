import React, { useEffect, useState } from 'react';
import { warningAPI, studentAPI } from '../services/api';
import '../styles/Warnings.css';

const SEVERITY_COLORS = {
  HIGH: { bg: 'rgba(239,68,68,0.15)', text: 'var(--color-error)', border: 'rgba(239,68,68,0.3)' },
  MEDIUM: { bg: 'rgba(245,158,11,0.15)', text: 'var(--color-warning)', border: 'rgba(245,158,11,0.3)' },
  LOW: { bg: 'rgba(59,130,246,0.15)', text: 'var(--color-info)', border: 'rgba(59,130,246,0.3)' },
};

export default function Warnings() {
  const [warnings, setWarnings] = useState([]);
  const [students, setStudents] = useState([]);
  const [loading, setLoading] = useState(true);
  const [generating, setGenerating] = useState(false);
  const [error, setError] = useState(null);
  const [searchTerm, setSearchTerm] = useState('');
  const [filterSeverity, setFilterSeverity] = useState('');

  useEffect(() => {
    const fetch = async () => {
      try {
        const [warnData, stuData] = await Promise.all([
          warningAPI.getAll(),
          studentAPI.getAll(),
        ]);
        setWarnings(warnData);
        setStudents(stuData);
      } catch (err) {
        setError(err.displayMessage);
      } finally {
        setLoading(false);
      }
    };
    fetch();
  }, []);

  const getStudentName = (studentId) => {
    const s = students.find(st => st.studentId === studentId);
    return s ? `${s.firstName} ${s.lastName}` : studentId;
  };

  const filtered = warnings.filter(w => {
    if (filterSeverity && w.severity !== filterSeverity) return false;
    if (!searchTerm) return true;
    const term = searchTerm.toLowerCase();
    return (
      w.warningId?.toLowerCase().includes(term) ||
      w.studentId?.toLowerCase().includes(term) ||
      getStudentName(w.studentId).toLowerCase().includes(term) ||
      w.type?.toLowerCase().includes(term) ||
      w.message?.toLowerCase().includes(term) ||
      w.severity?.toLowerCase().includes(term)
    );
  });

  const handleGenerate = async () => {
    setGenerating(true);
    setError(null);
    try {
      const data = await warningAPI.generate();
      setWarnings(data);
    } catch (err) {
      setError(err.displayMessage);
    } finally {
      setGenerating(false);
    }
  };

  if (loading) return <div className="loading">Loading warnings...</div>;

  return (
    <div className="warnings">
      <div className="warnings-header">
        <h1>Warnings</h1>
        <button
          className="btn-primary"
          onClick={handleGenerate}
          disabled={generating}
        >
          {generating ? 'Generating...' : 'Generate Warnings'}
        </button>
      </div>

      {error && <div className="error">{error}</div>}

      <div className="filter-bar">
        <input
          type="text"
          placeholder="Search any field..."
          value={searchTerm}
          onChange={(e) => setSearchTerm(e.target.value)}
        />
        <select
          value={filterSeverity}
          onChange={(e) => setFilterSeverity(e.target.value)}
        >
          <option value="">All Severities</option>
          <option value="HIGH">HIGH</option>
          <option value="MEDIUM">MEDIUM</option>
          <option value="LOW">LOW</option>
        </select>
      </div>

      {filtered.length > 0 && (
        <div className="warnings-summary">
          <span>Total: <strong>{filtered.length}</strong></span>
          <span>HIGH: <strong className="count-high">{warnings.filter(w => w.severity === 'HIGH').length}</strong></span>
          <span>MEDIUM: <strong className="count-medium">{warnings.filter(w => w.severity === 'MEDIUM').length}</strong></span>
          <span>LOW: <strong className="count-low">{warnings.filter(w => w.severity === 'LOW').length}</strong></span>
        </div>
      )}

      <div className="warnings-list">
        {filtered.map(w => {
          const colors = SEVERITY_COLORS[w.severity] || SEVERITY_COLORS.LOW;
          return (
            <div
              key={w.warningId}
              className="warning-card"
              style={{ borderLeftColor: colors.text }}
            >
              <div className="warning-card-top">
                <span
                  className="severity-badge"
                  style={{
                    background: colors.bg,
                    color: colors.text,
                    border: `1px solid ${colors.border}`,
                  }}
                >
                  {w.severity}
                </span>
                <span className="warning-type">{w.type?.replace(/_/g, ' ')}</span>
              </div>
              <p className="warning-message">{w.message}</p>
              <div className="warning-card-bottom">
                <span className="warning-student">
                  {getStudentName(w.studentId)} ({w.studentId})
                </span>
                <span className="warning-id">{w.warningId}</span>
              </div>
            </div>
          );
        })}
      </div>

      {filtered.length === 0 && !loading && (
        <div className="no-data">No warnings found</div>
      )}
    </div>
  );
}
