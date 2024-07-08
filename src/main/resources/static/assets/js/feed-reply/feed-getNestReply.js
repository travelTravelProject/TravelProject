// feed-getNestReply.js

import { NEST_BASE_URL } from "../feed-reply.js";
import { showSpinner, hideSpinner } from "../spinner.js";
import { getRelativeTime, fetchInfScrollReplies } from "./feed-getReply.js";

// 대댓글 렌더링  
export function appendNestedReplies({ nestedReplies, loginUser, boardId }, rno) {
  const $nestedReplyData = document.getElementById(`nestedReplyData-${rno}`);

  let tag = "";
  if (nestedReplies && nestedReplies.length > 0) {
    // "답글 보기" 버튼 추가
    tag += `
      <div class='text-right' style="margin-top: 10px;">
        <button id='toggleNestedRepliesBtn-${rno}' class='btn btn-outline-dark' style="box-shadow: none;border: none;width: 135px;display: inline-block; margin-left: 80px;"><i class="fas fa-arrow-right"></i> 답글 ${nestedReplies.length}개 보기</button>
      </div>
    `;

    $nestedReplyData.innerHTML = tag;

    // 답글보기 버튼 클릭시 대댓글 렌더링
    document.getElementById(`toggleNestedRepliesBtn-${rno}`).addEventListener('click', (e) => {
      const button = e.target;
      const isHidden = button.innerText.includes('숨기기');
      
      if (isHidden) {
        // 대댓글 숨기기
        $nestedReplyData.querySelectorAll('.nested-reply-card').forEach(card => card.remove());
        button.innerHTML = `<i class="fas fa-arrow-right"></i> 답글 ${nestedReplies.length}개 보기`;
      } else {
        // 대댓글 보기
        let nestedRepliesTag = nestedReplies.map(({ nestedReplyId, text, writer, createAt, account: nestReplyAccount }) => `
          <div id='nestedReplyContent-${nestedReplyId}' class='card-body nested-reply-card' data-nested-rno='${nestedReplyId}' style="
        height: 190px; background-color: #fff;border: none;">
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
                  <div class='offset-md-6 text-right' style="margin-left: 20px;"><b>${getRelativeTime(createAt)}</b></div>
                </div>
              </div>
            </div><br>
            <div class='row reply-content'>
              <div class='col-md-9' style="margin-left: 105px;">${text}</div>
              <div class='col-md-3 text-right nestModDel'
              style="margin-left: 222px;
                      width: 100%;">
              ${loginUser && (loginUser.auth === 'ADMIN' || nestReplyAccount === loginUser.account) ? `
                <a class='btn btn-sm btn-outline-dark nestedReplyModBtn' href='#' data-rno=${nestedReplyId}
                style="border: none;
                      width: 60px;
                      font-size: 16px;
                      padding-left: 8px;
                      font-weight: 500;
                      box-shadow:none;">수정</a>&nbsp;
                <a class='btn btn-sm btn-outline-dark nestedReplyDelBtn' href='#' data-rno=${nestedReplyId}
                style="border: none;
                      font-size: 16px;
                      width: 60px;
                      padding-left: 8px;
                      font-weight: 500;
                      box-shadow:none;">삭제</a>` : ''}
              </div>
            </div>
          </div>
        `).join('');
        
        $nestedReplyData.insertAdjacentHTML('beforeend', nestedRepliesTag);
        button.innerText = '답글 숨기기';

        // 대댓글 수정 및 삭제 버튼에 이벤트 리스너 추가
        addNestedReplyEventListeners(rno, loginUser, boardId);
      }
    });
  } else {
    $nestedReplyData.innerHTML = tag;
  }
}

// 대댓글 수정 및 삭제 버튼에 이벤트 리스너 추가 함수
function addNestedReplyEventListeners(rno, loginUser, boardId) {
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
                        class="form-control" style="width: 280px;height: 40px;box-shadow: none;"
                        placeholder="대댓글을 입력해주세요.">${currentText}</textarea>
            </div>
          </div>
          <div class="col-md-3" style="margin-left: 220px;display: flex;width: 40%;">
            <button id="nestedReplyModifyBtn-${nestedReplyId}" type="button"
                    class="btn btn-dark form-control nestedReplyModifyBtn" data-nested-rno="${nestedReplyId}"
                    style="color: black;box-shadow:none;width: 100px;background-color: #fff;border: none;font-weight: 500;">
              수정
            </button>
            <button id="nestedReplyCancelBtn-${nestedReplyId}" type="button"
                    class="btn btn-secondary form-control nestedReplyCancelBtn" data-nested-rno="${nestedReplyId}"
                    style="color: black;box-shadow:none;margi;width: 100px;border: none;background-color: #fff;font-weight: 500;">
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
          boardId: boardId // 수정 후 댓글 조회를 위한 게시판 번호
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

      // 대댓글 취소 버튼 클릭 이벤트
      document.getElementById(`nestedReplyCancelBtn-${nestedReplyId}`).addEventListener("click", () => {
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

      const response = await fetch(`${NEST_BASE_URL}/${nestedReplyId}?replyId=${rno}&boardId=${boardId}`, {
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
export async function fetchInfScrollNestReplies(rno, boardId) {
  showSpinner();

  // const rno = document.getElementById("replyContent").dataset.rno; // 댓글 번호
  const res = await fetch(`${NEST_BASE_URL}/${rno}?boardId=${boardId}`);
  const nestedReplyResponse = await res.json();

  hideSpinner();
  appendNestedReplies(nestedReplyResponse, rno, boardId);
}
