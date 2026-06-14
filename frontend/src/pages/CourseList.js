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
        setError(err.message);
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
      
      <div className="filter-section">
        <input
          type="text"
          placeholder="Filter by department..."
          value={filterDept}
          onChange={(e) => setFilterDept(e.target.value)}
          className="filter-input"
        />
      </div>

      {error && <div className="error">{error}</div>}

      <div className="table-container">
        <table className="courses-table">
          <thead>
            <tr>
              <th>Code</th>
              <th>Name</th>
              <th>Department</th>
              <th>Credits</th>
              <th>Semester</th>
              <th>Capacity</th>
            </tr>
          </thead>
          <tbody>
            {courses.map(course => (
              <tr key={course.courseId}>
                <td>{course.courseId}</td>
                <td>{course.courseName}</td>
                <td>{course.department}</td>
                <td>{course.credits}</td>
                <td>{course.semester}</td>
                <td>{course.capacity}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>

      {courses.length === 0 && (
        <div className="no-data">No courses found</div>
      )}
    </div>
  );
}
