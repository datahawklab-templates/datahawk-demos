FROM base-mule-image

MAINTAINER marcos.caldeira@gmail.com


# Copy configuration files
# COPY	./conf/* $MULE_HOME/conf/


# Deploy an Appplication
ARG APPLICATION=hello-mule-1.0.0-SNAPSHOT-mule-application.jar
COPY ${APPLICATION} $MULE_HOME/apps
