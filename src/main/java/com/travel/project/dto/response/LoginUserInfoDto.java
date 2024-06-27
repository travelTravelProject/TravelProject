package com.travel.project.dto.response;

//import com.travel.project.dto.request.UpdateProfileDto;
import com.travel.project.entity.Gender;
import com.travel.project.entity.User;
import com.travel.project.entity.UserDetail;
import lombok.*;

import java.time.LocalDate;

@Getter @Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
//클라이언트한테 보낼 dto (민감한 정보 빼고 보내는거임)
public class LoginUserInfoDto {

    private String account; // 계정
    private String name; // 계정
    private String nickname; // 닉네임
    private String email; // 이메일
    private String auth; // 권한

    // 마이페이지 내 정보 조회를 위해 추가
    private LocalDate birthday; // 생년월일
    private Gender gender; // 성별

    private String mbti; // mbti
    private String oneLiner; // 소개글
    @Setter
    private String profileImage; // 프로필 사진
    private int rating; // 평점


    //생성자
    public LoginUserInfoDto(User user){
        this.account = user.getAccount();
        this.name = user.getName();
        this.nickname = user.getNickname();
        this.email = user.getEmail();
        this.auth = user.getAuth().toString();

        this.birthday = user.getBirthday();
        this.gender = user.getGender();

    }

    public LoginUserInfoDto(UserDetail userDetail) {
        this.mbti = userDetail.getMbti();
        this.oneLiner = userDetail.getOneLiner();
        this.rating = userDetail.getRating();
        this.profileImage = userDetail.getProfileImage();
    }

//    // 필요한 필드만 초기화하는 생성자
//    public LoginUserInfoDto(String account, String name, String nickname, String email) {
//        this.account = account;
//        this.name = name;
//        this.nickname = nickname;
//        this.email = email;
//    }
}
