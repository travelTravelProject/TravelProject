package com.travel.project.service;

import com.travel.project.dto.response.BookmarkDto;
import com.travel.project.entity.Bookmark;
import com.travel.project.login.LoginUtil;
import com.travel.project.mapper.BookmarkMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookmarkService {

    private final BookmarkMapper bookmarkMapper;

    public Bookmark handleBookmark(String account, long boardId) {
        Bookmark existingBookmark = bookmarkMapper.existsBookmark(account, boardId);

        // 현재 게시물에 특정 사용자가 북마크 했는지 확인
        if (existingBookmark != null) { // 이미 북마크가 존재하는 경우
            bookmarkMapper.deleteBookmark(account, boardId);
        } else { // 기존에 북마크가 없는 경우
            Bookmark newBookmark = Bookmark.builder()
                    .account(account)
                    .boardId(boardId)
                    .build();
            bookmarkMapper.insertBookmark(newBookmark);
        }
        return bookmarkMapper.existsBookmark(account, boardId);
    }

    // 북마크 중간처리
    public BookmarkDto bookmark(String account, long boardId, String boardAccount) {

        if (LoginUtil.isMine(boardAccount, account)) {
            return null;
        }
        // DB처리
        Bookmark bookmark = handleBookmark(account, boardId);

        return BookmarkDto.builder()
                .bookmarkCount(bookmarkMapper.countBookmarks(boardId))
                .userBookmark(bookmark != null)
                .build();
    }

    public int countbookmarks(long boardId) {
        return bookmarkMapper.countBookmarks(boardId);
    }

    public boolean isBookmarkByUser(String account, long boardId) {
        return bookmarkMapper.existsBookmark(account, boardId) != null;
    }




}

