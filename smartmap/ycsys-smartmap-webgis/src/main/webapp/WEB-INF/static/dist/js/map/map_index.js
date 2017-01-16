(function($){
$(document).ready(function(){

    //给左边菜单追加自定义滚动条方法
    // $(".leftBox").mCustomScrollbar({
    //     scrollButtons:{enable:true},//是否使用上下滚动按钮  
    //     advanced:{autoScrollOnFocus:false }//是否自动滚动到聚焦中的对象
    // });

    //给图例追加自定义滚动条方法,需放在隐藏元素前,否则无法显示
    $(".legendBox").mCustomScrollbar({   
        scrollButtons:{  
            enable:true //是否使用上下滚动按钮  
        },          
    });
    //当屏幕在768下隐藏左侧菜单
    var _width = $(window).width(); 
     if(_width < 1023){
         $(".main").addClass('hide-left-menu');
         $("#mapContent").css('left','0px');
         $('.leftBox').css('left','-298px');
     }
    //小屏主菜单加下拉菜单
    $(".menu-toggler").click(function() {
        //event.preventDefault();
       $("#navbar-collapse").slideToggle();

    });
    //隐藏显示左菜单
    $(".l_icon").click(function(){
         //event.preventDefault();
        if($(".leftBox").is(":visible")){
         $(".main").addClass('hide-left-menu');
         $("#mapContent").css('left','0px');
         $('.leftBox').css('left','-298px');
        }else{   
         $(".main").removeClass('hide-left-menu');
         $("#mapContent").css('left','298px');
         $('.leftBox').css('left','0px');
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
          $('.leftBox').css('left','0px');
        };
        if($(".menu-toggler").is(":visible")){
          $("#navbar-collapse").slideToggle();
        }
        //一级菜单切换所需要做的操作
        onModelChangeHandler(index);
    });



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
     $('.btn_list').on('click','button',function(event){
        event.preventDefault();
        $(this).addClass('active').siblings().removeClass('active');
     })
    
    //地图标绘选项卡切换
//    $('.bs-icon-list li').click(function() {
//        $(this).toggleClass('active').siblings().removeClass('active');
//    });
    //地图属性查询结果点击改变图标
//    $('.result li').bind('click',function(){
//        $(this).toggleClass('active').siblings().removeClass('active');
//    });
    //地图标绘高级选项面板展开收起
    $(".advanced-box").hide();
    $('.btn_set').click(function(e){
        e.preventDefault();        
        if ($(this).parents().next(".advanced-box").is(':visible')) {
            $(this).parents().next(".advanced-box").slideUp();
            $(this).next('.btn-group-adv').hide();
            $(this).removeClass('active');
        }else{
            $(this).parents().next(".advanced-box").slideDown();
            $(this).next('.btn-group-adv').show();
            $(this).addClass('active');
        }
    });
    
    //空间查询点击选择查询方式高亮显示
    $('.icon-searchway a').click(function() {
        $(this).toggleClass('active').siblings().removeClass('active');
    });
    //图层悬浮点击选中效果
    $('.maplayer li a').click(function(){
        $(this).toggleClass('select');
    });

    /******空间分析 三维分析 林火蔓延分析******/
    //三维分析点击三级菜单高亮
    $('.side-menu a').click(function(){
        $(this).toggleClass('active').siblings().removeClass('active');
    });
    //显示林火蔓延分析面板
    $('#forestFirebox').hide();
     $("#sideMenu").on('click','#forestFire',function (e) { 
        e.preventDefault();
        if ($('#forestFirebox').is(":visible")) {
            $('#forestFirebox').hide();
            $('#sideMenu').show();
        }else{
            $('#forestFirebox').show();
            $('#sideMenu').hide();
        }
    });
     //返回选项
     $("#forestFirebox").on('click','.arrow-back', function (e) { 
        e.preventDefault();
        if ($('#sideMenu').is(":hidden")) {
            $('#sideMenu').show();
            $('#forestFirebox').hide();
        }
    });
    //点击按钮高亮 
    $('#forestFireBtn').on('click','button',function(e){
        e.preventDefault();
        $(this).addClass('active').siblings().removeClass('active');
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
    	var elem = $("#mapContent"); 
    	requestFullScreen(elem);
        $("#mapContent").css('left','0px');
        $(".main").addClass('hide-left-menu');
        $('.leftBox').css('left','-298px');
        
    });

    $(document).keyup(function(event) {
        if(event.keyCode == 27){
           exitFullScreen(); 
           $("#mapContent").css('left','298px');
           $(".main").removeClass('hide-left-menu');
           $('.leftBox').css('left','0px');
        }
    });
    function requestFullScreen() {
        var el = document.documentElement,
            rfs = el.requestFullScreen || el.webkitRequestFullScreen || el.mozRequestFullScreen || el.msRequestFullscreen,
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
    //三维漫游切换三维地图
    $("#3dmy").click(function() {       
        var index = $(this).index();
        $(this).addClass('active').siblings().removeClass('active');
        $('#fore-2d3d-menu-my').addClass('current').siblings().removeClass('current');
        $('#mapView-btn-3dmy').addClass('active').siblings().removeClass('active');
        $(".resizable-right").css('display','block');
        $(".resizable-left").css('display','none'); 
        $(".resizable-right").css('width','100%');
        //初始加载飞行路径列表
        getFlyPaths("tableFlyPathForRoam");
    });
    //地图切换按钮

    var $lir = $('.mapBtn-sel li');
    var $check = $lir.find("input[name='2d3dcheckbox']");

    //切换3d
    $('#mapView-btn-3dmy').click(function(){
        $(this).addClass('active').siblings().removeClass('active');
        if ($(this).hasClass('active')) {
            $(".resizable-right").css('display','block');
            $(".resizable-left").css('display','none'); 
            $(".resizable-right").css('width','100%'); 
            $check.removeAttr('checked');  
            $(".handler").css('display','none');
        };
    });
  //切换2d
    $('.mapView').on('click','#mapView-btn-2dmap,#mapView-btn-2dwx',function(){
        $(this).addClass('active').siblings().removeClass('active');
        if ($(this).hasClass('active')) {
            $(".resizable-left").css('display','block');
            $(".resizable-right").css('display','none'); 
            $(".resizable-left").css('width','100%');
            $check.removeAttr('checked');  
            $(".handler").css('display','none');
        };
    });


    $check.click(function(event){
            $lir.find("input[name='2d3dcheckbox']").prop('checked',$(this).prop('checked'));
            $('.mapView-btn').removeClass('active');
            if ($(this).prop('checked')) {
                    getMap3dReadyInCommon();//勾选二三维同步时，三维操作
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

    //地区选择
    var srTargetCity;   
    $(".city-change-inner").click(function(e){
         srTargetCity=e.target;
         $(".city-popup-main").fadeIn();
    });

    $(".sel-city").on('click','span,li,button.city-pupup-close',function(event) {
        if (event.target !=srTargetCity) {
          $(".city-popup-main").fadeOut();
        }
    });
    //点击下拉框其他地方隐藏
    $("html").click(function(e) {
        if (e.target !=srTargetCity) {
          $(".city-popup-main").fadeOut();
        }
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
})(jQuery);