// import {debounce} from "../util.js";

const $myBoardBtn = document.getElementById('my-board-btn');

async function boardList () {
    const account = $myBoardBtn.dataset.myAccount;
    console.log('마이페이지 동행 클릭!');
    console.log('account: ', account);

    try {
        const response = await fetch(`http://localhost:8181/mypage/boards/${account}`);
        console.log('response: ', response);
        if (!response.ok) {
            throw new Error('Network response was not ok ' + response.statusText);
        }

        const data = await response.json();
        console.log('게시글 목록: ', data);

        // 데이터 렌더링
        renderBoardList(data);
    } catch (error) {
        console.error('There was a problem with the fetch operation:', error);
    }
}

$myBoardBtn.addEventListener('click', async e => {
    boardList();
});


function renderBoardList(boardList) {
    const boardContainer = document.querySelector('.board-container');
    boardContainer.innerHTML = '';

    if (boardList.length === 0) {
        const noBoardMessage = document.createElement('div');
        noBoardMessage.className = 'no-board-message';
        noBoardMessage.textContent = '작성한 게시글이 없습니다.';
        boardContainer.appendChild(noBoardMessage);
        return;
    }

    boardList.forEach(board => {
        const boardElement = document.createElement('div');
        boardElement.className = 'board';

        boardElement.innerHTML = `
     <div class="card-wrapper">
                <section class="card-post" data-bno="${board.boardId}">
                    <div class="card-content-wrapper">
                        <div class="card-details-top">
                            <div class="card-text">
                                <div class="card-title">${board.shortTitle}</div>
                                <div class="card-content">${board.shortContent}</div>
                            </div>
                            <div class="card-img"> 
                            <img src="${board.imagePath != null ? board.imagePath : '/assets/img/accBoardDefaultImg.webp'}" alt="대표이미지" >
                           
                            </div>
                        </div>
                        <div class="card-details-bot">
                            <span>${board.gender}</span>
<!--                            <span class="lnr lnr-calendar-full"></span>-->
                            <span class="acc-period">&nbsp;${board.startDate} - ${board.endDate}</span>
                            <span class="view-count"> 조회수 ${board.view}</span>
                        </div>
                    </div>
                </section>
            </div>
          
`;
        boardContainer.appendChild(boardElement);
    });

}

const $boardTab = document.getElementById('my-board-tab');
$boardTab.addEventListener('click', e => {
    console.log('게시글 클릭!');

    const boardElement = e.target.closest('.card-post');

    if (boardElement) {
        // 클릭된 게시글의 데이터 속성에서 게시글 ID를 가져옴
        const boardId = boardElement.dataset.bno;

        if (boardId) {
            console.log('게시글 클릭! 게시글 ID:', boardId);

            // 게시글로 넘어가는 동작을 수행하는 로직을 추가
            // 예를 들어, 페이지 이동
            window.location.href = `/acc-board/detail?bno=${boardId}`;
        } else {
            console.log('게시글 ID를 찾을 수 없습니다.');
        }
    }
});
// openBoardTab(account);
// const $boardTab = document.getElementById('my-board-tab');
// const $FeedTab = document.getElementById('my-feed-tab');
// $boardTab.classList.add('active-tab');
// $FeedTab.classList.remove('active-tab-btn');
// $FeedTab.classList.add('inactive-tab');
// $myBoardBtn.classList.add('active-tab-btn');
// $FeedTab.classList.remove('inactive-tab');

export {boardList, renderBoardList} ;


