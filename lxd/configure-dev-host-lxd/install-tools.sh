#!/bin/bash

# on the host
lxc launch ubuntu-minimal:focal ubuntu-minimal-focal-sdkman &&\
lxc exec ubuntu-minimal-focal-sdkman bash


### as root on the container
apt-get update -y &&\
apt-get upgrade -y &&\
apt-get install -y curl wget git zip unzip &&\
useradd -d /home/servid -m -s /bin/bash -c "service id" servid ;\
echo -e "tublu1224\ntublu1224" | passwd servid &&\
su - servid 

### as servid on the container
curl -s "https://get.sdkman.io" | bash &&\
sed -i 's/sdkman_auto_answer=false/sdkman_auto_answer=true/g' ~/.sdkman/etc/config &&\
sed -i 's/sdkman_auto_selfupdate=false/sdkman_auto_selfupdate=true/g' ~/.sdkman/etc/config

### on the host
lxc stop ubuntu-minimal-focal-sdkman
lxc publish ubuntu-minimal-focal-sdkman --alias sdkman-base
lxc image ls -clda 
lxc launch sdkman-base java-devserver
lxc shell java-devserver

### on the container ``
sdk install java 11.0.11.j9-adpt

### on the host
lxc stop java-devserver
lxc publish java-devserver --alias ubuntu-focal-jdk-adpt11011j9

### launch a container of the image
lxc launch ubuntu-focal-jdk-adpt11011j9 eap-domain-master




sdk default java 11.0.11.j9-adpt

lxc exec $CONTAINER -- apt-get install -y curl wget git zip unzip
lxc exec $CONTAINER -- apt-get update -y
lxc exec $CONTAINER -- apt-get upgrade -y
lxc exec $CONTAINER -- useradd -d /home/servid -m -s /bin/bash -c "service id" servid
lxc exec $CONTAINER -- sh -c 'echo -e "tublu1224\ntublu1224" | passwd servid'
lxc exec $CONTAINER -- sh -c "curl -s "https://get.sdkman.io" | bash"
lxc exec $CONTAINER -- sh -c "sed -i 's/sdkman_auto_answer=false/sdkman_auto_answer=true/g' ~/.sdkman/etc/config"
lxc exec $CONTAINER -- sh -c "sed -i 's/sdkman_auto_selfupdate=false/sdkman_auto_selfupdate=true/g' ~/.sdkman/etc/config"
lxc exec $CONTAINER -- sdk install java 16.0.1.j9-adpt
lxc exec $CONTAINER -- sdk install java 11.0.11.j9-adpt
lxc exec $CONTAINER -- sdk install java 8.0.292.j9-adpt
lxc exec $CONTAINER -- sdk install maven 3.8.1
lxc exec $CONTAINER -- sdk install gradle 7.1
lxc exec $CONTAINER -- sdk default java 11.0.11.j9-adpt

sdk default java 11.0.11.j9-adpt
lxc publish domain-master --alias java-devserver
lxc image ls -clda 
lxc publish domain-master --alias java-devserver
