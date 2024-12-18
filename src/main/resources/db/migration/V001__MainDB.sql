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
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role` (
  `role_id` int NOT NULL AUTO_INCREMENT,
  `role_name` varchar(45) NOT NULL,
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (1,'admin'),(2,'user');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `task_sample`
--

DROP TABLE IF EXISTS `task_sample`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `task_sample` (
  `task_sample_id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `description` text,
  `script_path` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`task_sample_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `task_sample`
--

LOCK TABLES `task_sample` WRITE;
/*!40000 ALTER TABLE `task_sample` DISABLE KEYS */;
INSERT INTO `task_sample` VALUES (1,'Рендер картинки в Blender','Description of script 1','C:\\RichiFolder\\Projects\\RichisAppForServer\\richis_app\\src\\main\\resources\\files\\samples\\script.py'),(8,'script 5','Description of script 5',NULL),(10,'script 3','Description of script 3',NULL);
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
  `status` int NOT NULL DEFAULT '0',
  `task_sample_id` int DEFAULT NULL,
  `user_id` int DEFAULT NULL,
  `params` varchar(300) DEFAULT NULL,
  PRIMARY KEY (`task_to_proc_id`),
  KEY `task_to_proc_ibfk_1_idx` (`task_sample_id`),
  KEY `task_to_proc_ibfk_2_idx` (`user_id`),
  CONSTRAINT `task_to_proc_ibfk_1` FOREIGN KEY (`task_sample_id`) REFERENCES `task_sample` (`task_sample_id`),
  CONSTRAINT `task_to_proc_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `task_to_proc`
--

LOCK TABLES `task_to_proc` WRITE;
/*!40000 ALTER TABLE `task_to_proc` DISABLE KEYS */;
INSERT INTO `task_to_proc` VALUES (1,'2024-08-22 13:50:19',0,1,1,'20~30'),(2,'2024-08-22 13:51:38',0,10,3,'333'),(3,'2024-12-17 10:20:33',0,1,1,'0~0~0~0'),(4,'2024-12-17 10:21:05',0,1,1,'480~120~result~project.blend'),(5,'2024-12-17 14:56:42',0,1,1,'4~2~r~i'),(6,'2024-12-17 15:03:19',0,1,1,'4~1~o~i'),(7,'2024-12-17 15:06:37',0,1,1,'480~120~rendered~project.blend'),(8,'2024-12-17 20:38:03',0,1,1,'480~120~rendered~project.blend');
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
  `name` varchar(45) DEFAULT NULL,
  `task_sample_id` int DEFAULT NULL,
  `type` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `task_sample_id` (`task_sample_id`),
  CONSTRAINT `task_sample_id` FOREIGN KEY (`task_sample_id`) REFERENCES `task_sample` (`task_sample_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8mb3;
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
-- Table structure for table `user_role`
--

DROP TABLE IF EXISTS `user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_role` (
  `user_id` int NOT NULL,
  `role_id` int NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`),
  KEY `user_role_ibfk_2_idx` (`role_id`),
  CONSTRAINT `user_role_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`),
  CONSTRAINT `user_role_ibfk_2` FOREIGN KEY (`role_id`) REFERENCES `role` (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_role`
--

LOCK TABLES `user_role` WRITE;
/*!40000 ALTER TABLE `user_role` DISABLE KEYS */;
INSERT INTO `user_role` VALUES (1,1),(1,2),(3,2),(4,2);
/*!40000 ALTER TABLE `user_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `login` varchar(45) NOT NULL,
  `name` varchar(100) NOT NULL,
  `surname` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL,
  `email` varchar(45) DEFAULT NULL,
  `priority` int NOT NULL DEFAULT '5',
  `minutes_to_proc_task` int NOT NULL DEFAULT '60',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'R','Richi','Hnok','$2a$05$Jy9ysQVWGuwfplxJ9NUfb.S6bjysR7m/19YI6A7UOMONcnei8HPSq',NULL,5,120),(3,'A','Artemiy','Viazovski','$2a$05$Jy9ysQVWGuwfplxJ9NUfb.S6bjysR7m/19YI6A7UOMONcnei8HPSq',NULL,5,60),(4,'ap','Arkadiy','Porovozov','$2a$05$Jy9ysQVWGuwfplxJ9NUfb.S6bjysR7m/19YI6A7UOMONcnei8HPSq',NULL,5,60),(6,'B','B','B','$2a$05$BCQjpjA.bxcytp4Z2cgmBOzQyoi.N0LAgTfoAmx5feWrfMepI6c3S','a@a.c',5,60);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-12-18  7:38:15
