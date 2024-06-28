import { initInfScroll } from "./acc-reply/getReply.js";
import { removeReplyClickEvent } from "./acc-reply/deleteReply.js";
import { modifyReplyClickEvent, fetchReplyModify, isEditModeActive } from "./acc-reply/modifyReply.js";
import { fetchReplyPost } from "./acc-reply/postReply.js";
// import { fetchNestedReplyPost } from "./acc-reply/postNestReply.js";

// ========================= 전역 변수 ========================
export const BASE_URL = "http://localhost:8181/api/v1/replies";
export const NEST_BASE_URL = "http://localhost:8181/api/v1/nested/replies";


// ========================= 함수 정의 =========================


// ========================= 실행 코드 ==========================

// 댓글 목록 서버에서 불러오기
// fetchReplies();
initInfScroll();
// setupInfiniteScroll();

// 수정 이벤트 등록 함수 실행
modifyReplyClickEvent();

// 댓글 작성/수정 이벤트 등록 (POST)
document.getElementById('replyAddBtn').onclick = async e => {
    if (isEditModeActive()) {
      // 수정 모드일 때
      await fetchReplyModify();
    } else {
      // 일반 모드일 때
      await fetchReplyPost();
    }
}

// 대댓글 작성 이벤트 등록 (POST)
// document.getElementById('nestedReplyAddBtn').onclick = async e => {
//   console.log('click!')
//   // 함수
//   await fetchNestedReplyPost();
// }

// 댓글 삭제 이벤트 등록
removeReplyClickEvent();


// // 답글 누를 시 대댓글 쓰기 영역 나오는 이벤트
// const $replyReplyButtons = document.querySelector('.reply-reply-write');

// $replyReplyButtons.addEventListener('click', function() {
//   // 대댓글 쓰기 영역
//   const $nestWriteBtn = document.querySelector('.Nestedcard');
  
//     if ($nestWriteBtn.classList.contains('hidden')) {
//         $nestWriteBtn.classList.remove('hidden');
//     } else {
//       $nestWriteBtn.classList.add('hidden');
//     }
// });