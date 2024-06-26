// 이미지
export let imageFiles = [];

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
  imageFiles.forEach((file, index) => {
    formData.append(`images`, file);
    console.log(index, ' : ', file);
  });
  return formData;
}

// 캐러셀 템플릿에 추가
function addToCarousel(tagBtn, tagImg, boardId) {
  return `
  <div id="carousel${boardId}" class="carousel slide" data-bs-ride="carousel">
    <div class="carousel-indicators carousel-one">
      ${tagBtn}
    </div>
    <div class="carousel-inner">
      ${tagImg}
    </div>
    <button class="carousel-control-prev carousel-one" type="button" data-bs-target="#carousel${boardId}" data-bs-slide="prev">
      <span class="carousel-control-prev-icon" aria-hidden="true"></span>
      <span class="visually-hidden">Previous</span>
    </button>
    <button class="carousel-control-next carousel-one" type="button" data-bs-target="#carousel${boardId}" data-bs-slide="next">
      <span class="carousel-control-next-icon" aria-hidden="true"></span>
      <span class="visually-hidden">Next</span>
    </button>
  </div>
  `;
}

// 캐러셀 인디케이터 만드는 함수
function makeIndicateBtn(index, boardId) {
  const firstSet = index === 0 ? 'class="active" aria-current="true"' : '';
  return `
    <button type="button" data-bs-target="#carousel${boardId}" data-bs-slide-to="${index}" aria-label="Slide ${index+1}" ${firstSet}></button>
  `;
}

// 캐러셀 이미지 태그 만드는 함수
function makeImgTag(imagePath, className, index) {
  const path = (imagePath && imagePath.length !== 0) ? imagePath : '/assets/img/floating.jpg';
  return `
    <div class="carousel-item ${index === 0 ? 'active':''}">
      <img src="${path}" class="${className} d-block w-100" alt="image">
    </div>
    `;
}

// 캐러셀에 추가할 이미지 태그를 문자열로 반환
export function renderCarousel(images, className, boardId) {
  let tagImg = '';
  let tagBtn = '';
  console.log('렌더캐러셀: ', images);
  if (!images || images.length === 0) {
    // 디폴트 이미지를 추가해줘야할 듯
    tagImg = makeImgTag('/assets/img/floating.jpg', 'post-image', 0);
    tagBtn = makeIndicateBtn(0, boardId);
  } else {
    // 개별 이미지 img 태그 추가
    images.forEach((el, index) => {
          tagImg += makeImgTag(el.imagePath, className, index);
          tagBtn += makeIndicateBtn(index, boardId);
        }
    );
  }
  return addToCarousel(tagBtn, tagImg, boardId);
}

// 캐러셀에 이미지 1장이면 <,>, 인디케이터 안보이게 설정
export function setOneImgStyle() {
  const oneImages = document.querySelectorAll('.carousel-one');
  oneImages.forEach(el=>el.style.visiblity="hidden");
}
