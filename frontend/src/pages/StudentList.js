import React, { useEffect, useState } from 'react';
import { studentAPI } from '../services/api';
import '../styles/StudentList.css';

export default function StudentList() {
  const [students, setStudents] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [filterMajor, setFilterMajor] = useState('');

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
        setError(err.message);
      } finally {
        setLoading(false);
      }
    };
    fetchStudents();
  }, [filterMajor]);

  const handleDelete = async (id) => {
    if (window.confirm('Delete this student?')) {
      try {
        await studentAPI.delete(id);
        setStudents(students.filter(s => s.studentId !== id));
      } catch (err) {
        setError(err.message);
      }
    }
  };

  if (loading) return <div className="loading">Loading students...</div>;

  return (
    <div className="student-list">
      <h1>👨‍🎓 Students</h1>
      
      <div className="filter-section">
        <input
          type="text"
          placeholder="Filter by major..."
          value={filterMajor}
          onChange={(e) => setFilterMajor(e.target.value)}
          className="filter-input"
        />
      </div>

      {error && <div className="error">{error}</div>}

      <div className="table-container">
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
            {students.map(student => (
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

      {students.length === 0 && (
        <div className="no-data">No students found</div>
      )}
    </div>
  );
}
