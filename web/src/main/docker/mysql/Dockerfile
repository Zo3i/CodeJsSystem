FROM mysql:5.7

#设置免密登录
ENV MYSQL_ALLOW_EMPTY_PASSWORD yes

#将所需文件放到容器中
COPY js.sql /docker-entrypoint-initdb.d
COPY privileges.sql /docker-entrypoint-initdb.d 
#设置容器启动时执行的命令
#CMD ["sh", "/mysql/setup.sh"]
