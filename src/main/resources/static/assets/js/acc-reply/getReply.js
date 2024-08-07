import { BASE_URL } from "../acc-reply.js";
import { showSpinner, hideSpinner } from "../spinner.js";
import { fetchInfScrollNestReplies } from "./getNestReply.js";
import { fetchNestedReplyPost } from "./postNestReply.js";
import { debounce } from "../util.js";

// =============== 무한 스크롤 포함 ============= //

// 댓글 등록시 시간에 대한 필터 함수
export function getRelativeTime(createAt) {
  // 현재 시간 구하기
  const now = new Date();
  // 등록 시간 날짜타입으로 변환
  const past = new Date(createAt);

  // 사이 시간 구하기 (밀리초)
  const diff = now - past;

  const seconds = Math.floor(diff / 1000);
  const minutes = Math.floor(diff / 1000 / 60);
  const hours = Math.floor(diff / 1000 / 60 / 60);
  const days = Math.floor(diff / 1000 / 60 / 60 / 24);
  const weeks = Math.floor(diff / 1000 / 60 / 60 / 24 / 7);
  const years = Math.floor(diff / 1000 / 60 / 60 / 24 / 365);

  if (seconds < 60) {
    return "방금 전";
  } else if (minutes < 60) {
    return `${minutes}분 전`;
  } else if (hours < 24) {
    return `${hours}시간 전`;
  } else if (days < 7) {
    return `${days}일 전`;
  } else if (weeks < 52) {
    return `${weeks}주 전`;
  } else {
    return `${years}년 전`;
  }
}

// 전역 변수
let currentPage = 1; // 현재 무한스크롤시 진행되고 있는 페이지 번호
let isFetching = false; // 데이터를 불러오는 중에는 더 가져오지 않게 제어하는 변수
let totalReplies = 0; // 총 댓글 수
let loadedReplies = 0; // 로딩된 댓글 수

// 댓글 렌더링
export function appendReplies({ replies, loginUser }, reset = false) {
  const $replyData = document.getElementById("replyData");

  // reset모드일경우 댓글을 모두 지움
  if (reset) {
    $replyData.innerHTML = "";
    loadedReplies = 0; // 로드된 댓글 수 초기화
    return;
  }

  let tag = "";
  if (replies && replies.length > 0) {
    replies.forEach(({ replyId: rno, writer, text, createAt, account: replyAccount, profileImage }) => {

      tag += `
            <div id='replyContent' class='card-body' data-rno='${rno}'>
                <div class='row user-block'>
                  <div class='reply-head'>
                    <div class="profile-box">
                        <img src="${profileImage ? profileImage : "/assets/img/mimo.png"}" alt="profileImage image">
                    </div>
                    <div class="reply-body">
                      <div class='col-md-3'>
                          <b>${writer}</b>
                      </div>
                      <div class='offset-md-6 text-right' style="margin: 0;"><b>${getRelativeTime(createAt)}</b></div>
                    </div>
                  </div>
                </div><br>
                <div class='row'>
                    <div class='col-md-9' style="margin: 5px 0 20px 90px;">${text}</div>
                    <div class='col-md-3 text-right'>
                    <div class="reply-reply-write">
                      <button type="button" class="reply-reply-button" data-rno='${rno}'><i class="fas fa-comment"></i>답글</button>
                    </div>
                    `;
            // 관리자이거나 내가 쓴 댓글일 경우만 조건부 렌더링
            // 로그인한 회원 권한, 로그인한 회원 계정명, 해당 댓글의 계정명
            if (loginUser) { // 로그인 유저가 존재하면~
              const {auth, account: loginUserAccount} = loginUser;

              if (auth === 'ADMIN' || replyAccount === loginUserAccount) {
                tag += `
                  <div class="modDelBtn">
                    <a id='replyModBtn' class='btn btn-sm btn-outline-dark modBtn' href='#'>수정</a>&nbsp;
                    <a id='replyDelBtn' class='btn btn-sm btn-outline-dark delBtn' href='#'>삭제</a>&nbsp;
                  </div>
                `;
              }
              tag += `
                  </div>
                </div>

                </div>
                <div id="nestedReplyData-${rno}" class="nested-reply-data">
                </div>
                
            </div>

            <div id="nestedReplyWriteSection-${rno}" class="Nestedcard hidden" data-rno='${rno}'>
                <div class="card-body">
                    <div class="row">
                        <div class="col-md-3">
                            <div class="form-group">
                                <label for="newNestedReplyWriter-${rno}" hidden>대댓글 작성자</label>
                                <input
                                id="newNestedReplyWriter-${rno}"
                                name="nestedReplyWriter"
                                type="text"
                                value="${loginUser.nickname}"
                                class="form-control"
                                placeholder="작성자 이름"
                                style="margin-bottom: 6px"
                                readonly
                                />
                            </div>
                            <div class="col-md-9">
                              <div class="form-group">
                                  <label for="newNestedReplyText-${rno}" hidden>대댓글 내용</label>
                                  <textarea
                                  rows="3"
                                  id="newNestedReplyText-${rno}"
                                  name="nestedReplyText"
                                  class="form-control"
                                  placeholder="대댓글을 입력해주세요."
                                  ></textarea>
                                  <button
                                  id="nestedReplyAddBtn-${rno}"
                                  type="button"
                                  class="btn btn-dark form-control nested-reply-add-btn hidden"
                                  data-rno='${rno}'
                                  style="width: 60px;
                                        height: 35px;
                                        background-color: #d9d9d9;
                                        border: none;"
                                  >
                                  등록
                                </button>
                              </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>         
            `;

            // 대댓글 fetch
            fetchInfScrollNestReplies(rno);
    }
  });
  } else {
    // tag = `<div id='replyContent' class='card-body'>댓글이 아직 없습니다! ㅠㅠ</div>`;
  }

  $replyData.innerHTML += tag;

  // 답글 버튼 클릭시 rno에 맞는 대댓글 입력창 토글
  document.querySelectorAll(".reply-reply-button").forEach(button => {
    button.addEventListener("click", (event) => {
      const rno = event.target.dataset.rno;
      const nestedReplySection = document.getElementById(`nestedReplyWriteSection-${rno}`);
      nestedReplySection.classList.toggle("hidden");
      console.log('답글 click!');
    });
  });

// 대댓글 등록 버튼 클릭시 이벤트 리스너 추가
document.querySelectorAll(".nested-reply-add-btn").forEach(button => {
  const rno = button.dataset.rno;
  const textarea = document.getElementById(`newNestedReplyText-${rno}`);

  // textarea의 내용 변경시 버튼 보여주기/숨기기
  textarea.addEventListener("input", () => {
    if (textarea.value.trim() === '') {
      button.classList.add('hidden');
    } else {
      button.classList.remove('hidden');
    }
  });

  // 엔터키가 눌렸을 때도 대댓글 등록 버튼을 클릭하도록 설정
  textarea.addEventListener("keydown", async (event) => {
    if (event.key === 'Enter') {
      event.preventDefault(); // 기본 엔터키 동작을 막음 (줄바꿈 방지)
      await fetchNestedReplyPost(rno);
    }
  });

  button.addEventListener("click", async (event) => {
    await fetchNestedReplyPost(rno);
  });
});

  
  
}

// 서버에서 댓글 데이터를 페칭
export async function fetchInfScrollReplies(pageNo = 1, reset = false) {
  if (isFetching) return; // 서버에서 데이터를 가져오는 중이면 return

  isFetching = true;
  if (pageNo > 1) showSpinner();

  const bno = document.getElementById("wrap").dataset.bno; // 게시물 글번호
  const res = await fetch(`${BASE_URL}/${bno}/page/${pageNo}`);
  const replyResponse = await res.json();

  // 응답 데이터 구조 확인
  console.log("replyResponse:", replyResponse);

  if (reset) {
    // 총 댓글 수 전역 변수 값 세팅
    totalReplies = replyResponse.pageInfo.totalCount;
    // 댓글 수 렌더링
    document.getElementById("replyCnt").textContent = totalReplies;
    loadedReplies = 0; // 댓글 입력, 삭제시 다시 1페이지 로딩시 초기값으로 만든다.
    appendReplies([], true); // 기존 댓글 목록 비우기
  }

  const spinnerMinTime = 800; // 최소 스피너 표시 시간 (0.8초)

  setTimeout(() => {
    // 댓글 목록 렌더링
    hideSpinner();
    appendReplies(replyResponse);
    // 로드된 댓글 수 업데이트
    loadedReplies += replyResponse.replies.length;
    currentPage = pageNo;

    if (reset) {
      window.scrollTo(0, 0); // 수정 후 페이지 상단으로 이동
    }

    // 댓글을 전부 가져올 시 스크롤 이벤트 제거하기
    if (loadedReplies >= totalReplies) {
      removeInfiniteScroll();
    }
    
    isFetching = false;
  }, spinnerMinTime);
}

// 스크롤 이벤트 핸들러 함수
async function scrollHandler(e) {
  // console.log(123);
  // 스크롤이 최하단부로 내려갔을 때만 이벤트 발생시켜야 함
  //  현재창에 보이는 세로길이 + 스크롤을 내린 길이 >= 브라우저 전체 세로길이
  if (
    window.innerHeight + window.scrollY >= document.body.offsetHeight - 10
    && !isFetching
  ) {
    // showSpinner();
    // console.log('window.innerHeight:', window.innerHeight);
    // console.log('window.scrollY:', window.scrollY);
    // console.log('document.body.offsetHeight:', document.body.offsetHeight);
    // console.log('window.innerHeight + window.scrollY >= document.body.offsetHeight: ', window.innerHeight + window.scrollY >= document.body.offsetHeight);
    // await new Promise(resolve => setTimeout(resolve, 500));
    await fetchInfScrollReplies(currentPage + 1);
  }
}

// 디바운스 사용
const debounceScrollHandler = debounce(scrollHandler, 500);

// 무한 스크롤 이벤트 생성 함수
export function setupInfiniteScroll() {
  window.addEventListener('scroll', debounceScrollHandler);
}

// 무한 스크롤 이벤트 삭제 함수
export function removeInfiniteScroll() {
  // console.log('removed scroll');
  window.removeEventListener('scroll', debounceScrollHandler);
}

// 초기 상태 리셋 함수
export async function initInfScroll() {
  // console.log('init!!');
  removeInfiniteScroll();
  window.scrollTo(0, 0);
  currentPage = 1;
  fetchInfScrollReplies(1, true);
  // console.log('setup scroll');
  setupInfiniteScroll();
}
