package com.travel.project.service;

import com.travel.project.dto.request.FeedFindOneDto;
import com.travel.project.dto.response.LikeDto;
import com.travel.project.entity.Like;
import com.travel.project.login.LoginUtil;
import com.travel.project.mapper.FeedMapper;
import com.travel.project.mapper.LikeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeMapper likeMapper;

    private Like handleLike(String account, int boardId) {

        // 현재 게시물에 특정 사용자가 좋아요를 했는지 확인
        Like existingLike = likeMapper.existsLike(account, boardId);

        if (existingLike != null) { // 이미 좋아요가 존재하는 경우
            likeMapper.deleteLike(account, boardId);

        } else { // 기존의 like 없는 경우
            Like newLike = Like.builder()
                    .account(account)
                    .boardId(boardId)
                    .build();
            likeMapper.insertLike(newLike);
        }

        return likeMapper.existsLike(account, boardId);
    }

    // 좋아요 중간처리
    public LikeDto like(String account, int boardId, String boardAccount) {

        if (LoginUtil.isMine(boardAccount, account)) {
            return null;
        }
        // DB 좋아요 있으면 삭제, 없으면 추가 처리
        Like like = handleLike(account, boardId);

        return LikeDto.builder()
                .likeCount(likeMapper.countLikes(boardId))
                .userLike(like != null)
                .build();
    }

    public int countLikes(int boardId) {
        return likeMapper.countLikes(boardId);
    }

    public boolean isLikedByUser(String account, int boardId) {
        return likeMapper.existsLike(account, boardId) != null;
    }


}
