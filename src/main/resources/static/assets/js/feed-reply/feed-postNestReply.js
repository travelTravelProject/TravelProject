import { fetchInfScrollNestReplies } from "./feed-getNestReply.js";
import { NEST_BASE_URL } from "../feed-reply.js";

// 서버에 대댓글 등록을 요청하는 비동기 함수
export const fetchNestedReplyPost = async () => {
  
  const NestTextInput = document.getElementById('newNestedReplyText');
  const NestWriterInput = document.getElementById('newNestedReplyWriter');

  // 서버로 보낼 데이터
  // 댓글 요청dto인 NestedReplyRequestPostDto 필드와 이름이 같아야함
  const payload = {
    text: NestTextInput.value.trim(),
    author: NestWriterInput.value.trim(),
    replyId: document.getElementById("replyContent").dataset.rno
    // nestedReplyId: ??
  }
  console.log(payload);

  const res = await fetch(`${NEST_BASE_URL}`, {
    method: 'POST',
    headers: {
        'content-type': 'application/json'
    },
    body: JSON.stringify(payload)
});

console.log('res: ', res);


const replies = await res.json();

// 대댓글 입력 후 초기화
NestTextInput.value = '';
NestWriterInput.value = '';

// renderReplies(replies);
await fetchInfScrollNestReplies();
};




// async function postNestedReply(replyId, content) {
//     const response = await fetch(`${NEST_BASE_URL}`, {
//         method: 'POST',
//         headers: {
//             'Content-Type': 'application/json',
//         },
//         body: JSON.stringify({
//             replyId: replyId,
//             content: content,
//             writer: '작성자' // 작성자 정보 추가
//         }),
//     });

//     if (!response.ok) {
//         console.error('Failed to post nested reply');
//         return;
//     }

//     // 성공적으로 대댓글이 등록되었을 경우, 새로고침하거나 대댓글 목록을 다시 불러옵니다.
//     const newNestedReplies = await response.json();
//     appendNestedReplies(newNestedReplies);
// }

// function appendNestedReplies({ nestedReplies }) {
//     const $nestedReplyData = document.getElementById("nestedReplyData");

//     let tag = "";
//     if (nestedReplies && nestedReplies.length > 0) {
//         nestedReplies.forEach(({ nestedReplyId, writer, text, createAt }) => {
//             tag += `
//                 <div class='nested-reply' data-nested-rno='${nestedReplyId}'>
//                     <div><b>${writer}</b> ${getRelativeTime(createAt)}</div>
//                     <div>${text}</div>
//                     <div>
//                         <button class='nested-reply-modify-btn'>수정</button>
//                         <button class='nested-reply-delete-btn'>삭제</button>
//                     </div>
//                 </div>
//             `;
//         });
//     } else {
//         tag = `<div class='nested-reply'>대댓글이 없습니다!</div>`;
//     }

//     $nestedReplyData.innerHTML += tag;
// }

// // 모달 또는 입력 필드에서 제출 버튼 클릭 시
// document.getElementById('nestedReplySubmitBtn').onclick = async () => {
//     const replyId = getActiveReplyId(); // 현재 활성화된 대댓글 대상 댓글 ID
//     const content = document.getElementById('nestedReplyContent').value;

//     await postNestedReply(replyId, content);
// }
