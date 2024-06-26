use project_travel;

-- #1. 테이블 삭제
-- #2. 테이블 생성
-- #3. 테이블 테스트 데이터 삽입

-- #1. 테이블 drop
-- 외래 키 제약 조건 이름 조회 및 삭제 스크립트 생성
-- SET SESSION group_concat_max_len = 1000000;

-- SET @sql = (
--   SELECT GROUP_CONCAT(
--     CONCAT('ALTER TABLE ', table_name, ' DROP FOREIGN KEY ', constraint_name) SEPARATOR '; '
--   )
--   FROM INFORMATION_SCHEMA.KEY_COLUMN_USAGE
--   WHERE TABLE_SCHEMA = 'project_travel'
--   AND TABLE_NAME IN ('tbl_reply', 'tbl_like', 'tbl_bookmark', 'tbl_chat_message', 'tbl_chat_user', 'tbl_board', 'tbl_user_detail', 'tbl_noti', 'tbl_travel_destination')
--   AND REFERENCED_TABLE_NAME IS NOT NULL
-- );

-- -- @sql 확인
-- SELECT @sql;

-- -- #2. 생성된 SQL 명령 실행
-- -- CONCAT을 사용하여 최종 SQL 명령을 실행하는 절차를 수행합니다.
-- SET @sql = CONCAT(@sql, ';');
-- PREPARE stmt FROM @sql;
-- EXECUTE stmt;
-- DEALLOCATE PREPARE stmt;

-- -- 생성된 스크립트 확인 (디버깅 용도로, 실제 사용 시 주석 처리 가능)
-- -- SELECT @sql;

-- -- 생성된 스크립트 실행
-- PREPARE stmt FROM @sql;
-- EXECUTE stmt;
-- DEALLOCATE PREPARE stmt;

-- ALTER TABLE tbl_board DROP FOREIGN KEY tbl_board_ibfk_1;
-- ALTER TABLE tbl_board DROP FOREIGN KEY tbl_board_ibfk_2;
-- ALTER TABLE tbl_bookmark DROP FOREIGN KEY tbl_bookmark_ibfk_1;
-- ALTER TABLE tbl_bookmark DROP FOREIGN KEY tbl_bookmark_ibfk_2;
-- ALTER TABLE tbl_chat_message DROP FOREIGN KEY tbl_chat_message_ibfk_1;
-- ALTER TABLE tbl_chat_message DROP FOREIGN KEY tbl_chat_message_ibfk_2;
-- ALTER TABLE tbl_chat_user DROP FOREIGN KEY tbl_chat_user_ibfk_1;
-- ALTER TABLE tbl_chat_user DROP FOREIGN KEY tbl_chat_user_ibfk_2;
-- ALTER TABLE tbl_like DROP FOREIGN KEY tbl_like_ibfk_1;
-- ALTER TABLE tbl_like DROP FOREIGN KEY tbl_like_ibfk_2;
-- ALTER TABLE tbl_noti DROP FOREIGN KEY tbl_noti_ibfk_1;
-- ALTER TABLE tbl_noti DROP FOREIGN KEY tbl_noti_ibfk_2;
-- ALTER TABLE tbl_reply DROP FOREIGN KEY tbl_reply_ibfk_1;
-- ALTER TABLE tbl_reply DROP FOREIGN KEY tbl_reply_ibfk_2;
-- ALTER TABLE tbl_user_detail DROP FOREIGN KEY tbl_user_detail_ibfk_1;

-- -- SET foreign_key_checks = 0;    # 외래키 체크 설정 해제
-- -- drop table tbl_board;
-- -- SET foreign_key_checks = 1;    # 외래키 체크 설정

-- -- 테이블 삭제
-- DROP TABLE IF EXISTS tbl_reply;
-- DROP TABLE IF EXISTS tbl_like;
-- DROP TABLE IF EXISTS tbl_bookmark;
-- DROP TABLE IF EXISTS tbl_chat_message;
-- DROP TABLE IF EXISTS tbl_chat_user;
-- DROP TABLE IF EXISTS tbl_board;
-- DROP TABLE IF EXISTS tbl_board_image;
-- DROP TABLE IF EXISTS tbl_user_detail;
-- DROP TABLE IF EXISTS tbl_noti;
-- DROP TABLE IF EXISTS tbl_travel_destination;
-- DROP TABLE IF EXISTS tbl_chat_room;
-- DROP TABLE IF EXISTS tbl_user;
-- DROP TABLE IF EXISTS tbl_noti_type;
-- DROP TABLE IF EXISTS tbl_category;
-- DROP TABLE IF EXISTS tbl_mate_type;


-- #2. 테이블 생성

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
                            `auth` ENUM('COMMON', 'ADMIN') NOT NULL,
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
                             `view_count` INT NULL DEFAULT 0,
                             `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                             `updated_at` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
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
                                   `image_order` INT NULL,
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
                                 PRIMARY KEY (`mate_type_id`)
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


-- #3. 테스트 데이터 추가

-- tbl_user 테이블에 사용자 데이터 삽입 (COMMON 3명, ADMIN 1명)
-- 가정: account (varchar), username (varchar), user_type (varchar), email (varchar), password (varchar)
INSERT INTO tbl_user (account, name, nickname, auth, email, password, birthday)
VALUES
    ('tester1', '테스터1', 'user1', 'COMMON', 'common1@example.com', 'password1', '2000-01-18'),
    ('tester2', '테스터2', 'user2', 'COMMON', 'common2@example.com', 'password2', '1999-05-10'),
    ('tester3', '테스터3', 'user3', 'COMMON', 'common3@example.com', 'password3', '1998-03-24'),
    ('admin1','관리자', 'user4', 'ADMIN', 'admin@example.com', 'password4', '1995-06-03');


-- tbl_category 테이블에 mate(동행), feed(피드)
INSERT INTO tbl_category (category_name)
VALUES
    ('mate'),
    ('feed');


-- tbl_board 테이블에 게시판 데이터 삽입 (category_id 1인 것 3개, 2인 것 3개)
INSERT INTO tbl_board (category_id, title, content, account)
VALUES
    (1, '동행 Title 1-1', 'Board Content 1-1', 'tester1'),
    (1, '동행 Title 1-2', 'Board Content 1-2', 'tester2'),
    (1, '동행 Title 1-3', 'Board Content 1-3', 'tester3'),
    (2, '피드 Title 2-1', 'Board Content 2-1', 'tester1'),
    (2, '피드 Title 2-2', 'Board Content 2-2', 'tester2'),
    (2, '피드 Title 2-3', 'Board Content 2-3', 'tester3'),
    (1, '동행 Title 1-4', 'Board Content 1-4', 'tester1'),
    (1, '동행 Title 1-5', 'Board Content 1-5', 'tester2'),
    (1, '동행 Title 1-6', 'Board Content 1-6', 'tester3'),
    (2, '피드 Title 2-4', 'Board Content 2-4', 'tester1'),
    (2, '피드 Title 2-5', 'Board Content 2-5', 'tester2'),
    (2, '피드 Title 2-6', 'Board Content 2-6', 'tester3')
;

-- tbl_mate_type 테이블에 데이터 삽입
-- 가정: mate_type_id (auto_increment), type_name (varchar)
INSERT INTO tbl_mate_type (mate_type_name)
VALUES
    ('산책'),
    ('맛집'),
    ('숙박');


-- tbl_reply 테이블에 댓글 데이터 삽입
-- 가정: reply_id (auto_increment), board_id (int), account (varchar), content (text), parent_reply_id (int)
INSERT INTO tbl_reply (board_id, account, reply_text, parent_reply_id)
VALUES
    (1, 'tester2', '댓글 - 글번호 1 by user 2', NULL),
    (1, 'tester3', '댓글 - 글번호 1 by user 3', NULL),
    (2, 'tester1', '댓글 - 글번호 2 by user 1', NULL),
    (2, 'tester3', '댓글 - 글번호 2 by user 3', NULL),
    (3, 'tester2', '댓글 - 글번호 3 by user 2', NULL),
    (3, 'tester1', '댓글 - 글번호 3 by user 1', NULL),
    (3, 'tester3', '댓글 - 글번호 3 by user 3', NULL),
    (4, 'tester2', '댓글 - 글번호 4 by user 2', NULL),
    (5, 'tester1', '댓글 - 글번호 5 by user 1', NULL),
    (5, 'tester3', '댓글 - 글번호 5 by user 3', NULL),
    (5, 'tester2', '댓글 - 글번호 5 by user 2', NULL),
    (6, 'tester1', '댓글 - 글번호 6 by user 1', NULL),
    (6, 'tester2', '댓글 - 글번호 6 by user 2', NULL),
    (6, 'tester2', '댓글 - 글번호 6 by user 2', NULL);

-- 대댓글 삽입 예시 (부모 댓글 ID 사용)
INSERT INTO tbl_reply (board_id, account, reply_text, parent_reply_id)
VALUES
    (1, 'tester3', '대댓글 - 글번호 1 by user 3', 1),
    (2, 'tester3', '대댓글 - 글번호 2 by user 3', 3),
    (3, 'tester1', '대댓글 - 글번호 3 by user 1', 5),
    (3, 'tester3', '대댓글 - 글번호 3 by user 3', 5),
    (5, 'tester3', '대댓글 - 글번호 5 by user 3', 9),
    (5, 'tester2', '대댓글 - 글번호 5 by user 2', 9),
    (6, 'tester2', '대댓글 - 글번호 6 by user 2', 12);

-- user detail 데이터 추가
INSERT INTO tbl_user_detail (mbti, one_liner, rating, account)
VALUES ('ISFJ', '테스트테스트', 50, 'kitty');

SELECT * FROM tbl_user;
SELECT * FROM tbl_user_detail;

DELETE FROM tbl_user
WHERE account = 'hahahoho';

ALTER TABLE tbl_user
ADD (profile_image VARCHAR(200));