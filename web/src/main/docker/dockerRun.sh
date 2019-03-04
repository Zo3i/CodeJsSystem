#!/bin/sh

#rm local file
rm -rf CodeJs*

#down file
git clone https://github.com/Zo3i/CodeJsFront.git
git clone https://github.com/Zo3i/CodeJsSystem.git

mv ./CodeJsFront/dist/ ./CodeJsSystem/web/src/main/docker/nginx/
#enter path
cd ./CodeJsSystem/web/src/main/docker
chmod -R 755 ./*

#stop and del running docker
docker-compose down

read -p "first creat input(1):" first
if [ "$first" -eq 1 ];
then
 cd ./mysql
 docker build -t js/mysql .
 cd ..
fi

docker-compose build
docker-compose up -d
