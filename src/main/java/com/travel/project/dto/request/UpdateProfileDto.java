package com.travel.project.dto.request;

import com.travel.project.dto.response.LoginUserInfoDto;
import com.travel.project.entity.User;
import lombok.*;

import java.time.LocalDate;
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


    public UpdateProfileDto(User user){
        this.account = user.getAccount();
        this.name = user.getName();
        this.nickname = user.getNickname();
        this.email = user.getEmail();


    }

}
