import { NEST_BASE_URL } from "../feed-reply.js";
import { showSpinner, hideSpinner } from "../spinner.js";
import { getRelativeTime, fetchInfScrollReplies } from "./feed-getReply.js";

// 대댓글 렌더링  
export function appendNestedReplies({ nestedReplies }, rno) {
  const $nestedReplyData = document.getElementById(`nestedReplyData-${rno}`);

  let tag = "";
  if (nestedReplies && nestedReplies.length > 0) {
    nestedReplies.forEach(({ nestedReplyId, replyId, text, writer, createAt }) => {
      tag += `
        <div id='nestedReplyContent-${nestedReplyId}' class='card-body' data-nested-rno='${nestedReplyId}'>
          <div class='row user-block'>
            <span class='col-md-3'>
              <b>${writer}</b>
            </span>
            <span class='offset-md-6 col-md-3 text-right'><b>${getRelativeTime(createAt)}</b></span>
          </div><br>
          <div class='row'>
            <div class='col-md-9'>${text}</div>
            <div class='col-md-3 text-right'>
              <a class='btn btn-sm btn-outline-dark nestedReplyModBtn' href='#' data-rno=${nestedReplyId}>수정</a>&nbsp;
              <a class='btn btn-sm btn-outline-dark nestedReplyDelBtn' href='#' data-rno=${nestedReplyId}>삭제</a>
            </div>
          </div>
        </div>
      `;
    });
  } else {
    tag = `<div id='nestedReplyContent' class='card-body'>대댓글이 아직 없습니다! ㅠㅠ</div>`;
  }

  $nestedReplyData.innerHTML = tag;

  // 대댓글 수정버튼에 이벤트 리스너 추가
  document.querySelectorAll(`#nestedReplyData-${rno} .nestedReplyModBtn`).forEach((button) => {
    button.addEventListener("click", function (e) {
      e.preventDefault();
      const nestedReplyId = button.dataset.rno;
      console.log('nestedReplyId: ', nestedReplyId);
      const currentText = button.closest('.card-body').querySelector(".col-md-9").textContent.trim();

      const form = `
        <div class="row">
          <div class="col-md-9">
            <div class="form-group">
              <label for="editNestedReplyText-${nestedReplyId}" hidden>대댓글 내용</label>
              <textarea rows="3" id="editNestedReplyText-${nestedReplyId}" name="editNestedReplyText"
                        class="form-control"
                        placeholder="대댓글을 입력해주세요.">${currentText}</textarea>
            </div>
          </div>
          <div class="col-md-3">
            <button id="nestedReplyModifyBtn-${nestedReplyId}" type="button"
                    class="btn btn-dark form-control nestedReplyModifyBtn" data-nested-rno="${nestedReplyId}">
              수정 완료
            </button>
          </div>
        </div>
      `;

      // 대댓글 수정 화면으로 교체
      const nestedReplyContent = button.closest('.card-body');
      nestedReplyContent.innerHTML = form;

      // 대댓글 수정 완료 버튼 클릭 이벤트
      document.getElementById(`nestedReplyModifyBtn-${nestedReplyId}`).addEventListener("click", async () => {
        const newText = document.getElementById(`editNestedReplyText-${nestedReplyId}`).value.trim();
        
        const payload = {
          nestedReplyId: nestedReplyId,
          newText: newText,
          replyId: rno, // 수정 후 댓글 조회를 위한 댓글 번호
        }

        const response = await fetch(`${NEST_BASE_URL}`, {
          method: 'PUT',
          headers: {
            'content-type': 'application/json'
          },
          body: JSON.stringify(payload)
        });

        console.log('response: ', response);

        if (response.ok) {
          await fetchInfScrollReplies(1, true);
        } else {
          console.error('대댓글 수정 실패');
        }
      });
    });
  });

  // 대댓글 삭제버튼에 이벤트 리스너 추가
  document.querySelectorAll(`#nestedReplyData-${rno} .nestedReplyDelBtn`).forEach((button) => {
    button.addEventListener("click", async function (e) {
      e.preventDefault();
      const nestedReplyId = button.dataset.rno;
      console.log('nestedReplyId: ', nestedReplyId);
      
      const confirmDelete = confirm("정말로 이 대댓글을 삭제하시겠습니까?");
      if (!confirmDelete) return;

      const payload = {
        nestedReplyId: nestedReplyId,
      }

      const response = await fetch(`${NEST_BASE_URL}/${nestedReplyId}?replyId=${rno}`, {
        method: 'DELETE',
        headers: {
          'content-type': 'application/json'
        },
        body: JSON.stringify(payload)
      });

      console.log('response: ', response);

      if (response.ok) {
        await fetchInfScrollReplies(1, true);
      } else {
        console.error('대댓글 삭제 실패');
      }
    });
  });
}

// 서버에서 대댓글 데이터 페칭 (가져오는 함수)
export async function fetchInfScrollNestReplies(rno) {
  showSpinner();  

  // const rno = document.getElementById("replyContent").dataset.rno; // 댓글 번호
  const res = await fetch(`${NEST_BASE_URL}/${rno}`);
  const nestedReplyResponse = await res.json();

  hideSpinner();
  appendNestedReplies(nestedReplyResponse, rno);
}
