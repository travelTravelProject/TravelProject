package com.travel.project.dto.request;

import lombok.*;

@Getter @Setter @ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
// 대댓글 입력시 클라이언트에서 서버로 보낼 때 필요한 데이터들을 포장한 객체
public class NestedReplyRequestPostDto {

    private int nestedReplyId; // 대댓글 번호
    private int replyId; // 댓글 번호
    private String text; // 대댓글 내용
    private String author; // 대댓글 작성자
}
