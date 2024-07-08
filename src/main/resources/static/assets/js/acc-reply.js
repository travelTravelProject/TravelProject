import { initInfScroll } from "./acc-reply/getReply.js";
import { removeReplyClickEvent } from "./acc-reply/deleteReply.js";
import { modifyReplyClickEvent, fetchReplyModify, isEditModeActive } from "./acc-reply/modifyReply.js";
import { fetchReplyPost } from "./acc-reply/postReply.js";

// ========================= 전역 변수 ========================
export const BASE_URL = "http://localhost:8181/api/v1/replies";
export const NEST_BASE_URL = "http://localhost:8181/api/v1/nested/replies";


// ========================= 함수 정의 =========================


// ========================= 실행 코드 ==========================

// 댓글 목록 서버에서 불러오기
// fetchReplies();
// setupInfiniteScroll();

initInfScroll();

// 수정 이벤트 등록 함수 실행
modifyReplyClickEvent();

// 댓글 작성/수정 이벤트 등록 (POST)
document.getElementById('replyAddBtn').onclick = async () => {
    if (isEditModeActive()) {
      // 수정 모드일 때
      await fetchReplyModify();
    } else {
      // 일반 모드일 때
      await fetchReplyPost();
    }
};

// 댓글 삭제 이벤트 등록
removeReplyClickEvent();

// Input field and Add button elements
const newReplyText = document.getElementById('newReplyText');
const replyAddBtn = document.getElementById('replyAddBtn');

// Function to toggle the visibility of the replyAddBtn
const toggleReplyAddBtnVisibility = () => {
  if (newReplyText.value.trim() !== "") {
    replyAddBtn.style.display = "block"; // Show button
  } else {
    replyAddBtn.style.display = "none"; // Hide button
  }
};

// Attach input event listener to show/hide the add button
newReplyText.addEventListener('input', toggleReplyAddBtnVisibility);

// Attach keydown event listener to submit on Enter key
newReplyText.addEventListener('keydown', async e => {
  if (e.key === 'Enter') {
    if (newReplyText.value.trim() !== "") {
      if (isEditModeActive()) {
        // 수정 모드일 때
        await fetchReplyModify();
      } else {
        // 일반 모드일 때
        await fetchReplyPost();
      }
      // Clear input field after submission (optional)
      newReplyText.value = "";
      // Hide the button after submission (optional)
      replyAddBtn.style.display = "none";
    }
  }
});

// Initially hide the add button
replyAddBtn.style.display = "none";
