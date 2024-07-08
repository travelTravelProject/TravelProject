import { NEST_BASE_URL } from "../acc-reply.js";
import { initInfScroll } from "./getReply.js";
import { getRelativeTime } from "./getReply.js";
import { showSpinner, hideSpinner } from "../spinner.js";

// 대댓글 렌더링
export function appendNestedReplies({ nestedReplies, loginUser }, rno) {
  const $nestedReplyData = document.getElementById(`nestedReplyData-${rno}`);

  let tag = "";
  if (nestedReplies && nestedReplies.length > 0) {
    nestedReplies.forEach(({ nestedReplyId, replyId, text, writer, createAt, account: nestReplyAccount }) => {
      tag += `
        <div id='nestedReplyContent-${nestedReplyId}' class='card-body nested-reply-card' data-nested-rno='${nestedReplyId}'>
          <div class='row user-block'>
            <div class='nestReply-head'>
              <div class="profile-box">
                ${loginUser && loginUser.profileImage ? 
                  `<img src="${loginUser.profileImage}" alt="profileImage image">` : 
                  `<img src="/assets/img/mimo.png" alt="profile image">`}
              </div>
              <div class="nestReply-body">
                <div class='col-md-3'>
                  <b>${writer}</b>
                </div>
                <div class='offset-md-6 text-right'><b>${getRelativeTime(createAt)}</b></div>
              </div>
            </div>
          </div><br>
          <div class='row reply-content'>
            <div class='col-md-9'>${text}</div>
            <div class='col-md-3 text-right nestModDel'>
            `;
      if (loginUser) { // 로그인 유저가 존재하면~
        const {auth, account: loginUserAccount} = loginUser;

        if (auth === 'ADMIN' || nestReplyAccount === loginUserAccount) {
          tag += `
              <a class='btn btn-sm btn-outline-dark nestedReplyModBtn' href='#' data-rno=${nestedReplyId}>수정</a>&nbsp;
              <a class='btn btn-sm btn-outline-dark nestedReplyDelBtn' href='#' data-rno=${nestedReplyId}>삭제</a>
          `;
        }
        tag += `
            </div>
          </div>
        </div>
      `;
    }
  });
  } else {
    // tag = `<div id='nestedReplyContent' class='card-body'>대댓글이 아직 없습니다! ㅠㅠ</div>`;
  }

  $nestedReplyData.innerHTML = tag;

  // 대댓글 이벤트 리스너 추가
  addNestedReplyEventListeners(rno);
}

// 대댓글 이벤트 리스너 추가 함수
function addNestedReplyEventListeners(rno) {
  // 대댓글 수정버튼에 이벤트 리스너 추가
  document.querySelectorAll(`#nestedReplyData-${rno} .nestedReplyModBtn`).forEach((button) => {
    button.addEventListener("click", function (e) {
      e.preventDefault();
      const nestedReplyId = button.dataset.rno;
      console.log('nestedReplyId: ', nestedReplyId);
      const currentText = button.closest('.card-body').querySelector(".col-md-9").textContent.trim();

      const form = `
        <div class="row">
          <div class="col-md-9" style="margin: 5px 0 20px 0;">
            <div class="form-group">
              <label for="editNestedReplyText-${nestedReplyId}" hidden>대댓글 내용</label>
              <textarea rows="3" id="editNestedReplyText-${nestedReplyId}" name="editNestedReplyText"
                        class="form-control"
                        placeholder="대댓글을 입력해주세요."
                        style="width: 550px;">${currentText}</textarea>
            </div>
          </div>
          <div class="col-md-3">
            <button id="nestedReplyModifyBtn-${nestedReplyId}" type="button"
                    class="btn btn-dark form-control nestedReplyModifyBtn" data-nested-rno="${nestedReplyId}"
                    style="background-color: inherit;border: none;font-weight: 500;border-radius: 5px;">
              수정
            </button>
            <button id="nestedReplyCancelBtn-${nestedReplyId}" type="button"
                    class="btn btn-secondary form-control nestedReplyCancelBtn" data-nested-rno="${nestedReplyId}"
                    style="color: black;box-shadow:none;margin:5px;border: none;background-color: inherit;font-weight: 500;border-radius: 5px;">
              취소
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
          // await fetchInfScrollReplies(1, true);
          await initInfScroll();
        } else {
          console.error('대댓글 수정 실패');
        }
      });
      // 대댓글 취소 버튼 클릭 이벤트
      document.getElementById(`nestedReplyCancelBtn-${nestedReplyId}`).addEventListener("click", () => {
        console.log('취소');
        fetchInfScrollNestReplies(rno); // 원래 대댓글 목록 다시 불러오기
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
        // await fetchInfScrollReplies(1, true);
        await initInfScroll();
      } else {
        console.error('대댓글 삭제 실패');
      }
    });
  });
}

// 서버에서 대댓글 데이터 페칭 (가져오는 함수)
export async function fetchInfScrollNestReplies(rno) {
  showSpinner();
  const res = await fetch(`${NEST_BASE_URL}/${rno}`);
  const nestedReplyResponse = await res.json();
  console.log('nestedReplyResponse: ', nestedReplyResponse);
  hideSpinner();
  appendNestedReplies(nestedReplyResponse, rno);
  return nestedReplyResponse.nestedReplies.length;
}
