import {fetchFeedList} from "./feed/feed-getList.js";
import {initFeedFormEvents} from "./feed/feed-event.js";

console.log('feed-list.js 실행');
//===== 전역 변수 =====
export const FEED_URL = 'http://localhost:8181/feed';

//===== 피드 이벤트 ====
document.addEventListener('DOMContentLoaded', () => {
    initFeedFormEvents();
});


//===== 실행 코드 =====

// 피드 목록 서버에서 불러오기
fetchFeedList(); // 초기 피드 - 1페이지





