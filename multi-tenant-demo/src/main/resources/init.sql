create database tenant_manager character set utf8mb4 collate utf8mb4_general_ci;
create database tenant_test character set utf8mb4 collate utf8mb4_general_ci;

use tenant_manager;

CREATE TABLE `auth_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(100) NOT NULL COMMENT '用户名',
  `password` varchar(100) NOT NULL COMMENT '密码',
  `locked` tinyint(1) NOT NULL COMMENT '是否锁定（1锁定，0没锁定）',
  `salt` varchar(255) NOT NULL COMMENT '盐',
  `lastPasswordResetDate` date DEFAULT NULL COMMENT '密码最后更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8mb4;


INSERT INTO `auth_user`(`id`, `username`, `password`, `locked`, `salt`, `lastPasswordResetDate`) VALUES (1, 'manager_admin', '$2a$10$sEAnLKbZhRBevO7ZGkYWPuraXqeZHAiT6cjcdTafyDaiWC5/wcRKi', 0, '', '2018-11-22');

use tenant_test;

CREATE TABLE `auth_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(100) NOT NULL COMMENT '用户名',
  `password` varchar(100) NOT NULL COMMENT '密码',
  `locked` tinyint(1) NOT NULL COMMENT '是否锁定（1锁定，0没锁定）',
  `salt` varchar(255) NOT NULL COMMENT '盐',
  `lastPasswordResetDate` date DEFAULT NULL COMMENT '密码最后更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8mb4;


INSERT INTO `auth_user`(`id`, `username`, `password`, `locked`, `salt`, `lastPasswordResetDate`) VALUES (1, 'test_admin', '$2a$10$sEAnLKbZhRBevO7ZGkYWPuraXqeZHAiT6cjcdTafyDaiWC5/wcRKi', 0, '', '2018-11-22');