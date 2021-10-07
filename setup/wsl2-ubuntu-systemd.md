

### in your system bios turn on virtualization
*   on my machine i had to reboot, hit escape repeatedly to get into the bios to enable virtualization support


### in Windows turn on lookup "Turn Windows features on and off"
enable Virtual Machine platform and Windows Subsystem for Linux
![image](https://user-images.githubusercontent.com/81254968/122628834-993c6800-d086-11eb-9d5a-7392bff4d3cb.png)
### 

###  


### install the dontnet 5.0 sdk and runtime for linux Ubuntu 20.04
```bash
wget https://packages.microsoft.com/config/ubuntu/21.04/packages-microsoft-prod.deb -O packages-microsoft-prod.deb
sudo dpkg -i packages-microsoft-prod.deb
```

```bash
sudo apt-get update; \
  sudo apt-get install -y apt-transport-https && \
  sudo apt-get update && \
  sudo apt-get install -y dotnet-sdk-5.0
```

```bash
sudo apt-get update; \
  sudo apt-get install -y apt-transport-https && \
  sudo apt-get update && \
  sudo apt-get install -y aspnetcore-runtime-5.0
```

### install wsl trans debian

```bash
sudo -s && \
apt install apt-transport-https && \
wget -O /etc/apt/trusted.gpg.d/wsl-transdebian.gpg https://arkane-systems.github.io/wsl-transdebian/apt/wsl-transdebian.gpg && \
chmod a+r /etc/apt/trusted.gpg.d/wsl-transdebian.gpg && \
cat << EOF > /etc/apt/sources.list.d/wsl-transdebian.list && \
deb https://arkane-systems.github.io/wsl-transdebian/apt/ $(lsb_release -cs) main && \
deb-src https://arkane-systems.github.io/wsl-transdebian/apt/ $(lsb_release -cs) main && \
EOF && \
apt update && \
```

### nstall Genie

```bash
sudo apt update && \
sudo apt install -y systemd-genie
```