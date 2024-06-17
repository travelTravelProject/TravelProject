package com.travel.withtrip.service;

import com.travel.withtrip.dto.request.FeedFindAllDto;
import com.travel.withtrip.dto.request.FeedModifyDto;
import com.travel.withtrip.dto.request.FeedPostDto;
import com.travel.withtrip.dto.response.FeedDetailResponseDto;
import com.travel.withtrip.entity.Board;
import com.travel.withtrip.entity.BoardImage;
import com.travel.withtrip.mapper.FeedMapper;
import com.travel.withtrip.util.FileUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class FeedService {

    private final FeedMapper feedMapper;
    private final ImageService imageService;

    public List<FeedFindAllDto> findAll() {
        List<FeedFindAllDto> feedList = feedMapper.findAllFeeds();

        // 각 피드마다 이미지 리스트를 추가
        // 각 피드마다 댓글 수, 좋아요 수, 북마크 수 추가

        return feedList;
    }

    public FeedDetailResponseDto findById(long boardId) {
        // board 받아와서 response dto에 담아주기...
        // mapper.xml 타입도 주의
        Board feedById = feedMapper.findFeedById(boardId);


        return new FeedDetailResponseDto(feedById);

    }
    // 피드 등록 성공하면 새로운 boardId 리턴
    // 피드 등록 실패하면 -1 리턴
    public long insertFeed(FeedPostDto newFeed, HttpSession session) {

        Board board = Board.builder()
                .content(newFeed.getContent())
                .account("admin") // 로그인 유틸에서 세션으로 계정 정보 가져오기
                .build();

        long newBoardId = feedMapper.saveFeed(new Board());

        // 넘버포맷익셉션 예외 처리 필요
        if (newBoardId > 0) {

            // tbl_board_image에 이미지 순서대로 추가
            List<MultipartFile> files = newFeed.getFiles();
            if(!files.isEmpty()) {
                // file 존재하면 imageService로 추가
                files
                    .forEach((file) -> {
                        BoardImage b = BoardImage.builder()
                                .boardId(newBoardId)
                                .imagePath(FileUtil.uploadFile(file))
                                .order(files.indexOf(file) + 1)
                                .build();
                        long fileFlag = imageService.addImage(b);
                        if (fileFlag < 0) {
                            log.debug("file 추가 실패: ", fileFlag < 0 ? file : "");
                            return;
                        }
                    });
            }
        }

        return -1;
    }

    public boolean updateFeed(FeedModifyDto dto) {
        Board newBoard = dto.toEntity();
        return feedMapper.modifyFeed(newBoard);
    }

    public boolean deleteFeed(long boardId) {
        return feedMapper.deleteFeed(boardId);
    }
    public boolean addViewCount(long boardId) {
        return feedMapper.upViewCount(boardId);
    }

}
