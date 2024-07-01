import {
    clearImageFiles,
    dataToFormData, deletePreviewAndUpdate,
    handleFileInputChange,
    imageFiles as importedImages,
    previewImages
} from "../image.js";
import {fetchFeedPost, resetPostModal} from "./feed-post.js";
import {fetchFeedDetail} from "./feed-detail.js";

import {fetchFeedModify, setEditModal} from "./feed-modify.js";
import {fetchFeedList} from "./feed-getList.js";
import {fetchFeedDelete} from "./feed-delete.js";

import { fetchInfScrollReplies, state } from "../feed-reply/feed-getReply.js";
import {fetchLike} from "./feed-interaction.js";
// import { fetchReplyPost } from "../feed-reply/feed-postReply.js";
// import { isEditModeActive, fetchReplyModify } from "../feed-reply/feed-modifyReply.js";

export function initFeedFormEvents() {
    const $feedPostBtn = document.getElementById('feed-post-Btn');
    const $feedEditBtn = document.getElementById('feed-modify-Btn');
    const $feedDeleteBtn = document.getElementById('confirmDeleteBtn');
    const $imageInputPost = document.getElementById('postImage');
    const $imageInputEdit = document.getElementById('editPostImage');
    const $imageBoxPost = document.getElementById('post-preview');
    const $imageBoxEdit = document.getElementById('edit-preview');
    const $replyAddBtn = document.getElementById('replyAddBtn'); // 댓글 등록 버튼

    let imageFiles = [];

    const createModal = document.getElementById("createFeedModal");
    const editModal = document.getElementById("editFeedModal");
    const detailModal = document.getElementById("detailFeedModal");
    const deleteModal = document.getElementById('deleteFeedModal')

    // 모달 및 모달 닫기 버튼 처리
    document.addEventListener('click', function(e) {

        // 모달 열기 버튼 처리
        if (e.target.id === "createFeedBtn" || e.target.closest('#createFeedBtn')) {
            const tag = e.target.id === 'createFeedBtn' ? e.target : e.target.closest('#createFeedBtn');
            tag.dataset.feedUser
                ? createModal.style.display = "block"
                : alert("피드 작성 시 로그인이 필요합니다.");

        } else if (e.target.classList.contains("show-detail") || e.target.closest('.show-detail')) { // 더보기, 피드목록 댓글 클릭시 디테일 모달 열기
            detailModal.style.display = "block";
            console.log('글번호', e.target.closest('.feed-item').dataset.feedId);
            const boardId = e.target.closest('.feed-item').dataset.feedId;
            detailModal.setAttribute("data-board-id", boardId);
            fetchFeedDetail(boardId);
            fetchInfScrollReplies(1, true, boardId); // 모달이 열릴 때 댓글 fetch
          
        } else if (e.target.id === "editFeedBtn") { // 디테일 모달의 수정 버튼
            editModal.style.display = "block";
            const boardId = e.target.closest('.detail-modal').dataset.boardId;
            editModal.setAttribute("data-board-id", boardId );
            setEditModal(); // 수정모달 초기값 렌더링

        } else if (e.target.id === "deleteFeedBtn") { // 디테일 모달의 삭제 버튼
            deleteModal.style.display = "block";
            const boardId = e.target.closest('.detail-modal').dataset.boardId;
            deleteModal.setAttribute("data-board-id", boardId);
        }


        // 모달 닫기 버튼 처리
        if (e.target.classList.contains("close")) {
            const modal = e.target.closest('.modal');
            const modalDetail = e.target.closest('.detail-modal');
            const modalConfirm = e.target.closest('.confirm-modal');
            if (modal) {
                resetPostModal(); // 모달 입력사항 초기화
                clearImageFiles(); // 모달이 닫힐 때 imageFiles 초기화
                imageFiles = [];
                modal.style.display = "none";
            } else if(modalDetail) {
                modalDetail.style.display = "none";
                clearImageFiles();
            } else if(modalConfirm) {
                modalConfirm.style.display = "none";
            }

        }

        // 모달 백드롭 클릭 시 닫기 처리
        if (e.target.matches('#createFeedModal')
          || e.target.matches('#editFeedModal')
          || e.target.matches('#detailFeedModal')
          || e.target.matches('#deleteFeedModal')
        ) {
            if (!e.target.closest('.modal-content') && !e.target.closest('.detail-modal-content')) {
                e.target.style.display = "none";
                clearImageFiles(); // 모달이 닫힐 때 imageFiles 초기화
            }
        }

    });

    // 이미지 input 변경 시 발생 이벤트
    $imageInputPost.addEventListener('change', e => {
        console.log('post imageFiles: ', imageFiles);
        console.log('post 이미지 전: ', importedImages);
        imageFiles = handleFileInputChange(e, importedImages, $imageBoxPost);
        console.log('post 이미지 추가확인: ', imageFiles);
        console.log('post 이미지 추가후 import: ', importedImages);
    });
    $imageInputEdit.addEventListener('change', e => {
        console.log("edit 모달 이미지 추가 이벤트 실행!")
        imageFiles = handleFileInputChange(e, importedImages, $imageBoxEdit);
    });
    // 미리보기 삭제 버튼 이벤트
    $imageBoxPost.addEventListener('click', (e) => {
        if(e.target.classList.contains('delete-prev-image')) {
            deletePreviewAndUpdate(e, $imageBoxPost);
        }
    })
    $imageBoxEdit.addEventListener('click', (e) => {
        if (e.target.classList.contains('delete-prev-image')) {
            deletePreviewAndUpdate(e, $imageBoxEdit);
        }
    });

    // 모달 작성 완료 버튼 클릭 시 이벤트
    $feedPostBtn.addEventListener('click', async (e) => {
        e.preventDefault();

        // 태그들 value, 이미지 파일명 가져오기
        const createContent = document.getElementById('cr-content').value;
        // createModal
        const loginUser = createModal.dataset.feedUser

        if (!createContent || importedImages.length === 0) {
            alert('모든 필드를 채워주세요.');
            return;
        }

        // fetch payload에 담아서 POST 요청
        const data = {
            content: createContent,
            account: loginUser, // 로그인 계정 가져오기!!!
        }
        const formData = dataToFormData(data, importedImages);
        const payload = {
            method: 'POST',
            body: formData
        };

        await fetchFeedPost(payload);
    });

    // 모달 수정 완료 버튼
    $feedEditBtn.addEventListener('click', async (e) => {
        e.preventDefault();
        const boardId = document.getElementById('editFeedModal').dataset.boardId;
        const editContent = document.getElementById('ed-content').value;
        const data = {
            content: editContent,
        }
        const formData = dataToFormData(data, importedImages);
        const payload = {
            method: 'PUT',
            body: formData
        }
        await fetchFeedModify(boardId, payload);
    })
    // 모달 삭제 확인 버튼
    $feedDeleteBtn.addEventListener('click', async (e) => {
        e.preventDefault();
        const boardId = document.getElementById('deleteFeedModal').dataset.boardId;
        const payload = {
            method: 'DELETE',
            headers: {'content-type' : 'application/json'}
        }
        await fetchFeedDelete(boardId, payload)
        deleteModal.style.display = "none";
        detailModal.querySelector('.close').click();
        await fetchFeedList();
        window.scrollTo(0, 0);

    })

    // TOP 버튼
    const topBtn = document.getElementById('goTopBtn');
    window.addEventListener('scroll', () => {
        if(document.body.scrollTop > 200 || document.documentElement.scrollTop > 200) {
            topBtn.style.display = 'block';
        } else {
            topBtn.style.display = 'none';
        }
    });

    topBtn.addEventListener('click', e => {
        e.preventDefault();
        document.body.scrollTo({top: 0, behavior: 'smooth'});
    })

    // 좋아요 버튼
    document.addEventListener('click', e => {
        const $heartSpan = e.target.closest('.hearts');
        // 클릭된 요소가 .hearts이거나, .hearts의 자식인 경우 처리
        if ($heartSpan) {
            const boardId = $heartSpan.closest('.feed-item').dataset.feedId;
            console.log('좋아요 이벤트 실행! : ', boardId);
            fetchLike($heartSpan.firstElementChild, boardId);
        }
    })

    // 댓글 작성/수정 이벤트 등록 (POST)
    // $replyAddBtn.addEventListener('click', async e => {
    //     if (isEditModeActive()) {
    //       // 수정 모드일 때
    //       await fetchReplyModify();
    //     } else {
    //       // 일반 모드일 때
    //       await fetchReplyPost();
    //     }
    // });

}
