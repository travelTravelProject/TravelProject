import { BASE_URL } from "../feed-reply.js";
import { fetchInfScrollReplies, initInfScroll } from "./feed-getReply.js";

// 댓글 삭제 비동기 요청 처리 함수
const fetchDeleteReply = async (rno) => {
    const boardId = document.getElementById('detailFeedModal').dataset.boardId;

    const res = await fetch(`${BASE_URL}/${rno}`, {
        method: 'DELETE'
    });

    if (res.status !== 200) {
        alert("삭제에 실패했습니다!");
        return; 
    }

    // fetchInfScrollReplies(1, true, boardId);
    await initInfScroll(boardId);
    window.scrollTo(0, 0); // 삭제 후 페이지 상단으로 이동
};

// 댓글 삭제 처리 이벤트 등록 함수
export function removeReplyClickEvent() {
    document.getElementById('replyData').addEventListener('click', e => {
        e.preventDefault();
        if (!e.target.matches('#replyDelBtn')) return;

        const rno = e.target.closest('#replyContent').dataset.rno;
        console.log(rno);

        // 모달 열기
        const modal = document.getElementById('deleteConfirmModal1');
        modal.style.display = "block";

        // 모달에서 삭제 확인 버튼 클릭 이벤트
        document.getElementById('confirmDeleteBtn1').onclick = async function () {
            await fetchDeleteReply(rno);
            modal.style.display = "none";
        };

        // 모달에서 삭제 취소 버튼 클릭 이벤트
        document.getElementById('cancelDeleteBtn1').onclick = function () {
            modal.style.display = "none";
        };

        // 모달에서 닫기 버튼 클릭 이벤트
        document.querySelector('.modal1 .close1').onclick = function () {
            modal.style.display = "none";
        };
    });
}
