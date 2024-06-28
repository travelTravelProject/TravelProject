import { BASE_URL } from "../feed-reply.js";
import { fetchInfScrollReplies } from "./feed-getReply.js";

let isModifyMode = false; // 수정 모드를 설정하는 변수
let modifyRno = null; // 수정할 댓글의 rno

// 수정 이벤트 등록 함수
export function modifyReplyClickEvent() {
  // 버블링 (replyData에 event)
  document.getElementById('replyData').addEventListener('click', e => {
    e.preventDefault();

    if (!e.target.matches('#replyModBtn')) return;

    // 수정 전 텍스트 읽기
    const text = e.target.closest('.row').querySelector('.col-md-9').textContent;

    // 수정 전 작성자 읽기
    const writer =  e.target.closest('#replyContent').querySelector('.user-block .col-md-3 b').textContent;
    
    // 수정 버튼 누를시 text 넣기
    document.getElementById('newReplyText').value = text;

    // 수정 버튼 누를시 text 넣기
    document.getElementById('newReplyWriter').value = writer;


    // 수정할 댓글의 rno를 저장하고 수정 모드 활성화
    // editRno = document.getElementById('replyModBtn').closest('#replyContent').dataset.rno;
    modifyRno = e.target.closest('#replyContent').dataset.rno;
    
    isModifyMode = true;
  });
}

// 수정 요청 처리 함수
export async function fetchReplyModify() {

  const boardId = document.getElementById('detailFeedModal').dataset.boardId;

  const payload = {
    replyId: modifyRno,
    newText: document.getElementById('newReplyText').value,
    boardId: boardId
  };
  console.log('payload: ', payload);
  const res = await fetch(`${BASE_URL}`, {
    method: 'PUT',
    headers: {
      'content-type': 'application/json'
    },
    body: JSON.stringify(payload)
  });

  if (!res.ok) {
    alert('수정 실패!');
    return;
  }

  // window.scrollTo(0, 0); // 수정 후 페이지 상단으로 이동
  await fetchInfScrollReplies(1, true, boardId); // 전체 댓글 목록 다시 불러오기

  // 수정 모드 해제
  isModifyMode = false;
  modifyRno = null;

  // 입력 칸 비우기
  document.getElementById('newReplyText').value = '';
  // document.getElementById('newReplyWriter').value = '';
}

// 수정 모드 여부를 반환하는 함수
export function isEditModeActive() {
  return isModifyMode;
}
