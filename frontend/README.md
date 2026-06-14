# Frontend - React Application

React 18+ frontend for the Advising System. ✅ **Complete and production-ready**

## Quick Start

### Prerequisites
- Node.js 18+
- npm 9+

### Setup
```bash
cd frontend
npm install
npm start
```

Frontend available at: http://localhost:3000

---

## Project Structure

```
frontend/
├── public/
│   └── index.html                 # React root HTML
├── src/
│   ├── pages/                     # Page components
│   │   ├── Dashboard.js           # 📊 Statistics & overview
│   │   ├── StudentList.js         # 👨‍🎓 Student management
│   │   ├── CourseList.js          # 📚 Course catalog
│   │   └── AdvisorList.js         # 👨‍🏫 Advisor directory
│   ├── services/
│   │   └── api.js                 # 🔌 API client
│   ├── styles/                    # CSS files
│   │   ├── App.css                # Main layout
│   │   ├── Dashboard.css
│   │   ├── StudentList.css
│   │   ├── CourseList.css
│   │   ├── AdvisorList.css
│   │   └── index.css
│   ├── App.js                     # Main component
│   └── index.js                   # Entry point
├── build/                         # Production build
├── .env.development               # Dev environment
├── .env.production                # Prod environment
├── package.json
└── README.md
```

---

## Available Pages

✅ **Dashboard** - Statistics, student count, course count, advisor count  
✅ **Students** - Searchable table with GPA filtering  
✅ **Courses** - Grid view with course details  
✅ **Advisors** - Card view with contact information  

---

## Development

### Available Scripts

```bash
# Start development server
npm start

# Build for production
npm run build

# Run tests
npm test

# Eject configuration (irreversible!)
npm run eject
```

---

## Environment Variables

### Development (`.env.development`)
```
REACT_APP_API_URL=http://localhost:8080/api
```

### Production (`.env.production`)
```
REACT_APP_API_URL=https://your-backend-url.com/api
```

---

## API Integration

The frontend uses **axios** for HTTP requests. All API calls go through `src/services/api.js`:

```javascript
import { studentAPI, courseAPI, advisorAPI, dashboardAPI } from './services/api';

// Fetch students
const students = await studentAPI.getAll();

// Fetch specific student
const student = await studentAPI.getById(id);

// Fetch courses
const courses = await courseAPI.getAll();
```

---

## Build & Deploy

### Build for Production
```bash
npm run build
# Output: ./build/
```

### Deploy to Vercel
```bash
npm install -g vercel
vercel
```

See [../docs/DEPLOY_TO_VERCEL.md](../docs/DEPLOY_TO_VERCEL.md) for complete deployment guide.

---

## Styling

All components use **custom CSS** with a professional color scheme:
- Primary: #667eea (Purple)
- Secondary: #764ba2 (Dark Purple)
- Responsive design with mobile breakpoints
- Clean, modern UI

---

## Dependencies

### Core Dependencies
- **react** ^18.2.0 - UI framework
- **react-dom** ^18.2.0 - DOM rendering
- **react-router-dom** ^6.20.0 - Client-side routing
- **axios** ^1.6.0 - HTTP client
- **react-scripts** 5.0.1 - Build tool
- **ajv** ^8.12.0 - JSON validation

---

## Features

✅ **Client-side routing** with React Router  
✅ **Responsive design** - works on desktop and mobile  
✅ **API integration** - connects to Spring Boot backend  
✅ **Error handling** - user-friendly error messages  
✅ **Loading states** - visual feedback during data fetch  
✅ **Environment variables** - dev/prod configuration  
✅ **Production build** - optimized and minified  

---

## Troubleshooting

### Build Error: Cannot find module 'ajv'
```bash
npm install ajv@^8.12.0 --save
npm run build
```

### API Connection Issues
- Verify backend is running on http://localhost:8080
- Check `.env.development` has correct `REACT_APP_API_URL`
- Check CORS is enabled in backend (should be by default)

### Clear Cache & Rebuild
```bash
rm -rf node_modules package-lock.json
npm install
npm start
```

---

## Testing

```bash
npm test
```

Runs the test suite in interactive watch mode.

---

## Documentation

- **Frontend README**: This file ✅
- **Backend README**: [../backend/README.md](../backend/README.md)
- **Deployment Guide**: [../docs/DEPLOY_TO_VERCEL.md](../docs/DEPLOY_TO_VERCEL.md)
- **React Setup**: [../docs/REACT_SETUP.md](../docs/REACT_SETUP.md)
- **API Reference**: [../docs/DEPLOYMENT_READY.md](../docs/DEPLOYMENT_READY.md)

---

## Status

| Component | Status |
|-----------|--------|
| Pages | ✅ Complete (Dashboard, Students, Courses, Advisors) |
| Styling | ✅ Complete (responsive CSS for all pages) |
| API Integration | ✅ Complete (all endpoints configured) |
| Error Handling | ✅ Complete (user-friendly messages) |
| Production Build | ✅ Complete (72.86 kB gzipped) |
| Testing | 🔄 Ready for manual testing |

---

**Ready to deploy?** See [DEPLOY_TO_VERCEL.md](../docs/DEPLOY_TO_VERCEL.md) 🚀

