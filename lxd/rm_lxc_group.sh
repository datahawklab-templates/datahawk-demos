#!/usr/bin/sh
GROUP_PREFIX=$1

GROUP_PREFIX=$1
for server in `lxc list | grep $GROUP_PREFIX | awk -F"|" '{print $2}'`
do
    echo "deleting $server"
    `lxc delete $server --force`
done