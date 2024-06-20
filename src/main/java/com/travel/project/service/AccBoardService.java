package com.travel.project.service;

import com.travel.project.dto.request.AccBoardWriteDto;
import com.travel.project.dto.response.AccBoardDetailDto;
import com.travel.project.dto.response.AccBoardListDto;
import com.travel.project.dto.response.AccBoardModifyDto;
import com.travel.project.entity.AccBoard;
import com.travel.project.mapper.AccBoardMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AccBoardService {

    private final AccBoardMapper accBoardMapper;

    // 목록 조회 요청 중간처리
    public List<AccBoardListDto> findList() {

        List<AccBoard> accBoardList = accBoardMapper.findAll();
        return accBoardList.stream()
                .map(ab -> new AccBoardListDto(ab))
                .collect(Collectors.toList());
    }

    // 등록 요청 중간처리
    public boolean insert(AccBoardWriteDto dto) {

        AccBoard ab = dto.toEntity();
        return accBoardMapper.save(ab);
    }

    // 삭제 요청 중간처리
    public boolean remove(int boardId) {
        return accBoardMapper.delete(boardId);
    }

    // 상세 조회 요청 중간처리
    public AccBoardDetailDto detail(int boardId) {
        AccBoard ab = accBoardMapper.findOne(boardId);
        if(ab != null) accBoardMapper.upViewCount(boardId);
        return new AccBoardDetailDto(ab);
    }

    // 게시글 수정 화면 요청 처리
    public AccBoardModifyDto getModifyForm(int boardId) {
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


}
