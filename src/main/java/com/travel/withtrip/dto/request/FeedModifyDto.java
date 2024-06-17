package com.travel.withtrip.dto.request;

import com.travel.withtrip.entity.Board;
import lombok.*;

@Getter @ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FeedModifyDto {

    private long boardId; // 게시글 번호
    private String account; // 게시글 작성자 계정
    private int categoryId; // 게시글 타입(동행, 피드) 번호
    private String content; // 게시글 내용

    public Board toEntity() {

        return Board.builder()
                .boardId(boardId)
                .account(account)
                .categoryId(2)
                .content(content)
                .build();
    }
}
