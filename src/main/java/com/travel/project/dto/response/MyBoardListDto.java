package com.travel.project.dto.response;

import com.travel.project.common.PageMaker;
import lombok.*;

import java.util.List;

@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MyBoardListDto {

    private LoginUserInfoDto loginUser;
    private PageMaker pageInfo;
    private List<AccBoardListDto> myboards;

}
