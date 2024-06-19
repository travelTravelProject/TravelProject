
import { BASE_URL } from "./reply.js";


export function renderReplies({ replies }) {

    // 댓글 수 렌더링
    // document.getElementById('replyCnt').textContent = pageInfo.totalCount;

    // 댓글 목록 렌더링
    let tag = '';
    if (replies && replies.length > 0) {
        replies.forEach(({ replyId: rno, writer, text, createAt }) => {
            tag += `
        <div id='replyContent' class='card-body' data-reply-id='${rno}'>
            <div class='row user-block'>
                <span class='col-md-3'>
                    <b>${writer}</b>
                </span>
                <span class='offset-md-6 col-md-3 text-right'><b>${createAt}</b></span>
            </div><br>
            <div class='row'>
                <div class='col-md-9'>${text}</div>
                <div class='col-md-3 text-right'>
                    <a id='replyModBtn' class='btn btn-sm btn-outline-dark' data-bs-toggle='modal' data-bs-target='#replyModifyModal'>수정</a>&nbsp;
                    <a id='replyDelBtn' class='btn btn-sm btn-outline-dark' href='#'>삭제</a>
                </div>
            </div>
        </div>
        `;
        });


    } else {
        tag = `<div id='replyContent' class='card-body'>댓글이 아직 없습니다! ㅠㅠ</div>`;
    }

    document.getElementById('replyData').innerHTML = tag;

    // 페이지 태그 렌더링
    // renderPage(pageInfo);

}


// 서버에서 댓글 목록 가져오는 비동기 요청 함수
export async function fetchReplies() {

    const bno = document.getElementById("wrap").dataset.bno; // 게시물 글번호

    const res = await fetch(`${BASE_URL}/${bno}`);
    const replyResponse = await res.json();
    
    console.log(replyResponse);
    // 댓글 목록 렌더링
    renderReplies(replyResponse);
}


// 페이지 클릭 이벤트 생성 함수
export function replyPageClickEvent() {

    document.querySelector('.pagination').addEventListener('click', e => {
        e.preventDefault();
        // console.log(e.target.getAttribute('href'));
        fetchReplies(e.target.getAttribute('href'));
    })
}
