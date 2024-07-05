package com.travel.project.dto.request;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Setter
@Getter @ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
public class UpdateProfileDto {

    private String account;
    private String name;
//    private String email;
    private String nickname;

    private String mbti; // mbti
    private String oneLiner; // 소개글+

    @Setter
    private MultipartFile profileImage; // 프로필 사진
//    private String profileImage; // 프로필 사진





}
