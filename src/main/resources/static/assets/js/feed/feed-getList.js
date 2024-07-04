import { FEED_URL } from "../feed-list.js";
import {debounce, getRelTime} from "../util.js";
import {renderCarousel, setOneImgStyle} from "../image.js";
import {hideFeedSpinner, showFeedSpinner} from "../spinner.js";

let currentFeedPage = 1; // 현재 무한스크롤시 진행되고 있는 페이지 번호
let isFetchingFeed = false; // 데이터 불러오는 중에는 더 가져오지 않게 제어하기 위한 논리변수
let totalFeeds = 0;  // 총 게시글 수
let loadedFeeds = 0;  // 로딩된 게시글 수

// 피드 목록 렌더링
// spring FeedListDto 필드명 참고
function appendFeeds({ feeds, pageInfo, loginUser }) {

  console.log("appendFeeds 실행중: ", feeds);
  console.log("pageInfo : ", pageInfo.pageInfo);

  let tag = '';
  // 게시글이 존재하면
  if(feeds && feeds.length > 0) {
    let animationTiming = 1;
    feeds.forEach(
      ({boardId, nickname, content, createdAt, account
         , profileImage: profile, feedImageList
         , likeCount, userLike, bookmarkCount, userBookmark
       }, index) => {
        console.log('피드목록렌더링 account: ', account,' /userLike: ', userLike);
      tag += `
<!--        <div class="feed-item animate__animated animate__slideInUp animate__delay-${animationTiming}s" data-feed-id='${boardId}' data-feed-account='${account}'>-->
         <div class="feed-item" data-feed-id='${boardId}' data-feed-account='${account}'>
          <div class="profile-section">
            <div class="profile-row">
              <div class="profile-box">
                  <img src="${profile ? profile : '/assets/img/mimo.png'}" alt="Profile Picture" class="profile-pic">     
              </div>
              <div class="profile-column">
                <span class="nickname">${nickname}</span>
                <span class="created-at">${getRelTime(createdAt)}</span>          
              </div>
            </div>
          </div>
          <div class="image-carousel">`;

      // 캐러셀에 이미지 추가
      tag += renderCarousel(feedImageList, 'post-image d-block w-100', boardId,"List");

      tag += `</div>
          <div class="content-section">
            <span>${content}</span>
            <br>
            <span class="show-detail">더보기</span>
          </div>
          <div class="interaction-section">
            <span class="comments show-detail"><ion-icon name="chatbubble" ></ion-icon> ${pageInfo.totalCount}</span>`;

      tag+= `
            <span class="hearts">
                <ion-icon 
                    name="heart" class="${userLike ? 'liked':''}" 
                ></ion-icon> ${likeCount}</span>
            <span class="bookmarks">
                <ion-icon name="bookmark" class="${userBookmark ? 'bookmarked': ''}"
                ></ion-icon> ${bookmarkCount}</span>
          </div>
        </div>
      `;
    }); // feeads.forEach 종료

  } else{ // 게시글 없는 경우
      tag = `
        <div id="no-feed">
            <p>모든 피드를 다 보셨거나 작성하신 피드가 없습니다.</p> <br>
            <p>${loginUser ? loginUser.nickname : '방문자'}님 기억에 남은 여행을 공유하면 어떨까요?</p>
        </div>
      `;
  }
  // 게시글 컨테이너에 태그 추가
  document.getElementById('feedData').innerHTML += tag;
  // 이미지 1장이면 캐러셀 ui 안보이게 스타일 변경
  setOneImgStyle();
  // 로드된 게시글 수 업데이트
  loadedFeeds += feeds.length;
}

// 서버에서 피드 목록 가져오는 비동기 요청 함수
export async function fetchFeedList(
    pageNo = 1,
    // keyword = '',
    // sort='latest'
    ) {

  if(isFetchingFeed) return; // 서버에서 데이터를 가져오는 중이면 return;
  isFetchingFeed = true;
  const keyword = getSearchKeyword() || '';
  const sort = getSortSelected();

  const url = `${FEED_URL}/v1/list?pageNo=${pageNo}&type=cw&keyword=${keyword}&sort=${sort}`;
  console.log('fetchFeedList 실행: ', pageNo);

  const res = await fetch(url);
  if (!res.ok) {
    if(res.status === 204) {

    } else {
      throw new Error(`HTTP error! Status: ${res.status}`);
    }
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
  isFetchingFeed = false;

  // 피드 모두 가져오면 스크롤이벤트 제거
  if(loadedFeeds >= totalFeeds) {
    console.log('피드 모두 가져옴! 스크롤 이벤트 제거')
    hideFeedSpinner();
    document.body.removeEventListener('scroll', debouncedFeedScrollHandler);
  }

}


// 디바운싱 스크롤 이벤트 핸들러
const debouncedFeedScrollHandler = debounce(async function(e) {

  console.log("스크롤 이벤트 시작!");
  const scrollHeight = Math.max(
      document.documentElement.scrollHeight,
      document.body.scrollHeight,
  );
  const scrollTop = Math.max(document.documentElement.scrollTop, document.body.scrollTop);
  const clientHeight = document.documentElement.clientHeight;
  console.log(`scrollTop(${scrollTop}) + clientHeight(${clientHeight}) === scrollHeight(${scrollHeight})`)

  // 스크롤이 최하단부로 내려갔을 때만 이벤트 발생시켜야 함
  if (scrollTop + clientHeight + 200 >= scrollHeight
    && !isFetchingFeed
  ) {
    // 서버에서 데이터를 비동기로 불러와야 함
    console.log("스크롤 이벤트 핸들러 함수 실행");
    showFeedSpinner();
    await new Promise(resolve => setTimeout(resolve, 700));
    fetchFeedList(currentFeedPage + 1);
  }
}, 700);

// 무한 스크롤 이벤트 생성 함수
export function setupInfiniteScroll() {
  console.log("스크롤이벤트 생성 함수 실행");

  document.body.addEventListener('scroll', debouncedFeedScrollHandler)
}

// 검색어 값 찾아서 반환하는 함수
export function getSearchKeyword() {
  const $searchInput = document.querySelector('#searchFeed .form-control')
  return $searchInput.value;
}
// 정렬 선택사항 찾아서 반환하는 함수
export function getSortSelected() {
  const $filters = document.getElementById('filters-box');
  return  $filters.querySelector('.active-filter').dataset.sort || 'latest';
}