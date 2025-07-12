@echo off
echo ✅ gradlew 실행 권한 부여는 윈도우에서 불필요합니다.

echo ========== ▶ Eureka 서버 실행 ... ==============
start cmd /k "cd eureka-server && gradlew.bat bootRun"

timeout /t 5 /nobreak > nul

set services=example-service example2-service account-service

for %%s in (%services%) do (
    echo ========== ▶ 서비스 서버 실행: %%s ... ==============
    start cmd /k "cd %%s && gradlew.bat bootRun"
    timeout /t 3 /nobreak > nul
)

echo ========== ▶ 게이트웨이 서버 실행 ... ==============
start cmd /k "cd gateway-service && gradlew.bat bootRun"
