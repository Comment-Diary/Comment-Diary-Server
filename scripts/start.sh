#!/bin/bash

# start.sh
# 서버 구동을 위한 스크립트

CURRENT_PORT=$(cat /etc/nginx/conf.d/service-url.inc | grep -Po '[0-9]+' | tail -1)
TARGET_PORT=0

echo "> Current port of running WAS is ${CURRENT_PORT}." >> /home/ec2-user/app/scripts_log/start.log

if [ ${CURRENT_PORT} -eq 8081 ]; then
  TARGET_PORT=8082
elif [ ${CURRENT_PORT} -eq 8082 ]; then
  TARGET_PORT=8081
else
  echo "> No WAS is connected to nginx" >> /home/ec2-user/app/scripts_log/start.log
fi

TARGET_PID=$(lsof -Fp -i TCP:${TARGET_PORT} | grep -Po 'p[0-9]+' | grep -Po '[0-9]+')

if [ ! -z ${TARGET_PID} ]; then
  echo "> Kill WAS running at ${TARGET_PORT}." >> /home/ec2-user/app/scripts_log/start.log
  sudo kill ${TARGET_PID}
fi

BUILD_JAR=$(ls /home/ec2-user/app/deploy/*.jar)
JAR_NAME=$(basename $BUILD_JAR)
echo "> build 파일명: $JAR_NAME" >> /home/ec2-user/app/scripts_log/start.log

echo "> build 파일 복사" >> /home/ec2-user/app/scripts_log/start.log
DEPLOY_PATH=/home/ec2-user/app/deploy/
cp $BUILD_JAR $DEPLOY_PATH

DEPLOY_JAR=$DEPLOY_PATH$JAR_NAME

nohup java -jar \
    -Dspring.config.location=classpath:/application.yml,/home/ec2-user/app/resources/application-prod.yml \
    -Dserver.port=${TARGET_PORT} $DEPLOY_JAR > /home/ec2-user/app/deploy/nohup.out 2>&1 &
echo "> Now new WAS runs at ${TARGET_PORT}." >> /home/ec2-user/app/scripts_log/start.log
exit 0