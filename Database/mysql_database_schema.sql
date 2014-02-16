# ************************************************************
# Sequel Pro SQL dump
# Version 4096
#
# http://www.sequelpro.com/
# http://code.google.com/p/sequel-pro/
#
# Host: mysql.consultoriaavaliar.com.br (MySQL 5.5.29-log)
# Database: consultoriaava03
# Generation Time: 2013-05-24 17:31:33 +0000
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


# Dump of table all_services
# ------------------------------------------------------------

DROP VIEW IF EXISTS `all_services`;

CREATE TABLE `all_services` (
   `service_id` INT(11) NOT NULL DEFAULT '0',
   `service_type` VARCHAR(20) NULL DEFAULT NULL,
   `service_name` VARCHAR(45) NULL DEFAULT NULL,
   `description` VARCHAR(255) NULL DEFAULT NULL,
   `icon_name` VARCHAR(32) NULL DEFAULT NULL,
   `qtd_cases` INT(11) NULL DEFAULT NULL
) ENGINE=MyISAM;




# Dump of table attendance
# ------------------------------------------------------------

DROP TABLE IF EXISTS `attendance`;

CREATE TABLE `attendance` (
  `attendance_id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `occurrence_id` int(11) NOT NULL,
  `datetime` datetime NOT NULL,
  `status` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`attendance_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table contacts
# ------------------------------------------------------------

DROP TABLE IF EXISTS `contacts`;

CREATE TABLE `contacts` (
  `contact_id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `device_id` varchar(50) NOT NULL DEFAULT '',
  `ddi` varchar(3) NOT NULL,
  `ddd` varchar(3) NOT NULL,
  `phone_number` varchar(10) NOT NULL,
  `email` varchar(80) DEFAULT NULL,
  PRIMARY KEY (`contact_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table device
# ------------------------------------------------------------

DROP TABLE IF EXISTS `device`;

CREATE TABLE `device` (
  `device_id` varchar(50) NOT NULL DEFAULT '',
  `serial_number` varchar(30) DEFAULT NULL,
  `os_name` varchar(20) DEFAULT NULL,
  `os_version` varchar(30) DEFAULT NULL,
  `device_name` varchar(60) DEFAULT NULL,
  `datetime` datetime DEFAULT NULL,
  `user_name` varchar(60) DEFAULT NULL,
  `email` varchar(45) DEFAULT '',
  `phone_ddd` varchar(2) DEFAULT NULL,
  `phone_number` varchar(20) DEFAULT NULL,
  `status` varchar(15) DEFAULT NULL,
  `activation_code` varchar(3) DEFAULT NULL,
  `activation_date` datetime DEFAULT NULL,
  PRIMARY KEY (`device_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table occurrence
# ------------------------------------------------------------

DROP TABLE IF EXISTS `occurrence`;

CREATE TABLE `occurrence` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `device_id` varchar(45) DEFAULT NULL,
  `datetime` datetime DEFAULT NULL,
  `status` varchar(20) DEFAULT NULL,
  `service_provider_id` int(11) DEFAULT NULL,
  `latitude` varchar(20) DEFAULT NULL,
  `longitude` varchar(20) DEFAULT NULL,
  `phone_number` varchar(20) DEFAULT '',
  `service_id` int(11) DEFAULT NULL,
  `message` varchar(140) DEFAULT NULL,
  `media_type` int(1) DEFAULT NULL,
  `file_name` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `occurrence_service_fk` (`service_id`),
  KEY `occurrence_device_fk` (`device_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;




# Dump of table service
# ------------------------------------------------------------

DROP TABLE IF EXISTS `service`;

CREATE TABLE `service` (
  `service_id` int(11) NOT NULL AUTO_INCREMENT,
  `service_type` varchar(20) DEFAULT NULL,
  `service_name` varchar(45) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `qtd_cases` int(11) DEFAULT NULL,
  `icon_name` varchar(32) DEFAULT NULL,
  `response_message` varchar(128) DEFAULT NULL,
  `status` int(1) DEFAULT '1',
  PRIMARY KEY (`service_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


# Dump of table service_copy
# ------------------------------------------------------------

DROP TABLE IF EXISTS `service_copy`;

CREATE TABLE `service_copy` (
  `service_id` int(11) NOT NULL AUTO_INCREMENT,
  `service_type` varchar(20) DEFAULT NULL,
  `service_name` varchar(45) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `qtd_cases` int(11) DEFAULT NULL,
  `icon_name` varchar(32) DEFAULT NULL,
  `response_message` varchar(128) DEFAULT NULL,
  `status` int(1) DEFAULT '1',
  PRIMARY KEY (`service_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table service_provider
# ------------------------------------------------------------

DROP TABLE IF EXISTS `service_provider`;

CREATE TABLE `service_provider` (
  `service_provider_id` int(11) NOT NULL AUTO_INCREMENT,
  `service_id` int(11) DEFAULT NULL,
  `name` varchar(60) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `sms_ddd` varchar(2) DEFAULT NULL,
  `sms_number` varchar(20) DEFAULT NULL,
  `phone_number` varchar(20) DEFAULT NULL,
  `send_sms` tinyint(1) DEFAULT '0',
  `send_email` tinyint(1) DEFAULT '0',
  `send_push` tinyint(1) DEFAULT '0',
  `positive_qualifies` int(11) DEFAULT '0',
  `negative_qualifies` int(11) DEFAULT '0',
  `busy` tinyint(1) DEFAULT '0',
  `can_be_busy` tinyint(1) DEFAULT '1',
  `return_to_sender` int(1) DEFAULT '0',
  `status` int(1) DEFAULT '1',
  `latitude` varchar(20) DEFAULT NULL,
  `longitude` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`service_provider_id`),
  KEY `idx_seervice_id` (`service_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table top_six_service
# ------------------------------------------------------------

DROP VIEW IF EXISTS `top_six_service`;

CREATE TABLE `top_six_service` (
   `service_id` INT(11) NOT NULL DEFAULT '0',
   `service_type` VARCHAR(20) NULL DEFAULT NULL,
   `service_name` VARCHAR(45) NULL DEFAULT NULL,
   `description` VARCHAR(255) NULL DEFAULT NULL,
   `icon_name` VARCHAR(32) NULL DEFAULT NULL,
   `qtd_cases` INT(11) NULL DEFAULT NULL
) ENGINE=MyISAM;



# Dump of table version
# ------------------------------------------------------------

DROP TABLE IF EXISTS `version`;

CREATE TABLE `version` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `service_version` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

LOCK TABLES `version` WRITE;
/*!40000 ALTER TABLE `version` DISABLE KEYS */;

INSERT INTO `version` (`id`, `service_version`)
VALUES
	(1,1021);

/*!40000 ALTER TABLE `version` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table vw_devices_gps_problem
# ------------------------------------------------------------

DROP VIEW IF EXISTS `vw_devices_gps_problem`;

CREATE TABLE `vw_devices_gps_problem` (
   `device_id` VARCHAR(45) NULL DEFAULT NULL,
   `user_name` VARCHAR(60) NULL DEFAULT NULL,
   `count(*)` BIGINT(21) NOT NULL DEFAULT '0'
) ENGINE=MyISAM;



# Dump of table vw_occurrence_summary
# ------------------------------------------------------------

DROP VIEW IF EXISTS `vw_occurrence_summary`;

CREATE TABLE `vw_occurrence_summary` (
   `device_id` VARCHAR(45) NULL DEFAULT NULL,
   `user_name` VARCHAR(60) NULL DEFAULT NULL,
   `count(*)` BIGINT(21) NOT NULL DEFAULT '0'
) ENGINE=MyISAM;



# Dump of table vw_services
# ------------------------------------------------------------

DROP VIEW IF EXISTS `vw_services`;

CREATE TABLE `vw_services` (
   `service_provider_id` INT(11) NOT NULL DEFAULT '0',
   `service_id` INT(11) NULL DEFAULT NULL,
   `service_name` VARCHAR(45) NULL DEFAULT NULL,
   `name` VARCHAR(60) NULL DEFAULT NULL,
   `email` VARCHAR(45) NULL DEFAULT NULL,
   `sms_ddd` VARCHAR(2) NULL DEFAULT NULL,
   `sms_number` VARCHAR(20) NULL DEFAULT NULL,
   `phone_number` VARCHAR(20) NULL DEFAULT NULL,
   `send_sms` TINYINT(1) NULL DEFAULT '0',
   `send_email` TINYINT(1) NULL DEFAULT '0',
   `send_push` TINYINT(1) NULL DEFAULT '0',
   `positive_qualifies` INT(11) NULL DEFAULT '0',
   `negative_qualifies` INT(11) NULL DEFAULT '0',
   `busy` TINYINT(1) NULL DEFAULT '0',
   `can_be_busy` TINYINT(1) NULL DEFAULT '1',
   `status` INT(1) NULL DEFAULT '1'
) ENGINE=MyISAM;





# Replace placeholder table for vw_devices_gps_problem with correct view syntax
# ------------------------------------------------------------

DROP TABLE `vw_devices_gps_problem`;

CREATE ALGORITHM=UNDEFINED DEFINER=`consultoriaava03`@`%` SQL SECURITY DEFINER VIEW `vw_devices_gps_problem`
AS SELECT
   `occurrence`.`device_id` AS `device_id`,
   `device`.`user_name` AS `user_name`,count(0) AS `count(*)`
FROM (`occurrence` join `device` on((`occurrence`.`device_id` = `device`.`device_id`))) where (`occurrence`.`latitude` = '0.0') group by `occurrence`.`device_id`;


# Replace placeholder table for vw_occurrence_summary with correct view syntax
# ------------------------------------------------------------

DROP TABLE `vw_occurrence_summary`;

CREATE ALGORITHM=UNDEFINED DEFINER=`consultoriaava03`@`%` SQL SECURITY DEFINER VIEW `vw_occurrence_summary`
AS SELECT
   `occurrence`.`device_id` AS `device_id`,
   `device`.`user_name` AS `user_name`,count(0) AS `count(*)`
FROM (`occurrence` join `device` on((`occurrence`.`device_id` = `device`.`device_id`))) group by `occurrence`.`device_id`;


# Replace placeholder table for vw_services with correct view syntax
# ------------------------------------------------------------

DROP TABLE `vw_services`;

CREATE ALGORITHM=UNDEFINED DEFINER=`consultoriaava03`@`%` SQL SECURITY DEFINER VIEW `vw_services`
AS SELECT
   `a`.`service_provider_id` AS `service_provider_id`,
   `a`.`service_id` AS `service_id`,
   `b`.`service_name` AS `service_name`,
   `a`.`name` AS `name`,
   `a`.`email` AS `email`,
   `a`.`sms_ddd` AS `sms_ddd`,
   `a`.`sms_number` AS `sms_number`,
   `a`.`phone_number` AS `phone_number`,
   `a`.`send_sms` AS `send_sms`,
   `a`.`send_email` AS `send_email`,
   `a`.`send_push` AS `send_push`,
   `a`.`positive_qualifies` AS `positive_qualifies`,
   `a`.`negative_qualifies` AS `negative_qualifies`,
   `a`.`busy` AS `busy`,
   `a`.`can_be_busy` AS `can_be_busy`,
   `a`.`status` AS `status`
FROM (`service_provider` `a` join `service` `b` on((`a`.`service_id` = `b`.`service_id`))) order by `a`.`service_id`,`b`.`service_name`;


# Replace placeholder table for all_services with correct view syntax
# ------------------------------------------------------------

DROP TABLE `all_services`;

CREATE ALGORITHM=UNDEFINED DEFINER=`consultoriaava03`@`%` SQL SECURITY DEFINER VIEW `all_services`
AS SELECT
   `service`.`service_id` AS `service_id`,
   `service`.`service_type` AS `service_type`,
   `service`.`service_name` AS `service_name`,
   `service`.`description` AS `description`,
   `service`.`icon_name` AS `icon_name`,
   `service`.`qtd_cases` AS `qtd_cases`
FROM `service` where ((`service`.`status` = 1) and (not(`service`.`service_id` in (select `top_six_service`.`service_id` from `top_six_service`))) and `service`.`service_id` in (select `service_provider`.`service_id` from `service_provider` where (`service_provider`.`status` = 1))) order by `service`.`service_name`;


# Replace placeholder table for all_services_copy with correct view syntax
# ------------------------------------------------------------

DROP TABLE `all_services_copy`;

CREATE ALGORITHM=UNDEFINED DEFINER=`consultoriaava03`@`%` SQL SECURITY DEFINER VIEW `all_services_copy`
AS SELECT
   `service`.`service_id` AS `service_id`,
   `service`.`service_type` AS `service_type`,
   `service`.`service_name` AS `service_name`,
   `service`.`qtd_cases` AS `qtd_cases`
FROM `service` where ((not(`service`.`service_id` in (select `top_six_service`.`service_id` from `top_six_service`))) and `service`.`service_id` in (select `service_provider`.`service_id` from `service_provider`)) order by `service`.`service_name`;


# Replace placeholder table for top_six_service with correct view syntax
# ------------------------------------------------------------

DROP TABLE `top_six_service`;

CREATE ALGORITHM=UNDEFINED DEFINER=`consultoriaava03`@`%` SQL SECURITY DEFINER VIEW `top_six_service`
AS SELECT
   `service`.`service_id` AS `service_id`,
   `service`.`service_type` AS `service_type`,
   `service`.`service_name` AS `service_name`,
   `service`.`description` AS `description`,
   `service`.`icon_name` AS `icon_name`,
   `service`.`qtd_cases` AS `qtd_cases`
FROM `service` where (`service`.`status` = 1) order by `service`.`qtd_cases` desc;

/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
