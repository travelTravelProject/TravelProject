package com.travel.project.service;

import com.travel.project.common.Search;
import com.travel.project.dto.request.AccBoardWriteDto;
import com.travel.project.dto.response.AccBoardDetailDto;
import com.travel.project.dto.response.AccBoardListDto;
import com.travel.project.dto.response.AccBoardModifyDto;
import com.travel.project.entity.AccBoard;
import com.travel.project.entity.Bookmark;
import com.travel.project.login.LoginUtil;
import com.travel.project.mapper.AccBoardMapper;
import com.travel.project.mapper.BookmarkMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AccBoardService {

    private final AccBoardMapper accBoardMapper;
    private final BookmarkMapper bookmarkMapper;

    // 목록 조회 요청 중간처리
    public List<AccBoardListDto> findList(Search page) {

        List<AccBoard> accBoardList = accBoardMapper.findAll(page);
        return accBoardList.stream()
                .map(ab -> new AccBoardListDto(ab))
                .collect(Collectors.toList());
    }

    // 등록 요청 중간처리
    public boolean insert(AccBoardWriteDto dto, HttpSession session) {
        AccBoard ab = dto.toEntity();
        // 계정명을 엔터티에 추가 - 세션에서 계정명 가져오기
        ab.setAccount(LoginUtil.getLoggedInUserAccount(session));
        ab.setCategoryId(1);
        ab.setWriter(LoginUtil.getLoggedInUserNickname(session));
        return accBoardMapper.save(ab);
    }

    // 삭제 요청 중간처리
    public boolean remove(long boardId) {
        return accBoardMapper.delete(boardId);
    }

    // 상세 조회 요청 중간처리
    public AccBoardDetailDto detail(long boardId) {
        AccBoard ab = accBoardMapper.findOne(boardId);
        if(ab != null) {
            // 조회수 증가
            accBoardMapper.upViewCount(boardId);
            // 조회수 증가 후 게시글을 다시 조회하여 업데이트된 조회수를 가져옵니다.
            ab = accBoardMapper.findOne(boardId);
        }
        return new AccBoardDetailDto(ab);
    }

    // 게시글 수정 화면 요청 처리
    public AccBoardModifyDto getModifyForm(long boardId) {
        // 수정할 게시글 조회
        AccBoard ab = accBoardMapper.findOne(boardId);
//        if (ab == null) {
//            throw new IllegalArgumentException("Invalid board ID: " + boardId);
//        }
        AccBoardModifyDto dto = new AccBoardModifyDto();
        dto.setFromEntity(ab); // 필드를 개별적으로 설정
        return dto;
    }

    // 게시글 수정 요청 처리
    public boolean modify(AccBoardModifyDto dto) {
        AccBoard ab = dto.toEntity();
        return accBoardMapper.modify(ab);
    }

    public int getCount(Search search) {
        return accBoardMapper.count(search);
    }

    public boolean checkBookmark(HttpSession session, long boardId) {
        String account = LoginUtil.getLoggedInUserAccount(session);
        Bookmark bm = bookmarkMapper.existsBookmark(account, boardId);
        boolean flag = (bm != null);
        return flag;
    }
}
