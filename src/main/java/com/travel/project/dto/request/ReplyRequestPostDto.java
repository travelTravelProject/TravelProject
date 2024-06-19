package com.travel.project.dto.request;

import lombok.*;

@Getter @Setter @ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
// 댓글 입력시 클라이언트에서 서버로 보낼 때 필요한 데이터들을 포장한 객체
public class ReplyRequestPostDto {

    private String text; // 댓글 내용
    private String author; // 댓글 작성자
    private Long boardId; // 원본 글번호
    // '답글'을 눌렀을 때 자동으로 댓글번호 값이 들어가게함
    private Long parentReplyId; // 부모 댓글번호 (대댓글일 경우에만 사용)
}
