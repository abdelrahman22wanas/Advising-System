import axios from 'axios';

const API_URL = process.env.REACT_APP_API_URL || 'http://localhost:8080/api';

const api = axios.create({
  baseURL: API_URL,
  timeout: 10000,
  headers: {
    'Content-Type': 'application/json',
  },
});

api.interceptors.response.use(
  response => response.data,
  error => {
    const message =
      error.response?.data?.message ||
      error.response?.data?.error ||
      (error.code === 'ECONNABORTED' ? 'Request timed out' : null) ||
      error.message;
    error.displayMessage = message;
    return Promise.reject(error);
  }
);

export const studentAPI = {
  getAll: () => api.get('/students'),
  getById: (id) => api.get(`/students/${id}`),
  create: (student) => api.post('/students', student),
  delete: (id) => api.delete(`/students/${id}`),
  getByMajor: (major) => api.get(`/students/major/${major}`),
  getByGPARange: (min, max) => api.get(`/students/gpa-range/${min}/${max}`),
  getByGraduationYear: (year) => api.get(`/students/graduation/${year}`),
};

export const courseAPI = {
  getAll: () => api.get('/courses'),
  getById: (id) => api.get(`/courses/${id}`),
  create: (course) => api.post('/courses', course),
  getAvailable: () => api.get('/courses/available'),
  getByDepartment: (dept) => api.get(`/courses/department/${dept}`),
  getBySemester: (sem) => api.get(`/courses/semester/${sem}`),
};

export const advisorAPI = {
  getAll: () => api.get('/advisors'),
  getById: (id) => api.get(`/advisors/${id}`),
  create: (advisor) => api.post('/advisors', advisor),
  assignStudent: (advisorId, studentId) =>
    api.post(`/advisors/${advisorId}/students/${studentId}`),
  getStudents: (advisorId) => api.get(`/advisors/${advisorId}/students`),
};

export const enrollmentAPI = {
  getAll: () => api.get('/enrollments'),
  getStudent: (studentId) => api.get(`/enrollments/student/${studentId}`),
  getCourse: (courseId) => api.get(`/enrollments/course/${courseId}`),
  enroll: (studentId, courseId) =>
    api.post(`/enrollments/student/${studentId}/course/${courseId}`),
  drop: (studentId, courseId) =>
    api.delete(`/enrollments/student/${studentId}/course/${courseId}`),
  getAvailable: (studentId) =>
    api.get(`/enrollments/student/${studentId}/available-courses`),
  getCredits: (studentId) => api.get(`/enrollments/student/${studentId}/credits`),
};

export const gradeAPI = {
  getAll: () => api.get('/grades'),
  create: (grade) => api.post('/grades', grade),
  getStudent: (studentId) => api.get(`/grades/student/${studentId}`),
  calculateGPA: (studentId) => api.get(`/grades/student/${studentId}/gpa`),
  getCourseAverage: (courseId) => api.get(`/grades/course/${courseId}/average`),
  getSemesterGrades: (studentId, semester) =>
    api.get(`/grades/student/${studentId}/semester/${semester}`),
};

export const sessionAPI = {
  getAll: () => api.get('/sessions'),
  getById: (id) => api.get(`/sessions/${id}`),
  create: (session) => api.post('/sessions', session),
  delete: (id) => api.delete(`/sessions/${id}`),
  getStudent: (studentId) => api.get(`/sessions/student/${studentId}`),
  getAdvisor: (advisorId) => api.get(`/sessions/advisor/${advisorId}`),
  getByStatus: (status) => api.get(`/sessions/status/${status}`),
  updateStatus: (id, status) => api.put(`/sessions/${id}/status/${status}`),
};

export const dashboardAPI = {
  getStats: () => api.get('/dashboard/stats'),
  health: () => api.get('/dashboard/health'),
};

export default api;
