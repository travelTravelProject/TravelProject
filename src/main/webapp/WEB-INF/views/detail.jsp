<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%> <%@ taglib prefix="c"
uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <title>Insert title here</title>
  </head>
  <body>
    <div id="wrap" class="form-container" data-bno="${dto.boardId}">
      <h1>동행 댓글 테스트</h1>
      <p>내용내용</p>
      <p>Board ID: ${dto.boardId}</p>
      <iframe
        width="640"
        height="360"
        src="https://www.youtube.com/embed/phuiiNCxRMg"
        title="aespa 에스파 &#39;Supernova&#39; MV"
        frameborder="0"
        allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share"
        referrerpolicy="strict-origin-when-cross-origin"
        allowfullscreen
      ></iframe>

      <!-- 댓글 영역 -->
      <div id="replies" class="row">
        <div class="offset-md-1 col-md-10">
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

          <!-- 대댓글 쓰기 영역 -->

          <div class="card">
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
          </div>
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

    <!-- 로딩 스피너 -->
    <div class="spinner-container" id="loadingSpinner">
      <div class="spinner-border text-light" role="status">
        <span class="visually-hidden">Loading...</span>
      </div>
    </div>

    <!-- end reply reply modal -->

    <script type="module" src="/assets/js/reply.js"></script>
    </script>
  </body>
</html>
