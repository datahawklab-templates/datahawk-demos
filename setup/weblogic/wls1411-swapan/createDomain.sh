#!/usr/bin/bash

export MW_HOME=/home/namec/IdeaProjects/app/wls1411-name
export JAVA_HOME=/home/namec/IdeaProjects/bin/jdk1.8.0_281
export SCRIPT_HOME=/home/namec/scripts/wls1411-name
export WLS_HOME=$MW_HOME/wlserver
export WL_HOME=$WLS_HOME
export PATH=${JAVA_HOME}/bin:$PATH
export CONFIG_JVM_ARGS=-Djava.security.egd=file:/dev/./urandom
. $WLS_HOME/server/bin/setWLSEnv.sh && \
java weblogic.WLST CreateDomain.py -p myDomain.properties

