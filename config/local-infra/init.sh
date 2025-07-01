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

# redis directory
chmod -R 755 ./redis/config/node-1/*.conf
chmod -R 755 ./redis/config/node-2/*.conf
chmod -R 755 ./redis/config/node-3/*.conf

mkdir -p ./redis/data/node-1
mkdir -p ./redis/data/node-2
mkdir -p ./redis/data/node-3

sudo chmod -R 777 ./redis/data/node-1
sudo chmod -R 777 ./redis/data/node-2
sudo chmod -R 777 ./redis/data/node-3

# mysql directory
mkdir -p ./mysql/data/master
mkdir -p ./mysql/data/replica

sudo chmod -R 777 ./mysql/data/master
sudo chmod -R 777 ./mysql/data/replica

chmod -R 755 ./mysql/m_conf/*.cnf
chmod -R 755 ./mysql/r_conf/*.cnf

## docker compose 실행
docker_compose up -d
echo "sleep 20"
sleep 20


# replica 계정 생성
docker exec -it mysql-master mysql -uroot -proot -e " \
CREATE USER 'replica'@'%' IDENTIFIED WITH mysql_native_password BY 'replica'; \
GRANT REPLICATION SLAVE ON *.* TO 'replica'@'%'; \
FLUSH PRIVILEGES;"

# replica 계정 생성 확인
docker exec -it mysql-master mysql -uroot -proot -Dmysql -e "select user,host from user;"

# master 설정 조회
MASTER_FILE=$(docker exec -it mysql-master mysql -uroot -proot -Ne "SHOW MASTER STATUS;" | awk 'NR==3 {print $2}' | cut -f1 | tr -d '\n')
MASTER_POSITION=$(docker exec -it mysql-master mysql -uroot -proot -Ne "SHOW MASTER STATUS;" | awk 'NR==3 {print $4}' | cut -f1 | tr -d '\n')
echo "MASTER FILE: ${MASTER_FILE}, MASTER POSITION: ${MASTER_POSITION}"

# slave 설정
docker exec -it mysql-replica mysql -uroot -proot -e " \
STOP SLAVE; \
\
CHANGE MASTER TO \
MASTER_HOST='mysql-master', \
MASTER_PORT=3306, \
MASTER_USER='replica', \
MASTER_PASSWORD='replica', \
MASTER_LOG_FILE='${MASTER_FILE}', \
MASTER_LOG_POS=${MASTER_POSITION}, \
MASTER_CONNECT_RETRY=10; \
\
START SLAVE; \
\
SHOW SLAVE STATUS; \
"

# master db 생성 script 복사 및 실행
docker cp ./mysql/sql/init-master.sql mysql-master:/tmp/init-master.sql
docker exec -it mysql-master mysql -uroot -proot -Dmysql -e "source /tmp/init-master.sql"

# replica db 생성 script 복사 및 실행
docker cp ./mysql/sql/init-replica.sql mysql-replica:/tmp/init-replica.sql
docker exec -it mysql-replica mysql -uroot -proot -Dmysql -e "source /tmp/init-replica.sql"



###############################
# monitoring 용 계정 생성
docker exec -it mysql-master mysql -uroot -proot -e " \
CREATE USER 'monitor'@'%' IDENTIFIED BY 'monitor';
GRANT SELECT on sys.* to 'monitor'@'%';
FLUSH PRIVILEGES;"

echo "mysql done.."
echo "sleep 5s"
sleep 5


###############################
## redis
docker exec -it redis-node-1 redis-cli --cluster create \
  127.0.0.1:6379 \
  127.0.0.1:6380 \
  127.0.0.1:6381 \
  --cluster-replicas 0 \
  --cluster-yes
echo "redis done.."


###############################
## rabbitmq
docker exec rabbitmq rabbitmq-plugins enable rabbitmq_management
docker exec rabbitmq rabbitmqctl add_user user_nsc_app nscjackpot123\!\@\#
docker exec rabbitmq rabbitmqctl set_user_tags user_nsc_app administrator
docker exec rabbitmq rabbitmqctl set_permissions -p / user_nsc_app ".*" ".*" ".*"


echo "rabbitmq done.."
