import {FEED_URL} from "../feed-list.js";
import {addExistingImagesToPreview, imageFiles as importedImages} from "../image.js";
import {fetchFeedDetail} from "./feed-detail.js";
import {fetchFeedList} from "./feed-getList.js";

export function setEditModal() {
  // const {boardId, account, nickname, profileImage, content, createdAt, feedImageList} = dto;

  const nickText = document.querySelector('.feed-right-side .nickname').textContent;
  document.getElementById('ed-nickname').value = nickText;
  console.log('content디테일: ',document.querySelector('.detail-content').firstElementChild)
  const detailContent = document.querySelector('.detail-content').firstElementChild.textContent;
  document.getElementById('ed-content').value = detailContent;

  // 수정모달에 렌더링할 미리보기 컨테이너
  const $imageBox = document.getElementById('edit-preview');

  // 디테일모달에 있는 이미지 태그 가져오기
  // const detailImages = document.querySelectorAll("#detailFeedModal .post-image")
  const detailImages = Array.from(document.querySelectorAll("#detailFeedModal .post-image")).map(img => img.src);

  // 기존 이미지를 미리보기로 렌더링
  addExistingImagesToPreview(detailImages, $imageBox);


}


// 수정한 내용 api로 fetch
export async function fetchFeedModify(boardId, payload) {

  console.log('수정fetch실행중!: ', boardId+'\n수정페이로드: ',payload)

  const url = `${FEED_URL}/v1/${boardId}`;
  const res = await fetch(url, payload);
  if(!res.ok) {
    throw new Error(`HTTP error! Status: ${res.status}`);
  }
  const result = await res.json();
  console.log('수정 fetch 이후: ', result);

  const editModal = document.getElementById("editFeedModal");
  fetchFeedDetail(editModal.dataset.boardId);
  document.querySelector('#editFeedModal .close').click();
  fetchFeedList();
}
