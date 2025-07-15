@echo off
setlocal enabledelayedexpansion

REM 사용할 포트 목록
set PORTS=8080 9999 8761 9998 8081

for %%P in (%PORTS%) do (
    for /f "tokens=5" %%A in ('netstat -ano ^| findstr :%%P') do (
        set PID=%%A
        echo 🔴 Port %%P 사용 중인 프로세스 종료(PID: !PID!)
        taskkill /F /PID !PID! >nul 2>&1
    )
    REM 포트가 비어 있는지 확인
    netstat -ano | findstr :%%P >nul
    if errorlevel 1 (
        echo ✅ Port %%P 는 비어 있습니다.
    )
)

endlocal
pause