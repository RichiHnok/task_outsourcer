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
INSERT INTO `flyway_schema_history` VALUES (1,'1','<< Flyway Baseline >>','BASELINE','<< Flyway Baseline >>',NULL,'bestuser','2024-12-18 04:47:16',0,1),(2,'002','test insert','SQL','V002__test_insert.sql',1990482650,'bestuser','2024-12-18 04:54:59',22,1),(3,'003','1224181224','SQL','V003__1224181224.sql',1254298533,'bestuser','2024-12-18 09:24:45',2269,1),(4,'004','1238181224','SQL','V004__1238181224.sql',642546902,'bestuser','2024-12-18 09:42:45',1274,1),(5,'005','1416221224','SQL','V005__1416221224.sql',632960572,'bestuser','2024-12-27 12:44:02',1948,1),(6,'006','removing multiple roles','SQL','V006__removing_multiple_roles.sql',1170424908,'bestuser','2025-01-21 23:52:25',1263,1),(7,'007','update data','SQL','V007__update_data.sql',1284702533,'bestuser','2025-01-25 19:49:49',2222,1);
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
  PRIMARY KEY (`task_sample_id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `task_sample`
--

LOCK TABLES `task_sample` WRITE;
/*!40000 ALTER TABLE `task_sample` DISABLE KEYS */;
INSERT INTO `task_sample` VALUES (1,'Рендер картинки в Blender','Это задача нужна для рендера картинки в блендер-проекте. В параметрах укажите размеры картинки, имя блендер-проекта с расширением и название итоговой картинки.','src\\main\\resources\\files\\samples\\sample1\\script.py'),(8,'script 5','Description of script 5','src\\main\\resources\\files\\samples\\sample8\\script.py'),(10,'script 3','Description of script 3',NULL),(11,'Проверка работы на txt-файле','','src\\main\\resources\\files\\samples\\sample11\\Generate numbers.py');
/*!40000 ALTER TABLE `task_sample` ENABLE KEYS */;
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
  `params` varchar(300) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  PRIMARY KEY (`task_to_proc_id`),
  KEY `task_to_proc_ibfk_1_idx` (`task_sample_id`),
  KEY `task_to_proc_ibfk_2_idx` (`user_id`),
  CONSTRAINT `task_to_proc_ibfk_1` FOREIGN KEY (`task_sample_id`) REFERENCES `task_sample` (`task_sample_id`),
  CONSTRAINT `task_to_proc_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `task_to_proc`
--

LOCK TABLES `task_to_proc` WRITE;
/*!40000 ALTER TABLE `task_to_proc` DISABLE KEYS */;
/*!40000 ALTER TABLE `task_to_proc` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tasks_params`
--

DROP TABLE IF EXISTS `tasks_params`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tasks_params` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `task_sample_id` int DEFAULT NULL,
  `type` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `task_sample_id` (`task_sample_id`),
  CONSTRAINT `task_sample_id` FOREIGN KEY (`task_sample_id`) REFERENCES `task_sample` (`task_sample_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tasks_params`
--

LOCK TABLES `tasks_params` WRITE;
/*!40000 ALTER TABLE `tasks_params` DISABLE KEYS */;
INSERT INTO `tasks_params` VALUES (29,'resX',1,0),(30,'resY',1,0),(36,'par 1',10,0),(39,'name of result file',1,2),(40,'name of file to render',1,2);
/*!40000 ALTER TABLE `tasks_params` ENABLE KEYS */;
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

-- Dump completed on 2025-02-01 18:55:55
