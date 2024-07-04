package com.travel.project.common;

import lombok.*;

@Getter @Setter @ToString
@EqualsAndHashCode
@AllArgsConstructor
public class Search extends Page {

    // 검색어, 검색조건
    private String keyword, type;

    public Search() {
        this.keyword = "";
    }

    public Search(Page page) {
        super(page.getPageNo(), page.getAmount());
        this.keyword = "";
        this.type = "cw";
    }

    public Search(Page page, String keyword, String type) {
        super(page.getPageNo(), page.getAmount());
        this.keyword = keyword;
        this.type = type;
    }
}
