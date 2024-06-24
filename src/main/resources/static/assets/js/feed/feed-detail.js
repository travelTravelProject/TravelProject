import {FEED_URL} from "../feed-list";
import {renderCarousel} from "../image";

function updateDetailModal(dto) {
  console.log("디테일모달 업데이트 실행!");
  const {account, nickname, profileImage, content, createdAt, feedImageList} = dto;

  const $imgCarousel = document.querySelector('.image-carousel');
  $imgCarousel.innerHTML = '';

  // 프로필 사진 적용
  const $profile = document.querySelector('.feed-right-side .profile-pic');
  $profile.src = profileImage;

  // content 적용
  const $content = document.querySelector('.detail-content');
  $content.firstElementChild.textContent = content;

  // 상세조회 캐러설에 이미지 추가
  renderCarousel(feedImageList, $imgCarousel, 'post-image');

}

// 서버로 boardId 보내서 fetch로 데이터 받아와서 모달에 뿌려줌
export async function fetchFeedDetail(boardId) {

  const url = `${FEED_URL}/v1/${boardId}`
  const res = await fetch(url);
  if(!res.ok) {
    throw new Error(`HTTP error! Status: ${res.status}`);
  }
  const feedDetailDto = await res.json();
  console.log(feedDetailDto);
  updateDetailModal(feedDetailDto);
}