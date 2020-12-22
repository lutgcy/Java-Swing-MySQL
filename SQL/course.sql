/*
 Navicat Premium Data Transfer

 Source Server         : MYSQL
 Source Server Type    : MySQL
 Source Server Version : 50728
 Source Host           : localhost:3306
 Source Schema         : ssms

 Target Server Type    : MySQL
 Target Server Version : 50728
 File Encoding         : 65001

 Date: 22/12/2020 20:45:38
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for course
-- ----------------------------
DROP TABLE IF EXISTS `course`;
CREATE TABLE `course`  (
  `Cno` int(11) NOT NULL,
  `Cname` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`Cno`) USING BTREE,
  CONSTRAINT `aa` FOREIGN KEY (`Cno`) REFERENCES `teacher` (`subject`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of course
-- ----------------------------
INSERT INTO `course` VALUES (1, 'Java');
INSERT INTO `course` VALUES (2, 'C++');
INSERT INTO `course` VALUES (3, '数据库');
INSERT INTO `course` VALUES (4, '数据结构');

SET FOREIGN_KEY_CHECKS = 1;
