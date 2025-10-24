@echo off
echo Stopping all localhost services...

echo.
echo Checking for services on common development ports...

:: Check and stop port 8080 (Spring Boot, Tomcat)
for /f "tokens=5" %%a in ('netstat -ano ^| findstr :8080') do (
    echo Stopping process on port 8080: %%a
    taskkill /PID %%a /F 2>nul
)

:: Check and stop port 3000 (React, Node.js)
for /f "tokens=5" %%a in ('netstat -ano ^| findstr :3000') do (
    echo Stopping process on port 3000: %%a
    taskkill /PID %%a /F 2>nul
)

:: Check and stop port 5000 (Flask, Express)
for /f "tokens=5" %%a in ('netstat -ano ^| findstr :5000') do (
    echo Stopping process on port 5000: %%a
    taskkill /PID %%a /F 2>nul
)

:: Check and stop port 8000 (Django, FastAPI)
for /f "tokens=5" %%a in ('netstat -ano ^| findstr :8000') do (
    echo Stopping process on port 8000: %%a
    taskkill /PID %%a /F 2>nul
)

:: Check and stop port 8081 (Alternative Spring Boot)
for /f "tokens=5" %%a in ('netstat -ano ^| findstr :8081') do (
    echo Stopping process on port 8081: %%a
    taskkill /PID %%a /F 2>nul
)

:: Check and stop port 9000 (Various dev servers)
for /f "tokens=5" %%a in ('netstat -ano ^| findstr :9000') do (
    echo Stopping process on port 9000: %%a
    taskkill /PID %%a /F 2>nul
)

echo.
echo All localhost services stopped!
echo.
pause

