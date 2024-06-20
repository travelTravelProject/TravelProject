import {fetchFeedList} from "./feed-getList";

console.log('feed-list.js 실행');
//===== 전역 변수 =====
export const FEED_URL = 'http://localhost:8181/feed';

// 기존 검색 조건 option 태그 고정하기
function fixSearchOption() {
    // 1. 방금 전 어떤 조건으로 검색했는지 값을 알아옴
    const type = '${s.type}';
    // console.log('type: '+type);

    // 2. 해당 조건을 가진 option 태그를 검색
    const $option = document.querySelector(`#search-type option[value='\${type}']`);

    // 3. 해당 태그에 selected 속성 부여
    $option?.setAttribute('selected', 'selected');

}

//===== 실행 코드 =====

// 피드 목록 서버에서 불러오기
fetchFeedList(); // 초기 피드 - 1페이지

fixSearchOption();



