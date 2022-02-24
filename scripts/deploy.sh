#!/bin/bash

REPOSITORY=/home/ec2-user/app/dev

echo "> Check running pid"

CURRENT_PID=$(pgrep -f Comment-Diary)

echo "> CURRENT_PID"

if [ -z $CURRENT_PID ]; then
    echo "> Not shut down because there is not application running now."
else
    echo "> Kill -15 $CURRENT_PID"
    kill -15 $CURRENT_PID
    sleep 5
fi

echo "> Deploy new application"

echo "> Copy build file"

cp $REPOSITORY/*.jar $REPOSITORY/jar/

JAR_NAME=$(ls -tr $REPOSITORY/jar/*.jar | tail -n 1)

echo "> JAR Name: $JAR_NAME"

echo "> Give authority to $JAR_NAME"

chmod +x $JAR_NAME

nohup java -jar $JAR_NAME > $REPOSITORY/nohup.out 2>&1 &