import React, { useEffect, useState } from 'react';
import { gradeAPI, studentAPI, courseAPI } from '../services/api';
import '../styles/Grades.css';

export default function Grades() {
  const [grades, setGrades] = useState([]);
  const [students, setStudents] = useState([]);
  const [courses, setCourses] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [searchTerm, setSearchTerm] = useState('');
  const [selectedStudent, setSelectedStudent] = useState('');
  const [gpa, setGpa] = useState(null);
  const [showAddGrade, setShowAddGrade] = useState(false);
  const [gradeForm, setGradeForm] = useState({
    studentId: '', courseId: '', letterGrade: 'A', semester: ''
  });

  useEffect(() => {
    const fetchData = async () => {
      try {
        const [gradeData, studentData, courseData] = await Promise.all([
          gradeAPI.getAll(),
          studentAPI.getAll(),
          courseAPI.getAll(),
        ]);
        setGrades(gradeData);
        setStudents(studentData);
        setCourses(courseData);
      } catch (err) {
        setError(err.displayMessage);
      } finally {
        setLoading(false);
      }
    };
    fetchData();
  }, []);

  const getStudentName = (id) => {
    const s = students.find(st => st.studentId === id);
    return s ? `${s.firstName} ${s.lastName}` : id;
  };

  const getCourseName = (id) => {
    const c = courses.find(co => co.courseId === id);
    return c ? c.courseName : id;
  };

  const handleStudentSelect = async (studentId) => {
    setSelectedStudent(studentId);
    setGradeForm({ ...gradeForm, studentId });
    if (studentId) {
      try {
        const gpaVal = await gradeAPI.calculateGPA(studentId);
        setGpa(gpaVal);
      } catch {
        setGpa(null);
      }
    } else {
      setGpa(null);
    }
  };

  const baseGrades = selectedStudent
    ? grades.filter(g => g.studentId === selectedStudent)
    : grades;

  const studentGrades = baseGrades.filter(g => {
    if (!searchTerm) return true;
    const term = searchTerm.toLowerCase();
    return (
      getStudentName(g.studentId).toLowerCase().includes(term) ||
      getCourseName(g.courseId).toLowerCase().includes(term) ||
      g.courseId?.toLowerCase().includes(term) ||
      g.letterGrade?.toLowerCase().includes(term) ||
      g.semester?.toLowerCase().includes(term) ||
      String(g.gpa).includes(term)
    );
  });

  const handleAddGrade = async () => {
    try {
      await gradeAPI.create(gradeForm);
      const data = await gradeAPI.getAll();
      setGrades(data);
      if (selectedStudent) {
        const gpaVal = await gradeAPI.calculateGPA(selectedStudent);
        setGpa(gpaVal);
      }
      setShowAddGrade(false);
      setGradeForm({ studentId: '', courseId: '', letterGrade: 'A', semester: '' });
    } catch (err) {
      setError(err.displayMessage);
    }
  };

  if (loading) return <div className="loading">Loading grades...</div>;

  return (
    <div className="grades">
      <div className="grades-header">
        <h1>Grades</h1>
        <button className="btn-primary" onClick={() => setShowAddGrade(true)}>
          + Record Grade
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
          value={selectedStudent}
          onChange={(e) => handleStudentSelect(e.target.value)}
        >
          <option value="">All Students</option>
          {students.map(s => (
            <option key={s.studentId} value={s.studentId}>
              {s.firstName} {s.lastName} ({s.studentId})
            </option>
          ))}
        </select>
        {gpa !== null && (
          <div className="gpa-display">
            GPA: <strong>{gpa.toFixed(2)}</strong>
          </div>
        )}
      </div>

      <div className="grades-table-wrapper">
        <table className="grades-table">
          <thead>
            <tr>
              <th>Student</th>
              <th>Course</th>
              <th>Grade</th>
              <th>GPA</th>
              <th>Semester</th>
            </tr>
          </thead>
          <tbody>
            {studentGrades.map((g, idx) => (
              <tr key={`${g.studentId}-${g.courseId}-${idx}`}>
                <td>{getStudentName(g.studentId)}</td>
                <td>{getCourseName(g.courseId)} ({g.courseId})</td>
                <td>
                  <span className={`grade-letter grade-${g.letterGrade?.toLowerCase()}`}>
                    {g.letterGrade}
                  </span>
                </td>
                <td>{g.gpa?.toFixed(2)}</td>
                <td>{g.semester}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>

      {studentGrades.length === 0 && (
        <div className="no-data">No grades found</div>
      )}

      {showAddGrade && (
        <div className="modal-overlay" onClick={() => setShowAddGrade(false)}>
          <div className="modal-content" onClick={(e) => e.stopPropagation()}>
            <div className="modal-header">
              <h2>Record Grade</h2>
              <button className="modal-close" onClick={() => setShowAddGrade(false)}>&times;</button>
            </div>
            <div className="modal-body">
              <div className="grade-form">
                <label>
                  Student
                  <select
                    value={gradeForm.studentId}
                    onChange={(e) => setGradeForm({ ...gradeForm, studentId: e.target.value })}
                  >
                    <option value="">Select student...</option>
                    {students.map(s => (
                      <option key={s.studentId} value={s.studentId}>
                        {s.firstName} {s.lastName} ({s.studentId})
                      </option>
                    ))}
                  </select>
                </label>
                <label>
                  Course
                  <select
                    value={gradeForm.courseId}
                    onChange={(e) => setGradeForm({ ...gradeForm, courseId: e.target.value })}
                  >
                    <option value="">Select course...</option>
                    {courses.map(c => (
                      <option key={c.courseId} value={c.courseId}>
                        {c.courseId} - {c.courseName}
                      </option>
                    ))}
                  </select>
                </label>
                <label>
                  Grade
                  <select
                    value={gradeForm.letterGrade}
                    onChange={(e) => setGradeForm({ ...gradeForm, letterGrade: e.target.value })}
                  >
                    {['A', 'A-', 'B+', 'B', 'B-', 'C+', 'C', 'C-', 'D+', 'D', 'F'].map(g => (
                      <option key={g} value={g}>{g}</option>
                    ))}
                  </select>
                </label>
                <label>
                  Semester
                  <input
                    type="text"
                    placeholder="e.g. 2024 Fall"
                    value={gradeForm.semester}
                    onChange={(e) => setGradeForm({ ...gradeForm, semester: e.target.value })}
                  />
                </label>
                <button className="btn-primary" onClick={handleAddGrade}>
                  Record Grade
                </button>
              </div>
            </div>
          </div>
        </div>
      )}
    </div>
  );
}
