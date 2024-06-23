package com.travel.project.common;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

// 페이지 화면 렌더링에 필요한 정보 계산
@Getter @ToString
@EqualsAndHashCode
public class PageMaker {

    // 한 화면에 배치할 페이지 개수
    private static final int PAGE_COUNT = 10;

    // 현재 페이지 정보
    private Page pageInfo;

    // 이전, 다음버튼 활성화 여부
    private boolean prev, next;

    // 총 게시물 수
    private int totalCount;

    public PageMaker(Page page, int totalCount) {
        this.pageInfo = page;
        this.totalCount = totalCount;
        makePageInfo();
    }

    // 마지막 페이지
    private int finalPage;

    // 페이지 시작,끝번호
    private int begin, end;

    // 페이지 생성에 필요한 데이터를 만드는 알고리즘
    private void makePageInfo() {

        // 1. end
        this.end = (int) (Math.ceil((double) pageInfo.getPageNo() / PAGE_COUNT) * PAGE_COUNT);

        // 2. begin
        this.begin = this.end - PAGE_COUNT + 1;

        // 3. 마지막 페이지 구간 end값 보정
        this.finalPage = (int) Math.ceil((double) totalCount / pageInfo.getAmount());

        // 마지막 구간에서 end값을 finalPage로 보정
        if (finalPage < this.end) {
            this.end = finalPage;
        }

        // 4. 이전버튼
        this.prev = begin != 1;

        // 5. 다음버튼
        this.next = this.end < finalPage;

    }

}
