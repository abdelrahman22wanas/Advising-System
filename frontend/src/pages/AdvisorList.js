import React, { useEffect, useState } from 'react';
import { advisorAPI, studentAPI } from '../services/api';
import Modal from '../components/Modal';
import '../styles/AdvisorList.css';

export default function AdvisorList() {
  const [advisors, setAdvisors] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [searchTerm, setSearchTerm] = useState('');
  const [showModal, setShowModal] = useState(false);
  const [showAssignModal, setShowAssignModal] = useState(false);
  const [selectedAdvisor, setSelectedAdvisor] = useState(null);
  const [students, setStudents] = useState([]);
  const [selectedStudentId, setSelectedStudentId] = useState('');
  const [assigning, setAssigning] = useState(false);
  const [formData, setFormData] = useState({
    advisorId: '', firstName: '', lastName: '', email: '',
    office: '', phoneNumber: '', specializations: '', maxStudents: '25',
  });
  const [saving, setSaving] = useState(false);

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

  const handleDelete = async (id) => {
    if (window.confirm('Delete this advisor?')) {
      try {
        await advisorAPI.delete(id);
        setAdvisors(advisors.filter(a => a.advisorId !== id));
      } catch (err) {
        setError(err.displayMessage);
      }
    }
  };

  const openCreate = () => {
    setFormData({
      advisorId: '', firstName: '', lastName: '', email: '',
      office: '', phoneNumber: '', specializations: '', maxStudents: '25',
    });
    setShowModal(true);
  };

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleCreate = async (e) => {
    e.preventDefault();
    setSaving(true);
    setError(null);
    try {
      const payload = {
        ...formData,
        maxStudents: parseInt(formData.maxStudents, 10) || 25,
        specializations: formData.specializations
          ? formData.specializations.split(',').map(s => s.trim()).filter(Boolean)
          : [],
      };
      const created = await advisorAPI.create(payload);
      setAdvisors([created, ...advisors]);
      setShowModal(false);
    } catch (err) {
      setError(err.displayMessage);
    } finally {
      setSaving(false);
    }
  };

  const openAssign = async (advisor) => {
    setSelectedAdvisor(advisor);
    setSelectedStudentId('');
    setError(null);
    const ids = advisor.assignedStudentIds || [];
    try {
      const allStudents = await studentAPI.getAll();
      const unassigned = allStudents.filter(s => !ids.includes(s.studentId));
      setStudents(unassigned);
      setShowAssignModal(true);
    } catch (err) {
      setError(err.displayMessage);
    }
  };

  const handleAssign = async (e) => {
    e.preventDefault();
    if (!selectedStudentId) return;
    setAssigning(true);
    setError(null);
    try {
      await advisorAPI.assignStudent(selectedAdvisor.advisorId, selectedStudentId);
      const updated = await advisorAPI.getAll();
      setAdvisors(updated);
      setShowAssignModal(false);
      setSelectedAdvisor(null);
    } catch (err) {
      setError(err.displayMessage);
    } finally {
      setAssigning(false);
    }
  };

  if (loading) return <div className="loading">Loading advisors...</div>;

  return (
    <div className="advisor-list">
      <div className="list-header">
        <h1>Advisors</h1>
        <button className="btn-primary" onClick={openCreate}>+ Add Advisor</button>
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

      <div className="advisors-grid">
        {filtered.map(advisor => (
          <div key={advisor.advisorId} className="advisor-card">
            <h3>{advisor.firstName} {advisor.lastName}</h3>
            <p><strong>ID:</strong> {advisor.advisorId}</p>
            <p><strong>Email:</strong> {advisor.email}</p>
            <p><strong>Office:</strong> {advisor.office || 'N/A'}</p>
            <p><strong>Phone:</strong> {advisor.phoneNumber || 'N/A'}</p>
            <p><strong>Specialization:</strong> {advisor.specializations?.join(', ') || 'N/A'}</p>
            <p><strong>Students:</strong> {advisor.assignedStudentIds?.length || 0}/{advisor.maxStudents || 25}</p>
            <div className="advisor-card-actions">
              <button
                className="btn-assign"
                onClick={() => openAssign(advisor)}
              >
                Assign Student
              </button>
              <button
                className="btn-delete"
                onClick={() => handleDelete(advisor.advisorId)}
              >
                Delete
              </button>
            </div>
          </div>
        ))}
      </div>

      {filtered.length === 0 && (
        <div className="no-data">No advisors found</div>
      )}

      <Modal isOpen={showModal} onClose={() => setShowModal(false)} title="Add Advisor">
        <form className="modal-form" onSubmit={handleCreate}>
          <div className="form-row">
            <div className="form-group">
              <label>Advisor ID</label>
              <input name="advisorId" value={formData.advisorId} onChange={handleChange} required />
            </div>
            <div className="form-group">
              <label>Max Students</label>
              <input name="maxStudents" type="number" min="1" value={formData.maxStudents} onChange={handleChange} />
            </div>
          </div>
          <div className="form-row">
            <div className="form-group">
              <label>First Name</label>
              <input name="firstName" value={formData.firstName} onChange={handleChange} required />
            </div>
            <div className="form-group">
              <label>Last Name</label>
              <input name="lastName" value={formData.lastName} onChange={handleChange} required />
            </div>
          </div>
          <div className="form-group">
            <label>Email</label>
            <input name="email" type="email" value={formData.email} onChange={handleChange} required />
          </div>
          <div className="form-row">
            <div className="form-group">
              <label>Office</label>
              <input name="office" value={formData.office} onChange={handleChange} />
            </div>
            <div className="form-group">
              <label>Phone Number</label>
              <input name="phoneNumber" value={formData.phoneNumber} onChange={handleChange} />
            </div>
          </div>
          <div className="form-group">
            <label>Specializations (comma-separated)</label>
            <input name="specializations" value={formData.specializations} onChange={handleChange} placeholder="e.g. Data Science, AI, Networks" />
          </div>
          <div className="form-actions">
            <button type="button" className="btn-cancel" onClick={() => setShowModal(false)}>Cancel</button>
            <button type="submit" className="btn-primary" disabled={saving}>{saving ? 'Saving...' : 'Save'}</button>
          </div>
        </form>
      </Modal>

      <Modal isOpen={showAssignModal} onClose={() => setShowAssignModal(false)} title={`Assign Student to ${selectedAdvisor?.firstName || ''} ${selectedAdvisor?.lastName || ''}`}>
        <form className="modal-form" onSubmit={handleAssign}>
          <div className="form-group">
            <label>Select Student</label>
            <select value={selectedStudentId} onChange={(e) => setSelectedStudentId(e.target.value)} required>
              <option value="">Choose a student...</option>
              {students.map(s => (
                <option key={s.studentId} value={s.studentId}>
                  {s.firstName} {s.lastName} ({s.studentId}) - {s.major}
                </option>
              ))}
            </select>
            {students.length === 0 && <p className="form-hint">All students are already assigned to this advisor.</p>}
          </div>
          <div className="form-actions">
            <button type="button" className="btn-cancel" onClick={() => setShowAssignModal(false)}>Cancel</button>
            <button type="submit" className="btn-primary" disabled={assigning || !selectedStudentId}>
              {assigning ? 'Assigning...' : 'Assign'}
            </button>
          </div>
        </form>
      </Modal>
    </div>
  );
}
