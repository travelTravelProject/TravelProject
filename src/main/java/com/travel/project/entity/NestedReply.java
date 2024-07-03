package com.travel.project.entity;

import lombok.*;

import java.time.LocalDateTime;

@Getter @ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
// db와 1대1 매칭되는 객체
public class NestedReply {

    @Setter
    private long nestedReplyId; // 대댓글 번호
    private long replyId; // 댓글번호
    @Setter
    private String replyText; // 대댓글 내용
    private LocalDateTime createdAt; // 대댓글 작성일
    private String replyWriter; // 대댓글 작성자
    private String account; // 대댓글 쓴 회원의 계정
}
