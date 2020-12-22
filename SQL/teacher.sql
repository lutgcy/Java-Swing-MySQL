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

 Date: 22/12/2020 20:46:07
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for teacher
-- ----------------------------
DROP TABLE IF EXISTS `teacher`;
CREATE TABLE `teacher`  (
  `T_ID` int(11) NOT NULL,
  `name` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sex` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `age` int(11) NULL DEFAULT NULL,
  `phone` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `subject` int(11) NULL DEFAULT NULL,
  `passcode` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`T_ID`) USING BTREE,
  INDEX `aaaa_idx`(`subject`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of teacher
-- ----------------------------
INSERT INTO `teacher` VALUES (10001, '赵老师', '男', 29, '2345678', 1, 'abc');
INSERT INTO `teacher` VALUES (10002, '徐老师', '男', 31, '5345678', 2, 'abc');
INSERT INTO `teacher` VALUES (10003, '庞老师', '女', 28, '7634567', 3, 'abc');
INSERT INTO `teacher` VALUES (10004, '张老师', '男', 40, '45678', 4, 'abc');

SET FOREIGN_KEY_CHECKS = 1;
