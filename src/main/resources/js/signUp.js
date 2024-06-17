// 유효성 검증 함수 import
import { validateInput } from "./validate.js";
import { debounce } from "./util.js";

// form과 회원가입 버튼 요소를 가져옴
const form = document.getElementById('signUpForm');
const signupButton = document.getElementById('signup-btn');

// 각 필드에 대한 정보 배열(id, 유효성 검증 함수, 에러 메시지 표시 요소, 초기 유효 상태)
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
    { id: 'user_gender', validator: validateInput.gender, errorElement: 'genderChk', valid: false},
];

// 버튼 상태 업데이트
const updateButtonState = () => {

    // 모든 valid 가 true인지 확인
    const isFormValid = fields.every(fields => fields.valid);

    if(isFormValid) {
        signupButton.disabled = false;
        signupButton.style.backgroundColor = 'orangered';
    } else {
        signupButton.disabled = true;
        signupButton.style.backgroundColor = 'gray';
    }
};

