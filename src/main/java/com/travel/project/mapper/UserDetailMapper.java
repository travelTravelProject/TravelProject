package com.travel.project.mapper;

import com.travel.project.entity.UserDetail;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDetailMapper {

    // 상세정보 조회
    UserDetail findUserDetailByAccount(String account);

}
