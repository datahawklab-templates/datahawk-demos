#!/bin/sh

#if [ -z "$1" ];then echo "requires a remote repo to clone"; exit 1; fi

PROJECT_REPO=$(echo $1 | awk -F/ '{print $NF}' | awk -F. '{print $1}')
if [ -z "$PROJECT_REPO" ];then echo "local PROJECT_REPO is null"; exit 1; fi

LOCAL_REPO=$(echo $1 | awk -F/ '{print $NF}' | awk -F. '{print $1}')
if [ -z "$LOCAL_REPO" ];then echo "local directory is null"; exit 1; fi

echo $LOCAL_REPO
if [[ -d $LOCAL_REPO ]] ;then echo "local directory already exists"; exit 1;fi

#git clone $1 || echo "error cloning $1"; exit 1

cd $LOCAL_REPO || echo "error cding into $LOCAL_REPO"; exit 1

for branch in `git branch -a | grep remotes | grep -v HEAD | grep -v master `; do git branch --track ${branch#remotes/origin/} $branch; done
for branch in `git branch -a | grep remotes | grep -v HEAD | grep -v master `; do git branch --track ${branch#remotes/origin/} $branch; done
git worktree add --checkout ../httpd httpd


echo "https://gitlab.pasteur.fr/Openshift_public/docker_images.git" | awk -F/ '{print $NF}' | awk -F. '{print $1}'


echo "https://gitlab.pasteur.fr/Openshift_public/docker_images.git" | awk -F/ '{print $(NF-1)}'


git worktree add ../httpd httpd
