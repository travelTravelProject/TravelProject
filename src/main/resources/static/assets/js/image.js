// 이미지


// 미리보기 이미지 렌더링 함수
// imageBox는 미리보기 이미지가 렌더링될 DOM
export function previewImages(files, imageBox) {
    imageBox.innerHTML = ''; // 기존 미리보기 초기화
    Array.from(files).forEach((file, index) => {
        const reader = new FileReader();
        reader.onload = (e) => {
            const imgTag = `
                <div class="image-frame"> 
                    <img src="${e.target.result}" class="image-item" data-image-order="${index}" alt="이미지">
                </div>
            `;
            // img.style.width = '100px'; css 처리 필요
            // img.style.height = '100px';
            imageBox.innerHTML += imgTag;
        };
        // 파일을 Data URL로 읽기 완료하면 reader.onload 콜백함수 실행
        reader.readAsDataURL(file);
    });
}

// 이미지 input(e) 변경 시 미리보기 및 이미지 배열 생성
// dropbox : 미리보기 렌더링 될 부모 태그
export function handleFileInputChange(e, imageFiles, imageBox) {
    const newFiles = Array.from(e.target.files);
    imageFiles.push(...newFiles);
    previewImages(imageFiles, imageBox);
    e.target.value = '';
    return imageFiles;
}

// 게시글 작성, 수정(이미지 포함) FormData에 담는 함수
// data: FormData에 담아야 할 객체 (ex. title, content)
export function dataToFormData(data, imageFiles) {
    const formData = new FormData();
    for (const key in data) {
        formData.append(key, data[key]);
    }
    imageFiles.forEach((file, index)=>{
       formData.append(`images[${index}]`, file);
    });
    return formData;
}


