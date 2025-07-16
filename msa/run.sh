#!/bin/bash

echo "✅ gradlew 실행 권한 + ..."
chmod +x eureka-server/gradlew
chmod +x gateway-service/gradlew
chmod +x account-service/gradlew
chmod +x board-service/gradlew
chmod +x gathering-service/gradlew
chmod +x information-service/gradlew
chmod +x qna-service/gradlew
chmod +x review-service/gradlew


echo "========== ▶ Eureka 서버 실행 ... =============="
(cd eureka-server && ./gradlew bootRun) &

sleep 5

services=("account-service" "board-service" "information-service" "qna-service" "review-service")

for service in "${services[@]}"; do
  echo "========== ▶ 서비스 서버 실행 ... =============="
  (cd $service && ./gradlew bootRun) &
  sleep 3
done

echo "========== ▶ 게이트웨이 서버 실행 ... =============="
(cd gateway-service && ./gradlew bootRun) &
