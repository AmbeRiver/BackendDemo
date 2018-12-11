

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;


-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(30) DEFAULT NULL,
  `password` varchar(200) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `userfullname` varchar(50) DEFAULT NULL,
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `role` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8;

INSERT INTO `users`(`id`, `username`, `password`, `email`, `userfullname`, `create_date`, `update_date`, `role`) VALUES (1, 'Admin', '$2a$10$B6uexrhGEK5o0shjsZ/JRexgO906VaC/uKBryB/u9BCNU3zbHa1Gy', 'admin@123.com', 'Admin', '2018-08-12 15:31:15', '2018-09-03 17:42:40', 'ROLE_ADMIN');
