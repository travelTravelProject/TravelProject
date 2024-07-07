<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>게시글 수정</title>

    <%@ include file="../include/static-head.jsp" %>


    <!-- reset -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/reset-css@5.0.1/reset.min.css">

    <!-- fontawesome css: https://fontawesome.com -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.8.2/css/all.min.css">

    <!-- https://linearicons.com/free#cdn -->
    <link rel="stylesheet" href="https://cdn.linearicons.com/free/1.0.0/icon-font.min.css">

    <!-- bootstrap css -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">

    <!-- daterangepicker css -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/daterangepicker/daterangepicker.css">

    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f8f8f8;
            margin: 0;
            padding: 0;
        }
        #wrap {
            width: 60%;
            max-width: 800px;
            margin: 50px auto;
            background-color: #fff;
            padding: 20px;
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }
        h1 {
            margin-bottom: 20px;
            text-align: center;
        }
        label {
            font-weight: bold;
            margin-top: 10px;
            display: block;
        }
        input[type="text"], input[type="date"], textarea {
            width: 100%;
            padding: 10px;
            margin-top: 5px;
            border: 1px solid #ddd;
            border-radius: 5px;
            background-color: #f8f8f8;
            cursor: pointer;
        }
        input[type="text"]:not([readonly]), textarea {
            cursor: auto; /* 읽기 전용이 아닌 텍스트 필드는 기본 커서 사용 */
        }
        textarea {
            min-height: 100px;
        }
        .buttons {
            margin-top: 20px;
            display: flex;
            justify-content: space-between;
        }
        .buttons button {
            background-image: linear-gradient(to right, #4facfe 0%, #00f2fe 100%);
            color: #fff;
            border: none;
            padding: 10px 20px;
            border-radius: 5px;
            cursor: pointer;
        }
        .buttons button:hover {
            background-image: linear-gradient(to right, #4086d9 0%, #00c8da 100%);
        }
        .list-btn {
            background-color: #6c757d;
        }
        .list-btn:hover {
            background-color: #5a6268;
        }
        /* 사진 업로드 관련 스타일 */
        .image-box {
            position: relative;
            margin-top: 5px;
            border: 2px dashed #28cffe;
            border-radius: 5px;
            text-align: center;
            position: relative;
            width: 20%;
            aspect-ratio: 1 / 1.2;
            display: flex;
            justify-content: center;
            align-items: center;
        }
        .image-upload-btn {
            cursor: pointer;
            width: 100%;
            height: 100%;
            display: flex;
            justify-content: center;
            align-items: center;
        }
        .image-upload-btn i {
            font-size: 20px;
            color: #28cffe;
        }
        #upload-image {
            display: none;
        }
        .image-upload-btn img {
            max-width: 100%;
            max-height: 300px;
            object-fit: cover;
            border-radius: 5px;
        }
        /* 사진 제거 버튼 */
        .removeImage {
            position: absolute;
            top: -10px;
            right: -10px;
            width: 20px;
            height: 20px;
            background-color: rgba(0, 0, 0, 0.7);
            color: white;
            border-radius: 50%;
            font-size: 15px;
            cursor: pointer;
            user-select: none;

            display: none;
            align-items: center;
            justify-content: center;
        }
        /* 모달 스타일 */
        .modal {
            display: none;
            position: fixed;
            z-index: 1;
            left: 0;
            top: 0;
            width: 100%;
            height: 100%;
            overflow: auto;
            background-color: rgb(0, 0, 0);
            background-color: rgba(0, 0, 0, 0.4);
            padding-top: 60px;
        }
        .modal-content {
            background-color: #fefefe;
            margin: 5% auto;
            padding: 20px;
            border: 1px solid #888;
            width: 80%;
            max-width: 400px;
            position: relative;
        }
        .modal .close {
            position: absolute;
            top: 10px;
            right: 10px;
            color: #aaa;
            font-size: 28px;
            font-weight: bold;
        }
        .modal .close:hover,
        .modal .close:focus {
            color: black;
            text-decoration: none;
            cursor: pointer;
        }
        .modal h2 {
            margin-top: 0;
            text-align: center;
        }
        .modal .modal-buttons {
            display: flex;
            flex-wrap: wrap;
            justify-content: space-between;
        }
        .modal .modal-buttons button {
            flex: 0 0 calc(50% - 10px);
            margin: 5px;
            padding: 10px;
            background-image: linear-gradient(to right, #4facfe 0%, #00f2fe 100%);
            color: #fff;
            border: none;
            border-radius: 5px;
            cursor: pointer;
        }
        .modal .modal-buttons button:hover {
            background-image: linear-gradient(to right, #4086d9 0%, #00c8da 100%);
        }
    </style>
</head>
<body>

<div id="wrap" class="form-container">
    <h1>게시글 수정</h1>
    <form action="/acc-board/modify" method="post" enctype="multipart/form-data">
        <input type="hidden" name="boardId" value="${abm.boardId}">

        <label for="location">장소</label>
        <input type="text" id="location" name="location" value="${abm.location}" readonly required onclick="openModal()">

        <label for="dateRange">동행 기간</label>
        <input type="text" id="dateRange" name="dateRange" readonly required>

        <input type="hidden" id="startDate" name="startDate" value="${abm.startDate}">
        <input type="hidden" id="endDate" name="endDate" value="${abm.endDate}">

        <label for="title">제목</label>
        <input type="text" id="title" name="title" value="${abm.title}" required>

        <label for="content">내용</label>
        <textarea id="content" name="content" required>${abm.content}</textarea>

        <!-- 사진 첨부 관련 태그 -->
        <label>사진 첨부</label>
        <div class="image-box">
            <div class="image-upload-btn">
                <i class="fas fa-upload"></i>
            </div>
            <input type="file" id="upload-image" accept="image/*" name="postImage">
            <div class="removeImage">X</div>
        </div>

        <div class="buttons">
            <button class="list-btn" type="button" onclick="cancelModify(${abm.boardId})">취소</button>
            <button type="submit">수정하기</button>
        </div>
    </form>
</div>

<!-- 장소 선택 모달 -->
<div id="locationModal" class="modal">
    <div class="modal-content">
        <span class="close" onclick="closeModal()">&times;</span>
        <h2>장소 선택</h2>
        <div class="modal-buttons">
            <button onclick="selectLocation('제주도')">제주도</button>
            <button onclick="selectLocation('강원도')">강원도</button>
            <button onclick="selectLocation('서울')">서울</button>
            <button onclick="selectLocation('부산')">부산</button>
            <button onclick="selectLocation('경상도')">경상도</button>
            <button onclick="selectLocation('전라도')">전라도</button>
            <button onclick="selectLocation('경기도')">경기도</button>
            <button onclick="selectLocation('충청도')">충청도</button>
            <button onclick="selectLocation('인천')">인천</button>
            <button onclick="selectLocation('울릉도')">울릉도</button>
        </div>
    </div>
</div>

<!-- jQuery -->
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<!-- daterangepicker JS -->
<script src="https://cdn.jsdelivr.net/npm/moment@2.29.1/min/moment.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/daterangepicker/daterangepicker.min.js"></script>

<script>
    // 동행 기간 선택기 초기화
    $(function() {
        $('input[name="dateRange"]').daterangepicker({
            locale: {
                format: 'YYYY-MM-DD',
                separator: ' - ',
                applyLabel: '확인',
                cancelLabel: '취소',
                fromLabel: '시작일',
                toLabel: '종료일',
                customRangeLabel: '사용자 지정',
                daysOfWeek: ['일', '월', '화', '수', '목', '금', '토'],
                monthNames: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'],
                firstDay: 0
            },
            startDate: moment($('#startDate').val()), // 시작일 초기화
            endDate: moment($('#endDate').val()), // 종료일 초기화
            minDate: moment().startOf('day') // 오늘 이전 날짜 선택 불가
        }, function(start, end, label) {
            $('#startDate').val(start.format('YYYY-MM-DD'));
            $('#endDate').val(end.format('YYYY-MM-DD'));
        });
    });

    function openModal() {
        document.getElementById('locationModal').style.display = 'block';
    }

    function closeModal() {
        document.getElementById('locationModal').style.display = 'none';
    }

    function selectLocation(location) {
        document.getElementById('location').value = location;
        closeModal();
    }

    // 취소버튼 클릭 시 돌아갈 url 주소
    function cancelModify(boardId) {
        window.location.href = '/acc-board/detail?bno=' + boardId;
    }

    // 사진 업로드 관련 스크립트
    const $upload = document.querySelector('.image-upload-btn');
    // 실제 사진 업로드 될 input
    const $uploadImage = document.getElementById('upload-image');
    // 삭제 버튼
    const $removeImage = document.querySelector('.removeImage');

    $upload.addEventListener('click', e => {
        $uploadImage.click();
    });

    // 사진 선택 시 미리보기 보여주기
    $uploadImage.addEventListener('change', e => {
        if (e.target.classList.contains('removeImage')) {
            return;
        } else {
            // 첨부한 파일 데이터 읽기
            const fileData = $uploadImage.files[0];
            console.log(fileData);
            // 첨부파일 이미지의 로우데이터(바이트)를 읽는 객체 생성
            const reader = new FileReader();
            // 파일 데이터 읽어 img 태그 src 속성에 넣기 위해 url 형태로 변경
            reader.readAsDataURL(fileData);
            // 첨부파일이 등록되는 순간 img 태그에 이미지를 세팅
            reader.onloadend = e => {
                // 1. img태그 없으면 img태그 먼저 만들기
                // let $isImg = $uploadImage.querySelector('img');
                if ($uploadImage.querySelector('img') === null) {
                    document.querySelector('.image-upload-btn').appendChild(document.createElement('img'));
                }
                const $img = document.querySelector('.image-upload-btn img')
                $img.src = reader.result;

                // i태그 숨기기, 보더라인 숨기기, 삭제버튼 보이기
                document.querySelector('.image-upload-btn i').style.display = 'none';
                document.querySelector('.image-box').style.border = 'none';
                $removeImage.style.display = 'flex';
            }
        }
    });

    // 사진 삭제버튼 클릭 이벤트
    $removeImage.addEventListener('click', e => {
        console.log('삭제버튼 클릭!');
        // 파일 입력 초기화
        $uploadImage.value = "";
        // 미리보기 이미지 제거
        const $img = document.querySelector('.image-upload-btn img');
        if ($img) {
            $img.remove();
        }
        // i태그 보이기, 보더라인 보이기, 삭제버튼 숨기기
        document.querySelector('.image-upload-btn i').style.display = 'block';
        document.querySelector('.image-box').style.border = '2px dashed #33B897';
        $removeImage.style.display = 'none';
    });
</script>

</body>
</html>
