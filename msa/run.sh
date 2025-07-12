#!/bin/bash

echo "✅ gradlew 실행 권한 + ..."
chmod +x eureka-server/gradlew
chmod +x gateway-service/gradlew
chmod +x example-service/gradlew
chmod +x account-service/gradlew


echo "========== ▶ Eureka 서버 실행 ... =============="
(cd eureka-server && ./gradlew bootRun) &

sleep 5

services=("example-service" "example2-service" "account-service")

for service in "${services[@]}"; do
  echo "========== ▶ 서비스 서버 실행 ... =============="
  (cd $service && ./gradlew bootRun) &
  sleep 3
done

echo "========== ▶ 게이트웨이 서버 실행 ... =============="
(cd gateway-service && ./gradlew bootRun) &
