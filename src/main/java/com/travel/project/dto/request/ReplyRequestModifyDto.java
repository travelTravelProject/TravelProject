package com.travel.project.dto.request;

import com.travel.project.entity.Reply;
import lombok.*;

@Getter @Setter @ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
// 댓글 수정시 클라이언트에서 서버로 보낼 때 필요한 데이터들을 포장한 객체
public class ReplyRequestModifyDto {

    private Long replyId; // 수정할 댓글의 댓글번호
    private String newText; // 새로운 댓글 내용
    private Long boardId; // 글 번호 -> 수정 후 목록조회를 위해 필요
    private Long parentReplyId; // 부모 댓글번호 (대댓글일 경우에만 사용)

    // DTO -> 엔터티로 변환하는 메서드
    public Reply toEntity() {
        return Reply.builder()
                .replyText(this.newText)
                .replyId(this.replyId)
                .boardId(this.boardId)
                .parentReplyId(this.parentReplyId)
                .build();
    }
}
