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
<div class="feed-container">
  <div class="feed-item">
    <div class="profile-section">
      <img src="/assets/img/mimo.png" alt="Profile Picture" class="profile-pic">
      <span class="nickname">nickname123</span>
    </div>
    <div class="image-carousel">
      <img src="/assets/img/floating.jpg" alt="Post Image" class="post-image">
      <!-- Add more images here for carousel -->
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
    <form id="createFeedForm">
      <label for="postImage">이미지 업로드:</label>
      <input type="file" id="postImage" name="postImage" accept="image/*" required>
      <label for="nickname">닉네임:</label>
      <input type="text" id="nickname" name="nickname" required>
      <button type="submit">게시</button>
    </form>
  </div>
</div>

<!-- Feed 수정 모달 -->
<div id="editFeedModal" class="modal">
  <div class="modal-content">
    <span class="close">&times;</span>
    <form id="editFeedForm">
      <label for="editPostImage">이미지 업로드:</label>
      <input type="file" id="editPostImage" name="postImage" accept="image/*">
      <label for="editNickname">닉네임:</label>
      <input type="text" id="editNickname" name="nickname" required>
      <button type="submit">수정</button>
    </form>
  </div>
</div>


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