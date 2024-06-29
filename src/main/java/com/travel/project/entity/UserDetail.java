package com.travel.project.entity;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter @ToString @Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDetail {

    private int userDetailId; // 인덱스
    private String mbti; // mbti
    private String oneLiner; // 소개글
    @Setter
    private String profileImage; // 프로필 사진 경로
    private int rating; // 평점

    private String account; // 계정


}
