import React, { useEffect, useState } from 'react';
import { studentAPI, curriculumAPI } from '../services/api';
import '../styles/DegreeProgress.css';

export default function DegreeProgress() {
  const [students, setStudents] = useState([]);
  const [selectedStudent, setSelectedStudent] = useState('');
  const [progress, setProgress] = useState(null);
  const [remaining, setRemaining] = useState(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchStudents = async () => {
      try {
        const data = await studentAPI.getAll();
        setStudents(data);
      } catch (err) {
        setError(err.displayMessage);
      }
    };
    fetchStudents();
  }, []);

  const handleStudentSelect = async (studentId) => {
    setSelectedStudent(studentId);
    setProgress(null);
    setRemaining(null);
    if (!studentId) return;
    setLoading(true);
    setError(null);
    try {
      const [progressData, remainingData] = await Promise.all([
        curriculumAPI.getProgress(studentId),
        curriculumAPI.getRemaining(studentId),
      ]);
      setProgress(progressData);
      setRemaining(remainingData);
    } catch (err) {
      setError(err.displayMessage);
    } finally {
      setLoading(false);
    }
  };

  const categoryLabels = {
    UNIVERSITY_REQUIREMENTS: 'University Requirements',
    COLLEGE_REQUIREMENTS: 'College Requirements',
    MAJOR_CORE: 'Major Core Courses',
    MAJOR_ELECTIVE: 'Major Elective Courses',
    FREE_ELECTIVE: 'Free Elective Courses',
    CAPSTONE: 'Capstone Project',
    GENERAL_EDUCATION: 'General Education'
  };

  const getCompletionColor = (pct) => {
    if (pct >= 80) return 'var(--color-success)';
    if (pct >= 50) return 'var(--color-warning)';
    return 'var(--color-error)';
  };

  return (
    <div className="degree-progress">
      <h1>Degree Progress</h1>

      {error && <div className="error">{error}</div>}

      <div className="filter-bar">
        <select
          value={selectedStudent}
          onChange={(e) => handleStudentSelect(e.target.value)}
        >
          <option value="">Select a student...</option>
          {students.map(s => (
            <option key={s.studentId} value={s.studentId}>
              {s.firstName} {s.lastName} ({s.studentId} - {s.major})
            </option>
          ))}
        </select>
      </div>

      {loading && <div className="loading">Loading progress...</div>}

      {progress && (
        <div className="progress-overview">
          <div className="progress-summary">
            <div className="summary-card">
              <h3>Overall Progress</h3>
              <div className="progress-ring">
                <svg viewBox="0 0 36 36">
                  <path className="ring-bg"
                    d="M18 2.0845
                      a 15.9155 15.9155 0 0 1 0 31.831
                      a 15.9155 15.9155 0 0 1 0 -31.831"
                  />
                  <path className="ring-fill"
                    strokeDasharray={`${progress.completionPercentage?.toFixed(1) || 0}, 100`}
                    d="M18 2.0845
                      a 15.9155 15.9155 0 0 1 0 31.831
                      a 15.9155 15.9155 0 0 1 0 -31.831"
                  />
                  <text x="18" y="20.35" className="ring-text">
                    {progress.completionPercentage?.toFixed(0) || 0}%
                  </text>
                </svg>
              </div>
              <div className="summary-stats">
                <p><strong>{progress.totalCompletedCredits}</strong> / {progress.totalRequiredCredits} credits</p>
                <p className={progress.eligibleForGraduation ? 'eligible' : 'not-eligible'}>
                  {progress.eligibleForGraduation ? 'Eligible for Graduation' : 'Not yet eligible'}
                </p>
              </div>
            </div>

            {remaining && (
              <div className="categories-card">
                <h3>Requirements by Category</h3>
                <div className="categories-list">
                  {Object.entries(categoryLabels).map(([key, label]) => {
                    const completed = progress.completedCreditsByCategory?.[key] || 0;
                    const required = progress.requiredCreditsByCategory?.[key] || 0;
                    const remain = remaining[key] || 0;
                    const pct = required > 0 ? (completed / required) * 100 : 0;
                    return (
                      <div key={key} className="category-row">
                        <div className="category-info">
                          <span className="category-name">{label}</span>
                          <span className="category-credits">
                            {completed}/{required} cr
                            {remain > 0 && <span className="remaining"> ({remain} left)</span>}
                          </span>
                        </div>
                        <div className="category-bar-bg">
                          <div
                            className="category-bar-fill"
                            style={{
                              width: `${Math.min(pct, 100)}%`,
                              background: getCompletionColor(pct)
                            }}
                          />
                        </div>
                      </div>
                    );
                  })}
                </div>
              </div>
            )}
          </div>
        </div>
      )}

      {!loading && !progress && selectedStudent && (
        <div className="no-data">No curriculum data available for this student's major</div>
      )}
    </div>
  );
}
