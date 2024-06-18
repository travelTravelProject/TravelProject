// // 유효성 검증 함수 import
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

// 입력 필드 유효성 검사
const validateField = async (field) => {
    const $input = document.getElementById(field.id);
    const $errorSpan = document.getElementById(field.errorElement);
    const isValid = await field.validator($input.value);

    if(isValid.valid) {
        $input.style.borderColor = 'blue';
        $errorSpan.innerHTML = '<b class="success">사용가능합니다.</b>';
        field.valid = true;
    } else {
        $input.style.borderColor = 'red';
        $errorSpan.innerHTML = '<b class="warning">${isValid.message}</b>';
        field.valid = false;
    }
    updateButtonState();
};

// 필드 이벤트
fields.forEach(field => {
    const $input = document.getElementById(field.id);
    $input.addEventListener('keyup', debounce(() => {
        validateField(field);
        if(field.id === 'password') {
            const passwordCheckField = fields.find(f => f.id === 'password_check');
            validateField(passwordCheckField);
        }
    }, 500));
});


// 회원가입 버튼 클릭 이벤트
signupButton.addEventListener('click', e => {
    const isFormValid = fields.every(result => result.valid);

    if(isFormValid) {
        form.submit();
    } else {
        alert('입력란을 확인하세요.')
    }
});

// 페이지 로드 시 초기 버튼 상태 업데이트
updateButtonState();



// GPT
// document.addEventListener('DOMContentLoaded', () => {
//     const userIdInput = document.getElementById('user_id');
//     const emailInput = document.getElementById('user_email');
//     const passwordInput = document.getElementById('password');
//     const passwordCheckInput = document.getElementById('password_check');
//     const signupBtn = document.getElementById('signup-btn');
//     const idChkSpan = document.getElementById('idChk');
//     const emailChkSpan = document.getElementById('emailChk');
//     const pwChkSpan = document.getElementById('pwChk');
//     const pwChk2Span = document.getElementById('pwChk2');
//
//     let isUserIdValid = false;
//     let isEmailValid = false;
//     let isPasswordValid = false;
//     let isPasswordMatch = false;
//
//     userIdInput.addEventListener('blur', () => {
//         checkAvailability('account', userIdInput.value, idChkSpan)
//             .then(result => isUserIdValid = result);
//     });
//
//     emailInput.addEventListener('blur', () => {
//         checkAvailability('email', emailInput.value, emailChkSpan)
//             .then(result => isEmailValid = result);
//     });
//
//     passwordInput.addEventListener('blur', () => {
//         isPasswordValid = validatePassword(passwordInput.value, pwChkSpan);
//     });
//
//     passwordCheckInput.addEventListener('blur', () => {
//         isPasswordMatch = validatePasswordMatch(passwordInput.value, passwordCheckInput.value, pwChk2Span);
//     });
//
//     signupBtn.addEventListener('click', (event) => {
//         event.preventDefault();  // Prevent the form from submitting immediately
//         if (validateForm()) {
//             document.getElementById('signUpForm').submit();
//         } else {
//             alert('Please fix the errors in the form before submitting.');
//         }
//     });
//
//     async function checkAvailability(type, keyword, span) {
//         if (!keyword) {
//             span.innerText = '';
//             return false;
//         }
//
//         try {
//             const response = await fetch(`/check?type=${type}&keyword=${keyword}`);
//             const data = await response.json();
//             span.innerText = data ? `사용 가능한 ${type === 'account' ? '아이디' : '이메일'}입니다.` : `이미 사용 중인 ${type === 'account' ? '아이디' : '이메일'}입니다.`;
//             span.style.color = data ? 'green' : 'red';
//             return data;
//         } catch (error) {
//             console.error('Error:', error);
//             span.innerText = '오류가 발생했습니다. 나중에 다시 시도하세요.';
//             span.style.color = 'red';
//             return false;
//         }
//     }
//
//     function validatePassword(password, span) {
//         const regex = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{8,}$/;
//         const isValid = regex.test(password);
//         span.innerText = isValid ? '비밀번호가 유효합니다.' : '비밀번호는 최소 8자이며, 영문과 특수문자를 포함해야 합니다.';
//         span.style.color = isValid ? 'green' : 'red';
//         return isValid;
//     }
//
//     function validatePasswordMatch(password, passwordCheck, span) {
//         const isMatch = password === passwordCheck;
//         span.innerText = isMatch ? '비밀번호가 일치합니다.' : '비밀번호가 일치하지 않습니다.';
//         span.style.color = isMatch ? 'green' : 'red';
//         return isMatch;
//     }
//
//     function validateForm() {
//         // Trigger all validations to ensure the latest state
//         isPasswordValid = validatePassword(passwordInput.value, pwChkSpan);
//         isPasswordMatch = validatePasswordMatch(passwordInput.value, passwordCheckInput.value, pwChk2Span);
//
//         return isUserIdValid && isEmailValid && isPasswordValid && isPasswordMatch;
//     }
// });