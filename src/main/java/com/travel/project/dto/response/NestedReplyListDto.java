package com.travel.project.dto.response;

import com.travel.project.common.PageMaker;
import lombok.*;

import java.util.List;

@Getter @ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class NestedReplyListDto {

    @Setter
    private LoginUserInfoDto loginUser;

    private List<NestedReplyResponseDetailDto> nestedReplies;
}
