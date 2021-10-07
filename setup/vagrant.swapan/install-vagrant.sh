#/bin/bash

HASH_KRB5="c9ac833889fe2e83777faa751c5b603adb13a5ee3528e6d60c7ec93bd2f0bcd9"
HASH_LIBSSH="15f72a51a397f89e05336cfafc9fe7c625222126287fbf41ea90916f717514d2"
HASH_VAGRANT="be06ce2fa17ad45cdb2fa3c92054194a48b49f46c26ecc2aa1ff928cf861090a"

sudo dnf -y groupinstall "Development Tools"
sudo dnf -y install flex bison gcc gcc-c++ libguestfs-tools-c libvirt libvirt-devel libxml2-devel libxslt-devel make ruby-devel rpm-build openssl-devel cmake

curl -o vagrant_2.2.14_x86_64.rpm https://releases.hashicorp.com/vagrant/2.2.14/vagrant_2.2.14_x86_64.rpm
printf "${HASH_VAGRANT}  vagrant_2.2.14_x86_64.rpm\n" | sha256sum --check --quiet --status
[ $? != 0 ] && printf "\nVagrant download failed.\n\n" && exit 1
sudo dnf -y install vagrant_2.2.14_x86_64.rpm

mkdir libssh && cd libssh
curl -o libssh-0.9.4-2.el8.src.rpm https://vault.centos.org/8.3.2011/BaseOS/Source/SPackages/libssh-0.9.4-2.el8.src.rpm
printf "${HASH_LIBSSH}  libssh-0.9.4-2.el8.src.rpm\n" | sha256sum --check --quiet --status
[ $? != 0 ] && printf "\nThe libbssh source download failed.\n\n" && exit 1
rpm2cpio libssh-0.9.4-2.el8.src.rpm | cpio -imdV
tar xf libssh-0.9.4.tar.xz
mkdir build && cd build
sed -i 's/WITH_GSSAPI "Build with GSSAPI support" ON/WITH_GSSAPI "Build with GSSAPI support" OFF/' ../libssh-0.9.4/DefineOptions.cmake
cmake ../libssh-0.9.4 -DOPENSSL_ROOT_DIR=/opt/vagrant/embedded/ && make
sudo cp lib/libssh* /opt/vagrant/embedded/lib64/
cd ../../

mkdir krb5 && cd krb5
curl -o krb5-1.18.2-5.el8.src.rpm https://vault.centos.org/8.3.2011/BaseOS/Source/SPackages/krb5-1.18.2-5.el8.src.rpm
printf "${HASH_KRB5}  krb5-1.18.2-5.el8.src.rpm\n" | sha256sum --check --quiet --status
[ $? != 0 ] && printf "\nThe libbssh source download failed.\n\n" && exit 1
rpm2cpio krb5-1.18.2-5.el8.src.rpm | cpio -imdV
tar xf krb5-1.18.2.tar.gz
cd krb5-1.18.2/src
LDFLAGS='-L/opt/vagrant/embedded/' ./configure && make -j4
sudo cp -a lib/crypto/libk5crypto.* /opt/vagrant/embedded/lib64/
cd ../../../

rm -rf ~/.gem/ ~/.vagrant.d/ ; vagrant plugin install vagrant-libvirt

# Delete the downloaded RPM file, and remove build directories.
rm --force --recursive krb5 libssh vagrant_2.2.14_x86_64.rpm
