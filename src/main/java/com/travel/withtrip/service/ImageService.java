package com.travel.withtrip.service;

import com.travel.withtrip.entity.BoardImage;
import com.travel.withtrip.mapper.ImageMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;


@Service
@RequiredArgsConstructor
@Slf4j
public class ImageService {

    private final ImageMapper imageMapper;

    // 하나의 피드에 첨부된 이미지 전체 조회(최대 10개)
    public List<BoardImage> getFeedImages(long boardId) {
        return imageMapper.findAllImages(boardId);
    }

    // 이미지 수정 시 순서(order 컬럼) 어떻게 할지?
    // 1 2 3 4 중 2를 삭제하고 다른 이미지 추가하면 1 3 4 5 or 1 5 3 4 ?

    // BoardImage 로 이미지 추가
    // 추가 성공하면 새로 추가된 이미지 id 리턴
    // 추가 실패하면 -1 리턴
    public long addImage(BoardImage image) {

        long b = imageMapper.insertImage(image);
        // 넘버 포맷 익셉션 예외처리 필요
        if(b > 0) { return b; }
        else { return -1; }
    }

    // 이미지 id로 삭제
    // 삭제 성공하면 삭제한 이미지 id 리턴
    // 삭제 실패하면 -1 리턴
    public long deleteImage(long imageId) {
        boolean flag = imageMapper.deleteImage(imageId);
        if (!flag) { return -1; }
        return imageId;
    }

    // 한 게시글의 이미지 모두 교체 (삭제 후 추가)
    public boolean changeImages(long boardId, List<BoardImage> imageList) {

        AtomicBoolean flag = new AtomicBoolean(false);
        // imageId 리스트에 저장?
        if (deleteImages(boardId)) {
            imageList.forEach(image -> {
                long imageId = imageMapper.insertImage(image);
                if (imageId < 0) {
                    flag.set(false);
                    return;
                }
            });
            // image 등록을 한번이라도 실패하면 false 리턴
            // image 등록 모두 성공하면 true 리턴
            return flag.get() ? true : false;
        }
        return false;
    }
    // 한 게시글의 이미지 모두 삭제
    public boolean deleteImages(long boardId) {
        return imageMapper.deleteImagesByBoardId(boardId);
    }


}
