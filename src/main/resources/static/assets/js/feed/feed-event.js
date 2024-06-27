import {
    clearImageFiles,
    dataToFormData,
    handleFileInputChange,
    imageFiles as importedImages,
    previewImages
} from "../image.js";
import {fetchFeedPost} from "./feed-post.js";
import {fetchFeedDetail} from "./feed-detail.js";
import {fetchFeedModify, setEditModal} from "./feed-modify.js";

export function initFeedFormEvents() {
    const $feedPostBtn = document.getElementById('feed-post-Btn');
    const $feedEditBtn = document.getElementById('feed-modify-Btn');
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
            setEditModal(); // 수정모달 초기값 렌더링

        } else if (e.target.classList.contains("show-detail")) {
            detailModal.style.display = "block";
            console.log('글번호', e.target.closest('.feed-item').dataset.feedId);
            const boardId = e.target.closest('.feed-item').dataset.feedId;
            detailModal.setAttribute("data-board-id", boardId);
            fetchFeedDetail(boardId);
        }


        // 모달 닫기 버튼 처리
        if (e.target.classList.contains("close")) {
            const modal = e.target.closest('.modal');
            const modalDetail = e.target.closest('.detail-modal');
            if (modal) {
                modal.style.display = "none";
                clearImageFiles(); // 모달이 닫힐 때 imageFiles 초기화
            } else if(modalDetail) {
                modalDetail.style.display = "none";
                clearImageFiles();
            }

        }

        // 모달 백드롭 클릭 시 닫기 처리
        if (e.target.matches('#createFeedModal') || e.target.matches('#editFeedModal') || e.target.matches('#detailFeedModal')) {
            if (!e.target.closest('.modal-content') && !e.target.closest('.detail-modal-content')) {
                e.target.style.display = "none";
                clearImageFiles(); // 모달이 닫힐 때 imageFiles 초기화
            }
        }

    });

    // 이미지 input 변경 시 발생 이벤트
    $imageInputPost.addEventListener('change', e => {
        imageFiles = handleFileInputChange(e, importedImages, $imageBoxPost);
    });
    $imageInputEdit.addEventListener('change', e => {
        console.log("edit 모달 이벤트 실행!")
        imageFiles = handleFileInputChange(e, importedImages, $imageBoxEdit);
    });
    // 미리보기 삭제 버튼 이벤트
    document.getElementById('edit-preview').addEventListener('click', (e) => {
        if (e.target.classList.contains('delete-prev-image')) {
            const index = e.target.dataset.imageOrder;
            imageFiles.splice(index, 1);
            previewImages(imageFiles, document.getElementById('edit-preview'));
        }
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
            account: 'tester1', // 로그인 계정 가져오기!!!
        }
        const formData = dataToFormData(data, importedImages);
        const payload = {
            method: 'POST',
            body: formData
        };

        await fetchFeedPost(payload);
    });
    $feedEditBtn.addEventListener('click', async (e) => {
        e.preventDefault();
        const boardId = document.getElementById('editFeedModal').dataset.boardId;
        const editContent = document.getElementById('ed-content').value;
        const data = {
            content: editContent,
            account: 'tester1',
        }
        const formData = dataToFormData(data, importedImages);
        const payload = {
            method: 'PUT',
            body: formData
        }
        await fetchFeedModify(boardId, payload);
    })
}
