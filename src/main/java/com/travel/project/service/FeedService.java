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
import org.springframework.transaction.annotation.Transactional;
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
        if (feedList.isEmpty()) {
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
                    if (feedImages != null) {
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
        if (feedImages != null && !feedImages.isEmpty()) {
            responseDto.setFeedImageList(feedImages);
        }
        // 조회된 이미지 없으면 바로 리턴
        return responseDto;

    }

    // 새로운 피드(post DTO)를 tbl_board insert 시도
    // 성공하면 새로운 boardId로 tbl_board_image 이미지 등록
    // 피드 & 이미지 insert 성공하면 새로운 boardId 리턴
    // 피드 & 이미지 insert 실패하면 -1 리턴
    @Transactional
    public long insertFeed(FeedPostDto newFeed, HttpSession session) {

        // 로그인 유틸에서 세션으로 계정 정보 가져오기

        Board board = Board.builder()
                .content(newFeed.getContent())
                .account(newFeed.getAccount())
                .build();

        // 피드 insert 성공 시 1, 실패 시 0 리턴
        int res = feedMapper.saveFeed(board);

        if (res <= 0) { // 트랜잭션 취소
            throw new RuntimeException("피드 등록 - 글 저장에 실패했습니다.");
        }
        // tbl_board insert 성공하면 tbl_board_image 추가
        long newBoardId = feedMapper.getNewBoardId();
        log.debug("새 피드 번호: {}", newBoardId);

        List<MultipartFile> files = newFeed.getImages();

        if(files == null || files.isEmpty()) {
            throw new RuntimeException("피드 수정 - 이미지 파일이 없습니다.");
        }
        // file 존재하면 DB에 insert
            // 이미지 추가 성공하면 등록한 BoardId를 리턴
            // 이미지 추가 실패하면 트랜잭션 롤백
        for (int i = 0; i < files.size(); i++) {
            MultipartFile file = files.get(i);
            BoardImage b = BoardImage.builder()
                    .boardId(newBoardId)
                    .imagePath(FileUtil.uploadFile(file))
                    .imageOrder(i) // 인덱스 설정
                    .build();
            // DB insert 성공 ? 등록한 이미지 id : -1
            int result = imageService.addImage(b);
            if (result < 0) {
                log.debug("file 추가 실패: ", file);
                throw new RuntimeException("피드 등록 - 이미지 저장에 실패했습니다.");
            }
        }
        return newBoardId; // 피드 등록 완료

    }

    // 피드 수정
    @Transactional
    public boolean updateFeed(FeedModifyDto dto) {

        try {
            // tbl_board 수정
            Board newBoard = dto.toBoardEntity();
            boolean bFlag = feedMapper.modifyFeed(newBoard);
            if (!bFlag) {
                log.debug("게시글 디버그: ", newBoard);
                throw new RuntimeException("피드 수정 - 글 수정 저장에 실패했습니다.");
            }

            // tbl_board_image 수정 (삭제 후 추가 )
            List<MultipartFile> files = dto.getImages();
            if(files == null || files.isEmpty()) {
                throw new RuntimeException("피드 수정 - 이미지 파일이 없습니다.");
            }

            boolean b = imageService.deleteImages(dto.getBoardId());
            if(!b)  throw new RuntimeException("피드 수정 - 기존 이미지 파일 삭제에 실패했습니다.");

            int count = 0;
            for (MultipartFile file : files) {
                BoardImage imageEntity
                        = dto.toImageEntity(file, count++);
                int result = imageService.addImage(imageEntity);
                if (result <= 0) {
                    throw new RuntimeException("피드 수정 - 이미지 저장에 실패했습니다.\n"+imageEntity);
                }
            }

            return true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // 피드 삭제
    public FeedListDto deleteFeed(long boardId) {

        boolean flag = feedMapper.deleteFeed(boardId);
        log.debug("피드서비스 삭제: {}", flag);
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
