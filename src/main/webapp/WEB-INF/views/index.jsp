<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>

    <%@ include file="include/static-head.jsp" %>
    <link rel="stylesheet" type="text/css" href="/assets/css/fullPage.css">
    <link rel="stylesheet" href="/assets/css/index.css">
    <link href='https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css' rel='stylesheet'>
    <link rel="stylesheet" href="/assets/css/weather.css">


</head>
<body style="overflow: hidden; height: 100%;" class="fp-viewing-firstPage-0">

<%@ include file="include/header.jsp" %>

<div id="main" class="scroll-container fullpage-wrapper" style="height: 100%; position: relative; touch-action: none; transform: translate3d(0px, 0px, 0px);">

<section class="section1 section fp-section active fp-completely" style="height: 843px;" data-anchor="firstPage">


  <div id="carouselExampleFade" class="carousel slide carousel-fade" data-bs-ride="carousel">
    <div class="carousel-inner">
      <div class="carousel-item active carousel-item1">
        <div class="container">
          <div class="main_banner_box">
            <h1>
              바쁜 일상을 벗어나<br/>
              온전한 휴식과 힐링이 만나는 곳
            </h1>
          </div>
          <div class="progress">
            <div class="progress-value"></div>
          </div>
        </div>
      </div>
      <div class="carousel-item carousel-item2" >
        <div class="container">
          <div class="main_banner_box">
            <h1>
              혼자보다는 둘, 둘보다는 셋<br/> 함께 떠나는 즐거운 여행.
            </h1>
          </div>
          <div class="progress">
            <div class="progress-value"></div>
          </div>
        </div>
      </div>
      <div class="carousel-item carousel-item3">
        <div class="container">
          <div class="main_banner_box">
            <!-- <h1>
              숲과 바다, 바다 공해없는<br/>
              닝겐들과 함께하는 여행 어쩌구
            </h1> -->
          </div>
          <div class="progress">
            <div class="progress-value"></div>
          </div>
        </div>
      </div>
      <div class="carousel-item carousel-item4">
        <div class="container">
          <div class="main_banner_box">
            <!-- <h1>
              숲과 바다, 바다 공해없는<br/>
              닝겐들과 함께하는 여행 어쩌구
            </h1> -->
          </div>
          <div class="progress">
            <div class="progress-value"></div>
          </div>
        </div>
      </div>
    </div>
    <button class="carousel-control-prev2" type="button" data-bs-target="#carouselExampleFade" data-bs-slide="prev">
      <span class="carousel-control-prev-icon" aria-hidden="true"></span>
      <span class="visually-hidden">Previous</span>
    </button>
    <button class="carousel-control-next2" type="button" data-bs-target="#carouselExampleFade" data-bs-slide="next">
      <span class="carousel-control-next-icon" aria-hidden="true"></span>
      <span class="visually-hidden">Next</span>
    </button>
  </div>


</section>



<section class="section3 section fp-section fp-table" id="company" style="height: 843px;" data-anchor="3rdPage">
  <div class="d-flex justify-content-between align-items-center  container">
    <div class="w-100 text-center feed_wrap">
      <div class="d-flex justify-content-center align-items-center flex-column text_box">
        <h1># With Trip 여행자 이야기</h1>
        <div class="line"></div>
        <p>보물섬 남해를 먼저 방문한 사람들의 다양하고 유익한 이야기들을 피드로 구경해보세요!  </p>
      </div>
      <div class="d-flex justify-content-between align-items-center feed_box">
        <div class="col-2 bounce">
          <div class="feed_img_box">
            <img src="../assets/img/bn1.jpg" class="img-fluid">
          </div>
          <div class="d-flex justify-content-between align-items-center feed_like_box">
            <!-- <i class="fas fa-star"></i> -->
            <i class="fas fa-heart"></i>
            <i class="fas fa-comment"></i>
            <i class="fas fa-bookmark"></i>
          </div>
          <div class="feed_text_box">
            <p>
              위드트립 친구들이랑 해운대 해수욕장!<br>
              바다뷰 장난아님..!
            </p>
          </div>
        </div>
        <div class="col-2 down_box bounce2">
          <div class="feed_img_box">
            <img src="../assets/img/bn2.jpg" class="img-fluid">
          </div>
          <div class="d-flex justify-content-between align-items-center feed_like_box">
            <!-- <i class="fas fa-star"></i> -->
            <i class="fas fa-heart"></i>
            <i class="fas fa-comment"></i>
            <i class="fas fa-bookmark"></i>
          </div>
          <div class="feed_text_box">
            <p>
              위드트립 친구들이랑 해운대 해수욕장!<br>
              바다뷰 장난아님..!
            </p>
          </div>
        </div>
        <div class="col-2 bounce">
          <div class="feed_img_box">
            <img src="../assets/img/bn6.jpg" class="img-fluid">
          </div>
          <div class="d-flex justify-content-between align-items-center feed_like_box">
            <!-- <i class="fas fa-star"></i> -->
            <i class="fas fa-heart"></i>
            <i class="fas fa-comment"></i>
            <i class="fas fa-bookmark"></i>
          </div>
          <div class="feed_text_box">
            <p>
              위드트립 친구들이랑 해운대 해수욕장!<br>
              바다뷰 장난아님..!
            </p>
          </div>
        </div>
        <div class="col-2 down_box bounce2">
          <div class="feed_img_box">
            <img src="../assets/img/bn13.jpg" class="img-fluid">
          </div>
          <div class="d-flex justify-content-between align-items-center feed_like_box">
            <!-- <i class="fas fa-star"></i> -->
            <i class="fas fa-heart"></i>
            <i class="fas fa-comment"></i>
            <i class="fas fa-bookmark"></i>
          </div>
          <div class="feed_text_box">
            <p>
              위드트립 친구들이랑 해운대 해수욕장!<br>
              바다뷰 장난아님..!
            </p>
          </div>
        </div>
      </div>
    </div>
  </div>
</section>




<section class="section4 section fp-section fp-table" id="kakao" style="height: 843px;" data-anchor="4rdPage">
	
  <div class="left_img_box">
    <img src="../assets/img/img.png" class="img-fluid">
  </div>
  <div class="right_wrap">
    <div>
      <h1>
        그래! 이게 바로 여행이지~!<br>
        지금 당장 떠나는 여행
      </h1>
      <p>
        어디로 떠날지 모르신다구요? <br>
        위드트립이 여행지 추천해드립니다
      </p>
    </div>
    <div class="travel_good_box">
      <div class="d-flex align-items-center t_box1 t_box">
        <div></div>
        <div></div>
        <div></div>
        <div></div>
      </div>
      <!-- <div class="d-flex align-items-center t_box2 t_box">
        <div></div>
        <div></div>
        <div></div>
        <div></div>
      </div> -->
    </div>
  </div>
  
</section>


<section class="section2 section fp-section fp-table" id="service" style="height: 843px;" data-anchor="secondPage">
	<div class="fp-tableCell" style="height: 843px;">
    <div class="weather_wrap">
      <div class="weather_container">
        <div class="search-box">
            <i class='bx bxs-map'></i>
            <input type="text" placeholder="목적지를 입력하세요!">
            <button class="bx bx-search"></button>
        </div>
    
        <p class="city-hide">city hide</p>
    
        <div class="weather-box">
          <div class="box">
            <div class="info-weather">
              <div class="weather">
                <img src="../assets/img/weather/cloud.png">
                <p class="temperature">0<span>°C</span></p>
                <p class="description">Broken Clouds</p>
              </div>
            </div>
          </div>
        </div>
    
        <div class="weather-details">
          <div class="humidity">
            <i class="bx bx-water"></i>
            <div class="text">
              <div class="info-humidity">
                <span>0%</span>
              </div>
              <p>습도</p>
            </div>
          </div>
    
          <div class="wind">
              <i class="bx bx-wind"></i>
              <div class="text">
                <div class="info-wind">
                  <span>0Km/h</span>
                </div>
                <p>풍속</p>
              </div>
          </div>
        </div>
    
        <div class="not-found">
          <div class="box">
            <img src="../assets/img/weather/404.png">
            <p>없는 지역입니다!</p>
          </div>
        </div>
      </div>
    </div>
	</div>
</section>



<div id="footer" class="footer section fp-auto-height footer_none fp-section fp-table" style="height: 945px;">
  <div class="fp-tableCell" style="height: 945px;">
    <div>
      <div class="container">
        <div class="d-flex justify-content-between footer_top ">
          <p class="footer_title">
            온전한 쉼과 여유를 위해 <br/>
            자연 속에 깃든 완벽한 공간
          </p>
          <div class="scroll-top">
            탑버튼
          </div>
        </div>
        <div class="footer_info">
          <ul class="d-flex">
            <li>대표 <span>김니모</span></li>
            <li>주식회사 여행 중독자</li>
            <li>주소 <span>경기도 안양시 동안구 타워팰리스 18층</span></li>
          </ul>
          <ul class="d-flex">
            <li>대표번호 <span>02-1234-5678</span></li>
            <li>이메일 <span>mail@mail.com</span></li>
            <li>팩스 <span>02-1995-0323</span></li>
          </ul>
          <p class="Copyright">Copyright ⓒ Century21 Country Club All Rights Reserved.</p>
        </div>
      </div>
    </div>
  </div>
</div>
</div>




<script src="/assets/js/fullpage.js"></script>
<script src="/assets/js/index.js"></script>
<script src="/assets/js/weather.js"></script>

</body>
</html>
