import React, { useEffect, useState } from 'react';
import { studentAPI } from '../services/api';
import Modal from '../components/Modal';
import '../styles/StudentList.css';

export default function StudentList() {
  const [students, setStudents] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [filterMajor, setFilterMajor] = useState('');
  const [searchTerm, setSearchTerm] = useState('');
  const [minGpa, setMinGpa] = useState('');
  const [maxGpa, setMaxGpa] = useState('');
  const [showModal, setShowModal] = useState(false);
  const [formData, setFormData] = useState({
    studentId: '', firstName: '', lastName: '', email: '',
    major: '', graduationYear: '', gpa: '', currentSemester: '1',
  });
  const [saving, setSaving] = useState(false);

  useEffect(() => {
    const fetchStudents = async () => {
      try {
        let data;
        if (filterMajor) {
          data = await studentAPI.getByMajor(filterMajor);
        } else if (minGpa && maxGpa) {
          data = await studentAPI.getByGPARange(minGpa, maxGpa);
        } else {
          data = await studentAPI.getAll();
        }
        setStudents(data);
      } catch (err) {
        setError(err.displayMessage);
      } finally {
        setLoading(false);
      }
    };
    fetchStudents();
  }, [filterMajor, minGpa, maxGpa]);

  const filtered = students.filter(s => {
    if (!searchTerm) return true;
    const term = searchTerm.toLowerCase();
    return (
      s.studentId?.toLowerCase().includes(term) ||
      s.firstName?.toLowerCase().includes(term) ||
      s.lastName?.toLowerCase().includes(term) ||
      s.email?.toLowerCase().includes(term) ||
      s.major?.toLowerCase().includes(term) ||
      String(s.graduationYear).includes(term) ||
      String(s.gpa).includes(term)
    );
  });

  const handleDelete = async (id) => {
    if (window.confirm('Delete this student?')) {
      try {
        await studentAPI.delete(id);
        setStudents(students.filter(s => s.studentId !== id));
      } catch (err) {
        setError(err.displayMessage);
      }
    }
  };

  const openCreate = () => {
    setFormData({
      studentId: '', firstName: '', lastName: '', email: '',
      major: '', graduationYear: '', gpa: '', currentSemester: '1',
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
        graduationYear: parseInt(formData.graduationYear, 10) || 0,
        gpa: parseFloat(formData.gpa) || 0,
        currentSemester: parseInt(formData.currentSemester, 10) || 1,
      };
      const created = await studentAPI.create(payload);
      setStudents([created, ...students]);
      setShowModal(false);
    } catch (err) {
      setError(err.displayMessage);
    } finally {
      setSaving(false);
    }
  };

  const gpaBadgeClass = (gpa) => {
    if (gpa >= 3.5) return 'gpa-badge gpa-excellent';
    if (gpa >= 2.5) return 'gpa-badge gpa-good';
    return 'gpa-badge gpa-average';
  };

  if (loading) return <div className="loading">Loading students...</div>;

  return (
    <div className="student-list">
      <div className="list-header">
        <h1>Students</h1>
        <button className="btn-primary" onClick={openCreate}>+ Add Student</button>
      </div>

      <div className="filter-bar">
        <input
          type="text"
          placeholder="Search any field..."
          value={searchTerm}
          onChange={(e) => setSearchTerm(e.target.value)}
        />
        <input
          type="text"
          placeholder="Filter by major..."
          value={filterMajor}
          onChange={(e) => setFilterMajor(e.target.value)}
        />
        <input
          type="number"
          placeholder="Min GPA"
          value={minGpa}
          onChange={(e) => setMinGpa(e.target.value)}
          step="0.1"
          min="0"
          max="4"
        />
        <input
          type="number"
          placeholder="Max GPA"
          value={maxGpa}
          onChange={(e) => setMaxGpa(e.target.value)}
          step="0.1"
          min="0"
          max="4"
        />
      </div>

      {error && <div className="error">{error}</div>}

      <div className="students-table-wrapper">
        <table className="students-table">
          <thead>
            <tr>
              <th>ID</th>
              <th>Name</th>
              <th>Major</th>
              <th>GPA</th>
              <th>Graduation Year</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            {filtered.map(student => (
              <tr key={student.studentId}>
                <td>{student.studentId}</td>
                <td>{student.firstName} {student.lastName}</td>
                <td>{student.major}</td>
                <td>
                  <span className={gpaBadgeClass(student.gpa || 0)}>
                    {student.gpa?.toFixed(2) || 'N/A'}
                  </span>
                </td>
                <td>{student.graduationYear}</td>
                <td>
                  <button
                    className="btn-delete"
                    onClick={() => handleDelete(student.studentId)}
                  >
                    Delete
                  </button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>

      {filtered.length === 0 && (
        <div className="no-data">No students found</div>
      )}

      <Modal isOpen={showModal} onClose={() => setShowModal(false)} title="Add Student">
        <form className="modal-form" onSubmit={handleCreate}>
          <div className="form-row">
            <div className="form-group">
              <label>Student ID</label>
              <input name="studentId" value={formData.studentId} onChange={handleChange} required />
            </div>
            <div className="form-group">
              <label>Graduation Year</label>
              <input name="graduationYear" type="number" value={formData.graduationYear} onChange={handleChange} required />
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
              <label>Major</label>
              <input name="major" value={formData.major} onChange={handleChange} required />
            </div>
            <div className="form-group">
              <label>GPA</label>
              <input name="gpa" type="number" step="0.1" min="0" max="4" value={formData.gpa} onChange={handleChange} />
            </div>
          </div>
          <div className="form-group">
            <label>Current Semester</label>
            <input name="currentSemester" type="number" min="1" value={formData.currentSemester} onChange={handleChange} />
          </div>
          <div className="form-actions">
            <button type="button" className="btn-cancel" onClick={() => setShowModal(false)}>Cancel</button>
            <button type="submit" className="btn-primary" disabled={saving}>{saving ? 'Saving...' : 'Save'}</button>
          </div>
        </form>
      </Modal>
    </div>
  );
}
