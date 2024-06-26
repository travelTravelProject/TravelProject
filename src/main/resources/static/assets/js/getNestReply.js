// 전역 변수
// let currentPage = 1; // 현재 무한스크롤시 진행되고 있는 페이지 번호
// let isFetching = false; // 데이터를 불러오는 중에는 더 가져오지 않게 제어하는 변수
// let totalNestReplies = 0; // 총 대댓글 수
// let loadedNestReplies = 0; // 로딩된 대댓글 수

// // 서버에서 대댓글 데이터를 페칭
// export async function fetchInfScrollNestReplies(reset = false) {
//   if (isFetching) return; // 서버에서 데이터를 가져오는 중이면 return

//   isFetching = true;
//   // if (pageNo > 1) showSpinner();

//   const rno = document.getElementById("replyContent").dataset.rno; // 댓글 번호
//   const res = await fetch(`${NEST_BASE_URL}/${rno}`);
//   const replyResponse = await res.json(); 

//   // 응답 데이터 구조 확인
//   console.log("replyResponse:", replyResponse);

//   if (reset) {
//     // 총 대댓글 수 전역 변수 값 세팅
//     // totalNestReplies = replyResponse.pageInfo.totalCount;
//     // 댓글 수 렌더링
//     // document.getElementById("replyCnt").textContent = totalNestReplies;
//     loadedNestReplies = 0; // 대댓글 입력, 삭제시 다시 1페이지 로딩시 초기값으로 만든다.
//     appendReplies([], true); // 기존 댓글 목록 비우기
//   }

//   // const spinnerMinTime = 800; // 최소 스피너 표시 시간 (0.8초)

//   // setTimeout(() => {
//   //   // 댓글 목록 렌더링
//   //   hideSpinner();
//   //   appendReplies(replyResponse);
//   //   // 로드된 댓글 수 업데이트
//   //   loadedReplies += replyResponse.replies.length;
//   //   currentPage = pageNo;

//   //   if (reset) {
//   //     window.scrollTo(0, 0); // 수정 후 페이지 상단으로 이동
//   //   }
//   //   // 댓글을 전부 가져올 시 스크롤 이벤트 제거하기
//   //   if (loadedReplies >= totalReplies) {
//   //     removeInfiniteScroll();
//   //   }
//   //   isFetching = false;
//   // }, spinnerMinTime);
// }

import { NEST_BASE_URL } from "./reply.js";

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
