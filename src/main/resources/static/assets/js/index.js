var sts = 0;
		if($(window).width()<200){ sts=1;}
		else{sts = 0;}
		
		$(document).ready(function(){
			if(sts === 0){
			
			$("#main").fullpage({
				anchors: ['firstPage', 'secondPage', '3rdPage','4rdPage','5rdPage'],
				menu: '#menu',
				navigation:true,
				navigationPosition: 'right',
				navigationTooltips: ['메인1', '메인2','메인3','메인4','푸터'],
				afterResize: function(width, height){}
			});
			
				}
				
			else{
				$("#dwb_Mobile_Menu>ul>li").eq(0).children('a').attr('href','?page_id=#service');
				$("#dwb_Mobile_Menu>ul>li").eq(1).children('a').attr('href','?page_id=#company');
				$("#dwb_Mobile_Menu>ul>li").eq(2).children('a').attr('href','?page_id=#kakao');
				$("#dwb_Mobile_Menu>ul>li").eq(5).children('a').attr('href','?page_id=#car');
				$("#dwb_Mobile_Menu>ul>li").eq(6).children('a').attr('href','?page_id=#option');
			}
			
		});












    $(document).ready(function(){ 
      var num = 1;
      var slider = $('#carouselExampleFade').carousel({interval:3500, pause: 'false'});
        
      slider.on('slide.bs.carousel', function(from) {
        //console.log('slide.bs.carousel');
        console.log(from);
        num = num+1;
        
        $('.progress-value').css('width', '0px');
      });
      
      $('#carouselExampleFade .stop').on('click',function(){
        //console.log('#dwb_Carousel_main .play');
        slider.carousel('pause');
        clearInterval(slide_progress_bar);
        $('.progress-value').css('width', '0px');
      });
      
      $('#carouselExampleFade .play').on('click',function(){
        //console.log('dwb_Carousel_main');
        slider.carousel();
        
        slide_progress_bar = setInterval(function() {
          var value = Number($('.progress-value').css('width').replace('px', ''));  
          //console.log('loading '+ value);
          value += 1;
          $('.progress-value').css('width', value+'px');
        }, time);
      });
    }); 
      
    var progressbar = $('.progress'),
      max = progressbar.css('width').replace('px', ''),
      time = 100 / max;
    
    var slide_progress_bar = setInterval(function() {
      var value = Number($('.progress-value').css('width').replace('px', ''));  
      //console.log('loading '+ value);
      value += 1.6;
      $('.progress-value').css('width', value+'px');
    }, time);
      
      
          























































    



  


    





    



    





    



    





    



    





    