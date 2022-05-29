CREATE DATABASE  IF NOT EXISTS `oasip` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `oasip`;
-- MySQL dump 10.13  Distrib 8.0.28, for Win64 (x86_64)
--
-- Host: 10.4.56.81    Database: oasip
-- ------------------------------------------------------
-- Server version	8.0.29

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
-- Table structure for table `event`
--

DROP TABLE IF EXISTS `event`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `event` (
  `bookingId` int NOT NULL AUTO_INCREMENT,
  `bookingName` varchar(100) NOT NULL,
  `bookingEmail` varchar(50) NOT NULL,
  `eventCategory` int NOT NULL,
  `eventStartTime` datetime NOT NULL,
  `eventDuration` int NOT NULL,
  `eventNotes` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`bookingId`),
  KEY `fk_event_eventCategory_idx` (`eventCategory`),
  CONSTRAINT `fk_event_eventCategory` FOREIGN KEY (`eventCategory`) REFERENCES `eventcategory` (`eventCategoryId`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `event`
--

LOCK TABLES `event` WRITE;
/*!40000 ALTER TABLE `event` DISABLE KEYS */;
INSERT INTO `event` VALUES (1,'Somchai Jaidee(OR-7)','somchai.jai@mail.kmutt.ac.th',2,'2022-05-23 13:30:00',20,NULL),(2,'Somsri Rakdee(SJ-3)','somsri.rak@mail.kmutt.ac.th',1,'2022-04-27 09:30:00',30,'ขอปรึกษาปัญหาเพื่อนไม่ช่วยงาน'),(3,'สมเกียรติ ขยันเรียน กลุ่ม TT-4','somkiat.kay@kmutt.ac.th',3,'2022-05-23 16:30:00',15,NULL),(6,'เด่นภูมิ นากคล่ำ','denphum@kmutt.ac.th',4,'2022-10-25 10:30:00',30,'ส่งงาน sprint 1่งงาน sprint 1่งงาน sprint 1่งงาน sprint 1่งงาน sprint 1่งงาน sprint 1่งงาน sprint 1่งงาน sprint 1่งงาน sprint 1');
/*!40000 ALTER TABLE `event` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `eventcategory`
--

DROP TABLE IF EXISTS `eventcategory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `eventcategory` (
  `eventCategoryId` int NOT NULL AUTO_INCREMENT,
  `eventCategoryName` varchar(100) DEFAULT NULL,
  `eventCategoryDescription` varchar(500) DEFAULT NULL,
  `eventDuration` int NOT NULL,
  PRIMARY KEY (`eventCategoryId`),
  UNIQUE KEY `eventCategoryName_UNIQUE` (`eventCategoryName`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `eventcategory`
--

LOCK TABLES `eventcategory` WRITE;
/*!40000 ALTER TABLE `eventcategory` DISABLE KEYS */;
INSERT INTO `eventcategory` VALUES (1,'Project Management Clinic','ตารางนัดหมายนี้ใชKสำหรับนัดหมาย project management clinic ในวิชา INT221 integrated project I ใหKนักศึกษาเตรียมเอกสารที่เกี่ยวขKองเพื่อแสดงระหว่างขอคำปรึกษา',30),(2,'DevOps/Infra Clinic','Use this event category for DevOps/Infra clinic.',20),(3,'Database Clinic','ตารางนัดหมายนี้ใชKสำหรับนัดหมาย database clinic ในวิชา INT221 integrated project I',15),(4,'Client-side Clinic','ตารางนัดหมายนี้ใชKสำหรับนัดหมาย client-side clinic ในวิชา INT221 integrated project I',30),(5,'Server-side Clinic','',30);
/*!40000 ALTER TABLE `eventcategory` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-05-12 14:24:02
