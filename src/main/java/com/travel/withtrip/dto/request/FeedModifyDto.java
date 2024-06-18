package com.travel.withtrip.dto.request;

import com.travel.withtrip.entity.Board;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter @ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FeedModifyDto {

    private long boardId; // 게시글 번호
    private String account; // 게시글 작성자 계정
    private int categoryId; // 게시글 타입(동행, 피드) 번호
    private String content; // 게시글 내용

    private List<MultipartFile> files;  // 피드 이미지 데이터 ? 프론트에서 순서까지 정리한 뒤...서버로?
    // 수정일이 생성일과 다를 경우 이미지는 다른 폴더에 저장됨...
    // 그럼 기존 폴더껀 지우고 수정일에 해당하는 경로에 이미지 새로 저장하는게 맞는건가?

    public Board toEntity() {

        return Board.builder()
                .boardId(boardId)
                .account(account)
                .categoryId(2)
                .content(content)
                .build();
    }
}
