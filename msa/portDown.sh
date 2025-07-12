#!/bin/bash

PORTS=(8080 9999 8761 9998 8081)

for PORT in "${PORTS[@]}"; do
  PID=$(lsof -ti tcp:$PORT)

  if [ -n "$PID" ]; then
    echo "🔴 Port $PORT 사용 중인 프로세스 종료(PID: $PID)"
    kill -9 $PID
  else
    echo "✅ Port $PORT 는 비어 있습니다."
  fi
done
