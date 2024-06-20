package com.travel.project.dto.response;

import com.travel.project.common.PageMaker;
import com.travel.project.entity.BoardImage;
import lombok.*;

import java.util.List;

@Getter @ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FeedListDto {

//    private LoginUserInfoDto loginUser;
    private PageMaker pageInfo;
    private List<FeedDetailResponseDto> feeds;

}
