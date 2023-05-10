#!/bin/bash

appPort="40400"
# java [-options] -jar xxx.jar [args…]
JAVA_OPT="$JAVA_OPT --server.port=$appPort --spring.profiles.active=dev"

# 当前文件夹
CURRENT_DIR=$(
   cd "$(dirname "$0")"
   pwd
)

NATIVE_PATH=$CURRENT_DIR/stm-admin-0.0.1-SNAPSHOT.jar

start() {
  PID=$(ps -ef | grep "$NATIVE_PATH" | grep -v grep | awk '{print $2}')
  if [ -n "$PID" ]; then
    echo "$NATIVE_PATH:已经运行,PID=$PID"
  else
    exec nohup java -Dloader.path=./lib -jar  $NATIVE_PATH $JAVA_OPT >/dev/null 2>&1 &
    # 监控应用是否已经启动成功
    appIsStartSuccessful
  fi
}

stop() {
  PID=$(ps -ef | grep "$NATIVE_PATH" | grep -v grep | awk '{print $2}')
  if [ -n "$PID" ]; then
    echo "$NATIVE_PATH：准备结束，PID=$PID"
    sudo kill -15 "$PID"
    PID=$(ps -ef | grep "$NATIVE_PATH" | grep -v grep | awk '{print $2}')
    while [ -n "$PID" ]; do
      sleep 3s
      PID=$(ps -ef | grep "$NATIVE_PATH" | grep -v grep | awk '{print $2}')
    done
    echo "$NATIVE_PATH：成功结束"
  else
    echo "$NATIVE_PATH：未运行"
  fi
}

appIsStartSuccessful() {
  count=0
  sleepTime=2
  PID=$(netstat -ntpl | grep "$appPort" | awk '{print $7}' | sed -n '1p' | cut -d/ -f 1)
  while [ -z "$PID" ]; do
    if ((count == 15)); then
      echo "$NATIVE_PATH:$(expr $count \* $sleepTime)秒内未启动,请检查：tail -f logs/stm-admin.log"
      break
    fi
    count=$((count + 1))
    echo "$NATIVE_PATH:启动中..................$(expr $count \* $sleepTime - $sleepTime)秒"
    sleep 2s
    PID=$(netstat -ntpl | grep "$appPort" | awk '{print $7}' | sed -n '1p' | cut -d/ -f 1)
  done
  okCount=$((okCount + 1))
  echo "$NATIVE_PATH:已经启动成功,PID=$PID"
}

main() {
  case "$1" in
  start)
    start "$2" "$3"
    ;;
  stop)
    stop "$2" "$3"
    ;;
  restart)
    stop "$2" "$3"
    sleep 3s
    start "$2" "$3"
    ;;
  *)
    echo "第一个参数请输入:start|stop|restart"
    echo "默认为:start"
    start "$2" "$3"
    ;;
  esac
}

main "$1" "$2"
