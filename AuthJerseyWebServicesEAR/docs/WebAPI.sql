-- phpMyAdmin SQL Dump
-- version 4.8.3
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Generation Time: Jul 21, 2021 at 05:38 PM
-- Server version: 5.7.23
-- PHP Version: 7.2.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `webapi`
--
CREATE DATABASE IF NOT EXISTS `webapi` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
USE `webapi`;

-- --------------------------------------------------------

--
-- Table structure for table `access_token`
--

DROP TABLE IF EXISTS `access_token`;
CREATE TABLE IF NOT EXISTS `access_token` (
  `token` varchar(45) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'Access token',
  `user_id` varchar(150) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'User id',
  `alias` varchar(150) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'User alias',
  `expiration_dt` date NOT NULL COMMENT 'Expiration date of token',
  `user_ref_id` int(11) NOT NULL COMMENT 'User ref id'
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `access_token`
--

INSERT INTO `access_token` (`token`, `user_id`, `alias`, `expiration_dt`, `user_ref_id`) VALUES
('cDjZvPbmQXiZOgS6MHkt-RTS000IS-20210716', 'RTS000IS@ISU.NET', 'card', '2021-07-16', 1);

-- --------------------------------------------------------

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
CREATE TABLE IF NOT EXISTS `roles` (
  `role_ref_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Role reference id',
  `role_nm` varchar(5) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'Role name',
  `role_desc` varchar(250) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'Role description',
  `create_user_ref_id` int(11) NOT NULL COMMENT 'create user reference id',
  `create_ts` timestamp NOT NULL COMMENT 'create timestamp',
  `update_user_ref_id` int(11) DEFAULT NULL COMMENT 'update user reference id',
  `update_ts` timestamp NULL DEFAULT NULL COMMENT 'update timestamp',
  `delete_ind` char(1) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'N' COMMENT 'delete indicator',
  PRIMARY KEY (`role_ref_id`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='Roles defined';

--
-- Dumping data for table `roles`
--

INSERT INTO `roles` (`role_ref_id`, `role_nm`, `role_desc`, `create_user_ref_id`, `create_ts`, `update_user_ref_id`, `update_ts`, `delete_ind`) VALUES
(1, 'ADMIN', 'Administrator permissions', 1, '2021-07-08 17:20:11', 0, '2021-07-08 17:20:11', 'N'),
(2, 'USER', 'User permissions', 2, '2021-07-08 17:24:19', 0, '2021-07-08 17:24:19', 'N');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
CREATE TABLE IF NOT EXISTS `users` (
  `user_ref_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'User reference id',
  `first_name` varchar(125) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'First name',
  `last_name` varchar(125) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'Last name',
  `user_id` varchar(125) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'User id for active directory sign on',
  `alias` varchar(125) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'Unique user name',
  `create_user_ref_id` int(11) NOT NULL COMMENT 'create user id',
  `create_ts` timestamp NOT NULL COMMENT 'create timestamp',
  `update_user_ref_id` int(11) DEFAULT NULL COMMENT 'update user id',
  `update_ts` timestamp NULL DEFAULT NULL COMMENT 'update timestamp',
  `delete_ind` char(1) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'N' COMMENT 'delete indicator',
  PRIMARY KEY (`user_ref_id`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='Users information';

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`user_ref_id`, `first_name`, `last_name`, `user_id`, `alias`, `create_user_ref_id`, `create_ts`, `update_user_ref_id`, `update_ts`, `delete_ind`) VALUES
(1, 'RICHARD', 'SALAS', 'RTS000IS@ISU.NET', 'card', 2, '2021-07-08 17:27:21', NULL, NULL, 'N'),
(2, 'DBA', 'DBA', 'DBA000IS@ISU.NET', 'DBA', 2, '2021-07-08 17:28:08', NULL, NULL, 'N');

-- --------------------------------------------------------

--
-- Table structure for table `user_roles`
--

DROP TABLE IF EXISTS `user_roles`;
CREATE TABLE IF NOT EXISTS `user_roles` (
  `user_ref_id` int(11) NOT NULL COMMENT 'User reference id',
  `role_ref_id` int(11) NOT NULL COMMENT 'role reference id'
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='User roles';

--
-- Dumping data for table `user_roles`
--

INSERT INTO `user_roles` (`user_ref_id`, `role_ref_id`) VALUES
(1, 1);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
