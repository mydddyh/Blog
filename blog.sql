CREATE DATABASE  IF NOT EXISTS `blog_db` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `blog_db`;
-- MySQL dump 10.13  Distrib 8.0.32, for Win64 (x86_64)
--
-- Host: 47.115.211.204    Database: blog_db
-- ------------------------------------------------------
-- Server version	8.0.32-0ubuntu0.20.04.2

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
-- Table structure for table `sys_menu`
--

DROP TABLE IF EXISTS `sys_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_menu` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
  `menu_name` varchar(50) NOT NULL COMMENT '菜单名称',
  `parent_id` bigint DEFAULT '0' COMMENT '父菜单ID',
  `order_num` int DEFAULT '0' COMMENT '显示顺序',
  `path` varchar(200) DEFAULT '' COMMENT '路由地址',
  `component` varchar(255) DEFAULT NULL COMMENT '组件路径',
  `is_frame` int DEFAULT '1' COMMENT '是否为外链（0是 1否）',
  `menu_type` char(1) DEFAULT '' COMMENT '菜单类型（M目录 C菜单 F按钮）',
  `visible` char(1) DEFAULT '0' COMMENT '菜单状态（0显示 1隐藏）',
  `status` char(1) DEFAULT '0' COMMENT '菜单状态（0正常 1停用）',
  `perms` varchar(100) DEFAULT NULL COMMENT '权限标识',
  `icon` varchar(100) DEFAULT '#' COMMENT '菜单图标',
  `create_by` bigint DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT '' COMMENT '备注',
  `del_flag` char(1) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2035 DEFAULT CHARSET=utf8mb3 COMMENT='菜单权限表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_menu`
--

LOCK TABLES `sys_menu` WRITE;
/*!40000 ALTER TABLE `sys_menu` DISABLE KEYS */;
INSERT INTO `sys_menu` VALUES (1,'系统管理',0,1,'system',NULL,1,'M','0','0','','system',1,'2023-03-01 00:00:00',1,'2023-03-01 00:00:00','系统管理目录','0'),(100,'用户管理',1,1,'user','system/user/index',1,'C','0','0','system:user:list','user',1,'2023-03-01 00:00:00',1,'2023-03-01 00:00:00','用户管理菜单','0'),(101,'角色管理',1,2,'role','system/role/index',1,'C','0','0','system:role:list','peoples',1,'2023-03-01 00:00:00',1,'2023-03-01 00:00:00','角色管理菜单','0'),(102,'菜单管理',1,3,'menu','system/menu/index',1,'C','0','0','system:menu:list','tree-table',1,'2023-03-01 00:00:00',1,'2023-03-01 00:00:00','菜单管理菜单','0'),(1001,'用户查询',100,1,'','',1,'F','0','0','system:user:query','#',1,'2023-03-01 00:00:00',1,'2023-03-01 00:00:00','','0'),(1002,'用户新增',100,2,'','',1,'F','0','0','system:user:add','#',1,'2023-03-01 00:00:00',1,'2023-03-01 00:00:00','','0'),(1003,'用户修改',100,3,'','',1,'F','0','0','system:user:edit','#',1,'2023-03-01 00:00:00',1,'2023-03-01 00:00:00','','0'),(1004,'用户删除',100,4,'','',1,'F','0','0','system:user:remove','#',1,'2023-03-01 00:00:00',1,'2023-03-01 00:00:00','','0'),(1005,'用户导出',100,5,'','',1,'F','0','0','system:user:export','#',1,'2023-03-01 00:00:00',1,'2023-03-01 00:00:00','','0'),(1006,'用户导入',100,6,'','',1,'F','0','0','system:user:import','#',1,'2023-03-01 00:00:00',1,'2023-03-01 00:00:00','','0'),(1007,'重置密码',100,7,'','',1,'F','0','0','system:user:resetPwd','#',1,'2023-03-01 00:00:00',1,'2023-03-01 00:00:00','','0'),(1008,'角色查询',101,1,'','',1,'F','0','0','system:role:query','#',1,'2023-03-01 00:00:00',1,'2023-03-01 00:00:00','','0'),(1009,'角色新增',101,2,'','',1,'F','0','0','system:role:add','#',1,'2023-03-01 00:00:00',1,'2023-03-01 00:00:00','','0'),(1010,'角色修改',101,3,'','',1,'F','0','0','system:role:edit','#',1,'2023-03-01 00:00:00',1,'2023-03-01 00:00:00','','0'),(1011,'角色删除',101,4,'','',1,'F','0','0','system:role:remove','#',1,'2023-03-01 00:00:00',1,'2023-03-01 00:00:00','','0'),(1012,'角色导出',101,5,'','',1,'F','0','0','system:role:export','#',1,'2023-03-01 00:00:00',1,'2023-03-01 00:00:00','','0'),(1013,'菜单查询',102,1,'','',1,'F','0','0','system:menu:query','#',1,'2023-03-01 00:00:00',1,'2023-03-01 00:00:00','','0'),(1014,'菜单新增',102,2,'','',1,'F','0','0','system:menu:add','#',1,'2023-03-01 00:00:00',1,'2023-03-01 00:00:00','','0'),(1015,'菜单修改',102,3,'','',1,'F','0','0','system:menu:edit','#',1,'2023-03-01 00:00:00',1,'2023-03-01 00:00:00','','0'),(1016,'菜单删除',102,4,'','',1,'F','0','0','system:menu:remove','#',1,'2023-03-01 00:00:00',1,'2023-03-01 00:00:00','','0'),(2017,'内容管理',0,4,'content',NULL,1,'M','0','0',NULL,'table',1,'2023-03-01 00:00:00',1,'2023-03-01 00:00:00','','0'),(2018,'分类管理',2017,1,'category','content/category/index',1,'C','0','0','content:category:list','example',1,'2023-03-01 00:00:00',1,'2023-03-01 00:00:00','','0'),(2019,'文章管理',2017,0,'article','content/article/index',1,'C','0','0','content:article:list','build',1,'2023-03-01 00:00:00',1,'2023-03-01 00:00:00','','0'),(2021,'标签管理',2017,6,'tag','content/tag/index',1,'C','0','0','content:tag:index','button',1,'2023-03-01 00:00:00',1,'2023-03-01 00:00:00','','0'),(2022,'友链管理',2017,4,'link','content/link/index',1,'C','0','0','content:link:list','404',1,'2023-03-01 00:00:00',1,'2023-03-01 00:00:00','','0'),(2023,'写博文',0,0,'write','content/article/write/index',1,'C','0','0','content:article:writer','build',1,'2023-03-01 00:00:00',1,'2023-03-01 00:00:00','','0'),(2024,'友链新增',2022,0,'',NULL,1,'F','0','0','content:link:add','#',1,'2023-03-01 00:00:00',1,'2023-03-01 00:00:00','','0'),(2025,'友链修改',2022,1,'',NULL,1,'F','0','0','content:link:edit','#',1,'2023-03-01 00:00:00',1,'2023-03-01 00:00:00','','0'),(2026,'友链删除',2022,1,'',NULL,1,'F','0','0','content:link:remove','#',1,'2023-03-01 00:00:00',1,'2023-03-01 00:00:00','','0'),(2027,'友链查询',2022,2,'',NULL,1,'F','0','0','content:link:query','#',1,'2023-03-01 00:00:00',1,'2023-03-01 00:00:00','','0'),(2028,'导出分类',2018,1,'',NULL,1,'F','0','0','content:category:export','#',1,'2023-03-01 00:00:00',1,'2023-03-01 00:00:00','','0'),(2034,'新的内容管理',2017,10,'/addMore',NULL,1,'M','0','1',NULL,'chart',1,'2023-03-01 00:00:00',1,'2023-03-01 00:00:00','','0');
/*!40000 ALTER TABLE `sys_menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_role`
--

DROP TABLE IF EXISTS `sys_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_role` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `role_name` varchar(30) NOT NULL COMMENT '角色名称',
  `role_key` varchar(100) NOT NULL COMMENT '角色权限字符串',
  `role_sort` int NOT NULL COMMENT '显示顺序',
  `status` char(1) NOT NULL COMMENT '角色状态（0正常 1停用）',
  `del_flag` char(1) DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
  `create_by` bigint DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb3 COMMENT='角色信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_role`
--

LOCK TABLES `sys_role` WRITE;
/*!40000 ALTER TABLE `sys_role` DISABLE KEYS */;
INSERT INTO `sys_role` VALUES (1,'超级管理员','admin',1,'0','0',0,'2023-03-01 00:00:00',0,'2023-03-01 00:00:00','拥有所有权限'),(2,'普通角色','common',2,'0','0',1,'2023-03-01 00:00:00',1,'2023-03-01 00:00:00','普通角色'),(11,'测试角色','test',5,'0','0',1,'2023-03-01 00:00:00',1,'2023-03-01 00:00:00','临时测试用'),(12,'友链审核员','link',1,'0','0',1,'2023-03-01 00:00:00',1,'2023-03-01 00:00:00','只能审核友链');
/*!40000 ALTER TABLE `sys_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_role_menu`
--

DROP TABLE IF EXISTS `sys_role_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_role_menu` (
  `role_id` bigint NOT NULL COMMENT '角色ID',
  `menu_id` bigint NOT NULL COMMENT '菜单ID',
  PRIMARY KEY (`role_id`,`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='角色和菜单关联表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_role_menu`
--

LOCK TABLES `sys_role_menu` WRITE;
/*!40000 ALTER TABLE `sys_role_menu` DISABLE KEYS */;
INSERT INTO `sys_role_menu` VALUES (0,0),(2,1),(2,102),(2,1013),(2,1014),(2,1015),(2,1016),(2,2000),(3,2),(3,3),(3,4),(3,100),(3,101),(3,103),(3,104),(3,105),(3,106),(3,107),(3,108),(3,109),(3,110),(3,111),(3,112),(3,113),(3,114),(3,115),(3,116),(3,500),(3,501),(3,1001),(3,1002),(3,1003),(3,1004),(3,1005),(3,1006),(3,1007),(3,1008),(3,1009),(3,1010),(3,1011),(3,1012),(3,1017),(3,1018),(3,1019),(3,1020),(3,1021),(3,1022),(3,1023),(3,1024),(3,1025),(3,1026),(3,1027),(3,1028),(3,1029),(3,1030),(3,1031),(3,1032),(3,1033),(3,1034),(3,1035),(3,1036),(3,1037),(3,1038),(3,1039),(3,1040),(3,1041),(3,1042),(3,1043),(3,1044),(3,1045),(3,1046),(3,1047),(3,1048),(3,1049),(3,1050),(3,1051),(3,1052),(3,1053),(3,1054),(3,1055),(3,1056),(3,1057),(3,1058),(3,1059),(3,1060),(3,2000),(11,1),(11,100),(11,101),(11,102),(11,1001),(11,1002),(11,1003),(11,1004),(11,1005),(11,1006),(11,1007),(11,1008),(11,1009),(11,1010),(11,1011),(11,1012),(11,1013),(11,1014),(11,1015),(11,1016),(11,2017),(11,2022),(11,2024),(11,2025),(11,2026),(11,2027),(12,2017),(12,2022),(12,2024),(12,2025),(12,2026),(12,2027);
/*!40000 ALTER TABLE `sys_role_menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_user`
--

DROP TABLE IF EXISTS `sys_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_user` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_name` varchar(64) NOT NULL DEFAULT 'NULL' COMMENT '用户名',
  `nick_name` varchar(64) NOT NULL DEFAULT 'NULL' COMMENT '昵称',
  `password` varchar(64) NOT NULL DEFAULT 'NULL' COMMENT '密码',
  `type` char(1) DEFAULT '0' COMMENT '用户类型：0代表普通用户，1代表管理员',
  `status` char(1) DEFAULT '0' COMMENT '账号状态（0正常 1停用）',
  `email` varchar(64) DEFAULT NULL COMMENT '邮箱',
  `phone_number` varchar(32) DEFAULT NULL COMMENT '手机号',
  `sex` char(1) DEFAULT NULL COMMENT '用户性别（0男，1女，2未知）',
  `avatar` varchar(128) DEFAULT NULL COMMENT '头像',
  `create_by` bigint DEFAULT NULL COMMENT '创建人的用户id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint DEFAULT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `del_flag` int DEFAULT '0' COMMENT '删除标志（0代表未删除，1代表已删除）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user`
--

LOCK TABLES `sys_user` WRITE;
/*!40000 ALTER TABLE `sys_user` DISABLE KEYS */;
INSERT INTO `sys_user` VALUES (1,'dyh','超管','$2a$10$/87E3JZhq0vmF9ew9rgL5.NSbFWi/xxOMO8xa5p.XMDTjVoeGSrQO','1','0','610377357@qq.com','保密','0','http://rr1o9xquo.bkt.clouddn.com/71af3768b5c641df8d43277c1f5ea5da.jpg',0,'2023-03-01 00:00:00',0,'2023-03-01 00:00:00',0),(3,'guest','普通用户','$2a$10$/87E3JZhq0vmF9ew9rgL5.NSbFWi/xxOMO8xa5p.XMDTjVoeGSrQO','1','0','guest@gamil.com','13344445555','0','http://rr1o9xquo.bkt.clouddn.com/bae5e80b3a934fdfa98b20eccbc38ed7.jpg',1,'2023-03-01 00:00:00',1,'2023-03-01 00:00:00',0),(4,'link','友链管理员','$2a$10$/87E3JZhq0vmF9ew9rgL5.NSbFWi/xxOMO8xa5p.XMDTjVoeGSrQO','1','0','link@foxmail.com','18877776666','0','http://rr1o9xquo.bkt.clouddn.com/74db997daa8b4b8ea4ac3035400870a5.jpg',1,'2023-03-01 00:00:00',1,'2023-03-01 00:00:00',0),(5,'admin','普通管理员','$2a$10$/87E3JZhq0vmF9ew9rgL5.NSbFWi/xxOMO8xa5p.XMDTjVoeGSrQO','1','0','admin@163.com','13322221111','1','http://rr1o9xquo.bkt.clouddn.com/2c49f58bb2a4437fa60769a4dc06738c.jpg',1,'2023-03-01 00:00:00',1,'2023-03-01 00:00:00',0),(6,'sg','sg','$2a$10$/87E3JZhq0vmF9ew9rgL5.NSbFWi/xxOMO8xa5p.XMDTjVoeGSrQO','1','0','2312321','17777777777','0',NULL,1,'2023-03-01 00:00:00',1,'2023-03-01 00:00:00',0),(7,'dyh2','mydddyh','$2a$10$/87E3JZhq0vmF9ew9rgL5.NSbFWi/xxOMO8xa5p.XMDTjVoeGSrQO','1','0','610377357@qq.com','188888888','0','http://rr1o9xquo.bkt.clouddn.com/8b2dc23586f34efbb30fa7648b44b72e.jpg',1,'2023-03-01 00:00:00',1,'2023-03-01 00:00:00',0),(8,'weixin','weixin','$2a$10$/87E3JZhq0vmF9ew9rgL5.NSbFWi/xxOMO8xa5p.XMDTjVoeGSrQO','0','0','weixin@qq.com',NULL,NULL,NULL,1,'2023-03-01 00:00:00',1,'2023-03-01 00:00:00',0),(9,'zly','yy','$2a$10$/87E3JZhq0vmF9ew9rgL5.NSbFWi/xxOMO8xa5p.XMDTjVoeGSrQO','0','0','zly_4321@qq.com','18999998888','1','http://rr1o9xquo.bkt.clouddn.com/9906607990dc4229aed46e729a6f8ed2.png',1,'2023-03-01 00:00:00',1,'2023-03-01 00:00:00',0),(10,'测试名称','测试 用户','$2a$10$/87E3JZhq0vmF9ew9rgL5.NSbFWi/xxOMO8xa5p.XMDTjVoeGSrQO','0','0','ab@cdef.com','23333','0',NULL,1,'2023-03-01 00:00:00',1,'2023-03-01 00:00:00',0),(12,'hello','hello','$2a$10$/87E3JZhq0vmF9ew9rgL5.NSbFWi/xxOMO8xa5p.XMDTjVoeGSrQO','0','0','ab@cd.com',NULL,NULL,NULL,1,'2023-03-01 00:00:00',1,'2023-03-01 00:00:00',0);
/*!40000 ALTER TABLE `sys_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_user_role`
--

DROP TABLE IF EXISTS `sys_user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_user_role` (
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `role_id` bigint NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`user_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='用户和角色关联表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user_role`
--

LOCK TABLES `sys_user_role` WRITE;
/*!40000 ALTER TABLE `sys_user_role` DISABLE KEYS */;
INSERT INTO `sys_user_role` VALUES (1,1),(2,2),(3,2),(3,11),(4,11),(4,12),(5,2),(5,11),(5,12),(6,12),(7,1),(7,2),(9,2),(10,2),(10,11),(14787164048666,2),(14787164048666,12);
/*!40000 ALTER TABLE `sys_user_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_article`
--

DROP TABLE IF EXISTS `tbl_article`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tbl_article` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `title` varchar(256) DEFAULT NULL COMMENT '标题',
  `content` longtext COMMENT '文章内容',
  `summary` varchar(1024) DEFAULT NULL COMMENT '文章摘要',
  `category_id` bigint DEFAULT NULL COMMENT '所属分类id',
  `thumbnail` varchar(256) DEFAULT NULL COMMENT '缩略图',
  `is_top` char(1) DEFAULT '0' COMMENT '是否置顶（0否，1是）',
  `status` char(1) DEFAULT '1' COMMENT '状态（0已发布，1草稿）',
  `view_count` bigint DEFAULT '0' COMMENT '访问量',
  `is_comment` char(1) DEFAULT '1' COMMENT '是否允许评论 1是，0否',
  `create_by` bigint DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_by` bigint DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `del_flag` int DEFAULT '0' COMMENT '删除标志（0代表未删除，1代表已删除）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='文章表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_article`
--

LOCK TABLES `tbl_article` WRITE;
/*!40000 ALTER TABLE `tbl_article` DISABLE KEYS */;
INSERT INTO `tbl_article` VALUES (9,'博客格式测试','# 字符效果\n\n- ~~删除线~~ <s>删除线（开启识别 HTML 标签时）</s>\n\n- _斜体字_\n- **粗体**\n- **_粗斜体_** **_粗斜体_**\n\n- 上标：X<sub>2</sub>，下标：Y<sup>2</sup>\n\n- ==高亮==\n\n- `Inline Code`\n\n> 引用\n\n# 超链接\n\n- [普通链接](https://duyuheng.cn)\n- [_斜体链接_](https://duyuheng.cn)\n- [**粗体链接**](https://duyuheng.cn)\n\n# 脚注\n\n脚注1 [^1] 脚注2 [^2].\n\n[^1]: 这是第一个脚注.\n[^2]: 这是第二个脚注.\n\n# 图像\n\n![sjtu](https://www.sjtu.edu.cn/resource/upload/202302/20230221_134654_981.jpg)\n\n# 代码\n\n## 行内代码\n\n执行命令：`npm install mysql`\n\n## 代码片\n\n### Python 代码\n\n```python\nclass Animal:\n    \"\"\" 动物类 \"\"\"\n\n    def __init__(self, age: int, name: str):\n        self.age = age\n        self.name = name\n\n    def getInfo(self) -> str:\n        \"\"\" 返回信息 \"\"\"\n        return f\'age: {self.age}; name: {self.name}\'\n\n\nclass Dog(Animal):\n    \"\"\" 狗狗类 \"\"\"\n\n    def __init__(self, age: int, name: str, gender=\'female\', color=\'white\'):\n        super().__init__(age, name)\n        self.gender = gender\n        self.__color = color\n\n    def bark(self):\n        \"\"\" 狗叫 \"\"\"\n        print(\'lololo\')\n\n    @property\n    def color(self):\n        return self.__color\n\n    @color.setter\n    def color(self, color: str):\n        if color not in [\'red\', \'white\', \'black\']:\n            raise ValueError(\'颜色不符合要求\')\n        self.__color = color\n\n\nif __name__ == \'__main__\':\n    dog = Dog(16, \'啸天\', gender=\'male\')\n    # 狗叫\n    dog.bark()\n    # 设置狗狗毛色\n    dog.color = \'blue\'\n```\n\n### HTML 代码\n\n```html\n<!DOCTYPE html>\n<html>\n    <head>\n        <mate charest=\"utf-8\" />\n        <title>Hello world!</title>\n    </head>\n    <body>\n        <h1>Hello world!</h1>\n    </body>\n</html>\n```\n\n# 列表\n\n## 无序列表\n\n- 福建\n  - 厦门\n  - 福州\n- 浙江\n- 江苏\n\n## 有序列表\n\n1. 动物\n   1. 人类\n   2. 犬类\n2. 植物\n3. 微生物\n\n## 任务列表\n\n- [x] 计划1\n- [ ] 计划2\n- [ ] 计划3\n  - [ ] 计划3.1\n  - [ ] 计划3.2\n\n# 表格\n\n| 项目   |  价格 | 数量 |\n| ------ | ----: | :--: |\n| 计算机 | $1600 |  5   |\n| 手机   |   $12 |  12  |\n| 管线   |    $1 | 234  |\n\n---\n\n# 特殊符号\n\n&copy; & &uml; &trade; &iexcl; &pound;\n&amp; &lt; &gt; &yen; &euro; &reg; &plusmn; &para; &sect; &brvbar; &macr; &laquo; &middot;\n\nX&sup2; Y&sup3; &frac34; &frac14; &times; &divide; &raquo;\n\n18&ordm;C &quot; &apos;\n\n# Emoji 表情 ?\n\n- 马：?\n- 星星：✨\n- 笑脸：?\n- 跑步：?‍\n\n# 数学公式\n\n行间公式：\n$\\sin(\\alpha)^{\\theta}=\\sum_{i=0}^{n}(x^i + \\cos(f))$\n\n行内公式 $E=mc^2$\n','对博客支持的文章效果进行测试',15,'http://rr1o9xquo.bkt.clouddn.com/95e035d879ca4bba82efd7e3a6e506c2.jpg','1','0',28,'0',1,'2023-03-01 00:00:00',1,'2023-03-01 00:00:00',0),(12,'项目介绍','# Blog-Project\n基于Springboot + Vue3 开发的前后端分离博客\n\n## 在线地址\n\n**前台链接：** [duyuheng.cn:8080](http://duyuheng.cn:8080)\n\n**后台链接：** [duyuheng.cn:8081](http://duyuheng.cn:8081)\n\n**普通用户 账号：** guest，**密码**：123456\n\n**普通管理员 账号：** admin，**密码**：123456\n\n**友链管理员 账号：** link，**密码**：123456\n\n**超级管理员 账号：** dyh，**密码**：123456\n\n**Github 地址：** [https://github.com/mydddyh/Blog](https://github.com/mydddyh/Blog)\n\n**前台接口文档：** [http://duyuheng.cn:8082/swagger-ui.html](http://duyuheng.cn:8082/swagger-ui.html)\n\n**后台接口文档：** [http://duyuheng.cn:8083/swagger-ui.html](http://duyuheng.cn:8083/swagger-ui.html)\n\n## 本地运行\n\n1. 修改后端yml配置文件中的数据库Mysql、对象存储OSS等连接信息\n2. SQL 文件位于根目录下的`blog.sql`，将其中的数据导入到自己本地数据库中\n3. 前端文件位于根目录下的`vue.zip`\n4. 项目启动后，使用`dyh`超级管理员账号登录后台，密码为`123456`\n\n## 项目特点\n\n- 含有最新评论、文章目录、文章推荐和文章置顶功能\n- 文章编辑使用 Markdown 编辑器\n- 发布评论、回复评论、表情包\n- 采用 RBAC 权限模型，使用 Spring Security 进行权限管理\n- 支持动态权限修改、动态菜单和路由\n- 实现日志管理、定时任务管理\n- 采用 Restful 风格的 API，注释完善\n\n## 文件结构\n\n- blog-framework: 前后台共用,\n  - domain: 实体类和视图封装\n  - mapper: 数据库处理\n  - service: 业务逻辑处理\n    - impl: 主要功能的实现\n  - annotation & aspect: 自定义统计注解\n  - config: 配置类\n  - constants: 常量\n  - exception: 自定义异常\n  - handler: 异常处理和字段填充\n  - utils: 工具类\n- blog-web: 前台工程\n  - controller: 前台控制器\n- blog-admin: 后台工程\n  - controller: 后台控制器\n\n## 技术介绍\n\nSpringBoot + Mysql + MybatisPlus + SpringSecurity + Redis + Swagger2 + Nginx\n\n## 运行环境\n\n**服务器：** 阿里云 2 核 2G Ubuntu 20.04.5\n\n**对象存储：** 七牛云 OSS\n\n## 开发环境\n\n| 开发工具              | 说明               |\n| --------------------- | ------------------ |\n| IDEA                  | Java 开发工具 IDE  |\n| VS Code               | Vue 开发工具 IDE   |\n| MySQL Workbench       | MySQL 远程连接工具 |\n| FinalShell            | Linux 远程连接工具 |\n\n| 开发环境      | 版本   |\n| ------------- | ------ |\n| OpenJDK       | 11     |\n| MySQL         | 8.0.32 |\n| Redis         | 5.0.7  |\n\n## 项目截图\n\n![](http://rr1o9xquo.bkt.clouddn.com/%E5%8D%9A%E5%AE%A2%E9%A1%B5%E9%9D%A21.jpg)\n![](http://rr1o9xquo.bkt.clouddn.com/%E5%8D%9A%E5%AE%A2%E9%A1%B5%E9%9D%A22.jpg)\n\n特别鸣谢：\n\n- [ 三更草堂 ](https://www.bilibili.com/video/BV1hq4y1F7zk)','项目的README文件',15,'http://rr1o9xquo.bkt.clouddn.com/de35a7c7a59245aea5ba1d0d4fb4d909.jpg','1','0',5,'0',1,'2023-03-01 00:00:00',1,'2023-03-01 00:00:00',0),(16,'项目上线部署过程','服务器之前已安装、配置好Java和Mysql环境\n\n# 路径\n前端文件位置：/var/www/duyuheng/html\n后端文件位置：/usr/local/blog\n\n# Nginx安装\n先更新下包\napt update # 检查哪些包需要更新\napt upgrade # 执行更新\napt install nginx # 安装\nsystemctl status nginx # 查看状态\n发现启动失败，按q退出。先杀掉之前图书系统，空出80端口再启动\nps -aux | grep java\nkill -9 进程PID\nsystemctl start nginx\nsystemctl status nginx\ncurl -4 icanhazip.com # 借助第三方工具查看本机的ip地址\ncurl IP地址 # 用命令行或者浏览器访问地址\n启动后访问 http://duyuheng.cn/ 就可以看到Welcome to nginx!的欢迎页面了\nUbuntu下Nginx存储在/usr/sbin/nginx目录下\n调整防火墙规则\nufw app list # 列出ufw已知的应用程序配置\n其中Nginx有三个配置文件，表示需要打开的端口：Nginx Full (80+443)、Nginx HTTP (80)、Nginx HTTPS （443），我们可以选第二个\nufw allow \'Nginx HTTP\' # 防火墙允许Nginx HTTP对应的80端口\nufw status # 查看状态\n如果防火墙没启动，可以用ufw enable启动。后续调试时也可以暂时先用ufw disable关掉自带的防火墙\n\n## Nginx基本设置\nsystemctl start启动/stop停止/restart重启/status查看状态/reload重新加载配置/enable开启自启动(默认)/disable关闭自启动 nginx\n默认的欢迎页面放在/var/www/html/index.nginx-debian.html路径下\n创建自定义的虚拟主机Server Block\nmkdir -p /var/www/duyuheng/html # 创建目录\nchown -R $USER:$USER /var/www/duyuheng/html # 分配目录所有权\nchmod -R 755 /var/www/duyuheng # 权限\nnano /var/www/duyuheng/html/index.html # 写一个简单的HTML静态页面\nnano /etc/nginx/sites-available/duyuheng.conf # 参考default，创建配置文件\nln -s /etc/nginx/sites-available/duyuheng.conf /etc/nginx/sites-enabled/ # 创建符号链接\n来启用配置文件\nnano /etc/nginx/nginx.conf # 设置server_names_hash_bucket_size参数\nnginx -t # 检查配置文件的语法\nsystemctl restart nginx # 重启nginx\n此后，就可以在浏览器访问域名，得到/var/www/duyuheng/html/index.html路径的页面\n如果想禁用虚拟主机，只需要删除/sites-enabled下的符号链接\n为什么用虚拟主机，是为了在单个服务器上托管多个域名： https://cloud.tencent.com/developer/article/2015277\n\n## Nginx的文件和目录\n内容：\n/var/www/html：默认的实际Web内容\n服务器配置：\n/etc/nginx：nginx配置目录\n/etc/nginx/nginx.conf：nginx的主要配置文件\n/etc/nginx/sites-babailable/：存储每个站点的虚拟主机配置，只有链接到sites-enabled才能启用\n/etc/nginx/sites-enabled/：存储启用的虚拟主机\n/etc/nginx/appets：在Nginx配置中其他地方可以包含的配置片段\n日志：\n/var/log/nginx/access.log：记录对Web服务器的每个请求\n/var/log/nginx/error.log：记录任何nginx的错误\n\n参考： https://www.digitalocean.com/community/tutorials/how-to-install-nginx-on-ubuntu-20-04\n\n# Redis安装\napt install redis # 安装\nredis-server --version\nredis-cli --version\nsystemctl status redis\n\n# 项目打包\n## 前端前台打包\n将全文替换locahost:XX替换为duyuheng.cn:8082，注意host: \'localhost\',  port: 8080这个地方不改\n在\\Vue-web路径下执行npm run build命令打包，将本机\\Vue-web\\dist目录复制到服务器的/var/www/duyuheng/html/web-dist路径下\n\n## 前端后台打包\n在前端的vue.config.js中const port=process.env.port设置为前端端口，[process.env.VUE_APP_BASE_API]: { target:设置为后端路径，在.env.production中VUE_APP_BASE_API设置为后端路径\n在src\\router\\index.js中确实大量路由，可以由 https://gitee.com/xu-huaiang/sg-vue-admin/blob/master/src/router/index.js 补缺，还需要加上/logout的Route\n\n## 后端打包\n将yml中的端口分别配置为8082和8083\n更改pom文件，利用maven-compiler-plugin和maven-assembly-plugin插件，需要修改![image.png](http://rr1o9xquo.bkt.clouddn.com/51862e1c770249d08237c9ec5b75bcd1.png)夫项目、web和admin项目的pom\nclean+package后，把两个jar包放到服务器上/usr/local/blog路径\n在打包过程中，spring-boot-maven-plugin:3.0.0:repackage报错，发现是由于spring-boot-maven-plugin版本超过了3.0.0，因此在 https://developer.aliyun.com/mvn/search 查询后web和admin中手动指定版本为2.7.3即可\n\n参考： https://blog.csdn.net/qq_52030824/article/details/127982206\n\n# 网站首页设计\n主要参考了 http://www.hostbuf.com/ 的html设计\n图标在 https://icons8.com/icons 获取。先考虑颜色全为#EE6000橙色的iOS Glyph 16，后换为Cute Color风格\n\n# Swap设置\n先检查当前内存空间\nfree -h\ndf -h\nfallocate -l 2G /swapfile # 创建2G的swap分区文件/swapfile\nchmod 600 /swapfile # 锁定权限，只允许root读写\nls -lh /swapfile # 验证\nmkswap /swapfile # 标记文件为swap\nswapon /swapfile # 启用swap\n可以验证是否成功\nswapon --show\nfree -h\n最后添加/swapfile none swap sw 0 0到/etc/fstab的末尾，永久化设置\n此外还可以设置swappiness参数，代表RAM与swap的交换频率，[0-100]表示百分比。桌面系统可以设为60，在服务器上最好设为0或很小的值\ncat /proc/sys/vm/swappiness # 检查当前值\nsysctl vm.swappiness=10 # 设置值\n在/etc/sysctl.conf文件末尾添加vm.swappiness=10以永久化设置\n\n参考 https://www.digitalocean.com/community/tutorials/how-to-add-swap-space-on-ubuntu-20-04\n','记录项目部署到云服务器的过程（流水账）',15,'','1','0',1,'0',1,'2023-03-01 00:00:00',1,'2023-03-01 00:00:00',0),(17,'“其他”分类的文章','“其他”分类的文章','“其他”分类的文章',16,'','1','0',0,'0',1,'2023-03-01 00:00:00',1,'2023-03-01 00:00:00',0),(18,'此文章不许评论','此文章不许评论','此文章不许评论',15,'','1','0',0,'0',1,'2023-03-01 00:00:00',1,'2023-03-01 00:00:00',0);
/*!40000 ALTER TABLE `tbl_article` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_article_tag`
--

DROP TABLE IF EXISTS `tbl_article_tag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tbl_article_tag` (
  `article_id` bigint NOT NULL DEFAULT '0' COMMENT '文章id',
  `tag_id` bigint NOT NULL DEFAULT '0' COMMENT '标签id',
  PRIMARY KEY (`article_id`,`tag_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='文章标签关联表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_article_tag`
--

LOCK TABLES `tbl_article_tag` WRITE;
/*!40000 ALTER TABLE `tbl_article_tag` DISABLE KEYS */;
INSERT INTO `tbl_article_tag` VALUES (1,4),(2,1),(2,4),(3,4),(3,5),(4,4),(5,1),(8,1),(8,4),(9,4),(11,1),(11,4),(16,9),(16,10),(17,8),(18,1);
/*!40000 ALTER TABLE `tbl_article_tag` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_category`
--

DROP TABLE IF EXISTS `tbl_category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tbl_category` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(128) DEFAULT NULL COMMENT '分类名',
  `pid` bigint DEFAULT '-1' COMMENT '父分类id，如果没有父分类为-1',
  `description` varchar(512) DEFAULT NULL COMMENT '描述',
  `status` char(1) DEFAULT '0' COMMENT '状态0:正常,1禁用',
  `create_by` bigint DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_by` bigint DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `del_flag` int DEFAULT '0' COMMENT '删除标志（0代表未删除，1代表已删除）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='分类表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_category`
--

LOCK TABLES `tbl_category` WRITE;
/*!40000 ALTER TABLE `tbl_category` DISABLE KEYS */;
INSERT INTO `tbl_category` VALUES (1,'Java相关',-1,'Java分类','0',1,'2023-03-01 00:00:00',1,'2023-03-01 00:00:00',0),(15,'博客项目相关',-1,'记录博客项目搭建中的一些内容','0',1,'2023-03-01 00:00:00',1,'2023-03-01 00:00:00',0),(16,'其他',-1,'其他','0',1,'2023-03-01 00:00:00',1,'2023-03-01 00:00:00',0);
/*!40000 ALTER TABLE `tbl_category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_comment`
--

DROP TABLE IF EXISTS `tbl_comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tbl_comment` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `type` char(1) DEFAULT '0' COMMENT '评论类型（0代表文章评论，1代表友链评论）',
  `article_id` bigint DEFAULT NULL COMMENT '文章id',
  `root_id` bigint DEFAULT '-1' COMMENT '根评论id',
  `content` varchar(512) DEFAULT NULL COMMENT '评论内容',
  `to_comment_user_id` bigint DEFAULT '-1' COMMENT '所回复的目标评论的userid',
  `to_comment_id` bigint DEFAULT '-1' COMMENT '回复目标评论id',
  `create_by` bigint DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_by` bigint DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `del_flag` int DEFAULT '0' COMMENT '删除标志（0代表未删除，1代表已删除）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='评论表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_comment`
--

LOCK TABLES `tbl_comment` WRITE;
/*!40000 ALTER TABLE `tbl_comment` DISABLE KEYS */;
INSERT INTO `tbl_comment` VALUES (1,'0',1,-1,'asS',-1,-1,1,'2023-03-01 00:00:00',1,'2023-03-01 00:00:00',0),(2,'0',1,-1,'[哈哈]SDAS',-1,-1,1,'2023-03-01 00:00:00',1,'2023-03-01 00:00:00',0),(3,'0',1,-1,'是大多数',-1,-1,1,'2023-03-01 00:00:00',1,'2023-03-01 00:00:00',0),(4,'0',1,-1,'撒大声地',-1,-1,1,'2023-03-01 00:00:00',1,'2023-03-01 00:00:00',0),(5,'0',1,-1,'你再说什么',-1,-1,1,'2023-03-01 00:00:00',1,'2023-03-01 00:00:00',0),(6,'0',1,-1,'hffd',-1,-1,1,'2023-03-01 00:00:00',1,'2023-03-01 00:00:00',0),(9,'0',1,2,'你说什么',1,2,1,'2023-03-01 00:00:00',1,'2023-03-01 00:00:00',0),(10,'0',1,2,'哈哈哈哈[哈哈]',1,9,1,'2023-03-01 00:00:00',1,'2023-03-01 00:00:00',0),(11,'0',1,2,'we全文',1,10,1,'2023-03-01 00:00:00',1,'2023-03-01 00:00:00',0),(12,'0',1,-1,'王企鹅',-1,-1,1,'2023-03-01 00:00:00',1,'2023-03-01 00:00:00',0),(13,'0',1,-1,'什么阿是',-1,-1,1,'2023-03-01 00:00:00',1,'2023-03-01 00:00:00',0),(14,'0',1,-1,'新平顶山',-1,-1,1,'2023-03-01 00:00:00',1,'2023-03-01 00:00:00',0),(15,'0',1,-1,'2222',-1,-1,1,'2023-03-01 00:00:00',1,'2023-03-01 00:00:00',0),(16,'0',1,2,'3333',1,11,1,'2023-03-01 00:00:00',1,'2023-03-01 00:00:00',0),(17,'0',1,2,'回复weqedadsd',3,11,1,'2023-03-01 00:00:00',1,'2023-03-01 00:00:00',0),(18,'0',1,-1,'sdasd',-1,-1,1,'2023-03-01 00:00:00',1,'2023-03-01 00:00:00',0),(19,'0',1,-1,'111',-1,-1,1,'2023-03-01 00:00:00',1,'2023-03-01 00:00:00',0),(20,'0',1,1,'你说啥？',1,1,1,'2023-03-01 00:00:00',1,'2023-03-01 00:00:00',0),(21,'0',1,-1,'友链添加个呗',-1,-1,1,'2023-03-01 00:00:00',1,'2023-03-01 00:00:00',0),(22,'1',1,-1,'友链评论2',-1,-1,1,'2023-03-01 00:00:00',1,'2023-03-01 00:00:00',0),(23,'1',1,22,'回复友链评论3',1,22,1,'2023-03-01 00:00:00',1,'2023-03-01 00:00:00',0),(24,'1',1,-1,'友链评论4444',-1,-1,1,'2023-03-01 00:00:00',1,'2023-03-01 00:00:00',0),(25,'1',1,22,'收到的',1,22,1,'2023-03-01 00:00:00',1,'2023-03-01 00:00:00',0),(26,'0',1,-1,'sda',-1,-1,1,'2023-03-01 00:00:00',1,'2023-03-01 00:00:00',0),(27,'0',1,1,'说你咋地',1,20,1,'2023-03-01 00:00:00',1,'2023-03-01 00:00:00',0),(28,'0',1,1,'sdad',1,1,1,'2023-03-01 00:00:00',1,'2023-03-01 00:00:00',0),(29,'0',1,-1,'你说是的ad',-1,-1,1,'2023-03-01 00:00:00',1,'2023-03-01 00:00:00',0),(30,'0',1,1,'撒大声地',1,1,1,'2023-03-01 00:00:00',1,'2023-03-01 00:00:00',0),(34,'0',1,-1,'评论了文章',-1,-1,1,'2023-03-01 00:00:00',1,'2023-03-01 00:00:00',0),(35,'0',1,34,'回复了评论',7,34,1,'2023-03-01 00:00:00',1,'2023-03-01 00:00:00',0),(36,'0',1,34,'大师兄所说的对呀！[棒棒][棒棒]',7,34,1,'2023-03-01 00:00:00',1,'2023-03-01 00:00:00',0),(37,'1',1,24,'这是一条友链评论回复',1,24,1,'2023-03-01 00:00:00',1,'2023-03-01 00:00:00',0),(38,'1',1,-1,'zly的友链评论',-1,-1,1,'2023-03-01 00:00:00',1,'2023-03-01 00:00:00',0),(39,'1',1,38,'yy回复link comment',9,38,1,'2023-03-01 00:00:00',1,'2023-03-01 00:00:00',0),(40,'0',5,-1,'评论测试',-1,-1,1,'2023-03-01 00:00:00',1,'2023-03-01 00:00:00',0),(41,'1',1,-1,'最好加上天猫',-1,-1,1,'2023-03-01 00:00:00',1,'2023-03-01 00:00:00',0),(42,'1',1,-1,'你是个小笨蛋吗',-1,-1,1,'2023-03-01 00:00:00',1,'2023-03-01 00:00:00',0),(43,'1',1,-1,'[悲伤][悲伤]想要玩饥荒',-1,-1,1,'2023-03-01 00:00:00',1,'2023-03-01 00:00:00',0),(44,'0',9,-1,'评论1[微笑][微笑]',-1,-1,1,'2023-03-01 00:00:00',1,'2023-03-01 00:00:00',0),(45,'0',9,-1,'评论2[嘻嘻][嘻嘻]',-1,-1,1,'2023-03-01 00:00:00',1,'2023-03-01 00:00:00',0),(46,'0',9,44,'回复1.1[哈哈][哈哈]',1,44,1,'2023-03-01 00:00:00',1,'2023-03-01 00:00:00',0),(47,'0',9,44,'回复1.2[可爱][可爱]',1,44,1,'2023-03-01 00:00:00',1,'2023-03-01 00:00:00',0),(48,'0',9,44,'回复1.1.1[可怜][可怜]',3,46,1,'2023-03-01 00:00:00',1,'2023-03-01 00:00:00',0),(49,'0',9,44,'回复1.3[挖鼻][挖鼻]',1,44,1,'2023-03-01 00:00:00',1,'2023-03-01 00:00:00',0),(50,'0',9,44,'回复1.1.1.1',4,48,1,'2023-03-01 00:00:00',1,'2023-03-01 00:00:00',0);
/*!40000 ALTER TABLE `tbl_comment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_link`
--

DROP TABLE IF EXISTS `tbl_link`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tbl_link` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(256) DEFAULT NULL,
  `logo` varchar(256) DEFAULT NULL,
  `description` varchar(512) DEFAULT NULL,
  `address` varchar(128) DEFAULT NULL COMMENT '网站地址',
  `status` char(1) DEFAULT '2' COMMENT '审核状态 (0代表审核通过，1代表审核未通过，2代表未审核)',
  `create_by` bigint DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_by` bigint DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `del_flag` int DEFAULT '0' COMMENT '删除标志（0代表未删除，1代表已删除）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='友链';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_link`
--

LOCK TABLES `tbl_link` WRITE;
/*!40000 ALTER TABLE `tbl_link` DISABLE KEYS */;
INSERT INTO `tbl_link` VALUES (1,'百度','https://www.baidu.com/img/flexible/logo/pc/result.png','百度搜索网站','https://www.baidu.com','0',1,'2023-03-01 00:00:00',1,'2023-03-01 00:00:00',0),(2,'QQ','https://img1.baidu.com/it/u=3023691207,1208231529&fm=253&fmt=auto&app=120&f=JPEG?w=800&h=800','腾讯QQ','https://www.qq.com','0',1,'2023-03-01 00:00:00',1,'2023-03-01 00:00:00',0),(3,'淘宝','https://img0.baidu.com/it/u=2607889200,3343962549&fm=253&fmt=auto&app=138&f=PNG?w=500&h=500','淘宝网','https://www.taobao.com','0',1,'2023-03-01 00:00:00',1,'2023-03-01 00:00:00',0),(4,'京东','https://img10.360buyimg.com/img/jfs/t1/192028/25/33459/5661/63fc2af2F1f6ae1b6/d0e4fdc2f126cbf5.png','京东商城','https://www.jd.com/','0',1,'2023-03-01 00:00:00',1,'2023-03-01 00:00:00',0);
/*!40000 ALTER TABLE `tbl_link` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_tag`
--

DROP TABLE IF EXISTS `tbl_tag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tbl_tag` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(128) DEFAULT NULL COMMENT '标签名',
  `create_by` bigint DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_by` bigint DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `del_flag` int DEFAULT '0' COMMENT '删除标志（0代表未删除，1代表已删除）',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='标签';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_tag`
--

LOCK TABLES `tbl_tag` WRITE;
/*!40000 ALTER TABLE `tbl_tag` DISABLE KEYS */;
INSERT INTO `tbl_tag` VALUES (1,'Mybatis',1,'2023-03-01 00:00:00',1,'2023-03-01 00:00:00',0,'一种数据库中间件'),(4,'Java',1,'2023-03-01 00:00:00',1,'2023-03-01 00:00:00',0,'一种主流语言'),(6,'Python',1,'2023-03-01 00:00:00',1,'2023-03-01 00:00:00',0,'一种弱类型语言'),(8,'C++',1,'2023-03-01 00:00:00',1,'2023-03-01 00:00:00',0,'备用'),(9,'Linux',1,'2023-03-01 00:00:00',1,'2023-03-01 00:00:00',0,'一种操作系统'),(10,'Ubuntu',1,'2023-03-01 00:00:00',1,'2023-03-01 00:00:00',0,'基于Debian的Linxu系统');
/*!40000 ALTER TABLE `tbl_tag` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-03-19 19:04:24
