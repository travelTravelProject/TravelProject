
const $profile = document.querySelector('.profile');
const $fileInput = document.getElementById('profile-img');

$profile.addEventListener('click', e => {
   $fileInput.click();
});

// 썸네일
$fileInput.addEventListener('change', e => {

    const fileData = $fileInput.files[0];
    console.log(fileData);

    const reader = new FileReader();

    reader.readAsDataURL(fileData);

    reader.onload = e => {
        const $img = document.querySelector('.profile-image');
        $img.src = reader.result;
    };
});

const $updateButton = document.querySelector('.update');

$updateButton.addEventListener('click', e => {
    e.preventDefault();
    console.log('수정 클릭');
    window.location.href = '/mypage/update';
});