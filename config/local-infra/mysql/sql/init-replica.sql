
-- 유저 생성
create user 'nsc_admin_service_rp'@'%' identified by 'adminjackpot123!@#';
create user 'nsc_meta_service_rp'@'%' identified by 'metajackpot123!@#';
create user 'nsc_slot_service_rp'@'%' identified by 'slotjackpot123!@#';
create user 'nsc_shop_service_rp'@'%' identified by 'shopjackpot123!@#';
create user 'nsc_user_service_rp'@'%' identified by 'userjackpot123!@#';
create user 'nsc_product_service_rp'@'%' identified by 'productjackpot123!@#';
create user 'nsc_scheduler_service_rp'@'%' identified by 'dqbTwQV5JeQRH8Cw8D5r';


CREATE USER 'nsc_dataflow_service_rp'@'%' IDENTIFIED BY 'dataflowjackpot123!@#';
CREATE USER 'nsc_dataflow_service_rp'@'localhost' IDENTIFIED BY 'dataflowjackpot123!@#';
CREATE USER 'nsc_logging_service_rp'@'%' IDENTIFIED BY 'loggingjackpot123!@#';
CREATE USER 'nsc_logging_service_rp'@'localhost' IDENTIFIED BY 'loggingjackpot123!@#';


-- 권한
grant all privileges on admin.* to 'nsc_admin_service_rp'@'%';
grant all privileges on meta.* to 'nsc_meta_service_rp'@'%';
grant all privileges on slot.* to 'nsc_slot_service_rp'@'%';
grant all privileges on shop.* to 'nsc_shop_service_rp'@'%';
grant all privileges on user.* to 'nsc_user_service_rp'@'%';
grant all privileges on product.* to 'nsc_product_service_rp'@'%';
grant all privileges on scheduler.* to 'nsc_scheduler_service_rp'@'%';

GRANT ALL PRIVILEGES ON dataflow.* TO 'nsc_dataflow_service_rp'@'%';
GRANT ALL PRIVILEGES ON dataflow.* TO 'nsc_dataflow_service_rp'@'localhost';
GRANT ALL PRIVILEGES ON logging.* TO 'nsc_logging_service_rp'@'%';
GRANT ALL PRIVILEGES ON logging.* TO 'nsc_logging_service_rp'@'localhost';


flush privileges;

