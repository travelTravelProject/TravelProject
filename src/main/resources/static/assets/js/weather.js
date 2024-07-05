const container = document.querySelector('.weather_container');
const search = document.querySelector('.search-box button');
const weatherBox = document.querySelector('.weather-box');
const weatherDetails = document.querySelector('.weather-details');
const error404 = document.querySelector('.not-found');
const cityHide = document.querySelector('.city-hide');
const searchInput = document.querySelector('.search-box input');

// 한글 도시 이름을 영어로 변환하는 매핑 테이블
const cityMap = {
  '서울': 'Seoul',
  '부산': 'Busan',
  '인천': 'Incheon',
  '대구': 'Daegu',
  '대전': 'Daejeon',
  '광주': 'Gwangju',
  '수원': 'Suwon',
  '울산': 'Ulsan',
  '창원': 'Changwon',
  '고양': 'Goyang',
  '용인': 'Yongin',
  '성남': 'Seongnam',
  '부천': 'Bucheon',
  '청주': 'Cheongju',
  '화성': 'Hwaseong',
  '남양주': 'Namyangju',
  '전주': 'Jeonju',
  '천안': 'Cheonan',
  '안산': 'Ansan',
  '안양': 'Anyang',
  '김해': 'Gimhae',
  '평택': 'Pyeongtaek',
  '포항': 'Pohang',
  '제주도': 'Jeju',
  '의정부': 'Uijeongbu',
  '김포': 'Gimpo',
  '파주': 'Paju',
  '구미': 'Gumi',
  '진주': 'Jinju',
  '원주': 'Wonju',
  '경산': 'Gyeongsan',
  '아산': 'Asan',
  '익산': 'Iksan',
  '경주': 'Gyeongju',
  '거제': 'Geoje',
  '양산': 'Yangsan',
  '광명': 'Gwangmyeong',
  '충주': 'Chungju',
  '김천': 'Gimcheon',
  '강릉': 'Gangneung',
  '군포': 'Gunpo',
  '시흥': 'Siheung',
  '나주': 'Naju',
  '오산': 'Osan',
  '구리': 'Guri',
  '순천': 'Suncheon',
  '공주': 'Gongju',
  '여수': 'Yeosu',
  '목포': 'Mokpo'
};

// 날씨 설명을 변환하는 매핑 테이블
const weatherDescriptionMap = {
  '온흐림': '흐림',
  '실 비': '비'
};

// 공통 함수
function searchWeather() {
  const APIKey = 'b9c9490506000c14868645e006622c97';
  let city = searchInput.value.trim();

  if (city === '') {
    return;
  }

  // 한글 도시 이름을 영어로 변환
  if (cityMap[city]) {
    city = cityMap[city];
  }

  const encodedCity = encodeURIComponent(city);

  fetch(`https://api.openweathermap.org/data/2.5/weather?q=${encodedCity}&appid=${APIKey}&units=metric&lang=kr`)
  .then(response => response.json()).then(json => {
  
    if (json.cod === '404') {
      cityHide.textContent = city;
      container.style.height = '404px';
      weatherBox.classList.remove('active');
      weatherDetails.classList.remove('active');
      error404.classList.add('active');
      return;
    }

    const image = document.querySelector('.weather-box img');
    const temperature = document.querySelector('.weather-box .temperature');
    const description = document.querySelector('.weather-box .description');
    const humidity = document.querySelector('.weather-details .humidity span');
    const wind = document.querySelector('.weather-details .wind span');

    if (cityHide.textContent === city) {
      return;
    } else {
      cityHide.textContent = city;

      container.style.height = '555px';
      container.classList.add('active');
      weatherBox.classList.add('active');
      weatherDetails.classList.add('active');
      error404.classList.remove('active');

      setTimeout(() => {
        container.classList.remove('active');
      }, 2500);

      // 날씨 설명 변환
      let weatherDescription = json.weather[0].description;
      if (weatherDescriptionMap[weatherDescription]) {
        weatherDescription = weatherDescriptionMap[weatherDescription];
      }

      switch (json.weather[0].main) {
        case 'Clear':
          image.src = '../assets/img/weather/clear.png';
          break;
          
        case 'Rain':
          image.src = '../assets/img/weather/rain.png';
          break;
  
        case 'Snow':
          image.src = '../assets/img/weather/snow.png';
          break;
  
        case 'Clouds':
          image.src = '../assets/img/weather/cloud.png';
          break;
  
        case 'Mist':
          image.src = '../assets/img/weather/mist.png';
          break;
  
        case 'Haze':
          image.src = '../assets/img/weather/mist.png';
          break;
      
        default:
          image.src = '../assets/img/weather/cloud.png';
      }
  
      temperature.innerHTML = `${parseInt(json.main.temp)}<span>°C</span>`;
      description.innerHTML = weatherDescription;
      humidity.innerHTML = `${json.main.humidity}%`;
      wind.innerHTML = `${parseInt(json.wind.speed)}Km/h`;

      const infoWeather = document.querySelector('.info-weather');
      const infoHumidity = document.querySelector('.info-humidity');
      const infoWind = document.querySelector('.info-wind');

      const elCloneInfoWeather = infoWeather.cloneNode(true);
      const elCloneInfoHumidity = infoHumidity.cloneNode(true);
      const elCloneInfoWind = infoWind.cloneNode(true);
      
      elCloneInfoWeather.id = 'clone-info-weather';
      elCloneInfoWeather.classList.add('active-clone');

      elCloneInfoHumidity.id = 'clone-info-humidity';
      elCloneInfoHumidity.classList.add('active-clone');
      
      elCloneInfoWind.id = 'clone-info-wind';
      elCloneInfoWind.classList.add('active-clone');

      setTimeout(() => {
        infoWeather.insertAdjacentElement("afterend", elCloneInfoWeather);
        infoHumidity.insertAdjacentElement("afterend", elCloneInfoHumidity);
        infoWind.insertAdjacentElement("afterend", elCloneInfoWind);
      }, 2200);

      const cloneInfoWeather = document.querySelectorAll('.info-weather.active-clone');
      const totalCloneInfoWeather = cloneInfoWeather.length;
      const cloneInfoWeatherFirst = cloneInfoWeather[0];

      const cloneInfoHumidity = document.querySelectorAll('.info-humidity.active-clone');
      const cloneInfoHumidityFirst = cloneInfoHumidity[0];

      const cloneInfoWind = document.querySelectorAll('.info-wind.active-clone');
      const cloneInfoWindFirst = cloneInfoWind[0];

      if (totalCloneInfoWeather > 0 ) {
        cloneInfoWeatherFirst.classList.remove('active-clone');
        cloneInfoHumidityFirst.classList.remove('active-clone');
        cloneInfoWindFirst.classList.remove('active-clone');

        setTimeout(() => {
          cloneInfoWeatherFirst.remove();
          cloneInfoHumidityFirst.remove();
          cloneInfoWindFirst.remove();
        }, 2200);
      }
    }

  });
}

// 버튼 클릭 이벤트
search.addEventListener('click', searchWeather);

// 엔터 키 입력 이벤트
searchInput.addEventListener('keydown', (event) => {
  if (event.key === 'Enter') {
    searchWeather();
  }
});
