# Frontend - React Application

React 18+ frontend for the Advising System.

## Quick Start

### Prerequisites
- Node.js 18+ 
- npm 9+

### Setup
```bash
npx create-react-app .
npm install axios react-router-dom
npm start
```

Frontend available at: http://localhost:3000

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

## Project Structure (To Be Created)

```
frontend/
├── public/
│   └── index.html
├── src/
│   ├── components/           (Reusable components)
│   ├── pages/                (Page components)
│   │   ├── Dashboard.js
│   │   ├── StudentList.js
│   │   ├── CourseList.js
│   │   ├── AdvisorList.js
│   │   ├── Enrollment.js
│   │   ├── Grades.js
│   │   └── Sessions.js
│   ├── services/
│   │   └── api.js            (API client)
│   ├── styles/               (CSS files)
│   ├── App.js                (Main component)
│   └── index.js              (Entry point)
├── .env.development          (Dev environment)
├── .env.production           (Prod environment)
├── package.json
└── README.md                 (This file)
```

---

## Configuration

### Environment Variables

**`.env.development`**:
```
REACT_APP_API_URL=http://localhost:8080/api
REACT_APP_APP_NAME=Advising System
```

**`.env.production`**:
```
REACT_APP_API_URL=https://your-api-url.com/api
REACT_APP_APP_NAME=Advising System
```

---

## API Integration

See [../docs/REACT_SETUP.md](../docs/REACT_SETUP.md) for:
- API service setup
- Component examples
- Data fetching patterns
- Error handling

---

## Pages to Create

- **Dashboard** - Statistics and overview
- **Students** - List, search, add, edit, delete
- **Courses** - Browse, filter, view details
- **Advisors** - List, manage assignments
- **Enrollment** - Enroll, drop courses, view history
- **Grades** - View grades, GPA calculation
- **Sessions** - Schedule, track, update status

---

## Styling

### Options
- Tailwind CSS (recommended)
- Material-UI
- Bootstrap
- Custom CSS

See [../docs/REACT_SETUP.md](../docs/REACT_SETUP.md) for examples.

---

## Deployment

### Vercel Deployment
```bash
npm install -g vercel
vercel
```

See [../docs/DEPLOY_TO_VERCEL.md](../docs/DEPLOY_TO_VERCEL.md) for full instructions.

---

## Testing

```bash
npm test
```

---

## Dependencies

### Required
- react 18+
- react-dom 18+
- axios (HTTP client)
- react-router-dom (routing)

### Optional
- tailwindcss (styling)
- chart.js (charts)
- date-fns (date handling)

---

## Troubleshooting

### API Connection Issues
- Verify `REACT_APP_API_URL` in `.env` file
- Check backend is running
- Check CORS is enabled (should be by default)

### Build Issues
```bash
# Clear cache and rebuild
rm -rf node_modules package-lock.json
npm install
npm run build
```

---

## Next Steps

1. Create with: `npx create-react-app .`
2. Install dependencies: `npm install axios react-router-dom`
3. Create folder structure as shown above
4. Implement pages using [../docs/REACT_SETUP.md](../docs/REACT_SETUP.md) as guide
5. Test locally with backend running
6. Deploy to Vercel

---

## Documentation

- **Frontend Docs**: This file
- **Setup Guide**: [../docs/REACT_SETUP.md](../docs/REACT_SETUP.md)
- **Deployment**: [../docs/DEPLOY_TO_VERCEL.md](../docs/DEPLOY_TO_VERCEL.md)
- **API Reference**: [../docs/DEPLOYMENT_READY.md](../docs/DEPLOYMENT_READY.md)
- **Backend Docs**: [../backend/README.md](../backend/README.md)

---

**Ready to build the frontend?** See [../docs/REACT_SETUP.md](../docs/REACT_SETUP.md) 🚀
