package com.travel.project.dto.request;

import com.travel.project.entity.User;
import com.travel.project.entity.UserDetail;
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
    private String email;
    private String nickname;

    private String mbti; // mbti
    private String oneLiner; // 소개글
    @Setter
    private MultipartFile profileImage; // 프로필 사진
    private int rating; // 평점

    private String profileImagePath;


    public UpdateProfileDto(User user){
        this.account = user.getAccount();
        this.name = user.getName();
        this.nickname = user.getNickname();
        this.email = user.getEmail();
    }

    public UpdateProfileDto(UserDetail userDetail){
        this.oneLiner = userDetail.getOneLiner();
        this.mbti = userDetail.getMbti();
        this.rating = userDetail.getRating();
        this.profileImagePath = userDetail.getProfileImage();
    }


}
