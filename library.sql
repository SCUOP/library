CREATE DATABASE  IF NOT EXISTS `library` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `library`;
-- MySQL dump 10.13  Distrib 8.0.31, for Win64 (x86_64)
--
-- Host: localhost    Database: library
-- ------------------------------------------------------
-- Server version	8.0.31

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
-- Table structure for table `book`
--

DROP TABLE IF EXISTS `book`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `book` (
  `id` int NOT NULL AUTO_INCREMENT,
  `type` varchar(64) NOT NULL,
  `name` varchar(64) NOT NULL,
  `description` varchar(1024) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `book`
--

LOCK TABLES `book` WRITE;
/*!40000 ALTER TABLE `book` DISABLE KEYS */;
INSERT INTO `book` VALUES (1,'计算机理论','高级语言程序设计','C++程序设计教材'),(2,'数学','概率论','内容全面，例题和习题丰富，结构层次性强，浅显易懂，能够满足不同读者的需求'),(3,'英语','大学英语-1','大一上英语教材'),(4,'文学','悲惨世界','法国作家维克多·雨果于1862年所发表的一部长篇小说，是19世纪最著名的小说之一。小说描绘19世纪初20年间几个法国人物的生活背景，涵盖拿破仑战争和1832年巴黎共和党人起义等政治现象叙述。'),(5,'文学','叔本华思想随笔','叔本华是德国著名哲学家，唯意志主义和现代悲观主义创始人。 《叔本华思想随笔》选自叔本华的后期著作《附录和补遗》与《作为意欲和表象的世界》第二卷，《叔本华思想随笔》虽然讨论的话题众多，但里面贯穿着的基本思想主线清晰可辨。叔本华的过人之处就在于把真理裹以最朴素的语言外衣。'),(6,'文学','悉达多','《悉达多》是黑塞的第九部作品，1922年在德国出版，通过对主人公悉达多身上的两个“自我”——理性的无限的“自我”和感性的有限的“自我”——的描写，黑塞探讨了个人如何在有限的生命中追求无限的、永恒的人生境界的问题。读者从中既可以洞察作家对人性的热爱与敬畏，对人生和宇宙的充满睿智的理解，又能够感受到他对传统的人道主义理想的呼唤和向往，同时，还可以领略到作为西方人的作者对东方尤其是中国思想智慧的接受与借鉴。'),(7,'文学','肖申克的救赎','《肖申克的救赎》是美国作家斯蒂芬·埃德温·金的中篇小说，也是其代表作。收录于小说合集《四季奇谭》中，副标题为“春天的希望”。'),(8,'文学','白夜行','《白夜行》是日本作家东野圭吾创作的长篇小说，也是其代表作。该小说于1997年1月至1999年1月间连载于期刊，单行本1999年8月在日本发行。故事围绕着一对有着不同寻常情愫的小学生展开。1973年，大阪的一栋废弃建筑内发现了一具男尸，此后19年，嫌疑人之女雪穗与被害者之子桐原亮司走上截然不同的人生道路，一个跻身上流社会，一个却在底层游走，而他们身边的人，却接二连三地离奇死去，警察经过19年的艰苦追踪，终于使真相大白。'),(9,'文学','且听风吟','《且听风吟》 是日本作家村上春树创作的一部中篇小说，发表于1979年。'),(10,'文学','一九八四','《一九八四》（Nineteen Eighty-Four）是英国左翼作家乔治·奥威尔于1949年出版的长篇政治小说。'),(11,'文学','刺杀骑士团长','《刺杀骑士团长》是村上春树撰写的超现实主义小说。该书中文译本于2018年由上海译文出版社出版发行，译者是林少华。');
/*!40000 ALTER TABLE `book` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  PRIMARY KEY (`id`,`password`,`username`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'root','123');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-12-17  1:51:31
