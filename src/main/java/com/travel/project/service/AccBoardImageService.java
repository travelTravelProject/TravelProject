package com.travel.project.service;

import com.travel.project.entity.BoardImage;
import com.travel.project.mapper.ImageMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccBoardImageService {

    private final ImageMapper imageMapper;

    // 이미지 등록
    public void saveBoardImage(long boardId, String imagePath) {
        if (imagePath != null) {
            BoardImage boardImage = BoardImage.builder()
                    .boardId(boardId)
                    .imagePath(imagePath)
                    .imageOrder(1)
                    .build();
            imageMapper.insertImage(boardImage);
        }
    }

    // 이미지 삭제
    public void deleteBoardImage(long boardId) {
        imageMapper.deleteImagesByBoardId(boardId);
    }

    // 이미지 수정
    public void updateBoardImage(long boardId, String newImagePath) {
        deleteBoardImage(boardId); // 기존 이미지를 삭제하고
        saveBoardImage(boardId, newImagePath); // 새 이미지로 등록
    }

    // 이미지 경로 가져오기
    public String getImagePath(long boardId) {
        BoardImage image = imageMapper.findImageByBoardId(boardId);
        if (image != null) {
            return image.getImagePath(); // 이미지 경로 반환
        }
        return null;
    }

}
