
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

const $modifyButton = document.querySelector('.modify');

$modifyButton.addEventListener('click', e => {
    e.preventDefault();
    console.log('수정 클릭');
    window.location.href = '/mypage/update';
});


//  모달
document.addEventListener('DOMContentLoaded', (event) => {
    const modals = document.querySelectorAll('.modal');
    const openButtons = document.querySelectorAll('.modify');
    const closeButtons = document.querySelectorAll('.close');
    const saveButtons = document.querySelectorAll('.saveButton');

    openButtons.forEach(button => {
        button.onclick = function() {
            const modalId = button.getAttribute('data-modal');
            document.getElementById(modalId).style.display = "block";
        }
    });

    closeButtons.forEach(button => {
        button.onclick = function() {
            const modalId = button.getAttribute('data-modal');
            document.getElementById(modalId).style.display = "none";
        }
    });

    saveButtons.forEach(button => {
        button.onclick = function() {
            const modalId = button.getAttribute('data-modal');
            if (modalId === 'oneLinerModal') {
                const oneLinerInput = document.getElementById('oneLinerInput').value;
                document.getElementById('oneLinerText').innerText = oneLinerInput;
                addOrUpdateHiddenInput('oneLiner', oneLinerInput);
            } else if (modalId === 'mbtiModal') {
                const mbtiInput = document.getElementById('mbtiInput').value;
                document.getElementById('mbtiText').innerText = mbtiInput;
                addOrUpdateHiddenInput('mbti', mbtiInput);
            } else if (modalId === 'ratingModal') {
                const ratingInput = document.getElementById('ratingInput').value;
                document.getElementById('ratingText').innerText = ratingInput;
                addOrUpdateHiddenInput('rating', scoreInput);
            }
            document.getElementById(modalId).style.display = "none";
        }
    });

    window.onclick = function(event) {
        modals.forEach(modal => {
            if (event.target == modal) {
                modal.style.display = "none";
            }
        });
    }

    function addOrUpdateHiddenInput(name, value) {
        const form = document.getElementById('mypageForm');
        let input = form.querySelector(`input[name=${name}]`);
        if (!input) {
            input = document.createElement('input');
            input.type = 'hidden';
            input.name = name;
            form.appendChild(input);
        }
        input.value = value;
    }
});
