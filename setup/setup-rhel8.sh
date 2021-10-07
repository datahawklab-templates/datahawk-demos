#!/usr/bin/sh

sudo setenforce 0 && \
sudo systemctl stop firewalld ;\
sudo systemctl disable firewalld ;\
sudo sed -i 's/^SELINUX=.*/SELINUX=disabled/g' /etc/selinux/config && \
cat /etc/selinux/config | grep SELINUX=disabled | grep -v ^# && \
yum -y install https://dl.fedoraproject.org/pub/epel/epel-release-latest-8.noarch.rpm && \
sudo dnf install nss-mdns && \
sudo systemctl enable avahi-daemon && \
sudo systemctl start avahi-daemon && \
sudo sudo systemctl disable systemd-resolved && \
sudo systemctl stop systemd-resolved && \
sudo yum install stubby && \
sudo systemctl enable stubby && \
sudo systemctl start stubby && \
echo "nameserver 127.0.0.1" | sudo tee /etc/resolv.conf && \
sudo chattr +i /etc/resolv.conf && \
sudo shutdown -r now