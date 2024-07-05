// 이미지
export let imageFiles = [];

// 비동기 순서대로 미리보기 렌더링
function readFile(file, index, imageBox) {
  return new Promise((resolve, reject) => {
    const reader = new FileReader();
    reader.onload = (e) => {
      const imgTag = `
                <div class="image-frame"> 
                    <img src="${e.target.result}" class="image-item" data-image-order="${index}" alt="preview image">
                    <div class="delete-prev-image" data-image-order="${index}">x</div>
                </div>
            `;
      imageBox.innerHTML += imgTag;
      resolve(); // 파일 읽기가 완료되었음
    };
    reader.onerror = () => {
      reject(reader.error); // 파일 읽기 중 에러가 발생했음
    }
    // 파일을 Data URL로 읽기 완료하면 reader.onload 콜백함수 실행
    reader.readAsDataURL(file);
  });
}


// input 업로드된 파일 미리보기 렌더링 함수
// imageBox는 미리보기 이미지가 렌더링될 DOM
export async function previewImages(files, imageBox) {
  imageBox.innerHTML = ''; // 기존 미리보기 초기화
  if(!files) return; // 업로드 할 이미지가 없으면 종료

  console.log('미리보기 files: ', files);

  for (let i = 0; i < files.length; i++) {
    await readFile(files[i], i, imageBox);
  }
}

function getOriginalFileName(url) {
  const str = url.split("/");
  const setStr = str[str.length - 1].split("_");
  return setStr[setStr.length - 1];
}

// 기존 이미지를 파일로 변환하여 imageFiles에 추가하고 미리보기로 렌더링하는 함수
// images는 img 태그의 src 배열
export async function addExistingImagesToPreview(images, imageBox) {
  imageBox.innerHTML = ''; // 기존 미리보기 초기화
  imageFiles = []; // 이미지 배열 초기화

  for (let i = 0; i < images.length; i++) {
    const src = images[i];
    const res = await fetch(src);
    const blob = await res.blob();
    const file = new File([blob], getOriginalFileName(src), { type: blob.type });
    imageFiles.push(file);
    await readFile(file, i, imageBox);
  }

  console.log('기존미리보기 imageFiles: ', imageFiles)
}

// 이미지 input(e) 변경 시 미리보기 및 이미지 배열 생성
export function handleFileInputChange(e, imageList, imageBox) {
  if(imageList.length === 10) {
    document.querySelector('.typing-text').style.color='#4facfe';
  }
  console.log('image.js 타입: ', typeof e.target.files, ' / 출력: ', e.target.files[0])
  const newFiles = Array.from(e.target.files);
  imageList.push(...newFiles);
  console.log('image.js handle 이미지들 : ', imageList);
  previewImages(imageList, imageBox);
  e.target.value = '';
  return imageList;
}

// 게시글 작성, 수정(이미지 포함) FormData에 담는 함수
// data: FormData에 담아야 할 객체 (ex. {title:'', content:''})
export function dataToFormData(data, imageList) {
  const formData = new FormData();
  for (const key in data) {
    formData.append(key, data[key]);
  }
  imageList.forEach((file, index) => {
    formData.append(`images`, file);
    console.log(index, ' : ', file);
  });
  return formData;
}

// 캐러셀 템플릿에 추가
function addToCarousel(tagBtn, tagImg, identifier) {
  return `
<!--  <div id="carousel${identifier}" class="carousel slide" data-bs-ride="carousel">-->
  <div id="carousel${identifier}" class="carousel slide" data-bs-interval="false">
    <div class="carousel-indicators carousel-one">
      ${tagBtn}
    </div>
    <div class="carousel-inner">
      ${tagImg}
    </div>
    <button class="carousel-control-prev carousel-one" type="button" data-bs-target="#carousel${identifier}" data-bs-slide="prev">
      <span class="carousel-control-prev-icon" aria-hidden="true"></span>
      <span class="visually-hidden">Previous</span>
    </button>
    <button class="carousel-control-next carousel-one" type="button" data-bs-target="#carousel${identifier}" data-bs-slide="next">
      <span class="carousel-control-next-icon" aria-hidden="true"></span>
      <span class="visually-hidden">Next</span>
    </button>
  </div>
  `;
}

// 캐러셀 인디케이터 만드는 함수
function makeIndicateBtn(index, identifier) {
  const firstSet = index === 0 ? `class="active" aria-current="true"` : '';
  return `
    <button type="button" data-bs-target="#carousel${identifier}" data-bs-slide-to="${index}" aria-label="Slide ${index+1}" ${firstSet}></button>
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

// 캐러셀에 추가할 이미지 태그를 문자열로 반환하는 함수
// images: 이미지 배열
// className: img태그의 클래스
// boardId: 글번호
// delimit: 구분자 (다른 캐러셀과 구분하기 위해)
// -> carousel + 구분자 + 글번호 형태로 중복 방지
export function renderCarousel(images, className, boardId, delimit) {
  let tagImg = '';
  let tagBtn = '';
  const identifier = delimit + boardId;
  console.log('렌더캐러셀: ', images);

  if (!images || images.length === 0) {
    // 이미지가 없는 경우
    tagImg = makeImgTag('/assets/img/floating.jpg', 'post-image one-pic', 0);
    tagBtn = makeIndicateBtn(0, identifier);

  } else if(images.length === 1) {
    // 이미지 1개인 경우
    tagImg = makeImgTag(images[0].imagePath, className + ' one-pic', 0);
    tagBtn = makeIndicateBtn(0, identifier);

  } else{
    // 개별 이미지 img 태그 추가
    images.forEach((el, index) => {
          tagImg += makeImgTag(el.imagePath, className, index);
          tagBtn += makeIndicateBtn(index, identifier);
        }
    );
  }
  return addToCarousel(tagBtn, tagImg, identifier);
}

// 캐러셀에 이미지 1장이면 <,>, 인디케이터 안보이게 설정
export function setOneImgStyle() {
  document.querySelectorAll('.one-pic').forEach((el) => {
    const hiddenTags = el.closest('.carousel').querySelectorAll('.carousel-one');
    hiddenTags.forEach(el => el.style.visibility="hidden");
  })
}
// imageFiles 배열을 초기화하는 함수
export function clearImageFiles() {
  imageFiles = [];
}

// preview 삭제 후 업데이트
export function deletePreviewAndUpdate(e, $box) {
  const index = e.target.dataset.imageOrder;
  imageFiles.splice(index, 1);
  console.log('미리보기삭제이벤 imageFiles: ', imageFiles);
  previewImages(imageFiles, $box);
}