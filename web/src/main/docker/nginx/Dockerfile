FROM hub.c.163.com/library/nginx

MAINTAINER jo "tionsin@live.com"

RUN rm -rf /etc/nginx/conf.d/default.conf
COPY ./nginx.conf /etc/nginx/conf.d/default.conf
COPY ./dist/ /usr/share/nginx/html/
EXPOSE 99

CMD ["nginx", "-g", "daemon off;"]
