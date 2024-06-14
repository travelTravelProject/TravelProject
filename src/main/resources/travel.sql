use project_travel;

CREATE TABLE `tbl_user` (
                            `account` VARCHAR(20) NOT NULL,
                            `password` VARCHAR(100) NOT NULL,
                            `name` VARCHAR(30) NOT NULL,
                            `email` VARCHAR(100) NOT NULL,
                            `nickname` VARCHAR(30) NOT NULL,
                            `birthday` DATE NOT NULL,
                            `gender` ENUM('M', 'F') NOT NULL,
                            `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                            `updated_at` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                            `status` ENUM('A', 'D') NULL DEFAULT 'A',
                            `auth` ENUM('common', 'admin') NOT NULL,
                            PRIMARY KEY (`account`)
);

CREATE TABLE `tbl_category` (
                                `category_id` INT NOT NULL AUTO_INCREMENT,
                                `category_name` VARCHAR(255) NULL,
                                PRIMARY KEY (`category_id`)
);

CREATE TABLE `tbl_board` (
                             `board_id` INT NOT NULL AUTO_INCREMENT,
                             `account` VARCHAR(20) NOT NULL,
                             `category_id` INT NOT NULL,
                             `title` VARCHAR(100) NULL,
                             `content` TEXT NULL,
                             `view_count` INT NULL,
                             `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                             `updated_at` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                             `status` ENUM('A', 'D') NULL DEFAULT 'A',
                             `location` VARCHAR(255) NULL,
                             `start_date` DATE NULL,
                             `end_date` DATE NULL,
                             PRIMARY KEY (`board_id`),
                             FOREIGN KEY (`account`) REFERENCES `tbl_user` (`account`),
                             FOREIGN KEY (`category_id`) REFERENCES `tbl_category` (`category_id`)
);

CREATE TABLE `tbl_reply` (
                             `reply_id` INT NOT NULL AUTO_INCREMENT,
                             `board_id` INT NOT NULL,
                             `account` VARCHAR(20) NOT NULL,
                             `reply_text` VARCHAR(255) NULL,
                             `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                             `updated_at` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                             `status` ENUM('A', 'D') NULL DEFAULT 'A',
                             `parent_reply_id` INT NULL,
                             PRIMARY KEY (`reply_id`),
                             FOREIGN KEY (`board_id`) REFERENCES `tbl_board` (`board_id`),
                             FOREIGN KEY (`account`) REFERENCES `tbl_user` (`account`)
);

CREATE TABLE `tbl_like` (
                            `like_id` INT NOT NULL AUTO_INCREMENT,
                            `account` VARCHAR(20) NOT NULL,
                            `board_id` INT NOT NULL,
                            `created_at` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
                            PRIMARY KEY (`like_id`),
                            FOREIGN KEY (`account`) REFERENCES `tbl_user` (`account`),
                            FOREIGN KEY (`board_id`) REFERENCES `tbl_board` (`board_id`)
);

CREATE TABLE `tbl_travel_destination` (
                                          `destination_id` INT NOT NULL AUTO_INCREMENT,
                                          `nation` VARCHAR(100) NULL,
                                          `city` VARCHAR(100) NULL,
                                          PRIMARY KEY (`destination_id`)
);

CREATE TABLE `tbl_bookmark` (
                                `bookmark_id` INT NOT NULL AUTO_INCREMENT,
                                `account` VARCHAR(20) NOT NULL,
                                `board_id` INT NOT NULL,
                                `created_at` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
                                PRIMARY KEY (`bookmark_id`),
                                FOREIGN KEY (`account`) REFERENCES `tbl_user` (`account`),
                                FOREIGN KEY (`board_id`) REFERENCES `tbl_board` (`board_id`)
);

CREATE TABLE `tbl_chat_room` (
                                 `chat_room_id` INT NOT NULL AUTO_INCREMENT,
                                 `board_id` INT NOT NULL,
                                 `chat_room_type` ENUM('DM', 'GROUP'),
                                 `created_at` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
                                 PRIMARY KEY (`chat_room_id`),
                                 FOREIGN KEY (`board_id`) REFERENCES `tbl_board` (`board_id`)
);

CREATE TABLE `tbl_chat_message` (
                                    `chat_id` INT NOT NULL AUTO_INCREMENT,
                                    `chat_room_id` INT NOT NULL,
                                    `account` VARCHAR(20) NOT NULL,
                                    `message` TEXT NOT NULL,
                                    `created_at` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
                                    `read_status` ENUM('T', 'F') NOT NULL DEFAULT 'F',
                                    PRIMARY KEY (`chat_id`),
                                    FOREIGN KEY (`chat_room_id`) REFERENCES `tbl_chat_room` (`chat_room_id`),
                                    FOREIGN KEY (`account`) REFERENCES `tbl_user` (`account`)
);

CREATE TABLE `tbl_board_image` (
                                   `image_id` INT NOT NULL AUTO_INCREMENT,
                                   `board_id` INT NOT NULL,
                                   `created_at` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
                                   `image_path` VARCHAR(255) NULL DEFAULT 'none',
                                   `order` INT NULL,
                                   PRIMARY KEY (`image_id`),
                                   FOREIGN KEY (`board_id`) REFERENCES `tbl_board` (`board_id`)
);

CREATE TABLE `tbl_noti_type` (
                                 `noti_type_id` INT NOT NULL AUTO_INCREMENT,
                                 `noti_type_name` VARCHAR(50) NULL,
                                 PRIMARY KEY (`noti_type_id`)
);

CREATE TABLE `tbl_noti` (
                            `noti_id` INT NOT NULL AUTO_INCREMENT,
                            `account` VARCHAR(20) NOT NULL,
                            `noti_type_id` INT NOT NULL,
                            `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                            `read_status` ENUM('T', 'F') NOT NULL DEFAULT 'F',
                            PRIMARY KEY (`noti_id`),
                            FOREIGN KEY (`account`) REFERENCES `tbl_user` (`account`),
                            FOREIGN KEY (`noti_type_id`) REFERENCES `tbl_noti_type` (`noti_type_id`)
);

CREATE TABLE `tbl_chat_user` (
                                 `chat_user_id` INT NOT NULL AUTO_INCREMENT,
                                 `account` VARCHAR(20) NOT NULL,
                                 `chat_room_id` INT NOT NULL,
                                 `created_at` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
                                 PRIMARY KEY (`chat_user_id`),
                                 FOREIGN KEY (`account`) REFERENCES `tbl_user` (`account`),
                                 FOREIGN KEY (`chat_room_id`) REFERENCES `tbl_chat_room` (`chat_room_id`)
);

CREATE TABLE `tbl_mate_type` (
                                 `mate_type_id` INT NOT NULL AUTO_INCREMENT,
                                 `mate_type_name` VARCHAR(50) NULL,
                                 `board_id` INT NOT NULL,
                                 PRIMARY KEY (`mate_type_id`),
                                 FOREIGN KEY (`board_id`) REFERENCES `tbl_board` (`board_id`)
);

CREATE TABLE `tbl_user_detail` (
                                   `user_detail_id` INT NOT NULL AUTO_INCREMENT,
                                   `mbti` CHAR(4) NULL,
                                   `one_liner` VARCHAR(100) NULL,
                                   `profile_image` VARCHAR(255) NULL DEFAULT 'none',
                                   `rating` INT NULL DEFAULT 0,
                                   `account` VARCHAR(20) NOT NULL,
                                   PRIMARY KEY (`user_detail_id`),
                                   FOREIGN KEY (`account`) REFERENCES `tbl_user` (`account`)
);
