$(document).ready(function(){
    //给左边菜单追加自定义滚动条方法
/*    $(".leftBox").mCustomScrollbar({
        scrollButtons:{
            enable:true
        }
    });*/

    //给图例追加自定义滚动条方法,需放在隐藏元素前,否则无法显示
    $(".legendBox").mCustomScrollbar({   
        scrollButtons:{  
            enable:true //是否使用上下滚动按钮  
        },          
    });
    //当屏幕在768下隐藏左侧菜单
    var _width = $(window).width(); 
     if(_width < 769){
         $(".main").addClass('hide-left-menu');
         $("#mapContent").css('left','0px');
     }
    //小屏主菜单加下拉菜单
    $(".menu-toggler").click(function() {
       $("#navbar-collapse").slideToggle();

    });
    //隐藏显示左菜单
    $(".l_icon").click(function(){
        if($(".leftBox").is(":visible")){
         $(".main").addClass('hide-left-menu');
         $("#mapContent").css('left','0px');
        }else{   
         $(".main").removeClass('hide-left-menu');
         $("#mapContent").css('left','298px');
        }
    });

    //一级菜单切换效果
    $(".bigmenu").on('click','li',function(){
        var index = $(this).index();
        $(this).addClass('active').siblings().removeClass('active');
        var $marginlefty = $('.fore-core-side').children(); 
        $marginlefty.removeClass('current').eq(index).addClass('current');
        if($(".leftBox").is(":hidden")){ 
          $(".main").removeClass('hide-left-menu');
          $("#mapContent").css('left','298px');
        };
        if($(".menu-toggler").is(":visible")){
          $("#navbar-collapse").slideToggle();
        }
    });
    
    $('.homemenu li').click(function(){
        $(this).toggleClass('active');
        $('.tab-mapCon').children('div.resizable-left').addClass('active').siblings('div.resizable-right').removeClass('active');
    })
    //左菜单切换效果
     $('.menu').on('click','a',function(){
        $(this).addClass('active').siblings().removeClass('active');
     })
    //操作按钮激活
     $('.btn_list').on('click','button',function(){
        $(this).addClass('active').siblings().removeClass('active');
     })
    //左菜单面板折叠
    $('.panelBox-heading').click(function(){
        $(this).next().slideToggle();
        $(this).children('span').toggleClass('arrow_unfold');
    })

    //操作按钮激活
     $('.btn_list').on('click','button',function(){
        $(this).addClass('active').siblings().removeClass('active');
     })
     
    //图层悬浮点击选中效果
    $('.maplayer li a').click(function(){
        $(this).toggleClass('select');
    });


    //隐藏显示右下角图例以及调整鹰眼的显示位置
    $(".legendBox").hide();
    $("#js_legend").on('click','.legendIcon',function(){
        if($(this).hasClass("unfold")){
            $(this).removeClass("unfold");
            $(".legendBox").slideUp();
            $(".esriOverviewMap").css('right','32px');
            
        }else{
            $(this).addClass("unfold");
            $(".legendBox").slideDown();
            $(".esriOverviewMap").css('right','200px');
        } 
    });
 
  
    //启用页面中的所有的提示工具（tooltip）
    //$("[data-toggle='tooltip']").tooltip('toggle');



    /*全屏显示*/
    $('#fullScreenBtn').click(function(){
        $("#mapContent").css('left','0px');
        $(".main").addClass('hide-left-menu');
        requestFullScreen();
        return false;
    });

    $(document).keyup(function(event) {
        if(event.keyCode == 27){
           exitFullScreen(); 
           $("#mapContent").css('left','298px');
           $(".main").removeClass('hide-left-menu');
           return false;
        }
    });
    function requestFullScreen() {
        var el = document.documentElement,
            rfs = el.requestFullScreen || el.webkitRequestFullScreen || el.mozRequestFullScreen || el.msRequestFullScreen,
            wscript;
     
        if(typeof rfs != "undefined" && rfs) {
            rfs.call(el);
            return;
        }
     
        if(typeof window.ActiveXObject != "undefined") {
            wscript = new ActiveXObject("WScript.Shell");
            if(wscript) {
                wscript.SendKeys("{F11}");
            }
        }
    }
     
    function exitFullScreen() {
        var el = document,
            cfs = el.cancelFullScreen || el.webkitCancelFullScreen || el.mozCancelFullScreen || el.exitFullScreen,
            wscript;
     
        if (typeof cfs != "undefined" && cfs) {
          cfs.call(el);
          return;
        }
     
        if (typeof window.ActiveXObject != "undefined") {
            wscript = new ActiveXObject("WScript.Shell");
            if (wscript != null) {
                wscript.SendKeys("{F11}");
            }
        }
    }
    //地图切换按钮
    $('.mapView').on('click','.mapView-btn',function(){
        var index = $(this).index();
        $(this).addClass('active').siblings().removeClass('active');
        $('.tab-mapCon').children('div.mapCon-resizable').removeClass('active').eq(index/2).addClass('active');
       // $('.tab-mapCon').children('div.mapCon-resizable').css('width','width:100%');
        //alert(index);
    });
    
    $('.mapBtn-sel li').on('click',function(){
            var $tmp = $("input[name='2d3dcheckbox']");
             if ($tmp.is(":checked")) {
                    $tmp.removeAttr("checked");
                    $(".resizable-left").css('visibility','hidden');
                    $(".resizable-right").css('width','1200px');
                    $(".resizable-left").css('width','0px');
                       
            }else{
                    $tmp.attr("checked");
                    $(".resizable-left").css('width','570px');
                    $(".resizable-left").css('visibility','visible');
                    $(".resizable-right").css('width','570px');
                    $(".handler").css('left','570px');
            }
    });

    //鼠标拖拽滑块改变div大小
    var doc = $(document), dl = $("div.resizable-left"), dc = $("div.resizable-right");
    var sum = dl.width() + dc.width() + 
    $("div.handler").mousedown (function (e) {
        var me = $(this);
        var deltaX = e.clientX 
                - 
                (parseFloat(me.css("left")) || parseFloat(me.prop("clientLeft")));
        doc.mousemove(function (e){
            var lt = e.clientX - deltaX; 
            lt = lt < 0 ? 0 : lt;
            lt = lt > sum - me.width() ? sum - me.width() : lt;
            me.css ("left", lt + "px");
            dl.width(lt);
            dc.width(sum-lt-me.width());
        });
    }).width();

    doc.mouseup (function(){
        doc.unbind("mousemove");
    });
    doc[0].ondragstart 
    = doc[0].onselectstart 
    = function () 
    {
        return false;
    };

});