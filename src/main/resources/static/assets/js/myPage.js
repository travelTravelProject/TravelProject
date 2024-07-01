

const $modifyButton = document.querySelector('.modify');

$modifyButton.addEventListener('click', e => {
    e.preventDefault();
    console.log('수정 클릭');
    window.location.href = '/mypage/update';
});





