#!/bin/bash

export ROOT_HOME=/deploy/tmp

export PRO_HOME=/Users/heylear/IdeaProjects/xiaolun-parent/

REMOTE_USER='root'

REMOTE_IP='139.196.50.231'

DB_PASSWD='901124'

REMOTE_ADDRESS=$REMOTE_USER@$REMOTE_IP

# 执行此脚本前先做好与服务器的信任
# 使用方法
# deploy.sh --web 全量发布web应用
# deploy.sh --db 全量发布数据库
# deploy.sh [目录或文件] 仅同步某个文件或文件夹


if [ "$1" == "--web" ]
 then

  /usr/bin/rsync -avzP --progress --delete xiaolun-app/tools/deploy.sh $REMOTE_ADDRESS:/root/deploy.sh

  cd $PRO_HOME

  mvn clean package -Dmaven.test.skip=true

  if [ $? -ne 0 ]
   then
    echo 'compile failed!'
    exit
  fi

  /usr/bin/rsync -avzP --progress --delete xiaolun-app/target/xiaolun-app/ $REMOTE_ADDRESS:/glor/weball/wxglor/

  ssh $REMOTE_ADDRESS "chmod 777 /root/deploy.sh; /root/deploy.sh --remote"

elif [ "$1" == "--db" ]
 then

  /usr/bin/rsync -avzP --progress --delete xiaolun-app/sql/ $REMOTE_ADDRESS:/tmp/sqltmp/

  ssh $REMOTE_ADDRESS "cd /tmp/sqltmp/; for file in \`ls *.sql\`; do mysql -u$REMOTE_USER -p$DB_PASSWD --default-character-set=utf8 --force -Dxlap<\$file; done"

elif [ "$1" == "--remote" ]
 then

 PID="`ps -ef | grep tomcat | grep -v grep | awk '{print $2}'`"

 if [ -n "$PID" ]
  then
   kill -9 $PID
 fi

 export CATALINA_BASE=/glor/tomcat
 export CATALINA_HOME=/glor/tomcat
 export CATALINA_TMPDIR=/glor/tomcat/temp
 export JRE_HOME=/opt/java/default
 export CLASSPATH=/glor/tomcat/bin/bootstrap.jar:/glor/tomcat/bin/tomcat-juli.jar

 /glor/tomcat/bin/startup.sh && tail -f /glor/tomcat/logs/catalina.out;

elif [ -n "$1" ]
 then
  /usr/bin/rsync -avzP --progress --delete xiaolun-app/src/main/webapp/$1 $REMOTE_ADDRESS:/glor/weball/wxglor/$1
fi



