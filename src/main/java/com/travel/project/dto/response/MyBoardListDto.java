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
//    private PageMaker pageInfo;
    private List<AccBoardListDto> boards;

    private long boardId; // 게시글 번호
    private String shortTitle; // 줄임 처리된 제목
    private String shortContent; // 줄임 처리된 글 내용
    private String date; // 포맷팅된 날짜 문자열
    private int view; // 조회 수
    private String account; // 작성자 계정
    private String startDate; // 동행 시작일
    private String endDate; // 동행 종료일
    private String writer; // 닉네임
    private String gender; // 작성자 성별


    public MyBoardListDto(LoginUserInfoDto loginUser, List<AccBoardListDto> boards) {
//        this.loginUser = loginUser;
        this.boards = boards;
    }

    public MyBoardListDto(List<AccBoardListDto> boardList) {
    }


}
