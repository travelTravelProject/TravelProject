package com.travel.project.dto.request;

import com.travel.project.entity.AccBoard;
import lombok.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Setter @Getter @ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class AccBoardWriteDto {

    // 필드명 html form 태그 입력태그 name 속성과 일치시킬 것
    private String account;
    private int categoryId;
    private String title;
    private String content;
    private String writer;
    private String location;
    private String startDate;
    private String endDate;


    public AccBoard toEntity() {
        AccBoard ab = new AccBoard();
        ab.setAccount(this.account);
        ab.setCategoryId(this.categoryId);
        ab.setTitle(this.title);
        ab.setContent(this.content);
        ab.setWriter(this.writer);
        ab.setLocation(this.location);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        ab.setStartDate(LocalDate.parse(this.startDate, formatter).atStartOfDay());
        ab.setEndDate(LocalDate.parse(this.endDate, formatter).atStartOfDay());

        return ab;
    }
}
