import {FEED_URL} from "../feed-list.js";

function updateLike($icon, likeCount, userLike) {
    // 아이콘이 포함된 hearts 요소를 찾아서 텍스트만 변경
    const heartsElement = $icon.closest('.hearts');
    let tag = `<ion-icon name="heart-outline" size="large"></ion-icon> ${likeCount}`;
    if(userLike) {
        tag = `<ion-icon name="heart" size="large"></ion-icon> ${likeCount}`;
    }
    heartsElement.innerHTML = tag;

    // 스타일 업데이트
    const newIcon = heartsElement.querySelector('ion-icon');
    newIcon.style.color = userLike ? '#f44336' : '#000';
}

export async function fetchLike(tag, boardId) {
    const url = FEED_URL + '/v1/like/'+boardId;
    const res = await fetch(url);

    if (res.status === 403) {
        const msg = await res.text()
        console.log(msg);
        alert(msg);
        return;
    }
    const {likeCount, userLike} = await res.json();
    updateLike(tag, likeCount, userLike);

}