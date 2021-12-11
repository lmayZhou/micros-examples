/*
 Navicat Premium Data Transfer

 Source Server         : 127.0.0.1
 Source Server Type    : MySQL
 Source Server Version : 80016
 Source Host           : 127.0.0.1:3306
 Source Schema         : nacos_db

 Target Server Type    : MySQL
 Target Server Version : 80016
 File Encoding         : 65001

 Date: 01/11/2021 17:33:44
*/
DROP DATABASE IF EXISTS `nacos_db`;

CREATE DATABASE  `nacos_db` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

USE `nacos_db`;

-- ----------------------------
-- Table structure for config_info
-- ----------------------------
DROP TABLE IF EXISTS `config_info`;
CREATE TABLE `config_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `data_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'data_id',
  `group_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `content` longtext CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'content',
  `md5` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT 'md5',
  `gmt_create` datetime NOT NULL DEFAULT '2010-05-05 00:00:00' COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT '2010-05-05 00:00:00' COMMENT '修改时间',
  `src_user` text CHARACTER SET utf8 COLLATE utf8_bin COMMENT 'source user',
  `src_ip` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT 'source ip',
  `app_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT '' COMMENT '租户字段',
  `c_desc` varchar(256) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `c_use` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `effect` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `type` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `c_schema` text CHARACTER SET utf8 COLLATE utf8_bin,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_configinfo_datagrouptenant` (`data_id`,`group_id`,`tenant_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='config_info';

-- ----------------------------
-- Records of config_info
-- ----------------------------
BEGIN;
INSERT INTO `config_info` VALUES (8, 'ms-gateway-web-dev.yml', 'DEFAULT_GROUP', '# 服务端口\nserver:\n  port: 80\n\n# Spring 配置\nspring:\n  # Redis\n  redis:\n    host: ${REDIS_HOST:lms-redis}\n    port: 6379\n    password: 123456\n    timeout: 100ms\n    jedis:\n      pool:\n        max-active: 8\n        max-idle: 8\n        max-wait: -1ms\n        min-idle: 0\n  cloud:\n    # 网关\n    gateway:\n      globalcors:\n        cors-configurations:\n          # 匹配所有请求\n          \'[/**]\':\n            # 跨域处理 允许所有的域\n            allowedOrigins: \"*\"\n            # 支持的方法\n            allowedMethods:\n              - GET\n              - POST\n              - PUT\n              - DELETE\n      # 路由\n      routes:\n        - id: user-service-route\n#          uri: http://127.0.0.1:8003\n          uri: lb://ms-service-user\n          predicates:\n#            - Host = test.lmaye.com**\n            - Path=/api/user/**\n          filters:\n#            - PrefixPath=/user\n            - StripPrefix=2\n\n# 网关认证配置\ngateway-auth:\n  # Token 标识\n  authorizeToken: Authorization\n  # 放行的请求路径(eg: /api/shopify-plugin/category/list)\n  excludeUrls:\n    - /**/v3/api-docs\n    - /**/swagger-resources\n    - /**/swagger-resources/configuration/**\n    - /**/swagger-ui/**\n    - /**/actuator/**\n    - /**/instances/**\n    - /**/*.js\n    - /**/*.css\n    - /**/*.png\n    - /**/*.ico\n  # 认证登录地址\n  oauthLogin: http://localhost:9088/login', '9e0bf63baeb7fd5f4ac28c888f6fa28c', '2021-10-09 04:56:30', '2021-11-01 04:20:22', 'nacos', '172.18.0.1', '', '', '网关配置', '', '', 'yaml', '');
COMMIT;

-- ----------------------------
-- Table structure for config_info_aggr
-- ----------------------------
DROP TABLE IF EXISTS `config_info_aggr`;
CREATE TABLE `config_info_aggr` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `data_id` varchar(255) COLLATE utf8_bin NOT NULL COMMENT 'data_id',
  `group_id` varchar(255) COLLATE utf8_bin NOT NULL COMMENT 'group_id',
  `datum_id` varchar(255) COLLATE utf8_bin NOT NULL COMMENT 'datum_id',
  `content` longtext COLLATE utf8_bin NOT NULL COMMENT '内容',
  `gmt_modified` datetime NOT NULL COMMENT '修改时间',
  `app_name` varchar(128) COLLATE utf8_bin DEFAULT NULL,
  `tenant_id` varchar(128) COLLATE utf8_bin DEFAULT '' COMMENT '租户字段',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_configinfoaggr_datagrouptenantdatum` (`data_id`,`group_id`,`tenant_id`,`datum_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='增加租户字段';

-- ----------------------------
-- Records of config_info_aggr
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for config_info_beta
-- ----------------------------
DROP TABLE IF EXISTS `config_info_beta`;
CREATE TABLE `config_info_beta` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `data_id` varchar(255) COLLATE utf8_bin NOT NULL COMMENT 'data_id',
  `group_id` varchar(128) COLLATE utf8_bin NOT NULL COMMENT 'group_id',
  `app_name` varchar(128) COLLATE utf8_bin DEFAULT NULL COMMENT 'app_name',
  `content` longtext COLLATE utf8_bin NOT NULL COMMENT 'content',
  `beta_ips` varchar(1024) COLLATE utf8_bin DEFAULT NULL COMMENT 'betaIps',
  `md5` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT 'md5',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `src_user` text COLLATE utf8_bin COMMENT 'source user',
  `src_ip` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT 'source ip',
  `tenant_id` varchar(128) COLLATE utf8_bin DEFAULT '' COMMENT '租户字段',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_configinfobeta_datagrouptenant` (`data_id`,`group_id`,`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='config_info_beta';

-- ----------------------------
-- Records of config_info_beta
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for config_info_tag
-- ----------------------------
DROP TABLE IF EXISTS `config_info_tag`;
CREATE TABLE `config_info_tag` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `data_id` varchar(255) COLLATE utf8_bin NOT NULL COMMENT 'data_id',
  `group_id` varchar(128) COLLATE utf8_bin NOT NULL COMMENT 'group_id',
  `tenant_id` varchar(128) COLLATE utf8_bin DEFAULT '' COMMENT 'tenant_id',
  `tag_id` varchar(128) COLLATE utf8_bin NOT NULL COMMENT 'tag_id',
  `app_name` varchar(128) COLLATE utf8_bin DEFAULT NULL COMMENT 'app_name',
  `content` longtext COLLATE utf8_bin NOT NULL COMMENT 'content',
  `md5` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT 'md5',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `src_user` text COLLATE utf8_bin COMMENT 'source user',
  `src_ip` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT 'source ip',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_configinfotag_datagrouptenanttag` (`data_id`,`group_id`,`tenant_id`,`tag_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='config_info_tag';

-- ----------------------------
-- Records of config_info_tag
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for config_tags_relation
-- ----------------------------
DROP TABLE IF EXISTS `config_tags_relation`;
CREATE TABLE `config_tags_relation` (
  `id` bigint(20) NOT NULL COMMENT 'id',
  `tag_name` varchar(128) COLLATE utf8_bin NOT NULL COMMENT 'tag_name',
  `tag_type` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT 'tag_type',
  `data_id` varchar(255) COLLATE utf8_bin NOT NULL COMMENT 'data_id',
  `group_id` varchar(128) COLLATE utf8_bin NOT NULL COMMENT 'group_id',
  `tenant_id` varchar(128) COLLATE utf8_bin DEFAULT '' COMMENT 'tenant_id',
  `nid` bigint(20) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`nid`),
  UNIQUE KEY `uk_configtagrelation_configidtag` (`id`,`tag_name`,`tag_type`),
  KEY `idx_tenant_id` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='config_tag_relation';

-- ----------------------------
-- Records of config_tags_relation
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for group_capacity
-- ----------------------------
DROP TABLE IF EXISTS `group_capacity`;
CREATE TABLE `group_capacity` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `group_id` varchar(128) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT 'Group ID，空字符表示整个集群',
  `quota` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '配额，0表示使用默认值',
  `usage` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '使用量',
  `max_size` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '单个配置大小上限，单位为字节，0表示使用默认值',
  `max_aggr_count` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '聚合子配置最大个数，，0表示使用默认值',
  `max_aggr_size` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '单个聚合数据的子配置大小上限，单位为字节，0表示使用默认值',
  `max_history_count` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '最大变更历史数量',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_group_id` (`group_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='集群、各Group容量信息表';

-- ----------------------------
-- Records of group_capacity
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for his_config_info
-- ----------------------------
DROP TABLE IF EXISTS `his_config_info`;
CREATE TABLE `his_config_info` (
  `id` bigint(64) unsigned NOT NULL,
  `nid` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `data_id` varchar(255) COLLATE utf8_bin NOT NULL,
  `group_id` varchar(128) COLLATE utf8_bin NOT NULL,
  `app_name` varchar(128) COLLATE utf8_bin DEFAULT NULL COMMENT 'app_name',
  `content` longtext COLLATE utf8_bin NOT NULL,
  `md5` varchar(32) COLLATE utf8_bin DEFAULT NULL,
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `src_user` text COLLATE utf8_bin,
  `src_ip` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `op_type` char(10) COLLATE utf8_bin DEFAULT NULL,
  `tenant_id` varchar(128) COLLATE utf8_bin DEFAULT '' COMMENT '租户字段',
  PRIMARY KEY (`nid`),
  KEY `idx_gmt_create` (`gmt_create`),
  KEY `idx_gmt_modified` (`gmt_modified`),
  KEY `idx_did` (`data_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='多租户改造';

-- ----------------------------
-- Records of his_config_info
-- ----------------------------
BEGIN;
INSERT INTO `his_config_info` VALUES (0, 1, 'ms-gateway-web-dev.yml', 'DEFAULT_GROUP', '', '# 服务端口\nserver:\n  port: 90\n\n# Spring 配置\nspring:\n  # Redis\n  redis:\n    host: ${REDIS_HOST:lms-redis}\n    port: 6379\n    password: 123456\n    timeout: 100ms\n    jedis:\n      pool:\n        max-active: 8\n        max-idle: 8\n        max-wait: -1ms\n        min-idle: 0\n    # 网关\n    gateway:\n      globalcors:\n        cors-configurations:\n          # 匹配所有请求\n          \'[/**]\':\n            # 跨域处理 允许所有的域\n            allowedOrigins: \"*\"\n            # 支持的方法\n            allowedMethods:\n              - GET\n              - POST\n              - PUT\n              - DELETE\n      # 路由\n      routes:\n        - id: user-service-route\n#          uri: http://localhost:8003\n          uri: lb://user-service\n          predicates:\n#            - Host = test.lmaye.com**\n            - Path=/**\n          filters:\n#            - PrefixPath=/user\n            - StripPrefix=1\n\n# 网关认证配置\ngateway-auth:\n  # Token 标识\n  authorizeToken: Authorization\n  # 放行的请求路径(eg: /api/shopify-plugin/category/list)\n  excludeUrls:\n    - /*/doc.html\n    - /*/v3/api-docs\n    - /**/doc.html\n    - /**/swagger-resources\n    - /**/swagger/**\n    - /**/v2/api-docs\n    - /**/*.js\n    - /**/*.css\n    - /**/*.png\n    - /**/*.ico\n    - /**/webjars/springfox-swagger-ui/**\n  # 认证登录地址\n  oauthLogin: http://localhost:9088/login', '31273428941e2afe4d266277ac194236', '2021-10-09 17:56:30', '2021-10-09 04:56:30', NULL, '172.18.0.1', 'I', '');
INSERT INTO `his_config_info` VALUES (8, 2, 'ms-gateway-web-dev.yml', 'DEFAULT_GROUP', '', '# 服务端口\nserver:\n  port: 90\n\n# Spring 配置\nspring:\n  # Redis\n  redis:\n    host: ${REDIS_HOST:lms-redis}\n    port: 6379\n    password: 123456\n    timeout: 100ms\n    jedis:\n      pool:\n        max-active: 8\n        max-idle: 8\n        max-wait: -1ms\n        min-idle: 0\n    # 网关\n    gateway:\n      globalcors:\n        cors-configurations:\n          # 匹配所有请求\n          \'[/**]\':\n            # 跨域处理 允许所有的域\n            allowedOrigins: \"*\"\n            # 支持的方法\n            allowedMethods:\n              - GET\n              - POST\n              - PUT\n              - DELETE\n      # 路由\n      routes:\n        - id: user-service-route\n#          uri: http://localhost:8003\n          uri: lb://user-service\n          predicates:\n#            - Host = test.lmaye.com**\n            - Path=/**\n          filters:\n#            - PrefixPath=/user\n            - StripPrefix=1\n\n# 网关认证配置\ngateway-auth:\n  # Token 标识\n  authorizeToken: Authorization\n  # 放行的请求路径(eg: /api/shopify-plugin/category/list)\n  excludeUrls:\n    - /*/doc.html\n    - /*/v3/api-docs\n    - /**/doc.html\n    - /**/swagger-resources\n    - /**/swagger/**\n    - /**/v2/api-docs\n    - /**/*.js\n    - /**/*.css\n    - /**/*.png\n    - /**/*.ico\n    - /**/webjars/springfox-swagger-ui/**\n  # 认证登录地址\n  oauthLogin: http://localhost:9088/login', '31273428941e2afe4d266277ac194236', '2021-10-09 17:58:25', '2021-10-09 04:58:24', 'nacos', '172.18.0.1', 'U', '');
INSERT INTO `his_config_info` VALUES (8, 3, 'ms-gateway-web-dev.yml', 'DEFAULT_GROUP', '', '# 服务端口\nserver:\n  port: 80\n\n# Spring 配置\nspring:\n  # Redis\n  redis:\n    host: ${REDIS_HOST:lms-redis}\n    port: 6379\n    password: 123456\n    timeout: 100ms\n    jedis:\n      pool:\n        max-active: 8\n        max-idle: 8\n        max-wait: -1ms\n        min-idle: 0\n    # 网关\n    gateway:\n      globalcors:\n        cors-configurations:\n          # 匹配所有请求\n          \'[/**]\':\n            # 跨域处理 允许所有的域\n            allowedOrigins: \"*\"\n            # 支持的方法\n            allowedMethods:\n              - GET\n              - POST\n              - PUT\n              - DELETE\n      # 路由\n      routes:\n        - id: user-service-route\n#          uri: http://localhost:8003\n          uri: lb://user-service\n          predicates:\n#            - Host = test.lmaye.com**\n            - Path=/**\n          filters:\n#            - PrefixPath=/user\n            - StripPrefix=1\n\n# 网关认证配置\ngateway-auth:\n  # Token 标识\n  authorizeToken: Authorization\n  # 放行的请求路径(eg: /api/shopify-plugin/category/list)\n  excludeUrls:\n    - /*/doc.html\n    - /*/v3/api-docs\n    - /**/doc.html\n    - /**/swagger-resources\n    - /**/swagger/**\n    - /**/v2/api-docs\n    - /**/*.js\n    - /**/*.css\n    - /**/*.png\n    - /**/*.ico\n    - /**/webjars/springfox-swagger-ui/**\n  # 认证登录地址\n  oauthLogin: http://localhost:9088/login', '4527d3e995a400ff42800b9f603ffd7a', '2021-11-01 16:26:50', '2021-11-01 03:26:49', 'nacos', '172.18.0.1', 'U', '');
INSERT INTO `his_config_info` VALUES (8, 4, 'ms-gateway-web-dev.yml', 'DEFAULT_GROUP', '', '# 服务端口\nserver:\n  port: 80\n\n# Spring 配置\nspring:\n  # Redis\n  redis:\n    host: ${REDIS_HOST:lms-redis}\n    port: 6379\n    password: 123456\n    timeout: 100ms\n    jedis:\n      pool:\n        max-active: 8\n        max-idle: 8\n        max-wait: -1ms\n        min-idle: 0\n    # 网关\n    gateway:\n      globalcors:\n        cors-configurations:\n          # 匹配所有请求\n          \'[/**]\':\n            # 跨域处理 允许所有的域\n            allowedOrigins: \"*\"\n            # 支持的方法\n            allowedMethods:\n              - GET\n              - POST\n              - PUT\n              - DELETE\n      # 路由\n      routes:\n        - id: user-service-route\n#          uri: http://localhost:8003\n          uri: lb://user-service\n          predicates:\n#            - Host = test.lmaye.com**\n            - Path=/**\n          filters:\n#            - PrefixPath=/user\n            - StripPrefix=1\n\n# 网关认证配置\ngateway-auth:\n  # Token 标识\n  authorizeToken: Authorization\n  # 放行的请求路径(eg: /api/shopify-plugin/category/list)\n  excludeUrls:\n    - /**/v3/api-docs\n    - /**/swagger-resources\n    - /**/swagger-resources/configuration/**\n    - /**/swagger-ui/**\n    - /**/swagger-ui.html\n    - /**/actuator/**\n    - /**/instances/**\n    - /**/*.js\n    - /**/*.css\n    - /**/*.png\n    - /**/*.ico\n  # 认证登录地址\n  oauthLogin: http://localhost:9088/login', '68c45edeb59762a141de79e380cbbe47', '2021-11-01 16:30:31', '2021-11-01 03:30:31', 'nacos', '172.18.0.1', 'U', '');
INSERT INTO `his_config_info` VALUES (8, 5, 'ms-gateway-web-dev.yml', 'DEFAULT_GROUP', '', '# 服务端口\nserver:\n  port: 80\n\n# Spring 配置\nspring:\n  # Redis\n  redis:\n    host: ${REDIS_HOST:lms-redis}\n    port: 6379\n    password: 123456\n    timeout: 100ms\n    jedis:\n      pool:\n        max-active: 8\n        max-idle: 8\n        max-wait: -1ms\n        min-idle: 0\n    # 网关\n    gateway:\n      globalcors:\n        cors-configurations:\n          # 匹配所有请求\n          \'[/**]\':\n            # 跨域处理 允许所有的域\n            allowedOrigins: \"*\"\n            # 支持的方法\n            allowedMethods:\n              - GET\n              - POST\n              - PUT\n              - DELETE\n      # 路由\n      routes:\n        - id: user-service-route\n#          uri: http://localhost:8003\n          uri: lb://user-service\n          predicates:\n#            - Host = test.lmaye.com**\n            - Path=/**\n          filters:\n#            - PrefixPath=/user\n            - StripPrefix=1\n\n# 网关认证配置\ngateway-auth:\n  # Token 标识\n  authorizeToken: Authorization\n  # 放行的请求路径(eg: /api/shopify-plugin/category/list)\n  excludeUrls:\n    - /**/v3/api-docs\n    - /**/swagger-resources\n    - /**/swagger-resources/configuration/**\n    - /**/swagger-ui/**\n    - /**/swagger-ui/index.html\n    - /**/actuator/**\n    - /**/instances/**\n    - /**/*.js\n    - /**/*.css\n    - /**/*.png\n    - /**/*.ico\n  # 认证登录地址\n  oauthLogin: http://localhost:9088/login', '1d41807a5f02e262ef71bef79275fb89', '2021-11-01 16:33:33', '2021-11-01 03:33:33', 'nacos', '172.18.0.1', 'U', '');
INSERT INTO `his_config_info` VALUES (8, 6, 'ms-gateway-web-dev.yml', 'DEFAULT_GROUP', '', '# 服务端口\nserver:\n  port: 80\n\n# Spring 配置\nspring:\n  # Redis\n  redis:\n    host: ${REDIS_HOST:lms-redis}\n    port: 6379\n    password: 123456\n    timeout: 100ms\n    jedis:\n      pool:\n        max-active: 8\n        max-idle: 8\n        max-wait: -1ms\n        min-idle: 0\n  cloud:\n    # 网关\n    gateway:\n      globalcors:\n        cors-configurations:\n          # 匹配所有请求\n          \'[/**]\':\n            # 跨域处理 允许所有的域\n            allowedOrigins: \"*\"\n            # 支持的方法\n            allowedMethods:\n              - GET\n              - POST\n              - PUT\n              - DELETE\n      # 路由\n      routes:\n        - id: user-service-route\n#          uri: http://localhost:8003\n          uri: lb://ms-service-user\n          predicates:\n#            - Host = test.lmaye.com**\n            - Path=/user/**\n          filters:\n#            - PrefixPath=/user\n            - StripPrefix=1\n\n# 网关认证配置\ngateway-auth:\n  # Token 标识\n  authorizeToken: Authorization\n  # 放行的请求路径(eg: /api/shopify-plugin/category/list)\n  excludeUrls:\n    - /**/v3/api-docs\n    - /**/swagger-resources\n    - /**/swagger-resources/configuration/**\n    - /**/swagger-ui/**\n    - /**/actuator/**\n    - /**/instances/**\n    - /**/*.js\n    - /**/*.css\n    - /**/*.png\n    - /**/*.ico\n  # 认证登录地址\n  oauthLogin: http://localhost:9088/login', '3893c27eb845af84d05a104c70b7a83c', '2021-11-01 16:36:26', '2021-11-01 03:36:26', 'nacos', '172.18.0.1', 'U', '');
INSERT INTO `his_config_info` VALUES (8, 7, 'ms-gateway-web-dev.yml', 'DEFAULT_GROUP', '', '# 服务端口\nserver:\n  port: 80\n\n# Spring 配置\nspring:\n  # Redis\n  redis:\n    host: ${REDIS_HOST:lms-redis}\n    port: 6379\n    password: 123456\n    timeout: 100ms\n    jedis:\n      pool:\n        max-active: 8\n        max-idle: 8\n        max-wait: -1ms\n        min-idle: 0\n  cloud:\n    # 网关\n    gateway:\n      globalcors:\n        cors-configurations:\n          # 匹配所有请求\n          \'[/**]\':\n            # 跨域处理 允许所有的域\n            allowedOrigins: \"*\"\n            # 支持的方法\n            allowedMethods:\n              - GET\n              - POST\n              - PUT\n              - DELETE\n      # 路由\n      routes:\n        - id: user-service-route\n          uri: lb://ms-service-user\n          predicates:\n            - Path=/user/**\n          filters:\n            - StripPrefix=1\n\n# 网关认证配置\ngateway-auth:\n  # Token 标识\n  authorizeToken: Authorization\n  # 放行的请求路径(eg: /api/shopify-plugin/category/list)\n  excludeUrls:\n    - /**/v3/api-docs\n    - /**/swagger-resources\n    - /**/swagger-resources/configuration/**\n    - /**/swagger-ui/**\n    - /**/actuator/**\n    - /**/instances/**\n    - /**/*.js\n    - /**/*.css\n    - /**/*.png\n    - /**/*.ico\n  # 认证登录地址\n  oauthLogin: http://localhost:9088/login', 'b173ab8d237bde9dcd1a80c8d3c27781', '2021-11-01 16:40:59', '2021-11-01 03:40:59', 'nacos', '172.18.0.1', 'U', '');
INSERT INTO `his_config_info` VALUES (8, 8, 'ms-gateway-web-dev.yml', 'DEFAULT_GROUP', '', '# 服务端口\nserver:\n  port: 80\n\n# Spring 配置\nspring:\n  # Redis\n  redis:\n    host: ${REDIS_HOST:lms-redis}\n    port: 6379\n    password: 123456\n    timeout: 100ms\n    jedis:\n      pool:\n        max-active: 8\n        max-idle: 8\n        max-wait: -1ms\n        min-idle: 0\n  cloud:\n    # 网关\n    gateway:\n      globalcors:\n        cors-configurations:\n          # 匹配所有请求\n          \'[/**]\':\n            # 跨域处理 允许所有的域\n            allowedOrigins: \"*\"\n            # 支持的方法\n            allowedMethods:\n              - GET\n              - POST\n              - PUT\n              - DELETE\n      # 路由\n      routes:\n        - id: user-service-route\n#          uri: http://localhost:8003\n          uri: lb://ms-service-user\n          predicates:\n#            - Host = test.lmaye.com**\n            - Path=/api/user/**\n          filters:\n#            - PrefixPath=/user\n            - StripPrefix=2\n\n# 网关认证配置\ngateway-auth:\n  # Token 标识\n  authorizeToken: Authorization\n  # 放行的请求路径(eg: /api/shopify-plugin/category/list)\n  excludeUrls:\n    - /**/v3/api-docs\n    - /**/swagger-resources\n    - /**/swagger-resources/configuration/**\n    - /**/swagger-ui/**\n    - /**/actuator/**\n    - /**/instances/**\n    - /**/*.js\n    - /**/*.css\n    - /**/*.png\n    - /**/*.ico\n  # 认证登录地址\n  oauthLogin: http://localhost:9088/login', 'ab367ff88a0ba28633f7b6effa90edfa', '2021-11-01 17:20:22', '2021-11-01 04:20:22', 'nacos', '172.18.0.1', 'U', '');
COMMIT;

-- ----------------------------
-- Table structure for permissions
-- ----------------------------
DROP TABLE IF EXISTS `permissions`;
CREATE TABLE `permissions` (
  `role` varchar(50) COLLATE utf8mb4_general_ci NOT NULL,
  `resource` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `action` varchar(8) COLLATE utf8mb4_general_ci NOT NULL,
  UNIQUE KEY `uk_role_permission` (`role`,`resource`,`action`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of permissions
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for roles
-- ----------------------------
DROP TABLE IF EXISTS `roles`;
CREATE TABLE `roles` (
  `username` varchar(50) COLLATE utf8mb4_general_ci NOT NULL,
  `role` varchar(50) COLLATE utf8mb4_general_ci NOT NULL,
  UNIQUE KEY `idx_user_role` (`username`,`role`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of roles
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for tenant_capacity
-- ----------------------------
DROP TABLE IF EXISTS `tenant_capacity`;
CREATE TABLE `tenant_capacity` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `tenant_id` varchar(128) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT 'Tenant ID',
  `quota` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '配额，0表示使用默认值',
  `usage` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '使用量',
  `max_size` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '单个配置大小上限，单位为字节，0表示使用默认值',
  `max_aggr_count` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '聚合子配置最大个数',
  `max_aggr_size` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '单个聚合数据的子配置大小上限，单位为字节，0表示使用默认值',
  `max_history_count` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '最大变更历史数量',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_tenant_id` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='租户容量信息表';

-- ----------------------------
-- Records of tenant_capacity
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for tenant_info
-- ----------------------------
DROP TABLE IF EXISTS `tenant_info`;
CREATE TABLE `tenant_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `kp` varchar(128) COLLATE utf8_bin NOT NULL COMMENT 'kp',
  `tenant_id` varchar(128) COLLATE utf8_bin DEFAULT '' COMMENT 'tenant_id',
  `tenant_name` varchar(128) COLLATE utf8_bin DEFAULT '' COMMENT 'tenant_name',
  `tenant_desc` varchar(256) COLLATE utf8_bin DEFAULT NULL COMMENT 'tenant_desc',
  `create_source` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT 'create_source',
  `gmt_create` bigint(20) NOT NULL COMMENT '创建时间',
  `gmt_modified` bigint(20) NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_tenant_info_kptenantid` (`kp`,`tenant_id`),
  KEY `idx_tenant_id` (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='tenant_info';

-- ----------------------------
-- Records of tenant_info
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `username` varchar(50) COLLATE utf8mb4_general_ci NOT NULL,
  `password` varchar(500) COLLATE utf8mb4_general_ci NOT NULL,
  `enabled` tinyint(1) NOT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of users
-- ----------------------------
BEGIN;
INSERT INTO `users` VALUES ('nacos', '$2a$10$EuWPZHzz32dJN7jexM34MOeYirDdFAZm2kuWj7VEOJhhZkDrxfvUu', 1);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
