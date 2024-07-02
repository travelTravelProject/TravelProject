package com.travel.project.mapper;

import com.travel.project.dto.request.UpdateProfileDto;
import com.travel.project.entity.User;
import com.travel.project.entity.UserDetail;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDetailMapper {

    void save(UserDetail userDetail);

    // 상세정보 조회
    UserDetail findUserDetailByAccount(String account);

    // 상세정보 업데이트
    void updateUserDetail(UserDetail userDetail);

    // 상세정보 추가
    void insertUserDetail(UserDetail userDetail);

    // 회원정보 전체 조회
    UserDetail findAll(String account);
}
