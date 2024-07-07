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
import com.travel.project.util.FileUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AccBoardService {

    private final AccBoardMapper accBoardMapper;
    private final BookmarkMapper bookmarkMapper;
    private final AccBoardImageService accBoardImageService;


    // 목록 조회 요청 중간처리
    public List<AccBoardListDto> findList(Search page) {

        List<AccBoard> accBoardList = accBoardMapper.findAll(page);
        return accBoardList.stream()
                .map(ab -> new AccBoardListDto(ab))
                .collect(Collectors.toList());
    }

    // 등록 요청 중간처리
    @Transactional
    public boolean insert(AccBoardWriteDto dto, HttpSession session) {
        String imagePath = null;

        // 이미지 업로드가 있는 경우
        if (dto.getPostImage() != null && !dto.getPostImage().isEmpty()) {
            imagePath = FileUtil.uploadFile(dto.getPostImage());
        }

        // AccBoard 엔티티 생성
        AccBoard ab = dto.toEntity();
        ab.setAccount(LoginUtil.getLoggedInUserAccount(session));
        ab.setCategoryId(1);
        ab.setWriter(LoginUtil.getLoggedInUserNickname(session));

        // 게시글 저장
        boolean boardSaved = accBoardMapper.save(ab);

        // 게시글이 성공적으로 저장되면 이미지도 저장
        if (boardSaved && imagePath != null) {
            accBoardImageService.saveBoardImage(ab.getBoardId(), imagePath); // 이미지 저장 서비스 호출
        }

        return boardSaved;
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
        AccBoardModifyDto dto = new AccBoardModifyDto();
        dto.setFromEntity(ab); // 필드를 개별적으로 설정
        return dto;
    }

    // 게시글 수정 요청 처리
    @Transactional
    public boolean modify(AccBoardModifyDto dto, boolean imageDeleted) {
        AccBoard ab = dto.toEntity();
        String newImagePath = null;

        // 이미지 업로드가 있는 경우
        if (dto.getPostImage() != null && !dto.getPostImage().isEmpty()) {
            newImagePath = FileUtil.uploadFile(dto.getPostImage());
        }

        boolean boardModified = accBoardMapper.modify(ab);

        // 게시글이 성공적으로 수정되면 이미지도 수정
        if (boardModified) {
            if (imageDeleted) {
                accBoardImageService.deleteBoardImage(ab.getBoardId());
            }
            if (newImagePath != null) {
                accBoardImageService.updateBoardImage(ab.getBoardId(), newImagePath);
            }
        }

        return boardModified;
    }

    // 동행게시판 전체 글 수
    public int getCount(Search search) {
        return accBoardMapper.count(search);
    }

    // board_tbl 전체 글 수
    public long getTotalCount() {
        return accBoardMapper.totalCount();
    }

    public boolean checkBookmark(HttpSession session, long boardId) {
        String account = LoginUtil.getLoggedInUserAccount(session);
        Bookmark bm = bookmarkMapper.existsBookmark(account, boardId);
        boolean flag = (bm != null);
        return flag;
    }
}
