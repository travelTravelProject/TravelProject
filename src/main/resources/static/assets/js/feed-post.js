import {FEED_URL} from "./feed-list.js";
import {previewImages, handleFileInputChange} from "./image.js";

// 모달 작성 완료 버튼 클릭 시 이벤트
const $feedPostBtn = document.getElementById('feed-post-Btn')
const $imageInput = document.getElementById('postImage');
const $imageBox = document.querySelector('.dropbox');

$feedPostBtn.addEventListener('click', e => {
    e.preventDefault();

    // 태그들 value, 이미지 파일명 가져오기
    // 로그인한 회원 정보 가져오기
    const $createContent = document.getElementById('cr-content');
    const $createImages = document.querySelector('#createFeedModal ')

    // fetch payload에 담아서 POST 요청

});

$imageInput.addEventListener('change', e => {
    const files = e.target.files;
    previewImages(files, $imageBox);
});

const getImageFile = e => {
    const files = e.target.files; // 유사배열객체
    const reader = new FileReader();

    setImageFile(Array.from(files)); // 이미지 파일 자체

    reader.readAsDataURL(file);
    reader.onloadend = () => {
        setPreviewFile(reader.result); // 미리보기
    };
};
// 미리보기 확인 후 fetch
// const fetch