CREATE TABLE `item` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(64) COLLATE utf8_unicode_ci NOT NULL DEFAULT '',
  `price` double(10,0) NOT NULL DEFAULT '0',
  `description` varchar(500) COLLATE utf8_unicode_ci NOT NULL DEFAULT '',
  `sales` int(11) NOT NULL DEFAULT '0',
  `img_url` varchar(255) COLLATE utf8_unicode_ci NOT NULL DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


CREATE TABLE `item_stock` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `stock` int(11) NOT NULL DEFAULT '0',
  `item_id` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


CREATE TABLE `user_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(64) COLLATE utf8_unicode_ci NOT NULL DEFAULT '',
  `gender` tinyint(4) NOT NULL DEFAULT '0' COMMENT '//1代表男性2代表女性',
  `age` int(11) NOT NULL DEFAULT '0',
  `telphone` varchar(255) COLLATE utf8_unicode_ci NOT NULL DEFAULT '',
  `register_mode` varchar(255) COLLATE utf8_unicode_ci NOT NULL DEFAULT '' COMMENT '//byphone,bywechat,byalipay',
  `third_party_id` varchar(64) COLLATE utf8_unicode_ci NOT NULL DEFAULT '',
  PRIMARY KEY (`id`),
  UNIQUE KEY `telphone_unique_index` (`telphone`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


CREATE TABLE `user_password` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `encrpt_password` varchar(128) COLLATE utf8_unicode_ci NOT NULL DEFAULT '',
  `user_id` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


CREATE TABLE `order_info` (
  `id` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `user_id` int(11) NOT NULL DEFAULT '0',
  `item_id` int(11) NOT NULL DEFAULT '0',
  `item_price` double NOT NULL DEFAULT '0',
  `amount` int(11) NOT NULL DEFAULT '0',
  `order_price` double NOT NULL DEFAULT '0',
  `promo_id` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


CREATE TABLE `sequence_info` (
  `name` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `current_value` int(11) NOT NULL DEFAULT '0',
  `step` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

INSERT INTO `sequence_info`(`name`, `current_value`, `step`) VALUES ('order_info', 0, 1);



CREATE TABLE `promo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `promo_name` varchar(255) COLLATE utf8_unicode_ci NOT NULL DEFAULT '',
  `start_date` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `item_id` int(11) NOT NULL DEFAULT '0',
  `promo_item_price` double NOT NULL DEFAULT '0',
  `end_date` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

INSERT INTO `promo`(`id`, `promo_name`, `start_date`, `item_id`, `promo_item_price`, `end_date`) VALUES (1, '小米手机抢购活动', '2019-09-04 17:30:05', 1, 200, '2019-09-05 15:34:40');


