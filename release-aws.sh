#!/usr/bin/env bash

echo "[INFO] Release started"

# stop services
echo "[INFO] Stop services"
sudo service boot-api-c stop
sudo service boot-api-r stop
sudo service boot-api-a stop
sudo service boot-scr-j stop
sudo service boot-scr-f stop
sudo service boot-scr-t stop
sudo service boot-db stop

# pull latest version
echo "[INFO] Git pull"
git pull

# get artifact version
VERSION=`./mvnw clean | grep 'Building.*Samples [0-9].*' | sed -e 's/^.*Building.*Samples //g'`

# build artifacts
echo "[INFO] Build artifacts for ${VERSION}"
./mvnw -U install
buildResult=$?
if test ${buildResult} -ne 0 ; then
    echo "[ERROR] Failed a build."
    exit ${buildResult}
fi

# Make release directory
echo "[INFO] Make release directory [${HOME}/apps/${VERSION}]"
mkdir -p ${HOME}/apps/${VERSION}

# release artifacts to deploy dir
echo "[INFO] Release artifacts to ${HOME}/apps/${VERSION}"
chmod u+w ${HOME}/apps/${VERSION}/*.*ar
cp database/target/database-${VERSION}.jar ${HOME}/apps/${VERSION}/database.jar
cp screen-thymeleaf/target/screen-thymeleaf-${VERSION}.jar ${HOME}/apps/${VERSION}/screen-thymeleaf.jar
cp screen-freemarker/target/screen-freemarker-${VERSION}.jar ${HOME}/apps/${VERSION}/screen-freemarker.jar
cp screen-jsp/target/screen-jsp-${VERSION}.war ${HOME}/apps/${VERSION}/screen-jsp.war
cp api-auth/target/api-auth-${VERSION}.jar ${HOME}/apps/${VERSION}/api-auth.jar
cp api-resource/target/api-resource-${VERSION}.jar ${HOME}/apps/${VERSION}/api-resource.jar
cp api-client/target/api-client-${VERSION}.jar ${HOME}/apps/${VERSION}/api-client.jar
chmod 500 ${HOME}/apps/${VERSION}/*.*ar
ls -l ${HOME}/apps/${VERSION}/*.*ar

# release conf files to deploy dir
echo "[INFO] Release conf files to ${HOME}/apps/${VERSION}"
cp service-conf/*.conf ${HOME}/apps/${VERSION}/.
ls -l ${HOME}/apps/${VERSION}/*.conf

# release static resource files (html, etc..)
echo "[INFO] Release static resource files to /var/www/html/"
sudo cp -R www/html/* /var/www/html/.
ls -l /var/www/html/

# register services
echo "[INFO] Register services"
sudo ln -f -s ${HOME}/apps/${VERSION}/database.jar /etc/init.d/boot-db
sudo ln -f -s ${HOME}/apps/${VERSION}/screen-thymeleaf.jar /etc/init.d/boot-scr-t
sudo ln -f -s ${HOME}/apps/${VERSION}/screen-freemarker.jar /etc/init.d/boot-scr-f
sudo ln -f -s ${HOME}/apps/${VERSION}/screen-jsp.war /etc/init.d/boot-scr-j
sudo ln -f -s ${HOME}/apps/${VERSION}/api-auth.jar /etc/init.d/boot-api-a
sudo ln -f -s ${HOME}/apps/${VERSION}/api-resource.jar /etc/init.d/boot-api-r
sudo ln -f -s ${HOME}/apps/${VERSION}/api-client.jar /etc/init.d/boot-api-c
ls -l /etc/init.d/boot*

# delete auto-boot settings
echo "[INFO] Delete auto-boot settings"
sudo chkconfig --del boot-db
sudo chkconfig --del boot-scr-t
sudo chkconfig --del boot-scr-f
sudo chkconfig --del boot-scr-j
sudo chkconfig --del boot-api-a
sudo chkconfig --del boot-api-r
sudo chkconfig --del boot-api-c

# add auto-boot settings
echo "[INFO] Add auto-boot settings"
sudo chkconfig --add boot-db
#sudo chkconfig --add boot-scr-t
#sudo chkconfig --add boot-scr-f
#sudo chkconfig --add boot-scr-j
sudo chkconfig --add boot-api-a
sudo chkconfig --add boot-api-r
sudo chkconfig --add boot-api-c

# start services
echo "[INFO] Start services"
sudo service boot-db start
#sudo service boot-scr-t start
#sudo service boot-scr-f start
#sudo service boot-scr-j start
sudo service boot-api-a start
sudo service boot-api-r start
sudo service boot-api-c start

echo "[INFO] Release completed"
