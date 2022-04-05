#!/bin/bash

# switch.sh
# nginx 연결 설정 스위치

# Crawl current connected port of WAS
CURRENT_PORT=$(cat /etc/nginx/conf.d/service-url.inc | grep -Po '[0-9]+' | tail -1)
TARGET_PORT=0

echo "> Nginx currently proxies to ${CURRENT_PORT}." >> /home/ec2-user/app/scripts_log/switch.log

# Toggle port number
if [ ${CURRENT_PORT} -eq 8081 ]; then
  TARGET_PORT=8082
elif [ ${CURRENT_PORT} -eq 8082 ]; then
  TARGET_PORT=8081
else
  echo "> No WAS is connected to nginx" >> /home/ec2-user/app/scripts_log/switch.log
  exit 1
fi

# Change proxying port into target port
echo "set \$service_url http://127.0.0.1:${TARGET_PORT};" | sudo tee /etc/nginx/conf.d/service-url.inc

echo "> Now Nginx proxies to ${TARGET_PORT}." >> /home/ec2-user/app/scripts_log/switch.log

# Reload nginx
sudo service nginx reload

echo "> Nginx reloaded." >> /home/ec2-user/app/scripts_log/switch.log

