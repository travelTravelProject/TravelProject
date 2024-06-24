package com.travel.project.dto.request;

import lombok.*;

import java.time.LocalDate;

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
//    private LocalDate birthday;

}
