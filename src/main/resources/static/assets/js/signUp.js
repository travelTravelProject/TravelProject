import { validateInput } from "./validate.js";
import { debounce } from "./util.js";

const form = document.getElementById('signUpForm');
const signupButton = document.getElementById('signup-btn');

const fields = [
    { id: 'user_id', validator: validateInput.account, errorElement: 'idChk', valid: false},
    { id: 'password', validator: validateInput.password, errorElement: 'pwChk', valid: false},
    { id: 'password_check', validator: (value) =>
            validateInput.passwordCheck(value, document.getElementById('password').value),
        errorElement: 'pwChk2', valid: false},
    { id: 'user_name', validator: validateInput.name, errorElement: 'nameChk', valid: false},
    { id: 'user_email', validator: validateInput.email, errorElement: 'emailChk', valid: false},
    { id: 'user_nickname', validator: validateInput.nickname, errorElement: 'nicknameChk', valid: false},
    { id: 'user_birthday', validator: validateInput.birthday, errorElement: 'birthdayChk', valid: false},
];

const updateButtonState = () => {
    const isFormValid = fields.every(field => field.valid);

    if (isFormValid) {
        signupButton.disabled = false;
        signupButton.style.backgroundColor = 'blue';
    } else {
        signupButton.disabled = true;
        signupButton.style.backgroundColor = 'gray';
    }
};

const validateField = async (field) => {
    const $input = document.getElementById(field.id);
    const $errorSpan = document.getElementById(field.errorElement);
    const isValid = await field.validator($input.value);

    if (isValid.valid) {
        $input.style.borderColor = 'blue';
        $errorSpan.innerHTML = '<b class="success">[사용 가능합니다.]</b>';
        $errorSpan.style.color = 'gray';
        field.valid = true;
    } else {
        $input.style.borderColor = 'red';
        $errorSpan.innerHTML = `<b class="warning">[${isValid.message}]</b>`;
        $errorSpan.style.color = 'gray';
        field.valid = false;
    }
    console.log('생년월일: ', $input.value);
    updateButtonState();
};

fields.forEach(field => {
    const $input = document.getElementById(field.id);
    $input.addEventListener('keyup', debounce(() => {
        validateField(field);
        if (field.id === 'password') {
            const passwordCheckField = fields.find(f => f.id === 'password_check');
            validateField(passwordCheckField);
        }
    }, 1000));
});

form.addEventListener('submit', (event) => {
    fields.forEach(field => {
        if (!field.valid) {
            event.preventDefault();
            validateField(field);
        }
    });
    updateButtonState();
});

// 초기 버튼 상태로 업데이트
updateButtonState();
// 생년월일
