// import { initInfScroll } from "./feed-reply/feed-getReply.js";
// import { removeReplyClickEvent } from "./feed-reply/feed-deleteReply.js";
// import { modifyReplyClickEvent } from "./feed-reply/feed-modifyReply.js";
// import { fetchNestedReplyPost } from "./acc-reply/postNestReply.js";

// ========================= 전역 변수 ========================
export const BASE_URL = "http://localhost:8181/api/v1/replies";
export const NEST_BASE_URL = "http://localhost:8181/api/v1/nested/replies";


// ========================= 함수 정의 =========================


// ========================= 실행 코드 ==========================

// 댓글 목록 서버에서 불러오기
// fetchReplies();
// setupInfiniteScroll();
// initInfScroll();
// 수정 이벤트 등록 함수 실행
// modifyReplyClickEvent();

// 댓글 작성/수정 이벤트 등록 (POST)
// document.getElementById('replyAddBtn').onclick = async e => {
//     if (isEditModeActive()) {
//       // 수정 모드일 때
//       await fetchReplyModify();
//     } else {
//       // 일반 모드일 때
//       await fetchReplyPost();
//     }
// }

// 대댓글 작성 이벤트 등록 (POST)
// document.getElementById('nestedReplyAddBtn').onclick = async e => {
//   console.log('click!')
//   // 함수
//   await fetchNestedReplyPost();
// }

// 댓글 삭제 이벤트 등록
// removeReplyClickEvent();


