package com.travel.withtrip.service;

import com.travel.withtrip.dto.request.FeedFindAllDto;
import com.travel.withtrip.dto.request.FeedFindOneDto;
import com.travel.withtrip.dto.request.FeedModifyDto;
import com.travel.withtrip.dto.request.FeedPostDto;
import com.travel.withtrip.dto.response.FeedDetailResponseDto;
import com.travel.withtrip.dto.response.FeedListResponseDto;
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
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class FeedService {

    private final FeedMapper feedMapper;
    private final ImageService imageService;

    public List<FeedListResponseDto> findAll() {
        List<FeedFindAllDto> feedList = feedMapper.findAllFeeds();
        if(feedList.isEmpty()) {
            return null;
        }
        // 각 피드마다 이미지 리스트를 추가
        // 각 피드마다 댓글 수, 좋아요 수, 북마크 수 추가

        // Feed 전체조회 응답객체에 map()으로 담기
        // feedImageList imageService 조회 결과를 setter로 추가
        return feedList.stream()
                .map(f -> {
                    FeedListResponseDto responseDto = new FeedListResponseDto(f);
                    responseDto.setFeedImageList(imageService.getFeedImages(f.getBoardId()));
                    return responseDto;
                })
                .collect(Collectors.toList());
    }

    public FeedDetailResponseDto findById(long boardId) {
        // DB에서 FeedFindOneDto 받아와서 response dto에 담기
        FeedFindOneDto feedById = feedMapper.findFeedById(boardId);

        // 피드 상세조회 응답 DTO를 생성
        FeedDetailResponseDto responseDto = new FeedDetailResponseDto(feedById);

        // 이미지 모두 조회하여 추가
        responseDto
                .setFeedImageList(imageService.getFeedImages(feedById.getBoardId()));
        return responseDto;

    }
    // 새로운 피드(post DTO)를 tbl_board insert 시도
    // 성공하면 새로운 boardId로 tbl_board_image 이미지 등록
    // 피드 & 이미지 insert 성공하면 새로운 boardId 리턴
    // 피드 & 이미지 insert 실패하면 -1 리턴
    public long insertFeed(FeedPostDto newFeed, HttpSession session) {

        // 로그인 유틸에서 세션으로 계정 정보 가져오기

        Board board = Board.builder()
                .content(newFeed.getContent())
                .account("admin")
                .build();

        // 피드 insert 성공 시 등록된 피드 boardId를 리턴
        long newBoardId = feedMapper.saveFeed(new Board());

        // tbl_board insert 성공하면 tbl_board_image 추가
        if (newBoardId > 0) {

            List<MultipartFile> files = newFeed.getFiles();

            // file 존재하면 DB에 insert
            if(!files.isEmpty()) {
                // 이미지 추가 성공하면 등록한 BoardId를 리턴
                // forEach 중 이미지 추가 실패하면 수정으로 유도
                files.forEach((file) -> {
                        BoardImage b = BoardImage.builder()
                                .boardId(newBoardId)
                                .imagePath(FileUtil.uploadFile(file))
                                .order(files.indexOf(file)) // 첫번째 이미지 인덱스 0
                                .build();
                        // DB insert 성공 ? 등록한 이미지 id : -1
                        long imageId = imageService.addImage(b);
                        if (imageId < 0) {
                            log.debug("file 추가 실패: ", imageId < 0 ? file : "");
                            return;
                        }
                });
            }
            return newBoardId; // 피드 등록 완료 (첨부파일 없음)
        }
        return -1; // 피드 등록 실패
    }

    public boolean updateFeed(FeedModifyDto dto) {
        Board newBoard = dto.toBoardEntity();
        List<MultipartFile> files = dto.getFiles();
        AtomicInteger count = new AtomicInteger(0);
        files.stream().forEach(file -> {
            BoardImage imageEntity
                    = dto.toImageEntity(
                        dto.getBoardId()
                        ,file
                        , count.getAndIncrement());
            imageService.addImage(imageEntity);
        });

        return feedMapper.modifyFeed(newBoard);
    }

    public boolean deleteFeed(long boardId) {
        return feedMapper.deleteFeed(boardId);
    }
    public boolean addViewCount(long boardId) {
        return feedMapper.upViewCount(boardId);
    }

}
