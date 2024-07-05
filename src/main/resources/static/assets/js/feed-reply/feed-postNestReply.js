import { NEST_BASE_URL } from "../feed-reply.js";
// import { fetchInfScrollReplies } from "./feed-getReply.js";
import { fetchInfScrollNestReplies } from "./feed-getNestReply.js";

// 서버에 대댓글 등록을 요청하는 비동기 함수
export const fetchNestedReplyPost = async (rno) => {
  
  const NestTextInput = document.getElementById(`newNestedReplyText-${rno}`);
  const NestWriterInput = document.getElementById(`newNestedReplyWriter-${rno}`);

  // 서버로 보낼 데이터
  // 댓글 요청dto인 NestedReplyRequestPostDto 필드와 이름이 같아야함
  const payload = {
    text: NestTextInput.value.trim(),
    author: NestWriterInput.value.trim(),
    // replyId: document.getElementById("replyContent").dataset.rno
    replyId: rno
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
// NestWriterInput.value = '';

await fetchInfScrollNestReplies(rno);
};
