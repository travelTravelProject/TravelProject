package com.travel.withtrip.dto.request;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Getter @ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
public class FeedPostDto {

    @NotBlank
    @Size(min = 1, max = 100)
    private String content; // 피드 내용

    @NotNull
    private Long boardId;

    // 피드 이미지 데이터
    private List<MultipartFile> files;

}
