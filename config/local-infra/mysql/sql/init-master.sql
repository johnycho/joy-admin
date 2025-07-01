
-- mysql 콘솔에서 데이터베이스 생성
CREATE DATABASE admin;
CREATE DATABASE meta;
CREATE DATABASE slot;
CREATE DATABASE shop;
CREATE DATABASE user;
CREATE DATABASE product;
CREATE DATABASE scheduler;

CREATE DATABASE IF NOT EXISTS logging;
CREATE DATABASE IF NOT EXISTS dataflow;

-- mysql 콘솔에서 mysql 유저 생성
create user 'nsc_admin_service'@'%' identified by 'adminjackpot123!@#';
create user 'nsc_meta_service'@'%' identified by 'metajackpot123!@#';
create user 'nsc_slot_service'@'%' identified by 'slotjackpot123!@#';
create user 'nsc_shop_service'@'%' identified by 'shopjackpot123!@#';
create user 'nsc_user_service'@'%' identified by 'userjackpot123!@#';
create user 'nsc_product_service'@'%' identified by 'productjackpot123!@#';
create user 'nsc_product_svc'@'%' identified by 'productjackpot123!@#';
create user 'nsc_scheduler_service'@'%' identified by 'dqbTwQV5JeQRH8Cw8D5r';

CREATE USER 'nsc_dataflow_service'@'%' IDENTIFIED BY 'dataflowjackpot123!@#';
CREATE USER 'nsc_dataflow_service'@'localhost' IDENTIFIED BY 'dataflowjackpot123!@#';
CREATE USER 'nsc_logging_service'@'%' IDENTIFIED BY 'loggingjackpot123!@#';
CREATE USER 'nsc_logging_service'@'localhost' IDENTIFIED BY 'loggingjackpot123!@#';


-- mysql 유저에게 각 데이터베이스에 대한 권한 부여.
grant all privileges on admin.* to 'nsc_admin_service'@'%';
grant all privileges on meta.* to 'nsc_meta_service'@'%';
grant all privileges on slot.* to 'nsc_slot_service'@'%';
grant all privileges on shop.* to 'nsc_shop_service'@'%';
grant all privileges on user.* to 'nsc_user_service'@'%';
grant all privileges on product.* to 'nsc_product_service'@'%';
grant all privileges on product.* to 'nsc_product_svc'@'%';
grant all privileges on scheduler.* to 'nsc_scheduler_service'@'%';


GRANT ALL PRIVILEGES ON dataflow.* TO 'nsc_dataflow_service'@'%';
GRANT ALL PRIVILEGES ON dataflow.* TO 'nsc_dataflow_service'@'localhost';
GRANT ALL PRIVILEGES ON logging.* TO 'nsc_logging_service'@'%';
GRANT ALL PRIVILEGES ON logging.* TO 'nsc_logging_service'@'localhost';

flush privileges;


-- -------------------------------------------
-- scheduler table 생성
-- quartz
USE scheduler;

DROP TABLE IF EXISTS QRTZ_FIRED_TRIGGERS;
DROP TABLE IF EXISTS QRTZ_PAUSED_TRIGGER_GRPS;
DROP TABLE IF EXISTS QRTZ_SCHEDULER_STATE;
DROP TABLE IF EXISTS QRTZ_LOCKS;
DROP TABLE IF EXISTS QRTZ_SIMPLE_TRIGGERS;
DROP TABLE IF EXISTS QRTZ_SIMPROP_TRIGGERS;
DROP TABLE IF EXISTS QRTZ_CRON_TRIGGERS;
DROP TABLE IF EXISTS QRTZ_BLOB_TRIGGERS;
DROP TABLE IF EXISTS QRTZ_TRIGGERS;
DROP TABLE IF EXISTS QRTZ_JOB_DETAILS;
DROP TABLE IF EXISTS QRTZ_CALENDARS;


CREATE TABLE QRTZ_JOB_DETAILS
(
    SCHED_NAME        VARCHAR(120) NOT NULL,
    JOB_NAME          VARCHAR(200) NOT NULL,
    JOB_GROUP         VARCHAR(200) NOT NULL,
    DESCRIPTION       VARCHAR(250) NULL,
    JOB_CLASS_NAME    VARCHAR(250) NOT NULL,
    IS_DURABLE        VARCHAR(1)   NOT NULL,
    IS_NONCONCURRENT  VARCHAR(1)   NOT NULL,
    IS_UPDATE_DATA    VARCHAR(1)   NOT NULL,
    REQUESTS_RECOVERY VARCHAR(1)   NOT NULL,
    JOB_DATA          BLOB NULL,
    PRIMARY KEY (SCHED_NAME, JOB_NAME, JOB_GROUP)
);

CREATE TABLE QRTZ_TRIGGERS
(
    SCHED_NAME     VARCHAR(120) NOT NULL,
    TRIGGER_NAME   VARCHAR(200) NOT NULL,
    TRIGGER_GROUP  VARCHAR(200) NOT NULL,
    JOB_NAME       VARCHAR(200) NOT NULL,
    JOB_GROUP      VARCHAR(200) NOT NULL,
    DESCRIPTION    VARCHAR(250) NULL,
    NEXT_FIRE_TIME BIGINT(13) NULL,
    PREV_FIRE_TIME BIGINT(13) NULL,
    PRIORITY       INTEGER NULL,
    TRIGGER_STATE  VARCHAR(16)  NOT NULL,
    TRIGGER_TYPE   VARCHAR(8)   NOT NULL,
    START_TIME     BIGINT(13) NOT NULL,
    END_TIME       BIGINT(13) NULL,
    CALENDAR_NAME  VARCHAR(200) NULL,
    MISFIRE_INSTR  SMALLINT(2) NULL,
    JOB_DATA       BLOB NULL,
    PRIMARY KEY (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP),
    FOREIGN KEY (SCHED_NAME, JOB_NAME, JOB_GROUP)
        REFERENCES QRTZ_JOB_DETAILS (SCHED_NAME, JOB_NAME, JOB_GROUP)
);

CREATE TABLE QRTZ_SIMPLE_TRIGGERS
(
    SCHED_NAME      VARCHAR(120) NOT NULL,
    TRIGGER_NAME    VARCHAR(200) NOT NULL,
    TRIGGER_GROUP   VARCHAR(200) NOT NULL,
    REPEAT_COUNT    BIGINT(7) NOT NULL,
    REPEAT_INTERVAL BIGINT(12) NOT NULL,
    TIMES_TRIGGERED BIGINT(10) NOT NULL,
    PRIMARY KEY (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP),
    FOREIGN KEY (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP)
        REFERENCES QRTZ_TRIGGERS (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP)
);

CREATE TABLE QRTZ_CRON_TRIGGERS
(
    SCHED_NAME      VARCHAR(120) NOT NULL,
    TRIGGER_NAME    VARCHAR(200) NOT NULL,
    TRIGGER_GROUP   VARCHAR(200) NOT NULL,
    CRON_EXPRESSION VARCHAR(200) NOT NULL,
    TIME_ZONE_ID    VARCHAR(80),
    PRIMARY KEY (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP),
    FOREIGN KEY (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP)
        REFERENCES QRTZ_TRIGGERS (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP)
);

CREATE TABLE QRTZ_SIMPROP_TRIGGERS
(
    SCHED_NAME    VARCHAR(120) NOT NULL,
    TRIGGER_NAME  VARCHAR(200) NOT NULL,
    TRIGGER_GROUP VARCHAR(200) NOT NULL,
    STR_PROP_1    VARCHAR(512) NULL,
    STR_PROP_2    VARCHAR(512) NULL,
    STR_PROP_3    VARCHAR(512) NULL,
    INT_PROP_1    INT NULL,
    INT_PROP_2    INT NULL,
    LONG_PROP_1   BIGINT NULL,
    LONG_PROP_2   BIGINT NULL,
    DEC_PROP_1    NUMERIC(13, 4) NULL,
    DEC_PROP_2    NUMERIC(13, 4) NULL,
    BOOL_PROP_1   VARCHAR(1) NULL,
    BOOL_PROP_2   VARCHAR(1) NULL,
    PRIMARY KEY (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP),
    FOREIGN KEY (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP)
        REFERENCES QRTZ_TRIGGERS (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP)
);

CREATE TABLE QRTZ_BLOB_TRIGGERS
(
    SCHED_NAME    VARCHAR(120) NOT NULL,
    TRIGGER_NAME  VARCHAR(200) NOT NULL,
    TRIGGER_GROUP VARCHAR(200) NOT NULL,
    BLOB_DATA     BLOB NULL,
    PRIMARY KEY (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP),
    FOREIGN KEY (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP)
        REFERENCES QRTZ_TRIGGERS (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP)
);

CREATE TABLE QRTZ_CALENDARS
(
    SCHED_NAME    VARCHAR(120) NOT NULL,
    CALENDAR_NAME VARCHAR(200) NOT NULL,
    CALENDAR      BLOB         NOT NULL,
    PRIMARY KEY (SCHED_NAME, CALENDAR_NAME)
);

CREATE TABLE QRTZ_PAUSED_TRIGGER_GRPS
(
    SCHED_NAME    VARCHAR(120) NOT NULL,
    TRIGGER_GROUP VARCHAR(200) NOT NULL,
    PRIMARY KEY (SCHED_NAME, TRIGGER_GROUP)
);

CREATE TABLE QRTZ_FIRED_TRIGGERS
(
    SCHED_NAME        VARCHAR(120) NOT NULL,
    ENTRY_ID          VARCHAR(95)  NOT NULL,
    TRIGGER_NAME      VARCHAR(200) NOT NULL,
    TRIGGER_GROUP     VARCHAR(200) NOT NULL,
    INSTANCE_NAME     VARCHAR(200) NOT NULL,
    FIRED_TIME        BIGINT(13) NOT NULL,
    SCHED_TIME        BIGINT(13) NOT NULL,
    PRIORITY          INTEGER      NOT NULL,
    STATE             VARCHAR(16)  NOT NULL,
    JOB_NAME          VARCHAR(200) NULL,
    JOB_GROUP         VARCHAR(200) NULL,
    IS_NONCONCURRENT  VARCHAR(1) NULL,
    REQUESTS_RECOVERY VARCHAR(1) NULL,
    PRIMARY KEY (SCHED_NAME, ENTRY_ID)
);

CREATE TABLE QRTZ_SCHEDULER_STATE
(
    SCHED_NAME        VARCHAR(120) NOT NULL,
    INSTANCE_NAME     VARCHAR(200) NOT NULL,
    LAST_CHECKIN_TIME BIGINT(13) NOT NULL,
    CHECKIN_INTERVAL  BIGINT(13) NOT NULL,
    PRIMARY KEY (SCHED_NAME, INSTANCE_NAME)
);

CREATE TABLE QRTZ_LOCKS
(
    SCHED_NAME VARCHAR(120) NOT NULL,
    LOCK_NAME  VARCHAR(40)  NOT NULL,
    PRIMARY KEY (SCHED_NAME, LOCK_NAME)
);




-- -------------------------------------------
-- dataflow table 생성
USE dataflow;


DROP TABLE IF EXISTS QRTZ_FIRED_TRIGGERS;
DROP TABLE IF EXISTS QRTZ_PAUSED_TRIGGER_GRPS;
DROP TABLE IF EXISTS QRTZ_SCHEDULER_STATE;
DROP TABLE IF EXISTS QRTZ_LOCKS;
DROP TABLE IF EXISTS QRTZ_SIMPLE_TRIGGERS;
DROP TABLE IF EXISTS QRTZ_SIMPROP_TRIGGERS;
DROP TABLE IF EXISTS QRTZ_CRON_TRIGGERS;
DROP TABLE IF EXISTS QRTZ_BLOB_TRIGGERS;
DROP TABLE IF EXISTS QRTZ_TRIGGERS;
DROP TABLE IF EXISTS QRTZ_JOB_DETAILS;
DROP TABLE IF EXISTS QRTZ_CALENDARS;


CREATE TABLE QRTZ_JOB_DETAILS
(
    SCHED_NAME        VARCHAR(120) NOT NULL,
    JOB_NAME          VARCHAR(200) NOT NULL,
    JOB_GROUP         VARCHAR(200) NOT NULL,
    DESCRIPTION       VARCHAR(250) NULL,
    JOB_CLASS_NAME    VARCHAR(250) NOT NULL,
    IS_DURABLE        VARCHAR(1)   NOT NULL,
    IS_NONCONCURRENT  VARCHAR(1)   NOT NULL,
    IS_UPDATE_DATA    VARCHAR(1)   NOT NULL,
    REQUESTS_RECOVERY VARCHAR(1)   NOT NULL,
    JOB_DATA          BLOB NULL,
    PRIMARY KEY (SCHED_NAME, JOB_NAME, JOB_GROUP)
);

CREATE TABLE QRTZ_TRIGGERS
(
    SCHED_NAME     VARCHAR(120) NOT NULL,
    TRIGGER_NAME   VARCHAR(200) NOT NULL,
    TRIGGER_GROUP  VARCHAR(200) NOT NULL,
    JOB_NAME       VARCHAR(200) NOT NULL,
    JOB_GROUP      VARCHAR(200) NOT NULL,
    DESCRIPTION    VARCHAR(250) NULL,
    NEXT_FIRE_TIME BIGINT(13) NULL,
    PREV_FIRE_TIME BIGINT(13) NULL,
    PRIORITY       INTEGER NULL,
    TRIGGER_STATE  VARCHAR(16)  NOT NULL,
    TRIGGER_TYPE   VARCHAR(8)   NOT NULL,
    START_TIME     BIGINT(13) NOT NULL,
    END_TIME       BIGINT(13) NULL,
    CALENDAR_NAME  VARCHAR(200) NULL,
    MISFIRE_INSTR  SMALLINT(2) NULL,
    JOB_DATA       BLOB NULL,
    PRIMARY KEY (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP),
    FOREIGN KEY (SCHED_NAME, JOB_NAME, JOB_GROUP)
        REFERENCES QRTZ_JOB_DETAILS (SCHED_NAME, JOB_NAME, JOB_GROUP)
);

CREATE TABLE QRTZ_SIMPLE_TRIGGERS
(
    SCHED_NAME      VARCHAR(120) NOT NULL,
    TRIGGER_NAME    VARCHAR(200) NOT NULL,
    TRIGGER_GROUP   VARCHAR(200) NOT NULL,
    REPEAT_COUNT    BIGINT(7) NOT NULL,
    REPEAT_INTERVAL BIGINT(12) NOT NULL,
    TIMES_TRIGGERED BIGINT(10) NOT NULL,
    PRIMARY KEY (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP),
    FOREIGN KEY (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP)
        REFERENCES QRTZ_TRIGGERS (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP)
);

CREATE TABLE QRTZ_CRON_TRIGGERS
(
    SCHED_NAME      VARCHAR(120) NOT NULL,
    TRIGGER_NAME    VARCHAR(200) NOT NULL,
    TRIGGER_GROUP   VARCHAR(200) NOT NULL,
    CRON_EXPRESSION VARCHAR(200) NOT NULL,
    TIME_ZONE_ID    VARCHAR(80),
    PRIMARY KEY (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP),
    FOREIGN KEY (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP)
        REFERENCES QRTZ_TRIGGERS (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP)
);

CREATE TABLE QRTZ_SIMPROP_TRIGGERS
(
    SCHED_NAME    VARCHAR(120) NOT NULL,
    TRIGGER_NAME  VARCHAR(200) NOT NULL,
    TRIGGER_GROUP VARCHAR(200) NOT NULL,
    STR_PROP_1    VARCHAR(512) NULL,
    STR_PROP_2    VARCHAR(512) NULL,
    STR_PROP_3    VARCHAR(512) NULL,
    INT_PROP_1    INT NULL,
    INT_PROP_2    INT NULL,
    LONG_PROP_1   BIGINT NULL,
    LONG_PROP_2   BIGINT NULL,
    DEC_PROP_1    NUMERIC(13, 4) NULL,
    DEC_PROP_2    NUMERIC(13, 4) NULL,
    BOOL_PROP_1   VARCHAR(1) NULL,
    BOOL_PROP_2   VARCHAR(1) NULL,
    PRIMARY KEY (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP),
    FOREIGN KEY (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP)
        REFERENCES QRTZ_TRIGGERS (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP)
);

CREATE TABLE QRTZ_BLOB_TRIGGERS
(
    SCHED_NAME    VARCHAR(120) NOT NULL,
    TRIGGER_NAME  VARCHAR(200) NOT NULL,
    TRIGGER_GROUP VARCHAR(200) NOT NULL,
    BLOB_DATA     BLOB NULL,
    PRIMARY KEY (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP),
    FOREIGN KEY (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP)
        REFERENCES QRTZ_TRIGGERS (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP)
);

CREATE TABLE QRTZ_CALENDARS
(
    SCHED_NAME    VARCHAR(120) NOT NULL,
    CALENDAR_NAME VARCHAR(200) NOT NULL,
    CALENDAR      BLOB         NOT NULL,
    PRIMARY KEY (SCHED_NAME, CALENDAR_NAME)
);

CREATE TABLE QRTZ_PAUSED_TRIGGER_GRPS
(
    SCHED_NAME    VARCHAR(120) NOT NULL,
    TRIGGER_GROUP VARCHAR(200) NOT NULL,
    PRIMARY KEY (SCHED_NAME, TRIGGER_GROUP)
);

CREATE TABLE QRTZ_FIRED_TRIGGERS
(
    SCHED_NAME        VARCHAR(120) NOT NULL,
    ENTRY_ID          VARCHAR(95)  NOT NULL,
    TRIGGER_NAME      VARCHAR(200) NOT NULL,
    TRIGGER_GROUP     VARCHAR(200) NOT NULL,
    INSTANCE_NAME     VARCHAR(200) NOT NULL,
    FIRED_TIME        BIGINT(13) NOT NULL,
    SCHED_TIME        BIGINT(13) NOT NULL,
    PRIORITY          INTEGER      NOT NULL,
    STATE             VARCHAR(16)  NOT NULL,
    JOB_NAME          VARCHAR(200) NULL,
    JOB_GROUP         VARCHAR(200) NULL,
    IS_NONCONCURRENT  VARCHAR(1) NULL,
    REQUESTS_RECOVERY VARCHAR(1) NULL,
    PRIMARY KEY (SCHED_NAME, ENTRY_ID)
);

CREATE TABLE QRTZ_SCHEDULER_STATE
(
    SCHED_NAME        VARCHAR(120) NOT NULL,
    INSTANCE_NAME     VARCHAR(200) NOT NULL,
    LAST_CHECKIN_TIME BIGINT(13) NOT NULL,
    CHECKIN_INTERVAL  BIGINT(13) NOT NULL,
    PRIMARY KEY (SCHED_NAME, INSTANCE_NAME)
);

CREATE TABLE QRTZ_LOCKS
(
    SCHED_NAME VARCHAR(120) NOT NULL,
    LOCK_NAME  VARCHAR(40)  NOT NULL,
    PRIMARY KEY (SCHED_NAME, LOCK_NAME)
);







-- -------------------------------------------
-- dataflow table 생성



drop table if exists app_registration;
drop table if exists audit_records;
drop table if exists batch_job_execution_context;
drop table if exists batch_job_execution_params;
drop table if exists batch_job_execution_seq;
drop table if exists batch_job_seq;
drop table if exists batch_step_execution_context;
drop table if exists batch_step_execution;
drop table if exists batch_job_execution;
drop table if exists batch_job_instance;
drop table if exists batch_step_execution_seq;
drop table if exists flyway_schema_history_dataflow;
drop table if exists hibernate_sequence;
drop table if exists stream_definitions;
drop table if exists task_definitions;
drop table if exists task_deployment;
drop table if exists task_execution_metadata;
drop table if exists task_execution_metadata_seq;
drop table if exists task_execution_params;
drop table if exists task_lock;
drop table if exists task_seq;
drop table if exists task_task_batch;
drop table if exists task_execution;

drop table if exists BATCH_JOB_EXECUTION;
drop table if exists BATCH_JOB_EXECUTION_CONTEXT;
drop table if exists BATCH_JOB_EXECUTION_PARAMS;
drop table if exists BATCH_JOB_EXECUTION_SEQ;
drop table if exists BATCH_JOB_INSTANCE;
drop table if exists BATCH_JOB_SEQ;
drop table if exists BATCH_STEP_EXECUTION;
drop table if exists BATCH_STEP_EXECUTION_CONTEXT;
drop table if exists BATCH_STEP_EXECUTION_SEQ;
drop table if exists TASK_SEQ;
drop table if exists TASK_EXECUTION;
drop table if exists TASK_EXECUTION_PARAMS;
drop table if exists TASK_TASK_BATCH;

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `app_registration`
--

DROP TABLE IF EXISTS `app_registration`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `app_registration` (
                                    `id` bigint NOT NULL,
                                    `object_version` bigint DEFAULT NULL,
                                    `default_version` bit(1) DEFAULT NULL,
                                    `metadata_uri` longtext COLLATE utf8mb4_bin,
                                    `name` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
                                    `type` int DEFAULT NULL,
                                    `uri` longtext COLLATE utf8mb4_bin,
                                    `version` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
                                    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `app_registration`
--


--
-- Table structure for table `audit_records`
--

DROP TABLE IF EXISTS `audit_records`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `audit_records` (
                                 `id` bigint NOT NULL,
                                 `audit_action` bigint DEFAULT NULL,
                                 `audit_data` longtext COLLATE utf8mb4_bin,
                                 `audit_operation` bigint DEFAULT NULL,
                                 `correlation_id` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
                                 `created_by` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
                                 `created_on` datetime DEFAULT NULL,
                                 `platform_name` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
                                 PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `audit_records`
--


--
-- Table structure for table `batch_job_execution`
--

DROP TABLE IF EXISTS `batch_job_execution`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `batch_job_execution` (
                                       `JOB_EXECUTION_ID` bigint NOT NULL,
                                       `VERSION` bigint DEFAULT NULL,
                                       `JOB_INSTANCE_ID` bigint NOT NULL,
                                       `CREATE_TIME` datetime NOT NULL,
                                       `START_TIME` datetime DEFAULT NULL,
                                       `END_TIME` datetime DEFAULT NULL,
                                       `STATUS` varchar(10) COLLATE utf8mb4_bin DEFAULT NULL,
                                       `EXIT_CODE` varchar(2500) COLLATE utf8mb4_bin DEFAULT NULL,
                                       `EXIT_MESSAGE` varchar(2500) COLLATE utf8mb4_bin DEFAULT NULL,
                                       `LAST_UPDATED` datetime DEFAULT NULL,
                                       `JOB_CONFIGURATION_LOCATION` varchar(2500) COLLATE utf8mb4_bin DEFAULT NULL,
                                       PRIMARY KEY (`JOB_EXECUTION_ID`),
                                       KEY `JOB_INST_EXEC_FK` (`JOB_INSTANCE_ID`),
                                       CONSTRAINT `JOB_INST_EXEC_FK` FOREIGN KEY (`JOB_INSTANCE_ID`) REFERENCES `batch_job_instance` (`JOB_INSTANCE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `batch_job_execution`
--


--
-- Table structure for table `batch_job_execution_context`
--

DROP TABLE IF EXISTS `batch_job_execution_context`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `batch_job_execution_context` (
                                               `JOB_EXECUTION_ID` bigint NOT NULL,
                                               `SHORT_CONTEXT` varchar(2500) COLLATE utf8mb4_bin NOT NULL,
                                               `SERIALIZED_CONTEXT` text COLLATE utf8mb4_bin,
                                               PRIMARY KEY (`JOB_EXECUTION_ID`),
                                               CONSTRAINT `JOB_EXEC_CTX_FK` FOREIGN KEY (`JOB_EXECUTION_ID`) REFERENCES `batch_job_execution` (`JOB_EXECUTION_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `batch_job_execution_context`
--


--
-- Table structure for table `batch_job_execution_params`
--

DROP TABLE IF EXISTS `batch_job_execution_params`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `batch_job_execution_params` (
                                              `JOB_EXECUTION_ID` bigint NOT NULL,
                                              `TYPE_CD` varchar(6) COLLATE utf8mb4_bin NOT NULL,
                                              `KEY_NAME` varchar(100) COLLATE utf8mb4_bin NOT NULL,
                                              `STRING_VAL` varchar(250) COLLATE utf8mb4_bin DEFAULT NULL,
                                              `DATE_VAL` datetime DEFAULT NULL,
                                              `LONG_VAL` bigint DEFAULT NULL,
                                              `DOUBLE_VAL` double DEFAULT NULL,
                                              `IDENTIFYING` char(1) COLLATE utf8mb4_bin NOT NULL,
                                              KEY `JOB_EXEC_PARAMS_FK` (`JOB_EXECUTION_ID`),
                                              CONSTRAINT `JOB_EXEC_PARAMS_FK` FOREIGN KEY (`JOB_EXECUTION_ID`) REFERENCES `batch_job_execution` (`JOB_EXECUTION_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `batch_job_execution_params`
--


--
-- Table structure for table `batch_job_execution_seq`
--

DROP TABLE IF EXISTS `batch_job_execution_seq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `batch_job_execution_seq` (
                                           `ID` bigint NOT NULL,
                                           `UNIQUE_KEY` char(1) COLLATE utf8mb4_bin NOT NULL,
                                           UNIQUE KEY `UNIQUE_KEY_UN` (`UNIQUE_KEY`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `batch_job_execution_seq`
--

INSERT INTO `batch_job_execution_seq` (`ID`, `UNIQUE_KEY`) VALUES (0,'0');

--
-- Table structure for table `batch_job_instance`
--

DROP TABLE IF EXISTS `batch_job_instance`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `batch_job_instance` (
                                      `JOB_INSTANCE_ID` bigint NOT NULL,
                                      `VERSION` bigint DEFAULT NULL,
                                      `JOB_NAME` varchar(100) COLLATE utf8mb4_bin NOT NULL,
                                      `JOB_KEY` varchar(32) COLLATE utf8mb4_bin NOT NULL,
                                      PRIMARY KEY (`JOB_INSTANCE_ID`),
                                      UNIQUE KEY `JOB_INST_UN` (`JOB_NAME`,`JOB_KEY`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `batch_job_instance`
--


--
-- Table structure for table `batch_job_seq`
--

DROP TABLE IF EXISTS `batch_job_seq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `batch_job_seq` (
                                 `ID` bigint NOT NULL,
                                 `UNIQUE_KEY` char(1) COLLATE utf8mb4_bin NOT NULL,
                                 UNIQUE KEY `UNIQUE_KEY_UN` (`UNIQUE_KEY`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `batch_job_seq`
--

INSERT INTO `batch_job_seq` (`ID`, `UNIQUE_KEY`) VALUES (0,'0');

--
-- Table structure for table `batch_step_execution`
--

DROP TABLE IF EXISTS `batch_step_execution`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `batch_step_execution` (
                                        `STEP_EXECUTION_ID` bigint NOT NULL,
                                        `VERSION` bigint NOT NULL,
                                        `STEP_NAME` varchar(100) COLLATE utf8mb4_bin NOT NULL,
                                        `JOB_EXECUTION_ID` bigint NOT NULL,
                                        `START_TIME` datetime NOT NULL,
                                        `END_TIME` datetime DEFAULT NULL,
                                        `STATUS` varchar(10) COLLATE utf8mb4_bin DEFAULT NULL,
                                        `COMMIT_COUNT` bigint DEFAULT NULL,
                                        `READ_COUNT` bigint DEFAULT NULL,
                                        `FILTER_COUNT` bigint DEFAULT NULL,
                                        `WRITE_COUNT` bigint DEFAULT NULL,
                                        `READ_SKIP_COUNT` bigint DEFAULT NULL,
                                        `WRITE_SKIP_COUNT` bigint DEFAULT NULL,
                                        `PROCESS_SKIP_COUNT` bigint DEFAULT NULL,
                                        `ROLLBACK_COUNT` bigint DEFAULT NULL,
                                        `EXIT_CODE` varchar(2500) COLLATE utf8mb4_bin DEFAULT NULL,
                                        `EXIT_MESSAGE` varchar(2500) COLLATE utf8mb4_bin DEFAULT NULL,
                                        `LAST_UPDATED` datetime DEFAULT NULL,
                                        PRIMARY KEY (`STEP_EXECUTION_ID`),
                                        KEY `JOB_EXEC_STEP_FK` (`JOB_EXECUTION_ID`),
                                        KEY `STEP_NAME_IDX` (`STEP_NAME`),
                                        CONSTRAINT `JOB_EXEC_STEP_FK` FOREIGN KEY (`JOB_EXECUTION_ID`) REFERENCES `batch_job_execution` (`JOB_EXECUTION_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `batch_step_execution`
--


--
-- Table structure for table `batch_step_execution_context`
--

DROP TABLE IF EXISTS `batch_step_execution_context`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `batch_step_execution_context` (
                                                `STEP_EXECUTION_ID` bigint NOT NULL,
                                                `SHORT_CONTEXT` varchar(2500) COLLATE utf8mb4_bin NOT NULL,
                                                `SERIALIZED_CONTEXT` text COLLATE utf8mb4_bin,
                                                PRIMARY KEY (`STEP_EXECUTION_ID`),
                                                CONSTRAINT `STEP_EXEC_CTX_FK` FOREIGN KEY (`STEP_EXECUTION_ID`) REFERENCES `batch_step_execution` (`STEP_EXECUTION_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `batch_step_execution_context`
--


--
-- Table structure for table `batch_step_execution_seq`
--

DROP TABLE IF EXISTS `batch_step_execution_seq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `batch_step_execution_seq` (
                                            `ID` bigint NOT NULL,
                                            `UNIQUE_KEY` char(1) COLLATE utf8mb4_bin NOT NULL,
                                            UNIQUE KEY `UNIQUE_KEY_UN` (`UNIQUE_KEY`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `batch_step_execution_seq`
--

INSERT INTO `batch_step_execution_seq` (`ID`, `UNIQUE_KEY`) VALUES (0,'0');

--
-- Table structure for table `flyway_schema_history_dataflow`
--

DROP TABLE IF EXISTS `flyway_schema_history_dataflow`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `flyway_schema_history_dataflow` (
                                                  `installed_rank` int NOT NULL,
                                                  `version` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL,
                                                  `description` varchar(200) COLLATE utf8mb4_bin NOT NULL,
                                                  `type` varchar(20) COLLATE utf8mb4_bin NOT NULL,
                                                  `script` varchar(1000) COLLATE utf8mb4_bin NOT NULL,
                                                  `checksum` int DEFAULT NULL,
                                                  `installed_by` varchar(100) COLLATE utf8mb4_bin NOT NULL,
                                                  `installed_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                                  `execution_time` int NOT NULL,
                                                  `success` tinyint(1) NOT NULL,
                                                  PRIMARY KEY (`installed_rank`),
                                                  KEY `flyway_schema_history_dataflow_s_idx` (`success`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `flyway_schema_history_dataflow`
--

INSERT INTO `flyway_schema_history_dataflow` (`installed_rank`, `version`, `description`, `type`, `script`, `checksum`, `installed_by`, `installed_on`, `execution_time`, `success`) VALUES (1,'1','Initial Setup','JDBC','org.springframework.cloud.dataflow.server.db.migration.mysql.V1__Initial_Setup',NULL,'nsc_dataflow_service','2023-01-13 07:20:55',500,1),(2,'2','Add Descriptions And OriginalDefinition','JDBC','org.springframework.cloud.dataflow.server.db.migration.mysql.V2__Add_Descriptions_And_OriginalDefinition',NULL,'nsc_dataflow_service','2023-01-13 07:20:56',92,1),(3,'3','Add Platform To AuditRecords','JDBC','org.springframework.cloud.dataflow.server.db.migration.mysql.V3__Add_Platform_To_AuditRecords',NULL,'nsc_dataflow_service','2023-01-13 07:20:56',19,1),(4,'4','Add Step Name Indexes','JDBC','org.springframework.cloud.dataflow.server.db.migration.mysql.V4__Add_Step_Name_Indexes',NULL,'nsc_dataflow_service','2023-01-13 07:20:56',20,1),(5,'5','Add Task Execution Params Indexes','JDBC','org.springframework.cloud.dataflow.server.db.migration.mysql.V5__Add_Task_Execution_Params_Indexes',NULL,'nsc_dataflow_service','2023-01-13 07:20:56',22,1);

--
-- Table structure for table `hibernate_sequence`
--

DROP TABLE IF EXISTS `hibernate_sequence`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hibernate_sequence` (
    `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hibernate_sequence`
--

INSERT INTO `hibernate_sequence` (`next_val`) VALUES (1);

--
-- Table structure for table `stream_definitions`
--

DROP TABLE IF EXISTS `stream_definitions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `stream_definitions` (
                                      `definition_name` varchar(255) COLLATE utf8mb4_bin NOT NULL,
                                      `definition` longtext COLLATE utf8mb4_bin,
                                      `description` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
                                      `original_definition` longtext COLLATE utf8mb4_bin,
                                      PRIMARY KEY (`definition_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `stream_definitions`
--


--
-- Table structure for table `task_definitions`
--

DROP TABLE IF EXISTS `task_definitions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `task_definitions` (
                                    `definition_name` varchar(255) COLLATE utf8mb4_bin NOT NULL,
                                    `definition` longtext COLLATE utf8mb4_bin,
                                    `description` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
                                    PRIMARY KEY (`definition_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `task_definitions`
--


--
-- Table structure for table `task_deployment`
--

DROP TABLE IF EXISTS `task_deployment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `task_deployment` (
                                   `id` bigint NOT NULL,
                                   `object_version` bigint DEFAULT NULL,
                                   `task_deployment_id` varchar(255) COLLATE utf8mb4_bin NOT NULL,
                                   `task_definition_name` varchar(255) COLLATE utf8mb4_bin NOT NULL,
                                   `platform_name` varchar(255) COLLATE utf8mb4_bin NOT NULL,
                                   `created_on` datetime DEFAULT NULL,
                                   PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `task_deployment`
--


--
-- Table structure for table `task_execution`
--

DROP TABLE IF EXISTS `task_execution`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `task_execution` (
                                  `TASK_EXECUTION_ID` bigint NOT NULL,
                                  `START_TIME` datetime DEFAULT NULL,
                                  `END_TIME` datetime DEFAULT NULL,
                                  `TASK_NAME` varchar(100) COLLATE utf8mb4_bin DEFAULT NULL,
                                  `EXIT_CODE` int DEFAULT NULL,
                                  `EXIT_MESSAGE` varchar(2500) COLLATE utf8mb4_bin DEFAULT NULL,
                                  `ERROR_MESSAGE` varchar(2500) COLLATE utf8mb4_bin DEFAULT NULL,
                                  `LAST_UPDATED` timestamp NULL DEFAULT NULL,
                                  `EXTERNAL_EXECUTION_ID` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
                                  `PARENT_EXECUTION_ID` bigint DEFAULT NULL,
                                  PRIMARY KEY (`TASK_EXECUTION_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `task_execution`
--


--
-- Table structure for table `task_execution_metadata`
--

DROP TABLE IF EXISTS `task_execution_metadata`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `task_execution_metadata` (
                                           `id` bigint NOT NULL,
                                           `task_execution_id` bigint NOT NULL,
                                           `task_execution_manifest` longtext COLLATE utf8mb4_bin,
                                           PRIMARY KEY (`id`),
                                           KEY `TASK_METADATA_FK` (`task_execution_id`),
                                           CONSTRAINT `TASK_METADATA_FK` FOREIGN KEY (`task_execution_id`) REFERENCES `task_execution` (`TASK_EXECUTION_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `task_execution_metadata`
--


--
-- Table structure for table `task_execution_metadata_seq`
--

DROP TABLE IF EXISTS `task_execution_metadata_seq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `task_execution_metadata_seq` (
                                               `ID` bigint NOT NULL,
                                               `UNIQUE_KEY` char(1) COLLATE utf8mb4_bin NOT NULL,
                                               UNIQUE KEY `UNIQUE_KEY_UN` (`UNIQUE_KEY`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `task_execution_metadata_seq`
--

INSERT INTO `task_execution_metadata_seq` (`ID`, `UNIQUE_KEY`) VALUES (0,'0');

--
-- Table structure for table `task_execution_params`
--

DROP TABLE IF EXISTS `task_execution_params`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `task_execution_params` (
                                         `TASK_EXECUTION_ID` bigint NOT NULL,
                                         `TASK_PARAM` varchar(2500) COLLATE utf8mb4_bin DEFAULT NULL,
                                         KEY `TASK_EXECUTION_ID_IDX` (`TASK_EXECUTION_ID`),
                                         CONSTRAINT `TASK_EXEC_PARAMS_FK` FOREIGN KEY (`TASK_EXECUTION_ID`) REFERENCES `task_execution` (`TASK_EXECUTION_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `task_execution_params`
--


--
-- Table structure for table `task_lock`
--

DROP TABLE IF EXISTS `task_lock`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `task_lock` (
                             `LOCK_KEY` char(36) COLLATE utf8mb4_bin NOT NULL,
                             `REGION` varchar(100) COLLATE utf8mb4_bin NOT NULL,
                             `CLIENT_ID` char(36) COLLATE utf8mb4_bin DEFAULT NULL,
                             `CREATED_DATE` datetime(6) NOT NULL,
                             PRIMARY KEY (`LOCK_KEY`,`REGION`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `task_lock`
--


--
-- Table structure for table `task_seq`
--

DROP TABLE IF EXISTS `task_seq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `task_seq` (
                            `ID` bigint NOT NULL,
                            `UNIQUE_KEY` char(1) COLLATE utf8mb4_bin NOT NULL,
                            UNIQUE KEY `UNIQUE_KEY_UN` (`UNIQUE_KEY`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `task_seq`
--

INSERT INTO `task_seq` (`ID`, `UNIQUE_KEY`) VALUES (0,'0');

--
-- Table structure for table `task_task_batch`
--

DROP TABLE IF EXISTS `task_task_batch`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `task_task_batch` (
                                   `TASK_EXECUTION_ID` bigint NOT NULL,
                                   `JOB_EXECUTION_ID` bigint NOT NULL,
                                   KEY `TASK_EXEC_BATCH_FK` (`TASK_EXECUTION_ID`),
                                   CONSTRAINT `TASK_EXEC_BATCH_FK` FOREIGN KEY (`TASK_EXECUTION_ID`) REFERENCES `task_execution` (`TASK_EXECUTION_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `task_task_batch`
--

/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;


-- rename for case sensitive mysql db
ALTER TABLE batch_job_execution                 RENAME TO BATCH_JOB_EXECUTION;                 
ALTER TABLE batch_job_execution_context         RENAME TO BATCH_JOB_EXECUTION_CONTEXT;         
ALTER TABLE batch_job_execution_params          RENAME TO BATCH_JOB_EXECUTION_PARAMS;          
ALTER TABLE batch_job_execution_seq             RENAME TO BATCH_JOB_EXECUTION_SEQ;             
ALTER TABLE batch_job_instance                  RENAME TO BATCH_JOB_INSTANCE;                  
ALTER TABLE batch_job_seq                       RENAME TO BATCH_JOB_SEQ;                       
ALTER TABLE batch_step_execution                RENAME TO BATCH_STEP_EXECUTION;                
ALTER TABLE batch_step_execution_context        RENAME TO BATCH_STEP_EXECUTION_CONTEXT;        
ALTER TABLE batch_step_execution_seq            RENAME TO BATCH_STEP_EXECUTION_SEQ;            
ALTER TABLE task_seq                            RENAME TO TASK_SEQ;                            
ALTER TABLE task_execution                      RENAME TO TASK_EXECUTION;                      
ALTER TABLE task_execution_params               RENAME TO TASK_EXECUTION_PARAMS;               
ALTER TABLE task_task_batch                     RENAME TO TASK_TASK_BATCH;

-- migration sql for spring batch core 5.x
-- spring-batch-core-5.0.2.jar!/org/springframework/batch/core/migration/5.0/migration-mysql.sql
ALTER TABLE BATCH_STEP_EXECUTION ADD CREATE_TIME DATETIME(6) NOT NULL DEFAULT '1970-01-01 00:00:00';
ALTER TABLE BATCH_STEP_EXECUTION MODIFY START_TIME DATETIME(6) NULL;

ALTER TABLE BATCH_JOB_EXECUTION_PARAMS DROP COLUMN DATE_VAL;
ALTER TABLE BATCH_JOB_EXECUTION_PARAMS DROP COLUMN LONG_VAL;
ALTER TABLE BATCH_JOB_EXECUTION_PARAMS DROP COLUMN DOUBLE_VAL;

ALTER TABLE BATCH_JOB_EXECUTION_PARAMS CHANGE COLUMN TYPE_CD PARAMETER_TYPE VARCHAR(100);
ALTER TABLE BATCH_JOB_EXECUTION_PARAMS CHANGE COLUMN KEY_NAME PARAMETER_NAME VARCHAR(100);
ALTER TABLE BATCH_JOB_EXECUTION_PARAMS CHANGE COLUMN STRING_VAL PARAMETER_VALUE VARCHAR(2500);

