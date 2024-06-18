package com.travel.service;

import com.travel.dto.request.AccBoardWriteDto;
import com.travel.dto.response.AccBoardDetailDto;
import com.travel.dto.response.AccBoardListDto;
import com.travel.entity.AccBoard;
import com.travel.mapper.AccBoardMapper;
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
}
