package com.travel.project.service;

import com.travel.project.entity.BoardImage;
import com.travel.project.mapper.ImageMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccBoardImageService {

    // 이미지 등록
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

    // 이미지 삭제?

    // 이미지 수정?

}
