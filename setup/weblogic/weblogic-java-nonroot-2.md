# Weblogic java non root developer install


## create the required directories
```bash=
mkdir -p /home/swapanc/IdeaProjects/bin && \
mkdir -p /home/swapanc/IdeaProjects/app/wls1411-swapan && \
```
## delete everything in one shot
```bash=
export WEBLOGIC_PROJECT_HOME=/home/swapanc/IdeaProjects/app/wls1411-swapan ;\
([[ -e $WEBLOGIC_PROJECT_HOME ]] && (rm -rf $WEBLOGIC_PROJECT_HOME || echo "error deleting ${WEBLOGIC_PROJECT_HOME}")) || "$WEBLOGIC_PROJECT_HOME doesnt exist"
```

## install non-root oracle JDK
```bash=
export WEBLOGIC_PROJECT_HOME=/home/swapanc/IdeaProjects/app/wls1411-swapan ;\
export JDK_INSTALL_HOME=${WEBLOGIC_PROJECT_HOME}/jdk ;\
export JDK_ARCHIVE=jdk-8u281-linux-x64.tar.gz ;\
export JDK=jdk1.8.0_281 ;\
export JAVA_HOME=${JDK_INSTALL_HOME}/${JDK} ;\
export PATH=${JAVA_HOME}/bin:$PATH ;\
( [[ ! -e $WEBLOGIC_PROJECT_HOME ]] && (( mkdir -p $JDK_INSTALL_HOME) || ( echo "could not create $WEBLOGIC_PROJECT_HOME" && exit 1 ))) || ( echo "$WEBLOGIC_PROJECT_HOME already exists" && exit 1 )
echo "installing java ${JAVA_HOME} in ${JDK_INSTALL_HOME}" ;\
cd $JDK_INSTALL_HOME && \
wget -O - -q https://raw.githubusercontent.com/typekpb/oradown/master/oradown.sh  | bash -s -- --cookie=accept-weblogicserver-server --username=email.address@dmain.com --password=pass! http://download.oracle.com/otn/java/jdk/8u281-b09/89d678f2be164786b292527658ca1605/jdk-8u281-linux-x64.tar.gz && \
tar xvf $JDK_ARCHIVE && \
java -version && \
echo "success installing java to ${JAVA_HOME}"
```

## prep non-root weblogic
```bash=
export WEBLOGIC_PROJECT_HOME=/home/swapanc/IdeaProjects/app/wls1411-swapan
export JAR_DOWNLOAD_HOME=${WEBLOGIC_PROJECT_HOME}/weblogic_jar ;\
export WEBLOGIC_ARCHIVE=fmw_14.1.1.0.0_wls_lite_quick_Disk1_1of1.zip ;\
export jar=fmw_14.1.1.0.0_wls_lite_quick_generic.jar ;\
( ([[ -e $WEBLOGIC_PROJECT_HOME ]] && [[ -e $JAR_DOWNLOAD_HOME ]]) && ( echo "$JAR_DOWNLOAD_HOME already exists" && exit 1 ) )



&& (( mkdir -p $JAR_DOWNLOAD_HOME) || echo "could not create $JAR_DOWNLOAD_HOME" && exit 1 )) || ( echo "could not create $WEBLOGIC_PROJECT_HOME" && exit 1 ))) ||
echo "unarchiving ${ARCHIVE} to ${AR_DOWNLOAD_HOME" ;\
cd $INSTALL_HOME && \
wget -O - -q https://raw.githubusercontent.com/typekpb/oradown/master/oradown.sh | bash -s -- --cookie=accept-weblogicserver-server --username=swapan.chakrabarty@domain.com--password=pass https://download.oracle.com/otn/nt/middleware/14c/14110/fmw_14.1.1.0.0_wls_lite_quick_Disk1_1of1.zip && \
unzip $ARCHIVE && \
echo "successfuly unzipped"
```
## create non-root weblogic oracle_home for developers
```bash=
export INSTALL_HOME=/home/swapanc/IdeaProjects/bin ;\
export JDK=jdk1.8.0_281 ;\
export JAVA_HOME=${INSTALL_HOME}/${JDK} ;\
export PATH=${JAVA_HOME}/bin:${PATH} ;\
export PROJ_HOME=/home/swapanc/IdeaProjects/app ;\
export PROJECT=wls1411-swapan ;\
export JAR=fmw_14.1.1.0.0_wls_lite_quick_generic.jar ;\
export OCL_HOME=${PROJ_HOME}/${PROJECT} ;\
java -version && \
java -jar ${INSTALL_HOME}/${JAR} ORACLE_HOME=${OCL_HOME} && \
echo "successfuly created Weblogic home: ${OCL_HOME}"
```
# create a Weblogic domain

## git clone this repo repo

## set env variables at command line
```bash=
export MW_HOME=$1
export JAVA_HOME=$2
```

## CreateDomain.sh

```bash=
cat >/home/swapanc/scripts/wls1411-swapan/createDomain.sh <<<'#!/usr/bin/bash

export MW_HOME=/home/swapanc/IdeaProjects/app/wls1411-swapan
export JAVA_HOME=/home/swapanc/IdeaProjects/bin/jdk1.8.0_281
export SCRIPT_HOME=/home/swapanc/scripts/wls1411-swapan
export WLS_HOME=$MW_HOME/wlserver
export WL_HOME=$WLS_HOME
export PATH=${JAVA_HOME}/bin:$PATH
export CONFIG_JVM_ARGS=-Djava.security.egd=file:/dev/./urandom
. $WLS_HOME/server/bin/setWLSEnv.sh && \
java weblogic.WLST CreateDomain.py -p myDomain.properties
'
```
### myDomain.properties
```bash=

cat >/home/swapanc/scripts/wls1411-swapan/myDomain.properties <<<"\
# Paths
path.middleware=/home/swapanc/IdeaProjects/app/wls1411-swapan
path.wls=/home/swapanc/IdeaProjects/app/wls1411-swapan/wlserver
path.domain.config=/home/swapanc/IdeaProjects/app/swapan-wlsdomains
path.app.config=/home/swapanc/IdeaProjects/app/swapan-wlsapplications

# Credentials
domain.name=swapan-domain
domain.username=weblogic
domain.password=Password1

# Listening address
domain.admin.port=700
domain.admin.address=acer.test.com
domain.admin.port.ssl=7501# Paths
path.middleware=/home/swapanc/IdeaProjects/app/wls1411-swapan
path.wls=/home/swapanc/IdeaProjects/app/wls1411-swapan/wlserver
path.domain.config=/home/swapanc/IdeaProjects/app/swapan-wlsdomains
path.app.config=/home/swapanc/IdeaProjects/app/swapan-wlsapplications

# Credentials
domain.name=swapan-domain
domain.username=weblogic
domain.password=Password1

# Listening address
domain.admin.port=7001
domain.admin.address=acer.test.com
domain.admin.port.ssl=7501
EOF
"

chmod u+x /home/swapanc/scripts/wls1411-swapan/createDomain.sh && \
cd /home/swapanc/scripts/wls1411-swapan && \
./createDomain.sh
```

## Start weblogic domain
```bash=
export INSTALL_HOME=/home/swapanc/IdeaProjects/bin ;\
export JDK=jdk1.8.0_281 ;\
export JAVA_HOME=${INSTALL_HOME}/${JDK} ;\
export PATH=${JAVA_HOME}/bin:${PATH} ;\
java -version &&
cd /home/swapanc/IdeaProjects/app/swapan-wlsdomains && \
./startWebLogic.sh
nohup ./startWebLogic.sh > /dev/null 2>&1 &
```

## recreate Domain

**make sure to set DOMAIN_PATH**

```bash=
rm -rf /home/schakrabarty/wlsdomains/domains/swapan-domain && \
cd /home/schakrabarty/Documents/work/code-work/weblogic-openshift/wlst && \
./CreateDomain.sh && \
cd /home/swapanc/IdeaProjects/app/swapan-wlsdomains/swapan-domain &&\
./startWebLogic.sh
```

## access the weblogic admin console

[http://192.168.1.178:7001/console](http://192.168.1.178:7001/console)

```bash=
/tmp/yourfilehere


export tmpdir="/tmp/kafka-connect-${RANDOM}" ;\
export version="8.0.19" ;\
export url_prefix='https://dev.mysql.com/get/Downloads/Connector-J' ;\
bash << EOF
echo $tmpdir
export driver="mysql-connector-java-${version}.tar.gz"
export download_url="${url_prefix}/${driver}"
if [[ ! -d $tmpdir ]]; then if ! mkdir $tmpdir; then echo "error creating $tmpdir"; exit 1; fi ;fi
if ! cd $tmpdir; then echo "could not cd to $tmpdir"; fi
if ! wget -P "${tmpdir}/" -q $download_url ; then "echo could not download $driver to $tmpdir "; exit 1; fi
if tar -zxvf $tmpdir/$driver -C $tmpdir && tar -xf
cd $tmpdir
ls -rtl $tmpdir
EOF
```















## archive
## download binaries
```bash
mkdir -p /home/schakrabarty/IdeaProjects/bin &&
cd /home/schakrabarty/IdeaProjects/bin &&
wget -O - -q https://raw.githubusercontent.com/typekpb/oradown/master/oradown.sh  | bash -s -- --cookie=accept-weblogicserver-server --username=swapan.chakrabarty@domain.com--password=pass http://download.oracle.com/otn/nt/middleware/12c/122140/fmw_12.2.1.4.0_wls_lite_Disk1_1of1.zip &&
wget -O - -q https://raw.githubusercontent.com/typekpb/oradown/master/oradown.sh  | bash -s -- --cookie=accept-weblogicserver-server --username=swapan.chakrabarty@domain.com--password=pass http://download.oracle.com/otn/java/jdk/8u281-b09/89d678f2be164786b292527658ca1605/jdk-8u281-linux-x64.tar.gz
```
https://www.oracle.com/webapps/redirect/signon?nexturl=



## Install binaries for Oracle JDK and Weblogic dev installer (ONE TIME)

## Get the latest Oracle JDK and the 12.2.1.4 generic weblogic installer. Install under a non root id
[https://www.oracle.com/java/technologies/javase/javase-jdk8-downloads.html](https://www.oracle.com/java/technologies/javase/javase-jdk8-downloads.html)
[https://www.oracle.com/middleware/technologies/weblogic-server-downloads.html](https://www.oracle.com/middleware/technologies/weblogic-server-downloads.html)

## install weblogic dev installer locally

```bash
export JAVA_HOME=/local/path/jdk-8u271-linux-x64/jdk1.8.0_271 ; \
export PATH=${JAVA_HOME}/bin:$PATH ; \
java -version && \
java -jar /localpath/fmw_12.2.1.4.0_wls_quick_Disk1_1of1/fmw_12.2.1.4.0_wls_quick.jar ORACLE_HOME=/local/path/wls1221
```