/*
 * *
 *  @file com.maddyhome.idea.copyright.pattern.FileInfo@2f326d2c$
 *  @CopyRight (C) 2018 ZheJiangJingRui Co. Ltd.
 *  @brief JingRui Application Platform
 *  @author $name$
 *  @email yulong.yuan@jr-info.cn
 *  @date $date$
 * /
 */

var Login = function() {
    var handleAlerts = function() {
        $('body').on('click', '[data-close="alert"]', function(e) {
            $(this).parent('.alert').hide();
            $(this).closest('.note').hide();
            e.preventDefault();
        });

        $('body').on('click', '[data-close="note"]', function(e) {
            $(this).closest('.note').hide();
            e.preventDefault();
        });

        $('body').on('click', '[data-remove="note"]', function(e) {
            $(this).closest('.note').remove();
            e.preventDefault();
        });

    };
    return {
        init: function() {


            handleAlerts();
            
            $(".toTOP").on("click",function () {
                if ($('html').scrollTop()) {
                    $('html').animate({ scrollTop: 0 }, 1000);
                    return false;
                }
                $('body').animate({ scrollTop: 0 }, 1000);
                return false;
                e.defaultPrevented();
            });
            var navbarToggleFlag = true;
            $("#navbarToggle").on("touched click",function () {
                if (navbarToggleFlag) {
                    $("#nav-list").animate({left: '0'});
                    navbarToggleFlag = false;
                } else {
                    $("#nav-list").animate({left: '-1000px'});
                    navbarToggleFlag = true;
                }

            })


        }

    };

}();

jQuery(document).ready(function() {
    Login.init();
    function timeTwo(n) {
        if(n<10){
            return "0"+n;
        }else{
            return n;
        }
    }
    function timeFormat(time) {
        var date=new Date(time);
        var y = date.getFullYear();
        var month = date.getMonth()+1;
        var d= date.getDate();
        return  ""+y+"-"+(timeTwo(month))+"-"+(timeTwo(d));
    }
   $.ajax({
        url   :  _baseContext+'/fnd/notice/queryAll',
        type: "get",
        async: true,
        success: function (res) {

           var data=res.rows;
           if(data){
               data=data.reverse();
               var str="";
               var notice_length = (data.length<4)?data.length:4;
               for(var i=0;i<notice_length;i++){
                   var news=data[i];
                   var noticeId = news.noticeId;
                   var date=timeFormat(news.startDate);
                   var digest = (news.noticeDigest==null)?" ":news.noticeDigest
                   var notice_url=_baseContext+"/notice_detail.html?noticeId="+noticeId;
                   str+='<li class=""><a href='+notice_url+' class="news_a" target="_blank"><div class="news_left col-xs-3 col-sm-2 col-md-2">' +
                       '<p  class="news_order">0'+(i+1)+'</p><p class="news_time">'+date+'</p></div>' +
                       '<div class="news_right col-xs-9 col-sm-10 col-md-10">' +
                       ' <p class="news_tit">'+news.noticeTitle+' </p><p class="news_con">'+digest+'</p>' +
                       '</div></a></li>'
               }

               $(".news-list ul").html(str);
           }

        },
        error:function (error) {
            console.log(error);
        }
    });

});

