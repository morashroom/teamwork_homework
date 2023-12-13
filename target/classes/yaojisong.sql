/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306_3
 Source Server Type    : MySQL
 Source Server Version : 80030
 Source Host           : localhost:3306
 Source Schema         : yaojisong

 Target Server Type    : MySQL
 Target Server Version : 80030
 File Encoding         : 65001

 Date: 01/03/2023 16:17:49
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for address_book
-- ----------------------------
DROP TABLE IF EXISTS `address_book`;
CREATE TABLE `address_book`  (
  `id` bigint NOT NULL COMMENT '主键',
  `user_id` bigint NOT NULL COMMENT '用户id',
  `consignee` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT '收货人',
  `sex` tinyint NOT NULL COMMENT '性别 0 女 1 男',
  `phone` varchar(11) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT '手机号',
  `province_code` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '省级区划编号',
  `province_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '省级名称',
  `city_code` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '市级区划编号',
  `city_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '市级名称',
  `district_code` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '区级区划编号',
  `district_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '区级名称',
  `detail` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '详细地址',
  `label` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '标签',
  `is_default` tinyint(1) NOT NULL DEFAULT 0 COMMENT '默认 0 否 1是',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `create_user` bigint NOT NULL COMMENT '创建人',
  `update_user` bigint NOT NULL COMMENT '修改人',
  `is_deleted` int NOT NULL DEFAULT 0 COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin COMMENT = '地址管理' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of address_book
-- ----------------------------
INSERT INTO `address_book` VALUES (1417414526093082626, 1417012167126876162, '小明', 1, '13812345678', NULL, NULL, NULL, NULL, NULL, NULL, '昌平区金燕龙办公楼', '公司', 1, '2021-07-20 17:22:12', '2021-07-20 17:26:33', 1417012167126876162, 1417012167126876162, 0);
INSERT INTO `address_book` VALUES (1417414926166769666, 1417012167126876162, '小李', 1, '13512345678', NULL, NULL, NULL, NULL, NULL, NULL, '测试', '家', 0, '2021-07-20 17:23:47', '2021-07-20 17:23:47', 1417012167126876162, 1417012167126876162, 0);
INSERT INTO `address_book` VALUES (1629442619670409217, 1629442372168724481, '刘钦志', 0, '15096375106', NULL, NULL, NULL, NULL, NULL, NULL, '大连海事大学', '公司', 0, '2023-02-25 19:26:09', '2023-02-25 19:26:09', 1629442372168724481, 1629442372168724481, 0);
INSERT INTO `address_book` VALUES (1629817919965220865, 1629442372168724481, '刘钦志', 1, '13873384594', NULL, NULL, NULL, NULL, NULL, NULL, '大连海事大学', '家', 0, '2023-02-26 20:17:27', '2023-02-26 20:17:35', 1629442372168724481, 1629442372168724481, 0);
INSERT INTO `address_book` VALUES (1629824365402476545, 1, '谭佳熙', 1, '13874394598', NULL, NULL, NULL, NULL, NULL, NULL, '大连海事大学', '公司', 1, '2023-02-26 20:43:04', '2023-02-26 20:43:16', 1, 1, 0);

-- ----------------------------
-- Table structure for category
-- ----------------------------
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category`  (
  `id` bigint NOT NULL COMMENT '主键',
  `type` int NULL DEFAULT NULL COMMENT '类型   1 药品分类 2 套餐分类',
  `name` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT '分类名称',
  `sort` int NOT NULL DEFAULT 0 COMMENT '顺序',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `create_user` bigint NOT NULL COMMENT '创建人',
  `update_user` bigint NOT NULL COMMENT '修改人',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_category_name`(`name` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin COMMENT = '药品及药品包分类' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of category
-- ----------------------------
INSERT INTO `category` VALUES (1397844263642378242, 1, '儿科用药', 1, '2021-05-27 09:16:58', '2023-02-08 10:10:21', 1, 1);
INSERT INTO `category` VALUES (1397844303408574465, 1, '感冒用药', 2, '2021-05-27 09:17:07', '2023-02-08 10:10:47', 1, 1);
INSERT INTO `category` VALUES (1397844391040167938, 1, '止痛镇痛', 3, '2021-05-27 09:17:28', '2023-02-08 10:11:18', 1, 1);
INSERT INTO `category` VALUES (1413341197421846529, 1, '口腔用药', 11, '2021-07-09 11:36:15', '2023-02-08 10:11:35', 1, 1);
INSERT INTO `category` VALUES (1413384954989060097, 1, '肝胆用药', 12, '2021-07-09 14:30:07', '2023-02-08 10:13:09', 1, 1);
INSERT INTO `category` VALUES (1623150314370031617, 2, '新冠用药', 13, '2023-02-08 10:42:46', '2023-02-08 10:42:46', 1, 1);

-- ----------------------------
-- Table structure for employee
-- ----------------------------
DROP TABLE IF EXISTS `employee`;
CREATE TABLE `employee`  (
  `id` bigint NOT NULL COMMENT '主键',
  `name` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT '姓名',
  `username` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT '用户名',
  `password` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT '密码',
  `phone` varchar(11) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT '手机号',
  `sex` varchar(2) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT '性别',
  `id_number` varchar(18) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT '身份证号',
  `status` int NOT NULL DEFAULT 1 COMMENT '状态 0:禁用，1:正常',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `create_user` bigint NOT NULL COMMENT '创建人',
  `update_user` bigint NOT NULL COMMENT '修改人',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_username`(`username` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin COMMENT = '员工信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of employee
-- ----------------------------
INSERT INTO `employee` VALUES (1, '管理员', 'admin', 'e10adc3949ba59abbe56e057f20f883e', '13812312312', '1', '110101199001010047', 1, '2021-05-06 17:20:07', '2021-05-10 02:24:09', 1, 1);
INSERT INTO `employee` VALUES (1623148636837175297, '谭佳熙', 'tanjiaxi', 'e10adc3949ba59abbe56e057f20f883e', '15066377106', '1', '990204230217174013', 1, '2023-02-08 10:36:06', '2023-02-08 10:36:06', 1, 1);
INSERT INTO `employee` VALUES (1624005444686508034, '张三', 'zhangsan', 'e10adc3949ba59abbe56e057f20f883e', '15096376106', '1', '402040200210174089', 1, '2023-02-10 19:20:45', '2023-02-10 19:20:45', 1, 1);

-- ----------------------------
-- Table structure for health_tab
-- ----------------------------
DROP TABLE IF EXISTS `health_tab`;
CREATE TABLE `health_tab`  (
  `id` bigint NOT NULL COMMENT '主键',
  `name` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT '姓名',
  `telephone` varchar(11) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT '手机号',
  `status` int NOT NULL DEFAULT 0 COMMENT '状态 0:否，1:是 健康的状态',
  `temperature` int NOT NULL DEFAULT 0 COMMENT '体温',
  `is_touchriskpeople` int NOT NULL DEFAULT 0 COMMENT '状态 0:否，1:是 是否接触中高风险地区的人',
  `is_touch` int NOT NULL DEFAULT 0 COMMENT '状态 0:否，1:是 是否是新冠病毒患者',
  `is_touchoverseas` int NOT NULL DEFAULT 0 COMMENT '状态 0:否，1:是 是否接触海外人员',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `name`(`name` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin COMMENT = '员工健康信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of health_tab
-- ----------------------------
INSERT INTO `health_tab` VALUES (1624028213822308354, '谭佳熙', '15096375109', 0, 38, 0, 0, 0);
INSERT INTO `health_tab` VALUES (1624029059226185730, '李哲', '12315', 1, 39, 0, 0, 0);

-- ----------------------------
-- Table structure for medicine
-- ----------------------------
DROP TABLE IF EXISTS `medicine`;
CREATE TABLE `medicine`  (
  `id` bigint NOT NULL COMMENT '主键',
  `name` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT '药品名称',
  `category_id` bigint NOT NULL COMMENT '药品分类id',
  `price` decimal(10, 2) NULL DEFAULT NULL COMMENT '药品价格',
  `code` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT '商品码',
  `image` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT '图片',
  `description` varchar(400) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT '描述信息',
  `status` int NOT NULL DEFAULT 1 COMMENT '0 停售 1 起售',
  `sort` int NOT NULL DEFAULT 0 COMMENT '顺序',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `create_user` bigint NOT NULL COMMENT '创建人',
  `update_user` bigint NOT NULL COMMENT '修改人',
  `is_deleted` int NOT NULL DEFAULT 0 COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_dish_name`(`name` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin COMMENT = '药品管理' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of medicine
-- ----------------------------
INSERT INTO `medicine` VALUES (1397849739276890114, '儿童退热贴', 1397844263642378242, 1000.00, '222222222', 'b4887f18-731f-45ac-a45f-b1a01390ef6d.jpg', '儿童快速退烧退热', 1, 0, '2021-05-27 09:38:43', '2023-02-28 23:19:11', 1, 1, 0);
INSERT INTO `medicine` VALUES (1397850140982161409, '三精蓝瓶锌', 1397844263642378242, 6800.00, '123412341234', '9cdc7b29-dea1-4308-9ff2-fe9ec0e7055f.jpg', '治疗腹泻，增强食欲', 1, 0, '2021-05-27 09:40:19', '2023-02-28 23:18:29', 1, 1, 0);
INSERT INTO `medicine` VALUES (1397850392090947585, '儿童钙片', 1397844263642378242, 4800.00, '123412341234', '4a9acbeb-ffbc-4d0c-a25c-a9f4044807c4.jpg', '进口优质钙片，中老年人专用', 1, 0, '2021-05-27 09:41:19', '2023-02-28 23:18:22', 1, 1, 0);
INSERT INTO `medicine` VALUES (1397850851245600769, '小儿咳喘口服液', 1397844263642378242, 2500.00, '123412341234', 'b35312a7-4d1c-43ee-99ef-57352047a15d.jpg', '治疗哮喘咳嗽专用', 1, 0, '2021-05-27 09:43:08', '2023-02-28 23:18:17', 1, 1, 0);
INSERT INTO `medicine` VALUES (1397851099502260226, '小儿感冒颗粒', 1397844263642378242, 3500.00, '23412341234', 'dc1c7017-04e2-46e7-a64e-11031cfb94e0.jpg', '中西药结合，专治小儿感冒头痛', 1, 0, '2021-05-27 09:44:08', '2023-02-28 23:14:43', 1, 1, 0);
INSERT INTO `medicine` VALUES (1397851370462687234, '小儿清肺化痰口服液', 1397844263642378242, 2800.00, '1246812345678', '8f27648d-076b-4b1e-94e6-90b0cc327e50.jpg', '儿科用药，适用于小儿感冒，发烧，咳嗽', 1, 0, '2021-05-27 09:45:12', '2023-03-01 15:52:16', 1, 1, 0);
INSERT INTO `medicine` VALUES (1397851668262465537, '小葵花颗粒', 1397844263642378242, 2400.00, '1234567812345678', 'a4273ef9-8cbc-4def-aadf-c853e2e1fce1.jpg', '不可和布洛芬一起使用 具有退烧功能', 1, 0, '2021-05-27 09:46:23', '2023-03-01 09:20:47', 1, 1, 0);
INSERT INTO `medicine` VALUES (1397852391150759938, '莲花清瘟', 1397844303408574465, 4500.00, '2346812468', 'd1e09658-0f14-441b-ae6e-84dd045ab5f9.jpg', '用于止咳退烧，治疗新冠有一定之功效', 1, 0, '2021-05-27 09:49:16', '2023-03-01 09:25:15', 1, 1, 0);
INSERT INTO `medicine` VALUES (1397853183287013378, '感冒疏风颗粒', 1397844303408574465, 2200.00, '123456787654321', '37e8f0ca-51dc-433f-b266-10819f32eb18.jpg', '用于春季流感时期的感冒疏风', 1, 0, '2021-05-27 09:52:24', '2023-03-01 09:17:09', 1, 1, 0);
INSERT INTO `medicine` VALUES (1397853709101740034, '止咳糖浆', 1397844303408574465, 9800.00, '1234321234321', '458974a3-2a2e-4f96-a9d0-4aab47e6f6a2.jpg', '补气养阴 润肺止咳', 1, 0, '2021-05-27 09:54:30', '2023-03-01 09:19:59', 1, 1, 0);
INSERT INTO `medicine` VALUES (1397853890262118402, '感冒灵', 1397844303408574465, 3800.00, '1234212321234', 'b37ca739-24ab-4c3a-8366-a856c08d5c7c.jpg', '感冒灵颗粒，用于治疗流行性感冒', 1, 0, '2021-05-27 09:55:13', '2023-03-01 09:18:16', 1, 1, 0);
INSERT INTO `medicine` VALUES (1397854652581064706, '止咳宝片', 1397844303408574465, 3000.00, '2345312·345321', '05926a92-ff35-4acc-8b7c-bd256868490c.jpg', '特异止咳宝，用于治疗咳嗽痰多，咳嗽气喘，慢性支气管炎。', 1, 0, '2021-05-27 09:58:15', '2023-03-01 09:16:26', 1, 1, 0);
INSERT INTO `medicine` VALUES (1397854865672679425, '抗病毒口服液', 1397844303408574465, 2000.00, '23456431·23456', 'e812a4c1-58f1-4c35-8130-2cae42bfc189.jpg', '用于风寒，感冒，祛湿，清热解毒之疗效', 1, 0, '2021-05-27 09:59:06', '2023-03-01 09:12:51', 1, 1, 0);
INSERT INTO `medicine` VALUES (1397860242057375745, '布洛芬', 1397844391040167938, 10000.00, '123456786543213456', '966be7f6-d7ed-47aa-87d4-730cf8ddeac9.jpg', '家庭常备解热镇痛药', 1, 0, '2021-05-27 10:20:27', '2023-03-01 09:21:34', 1, 1, 0);
INSERT INTO `medicine` VALUES (1397860578738352129, '丹珍头痛胶囊', 1397844391040167938, 6600.00, '12345678654', '1b2d9562-b719-471c-a36e-3a0fcc308f7a.jpg', '具有止痛镇痛，治疗偏头痛之功效', 1, 0, '2021-05-27 10:21:48', '2023-03-01 09:12:19', 1, 1, 0);
INSERT INTO `medicine` VALUES (1397860792492666881, '新癀片', 1397844391040167938, 3300.00, '213456432123456', '69ba2b84-a66b-40ad-9258-a13cc6034d30.jpg', '具有止痛镇痛，祛风湿之神奇疗效。', 1, 0, '2021-05-27 10:22:39', '2023-03-01 09:11:38', 1, 1, 0);
INSERT INTO `medicine` VALUES (1397860963880316929, '四妙丸', 1397844391040167938, 10800.00, '1234563212345', '6e001927-f1f1-4c64-878d-65e977e09eda.jpg', '可用于局部性外伤', 1, 0, '2021-05-27 10:23:19', '2023-03-01 09:16:48', 1, 1, 0);
INSERT INTO `medicine` VALUES (1397861683434139649, '沙利度胺片', 1397844391040167938, 38800.00, '1234567876543213456', '1fb9075e-241e-4fd4-b2b5-7e3cd980e2fc.jpg', '用于止痛镇痛，开水口服使用最佳', 1, 0, '2021-05-27 10:26:11', '2023-03-01 09:23:34', 1, 1, 0);
INSERT INTO `medicine` VALUES (1397862198033297410, '复方羊角颗粒', 1397844391040167938, 3000.00, '123456786532455', '86ea9b58-c4a8-4995-8d0a-5c9df714b930.jpg', '颗粒状药物，中药出品，具有止痛阵痛之疗效', 1, 0, '2021-05-27 10:28:14', '2023-03-01 09:14:12', 1, 1, 0);
INSERT INTO `medicine` VALUES (1397862477831122945, '重感灵片', 1397844391040167938, 1000.00, '1234567865432', '15435f56-4b65-48c6-aa67-8598d9734f24.jpg', '解读清热，疏风止痛，用于治疗重感冒', 1, 0, '2021-05-27 10:29:20', '2023-03-01 09:15:28', 1, 1, 0);
INSERT INTO `medicine` VALUES (1413342036832100354, '蜂胶口腔膜', 1413341197421846529, 500.00, '', '52d511ca-abe7-41aa-a0dc-4b21a4c87923.jpg', '', 1, 0, '2021-07-09 11:39:35', '2023-02-28 23:20:54', 1, 1, 0);
INSERT INTO `medicine` VALUES (1413384757047271425, '口炎清颗粒', 1413341197421846529, 2500.00, '', '93eec35c-95be-446a-a3d3-26f94c74dde5.jpg', '', 1, 0, '2021-07-09 14:29:20', '2023-02-28 23:20:48', 1, 1, 0);
INSERT INTO `medicine` VALUES (1413385247889891330, '复方胆通片', 1413384954989060097, 6000.00, '', 'd76ab8b6-02f0-4a77-b12a-c6cd6923f538.jpg', '', 1, 0, '2021-07-09 14:31:17', '2023-02-08 10:25:18', 1, 1, 0);

-- ----------------------------
-- Table structure for medicine_requirement
-- ----------------------------
DROP TABLE IF EXISTS `medicine_requirement`;
CREATE TABLE `medicine_requirement`  (
  `id` bigint NOT NULL COMMENT '主键',
  `medicine_id` bigint NULL DEFAULT NULL,
  `name` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT '药品需求名称',
  `value` varchar(500) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT '药品需求数据list',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `create_user` bigint NOT NULL COMMENT '创建人',
  `update_user` bigint NOT NULL COMMENT '修改人',
  `is_deleted` int NOT NULL DEFAULT 0 COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin COMMENT = '药品需求和药品关系表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of medicine_requirement
-- ----------------------------
INSERT INTO `medicine_requirement` VALUES (1630733828443484161, 1397861683434139649, '缓急', '[\"普通\",\"急\",\"加急\",\"特急\"]', '2023-03-01 09:23:34', '2023-03-01 09:23:34', 1, 1, 0);
INSERT INTO `medicine_requirement` VALUES (1630838348276174850, 1397851370462687234, '缓急', '[\"普通\",\"急\",\"加急\",\"特急\"]', '2023-03-01 15:52:16', '2023-03-01 15:52:16', 1, 1, 0);

-- ----------------------------
-- Table structure for order_detail
-- ----------------------------
DROP TABLE IF EXISTS `order_detail`;
CREATE TABLE `order_detail`  (
  `id` bigint NOT NULL COMMENT '主键',
  `name` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT '名字',
  `image` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT '图片',
  `order_id` bigint NOT NULL COMMENT '订单id',
  `medicine_id` bigint NULL DEFAULT NULL,
  `packages_id` bigint NULL DEFAULT NULL,
  `number` int NOT NULL DEFAULT 1 COMMENT '数量',
  `amount` decimal(10, 2) NOT NULL COMMENT '金额',
  `dish_flavor` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin COMMENT = '订单明细表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of order_detail
-- ----------------------------
INSERT INTO `order_detail` VALUES (1629694822732357634, '儿童退热贴', 'b4887f18-731f-45ac-a45f-b1a01390ef6d.jpg', 1629694822665248769, 1397849739276890114, NULL, 1, 10.00, NULL);
INSERT INTO `order_detail` VALUES (1629695707428511745, '小儿感冒颗粒', 'dc1c7017-04e2-46e7-a64e-11031cfb94e0.jpg', 1629695707378180098, 1397851099502260226, NULL, 1, 35.00, NULL);
INSERT INTO `order_detail` VALUES (1629799827037937666, '小儿感冒颗粒', 'dc1c7017-04e2-46e7-a64e-11031cfb94e0.jpg', 1629799826970828802, 1397851099502260226, NULL, 1, 35.00, NULL);
INSERT INTO `order_detail` VALUES (1629800594801090562, '小儿感冒颗粒', 'dc1c7017-04e2-46e7-a64e-11031cfb94e0.jpg', 1629800594801090561, 1397851099502260226, NULL, 1, 35.00, NULL);
INSERT INTO `order_detail` VALUES (1629802502391537666, '小儿感冒颗粒', 'dc1c7017-04e2-46e7-a64e-11031cfb94e0.jpg', 1629802502324428801, 1397851099502260226, NULL, 1, 35.00, NULL);
INSERT INTO `order_detail` VALUES (1629805543933022210, '小儿感冒颗粒', 'dc1c7017-04e2-46e7-a64e-11031cfb94e0.jpg', 1629805543865913345, 1397851099502260226, NULL, 1, 35.00, NULL);

-- ----------------------------
-- Table structure for orders
-- ----------------------------
DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders`  (
  `id` bigint NOT NULL COMMENT '主键',
  `number` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT '订单号',
  `status` int NOT NULL DEFAULT 1 COMMENT '订单状态 1待付款，2待派送，3已派送，4已完成，5已取消',
  `user_id` bigint NOT NULL COMMENT '下单用户',
  `address_book_id` bigint NOT NULL COMMENT '地址id',
  `order_time` datetime NOT NULL COMMENT '下单时间',
  `checkout_time` datetime NOT NULL COMMENT '结账时间',
  `pay_method` int NOT NULL DEFAULT 1 COMMENT '支付方式 1微信,2支付宝',
  `amount` decimal(10, 2) NOT NULL COMMENT '实收金额',
  `remark` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT '备注',
  `phone` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `address` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `user_name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `consignee` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin COMMENT = '订单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of orders
-- ----------------------------
INSERT INTO `orders` VALUES (1629694822665248769, '1629694822665248769', 2, 1629442372168724481, 1629442712284835842, '2023-02-26 12:08:19', '2023-02-26 12:08:19', 1, 10.00, '', '15096375105', '大连海事大学', NULL, '刘钦志');
INSERT INTO `orders` VALUES (1629695707378180098, '1629695707378180098', 2, 1629442372168724481, 1629442712284835842, '2023-02-26 12:11:50', '2023-02-26 12:11:50', 1, 35.00, '', '15096375105', '大连海事大学', NULL, '刘钦志');
INSERT INTO `orders` VALUES (1629799826970828802, '1629799826970828802', 2, 1629442372168724481, 1629442712284835842, '2023-02-26 19:05:34', '2023-02-26 19:05:34', 1, 35.00, '', '15096375105', '大连海事大学', NULL, '刘钦志');
INSERT INTO `orders` VALUES (1629800594801090561, '1629800594801090561', 3, 1629442372168724481, 1629442712284835842, '2023-02-26 19:08:37', '2023-02-26 19:08:37', 1, 35.00, '', '15096375105', '大连海事大学', NULL, '刘钦志');
INSERT INTO `orders` VALUES (1629802502324428801, '1629802502324428801', 3, 1629442372168724481, 1629442712284835842, '2023-02-26 19:16:11', '2023-02-26 19:16:11', 1, 35.00, '', '15096375105', '大连海事大学', NULL, '刘钦志');
INSERT INTO `orders` VALUES (1629805543865913345, '1629805543865913345', 3, 1629442372168724481, 1629442712284835842, '2023-02-26 19:28:17', '2023-02-26 19:28:17', 1, 35.00, '', '15096375105', '大连海事大学', NULL, '刘钦志');

-- ----------------------------
-- Table structure for packages
-- ----------------------------
DROP TABLE IF EXISTS `packages`;
CREATE TABLE `packages`  (
  `id` bigint NOT NULL COMMENT '主键',
  `category_id` bigint NOT NULL COMMENT '药品分类id',
  `name` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT '套餐名称',
  `price` decimal(10, 2) NOT NULL COMMENT '套餐价格',
  `status` int NULL DEFAULT NULL COMMENT '状态 0:停用 1:启用',
  `code` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT '编码',
  `description` varchar(512) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT '描述信息',
  `image` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT '图片',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `create_user` bigint NOT NULL COMMENT '创建人',
  `update_user` bigint NOT NULL COMMENT '修改人',
  `is_deleted` int NOT NULL DEFAULT 0 COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_setmeal_name`(`name` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin COMMENT = '套餐' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of packages
-- ----------------------------
INSERT INTO `packages` VALUES (1623151559268818945, 1623150314370031617, '新冠用药包', 35000.00, 1, '', '', '59c440e1-c21d-4b55-a4af-d6318e9fab04.jpg', '2023-02-08 10:47:43', '2023-02-08 10:47:43', 1, 1, 0);

-- ----------------------------
-- Table structure for packages_medicine
-- ----------------------------
DROP TABLE IF EXISTS `packages_medicine`;
CREATE TABLE `packages_medicine`  (
  `id` bigint NOT NULL COMMENT '主键',
  `packages_id` bigint NULL DEFAULT NULL,
  `medicine_id` bigint NULL DEFAULT NULL,
  `name` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT '药品名称 （冗余字段）',
  `price` decimal(10, 2) NULL DEFAULT NULL COMMENT '药品原价（冗余字段）',
  `copies` int NOT NULL COMMENT '份数',
  `sort` int NOT NULL DEFAULT 0 COMMENT '排序',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `create_user` bigint NOT NULL COMMENT '创建人',
  `update_user` bigint NOT NULL COMMENT '修改人',
  `is_deleted` int NOT NULL DEFAULT 0 COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin COMMENT = '套餐药品关系' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of packages_medicine
-- ----------------------------
INSERT INTO `packages_medicine` VALUES (1623151559268818946, 1623151559268818945, 1397853183287013378, '感冒疏风颗粒', 2200.00, 1, 0, '2023-02-08 10:47:43', '2023-02-08 10:47:43', 1, 1, 0);
INSERT INTO `packages_medicine` VALUES (1623151559268818947, 1623151559268818945, 1397850851245600769, '小儿咳喘口服液', 2500.00, 1, 0, '2023-02-08 10:47:43', '2023-02-08 10:47:43', 1, 1, 0);
INSERT INTO `packages_medicine` VALUES (1623151559268818948, 1623151559268818945, 1397860242057375745, '布洛芬', 10000.00, 1, 0, '2023-02-08 10:47:43', '2023-02-08 10:47:43', 1, 1, 0);

-- ----------------------------
-- Table structure for shopping_cart
-- ----------------------------
DROP TABLE IF EXISTS `shopping_cart`;
CREATE TABLE `shopping_cart`  (
  `id` bigint NOT NULL COMMENT '主键',
  `name` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT '名称',
  `image` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT '图片',
  `user_id` bigint NOT NULL COMMENT '主键',
  `medicine_id` bigint NULL DEFAULT NULL,
  `packages_id` bigint NULL DEFAULT NULL,
  `medicine_requirement` bigint NULL DEFAULT NULL,
  `number` int NOT NULL DEFAULT 1 COMMENT '数量',
  `amount` decimal(10, 2) NOT NULL COMMENT '金额',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin COMMENT = '购物车' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of shopping_cart
-- ----------------------------

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` bigint NOT NULL COMMENT '主键',
  `name` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT '姓名',
  `phone` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT '手机号',
  `sex` varchar(2) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT '性别',
  `id_number` varchar(18) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT '身份证号',
  `avatar` varchar(500) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT '头像',
  `status` int NULL DEFAULT 0 COMMENT '状态 0:禁用，1:正常',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin COMMENT = '用户信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1629442372168724481, NULL, '2717557144@qq.com', NULL, NULL, NULL, 1);

SET FOREIGN_KEY_CHECKS = 1;
