import {
    clearImageFiles,
    dataToFormData,
    deletePreviewAndUpdate,
    handleFileInputChange,
    imageFiles as importedImages
} from "../image.js";
import {fetchFeedPost, resetPostModal} from "./feed-post.js";
import {fetchFeedDetail} from "./feed-detail.js";

import {fetchFeedModify, setEditModal} from "./feed-modify.js";
import {fetchFeedList, getSearchKeyword, getSortSelected} from "./feed-getList.js";
import {fetchFeedDelete} from "./feed-delete.js";
import {fetchBookmark, fetchLike} from "./feed-interaction.js";

import {initInfScroll} from "../feed-reply/feed-getReply.js";
import { fetchReplyPost } from "../feed-reply/feed-postReply.js";
import {modifyReplyClickEvent, isEditModeActive, fetchReplyModify} from "../feed-reply/feed-modifyReply.js";
import {removeReplyClickEvent} from "../feed-reply/feed-deleteReply.js";

// 대댓글 작성시 boardId를 전달하기 위해 설정한 전역변수
let currentBoardId = null;


export function initFeedFormEvents() {
    const $feedPostBtn = document.getElementById('feed-post-Btn');
    const $feedEditBtn = document.getElementById('feed-modify-Btn');
    const $feedDeleteBtn = document.getElementById('confirmDeleteBtn');
    const $feedDelCancelBtn = document.getElementById('cancelDeleteBtn');
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
            currentBoardId = boardId;
            fetchFeedDetail(boardId);
            initInfScroll(boardId);
            
            
          
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
                detailModal.style.display = "none";
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
                resetPostModal(); // 피드 작성 모달 입력사항 초기화
                clearImageFiles(); // 모달이 닫힐 때 imageFiles 초기화
            }
        }

    });

    // 이미지 input 변경 시 발생 이벤트
    $imageInputPost.addEventListener('change', e => {
        console.log('post imageFiles: ', imageFiles);
        console.log('post 이미지 전: ', importedImages);
        const $span = document.querySelector('#createFeedModal .stop-msg');
        $span.classList.add('hidden');

        const $imgMsg = document.querySelector('#createFeedModal .img-msg');

        if(imageFiles.length === 10 || importedImages.length === 10) {
            $imgMsg.classList.add('warning');
            return;
        } else {
            if($imgMsg.classList.contains('warning')) {
                $imgMsg.classList.remove('warning');
            }
        }

        imageFiles = handleFileInputChange(e, importedImages, $imageBoxPost);

        if(imageFiles.length === 0) {
            $imgMsg.classList.add('warning');
        }
    });
    $imageInputEdit.addEventListener('change', e => {
        console.log("edit 모달 이미지 추가 이벤트 실행!")
        const $span = document.querySelector('#editFeedModal .stop-msg');
        $span.classList.add('hidden');
        const $imgMsg = document.querySelector('#editFeedModal .img-msg');

        if(imageFiles.length === 10 || importedImages.length === 10) {
            $imgMsg.classList.add('warning');
            return;
        } else {
            if($imgMsg.classList.contains('warning')) {
                $imgMsg.classList.remove('warning');
            }
        }
        imageFiles = handleFileInputChange(e, importedImages, $imageBoxEdit);
        if(imageFiles.length === 0) {
            $imgMsg.classList.add('warning');
        }
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
        const $span = document.querySelector('#createFeedModal .stop-msg');

        if (!createContent || importedImages.length === 0) {
            $span.classList.remove('hidden');
            // alert('모든 필드를 채워주세요.');
            return;
        } else {
            $span.classList.add('hidden');
        }
        if(createContent.length >= 50) {
            alert('피드 분문은 최대 50자까지 입력 가능합니다.');
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
        const $span = document.querySelector('#editFeedModal .stop-msg');

        if (!editContent || importedImages.length === 0) {
            $span.classList.remove('hidden');
            // alert('모든 필드를 채워주세요.');
            return;
        } else {
            $span.classList.add('hidden');
        }
        if(editContent.length >= 50) {
            alert('피드 분문은 최대 50자까지 입력 가능합니다.');
            return;
        }

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

    // 스크롤 최상단으로 이동 버튼
    const topBtn = document.getElementById('goTopBtn');
    topBtn.addEventListener('click', e => {
        e.preventDefault();
        document.body.scrollTo({top: 0, behavior: 'smooth'});
    })
    window.addEventListener('scroll', () => {
        if(document.body.scrollTop > 200 || document.documentElement.scrollTop > 200) {
            topBtn.style.display = 'block';
        } else {
            topBtn.style.display = 'none';
        }
    });

    // 스크롤 최하단으로 이동 버튼
    const bottomBtn = document.getElementById('goBottomBtn');
    bottomBtn.addEventListener('click', e => {
        e.preventDefault();
        document.body.scrollTop = document.body.scrollHeight;
    })

    // 좋아요 버튼, 북마크 버튼 클릭 시 상태 업데이트
    document.addEventListener('click', e => {
        const $heartSpan = e.target.closest('.hearts');
        // 클릭된 요소가 .hearts이거나, .hearts의 자식인 경우 처리
        if ($heartSpan) {
            const boardId = $heartSpan.closest('.feed-item').dataset.feedId;
            console.log('좋아요 이벤트 실행! : ', boardId);
            fetchLike($heartSpan.firstElementChild, boardId);
        }

    })
    document.addEventListener('click', e=> {
        const $bookmarkSpan = e.target.closest('.bookmarks');
        if($bookmarkSpan) {
            const boardId = $bookmarkSpan.closest('.feed-item').dataset.feedId;
            fetchBookmark($bookmarkSpan.firstElementChild, boardId);
        }
    })

    // 텍스트 입력 제한 이벤트
    const $textareaPost = document.getElementById('cr-content');
    const $textareaEdit = document.getElementById('ed-content');
    $textareaPost.addEventListener('keydown', e => {
        const text = $textareaPost.value;
        const length = $textareaPost.value.length;
       const $typingCnt = document.querySelector('#createFeedModal .typing-count');

        const $span = document.querySelector('#createFeedModal .stop-msg');
        $span.classList.add('hidden');

        if(length > 50 || e.target.clientHeight !== e.target.scrollHeight) {
            alert('피드 본문은 최대 4줄 또는 50자까지 입력 가능합니다.');
            length < 50 ? $textareaPost.value = text.substring(length-1, 1)
              : $textareaPost.value = text.substring(0, 50);
       }
       $typingCnt.textContent = $textareaPost.value.length.toString();
    });

    $textareaEdit.addEventListener('keydown', e => {
        const text = $textareaEdit.value;
        const length = text.length;
        const $typingCnt = document.querySelector('#editFeedModal .typing-count');

        const $span = document.querySelector('#editFeedModal .stop-msg');
        $span.classList.add('hidden');

        $typingCnt.textContent = length.toString();
        if(length > 50 || e.target.clientHeight !== e.target.scrollHeight) {
            alert('피드 본문은 최대 4줄 또는 50자까지 입력 가능합니다.');
            length < 50 ? $textareaEdit.value = text.substring(length-1, 1)
            : $textareaEdit.value = text.substring(0, 50);
        }
        $typingCnt.textContent = $textareaEdit.value.length.toString();
    });
    function validateTextarea(tag, className) {
        const text = tag.value;
        const length = text.length;
        const $typingCnt = document.querySelector(className);
        $typingCnt.textContent = length.toString();
        if(length > 50 || tag.clientHeight !== tag.scrollHeight) {
            alert('피드 본문은 최대 4줄 또는 50자까지 입력 가능합니다.');
            length < 50 ? tag.value = text.substring(length-1, 1)
              : tag.value = text.substring(0, 50);
        }
        $typingCnt.textContent = $textareaEdit.value.length.toString();
    }
    // 검색
    document.querySelector('.search input[name="keyword"]').addEventListener('keydown', function(e) {
        if (e.key === 'Enter') {
            e.preventDefault();
            fetchFeedList();
        }
    });
    // 정렬
    document.getElementById('filters-box').addEventListener('click', e => {
        const $latest = document.getElementById('filter-latest');
        const $popular = document.getElementById('filter-pop');
        if(e.target.matches('#filter-latest')) {
            $latest.classList.add('active-filter');
            $popular.classList.remove('active-filter');
        } else if (e.target.matches('#filter-pop')) {
            $latest.classList.remove('active-filter');
            $popular.classList.add('active-filter');
        }
        fetchFeedList();
    })

    // 댓글 관련 이벤트

    document.getElementById('replyAddBtn').onclick = async () => {
        if (isEditModeActive()) {
          // 수정 모드일 때
          await fetchReplyModify();
        } else {
          // 일반 모드일 때
          await fetchReplyPost();
        }
    };

    
    const newReplyText = document.getElementById('newReplyText');
    const replyAddBtn = document.getElementById('replyAddBtn');
    
    const toggleReplyAddBtnVisibility = () => {
      if (newReplyText.value.trim() !== "") {
        replyAddBtn.style.display = "block"; // Show button
      } else {
        replyAddBtn.style.display = "none"; // Hide button
      }
    };
    
    newReplyText.addEventListener('input', toggleReplyAddBtnVisibility);
    
    newReplyText.addEventListener('keydown', async e => {
      if (e.key === 'Enter') {
        if (newReplyText.value.trim() !== "") {
          if (isEditModeActive()) {
            // 수정 모드일 때
            await fetchReplyModify();
          } else {
            // 일반 모드일 때
            await fetchReplyPost();
          }
          newReplyText.value = "";
          replyAddBtn.style.display = "none";
        }
      }
    });
    
    replyAddBtn.style.display = "none";
    

    modifyReplyClickEvent();
    removeReplyClickEvent();
}
