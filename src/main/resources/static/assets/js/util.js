
// 디바운싱 함수 정의
export function debounce(callback, wait) {
    let timeout;
    return (...args) => {
        clearTimeout(timeout);
        timeout = setTimeout(() => callback(...args), wait);
    };
}

// 등록 시간에 대한 필터 함수
export function getRelTime(createAt) {
    // 현재 시간 구하기
    const now = new Date();
    // 등록 시간 날짜타입으로 변환
    const past = new Date(createAt);
    const year = past.getFullYear();
    const month = past.getMonth()+1;
    const date = past.getDate();

    // 사이 시간 구하기 (밀리초)
    const diff = now - past;

    const seconds = Math.floor(diff / 1000);
    const minutes = Math.floor(diff / 1000 / 60);
    const hours = Math.floor(diff / 1000 / 60 / 60);
    const days = Math.floor(diff / 1000 / 60 / 60 / 24);
    // const weeks = Math.floor(diff / 1000 / 60 / 60 / 24 / 7);
    // const years = Math.floor(diff / 1000 / 60 / 60 / 24 / 365);

    if (seconds < 60) {
        return "방금 전";
    } else if (minutes < 60) {
        return `${minutes}분 전`;
    } else if (hours < 24) {
        return `${hours}시간 전`;
    } else if (days < 7) {
        return `${days}일 전`;
    } else {
        return `${year}.${month}.${date}`;
    }
}