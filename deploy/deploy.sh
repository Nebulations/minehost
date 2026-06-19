#!/bin/bash

# This build script automatically builds artifacts and the docker images required for
# starting lobbies, proxies and other things MineHost might require.

echo "Starting deployment"
echo "Current working directory: "
pwd

echo "==== Building artifacts ===="
mvn clean package

echo "==== Moving artifacts ===="
mv plugins/lobby-plugin/target/lobby-plugin-1.0.0.jar templates/lobby/plugins/MineHost-Lobby.jar
mv plugins/velocity-plugin/target/velocity-plugin-1.0.0.jar templates/proxy/plugins/MineHost-Velocity.jar

echo "==== Building docker images ===="
echo "Building proxy"
docker build -t minehost-proxy templates/proxy
echo "Building lobby"
docker build -t minehost-lobby templates/lobby

echo "==== Updating docker containers ===="
# This is temporary but force stop all containers and remove them
docker stop $(docker ps -a -q)
docker rm $(docker ps -a -q)

# Start the proxy
docker run -d --name minehost-proxy --network minehost -p 25565:25565 minehost-proxy

# Start lobby 1
docker run -d --name lobby-1 --network minehost -p 30000:25565 minehost-lobby

echo "==== Cleaning up files ===="
# TODO: Delete files
cd ..
rm code.zip # Gets deleted in the first script but just to make sure
rm -rf minehost-main

echo "Done."