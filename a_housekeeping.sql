/*
 Navicat MySQL Data Transfer

 Source Server         : taobishe
 Source Server Type    : MySQL
 Source Server Version : 50734
 Source Host           : 1.116.117.222:3306
 Source Schema         : a_housekeeping

 Target Server Type    : MySQL
 Target Server Version : 50734
 File Encoding         : 65001

 Date: 04/11/2022 11:27:24
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for appoint_advert
-- ----------------------------
DROP TABLE IF EXISTS `appoint_advert`;
CREATE TABLE `appoint_advert`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pic_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图片地址',
  `enable` tinyint(4) NULL DEFAULT NULL COMMENT '是否启用，0：禁用，1：启用',
  `link` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '链接',
  `sort` int(11) NULL DEFAULT NULL COMMENT '排序',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '广告' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of appoint_advert
-- ----------------------------
INSERT INTO `appoint_advert` VALUES (2, 'http://localhost:8080/img/banner-2.jpg', 1, NULL, 2, '2020-10-31 13:45:13');
INSERT INTO `appoint_advert` VALUES (5, 'http://localhost:8080/img/banner-1.jpg', 1, NULL, 1, '2021-02-12 16:16:25');

-- ----------------------------
-- Table structure for appoint_category
-- ----------------------------
DROP TABLE IF EXISTS `appoint_category`;
CREATE TABLE `appoint_category`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `category_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '分类名称',
  `pic_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图片',
  `sort` int(11) NULL DEFAULT NULL COMMENT '排序',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 21 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '分类' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of appoint_category
-- ----------------------------
INSERT INTO `appoint_category` VALUES (18, '日常保洁', NULL, 1, '2021-05-22 11:35:45');
INSERT INTO `appoint_category` VALUES (19, '家电清洗', NULL, 2, '2021-05-22 11:35:55');
INSERT INTO `appoint_category` VALUES (20, '维修服务', NULL, 3, '2021-07-04 09:56:00');

-- ----------------------------
-- Table structure for appoint_evaluation
-- ----------------------------
DROP TABLE IF EXISTS `appoint_evaluation`;
CREATE TABLE `appoint_evaluation`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `order_id` int(11) NULL DEFAULT NULL COMMENT '订单id',
  `member_id` int(11) NULL DEFAULT NULL COMMENT '用户id',
  `content` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '评价内容',
  `star` int(11) NULL DEFAULT NULL,
  `project_id` int(11) NULL DEFAULT NULL COMMENT '项目id',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '评价时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '订单评价' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of appoint_evaluation
-- ----------------------------
INSERT INTO `appoint_evaluation` VALUES (12, 8, 1, '服务很好', NULL, 5, '2021-05-22 12:14:54');

-- ----------------------------
-- Table structure for appoint_order
-- ----------------------------
DROP TABLE IF EXISTS `appoint_order`;
CREATE TABLE `appoint_order`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `order_number` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '订单编号',
  `project_id` int(11) NULL DEFAULT NULL COMMENT '项目',
  `user_id` int(11) NULL DEFAULT NULL COMMENT '用户',
  `teacher_id` int(11) NULL DEFAULT NULL COMMENT '老师id',
  `teacher_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `project_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `pic_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `appoint_time` datetime(0) NULL DEFAULT NULL COMMENT '预约时间',
  `time_id` int(11) NULL DEFAULT NULL,
  `time` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `status` tinyint(2) NULL DEFAULT NULL COMMENT '状态',
  `total_amount` decimal(10, 2) NULL DEFAULT NULL COMMENT '金额',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 27 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '预约' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of appoint_order
-- ----------------------------
INSERT INTO `appoint_order` VALUES (23, '20220901359169', 5, 1, NULL, '王老师', '空调家电清洗', 'http://localhost:8080/img/goods-3.jpg', '2022-09-01 00:00:00', 52, '08:00~10:00', 2, 120.00, '2022-09-01 18:30:12');
INSERT INTO `appoint_order` VALUES (25, '20220901302481', 4, 1, NULL, '王老师', '家政上门保洁', 'http://localhost:8080/img/goods-2.jpg', '2022-09-01 00:00:00', 51, '11:00~12:00', 1, 40.00, '2022-09-01 18:30:30');
INSERT INTO `appoint_order` VALUES (26, '20220901009046', 3, 1, NULL, '王老师', '深度清洁专业阿姨上门服务', 'http://localhost:8080/img/goods-1.jpg', '2022-09-01 00:00:00', 49, '15:00~16:00', 3, 100.00, '2022-09-01 18:30:43');

-- ----------------------------
-- Table structure for appoint_order_address
-- ----------------------------
DROP TABLE IF EXISTS `appoint_order_address`;
CREATE TABLE `appoint_order_address`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键自增',
  `order_id` int(11) NULL DEFAULT NULL COMMENT '订单ID',
  `contacts` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '联系人',
  `mobile` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手机号码',
  `province_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '省名称',
  `city_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '城市名称',
  `district_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '地区名称',
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '详细地址',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 23 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '订单地址' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of appoint_order_address
-- ----------------------------
INSERT INTO `appoint_order_address` VALUES (20, 23, '李老师', '18112907714', '北京市', '北京市', '西城区', '101号', NULL);
INSERT INTO `appoint_order_address` VALUES (21, 25, '李老师', '18112907714', '北京市', '北京市', '西城区', '101号', NULL);
INSERT INTO `appoint_order_address` VALUES (22, 26, '微服汇', '18021418906', '北京市', '北京市', '东城区', '101号', NULL);

-- ----------------------------
-- Table structure for appoint_project
-- ----------------------------
DROP TABLE IF EXISTS `appoint_project`;
CREATE TABLE `appoint_project`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `project_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '项目名称',
  `price` decimal(10, 2) NULL DEFAULT NULL COMMENT '价格',
  `remark` text CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL COMMENT '备注',
  `pic_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '图片',
  `category_id` int(11) NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '项目' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of appoint_project
-- ----------------------------
INSERT INTO `appoint_project` VALUES (3, '深度清洁专业阿姨上门服务', 100.00, '测试预约', 'http://localhost:8080/img/goods-1.jpg', 18, '2021-02-24 17:17:42');
INSERT INTO `appoint_project` VALUES (4, '家政上门保洁', 40.00, '欢迎欢迎', 'http://localhost:8080/img/goods-2.jpg', 18, '2021-04-02 19:51:24');
INSERT INTO `appoint_project` VALUES (5, '空调家电清洗', 120.00, '欢迎预约', 'http://localhost:8080/img/goods-3.jpg', 19, '2021-04-02 19:51:43');

-- ----------------------------
-- Table structure for appoint_setting
-- ----------------------------
DROP TABLE IF EXISTS `appoint_setting`;
CREATE TABLE `appoint_setting`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `teacher_id` int(11) NULL DEFAULT NULL COMMENT '技师id',
  `start_time` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '开始时间',
  `end_time` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '结束时间',
  `max_people` int(11) NULL DEFAULT NULL COMMENT '预约人数',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '预约设置' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of appoint_setting
-- ----------------------------
INSERT INTO `appoint_setting` VALUES (10, 3, '08:00', '10:00', 1);
INSERT INTO `appoint_setting` VALUES (11, 3, '11:00', '12:00', 1);
INSERT INTO `appoint_setting` VALUES (12, 3, '13:00', '14:00', 1);
INSERT INTO `appoint_setting` VALUES (13, 3, '15:00', '16:00', 1);

-- ----------------------------
-- Table structure for appoint_teacher
-- ----------------------------
DROP TABLE IF EXISTS `appoint_teacher`;
CREATE TABLE `appoint_teacher`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pic_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '头像',
  `real_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '技师名称',
  `mobile` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '电话',
  `specialty` text CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL COMMENT '特长',
  `remark` text CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL COMMENT '备注',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '老师' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of appoint_teacher
-- ----------------------------
INSERT INTO `appoint_teacher` VALUES (2, 'http://localhost:8080/img/avatar.png', '李老师', '18112907714', 'java', 'java springboot vue 小程序', '2021-03-23 21:37:02');
INSERT INTO `appoint_teacher` VALUES (3, 'http://localhost:8080/img/avatar-2.png', '王老师', '18112907714', 'springcloud vue', 'java springcloud vue android', '2021-03-24 09:04:33');

-- ----------------------------
-- Table structure for appoint_time
-- ----------------------------
DROP TABLE IF EXISTS `appoint_time`;
CREATE TABLE `appoint_time`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `teacher_id` int(11) NULL DEFAULT NULL COMMENT '技师id',
  `appoint_date` date NULL DEFAULT NULL COMMENT '预约日期',
  `start_time` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '开始时间',
  `end_time` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '结束时间',
  `status` tinyint(2) NULL DEFAULT NULL COMMENT '状态',
  `people` int(11) NULL DEFAULT NULL COMMENT '预约人数',
  `max_people` int(11) NULL DEFAULT NULL COMMENT '最大预约',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 53 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '预约时间' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of appoint_time
-- ----------------------------
INSERT INTO `appoint_time` VALUES (45, 3, '2022-08-31', '15:00', '16:00', 1, 0, 1);
INSERT INTO `appoint_time` VALUES (46, 3, '2022-08-31', '13:00', '14:00', 1, 0, 1);
INSERT INTO `appoint_time` VALUES (47, 3, '2022-08-31', '11:00', '12:00', 1, 0, 1);
INSERT INTO `appoint_time` VALUES (48, 3, '2022-08-31', '08:00', '10:00', 1, 0, 1);
INSERT INTO `appoint_time` VALUES (49, 3, '2022-09-01', '15:00', '16:00', 1, 0, 1);
INSERT INTO `appoint_time` VALUES (50, 3, '2022-09-01', '13:00', '14:00', 1, 0, 1);
INSERT INTO `appoint_time` VALUES (51, 3, '2022-09-01', '11:00', '12:00', 1, 0, 1);
INSERT INTO `appoint_time` VALUES (52, 3, '2022-09-01', '08:00', '10:00', 1, 0, 1);

-- ----------------------------
-- Table structure for base_member
-- ----------------------------
DROP TABLE IF EXISTS `base_member`;
CREATE TABLE `base_member`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nickname` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '昵称',
  `avatar_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '头像',
  `gender` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '性别',
  `real_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '姓名',
  `mobile` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手机号码',
  `login_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '登录账号',
  `password` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '会员' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of base_member
-- ----------------------------
INSERT INTO `base_member` VALUES (1, '清风', 'https://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTK3JVibuZg8wiaKG9ExyVJJT2R4s398eropw2qU7GhJEwgwNB8Y56GWh4dDHPSYTNcJXgmkvz4809SA/132', '1', '微服汇', '18112907714', 'test', '123456', '2022-05-04 17:10:30');
INSERT INTO `base_member` VALUES (2, '微服汇', 'https://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTK3JVibuZg8wiaKG9ExyVJJT2R4s398eropw2qU7GhJEwgwNB8Y56GWh4dDHPSYTNcJXgmkvz4809SA/132', NULL, '李老师', NULL, 'qwer', '123456', '2022-06-16 16:18:11');
INSERT INTO `base_member` VALUES (3, '微服汇科技', 'https://thirdwx.qlogo.cn/mmopen/vi_32/pIK8DLZibYyGhNSMOgiasic3ruOQROibd9TPZHhEwibuicpTIJbKdnF3V2k3TLsicOeTjKDPBOicVE7jljp273gvmA1dPA/132', '0', NULL, NULL, NULL, NULL, '2022-08-31 19:35:38');

-- ----------------------------
-- Table structure for base_member_address
-- ----------------------------
DROP TABLE IF EXISTS `base_member_address`;
CREATE TABLE `base_member_address`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `member_id` int(11) NULL DEFAULT NULL COMMENT '会员id',
  `contacts` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '联系人',
  `mobile` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手机',
  `province_id` int(11) NULL DEFAULT NULL COMMENT '省',
  `province_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `city_id` int(11) NULL DEFAULT NULL COMMENT '市',
  `city_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `district_id` int(11) NULL DEFAULT NULL COMMENT '区',
  `district_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `address` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '详细地址',
  `zipcode` int(11) NULL DEFAULT NULL COMMENT '邮编',
  `dft` tinyint(1) NULL DEFAULT NULL COMMENT '默认地址',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 16 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户地址' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of base_member_address
-- ----------------------------
INSERT INTO `base_member_address` VALUES (10, 1, '张三', '18112907714', NULL, '北京市', NULL, '北京市', NULL, '东城区', '测试101', NULL, 0, '2021-04-06 21:08:56');
INSERT INTO `base_member_address` VALUES (13, 1, '测试', '18112907714', NULL, '河北省', NULL, '秦皇岛市', NULL, '海港区', '测试', NULL, 0, '2021-04-14 14:50:01');
INSERT INTO `base_member_address` VALUES (14, 1, '微服汇', '18021418906', NULL, '北京市', NULL, '北京市', NULL, '东城区', '101号', NULL, 0, '2022-03-25 21:36:33');
INSERT INTO `base_member_address` VALUES (15, 1, '李老师', '18112907714', NULL, '北京市', NULL, '北京市', NULL, '西城区', '101号', NULL, 1, '2022-03-25 21:43:03');

-- ----------------------------
-- Table structure for base_member_auth
-- ----------------------------
DROP TABLE IF EXISTS `base_member_auth`;
CREATE TABLE `base_member_auth`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `member_id` int(11) NULL DEFAULT NULL COMMENT '用户ID',
  `openid` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT 'openid',
  `auth_type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '授权类型',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `OPENID_UNIQUE`(`openid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '授权' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of base_member_auth
-- ----------------------------
INSERT INTO `base_member_auth` VALUES (1, 1, 'o1HEb0bbQgdd1aPNyr2ZXvutSU8U', 'wechat', '2022-05-04 17:10:30');
INSERT INTO `base_member_auth` VALUES (2, 3, 'o1HEb0T_X7fKBCNnwVsyAvon4H14', 'wechat', '2022-08-31 19:35:38');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名',
  `password` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码',
  `email` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `mobile` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手机号',
  `status` tinyint(4) NULL DEFAULT NULL COMMENT '状态  0：禁用   1：正常',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `username`(`username`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '管理员' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, 'admin', '21232f297a57a5a743894a0e4a801fc3', '2803180149@qq.com', '18021418906', 1, '2021-12-29 11:11:11');

SET FOREIGN_KEY_CHECKS = 1;
