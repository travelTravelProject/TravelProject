package com.travel.project.common;

import lombok.*;

@Getter @ToString
@EqualsAndHashCode
@AllArgsConstructor
public class Page {

    private int pageNo; // 요청한 페이지 번호
    private int amount; // 페이지당 게시물 개수

    public Page() {
        this.pageNo = 1;
        this.amount = 6;
    }

    public void setPageNo(int pageNo) {
        if (pageNo < 1 || pageNo > Integer.MAX_VALUE) {
            this.pageNo = 1;
            return;
        }
        this.pageNo = pageNo;
    }

    public void setAmount(int amount) {
        if (amount < 6 || amount > 60) {
            this.amount = 6;
            return;
        }
        this.amount = amount;
    }

    public int getPageStart() {
        return (this.pageNo - 1) * this.amount;
    }

}
