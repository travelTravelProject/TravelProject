package com.travel.project.dto.response;

import com.travel.project.entity.BoardImage;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter @ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MyFeedDto {

    private long boardId;
    private String account;

    private BoardImage imagePath; // 썸네일용 이미지 하나

    @Setter
    private int replyCount;
    @Setter
    private int likeCount;
    @Setter
    private int bookmarkCount;


}
