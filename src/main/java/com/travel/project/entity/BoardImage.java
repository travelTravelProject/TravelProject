package com.travel.project.entity;

import lombok.*;

@Getter @ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardImage {

    private long imageId; // 이미지 인덱스 번호
    private long boardId; // 이미지가 업로드된 게시글 번호
    private String imagePath; // 이미지 경로
    private int imageOrder; // 게시글에서 이미지 순서

}
