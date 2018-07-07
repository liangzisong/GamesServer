/*
 Navicat MySQL Data Transfer

 Source Server         : mysql
 Source Server Type    : MySQL
 Source Server Version : 50560
 Source Host           : localhost:3306
 Source Schema         : yhmp

 Target Server Type    : MySQL
 Target Server Version : 50560
 File Encoding         : 65001

 Date: 20/06/2018 17:25:08
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for declare_project
-- ----------------------------
DROP TABLE IF EXISTS `declare_project`;
CREATE TABLE `declare_project`  (
  `id` int(255) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `declareProject` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '项目名称',
  `declareNumber` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '项目编号',
  `declareFraction` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '项目标准分',
  `declareScale` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '项目规模',
  `declareArrange` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '项目类型：整理',
  `declareAssess` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '项目类型 ：评估',
  `declareSurveying` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '项目类型：测绘',
  `declarePlan` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '项目类型：规划',
  `contract` int(3) DEFAULT NULL COMMENT '合同签订情况：1为签订，0为未签订',
  `declareCompany` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '单位名称',
  `declareName` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '联系人姓名',
  `declarePhone` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '联系人电话',
  `finishTime` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '项目完成时间',
  `quality` int(10) DEFAULT NULL COMMENT '项目质量要求：1为优秀，2为良好，3为合格',
  `Bid` int(10) DEFAULT NULL COMMENT '是否需要招投标：1为需要，2为不需要，3为不确定',
  `responsibilityName` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '负责人姓名',
  `responsibilityPhone` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '负责人电话',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of declare_project
-- ----------------------------
INSERT INTO `declare_project` VALUES (3, '项', '项目编号', '项目标准分', '项目规模', '项目类型：整理', '项目类型 ：评估', '项目类型：测绘', '项目类型：规划', 1, '单位名称', '联系人姓名', '联系人电话', '2018-03-20', 1, 1, '负责人姓名', '负责人电话');
INSERT INTO `declare_project` VALUES (6, '测绘1234', '001', '10分', '挺大', '123', '456', '789', '10', 1, '山东元鸿', '张三', '18888888888', '2018-03-20', 2, 2, '李四', '18888888888');
INSERT INTO `declare_project` VALUES (9, '项目名称', '项目编号', '项目标准分:', '项目规模', '整理：', '评估：', '测绘', '规划：', 1, '单位名称', '联系人姓名:', '系人电话：', '2018-02-28', 2, 2, '责人姓名', '人电话：');
INSERT INTO `declare_project` VALUES (10, '基础测绘项目', '项目编号', '项目标准分', '项目规模', '项目类型：整理', '项目类型 ：评估', '项目类型：测绘', '项目类型：规划', 1, '单位名称', '联系人姓名', '联系人电话', '2018-03-20', 2, 2, '负责人姓名', '负责人电话');

-- ----------------------------
-- Table structure for plan
-- ----------------------------
DROP TABLE IF EXISTS `plan`;
CREATE TABLE `plan`  (
  `id` int(11) NOT NULL COMMENT 'id与declare_project的主键id对应',
  `planSchedule` int(11) DEFAULT NULL,
  `declareProject` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '项目名称',
  `responsibilityName` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '项目负责人',
  `projectPlan` int(11) NOT NULL,
  `projectContent` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '项目计划内容',
  `finishTime` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '项目计划完成时间',
  `personLiable` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '项目成员',
  INDEX `id`(`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of plan
-- ----------------------------
INSERT INTO `plan` VALUES (9, 56, '项目名称', '责人姓名', 1, '11', '2018-06-15', '111');
INSERT INTO `plan` VALUES (9, 78, '项目名称', '责人姓名', 2, '111', '2018-06-15', '111');
INSERT INTO `plan` VALUES (6, 3, '测绘1234', '李四', 1, '222', '2018-06-08', '222');
INSERT INTO `plan` VALUES (3, 5, '项', '负责人姓名', 1, '333', '2018-06-03', '333');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(255) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '000000',
  `job` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `department` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `hiredate` varchar(25) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '',
  `phone` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `status` int(3) NOT NULL,
  `domicile` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `sex` int(2) DEFAULT NULL,
  `age` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `authority` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '0' COMMENT '用户权限',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (-1, 'admin', 'admin', '管理员', '1', '2018-04-09', '1122334455', 0, '山东省', 1, '18', NULL);
INSERT INTO `user` VALUES (2, 'zhangsan', '5252', 'ad', '5部', '2018-05-20', '45454545', 0, '山东省', 0, '8', '0,2,14,3,16');
INSERT INTO `user` VALUES (5, 'abc', '000000', '职位', '四部', '2018-05-31', '15555555555', 2, '山东省', 1, '50', NULL);
INSERT INTO `user` VALUES (6, 'abc454', '000000', '职位', '四部', '2018-06-06', '15555555555', 0, '山东省', 1, '18', '0,2,14,3,16,17');
INSERT INTO `user` VALUES (7, 'abc454sad', '000000', '职位', '四部', '2018-06-05', '15555555555', 0, '山东省', 1, '18', NULL);
INSERT INTO `user` VALUES (8, '21651', '000000', '方式', '是梵蒂冈', '2018-16-12', '161616566', 0, '四大', 1, '18', NULL);

SET FOREIGN_KEY_CHECKS = 1;
