const boardId = 1; // 가짜 게시글 ID, 실제로는 동적으로 설정

function loadReplies() {
    $.ajax({
        url: `/api/v1/replies/${boardId}/page/1`,
        method: 'GET',
        success: function(data) {
            const replies = data.replies;
            $('#replies').empty();
            replies.forEach(reply => {
                $('#replies').append(`
                    <div>
                        <p>${reply.replyText} - ${reply.replyWriter}</p>
                        <button onclick="showNestedReplyForm(${reply.replyId})">대댓글 작성</button>
                        <div id="nested-replies-${reply.replyId}"></div>
                    </div>
                `);
                loadNestedReplies(reply.replyId);
            });
        }
    });
}

function loadNestedReplies(parentReplyId) {
    $.ajax({
        url: `/api/v1/replies/nested/${parentReplyId}`,
        method: 'GET',
        success: function(data) {
            const nestedReplies = data;
            nestedReplies.forEach(nestedReply => {
                $(`#nested-replies-${parentReplyId}`).append(`
                    <div>
                        <p>${nestedReply.replyText} - ${nestedReply.replyWriter}</p>
                    </div>
                `);
            });
        }
    });
}

function postReply() {
    const text = $('#replyText').val();
    const author = $('#replyAuthor').val();

    $.ajax({
        url: '/api/v1/replies',
        method: 'POST',
        contentType: 'application/json',
        data: JSON.stringify({
            text: text,
            author: author,
            boardId: boardId
        }),
        success: function(data) {
            loadReplies();
        }
    });
}

function showNestedReplyForm(parentReplyId) {
    $(`#nested-replies-${parentReplyId}`).append(`
        <textarea id="nestedReplyText-${parentReplyId}" placeholder="대댓글을 입력하세요"></textarea>
        <input type="text" id="nestedReplyAuthor-${parentReplyId}" placeholder="작성자">
        <button onclick="postNestedReply(${parentReplyId})">대댓글 등록</button>
    `);
}

function postNestedReply(parentReplyId) {
    const text = $(`#nestedReplyText-${parentReplyId}`).val();
    const author = $(`#nestedReplyAuthor-${parentReplyId}`).val();

    $.ajax({
        url: '/api/v1/replies/nested',
        method: 'POST',
        contentType: 'application/json',
        data: JSON.stringify({
            text: text,
            author: author,
            boardId: boardId,
            parentReplyId: parentReplyId
        }),
        success: function(data) {
            loadNestedReplies(parentReplyId);
        }
    });
}

$(document).ready(function() {
    loadReplies();
});
