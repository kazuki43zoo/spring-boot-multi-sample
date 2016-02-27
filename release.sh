#!/usr/bin/env bash

# pull latest version
git pull

# get artifact version
VERSION=`mvn clean | grep 'Building.*Samples [0-9].*' | sed -e 's/^.*Building.*Samples //g'`
CURRENT_DIR=`pwd`

# install artifacts
mvn -U install
buildResult=$?
if test ${buildResult} -ne 0 ; then
    echo "[ERROR] Failed a build."
    exit ${buildResult}
fi

# stop services
sudo service boot-api-c stop
sudo service boot-api-r stop
sudo service boot-api-a stop
sudo service boot-scr-j stop
sudo service boot-scr-f stop
sudo service boot-scr-t stop
sudo service boot-db stop

# copy artifacts to deploy dir
chmod u+w services/*-${VERSION}.*
cp database/target/database-${VERSION}.jar services/.
cp screen-thymeleaf/target/screen-thymeleaf-${VERSION}.jar services/.
cp screen-freemarker/target/screen-freemarker-${VERSION}.jar services/.
cp screen-jsp/target/screen-jsp-${VERSION}.war services/.
cp api-auth/target/api-auth-${VERSION}.jar services/.
cp api-resource/target/api-resource-${VERSION}.jar services/.
cp api-client/target/api-client-${VERSION}.jar services/.

# change permission
chmod 500 services/database-${VERSION}.jar
chmod 500 services/screen-thymeleaf-${VERSION}.jar
chmod 500 services/screen-freemarker-${VERSION}.jar
chmod 500 services/screen-jsp-${VERSION}.war
chmod 500 services/api-auth-${VERSION}.jar
chmod 500 services/api-resource-${VERSION}.jar
chmod 500 services/api-client-${VERSION}.jar

# register services
sudo ln -f -s ${CURRENT_DIR}/services/database-${VERSION}.jar /etc/init.d/boot-db
sudo ln -f -s ${CURRENT_DIR}/services/screen-thymeleaf-${VERSION}.jar /etc/init.d/boot-scr-t
sudo ln -f -s ${CURRENT_DIR}/services/screen-freemarker-${VERSION}.jar /etc/init.d/boot-scr-f
sudo ln -f -s ${CURRENT_DIR}/services/screen-jsp-${VERSION}.war /etc/init.d/boot-scr-j
sudo ln -f -s ${CURRENT_DIR}/services/api-auth-${VERSION}.jar /etc/init.d/boot-api-a
sudo ln -f -s ${CURRENT_DIR}/services/api-resource-${VERSION}.jar /etc/init.d/boot-api-r
sudo ln -f -s ${CURRENT_DIR}/services/api-client-${VERSION}.jar /etc/init.d/boot-api-c

# setting auto-boot
sudo chkconfig --add boot-db
sudo chkconfig --add boot-scr-t
sudo chkconfig --add boot-scr-f
sudo chkconfig --add boot-scr-j
sudo chkconfig --add boot-api-a
sudo chkconfig --add boot-api-r
sudo chkconfig --add boot-api-c

# start services
sudo service boot-db start
sudo service boot-scr-t start
sudo service boot-scr-f start
sudo service boot-scr-j start
sudo service boot-api-a start
sudo service boot-api-r start
sudo service boot-api-c start
