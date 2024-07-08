package com.travel.project.dto.request;

import com.travel.project.entity.Reply;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter @Setter @ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
// 댓글 수정시 클라이언트에서 서버로 보낼 때 필요한 데이터들을 포장한 객체
public class ReplyRequestModifyDto {

    @NotNull
    private Long replyId; // 수정할 댓글의 댓글번호
    @NotBlank
    private String newText; // 새로운 댓글 내용
    @NotNull
    private Long boardId; // 글 번호 -> 수정 후 목록조회를 위해 필요
    private Long parentReplyId; // 부모 댓글번호 (대댓글일 경우에만 사용)
    private LocalDateTime updatedAt; // 댓글 수정일

    // DTO -> 엔터티로 변환하는 메서드
    public Reply toEntity() {
        return Reply.builder()
                .replyText(this.newText)
                .replyId(this.replyId)
                .boardId(this.boardId)
                .updatedAt(this.updatedAt != null ? this.updatedAt : LocalDateTime.now()) // 수정 시간을 업데이트
//                .parentReplyId(this.parentReplyId)
                .build();
    }
}
