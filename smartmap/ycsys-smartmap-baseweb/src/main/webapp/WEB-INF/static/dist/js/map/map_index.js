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
        return false;
    });
    //三维漫游切换三维地图
    $('#3dmy').click(function(){
        var index = $(this).index();
        $(this).addClass('active').siblings().removeClass('active');
        $('#fore-2d3d-menu-my').addClass('current').siblings().removeClass('current');
        $('#mapView-btn-3dmy').addClass('active').siblings().removeClass('active');
        $(".resizable-right").addClass('active').siblings('div.resizable-left').removeClass('active');
        $('.resizable-left').css('display','none');
        $('.resizable-right').css('display','block');
        return false;        
    });

    //主页切换二维
    // $('.homemenu li').click(function(){
    //     $(this).toggleClass('active');
    //     $('#mapView-btn-3dmy').removeClass('active').siblings('.mapView-btn').addClass('active');
    //     $('.resizable-left').addClass('active').siblings('div.resizable-right').removeClass('active');
    //     $('.resizable-left').css('display','block');
    //     $('.resizable-right').css('display','none');
    //     return false;
    // })
    //左菜单切换效果
     $('.submenu').on('click','a',function(){
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
    
    //地图标绘选中工具高亮显示
    $('#myTabContentPlot a').click(function() {
        $(this).toggleClass('active').siblings().removeClass('active');
    });
    //空间查询点击选择查询方式高亮显示
    $('.icon-searchway a').click(function() {
        $(this).toggleClass('active').siblings().removeClass('active');
    });
    //图层悬浮点击选中效果
    $('.maplayer li a').click(function(){
        $(this).toggleClass('select');
    });
    //三维分析点击三级菜单高亮
    $('.side-menu a').click(function(){
        $(this).toggleClass('active').siblings().removeClass('active');
    });

    //隐藏显示右下角图例以及调整鹰眼的显示位置
    $(".legendBox").hide();
    $("#js_legend").on('click','.legendIcon',function(){
        if($(this).hasClass("unfold")){
            $(this).removeClass("unfold");
            $(".legendBox").slideUp();
            $(".esriOverviewMap").css('right','40px');
            
        }else{
            $(this).addClass("unfold");
            $(".legendBox").slideDown();
            $(".esriOverviewMap").css('right','210px');
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
    var $lir = $('.mapBtn-sel li');
    var $check = $lir.find("input[name='2d3dcheckbox']");

    $('.mapView').on('click','.mapView-btn',function(){
        var index = $(this).index();
        $(this).addClass('active').siblings().removeClass('active');
        $('.tab-mapCon').children('div.mapCon-resizable').removeClass('active').eq(index/2).addClass('active');
        if($(".resizable-left").hasClass('active')){
            $(".resizable-left").css('display','block');
            $(".resizable-right").css('display','none'); 
            $(".resizable-left").css('width','100%');
            $check.removeAttr('checked');  
        }else if($(".resizable-right").hasClass('active')){
            $(".resizable-left").css('display','none');
            $(".resizable-right").css('display','block');
            $(".resizable-right").css('width','100%');
            $check.removeAttr('checked');            
        }
         return false; 
    });


    $check.click(function(event){
            $lir.find("input[name='2d3dcheckbox']").prop('checked',$(this).prop('checked'));
            $('.mapView-btn').removeClass('active');
            if ($(this).prop('checked')) {
                    $(".resizable-left").css('width','50%');
                    $(".resizable-left").css('display','block');
                    $(".resizable-right").css('display','block');
                    $(".resizable-right").css('width','50%');
                    $(".handler").css('display','block');
                    $(".handler").css('left','50%');                       
            }else{
                    $(".resizable-left").css('display','none');
                    $(".resizable-right").css('visibility','visible');
                    $(".resizable-right").css('width','100%');
                    $(".handler").css('display','none');

            }
             
            /*阻止向上冒泡，以防再次触发点击操作*/  
            event.stopPropagation();  
    });


    //鼠标拖拽滑块改变div大小
    var doc = $(document), dl = $("div.resizable-left"), dc = $("div.resizable-right");
    var sum = dl.width() * 100 + "%" + dc.width() * 100 + "%" + 
    $("div.handler").mousedown (function (e) {
        var me = $(this);
        var deltaX = e.clientX 
                - 
                (parseFloat(me.css("left")) || parseFloat(me.prop("clientLeft")));
        doc.mousemove(function (e){
            var lt = e.clientX - deltaX; 
            lt = lt < 0 ? 0 : lt;
            lt = lt > sum - me.width() ? sum - me.width() : lt;
            me.css ("left", lt * 100 + "%");
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

//颜色选择调用
window.myColorPicker = $('input.color').colorPicker({
    buildCallback: function($elm) {
        this.$colorPatch = $elm.prepend('<div class="cp-disp">').find('.cp-disp');
    },
    cssAddon:
        '.cp-disp {padding:0px 10px; margin-bottom:6px; font-size:12px; height:20px; line-height:20px}' +
        '.cp-xy-slider {width:110px; height:128px;}' +
        '.cp-xy-cursor {width:16px; height:16px; border-width:2px; margin:-8px}' +
        '.cp-z-slider {height:128px; width:20px;}' +
        '.cp-z-cursor {border-width:8px; margin-top:-8px;}' +
        '.cp-alpha {height:16px;}' +
        '.cp-alpha-cursor {border-width:8px; margin-left:-8px;}',

    renderCallback: function($elm, toggled) {
        var colors = this.color.colors;

        this.$colorPatch.css({
            backgroundColor: '#' + colors.HEX,
            color: colors.RGBLuminance > 0.22 ? '#222' : '#ddd'
        }).text(this.color.toString($elm._colorMode)); // $elm.val();
    }
});

});