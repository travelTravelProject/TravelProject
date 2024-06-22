import {FEED_URL} from "./feed-list.js";
import {dataToFormData, handleFileInputChange} from "./image.js";

const $feedPostBtn = document.getElementById('feed-post-Btn')
const $imageInput = document.getElementById('postImage');
const $imageBox = document.querySelector('.dropbox');
let imageFiles = [];

// 이미지 input 변경 시 발생 이벤트
$imageInput.addEventListener('change', e => {
    imageFiles = handleFileInputChange(e, imageFiles, $imageBox);
});

// 미리보기 확인 후 fetch
const fetchFeedPost = async (payload) => {
    try {
        const res
            = await fetch(FEED_URL + '/list', payload);
        if (res.ok) {
            const result = await res.json();
            alert('게시글이 성공적으로 등록되었습니다.');
            document.getElementById('createFeedModal').style.display = 'none';
            // formData = new FormData(); // FormData 초기화
            imageFiles = []; // 파일 리스트 초기화
            document.getElementById('createFeedForm').reset();
            $imageBox.innerHTML = '';
        } else {
            throw new Error('서버 응답이 올바르지 않습니다.');
        }
    } catch (e) {
        console.log('fetchFeedPost 에러: ', error);
    }
};

// 모달 작성 완료 버튼 클릭 시 이벤트
$feedPostBtn.addEventListener('click', e => {
    e.preventDefault();

    // 태그들 value, 이미지 파일명 가져오기
    // 로그인한 회원 정보 가져오기
    const feedContent = document.getElementById('cr-content').value;

    // 입력값 누락 있는지 확인 필요

    // fetch payload에 담아서 POST 요청
    const payload = {
        method: 'POST',
        body: dataToFormData({content}, imageFiles)
    };

    fetchFeedPost(payload);


});
