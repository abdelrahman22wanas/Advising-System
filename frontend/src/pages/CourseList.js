import React, { useEffect, useState } from 'react';
import { courseAPI } from '../services/api';
import Modal from '../components/Modal';
import '../styles/CourseList.css';

const COURSE_CATEGORIES = [
  'UNIVERSITY_REQUIREMENTS', 'COLLEGE_REQUIREMENTS', 'MAJOR_CORE',
  'MAJOR_ELECTIVE', 'FREE_ELECTIVE', 'CAPSTONE', 'GENERAL_EDUCATION'
];

export default function CourseList() {
  const [courses, setCourses] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [filterDept, setFilterDept] = useState('');
  const [searchTerm, setSearchTerm] = useState('');
  const [showModal, setShowModal] = useState(false);
  const [formData, setFormData] = useState({
    courseId: '', courseName: '', department: '', credits: '',
    capacity: '30', semester: '', instructor: '', category: '',
    description: '', prerequisites: '', recommendedSemester: '',
  });
  const [saving, setSaving] = useState(false);

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

  const handleDelete = async (id) => {
    if (window.confirm('Delete this course?')) {
      try {
        await courseAPI.delete(id);
        setCourses(courses.filter(c => c.courseId !== id));
      } catch (err) {
        setError(err.displayMessage);
      }
    }
  };

  const openCreate = () => {
    setFormData({
      courseId: '', courseName: '', department: '', credits: '',
      capacity: '30', semester: '', instructor: '', category: '',
      description: '', prerequisites: '', recommendedSemester: '',
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
        credits: parseInt(formData.credits, 10) || 0,
        capacity: parseInt(formData.capacity, 10) || 0,
        recommendedSemester: parseInt(formData.recommendedSemester, 10) || 1,
        prerequisites: formData.prerequisites
          ? formData.prerequisites.split(',').map(s => s.trim()).filter(Boolean)
          : [],
      };
      const created = await courseAPI.create(payload);
      setCourses([created, ...courses]);
      setShowModal(false);
    } catch (err) {
      setError(err.displayMessage);
    } finally {
      setSaving(false);
    }
  };

  if (loading) return <div className="loading">Loading courses...</div>;

  return (
    <div className="course-list">
      <div className="list-header">
        <h1>Courses</h1>
        <button className="btn-primary" onClick={openCreate}>+ Add Course</button>
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
          placeholder="Filter by department..."
          value={filterDept}
          onChange={(e) => setFilterDept(e.target.value)}
        />
      </div>

      {error && <div className="error">{error}</div>}

      <div className="course-grid">
        {filtered.map(course => (
          <div key={course.courseId} className="course-card">
            <div className="course-card-header">
              <h3>{course.courseName}</h3>
              <span className="course-code">{course.courseId}</span>
            </div>
            <p><strong>Department:</strong> {course.department}</p>
            <p><strong>Semester:</strong> {course.semester}</p>
            <p><strong>Capacity:</strong> {course.enrolledCount != null ? `${course.enrolledCount}/${course.capacity}` : course.capacity}</p>
            <p><strong>Category:</strong> {course.category?.replace(/_/g, ' ') || 'N/A'}</p>
            {course.prerequisites && course.prerequisites.length > 0 && (
              <p><strong>Prerequisites:</strong> {course.prerequisites.join(', ')}</p>
            )}
            <div className="course-card-footer">
              <span className="credits-badge">{course.credits} Credits</span>
              <button
                className="btn-delete-sm"
                onClick={() => handleDelete(course.courseId)}
              >
                Delete
              </button>
            </div>
          </div>
        ))}
      </div>

      {filtered.length === 0 && !loading && (
        <div className="no-data">No courses found</div>
      )}

      <Modal isOpen={showModal} onClose={() => setShowModal(false)} title="Add Course">
        <form className="modal-form" onSubmit={handleCreate}>
          <div className="form-row">
            <div className="form-group">
              <label>Course ID</label>
              <input name="courseId" value={formData.courseId} onChange={handleChange} required />
            </div>
            <div className="form-group">
              <label>Credits</label>
              <input name="credits" type="number" min="1" value={formData.credits} onChange={handleChange} required />
            </div>
          </div>
          <div className="form-group">
            <label>Course Name</label>
            <input name="courseName" value={formData.courseName} onChange={handleChange} required />
          </div>
          <div className="form-row">
            <div className="form-group">
              <label>Department</label>
              <input name="department" value={formData.department} onChange={handleChange} required />
            </div>
            <div className="form-group">
              <label>Category</label>
              <select name="category" value={formData.category} onChange={handleChange} required>
                <option value="">Select category</option>
                {COURSE_CATEGORIES.map(cat => (
                  <option key={cat} value={cat}>{cat.replace(/_/g, ' ')}</option>
                ))}
              </select>
            </div>
          </div>
          <div className="form-row">
            <div className="form-group">
              <label>Semester</label>
              <input name="semester" value={formData.semester} onChange={handleChange} required />
            </div>
            <div className="form-group">
              <label>Instructor</label>
              <input name="instructor" value={formData.instructor} onChange={handleChange} required />
            </div>
          </div>
          <div className="form-row">
            <div className="form-group">
              <label>Capacity</label>
              <input name="capacity" type="number" min="1" value={formData.capacity} onChange={handleChange} required />
            </div>
            <div className="form-group">
              <label>Recommended Semester</label>
              <input name="recommendedSemester" type="number" min="1" value={formData.recommendedSemester} onChange={handleChange} />
            </div>
          </div>
          <div className="form-group">
            <label>Prerequisites (comma-separated course IDs)</label>
            <input name="prerequisites" value={formData.prerequisites} onChange={handleChange} placeholder="e.g. CS101, CS102" />
          </div>
          <div className="form-group">
            <label>Description</label>
            <textarea name="description" value={formData.description} onChange={handleChange} rows="3" />
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
