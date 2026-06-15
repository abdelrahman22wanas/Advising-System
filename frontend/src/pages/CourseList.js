import React, { useEffect, useState } from 'react';
import { courseAPI } from '../services/api';
import '../styles/CourseList.css';

export default function CourseList() {
  const [courses, setCourses] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [filterDept, setFilterDept] = useState('');

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

  if (loading) return <div className="loading">Loading courses...</div>;

  return (
    <div className="course-list">
      <h1>📚 Courses</h1>

      <div className="filter-bar">
        <input
          type="text"
          placeholder="Filter by department..."
          value={filterDept}
          onChange={(e) => setFilterDept(e.target.value)}
        />
      </div>

      {error && <div className="error">{error}</div>}

      <div className="course-grid">
        {courses.map(course => (
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

      {courses.length === 0 && !loading && (
        <div className="no-data">No courses found</div>
      )}
    </div>
  );
}
