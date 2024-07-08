package com.travel.project.dto.request;

import com.travel.project.entity.NestedReply;
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
// 대댓글 수정시 클라이언트에서 서버로 보낼 때 필요한 데이터들을 포장한 객체
public class NestedReplyRequestModifyDto {

    private Long nestedReplyId; // 수정할 대댓글의 댓글번호
    private String newText; // 새로운 댓글 내용
    private Long replyId; // 댓글 번호 -> 수정 후 댓글조회를 위해 필요
    private LocalDateTime updatedAt; // 댓글 수정일

    // DTO -> 엔터티로 변환하는 메서드
    public NestedReply toEntity() {
        return NestedReply.builder()
                .nestedReplyId(this.nestedReplyId)
                .replyText(this.newText)
                .replyId(this.replyId)
                .updatedAt(this.updatedAt)
                .build();
    }
}
