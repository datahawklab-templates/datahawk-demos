#!/usr/bin/bash

export MW_HOME=/home/swapanc/IdeaProjects/app/wls1411-swapan
export JAVA_HOME=/home/swapanc/IdeaProjects/bin/jdk1.8.0_281
export SCRIPT_HOME=/home/swapanc/scripts/wls1411-swapan
export WLS_HOME=$MW_HOME/wlserver
export WL_HOME=$WLS_HOME
export PATH=${JAVA_HOME}/bin:$PATH
export CONFIG_JVM_ARGS=-Djava.security.egd=file:/dev/./urandom
. $WLS_HOME/server/bin/setWLSEnv.sh && \
java weblogic.WLST CreateDomain.py -p myDomain.properties

