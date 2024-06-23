import {handleFileInputChange} from "../image.js";
import {fetchFeedPost} from "./feed-post.js";

export function initFeedFormEvents() {
    const $feedPostBtn = document.getElementById('feed-post-Btn');
    const $imageInput = document.getElementById('postImage');
    const $imageBox = document.querySelector('.dropbox');
    let imageFiles = [];

    // 이미지 input 변경 시 발생 이벤트
    $imageInput.addEventListener('change', e => {
        imageFiles = handleFileInputChange(e, imageFiles, $imageBox);
    });

    // 모달 작성 완료 버튼 클릭 시 이벤트
    $feedPostBtn.addEventListener('click', e => {
        e.preventDefault();

        // 태그들 value, 이미지 파일명 가져오기
        const $createContent = document.getElementById('cr-content').value;

        if (!$createContent || imageFiles.length === 0) {
            alert('모든 필드를 채워주세요.');
            return;
        }

        // fetch payload에 담아서 POST 요청
        const formData = dataToFormData({content: $createContent}, imageFiles);
        const payload = {
            method: 'POST',
            body: formData
        };

        fetchFeedPost(payload);
    });
}
