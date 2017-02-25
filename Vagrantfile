# -*- mode: ruby -*-
# vi: set ft=ruby :

Vagrant.configure(2) do |config|
  config.vm.box = "debian/testing64"
  config.vm.provision :shell, path: "scripts/install-deps-debian.sh"
  config.vm.provision "shell", privileged: true, inline: <<-SHELL
        apt-get install -y curl make
        curl -sSL https://get.docker.com/ | sh
        adduser vagrant docker
	make -C /vagrant es
        su - vagrant -c "make -C /vagrant build"
  SHELL
end
