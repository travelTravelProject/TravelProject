<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>Web Study</title>
  <link rel="stylesheet" href="/assets/css/feed-list.css">
  <link rel="preconnect" href="https://fonts.googleapis.com">
  <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.8.2/css/all.min.css">
  <link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@100..900&display=swap" rel="stylesheet">
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/animate.css/4.1.1/animate.min.css"/>
<%--  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">--%>
</head>
<body>

<section id="feed-header">
  <div class="top-section">
    <!-- 검색창 영역 -->
    <div class="search">
      <form action="/feed/list" method="get">

        <select class="form-select" name="type" id="search-type">

          <option value="content">내용</option>
          <option value="writer">작성자</option>
          <option value="cw">내용+작성자</option>
        </select>

        <input type="text" class="form-control" name="keyword" value="${s.keyword}">

        <button class="btn btn-primary" type="submit">
  <%--        <i class="fas fa-search"></i>--%>
          검색
        </button>

      </form>
    </div>

  </div>

  <div class="btn-container">
    <div id="createFeedBtn"> <i class="fas fa-pen"></i> </div>
<%--    <button id="editFeedBtn">피드 수정</button>--%>
  </div>

</section>
<%-- 피드 헤더 끝 --%>
<%-- 피드 목록 시작 --%>
<section id="feed-list">
  <div class="feed-container" id="feedData">
    <div class="feed-item">
      <div class="profile-section">
        <img src="/assets/img/mimo.png" alt="Profile Picture" class="profile-pic">
        <span class="nickname">nickname123</span>
      </div>
      <div class="image-carousel">
<%--        <img src="/assets/img/floating.jpg" alt="Post Image" class="post-image">--%>
        <!-- Add more images here for carousel -->
      </div>
      <div class="content-section">
        <span>너무 더워</span>
      </div>
      <div class="interaction-section">
        <span class="comments">💬 10</span>
        <span class="hearts">❤️ 25</span>
        <span class="bookmarks">🔖 5</span>
      </div>
    </div>
  </div>
</section>
<%-- 피드 목록 끝 --%>
<!-- 피드 작성 모달 -->
<div id="createFeedModal" class="modal">
  <div class="modal-content">
    <span class="close close-modal">&times;</span>
    <div id="createFeedForm">
      <label for="cr-nickname">닉네임:</label>
      <input type="text" id="cr-nickname" name="nickname" required>
      <label for="cr-content">내용:</label>
      <input type="text" id="cr-content" name="content" required>
      <label for="postImage">이미지 업로드:</label>
      <input type="file" id="postImage" name="postImage" class="hidden" accept="image/*" required>
      <div class="dropbox" id="post-preview"></div>
      <button type="submit" id="feed-post-Btn">게시</button>
    </div>
  </div>
</div>
<!-- 피드 작성 모달 끝 -->
<!-- 피드 수정 모달 -->
<div id="editFeedModal" class="modal">
  <div class="modal-content">
    <span class="close close-modal">&times;</span>
    <div id="editFeedForm">
      <label for="ed-nickname">닉네임</label>
      <input type="text" id="ed-nickname" name="nickname" required>
      <label for="ed-content">내용</label>
      <input type="text" id="ed-content" name="nickname" required>
      <label for="editPostImage" class="fake-upload">+ 이미지 업로드</label>
      <input type="file" id="editPostImage" name="postImage" class="hidden" accept="image/*">
      <div class="dropbox" id="edit-preview"></div>
      <button type="submit" id="feed-modify-Btn">수정 완료</button>
    </div>
  </div>
</div>
<!-- 피드 수정 모달 끝 -->
<!-- 피드 상세조회 모달 -->
<div id="detailFeedModal" class="detail-modal">
  <div class="detail-modal-content">
    <div class="feed-left-side">
      <div class="image-carousel">
        <img src="/assets/img/floating.jpg" alt="Post Image" class="post-image">
        <!-- Add more images here for carousel -->
      </div>
    </div>
    <div class="feed-right-side">
      <div class="profile-section">
        <div class="profile-row">
          <img src="/assets/img/mimo.png" alt="Profile Picture" class="profile-pic">
          <div class="profile-column">
            <span class="nickname">nickname</span>
            <span class="created-at">createdAt</span>
          </div>
        </div>
        <div class="profile-row row2">
          <button class="edit-feed detail-set-btn" id="editFeedBtn">수정</button>
          <button class="delete-feed detail-set-btn" id="deleteFeedBtn">삭제</button>
        </div>
      </div>
      <div class="detail-content">
<%--        <h2>Title</h2>--%>
        <p>Some description or content goes here...</p>
      </div>
      <!-- 댓글 영역 -->

      <div id="replies" class="row">
        <div class="offset-md-1 col-md-10">
        

        <!--댓글 내용 영역-->
        <div class="card">
            <!-- 댓글 내용 헤더 -->
            <div class="card-header text-white m-0" style="background: #343a40">
            <div class="float-left">댓글 (<span id="replyCnt">0</span>)</div>
            </div>

            <!-- 댓글 내용 바디 -->
            <div id="replyCollapse" class="card">
            <div id="replyData">
                <!--
                < JS로 댓글 정보 DIV삽입 >
                -->
            </div>
            </div>

            <!-- 대댓글 내용 바디 -->
            <div id="nestedReplyCollapse" class="card">
                <div id="nestedReplyData">
                <!-- 대댓글 목록이 여기에 렌더링 됩니다 -->
                </div>
            </div>
        </div>
        <!-- 댓글 쓰기 영역 -->
        <div class="card">
          <div class="card-body">
          <div class="row">
              <div class="col-md-9">
              <div class="form-group">
                  <label for="newReplyText" hidden>댓글 내용</label>
                  <textarea
                  rows="3"
                  id="newReplyText"
                  name="replyText"
                  class="form-control"
                  placeholder="댓글을 입력해주세요."
                  ></textarea>
              </div>
              </div>
              <div class="col-md-3">
              <div class="form-group">
                  <label for="newReplyWriter" hidden>댓글 작성자</label>
                  <input
                  id="newReplyWriter"
                  name="replyWriter"
                  type="text"
                  class="form-control"
                  placeholder="작성자 이름"
                  style="margin-bottom: 6px"
                  />
                  <button
                  id="replyAddBtn"
                  type="button"
                  class="btn btn-dark form-control"
                  >
                  등록
                  </button>
              </div>
              </div>
          </div>
          </div>
      </div>
      <!-- end reply write -->
        
        <!-- 대댓글 쓰기 영역 -->

        <!-- <div class="card">
            <div class="card-body">
            <div class="row">
                <div class="col-md-9">
                <div class="form-group">
                    <label for="newNestedReplyText" hidden>대댓글 내용</label>
                    <textarea
                    rows="3"
                    id="newNestedReplyText"
                    name="nestedReplyText"
                    class="form-control"
                    placeholder="대댓글을 입력해주세요."
                    ></textarea>
                </div>
                </div>
                <div class="col-md-3">
                <div class="form-group">
                    <label for="newNestedReplyWriter" hidden
                    >대댓글 작성자</label
                    >
                    <input
                    id="newNestedReplyWriter"
                    name="nestedReplyWriter"
                    type="text"
                    class="form-control"
                    placeholder="작성자 이름"
                    style="margin-bottom: 6px"
                    />
                    <button
                    id="nestedReplyAddBtn"
                    type="button"
                    class="btn btn-dark form-control"
                    >
                    등록
                    </button>
                </div>
                </div>
            </div>
            </div>
        </div> -->
        <!-- end nested reply write -->

        <!-- 댓글 페이징 영역 -->
        <ul class="pagination justify-content-center">
            <!--
            < JS로 댓글 페이징 DIV삽입 >
            -->
        </ul>
        </div>
    </div>
    <!-- end reply content -->
    </div>
    <!-- end replies row -->
     
    <div class="spinner-container" id="loadingSpinner">
      <div class="spinner-border text-light" role="status">
          <span class="visually-hidden">Loading...</span>
      </div>  
      </div>


    </div>
    <span class="close">&times;</span>
  </div>
</div>
<%-- 피드 상세조회 모달 끝 --%>
<%-- 삭제 확인 모달 --%>
<div id="deleteFeedModal" class="confirm-modal">
  <div class="modal-content confirm">
    <span class="close close-modal">&times;</span>
    <p>정말 삭제하시겠습니까?</p>
    <div class="modal-buttons">
      <button id="confirmDeleteBtn" class="confirm-btn">삭제</button>
      <button id="cancelDeleteBtn" class="cancel-btn">취소</button>
    </div>
  </div>
</div>
<%-- 삭제 확인 모달 끝 --%>


<%-- 스크립트 --%>
<script type="module" src="/assets/js/feed-list.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/ScrollMagic/2.0.8/ScrollMagic.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
<script type="module" src="/assets/js/feed-reply.js"></script>
</body>
</html>