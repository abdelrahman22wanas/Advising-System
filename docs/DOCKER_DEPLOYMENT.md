# Docker Deployment Guide

## Quick Start with Docker Compose

Run the entire application stack locally with one command:

```bash
docker-compose up --build
```

This will:
1. Build the Spring Boot backend image
2. Build the React frontend image
3. Start both containers
4. Configure networking between them
5. Expose backend on port 8080
6. Expose frontend on port 3000

Access the application:
- **Frontend**: http://localhost:3000
- **Backend API**: http://localhost:8080/api
- **Dashboard Stats**: http://localhost:8080/api/dashboard/stats

---

## Building Individual Images

### Build Backend Image
```bash
cd backend
docker build -t advising-backend:latest .
```

### Build Frontend Image
```bash
cd frontend
docker build -t advising-frontend:latest .
```

---

## Running Individual Containers

### Run Backend
```bash
docker run -d \
  --name advising-backend \
  -p 8080:8080 \
  advising-backend:latest
```

### Run Frontend (connected to backend)
```bash
docker run -d \
  --name advising-frontend \
  -p 3000:3000 \
  -e REACT_APP_API_URL=http://localhost:8080/api \
  advising-frontend:latest
```

---

## Dockerfile Details

### Backend (Spring Boot)
- **Multi-stage build**: Reduces final image size
- **Maven builder stage**: Builds the JAR
- **Java 21 JRE stage**: Minimal runtime
- **Health check**: Monitors API health every 30s
- **Exposed port**: 8080

### Frontend (React)
- **Multi-stage build**: Optimizes production bundle
- **Node builder stage**: Installs dependencies and builds
- **Serve runtime**: Uses lightweight HTTP server
- **Health check**: Monitors application health every 30s
- **Exposed port**: 3000

---

## Deploying to Cloud Platforms

### Railway.app
```bash
# Install railway CLI
npm i -g @railway/cli

# Login
railway login

# Deploy
railway init
railway up
```

### Render
1. Connect GitHub repository
2. Create two services (backend and frontend)
3. Set environment variables
4. Deploy

### Fly.io
```bash
# Install Fly CLI
flyctl auth login

# Deploy backend from the backend directory
cd backend
flyctl launch
flyctl deploy
```

Use the generated Fly URL as `REACT_APP_API_URL` for the frontend.

### Docker Hub Registry
```bash
# Build and tag
docker build -t yourusername/advising-backend:latest ./backend
docker build -t yourusername/advising-frontend:latest ./frontend

# Push to Docker Hub
docker login
docker push yourusername/advising-backend:latest
docker push yourusername/advising-frontend:latest
```

### AWS ECS / AWS App Runner
```bash
# Create ECR repositories
aws ecr create-repository --repository-name advising-backend
aws ecr create-repository --repository-name advising-frontend

# Build and push to ECR
aws ecr get-login-password --region us-east-1 | docker login --username AWS --password-stdin <account-id>.dkr.ecr.us-east-1.amazonaws.com

docker build -t advising-backend:latest ./backend
docker tag advising-backend:latest <account-id>.dkr.ecr.us-east-1.amazonaws.com/advising-backend:latest
docker push <account-id>.dkr.ecr.us-east-1.amazonaws.com/advising-backend:latest
```

### Google Cloud Run
```bash
# Build with Cloud Build
gcloud builds submit --tag gcr.io/PROJECT-ID/advising-backend ./backend
gcloud builds submit --tag gcr.io/PROJECT-ID/advising-frontend ./frontend

# Deploy
gcloud run deploy advising-backend --image gcr.io/PROJECT-ID/advising-backend
gcloud run deploy advising-frontend --image gcr.io/PROJECT-ID/advising-frontend
```

---

## Environment Variables

### Backend
- `SPRING_APPLICATION_NAME` - Application name
- `SERVER_PORT` - Port (default: 8080)
- `SPRING_PROFILES_ACTIVE` - Optional profile for hosted runs

### Frontend
- `REACT_APP_API_URL` - Backend API URL (required)

---

## Notes

- The backend image includes `curl` so the health check can run inside the container.
- `docker-compose.yml` is optimized for local/full-stack runs and does not mount source code.

---

## Docker Compose Networking

Services communicate via container names:
- Backend: `http://backend:8080`
- Frontend: `http://frontend:3000`

The `docker-compose.yml` creates a bridge network allowing containers to resolve hostnames.

---

## Cleaning Up

```bash
# Stop all containers
docker-compose down

# Remove images
docker rmi advising-backend advising-frontend

# Remove dangling images and layers
docker image prune -a
```

---

## Health Checks

Both images include health checks:

**Backend**: Pings `/api/dashboard/health` endpoint
**Frontend**: Checks if port 3000 is responding

View health status:
```bash
docker ps
# HEALTHCHECK column shows (healthy), (unhealthy), or (starting)
```

---

## Troubleshooting

### Frontend can't reach backend
- Check `REACT_APP_API_URL` environment variable
- Ensure both containers are on same network
- Verify backend is healthy: `docker ps` shows (healthy)

### Port already in use
```bash
# Find what's using port 8080
netstat -ano | findstr :8080  # Windows
lsof -i :8080                 # Mac/Linux

# Use different ports
docker-compose.yml:
  ports:
    - "9000:8080"  # Backend
    - "3001:3000"  # Frontend
```

### Image build fails
```bash
# Clean up and rebuild
docker-compose down
docker system prune -a
docker-compose up --build
```

---

## Production Deployment Checklist

- [ ] Test locally with `docker-compose up`
- [ ] Verify frontend connects to backend API
- [ ] Check health checks are working
- [ ] Review environment variables for production
- [ ] Push images to registry (Docker Hub, ECR, GCR, etc.)
- [ ] Deploy to cloud platform
- [ ] Monitor logs and health status
- [ ] Test all endpoints with production URLs

---

See [DEPLOY_TO_VERCEL.md](./docs/DEPLOY_TO_VERCEL.md) for additional deployment options.
