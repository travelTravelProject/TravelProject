package com.travel.project.dto.response;

import com.travel.project.entity.User;
import lombok.*;

@Getter
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

    //생성자
    public LoginUserInfoDto(User user){
        this.account = user.getAccount();
        this.name = user.getName();
        this.nickname = user.getNickname();
        this.email = user.getEmail();
        this.auth = user.getAuth().toString();

    }
}
