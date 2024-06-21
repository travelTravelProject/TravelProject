import {FEED_URL} from "./feed-list.js";

const $feedPostBtn = document.getElementById('feed-post-Btn')
$feedPostBtn.addEventListener('click', e => {
    e.preventDefault();
    const $feedCreateForm = e.target.parentElement;
    // 태그들 value, 이미지 파일명 가져오기
    // fetch payload에 담아서 POST 요청

});
