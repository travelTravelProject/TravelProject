import {FEED_URL} from "../feed-list.js";
// import {fetchFeedDetail} from "./feed-detail.js";
import {imageFiles as importedImages} from "../image.js";

let imageFiles = importedImages;

export function setEditModal() {
  // const {boardId, account, nickname, profileImage, content, createdAt, feedImageList} = dto;

  const nickText = document.querySelector('.feed-right-side .nickname').textContent;
  document.getElementById('editNickname').value = nickText;
  const textContent = document.querySelector('.detail-content').firstElementChild.textContent;
  document.getElementById('editContent').value = textContent;

  // 수정모달에 렌더링할 미리보기 컨테이너
  const $imageBox = document.getElementById('edit-preview');
  $imageBox.innerHTML = '';
  // 디테일모달에 있는 이미지 태그 가져오기
  const detailImages = document.querySelectorAll("#detailFeedModal .post-image")
  detailImages.forEach((el, index) => {
    imageFiles.push(el);
    const imgTag = `
      <div class="image-frame"> 
          <img src="${el.src}" class="image-item" data-image-order="${index}" alt="preview image">
      </div>
    `;
    $imageBox.innerHTML += imgTag;
  });
  // 이렇게 디비에 저장한 주소를 가져온 경우 다시 디비로 전달 시 기존 이미지는 어떻게 전달되지?
  console.log('modify preview 파일들: ', imageFiles)
}


// 수정한 내용 api로 fetch
export async function fetchFeedModify(boardId, payload) {

  const url = `${FEED_URL}/v1/${boardId}`;
  const res = await fetch(url, payload);
  if(!res.ok) {
    throw new Error(`HTTP error! Status: ${res.status}`);
  }
  const result = await res.json();
  console.log('수정 fetch 이후: ', result);

  // fetchFeedDetail(boardId); 컨트롤러에서 리다이렉트

}
