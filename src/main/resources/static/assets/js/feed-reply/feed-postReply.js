import { fetchInfScrollReplies } from "./feed-getReply.js";
import { BASE_URL } from "../feed-reply.js";

// 서버에 댓글 등록을 요청하는 비동기 함수
export const fetchReplyPost = async () => {
  
  const textInput = document.getElementById('newReplyText');
  const writerInput = document.getElementById('newReplyWriter');
  const boardId = document.getElementById('detailFeedModal').dataset.boardId;
  // 서버로 보낼 데이터
  // 댓글 요청dto인 ReplyRequestPostDto의 필드와 이름이 같아야함
  const payload = {
    text: textInput.value.trim(),
    author: writerInput.value.trim(),
    // boardId: document.getElementById('feedData').closest('.feed-item').dataset.feedId
    boardId: boardId
  }
  console.log(payload);

  const res = await fetch(`${BASE_URL}`, {
    method: 'POST',
    headers: {
        'content-type': 'application/json'
    },
    body: JSON.stringify(payload)
});

console.log('res: ', res);


const replies = await res.json();

// 댓글 입력 후 초기화
textInput.value = '';
writerInput.value = '';

// renderReplies(replies);
await fetchInfScrollReplies(1, true, boardId);
};
