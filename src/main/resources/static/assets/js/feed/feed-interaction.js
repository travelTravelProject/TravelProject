import {FEED_URL} from "../feed-list.js";

// 좋아요 상태 업데이트
function updateLike($icon, likeCount, userLike) {
    // 아이콘이 포함된 hearts 요소를 찾아서 텍스트만 변경
    const heartsElement = $icon.closest('.hearts');
    let tag = `<ion-icon name="heart"></ion-icon> ${likeCount}`;
    if(userLike) {
        tag = `<ion-icon name="heart" class="liked"></ion-icon> ${likeCount}`;
    }
    heartsElement.innerHTML = tag;

    // // 스타일 업데이트
    // const newIcon = heartsElement.querySelector('ion-icon');
    // newIcon.style.color = userLike ? '#f44336' : '#666';
}
// 좋아요 비동기 요청
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

// 북마크 상태 업데이트
function updateBookmark($icon, bookmarkCount, userBookmark) {
    // 아이콘이 포함된 hearts 요소를 찾아서 텍스트만 변경
    const bookmarkElement = $icon.closest('.bookmarks');
    let tag = `<ion-icon name="bookmark"></ion-icon> ${bookmarkCount}`;
    if(userBookmark) {
        tag = `<ion-icon name="bookmark" class="bookmarked"></ion-icon> ${bookmarkCount}`;
    }
    bookmarkElement.innerHTML = tag;

    // // 스타일 업데이트
    // const newIcon = bookmarkElement.querySelector('ion-icon');
    // newIcon.style.color = userLike ? '#f44336' : '#666';
}
// 북마크 비동기 요청
export async function fetchBookmark(tag, boardId) {
    const url = FEED_URL + '/v1/bookmark/'+boardId;
    const res = await fetch(url);

    if (res.status === 403) {
        const msg = await res.text()
        console.log(msg);
        alert(msg);
        return;
    }
    const {bookmarkCount, userBookmark} = await res.json();
    updateBookmark(tag, bookmarkCount, userBookmark);

}
