CREATE DATABASE  IF NOT EXISTS `my_db` /*!40100 DEFAULT CHARACTER SET utf8mb3 */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `my_db`;
-- MySQL dump 10.13  Distrib 8.0.36, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: my_db
-- ------------------------------------------------------
-- Server version	8.0.37

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `flyway_schema_history`
--

DROP TABLE IF EXISTS `flyway_schema_history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `flyway_schema_history` (
  `installed_rank` int NOT NULL,
  `version` varchar(50) DEFAULT NULL,
  `description` varchar(200) NOT NULL,
  `type` varchar(20) NOT NULL,
  `script` varchar(1000) NOT NULL,
  `checksum` int DEFAULT NULL,
  `installed_by` varchar(100) NOT NULL,
  `installed_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `execution_time` int NOT NULL,
  `success` tinyint(1) NOT NULL,
  PRIMARY KEY (`installed_rank`),
  KEY `flyway_schema_history_s_idx` (`success`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `flyway_schema_history`
--

LOCK TABLES `flyway_schema_history` WRITE;
/*!40000 ALTER TABLE `flyway_schema_history` DISABLE KEYS */;
INSERT INTO `flyway_schema_history` VALUES (1,'1','<< Flyway Baseline >>','BASELINE','<< Flyway Baseline >>',NULL,'bestuser','2024-12-18 04:47:16',0,1),(2,'002','test insert','SQL','V002__test_insert.sql',1990482650,'bestuser','2024-12-18 04:54:59',22,1),(3,'003','1224181224','SQL','V003__1224181224.sql',1254298533,'bestuser','2024-12-18 09:24:45',2269,1),(4,'004','1238181224','SQL','V004__1238181224.sql',642546902,'bestuser','2024-12-18 09:42:45',1274,1),(5,'005','1416221224','SQL','V005__1416221224.sql',632960572,'bestuser','2024-12-27 12:44:02',1948,1),(6,'006','removing multiple roles','SQL','V006__removing_multiple_roles.sql',1170424908,'bestuser','2025-01-21 23:52:25',1263,1),(7,'007','update data','SQL','V007__update_data.sql',1284702533,'bestuser','2025-01-25 19:49:49',2222,1),(8,'008','removed old data','SQL','V008__removed_old_data.sql',-1368182085,'bestuser','2025-02-01 15:55:59',1988,1),(9,'009','extending params types 1','SQL','V009__extending_params_types_1.sql',1682798510,'bestuser','2025-03-09 11:11:23',2249,1),(10,'010','extending params types 2','SQL','V010__extending_params_types_2.sql',2046008346,'bestuser','2025-03-11 11:45:51',2557,1),(11,'011','added laucnh cammand template to task sample','SQL','V011__added_laucnh_cammand_template_to_task_sample.sql',-1302724727,'bestuser','2025-04-09 07:20:24',3202,1);
/*!40000 ALTER TABLE `flyway_schema_history` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `task_sample`
--

DROP TABLE IF EXISTS `task_sample`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `task_sample` (
  `task_sample_id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `description` text CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci,
  `script_path` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `launch_command_template` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`task_sample_id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `task_sample`
--

LOCK TABLES `task_sample` WRITE;
/*!40000 ALTER TABLE `task_sample` DISABLE KEYS */;
INSERT INTO `task_sample` VALUES (1,'Рендер картинки в Blender. Движок Cycles','Это задача нужна для рендера картинки в блендер-проекте. В параметрах укажите размеры картинки, имя блендер-проекта с расширением и название итоговой картинки.','src\\main\\resources\\files\\samples\\sample1\\render_image.py','blender -b -P \"%s\" -- \"%s\" %s'),(8,'script 5','Description of script 5','src\\main\\resources\\files\\samples\\sample8\\script.py',NULL),(10,'script 3','Description of script 3',NULL,NULL),(11,'Проверка работы на txt-файле','','src\\main\\resources\\files\\samples\\sample11\\Generate numbers.py','python %s %s'),(12,'Задача, выполняющийся указанное количество времени','Тестовая задача для проверки работы выполнения долгих задач, которая также принимает параметр','src\\main\\resources\\files\\samples\\sample12\\Generate numbers and sleep.py',NULL),(13,'Проверка строкового параметра','',NULL,NULL),(14,'Проверка целочисленного параметра','',NULL,NULL),(15,'Проверка файлового параметра','',NULL,NULL),(16,'Проверка числа, строки','',NULL,NULL),(17,'Проверка нескольких чисел','',NULL,NULL),(18,'Проверка числа, файла','',NULL,NULL),(19,'Проверка шаблона без параметра','',NULL,NULL);
/*!40000 ALTER TABLE `task_sample` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `task_sample_param`
--

DROP TABLE IF EXISTS `task_sample_param`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `task_sample_param` (
  `task_sample_param_id` int NOT NULL AUTO_INCREMENT,
  `task_sample_id` int DEFAULT NULL,
  `name` varchar(100) DEFAULT NULL,
  `type` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`task_sample_param_id`),
  KEY `task_sample_param_ibfk_1_idx` (`task_sample_id`),
  CONSTRAINT `task_sample_param_ibfk_1` FOREIGN KEY (`task_sample_id`) REFERENCES `task_sample` (`task_sample_id`)
) ENGINE=InnoDB AUTO_INCREMENT=55 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `task_sample_param`
--

LOCK TABLES `task_sample_param` WRITE;
/*!40000 ALTER TABLE `task_sample_param` DISABLE KEYS */;
INSERT INTO `task_sample_param` VALUES (29,1,'Горизонтальное разрешение',0),(30,1,'Вертикальное разрешение',0),(39,1,'blender-проект',2),(43,10,'No name 7853',2),(44,13,'Строка',1),(45,14,'Целое число',0),(46,15,'Файл',2),(47,16,'Число',0),(48,16,'Строка',1),(49,17,'Число 1',0),(50,17,'Число 2',0),(51,18,'Файл',2),(52,18,'Число',0),(53,1,'Название итоговой картинки',1),(54,1,'Samples',0);
/*!40000 ALTER TABLE `task_sample_param` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `task_sample_param_file`
--

DROP TABLE IF EXISTS `task_sample_param_file`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `task_sample_param_file` (
  `task_sample_param_id` int NOT NULL,
  PRIMARY KEY (`task_sample_param_id`),
  KEY `task_sample_param_file_ibfk_1_idx` (`task_sample_param_id`),
  CONSTRAINT `task_sample_param_file_ibfk_1` FOREIGN KEY (`task_sample_param_id`) REFERENCES `task_sample_param` (`task_sample_param_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `task_sample_param_file`
--

LOCK TABLES `task_sample_param_file` WRITE;
/*!40000 ALTER TABLE `task_sample_param_file` DISABLE KEYS */;
/*!40000 ALTER TABLE `task_sample_param_file` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `task_sample_param_integer`
--

DROP TABLE IF EXISTS `task_sample_param_integer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `task_sample_param_integer` (
  `task_sample_param_id` int NOT NULL,
  PRIMARY KEY (`task_sample_param_id`),
  CONSTRAINT `task_sample_param_integer_ibfk_1` FOREIGN KEY (`task_sample_param_id`) REFERENCES `task_sample_param` (`task_sample_param_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `task_sample_param_integer`
--

LOCK TABLES `task_sample_param_integer` WRITE;
/*!40000 ALTER TABLE `task_sample_param_integer` DISABLE KEYS */;
/*!40000 ALTER TABLE `task_sample_param_integer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `task_sample_param_string`
--

DROP TABLE IF EXISTS `task_sample_param_string`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `task_sample_param_string` (
  `task_sample_param_id` int NOT NULL,
  PRIMARY KEY (`task_sample_param_id`),
  CONSTRAINT `task_sample_param_string_ibfk_1` FOREIGN KEY (`task_sample_param_id`) REFERENCES `task_sample_param` (`task_sample_param_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `task_sample_param_string`
--

LOCK TABLES `task_sample_param_string` WRITE;
/*!40000 ALTER TABLE `task_sample_param_string` DISABLE KEYS */;
/*!40000 ALTER TABLE `task_sample_param_string` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `task_to_proc`
--

DROP TABLE IF EXISTS `task_to_proc`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `task_to_proc` (
  `task_to_proc_id` int NOT NULL AUTO_INCREMENT,
  `start_time` timestamp NULL DEFAULT NULL,
  `status` varchar(20) NOT NULL DEFAULT 'CREATED',
  `task_sample_id` int DEFAULT NULL,
  `user_id` int DEFAULT NULL,
  `params` json DEFAULT NULL,
  PRIMARY KEY (`task_to_proc_id`),
  KEY `task_to_proc_ibfk_1_idx` (`task_sample_id`),
  KEY `task_to_proc_ibfk_2_idx` (`user_id`),
  CONSTRAINT `task_to_proc_ibfk_1` FOREIGN KEY (`task_sample_id`) REFERENCES `task_sample` (`task_sample_id`),
  CONSTRAINT `task_to_proc_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=104 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `task_to_proc`
--

LOCK TABLES `task_to_proc` WRITE;
/*!40000 ALTER TABLE `task_to_proc` DISABLE KEYS */;
INSERT INTO `task_to_proc` VALUES (50,'2025-03-01 05:45:30','FINISHED',11,1,NULL),(51,'2025-03-01 05:47:14','FINISHED',11,3,NULL),(52,'2025-03-01 05:47:21','FINISHED',11,3,NULL),(95,'2025-04-10 05:10:20','FINISHED',11,1,NULL),(103,'2025-04-12 08:18:51','FINISHED',1,1,'[{\"paramName\": \"Горизонтальное разрешение\", \"paramType\": \"INTEGER\", \"paramValue\": \"100\"}, {\"paramName\": \"Вертикальное разрешение\", \"paramType\": \"INTEGER\", \"paramValue\": \"100\"}, {\"paramName\": \"blender-проект\", \"paramType\": \"FILE\", \"paramValue\": \"C:\\\\RichiFolder\\\\Projects\\\\RichisAppForServer\\\\richis_app\\\\src\\\\main\\\\resources\\\\files\\\\users\\\\R\\\\1103\\\\input\\\\test.blend\"}, {\"paramName\": \"Название итоговой картинки\", \"paramType\": \"STRING\", \"paramValue\": \"еуые\"}, {\"paramName\": \"Samples\", \"paramType\": \"INTEGER\", \"paramValue\": \"100\"}]');
/*!40000 ALTER TABLE `task_to_proc` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `login` varchar(45) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `name` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `surname` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `password` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `email` varchar(45) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `priority` int NOT NULL DEFAULT '5',
  `minutes_to_proc_task` int NOT NULL DEFAULT '60',
  `user_role` varchar(45) NOT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `login_UNIQUE` (`login`),
  UNIQUE KEY `email_UNIQUE` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'R','Richi','Hnok','$2a$05$Jy9ysQVWGuwfplxJ9NUfb.S6bjysR7m/19YI6A7UOMONcnei8HPSq',NULL,5,120,'ROLE_ADMIN'),(3,'A','Artemiy','Viazovski','$2a$05$Jy9ysQVWGuwfplxJ9NUfb.S6bjysR7m/19YI6A7UOMONcnei8HPSq',NULL,7,60,'ROLE_USER'),(4,'ap','Arkadiy','Porovozov','$2a$05$Jy9ysQVWGuwfplxJ9NUfb.S6bjysR7m/19YI6A7UOMONcnei8HPSq',NULL,5,60,'ROLE_USER'),(9,'B','B','B','$2a$05$LwOnoV7kXr4Q5lZxOhPOd.SNTwq0HwfZvVKRH3GjpvgW2YVQXvWg2','a@m.com',5,60,'ROLE_ADMIN'),(10,'C','B','B','$2a$05$HgLpSrzHLDirmStJeGdk.ONumpLkjiDTRD68DLmDl0k3O.rFUO8hm','A@M.a',5,60,'ROLE_ADMIN'),(11,'D','D','D','$2a$05$82G43HOQ6/QrQo6hD4KztOll3hiYc6VezMuzEDF7fYxdRyhKdocJO','D@m.c',6,60,'ROLE_USER');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'my_db'
--

--
-- Dumping routines for database 'my_db'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-04-13 11:24:42
