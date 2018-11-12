#!/bin/sh
# /**
#  * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
#  *
#  * Author: ThinkGem@163.com
#  * 
#  */
echo ""
echo "[信息] 打包Web工程，并运行Web工程。"
echo ""

# 打包Web工程（开始）
cd ..
mvn clean package spring-boot:repackage -Dmaven.test.skip=true -U
cd target
# 打包Web工程（结束）

# 优化JVM参数
JAVA_OPTS="$MAVEN_OPTS -Xms256m -Xmx1024m -XX:MetaspaceSize=128m -XX:MaxMetaspaceSize=512m"

# 方式一、配置环境名称
# JAVA_OPTS="$JAVA_OPTS -Dspring.profiles.active=prod"

# 方式二、配置外部属性文件（建议）
# JAVA_OPTS="$JAVA_OPTS -Dspring.config.location=$PWD\app.yml"

if [ -z "$JAVA_HOME" ]; then
  RUN_JAVA=java
else
  RUN_JAVA="$JAVA_HOME"/bin/java
fi

# 根据情况修改 web.war 为您的 war 包名称
exec $RUN_JAVA $JAVA_OPTS -jar web.war