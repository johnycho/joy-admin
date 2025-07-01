#!/bin/bash
set -x

# docker compose 명령어 함수 정의
docker_compose() {
  if command -v docker-compose &> /dev/null; then
    docker-compose "$@"
  else
    docker compose "$@"
  fi
}

# docker rm -f $(docker ps -aq)

docker_compose down

sudo rm -rf ./mysql/data/master
sudo rm -rf ./mysql/data/replica

sudo rm -rf ./redis/data/node-1
sudo rm -rf ./redis/data/node-2
sudo rm -rf ./redis/data/node-3

docker ps
