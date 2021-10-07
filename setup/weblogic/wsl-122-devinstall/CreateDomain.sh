#!/usr/bin/sh

export MW_HOME=/home/slastname/bin/wls1221
export JAVA_HOME=/home/slastname/bin/jdk1.8.0_271
export SCRIPT_HOME=/home/slastname/name-github/wsl-122-devinstall
export WLS_HOME=$MW_HOME/wlserver
export WL_HOME=$WLS_HOME
export PATH=${JAVA_HOME}/bin:$PATH
export CONFIG_JVM_ARGS=-Djava.security.egd=file:/dev/./urandom
. $WLS_HOME/server/bin/setWLSEnv.sh && \
java weblogic.WLST CreateDomain.py -p myDomain.properties