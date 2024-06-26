import {dataToFormData, handleFileInputChange, imageFiles} from "../image.js";
import {fetchFeedPost} from "./feed-post.js";
import {fetchFeedDetail} from "./feed-detail.js";
import {setEditModal} from "./feed-modify.js";

export function initFeedFormEvents() {
    const $feedPostBtn = document.getElementById('feed-post-Btn');
    const $imageInputPost = document.getElementById('postImage');
    const $imageInputEdit = document.getElementById('editPostImage');
    const $imageBoxPost = document.getElementById('post-preview');
    const $imageBoxEdit = document.getElementById('edit-preview');
    let imageFiles = [];

    // 모달 및 모달 닫기 버튼 처리
    document.addEventListener('click', function(e) {
        const createModal = document.getElementById("createFeedModal");
        const editModal = document.getElementById("editFeedModal");
        const detailModal = document.getElementById("detailFeedModal");

        // 모달 열기 버튼 처리
        if (e.target.id === "createFeedBtn") {
            createModal.style.display = "block";

        } else if (e.target.id === "editFeedBtn") {
            editModal.style.display = "block";
            const boardId = e.target.closest('.detail-modal').dataset.boardId;
            editModal.setAttribute("data-board-id", boardId );
            // fetchFeedDetail(boardId, 'edit');
            setEditModal(); // 수정모달 초기값 렌더링

        } else if (e.target.classList.contains("show-detail")) {
            detailModal.style.display = "block";
            console.log('글번호', e.target.closest('.feed-item').dataset.feedId);
            const boardId = e.target.closest('.feed-item').dataset.feedId;
            detailModal.setAttribute("data-board-id", boardId);
            fetchFeedDetail(boardId, 'detail');
        }


        // 모달 닫기 버튼 처리
        if (e.target.classList.contains("close")
          || e.target === createModal
          || e.target === editModal
          || e.target === detailModal) {
            createModal.style.display = "none";
            editModal.style.display = "none";
            detailModal.style.display = "none";
        }
    });

    // 이미지 input 변경 시 발생 이벤트
    $imageInputPost.addEventListener('change', e => {
        imageFiles = handleFileInputChange(e, imageFiles, $imageBoxPost);
    });
    $imageInputEdit.addEventListener('change', e => {
        console.log("edit 모달 이벤트 실행!")
        imageFiles = handleFileInputChange(e, imageFiles, $imageBoxEdit);
    });

    // 모달 작성 완료 버튼 클릭 시 이벤트
    $feedPostBtn.addEventListener('click', async (e) => {
        e.preventDefault();

        // 태그들 value, 이미지 파일명 가져오기
        const $createContent = document.getElementById('cr-content').value;

        // if (!$createContent || imageFiles.length === 0) {
        //     alert('모든 필드를 채워주세요.');
        //     return;
        // }

        // fetch payload에 담아서 POST 요청
        const data = {
            content: $createContent,
            account: 'tester1',
        }
        const formData = dataToFormData(data, imageFiles);
        const payload = {
            method: 'POST',
            body: formData
        };

        await fetchFeedPost(payload);
    });
}
