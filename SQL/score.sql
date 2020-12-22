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

 Date: 22/12/2020 20:45:53
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for score
-- ----------------------------
DROP TABLE IF EXISTS `score`;
CREATE TABLE `score`  (
  `S_ID` int(11) NOT NULL,
  `T_ID` int(11) NOT NULL,
  `grade` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`S_ID`, `T_ID`) USING BTREE,
  INDEX `ss_idx`(`S_ID`) USING BTREE,
  INDEX `tt_idx`(`T_ID`) USING BTREE,
  CONSTRAINT `ss` FOREIGN KEY (`S_ID`) REFERENCES `student` (`S_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `tt` FOREIGN KEY (`T_ID`) REFERENCES `teacher` (`T_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of score
-- ----------------------------
INSERT INTO `score` VALUES (20001, 10001, 80);

SET FOREIGN_KEY_CHECKS = 1;
