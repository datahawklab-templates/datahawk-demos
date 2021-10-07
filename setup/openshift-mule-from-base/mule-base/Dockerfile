FROM registry.redhat.io/openjdk/openjdk-8-rhel8

MAINTAINER marcos.caldeira@gmail.com

# Install some requerid packges in order to /opt/bin/mule works properly
USER 0
RUN yum install gettext procps -y
RUN yum clean all -y


# Install Mule Runtime
ENV MULE_HOME /opt/mule

ARG MULE_EE_VERSION=4.2.0
ARG MULE_EE_MD5=06478aec4aa1c1fd8d9a4cf8c17b3a2a
ARG MULE_LICENSE_FILE=license.lic

ARG MULE_TARGZ_FILE=mule-ee-distribution-standalone-${MULE_EE_VERSION}.tar.gz
ARG MULE_URL_BASE=http://s3.amazonaws.com/new-mule-artifacts
ARG MULE_DIR=mule-enterprise-standalone-${MULE_EE_VERSION}

RUN cd ~ &&\
    curl ${MULE_URL_BASE}/${MULE_TARGZ_FILE} -o ${MULE_TARGZ_FILE} &&\
    echo "${MULE_EE_MD5} ${MULE_TARGZ_FILE}" | md5sum -c &&\
	tar -xvzf ~/${MULE_TARGZ_FILE} --directory /opt/ &&\
	mv /opt/${MULE_DIR} $MULE_HOME &&\
	rm -f ~/${MULE_TARGZ_FILE}


# Install the Mule license, after installation Mule removes automacally the license file
COPY ${MULE_LICENSE_FILE} /license.lic
RUN $MULE_HOME/bin/mule -installLicense /license.lic


# Run as non-root user, 1001 Openshift runtime user
RUN chown -R 1001:0 $MULE_HOME &&\
	chmod -R g+wrx $MULE_HOME

USER 1001

# Default HTTP/HTTPS ports for Mule Applications
EXPOSE 8081 8082

ENV MULE_ARGUMENTS=
WORKDIR $MULE_HOME
ENTRYPOINT ["./bin/mule", "${MULE_ARGUMENTS}"]
