# automated install

1. git clone this repo repo

## Install binaries for Oracle JDK and Weblogic dev installer (ONE TIME)

[https://download.oracle.com/otn/nt/middleware/12c/122140/fmw_12.2.1.4.0_wls_quick_Disk1_1of1.zip]
[https://www.oracle.com/java/technologies/javase/javase-jdk8-downloads.html]

## create an ORACLE_HOME/WEBLOGIC_HOME

```bash
mkdir -p /home/slastname/bin/wls1221
```

## install weblogic

* set JAVA_HOME,JAR_PATH and ORACLE_HOME

```bash
export JAVA_HOME=/home/slastname/bin/jdk1.8.0_271 ; \
export JAR_PATH=/home/slastname/bin
export ORACLE_HOME=/home/slastname/bin/wls1221
export PATH=${JAVA_HOME}/bin:$PATH ; \
java -version && \
java -jar ${JAR_PATH}/fmw_12.2.1.4.0_wls_quick.jar  ORACLE_HOME=${ORACLE_HOME}
```

## create a Weblogic domain

* update CreateDomain.sh
  export JAVA_HOME
  export SCRIPT_HOME
* update myDomain.properties
* execute CreateDomain.sh

## start your domain

* set JAVA_HOME and DOMAIN_PATH
  check path.domain.config in myDomain.properties for the location DOMAIN_PATH

```bash
export JAVA_HOME=/home/slastname/bin/jdk1.8.0_271 ; \
export DOMAIN_PATH=/home/slastname/name-github/wlsdomains/domains/name-domain
export PATH=${JAVA_HOME}/bin:$PATH ; \
java -version &&
cd $DOMAIN_PATH && \
./startWebLogic.sh
```

* to run in the background

  ```bash
  nohup ./startWebLogic.sh > /dev/null 2>&1 &
  ```

* look for a message like below in the startup output:

```bash
Dec 18, 2020 12:57:48,020 PM EST> <Notice> <Server> <BEA-002613> <Channel "Default" is now listening on 192.168.1.11:7001 for protocols iiop, t3, ldap, snmp, http.>
```

## access your domain

  [http://192.168.1.11:7001/console](http://192.168.1.11:7001/console)
