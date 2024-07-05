// import {debounce} from "../util.js";

const $myBoardBtn = document.getElementById('my-board-btn');

$myBoardBtn.addEventListener('click', async e => {
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

});

function renderBoardList(boardList) {
    const boardContainer = document.querySelector('.board-container');
    boardContainer.innerHTML = '';

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
                                <img src="#" alt="대표이미지">
                            </div>
                        </div>
                        <div class="card-details-bot">
                            <span>${board.gender}</span>
                            <span class="lnr lnr-calendar-full"></span>
                            <span class="acc-period">&nbsp;${board.startDate} - ${board.endDate}</span>
                            <span class="view-count">${board.view}</span>
                        </div>
                    </div>
                </section>
            </div>
`;
        boardContainer.appendChild(boardElement);
    });
}


