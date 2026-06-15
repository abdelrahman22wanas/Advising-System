import React, { useEffect, useState } from 'react';
import { enrollmentAPI, studentAPI, courseAPI } from '../services/api';
import Modal from '../components/Modal';
import '../styles/Enrollments.css';

export default function Enrollments() {
  const [enrollments, setEnrollments] = useState([]);
  const [students, setStudents] = useState([]);
  const [courses, setCourses] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [searchTerm, setSearchTerm] = useState('');
  const [selectedStudent, setSelectedStudent] = useState('');
  const [showEnrollModal, setShowEnrollModal] = useState(false);
  const [availableCourses, setAvailableCourses] = useState([]);
  const [selectedCourse, setSelectedCourse] = useState('');

  useEffect(() => {
    const fetchData = async () => {
      try {
        const [enrollData, studentData, courseData] = await Promise.all([
          enrollmentAPI.getAll(),
          studentAPI.getAll(),
          courseAPI.getAll(),
        ]);
        setEnrollments(enrollData);
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

  const handleDrop = async (studentId, courseId) => {
    try {
      await enrollmentAPI.drop(studentId, courseId);
      setEnrollments(enrollments.filter(
        e => !(e.studentId === studentId && e.courseId === courseId)
      ));
    } catch (err) {
      setError(err.displayMessage);
    }
  };

  const openEnrollModal = async (studentId) => {
    setSelectedStudent(studentId);
    setSelectedCourse('');
    try {
      const courses = await enrollmentAPI.getAvailable(studentId);
      setAvailableCourses(courses);
      setShowEnrollModal(true);
    } catch (err) {
      setError(err.displayMessage);
    }
  };

  const handleEnroll = async () => {
    if (!selectedStudent || !selectedCourse) return;
    try {
      await enrollmentAPI.enroll(selectedStudent, selectedCourse);
      const [enrollData, courseData] = await Promise.all([
        enrollmentAPI.getAll(),
        courseAPI.getAll(),
      ]);
      setEnrollments(enrollData);
      setCourses(courseData);
      setShowEnrollModal(false);
    } catch (err) {
      setError(err.displayMessage);
    }
  };

  const getStudentName = (studentId) => {
    const s = students.find(st => st.studentId === studentId);
    return s ? `${s.firstName} ${s.lastName}` : studentId;
  };

  const getCourseName = (courseId) => {
    const c = courses.find(co => co.courseId === courseId);
    return c ? c.courseName : courseId;
  };

  const filtered = enrollments.filter(e => {
    if (!searchTerm) return true;
    const term = searchTerm.toLowerCase();
    const sName = (e.studentName || getStudentName(e.studentId)).toLowerCase();
    const cName = (e.courseName || getCourseName(e.courseId)).toLowerCase();
    return (
      sName.includes(term) ||
      cName.includes(term) ||
      e.studentId?.toLowerCase().includes(term) ||
      e.courseId?.toLowerCase().includes(term) ||
      String(e.credits).includes(term)
    );
  });

  if (loading) return <div className="loading">Loading enrollments...</div>;

  return (
    <div className="enrollments">
      <h1>Enrollments</h1>

      {error && <div className="error">{error}</div>}

      <div className="filter-bar">
        <input
          type="text"
          placeholder="Search any field..."
          value={searchTerm}
          onChange={(e) => setSearchTerm(e.target.value)}
        />
      </div>

      <div className="enrollments-table-wrapper">
        <table className="enrollments-table">
          <thead>
            <tr>
              <th>Student</th>
              <th>Course</th>
              <th>Credits</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            {filtered.map((enr, idx) => (
              <tr key={`${enr.studentId}-${enr.courseId}-${idx}`}>
                <td>{enr.studentName || getStudentName(enr.studentId)}</td>
                <td>{enr.courseName || getCourseName(enr.courseId)}</td>
                <td><span className="credits-badge">{enr.credits}</span></td>
                <td>
                  <button
                    className="btn-drop"
                    onClick={() => handleDrop(enr.studentId, enr.courseId)}
                  >
                    Drop
                  </button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>

      {filtered.length === 0 && (
        <div className="no-data">No enrollments found</div>
      )}

      <div className="enroll-actions">
        <h3>Enroll a Student</h3>
        <div className="enroll-form">
          <select
            value={selectedStudent}
            onChange={(e) => openEnrollModal(e.target.value)}
          >
            <option value="">Select student...</option>
            {students.map(s => (
              <option key={s.studentId} value={s.studentId}>
                {s.firstName} {s.lastName} ({s.studentId})
              </option>
            ))}
          </select>
        </div>
      </div>

      <Modal isOpen={showEnrollModal} onClose={() => setShowEnrollModal(false)} title="Select Course">
        <div className="modal-enroll">
          <select
            value={selectedCourse}
            onChange={(e) => setSelectedCourse(e.target.value)}
          >
            <option value="">Choose a course...</option>
            {availableCourses.map(c => (
              <option key={c.courseId} value={c.courseId}>
                {c.courseId} - {c.courseName} ({c.credits} cr)
              </option>
            ))}
          </select>
          <button className="btn-primary" onClick={handleEnroll}>
            Enroll
          </button>
        </div>
      </Modal>
    </div>
  );
}
