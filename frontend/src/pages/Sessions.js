import React, { useEffect, useState } from 'react';
import { sessionAPI, studentAPI, advisorAPI } from '../services/api';
import Modal from '../components/Modal';
import '../styles/Sessions.css';

export default function Sessions() {
  const [sessions, setSessions] = useState([]);
  const [students, setStudents] = useState([]);
  const [advisors, setAdvisors] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [searchTerm, setSearchTerm] = useState('');
  const [showCreate, setShowCreate] = useState(false);
  const [form, setForm] = useState({
    studentId: '', advisorId: '', sessionDate: '', topic: '', notes: ''
  });

  useEffect(() => {
    const fetchData = async () => {
      try {
        const [sessionData, studentData, advisorData] = await Promise.all([
          sessionAPI.getAll(),
          studentAPI.getAll(),
          advisorAPI.getAll(),
        ]);
        setSessions(sessionData);
        setStudents(studentData);
        setAdvisors(advisorData);
      } catch (err) {
        setError(err.displayMessage);
      } finally {
        setLoading(false);
      }
    };
    fetchData();
  }, []);

  const handleCreate = async () => {
    try {
      const session = {
        sessionId: 'SESSION' + Date.now(),
        studentId: form.studentId,
        advisorId: form.advisorId,
        sessionDate: form.sessionDate,
        topic: form.topic,
        notes: form.notes,
        status: 'SCHEDULED'
      };
      await sessionAPI.create(session);
      const data = await sessionAPI.getAll();
      setSessions(data);
      setShowCreate(false);
      setForm({ studentId: '', advisorId: '', sessionDate: '', topic: '', notes: '' });
    } catch (err) {
      setError(err.displayMessage);
    }
  };

  const handleCancel = async (id) => {
    try {
      await sessionAPI.delete(id);
      setSessions(sessions.filter(s => s.sessionId !== id));
    } catch (err) {
      setError(err.displayMessage);
    }
  };

  const handleStatusUpdate = async (id, status) => {
    try {
      await sessionAPI.updateStatus(id, status);
      const data = await sessionAPI.getAll();
      setSessions(data);
    } catch (err) {
      setError(err.displayMessage);
    }
  };

  const getStudentName = (id) => {
    const s = students.find(st => st.studentId === id);
    return s ? `${s.firstName} ${s.lastName}` : id;
  };

  const getAdvisorName = (id) => {
    const a = advisors.find(ad => ad.advisorId === id);
    return a ? `${a.firstName} ${a.lastName}` : id;
  };

  const formatDate = (dateStr) => {
    try {
      return new Date(dateStr).toLocaleString();
    } catch {
      return dateStr;
    }
  };

  const filtered = sessions.filter(s => {
    if (!searchTerm) return true;
    const term = searchTerm.toLowerCase();
    return (
      s.sessionId?.toLowerCase().includes(term) ||
      getStudentName(s.studentId).toLowerCase().includes(term) ||
      getAdvisorName(s.advisorId).toLowerCase().includes(term) ||
      s.topic?.toLowerCase().includes(term) ||
      s.status?.toLowerCase().includes(term) ||
      (s.sessionDate || s.formattedDate || '').toLowerCase().includes(term)
    );
  });

  if (loading) return <div className="loading">Loading sessions...</div>;

  return (
    <div className="sessions">
      <div className="sessions-header">
        <h1>Advising Sessions</h1>
        <button className="btn-primary" onClick={() => setShowCreate(true)}>
          + New Session
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
      </div>

      <div className="sessions-table-wrapper">
        <table className="sessions-table">
          <thead>
            <tr>
              <th>ID</th>
              <th>Student</th>
              <th>Advisor</th>
              <th>Date</th>
              <th>Topic</th>
              <th>Status</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            {filtered.map(s => (
              <tr key={s.sessionId}>
                <td className="session-id">{s.sessionId}</td>
                <td>{getStudentName(s.studentId)}</td>
                <td>{getAdvisorName(s.advisorId)}</td>
                <td>{s.sessionDate ? formatDate(s.sessionDate) : s.formattedDate}</td>
                <td>{s.topic}</td>
                <td>
                  <span className={`status-badge status-${s.status?.toLowerCase()}`}>
                    {s.status}
                  </span>
                </td>
                <td className="session-actions">
                  {s.status === 'SCHEDULED' && (
                    <>
                      <button className="btn-complete" onClick={() => handleStatusUpdate(s.sessionId, 'COMPLETED')}>
                        Complete
                      </button>
                      <button className="btn-delete" onClick={() => handleCancel(s.sessionId)}>
                        Cancel
                      </button>
                    </>
                  )}
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>

      {filtered.length === 0 && (
        <div className="no-data">No sessions found</div>
      )}

      <Modal isOpen={showCreate} onClose={() => setShowCreate(false)} title="New Advising Session">
        <div className="session-form">
          <label>
            Student
            <select value={form.studentId} onChange={(e) => setForm({ ...form, studentId: e.target.value })}>
              <option value="">Select student...</option>
              {students.map(s => (
                <option key={s.studentId} value={s.studentId}>
                  {s.firstName} {s.lastName} ({s.studentId})
                </option>
              ))}
            </select>
          </label>
          <label>
            Advisor
            <select value={form.advisorId} onChange={(e) => setForm({ ...form, advisorId: e.target.value })}>
              <option value="">Select advisor...</option>
              {advisors.map(a => (
                <option key={a.advisorId} value={a.advisorId}>
                  {a.firstName} {a.lastName} ({a.advisorId})
                </option>
              ))}
            </select>
          </label>
          <label>
            Date & Time
            <input
              type="datetime-local"
              value={form.sessionDate}
              onChange={(e) => setForm({ ...form, sessionDate: e.target.value })}
            />
          </label>
          <label>
            Topic
            <input
              type="text"
              placeholder="Session topic"
              value={form.topic}
              onChange={(e) => setForm({ ...form, topic: e.target.value })}
            />
          </label>
          <label>
            Notes
            <textarea
              placeholder="Optional notes"
              value={form.notes}
              onChange={(e) => setForm({ ...form, notes: e.target.value })}
              rows={3}
            />
          </label>
          <button className="btn-primary" onClick={handleCreate}>
            Create Session
          </button>
        </div>
      </Modal>
    </div>
  );
}
