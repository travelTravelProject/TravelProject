
const $profile = document.querySelector('.profile');
const $fileInput = document.getElementById('profile-img');

$profile.addEventListener('click', e => {
   $fileInput.click();
});

// 썸네일 (클라이언트 처리)
$fileInput.addEventListener('change', e => {

    // 파일 데이터 읽기
    const fileData = $fileInput.files[0];
    console.log(fileData);

    // 첨부파일의 로우데이터를 읽는 객체 생성
    const reader = new FileReader();
    // 파일을 URL 형태로 변경
    reader.readAsDataURL(fileData);

    // 첨부파일 등록되면 이미지 세팅
    reader.onload = e => {
        const $img = document.querySelector('.profile-image');
        $img.src = reader.result;
    };
});

const $modifyButton = document.querySelector('.modify');

$modifyButton.addEventListener('click', e => {
    e.preventDefault();
    console.log('수정 클릭');
    window.location.href = '/mypage/update';
});




