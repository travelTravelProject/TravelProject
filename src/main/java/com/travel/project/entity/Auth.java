package com.travel.project.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public enum Auth {

    common, //tbl_user 테이블에서는 소문자 common 으로 되어있어서 COMMON 을 common 으로 바꿈
    admin


}
