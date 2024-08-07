package com.travel.project.dto.response;

import com.travel.project.common.PageMaker;
import lombok.*;

import java.util.List;

@Getter @ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
// ReplyListDto의 형태
/*
    {
        "replies": [
            {}, {}, {}
        ]
    }
 */
public class ReplyListDto {

    // ReplyDetailDto의 형태
    // ReplyDetailDto를 필드로 만들면서 배열의 이름이 생김 (replies)
    /*
        [
            {}, {}, {}
        ]
     */
    @Setter
    private LoginUserInfoDto loginUser;

    private PageMaker pageInfo;
    private List<ReplyResponseDetailDto> replies;
}
