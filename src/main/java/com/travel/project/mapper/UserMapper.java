package com.travel.project.mapper;

import com.travel.project.dto.request.AutoLoginDto;
import com.travel.project.entity.User;
import com.travel.project.entity.UserDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {

    // 회원가입
    boolean save(User user);

    // 회원정보 개별 조회
    User findOne(String account);

    // 중복 확인(아이디, 이메일)
    /**
     * 중복 확인(아이디, 이메일)
     * @param type - 중복 검사 타입(account OR email)
     * @param keyword - 중복 검사할 실제 값
     * @return - 중복이면 true, 아니면 false
     */
    boolean existById(@Param("type") String type,
                      @Param("keyword") String keyword);

    //자동 로그인 쿠키값, 만료시간 업데이트
    void updateAutoLogin(AutoLoginDto dto); // AutoLoginDto 로 정의 함

    // 세션 아이디로 회원정보 조회
//     User findBySessionId(String sessionId);

    // 세션아이디로 회원정보 조회
    User findMemberBySessionId(String sessionId);
  
    void updateUser(User user);




    // 이름과 이메일로 사용자 아이디 조회
    User findIdByNameAndEmail(@Param("name") String name,
                              @Param("email") String email);


    User findByAccountAndNameAndEmail(@Param("account") String account, @Param("name") String name, @Param("email") String email);

    User findByAccount(@Param("account") String account);

    boolean updatePassword(User user);

}
