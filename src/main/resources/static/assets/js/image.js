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

const carouselTemplate = `
    <div id="carouselExampleIndicators" class="carousel slide" data-bs-ride="carousel">
  <div class="carousel-indicators">
    <button type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide-to="0" class="active" aria-current="true" aria-label="Slide 1"></button>
    <button type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide-to="1" aria-label="Slide 2"></button>
    <button type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide-to="2" aria-label="Slide 3"></button>
  </div>
<!--  <div class="carousel-inner">-->
<!--    <div class="carousel-item active">-->
<!--      <img src="..." class="d-block w-100" alt="...">-->
<!--    </div>-->
  </div>
  <button class="carousel-control-prev" type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide="prev">
    <span class="carousel-control-prev-icon" aria-hidden="true"></span>
    <span class="visually-hidden">Previous</span>
  </button>
  <button class="carousel-control-next" type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide="next">
    <span class="carousel-control-next-icon" aria-hidden="true"></span>
    <span class="visually-hidden">Next</span>
  </button>
</div>

  <div id="carouselExampleIndicators" className="carousel slide" data-bs-ride="carousel">
  <div className="carousel-indicators">
    <button type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide-to="0" className="active"
            aria-current="true" aria-label="Slide 1"></button>
    <button type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide-to="1"
            aria-label="Slide 2"></button>
    <button type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide-to="2"
            aria-label="Slide 3"></button>
  </div>
  <div className="carousel-inner">
    <!-- 이미지 carousel-item -->
  </div>
  <button className="carousel-control-prev" type="button" data-bs-target="#carouselExampleIndicators"
          data-bs-slide="prev">
    <span className="carousel-control-prev-icon" aria-hidden="true"></span>
    <span className="visually-hidden">Previous</span>
  </button>
  <button className="carousel-control-next" type="button" data-bs-target="#carouselExampleIndicators"
          data-bs-slide="next">
    <span className="carousel-control-next-icon" aria-hidden="true"></span>
    <span className="visually-hidden">Next</span>
  </button>
    </div>
`;



// 이미지 태그 만드는 함수
function makeImgTag(imagePath, className) {
  const path = (imagePath && imagePath.length !== 0) ? imagePath : '/assets/img/floating.jpg';
  return `
    <div class="carousel-item">
      <img src="${path}" class="${className} d-block w-100" alt="image">
    </div>
    `;
}

// 캐러셀 DOM 에 추가할 이미지 태그를 문자열로 반환
export function renderCarousel(images, className) {
  let tag = '';
  console.log('렌더캐러셀: ', images);
  if (!images || images.length === 0) {
    // 디폴트 이미지를 추가해줘야할 듯
    tag = makeImgTag('/assets/img/floating.jpg', 'post-image');

  } else {
    // 이미지캐러셀에 img 태그 추가
    images.forEach(el => {
          tag += makeImgTag(el.imagePath, className);
        }
    );
  }
  return tag;
}

