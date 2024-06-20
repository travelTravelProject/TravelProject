<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>Web Study</title>
  <link rel="stylesheet" href="/assets/css/feed-list.css">
</head>
<body>

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
    <button id="createFeedBtn">새 피드 작성</button>
    <button id="editFeedBtn">피드 수정</button>
  </div>
<div class="feed-container" id="feedData">
  <div class="feed-item">
    <div class="profile-section">
      <img src="/assets/img/mimo.png" alt="Profile Picture" class="profile-pic">
      <span class="nickname">nickname123</span>
    </div>
    <div class="image-carousel">
      <img src="/assets/img/floating.jpg" alt="Post Image" class="post-image">
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

<!-- Feed 작성 모달 -->
<div id="createFeedModal" class="modal">
  <div class="modal-content">
    <span class="close">&times;</span>
    <div id="createFeedForm">
      <label for="nickname">닉네임:</label>
      <input type="text" id="nickname" name="nickname" required>
      <label for="content">내용:</label>
      <input type="text" id="content" name="nickname" required>
      <label for="postImage">이미지 업로드:</label>
      <input type="file" id="postImage" name="postImage" accept="image/*" required>
      <button type="submit" id="feed-post-Btn">게시</button>
    </div>
  </div>
</div>

<!-- Feed 수정 모달 -->
<div id="editFeedModal" class="modal">
  <div class="modal-content">
    <span class="close">&times;</span>
    <div id="editFeedForm">
      <label for="editNickname">닉네임:</label>
      <input type="text" id="editNickname" name="nickname" required>
      <label for="editContent">내용:</label>
      <input type="text" id="editContent" name="nickname" required>
      <label for="editPostImage">이미지 업로드:</label>
      <input type="file" id="editPostImage" name="postImage" accept="image/*">
      <button type="submit" id="feed-modify-Btn">수정</button>
    </div>
  </div>
</div>

<script type="module" src="/assets/js/feed-list.js"></script>
<script>
  // 모달 열고 닫는 이벤트
  document.addEventListener('DOMContentLoaded', (event) => {
    const createModal = document.getElementById("createFeedModal");
    const editModal = document.getElementById("editFeedModal");

    const createBtn = document.getElementById("createFeedBtn");
    const editBtn = document.getElementById("editFeedBtn");

    const closeButtons = document.getElementsByClassName("close");

    if (createBtn) {
      createBtn.onclick = function() {
        createModal.style.display = "block";
      };
    }

    if (editBtn) {
      editBtn.onclick = function() {
        editModal.style.display = "block";
      };
    }

    for (let i = 0; i < closeButtons.length; i++) {
      closeButtons[i].onclick = function() {
        createModal.style.display = "none";
        editModal.style.display = "none";
      };
    }

    window.onclick = function(event) {
      if (event.target == createModal) {
        createModal.style.display = "none";
      }
      if (event.target == editModal) {
        editModal.style.display = "none";
      }
    }
  });

</script>
</body>
</html>