#!/bin/bash

# This build script automatically builds artifacts and the docker images required for
# starting lobbies, proxies and other things MineHost might require.

echo "Starting deployment"
echo "Current working directory: "
pwd

echo "Building artifacts"
mvn clean package

echo "Moving artifacts"
mv plugins/lobby-plugin/target/lobby-plugin-1.0.0.jar templates/lobby/plugins/MineHost-Lobby.jar
mv plugins/velocity-plugin/target/velocity-plugin-1.0.0.jar templates/proxy/plugins/MineHost-Velocity.jar

echo "Building docker images"
# TODO: Build docker images

echo "Cleaning up files"
# TODO: Delete files

echo "Done."