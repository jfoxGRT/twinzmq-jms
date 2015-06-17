DROP TABLE IF EXISTS `quote`;  
/*!40101 SET @saved_cs_client     = @@character_set_client */;  
/*!40101 SET character_set_client = utf8 */;  
CREATE TABLE `quote` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `quote_text` varchar(255) NOT NULL,
  `rating` int(8) DEFAULT NULL,
  `acknowledged` bit NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`)
);


