// feed-getReply.js

import { BASE_URL } from "../feed-reply.js";
import { showSpinner, hideSpinner } from "../spinner.js";
import { debounce } from "../util.js";

// 전역 상태 객체
export const state = {
  currentPage: 1, // 현재 무한스크롤시 진행되고 있는 페이지 번호
  isFetching: false, // 데이터를 불러오는 중에는 더 가져오지 않게 제어하는 변수
  totalReplies: 0, // 총 댓글 수
  loadedReplies: 0, // 로딩된 댓글 수
};

// 댓글 등록시 시간에 대한 필터 함수
function getRelativeTime(createAt) {
  // 현재 시간 구하기
  const now = new Date();
  // 등록 시간 날짜타입으로 변환
  const past = new Date(createAt);

  // 사이 시간 구하기 (밀리초)
  const diff = now - past;

  const seconds = Math.floor(diff / 1000);
  const minutes = Math.floor(diff / 1000 / 60);
  const hours = Math.floor(diff / 1000 / 60 / 60);
  const days = Math.floor(diff / 1000 / 60 / 60 / 24);
  const weeks = Math.floor(diff / 1000 / 60 / 60 / 24 / 7);
  const years = Math.floor(diff / 1000 / 60 / 60 / 24 / 365);

  if (seconds < 60) {
    return "방금 전";
  } else if (minutes < 60) {
    return `${minutes}분 전`;
  } else if (hours < 24) {
    return `${hours}시간 전`;
  } else if (days < 7) {
    return `${days}일 전`;
  } else if (weeks < 52) {
    return `${weeks}주 전`;
  } else {
    return `${years}년 전}`;
  }
}

// 댓글 렌더링
export function appendReplies({ replies }, reset = false) {
  const $replyData = document.getElementById("replyData");

  // reset모드일경우 댓글을 모두 지움
  if (reset) {
    $replyData.innerHTML = "";
    state.loadedReplies = 0; // 로드된 댓글 수 초기화
    return;
  }

  // 댓글 목록 렌더링
  let tag = "";
  if (replies && replies.length > 0) {
    replies.forEach(({ replyId: rno, writer, text, createAt }) => {
      tag += `
            <div id='replyContent' class='card-body' data-rno='${rno}'>
                <div class='row user-block'>
                    <span class='col-md-3'>
                        <b>${writer}</b>
                    </span>
                    <span class='offset-md-6 col-md-3 text-right'><b>${getRelativeTime(
                      createAt
                    )}</b></span>
                </div><br>
                <div class='row'>
                    <div class='col-md-9'>${text}</div>
                    <div class='col-md-3 text-right'>
                        <a id='replyModBtn' class='btn btn-sm btn-outline-dark' href='#'>수정</a>&nbsp;
                        <a id='replyDelBtn' class='btn btn-sm btn-outline-dark' href='#'>삭제</a>&nbsp;
                        <div class="reply-reply-write"> <button type="button"
                          class="btn btn-dark form-control reply-reply-button">답글</button>
                        </div>
                    </div>
                </div>
            </div>
            `;
    });
  } else {
    tag = `<div id='replyContent' class='card-body'>댓글이 아직 없습니다! ㅠㅠ</div>`;
  }

  $replyData.innerHTML += tag;
}

// 더보기 누를시 -> 서버에서 댓글 데이터를 페칭
export async function fetchInfScrollReplies(pageNo = 1, reset = false, boardId) {
  if (state.isFetching) return; // 서버에서 데이터를 가져오는 중이면 return

  state.isFetching = true;
  if (pageNo > 1) showSpinner();

  const res = await fetch(`${BASE_URL}/${boardId}/page/${pageNo}`);
  const replyResponse = await res.json();

  // 응답 데이터 구조 확인
  console.log("replyResponse:", replyResponse);

  if (reset) {
    // 총 댓글 수 전역 변수 값 세팅
    state.totalReplies = replyResponse.pageInfo.totalCount;
    // 댓글 수 렌더링
    document.getElementById("replyCnt").textContent = state.totalReplies;
    state.loadedReplies = 0; // 댓글 입력, 삭제시 다시 1페이지 로딩시 초기값으로 만든다.
    appendReplies([], true); // 기존 댓글 목록 비우기
  }

  const spinnerMinTime = 800; // 최소 스피너 표시 시간 (0.8초)

  setTimeout(() => {
    // 댓글 목록 렌더링
    hideSpinner();
    appendReplies(replyResponse);
    // 로드된 댓글 수 업데이트
    state.loadedReplies += replyResponse.replies.length;
    state.currentPage = pageNo;

    if (reset) {
      window.scrollTo(0, 0); // 수정 후 페이지 상단으로 이동
    }
    // 댓글을 전부 가져올 시 스크롤 이벤트 제거하기
    if (state.loadedReplies >= state.totalReplies) {
      removeInfiniteScroll();
    }
    state.isFetching = false;
  }, spinnerMinTime);
}

// 스크롤 이벤트 핸들러 함수
async function scrollHandler(e) {
  if (
    window.innerHeight + window.scrollY >= document.body.offsetHeight + 0 &&
    !state.isFetching
  ) {
    await fetchInfScrollReplies(state.currentPage + 1);
  }
}

// 디바운스 사용
function debounceScrollHandler() {
  debounce(scrollHandler, 500);
} 


// 디바운싱 함수
// export function debounce(callback, wait) {
//   let timeout;
//   return (...args) => {
//     clearTimeout(timeout);
//     timeout = setTimeout(() => callback(...args), wait);
//   };
// }

// 무한 스크롤 이벤트 생성 함수
export function setupInfiniteScroll() {
  window.addEventListener("scroll", debounceScrollHandler());
}

// 무한 스크롤 이벤트 삭제 함수
export function removeInfiniteScroll() {
  window.removeEventListener("scroll", debounceScrollHandler());
}

// 초기 상태 리셋 함수
export async function initInfScroll() {
  removeInfiniteScroll();
  window.scrollTo(0, 0);
  state.currentPage = 1;
  fetchInfScrollReplies(1, true);
  setupInfiniteScroll();
}