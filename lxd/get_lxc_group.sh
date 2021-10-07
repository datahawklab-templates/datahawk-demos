#!/usr/bin/sh

GROUP_PREFIX=$1

lxc list | grep $GROUP_PREFIX | awk -F"|" '{print $2 $4}' | awk -F"(" '{print $1 $2}' | awk '{print $1,$2}'