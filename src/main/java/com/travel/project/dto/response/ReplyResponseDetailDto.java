package com.travel.project.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.travel.project.entity.Reply;
import lombok.*;

import java.time.LocalDateTime;

@Getter @ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
// 댓글 조회 목록 요청시 필요한 정보들을 포장한 객체
public class ReplyResponseDetailDto {

    private long replyId; // 댓글 번호
    private String text; // 댓글 내용
    private String writer; // 댓글 작성자

    @JsonFormat(pattern = "yyyy년 MM월 dd일 HH:mm")
    private LocalDateTime createAt; // 댓글 작성일

    // 엔터티를 DTO로 변환하는 생성자
    public ReplyResponseDetailDto(Reply r) {
        this.replyId = r.getReplyId();
        this.text = r.getReplyText();
        this.writer = r.getReplyWriter();
        this.createAt = r.getCreatedAt();
    }
}
