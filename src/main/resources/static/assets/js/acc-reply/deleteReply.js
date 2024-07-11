import { BASE_URL } from "../acc-reply.js";
import { initInfScroll } from "./getReply.js";

let deleteRno = null; // 삭제할 댓글의 ID를 저장하는 변수

// 모달 요소들 가져오기
const modal = document.getElementById("deleteConfirmModal");
const closeModalBtn = document.querySelector(".modal .close");
const confirmDeleteBtn = document.getElementById("confirmDeleteBtn");
const cancelDeleteBtn = document.getElementById("cancelDeleteBtn");

// 모달 열기 함수
function openModal(rno) {
    deleteRno = rno;
    modal.style.display = "block";
}

// 모달 닫기 함수
function closeModal() {
    deleteRno = null;
    modal.style.display = "none";
}

// 모달 닫기 이벤트
closeModalBtn.addEventListener("click", closeModal);
cancelDeleteBtn.addEventListener("click", closeModal);

// 댓글 삭제 비동기 요청 처리 함수
const fetchDeleteReply = async (rno) => {
    const res = await fetch(`${BASE_URL}/${rno}`, {
        method: 'DELETE'
    });

    if (res.status !== 200) {
        alert("삭제에 실패했습니다!")
        return; 
    }

    await initInfScroll();
    window.scrollTo(0, 0); // 삭제 후 페이지 상단으로 이동
    closeModal(); // 삭제 후 모달 닫기
};

// 댓글 삭제 확인 버튼 클릭 이벤트
confirmDeleteBtn.addEventListener("click", () => {
    if (deleteRno) {
        fetchDeleteReply(deleteRno);
    }
});

// 댓글 삭제 처리 이벤트 등록 함수
export function removeReplyClickEvent() {
    document.getElementById('replyData').addEventListener('click', e => {
        e.preventDefault();
        if (!e.target.matches('#replyDelBtn')) return;

        const rno = e.target.closest('#replyContent').dataset.rno;
        openModal(rno); // 모달 열기
    });
}
