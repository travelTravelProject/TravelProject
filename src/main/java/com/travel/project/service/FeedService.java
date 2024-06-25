package com.travel.project.service;

import com.travel.project.common.Page;
import com.travel.project.common.PageMaker;
import com.travel.project.common.Search;
import com.travel.project.dto.request.FeedFindAllDto;
import com.travel.project.dto.request.FeedFindOneDto;
import com.travel.project.dto.request.FeedModifyDto;
import com.travel.project.dto.request.FeedPostDto;
import com.travel.project.dto.response.FeedDetailResponseDto;
import com.travel.project.dto.response.FeedListDto;
import com.travel.project.entity.Board;
import com.travel.project.entity.BoardImage;
import com.travel.project.mapper.FeedMapper;
import com.travel.project.util.FileUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class FeedService {

    private final FeedMapper feedMapper;
    private final ImageService imageService;

    public FeedListDto findAll(Search search) {
        List<FeedFindAllDto> feedList = feedMapper.findAllFeeds(search);
        log.debug("서비스 findAll: {}", feedList);
        if(feedList.isEmpty()) {
            return null;
        }
        // 각 피드마다 이미지 리스트를 추가
        // 각 피드마다 댓글 수, 좋아요 수, 북마크 수 추가

        // Feed 디테일 응답객체에 이미지 담기
        // imageService 조회 결과를 setter로 추가
        List<FeedDetailResponseDto> detailDto = feedList.stream()
                .map(f -> {
                    FeedDetailResponseDto responseDto = f.toDetailResponseDto();
                    log.debug("피드서비스 f: {}", f);
                    log.debug("f 글번호: {}", f.getBoardId());
                    List<BoardImage> feedImages = imageService.findFeedImages(f.getBoardId());
                    if(feedImages != null) {
                        responseDto.setFeedImageList(feedImages);
                    }
                    return responseDto;
                })
                .collect(Collectors.toList());

        Page page = new Page(search.getPageNo(), search.getAmount());

        return FeedListDto.builder()
                .pageInfo(new PageMaker(page, getCount(search)))
                .feeds(detailDto)
                .build();

    }

    public FeedDetailResponseDto findById(long boardId) {
        log.debug("글번호: {}", boardId);

        // DB에서 FeedFindOneDto 받아와서 response dto에 담기
        FeedFindOneDto feedById = feedMapper.findFeedById(boardId);
        log.debug("개별조회: {}", feedById);

        // 피드 상세조회 응답 DTO를 생성
        FeedDetailResponseDto responseDto = feedById.toDetailDto();

        // 이미지 모두 조회하여 추가
        List<BoardImage> feedImages = imageService.findFeedImages(feedById.getBoardId());
        if(feedImages != null && !feedImages.isEmpty()) {
            responseDto.setFeedImageList(feedImages);
        }
        // 조회된 이미지 없으면 바로 리턴
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
                .account(newFeed.getAccount())
                .build();

        // 피드 insert 성공 시 등록된 피드 boardId를 리턴
        long newBoardId = feedMapper.saveFeed(board);

        // tbl_board insert 성공하면 tbl_board_image 추가
        if (newBoardId > 0) {

            List<MultipartFile> files = newFeed.getImages();

            // file 존재하면 DB에 insert
            if(!files.isEmpty()) {
                // 이미지 추가 성공하면 등록한 BoardId를 리턴
                // forEach 중 이미지 추가 실패하면 수정으로 유도
                for (int i = 0; i < files.size(); i++) {
                    MultipartFile file = files.get(i);
                    BoardImage b = BoardImage.builder()
                            .boardId(newBoardId)
                            .imagePath(FileUtil.uploadFile(file))
                            .imageOrder(i) // 인덱스 설정
                            .build();
                    // DB insert 성공 ? 등록한 이미지 id : -1
                    long imageId = imageService.addImage(b);
                    if (imageId < 0) {
                        log.debug("file 추가 실패: ", file);
                        return -1; // 이미지 추가 실패 시 -1 리턴
                    }
                }
            }
            return newBoardId; // 피드 등록 완료 (첨부파일 없음)
        }
        return -1; // 피드 등록 실패
    }

    // 피드 수정
    public boolean updateFeed(FeedModifyDto dto) {
        // tbl_board 수정
        Board newBoard = dto.toBoardEntity();

        // tbl_board_image 수정 (삭제 후 추가 )
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

    // 피드 삭제
    public FeedListDto deleteFeed(long boardId) {

        boolean flag = feedMapper.deleteFeed(boardId);

        return flag ? findAll(new Search(new Page(1, 5))) : null;
    }
    public boolean addViewCount(long boardId) {
        return feedMapper.upViewCount(boardId);
    }

    // 총 피드 개수
    public int getCount(Search search) {
        Integer result = feedMapper.countFeeds(search);
        return result != null ? result : 0;
    }
}
