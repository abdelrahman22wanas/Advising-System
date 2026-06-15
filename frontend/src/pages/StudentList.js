import React, { useEffect, useState } from 'react';
import { studentAPI } from '../services/api';
import '../styles/StudentList.css';

export default function StudentList() {
  const [students, setStudents] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [filterMajor, setFilterMajor] = useState('');
  const [searchTerm, setSearchTerm] = useState('');

  useEffect(() => {
    const fetchStudents = async () => {
      try {
        let data;
        if (filterMajor) {
          data = await studentAPI.getByMajor(filterMajor);
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
  }, [filterMajor]);

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

  if (loading) return <div className="loading">Loading students...</div>;

  return (
    <div className="student-list">
      <h1>👨‍🎓 Students</h1>
      
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
                <td>{student.gpa?.toFixed(2) || 'N/A'}</td>
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
    </div>
  );
}
