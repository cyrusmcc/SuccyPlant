CREATE TABLE `post_list` (
                             `post_list_id` bigint NOT NULL AUTO_INCREMENT,
                             PRIMARY KEY (`post_list_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `post` (
                        `post_id` bigint NOT NULL AUTO_INCREMENT,
                        `auth_username` varchar(255) DEFAULT NULL,
                        `image_id` varchar(255) DEFAULT NULL,
                        `title` varchar(255) DEFAULT NULL,
                        `type` int DEFAULT NULL,
                        `post_list_id` bigint DEFAULT NULL,
                        PRIMARY KEY (`post_id`),
                        KEY `FKkdcl0uw17gct3tmlvxlektqwb` (`post_list_id`),
                        CONSTRAINT `FKkdcl0uw17gct3tmlvxlektqwb` FOREIGN KEY (`post_list_id`) REFERENCES `post_list` (`post_list_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `plant` (
                         `plant_id` bigint NOT NULL AUTO_INCREMENT,
                         `common_name` varchar(255) DEFAULT NULL,
                         `desc_text_file_url` varchar(255) DEFAULT NULL,
                         `science_name` varchar(255) DEFAULT NULL,
                         `post_id` bigint NOT NULL,
                         PRIMARY KEY (`plant_id`),
                         UNIQUE KEY `UK_a40qkxbw9houh0rpofwd7xybg` (`post_id`),
                         CONSTRAINT `FKrv84d146lhj1m0b54edmjbk8x` FOREIGN KEY (`post_id`) REFERENCES `post` (`post_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `content_tag` (
                               `tag_id` bigint NOT NULL AUTO_INCREMENT,
                               `category` varchar(255) DEFAULT NULL,
                               `value` varchar(255) DEFAULT NULL,
                               PRIMARY KEY (`tag_id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `post_tag` (
                            `post_id` bigint NOT NULL,
                            `tag_id` bigint NOT NULL,
                            PRIMARY KEY (`tag_id`,`post_id`),
                            KEY `FKc2auetuvsec0k566l0eyvr9cs` (`post_id`),
                            CONSTRAINT `FKc2auetuvsec0k566l0eyvr9cs` FOREIGN KEY (`post_id`) REFERENCES `post` (`post_id`),
                            CONSTRAINT `FKdpeh2os0rodt2s5ur1ifrvq9l` FOREIGN KEY (`tag_id`) REFERENCES `content_tag` (`tag_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


