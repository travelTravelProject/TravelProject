package com.travel.project.entity;

/*
    -- 댓글 테이블 생성
    CREATE TABLE `tbl_reply` (
        `reply_id` INT NOT NULL AUTO_INCREMENT,
        `board_id` INT NOT NULL,
        `account` VARCHAR(20) NOT NULL,
        `reply_text` VARCHAR(255) NULL,
        `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
        `updated_at` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
        `status` ENUM('A', 'D') NULL DEFAULT 'A',
        `parent_reply_id` INT NULL,
        `reply_writer` VARCHAR(20) NULL,
        PRIMARY KEY (`reply_id`),
        FOREIGN KEY (`board_id`) REFERENCES `tbl_board` (`board_id`),
        FOREIGN KEY (`account`) REFERENCES `tbl_user` (`account`)
    );
*/

import lombok.*;
import java.time.LocalDateTime;

@Getter @ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
// db와 1대1 매칭되는 객체
public class Reply {

    @Setter
    private long replyId; // 댓글번호
    private long boardId; // 글번호
    private String account; // 댓글 쓴 회원의 계정
    @Setter
    private String replyText; // 댓글 내용
    private LocalDateTime createdAt; // 댓글 작성일
    private LocalDateTime updatedAt; // 댓글 수정일
    private Status status; // 댓글의 상태
    private String replyWriter; // 댓글 작성자
    private Long parentReplyId; // 부모 댓글번호
}
