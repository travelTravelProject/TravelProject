package com.travel.project.dto.response;

import com.travel.project.entity.AccBoard;
import lombok.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Setter @Getter @ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
public class AccBoardModifyDto {

    private int boardId;
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
        ab.setBoardId(this.boardId);
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
