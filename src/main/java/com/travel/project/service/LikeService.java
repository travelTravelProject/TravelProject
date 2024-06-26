package com.travel.project.service;

import com.travel.project.dto.response.LikeDto;
import com.travel.project.entity.Like;
import com.travel.project.mapper.LikeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeMapper likeMapper;

    private Like handleLike(String account, int boardId) {

        // 현재 게시물에 특정 사용자가 리액션을 했는지 확인
        Like existingLike = likeMapper.existsLike(account, boardId);

        if (existingLike != null) { // 이미 like가 존재하는 경우
            likeMapper.deleteLike(account, boardId);
//            return null;
        } else { // 기존의 like 없는 경우
            Like newLike = Like.builder()
                    .account(account)
                    .boardId(boardId)
                    .build();
            likeMapper.insertLike(newLike);
//            return newLike;
        }

        return likeMapper.existsLike(account, boardId);
    }

    // 좋아요 중간처리
    public LikeDto like(String account, int boardId) {

//        if(likeMapper.existsLike(account, boardId) != null) {}

        return LikeDto.builder()
                .likeCount(likeMapper.countLikes(boardId))
                .build();
    }

//    public int countLikes(int boardId) {
//        return likeMapper.countLikes(boardId);
//    }

}
