import { FEED_URL } from "../feed-list.js";
import {debounce} from "../util.js";
import {renderCarousel, setOneImgStyle} from "../image.js";

let currentFeedPage = 1; // 현재 무한스크롤시 진행되고 있는 페이지 번호
let isFetching = false; // 데이터 불러오는 중에는 더 가져오지 않게 제어하기 위한 논리변수
let totalFeeds = 0;  // 총 게시글 수
let loadedFeeds = 0;  // 로딩된 게시글 수

// 피드 목록 렌더링
// spring FeedListDto 필드명 참고
function appendFeeds({ feeds, pageInfo }) {

  console.log("appendFeeds 실행중: ", feeds);
  console.log("pageInfo : ", pageInfo.pageInfo);

  let tag = '';
  // 게시글이 존재하면
  if(feeds && feeds.length > 0) {
    let animationTiming = 1;
    feeds.forEach(
      ({boardId, nickname, content, createdAt, account
         , profileImage: profile, feedImageList}) => {

      tag += `
        <div class="feed-item scroll-spy animate__animated animate__slideInUp animate__delay-${animationTiming}s" data-feed-id='${boardId}' data-feed-account='${account}'>
        <!-- <div class="feed-item" data-feed-id='${boardId}' data-feed-account='${account}'>-->
          <div class="profile-section">
            <img src="${profile ? profile : '/assets/img/mimo.png'}" alt="Profile Picture" class="profile-pic">
            <span class="nickname">${nickname}</span>
            <span class="created-at">${createdAt}</span>
          </div>
          <div class="image-carousel">`;

      // 캐러셀에 이미지 추가
      tag += renderCarousel(feedImageList, 'post-image d-block w-100', boardId);

      tag += `</div>
          <div class="content-section">
            <span>${content}</span>
            <br>
            <span class="show-detail">더보기</span>
          </div>
          <div class="interaction-section">
            <span class="comments show-detail">💬 ${pageInfo.totalCount}</span>
            <span class="hearts">❤️ 25</span>
            <span class="bookmarks">🔖 5</span>
          </div>
        </div>
      `;
    }); // feeads.forEach 종료

  } else{ // 게시글 없는 경우

  }
  // 게시글 컨테이너에 태그 추가
  document.getElementById('feedData').innerHTML += tag;
  // 이미지 1장이면 캐러셀 ui 안보이게..
  setOneImgStyle();
  // 로드된 게시글 수 업데이트
  loadedFeeds += feeds.length;
}

// 서버에서 피드 목록 가져오는 비동기 요청 함수
export async function fetchFeedList(pageNo = 1, type = 'content', keyword = '') {

  const url = `${FEED_URL}/v1/list?pageNo=${pageNo}&type=${type}&keyword=${keyword}`;
  console.log('fetchFeedList 실행: ', pageNo);

  const res = await fetch(url);
  if (!res.ok) {
    throw new Error(`HTTP error! Status: ${res.status}`);
  }
  const feedListDto = await res.json();
  console.log(feedListDto);

  if(pageNo === 1) {
    totalFeeds = feedListDto.pageInfo.totalCount;
    loadedFeeds = 0;

    document.getElementById('feedData').innerHTML = '';

    setupInfiniteScroll();
  }

  // 피드 목록 렌더링
  appendFeeds(feedListDto);
  currentFeedPage = pageNo;
  isFetching = false;

  // 피드 모두 가져오면 스크롤이벤트 제거
  if(loadedFeeds >= totalFeeds) {
    window.removeEventListener('scroll', debouncedScrollHandler);
  }

}


// 디바운싱 스크롤 이벤트 핸들러
const debouncedScrollHandler = debounce(async function(e) {
  // 스크롤이 최하단부로 내려갔을 때만 이벤트 발생시켜야 함
  // 현재창에 보이는 세로길이 + 스크롤을 내린 길이 >= 브라우저 전체 세로길이
  if (
    window.innerHeight + window.scrollY >= document.body.offsetHeight + 200
    && !isFetching
  ) {
    // console.log(e);
    // 서버에서 데이터를 비동기로 불러와야 함
    // 2초의 대기열이 생성되면 다음 대기열 생성까지 2초를 기다려야 함
    console.log("스크롤 이벤트 핸들러 함수 실행");
    // showSpinner();
    await new Promise(resolve => setTimeout(resolve, 700));
    await fetchFeedList(currentFeedPage + 1);
  }
}, 700);

// 무한 스크롤 이벤트 생성 함수
export function setupInfiniteScroll() {
  console.log("스크롤이벤트 생성 함수 실행");

  window.addEventListener('scroll', debouncedScrollHandler)
}

