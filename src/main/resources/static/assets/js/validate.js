// // 서버에 중복확인 비동기 요청
export const checkAvailability = async (type, keyword) => {
    const response = await fetch(`http://localhost:8181/check?type=${type}&keyword=${keyword}`);
    const flag = await response.json();
    return !flag;
};

// 정규표현식 패턴
// 아이디 패턴: 영문 대소문자와 숫자, 4~14글자
const accountPattern = /^[a-zA-Z0-9]{4,14}$/;

// 비밀번호 패턴: 반드시 영문, 숫자, 특수문자를 포함하여 8자 이상
const passwordPattern = /^(?=.*[a-zA-Z])(?=.*\d)(?=.*[!@#$%^&*?_~])[A-Za-z\d!@#$%^&*?_~]{8,}$/;

// 이름 패턴: 한글만 허용
const namePattern = /^[가-힣]+$/;

// 이메일 패턴: 기본적인 이메일 형식
const emailPattern = /^[A-Za-z0-9_\.\-]+@[A-Za-z0-9\-]+\.[A-Za-z0-9\-]+/;

// 닉네임 패턴: 최대 8글자, 영문 대소문자, 숫자, 밑줄(_), 하이픈(-)을 허용
const nicknamePattern = /^[a-zA-Z0-9가-힣_-]{1,8}$/;

// 생년월일 패턴: YYYY-MM-DD 형식의 날짜
const birthdatePattern = /^\d{4}-\d{2}-\d{2}$/;

// 성별 패턴: M OR F
// const genderPattern = /^[MF]$/i;

// validateInput
export const validateInput = {

    // 아이디 유효성 검사 함수
    account: async (value) => {
        // 빈 값 검사
        if (!value.trim()) return {valid: false, message: '아이디는 필수입니다.'};
        // 정규표현식 검사
        if(!accountPattern.test(value)) return {valid: false, message: '아이디는 4~14글자의 영문,숫자로 입력하세요.'};
        // 중복 검사
        const isAvailable = await checkAvailability('account', value);
        return isAvailable ? {valid: true} : {valid: false, message: '아이디가 중복되었습니다.'};
    },
    // 비밀번호 유효성 검사 함수
    password: (value) => {
        // 빈 값 검사
        if (!value.trim()) return {valid: false, message: '비밀번호는 필수입니다.'};
        // 정규표현식 검사
        if(!passwordPattern.test(value)) return {valid: false, message: '비밀번호는 영문, 숫자, 특수문자를 포함하여 8자 이상이어야 합니다.'};

        return {valid: true} ;
    },
    // 비밀번호 확인 유효성 검사 함수
    passwordCheck: (value, password) => {
        // 빈 값 검사
        if (!value.trim()) return {valid: false, message: '비밀번호 확인은 필수입니다.'};
        // 일치 여부
        if(value !== password) return {valid: false, message: '비밀번호가 일치하지 않습니다.'};

        return {valid: true} ;
    },
    // 이름 유효성 검사 함수
    name: (value) => {
        // 빈 값 검사
        if (!value.trim()) return {valid: false, message: '이름은 필수입니다.'};
        // 정규표현식 검사
        if(!namePattern.test(value)) return {valid: false, message: '이름은 한글로 입력해주세요.'};

        return {valid: true} ;
    },
    // 이메일 유효성 검사 함수
    email: async (value) => {
        // 빈 값 검사
        if (!value.trim()) return {valid: false, message: '이메일은 필수입니다.'};
        // 정규표현식 검사
        if(!emailPattern.test(value)) return {valid: false, message: '이메일 형식에 맞게 입력해주세요.'};
        // 중복 검사
        const isAvailable = await checkAvailability('email', value);
        return isAvailable ? {valid: true} : {valid: false, message: '이메일이 중복되었습니다.'};
    },
    // 닉네임 유효성 검사 함수
    nickname: (value) => {
        // 빈 값 검사
        if (!value.trim()) return {valid: false, message: '닉네임은 필수입니다.'};
        // 정규표현식 검사
        if(!nicknamePattern.test(value)) return {valid: false, message: '닉네임은 최대 8글자로 입력해주세요. (영문 대소문자, 숫자, 밑줄(_), 하이픈(-) 허용)'};

        return {valid: true} ;
    },
    // 생년월일 유효성 검사 함수
    birthday: (value) => {

        // 빈 값 검사
        if (!value.trim()) return {valid: false, message: '생년월일은 필수입니다.'};
        // 정규표현식 검사
        if(!birthdatePattern.test(value)) return {valid: false, message: '생년월일은 YYYY-MM-DD 형식의 날짜로 입력해주세요.'};

        // 날짜 유효성 검사 (예: 윤년 등)
        const parts = value.split('-');
        const year = parseInt(parts[0], 10);
        const month = parseInt(parts[1], 10) - 1; // JavaScript에서 월은 0부터 시작
        const day = parseInt(parts[2], 10);
        const date = new Date(year, month, day);

        if (
            date.getFullYear() !== year ||
            date.getMonth() !== month ||
            date.getDate() !== day
        ) {
            return { valid: false, message: '올바른 날짜를 입력해주세요.' };
        }

        return {valid: true} ;
    },
    // 성별 유효성 검사 함수
    // gender: (value) => {
    //     // 빈 값 검사
    //     if (!value.trim()) return {valid: false, message: '성별은 필수입니다.'};
    //     // 정규표현식 검사
    //     if(!genderPattern.test(value)) return {valid: false, message: '성별은 M 또는 F로 입력해주세요.'};
    //
    //     return {valid: true} ;
    // }
};


