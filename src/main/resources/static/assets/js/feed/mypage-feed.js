import {setupInfiniteScroll} from "./feed-getList";

const $myFeedBtn = document.getElementById('my-feed-btn');

let currentMyFeedPage = 1; // 현재 무한스크롤시 진행되고 있는 페이지 번호
let isFetchingMyFeed = false; // 데이터 불러오는 중에는 더 가져오지 않게 제어하기 위한 논리변수
let totalMyFeeds = 0;  // 총 게시글 수
let loadedMyFeeds = 0;  // 로딩된 게시글 수


function openFeedTab(account) {
    const $feedTab = document.getElementById('my-feed-tab');
    $feedTab.style.display = block;
}

$myFeedBtn.addEventListener('click', e => {
    const account = $myFeedBtn.dataset.myAccount
    openFeedTab(account);
});

function appendMyFeeds(myFeedListDto) {
    const {myFeeds} = myFeedListDto;
    let tag = '';
    myFeeds.forEach(f => {
        const {boardId, imagePath, likeCount, bookmarkCount, replyCount} = f;
        tag += `
            <div class="myfeed-item" data-feed-id="${boardId}">
                <img src="${imagePath}" alt="Image 1">
                <div class="overlay">
                    <div class="text">❤️ ${likeCount} | 💬 ${replyCount} ${bookmarkCount}</div>
                </div>
            </div>
        `;
    });
    const $feedTab = document.getElementById('my-feed-tab');
    $feedTab.innerHTML += tag;
}

async function fetchMyFeedList(account, pageNo=1) {

    const url = `http://localhost:8181/mypage/v1/list/${account}/${pageNo}`;

    if(isFetchingMyFeed) return; // 서버에서 데이터를 가져오는 중이면 return;
    isFetchingMyFeed = true;

    const res = await fetch(url);
    if(!res.ok()) {
        throw new Error(`HTTP error! Status: ${res.status}`);
    }
    const myFeedListDto = await res.json();
    console.log('마이피드: ',myFeedListDto);

    if(pageNo === 1) {
        totalMyFeeds = myFeedListDto.pageInfo.totalCount;
        loadedMyFeeds = 0;

        const $feedTab = document.getElementById('my-feed-tab');
        $feedTab.innerHTML = '';
        setupInfiniteScroll();
    }
    // 피드 목록 렌더링
    appendMyFeeds(myFeedListDto);
    currentMyFeedPage = pageNo;
    isFetchingMyFeed = false;

    // 피드 모두 가져오면 스크롤이벤트 제거
    if(loadedMyFeeds >= totalMyFeeds) {
        console.log('피드 모두 가져옴! 스크롤 이벤트 제거')
        document.body.removeEventListener('scroll', debouncedMyFeedScrollHandler);
    }

}
// 디바운싱 스크롤 이벤트 핸들러
const debouncedMyFeedScrollHandler = debounce(async function(e) {

    console.log("스크롤 이벤트 시작!");
    const scrollHeight = Math.max(
        document.documentElement.scrollHeight,
        document.body.scrollHeight,
    );
    const scrollTop = Math.max(document.documentElement.scrollTop, document.body.scrollTop);
    const clientHeight = document.documentElement.clientHeight;

    // 스크롤이 최하단부로 내려갔을 때만 이벤트 발생시켜야 함
    if (scrollTop + clientHeight + 200 >= scrollHeight
        && !isFetchingFeed
    ) {
        // console.log(e);
        // 서버에서 데이터를 비동기로 불러와야 함
        // 2초의 대기열이 생성되면 다음 대기열 생성까지 2초를 기다려야 함
        console.log("스크롤 이벤트 핸들러 함수 실행");
        // showSpinner();
        await new Promise(resolve => setTimeout(resolve, 700));
        const account = $myFeedBtn.dataset.myAccount;
        fetchMyFeedList(account, currentFeedPage + 1);
    }
}, 700);

// 무한 스크롤 이벤트 생성 함수
export function setupInfiniteScroll() {
    console.log("스크롤이벤트 생성 함수 실행");

    document.body.addEventListener('scroll', debouncedMyFeedScrollHandler)
}
