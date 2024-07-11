package com.travel.project.dto.response;

import com.travel.project.entity.NestedReply;
import com.travel.project.entity.Reply;
import lombok.*;

import java.time.LocalDateTime;

@Getter @ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
// 대댓글 조회 목록 요청시 필요한 정보들을 포장한 객체
public class NestedReplyResponseDetailDto {

    private long nestedReplyId; // 대댓글 번호
    private long replyId; // 댓글 번호
    private String text; // 대댓글 내용
    private String writer; // 대댓글 작성자
    private LocalDateTime createAt; // 대댓글 작성일
    private String account;
    private String profileImage;

    // 엔터티를 DTO로 변환하는 생성자
    public NestedReplyResponseDetailDto(NestedReply r) {
        this.replyId = r.getReplyId();
        this.nestedReplyId = r.getNestedReplyId();
        this.text = r.getReplyText();
        this.writer = r.getReplyWriter();
        this.createAt = r.getCreatedAt();
        this.account = r.getAccount();
        this.profileImage = r.getProfileImage();
    }
}
