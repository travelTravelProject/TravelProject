<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
   
</head>
<body>

<style>
      
      #footer {
    position: relative;
    width: 100%;
    background-color: #302b26;
    color: #fff;
}
#footer .container{
    padding: 70px 0;
}
.footer_info ol li, 
.footer_info ul, ul li{
    
    padding: 0;
    margin: 0;
}
.footer_info ul{
    padding-bottom: 20px;
}
.footer_info ul li{
    list-style: none;
    padding-right: 20px;
}
.footer_info ul li span{
    padding-left: 10px;
    color: #c1c1c1;
}
.Copyright{
    font-size: 12px;
    color: #c1c1c1;
}
.footer_title{
    font-size: 30px;
}
.footer_top{
    padding-bottom: 35px;
}
.scroll-top{
    width: 200px;
}


</style>

<div id="footer" style="margin-top: 100px;">
    <div>
      <div class="container" style="padding: 50px 0;">
        <div class="d-flex justify-content-between footer_top ">
          <p class="footer_title">
            바쁜 일상을 벗어나 온전한 휴식과<br/>
             힐링이 만나는 곳, 위드트립
          </p>
          <div class="scroll-top">
            <img src="../assets/img/logo_white.png" class="img-fluid">
          </div>
        </div>
        <div class="footer_info">
          <ul class="d-flex">
            <li>대표자 <span>위드트립</span></li>
            <li>주식회사 위드트립</li>
            <li>주소 <span>서울 마포구 신촌로 176 중앙빌딩</span></li>
          </ul>
          <ul class="d-flex">
            <li>대표번호 <span>02-704-1711</span></li>
            <li>이메일 <span>WithTrip@WithTrip.com</span></li>
            <li>팩스 <span>02-704-1711</span></li>
          </ul>
          <p class="Copyright">Copyright ⓒ Century24 Country Club All Rights Reserved.</p>
        </div>
      </div>
    </div>
</div>
    
</body>
</html>
