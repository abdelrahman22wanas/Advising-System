import React, { useEffect, useState } from 'react';
import { courseAPI } from '../services/api';
import '../styles/CourseList.css';

export default function CourseList() {
  const [courses, setCourses] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [filterDept, setFilterDept] = useState('');
  const [searchTerm, setSearchTerm] = useState('');

  useEffect(() => {
    const fetchCourses = async () => {
      try {
        let data;
        if (filterDept) {
          data = await courseAPI.getByDepartment(filterDept);
        } else {
          data = await courseAPI.getAll();
        }
        setCourses(data);
      } catch (err) {
        setError(err.displayMessage);
      } finally {
        setLoading(false);
      }
    };
    fetchCourses();
  }, [filterDept]);

  const filtered = courses.filter(c => {
    if (!searchTerm) return true;
    const term = searchTerm.toLowerCase();
    return (
      c.courseId?.toLowerCase().includes(term) ||
      c.courseName?.toLowerCase().includes(term) ||
      c.department?.toLowerCase().includes(term) ||
      String(c.credits).includes(term) ||
      c.instructor?.toLowerCase().includes(term) ||
      c.semester?.toLowerCase().includes(term) ||
      c.category?.toLowerCase().includes(term)
    );
  });

  if (loading) return <div className="loading">Loading courses...</div>;

  return (
    <div className="course-list">
      <h1>📚 Courses</h1>

      <div className="filter-bar">
        <input
          type="text"
          placeholder="Search any field..."
          value={searchTerm}
          onChange={(e) => setSearchTerm(e.target.value)}
        />
        <input
          type="text"
          placeholder="Filter by department..."
          value={filterDept}
          onChange={(e) => setFilterDept(e.target.value)}
        />
      </div>

      {error && <div className="error">{error}</div>}

      <div className="course-grid">
        {filtered.map(course => (
          <div key={course.courseId} className="course-card">
            <h3>{course.courseName}</h3>
            <span className="course-code">{course.courseId}</span>
            <p><strong>Department:</strong> {course.department}</p>
            <p><strong>Semester:</strong> {course.semester}</p>
            <p><strong>Capacity:</strong> {course.capacity}</p>
            <span className="credits-badge">{course.credits} Credits</span>
          </div>
        ))}
      </div>

      {filtered.length === 0 && !loading && (
        <div className="no-data">No courses found</div>
      )}
    </div>
  );
}
