# How to Run the Academic Advising System

## ✅ Quick Start

### Option 1: Run from JAR (Easiest)
```bash
java -jar target/advising-system-1.0.0.jar
```

### Option 2: Use Build Script (Windows)
```bash
build-and-run.bat
```

### Option 3: Use Build Script (Linux/Mac)
```bash
bash build-and-run.sh
```

## 🔧 Build from Scratch

### Step 1: Clean Build
```bash
mvn clean install
```

### Step 2: Run from JAR
```bash
java -jar target/advising-system-1.0.0.jar
```

## 💡 PowerShell Notes

If you're using PowerShell and want to run with Maven exec plugin, use:
```powershell
mvn exec:java -D "exec.mainClass=com.advisingsystem.AdvisingSystemApp"
```

Or simpler - just use the JAR method above (recommended).

## ✨ What You'll See

When the application starts:
1. Sample data loads automatically
2. Main menu displays with 6 options
3. All features ready to use

## 📋 Sample Menu

```
========== ADVISING SYSTEM MAIN MENU ==========
1. Manage Students
2. Manage Courses
3. Manage Advisors
4. Manage Enrollments
5. Manage Advising Sessions
6. Manage Grades
0. Exit
```

## 🎯 Next Steps

- Select option **1** to explore Student Management
- Select option **2** to explore Course Management
- Try all 6 menu options to see features
- Read [GUIDE.md](GUIDE.md) for detailed usage

---

**✅ Everything is working! Happy exploring!**
