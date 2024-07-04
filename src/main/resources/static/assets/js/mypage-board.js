// import {debounce} from "../util.js";

const $myBoardBtn = document.getElementById('my-board-btn');

$myBoardBtn.addEventListener('click', async e => {
    const account = $myBoardBtn.dataset.myAccount;
    console.log('마이페이지 동행 클릭!');
    console.log('account: ', account);

    try {
    const response = await fetch(`/mypage/boards?account=${account}`);
    if(!response.ok) {
    throw new Error('Network response was not ok ' + response.statusText);
    }

const data = await response.json();
 console.log('게시글 목록: ', data);

 // 데이터 렌더링
         renderBoardList(data);
    }

});

function renderBoardList(boardList) {
    const boardContainer = document.getElementById('board-container');
    boardContainer.innerHTML = '';

    boardList.forEach(board => {
        const boardElement = document.createElement('div');
        boardElement.className = 'board';
        boardElement.innerHTML = `
            <h3>${board.title}</h3>
            <p>${board.content}</p>
            <p>작성자: ${board.writer}</p>
            <p>조회수: ${board.viewCount}</p>
            <p>작성일: ${board.createdAt}</p>
        `;
        boardContainer.appendChild(boardElement);
    });
}




// async function fetchMyFeedList(account, pageNo=1) {
//
//     const url = `http://localhost:8181/mypage/v1/list/${account}/${pageNo}`;
//
//     if(isFetchingMyFeed) return; // 서버에서 데이터를 가져오는 중이면 return;
//     isFetchingMyFeed = true;
//
//     const res = await fetch(url);
//     if(!res.ok) {
//         throw new Error(`HTTP error! Status: ${res.status}`);
//     }
//     const myFeedListDto = await res.json();
//     console.log('마이피드: ',myFeedListDto);
//
//     if(pageNo === 1) {
//         totalMyFeeds = myFeedListDto.pageInfo.totalCount;
//         loadedMyFeeds = 0;
//
//         const $feedTab = document.getElementById('my-feed-tab');
//         $feedTab.innerHTML = '';
//         setupMyFeedInfiniteScroll();
//     }
//     // 피드 목록 렌더링
//     appendMyFeeds(myFeedListDto);
//     currentMyFeedPage = pageNo;
//     isFetchingMyFeed = false;
//
//     // 피드 모두 가져오면 스크롤이벤트 제거
//     if(loadedMyFeeds >= totalMyFeeds) {
//         console.log('마이페이지 피드 모두 가져옴! 스크롤 이벤트 제거')
//         document.body.removeEventListener('scroll', debouncedMyFeedScrollHandler);
//     }
//
// }
// 디바운싱 스크롤 이벤트 핸들러
// const debouncedMyFeedScrollHandler = debounce(async function(e) {
//
//     console.log("스크롤 이벤트 시작!");
//     const scrollHeight = Math.max(
//         document.documentElement.scrollHeight,
//         document.body.scrollHeight,
//     );
//     const scrollTop = Math.max(document.documentElement.scrollTop, document.body.scrollTop);
//     const clientHeight = document.documentElement.clientHeight;
//
//     // 스크롤이 최하단부로 내려갔을 때만 이벤트 발생시켜야 함
//     if (scrollTop + clientHeight + 200 >= scrollHeight
//         && !isFetchingMyFeed
//     ) {
//         // console.log(e);
//         // 서버에서 데이터를 비동기로 불러와야 함
//         // 2초의 대기열이 생성되면 다음 대기열 생성까지 2초를 기다려야 함
//         console.log("스크롤 이벤트 핸들러 함수 실행");
//         // showSpinner();
//         await new Promise(resolve => setTimeout(resolve, 700));
//         const account = $myFeedBtn.dataset.myAccount;
//         await fetchMyFeedList(account, currentMyFeedPage + 1);
//     }
// }, 700);
//
// // 무한 스크롤 이벤트 생성 함수
// export function setupMyFeedInfiniteScroll() {
//     console.log("스크롤이벤트 생성 함수 실행");
//
//     document.body.addEventListener('scroll', debouncedMyFeedScrollHandler)
// }
