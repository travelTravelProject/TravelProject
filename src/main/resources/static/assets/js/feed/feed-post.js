import {FEED_URL} from "../feed-list.js";
import {dataToFormData, handleFileInputChange,  imageFiles as importedImageFiles } from "../image.js";

const $feedPostBtn = document.getElementById('feed-post-Btn')
const $imageInput = document.getElementById('postImage');
const $imageBox = document.querySelector('.dropbox');
let imageFiles = importedImageFiles; // 다른 변수에 할당하여 재할당 가능

// 미리보기 확인 후 fetch
export const fetchFeedPost = async (payload) => {
    console.log("fetchFeedPost 실행!")
    try {
        const res
            = await fetch(FEED_URL + '/v1/list', payload);
        if (res.ok) {
            const result = await res.json();

            console.log('fetch 이후: ', result)

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
        console.log('fetchFeedPost 에러: ', e);
    }
};



