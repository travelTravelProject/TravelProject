import { NEST_BASE_URL } from "../feed-reply.js";

// 대댓글 렌더링
export function appendNestedReplies({ nestedReplies }) {
  const $nestedReplyData = document.getElementById("nestedReplyData");

  let tag = "";
  if (nestedReplies && nestedReplies.length > 0) {
    nestedReplies.forEach(({ nestedReplyId, replyId, text, writer, createAt }) => {
      tag += `
        <div id='nestedReplyContent' class='card-body' data-nested-rno='${nestedReplyId}'>
          <div class='row user-block'>
            <span class='col-md-3'>
              <b>${writer}</b>
            </span>
            <span class='offset-md-6 col-md-3 text-right'><b>${createAt}</b></span>
          </div><br>
          <div class='row'>
            <div class='col-md-9'>${text}</div>
            <div class='col-md-3 text-right'>
              <a id='nestedReplyModBtn' class='btn btn-sm btn-outline-dark' href='#'>수정</a>&nbsp;
              <a id='nestedReplyDelBtn' class='btn btn-sm btn-outline-dark' href='#'>삭제</a>
            </div>
          </div>
        </div>
      `;
    });
  } else {
    tag = `<div id='nestedReplyContent' class='card-body'>대댓글이 아직 없습니다! ㅠㅠ</div>`;
  }

  $nestedReplyData.innerHTML = tag;
}

// 서버에서 대댓글 데이터 페칭 (가져오는 함수)
export async function fetchInfScrollNestReplies() {
  // showSpinner();

  const rno = document.getElementById("replyContent").dataset.rno; // 댓글 번호
  const res = await fetch(`${NEST_BASE_URL}/${rno}`);
  const nestedReplyResponse = await res.json();

  // hideSpinner();
  appendNestedReplies(nestedReplyResponse);
}
