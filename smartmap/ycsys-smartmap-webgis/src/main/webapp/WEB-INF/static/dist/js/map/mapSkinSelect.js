/**
 * AdminLTE Demo Menu
 * ------------------
 * You should not use this file in production.
 * This file is for demo purposes only.
 */
(function ($, AdminLTE) {

  "use strict";

  /**
   * List of all the available skins
   *
   * @type Array
   */
  var my_skins = [
    "skin-cyan",
    "skin-blue",
    "skin-green"
    
  ];

  //Create the new tab
  var tab_pane = $("<div />", {
    "id": "control-sidebar-theme-demo-options-tab",
    "class": "tab-pane active"
  });

  //Create the tab button
  var tab_button = $("<li />", {"class": "active"})
      .html("<a href='#control-sidebar-theme-demo-options-tab' data-toggle='tab'>"
      + "<i class='fa fa-wrench'></i>"
      + "</a>");

  //Add the tab button to the right sidebar tabs
  $("[href='#control-sidebar-home-tab']")
      .parent()
      .before(tab_button);

  //Create the menu
  var demo_settings = $("<div />");

  var skins_list = $("<ul />", {"class": 'list-unstyled clearfix'});

  //Dark sidebar skins
  var skin_cyan =
      $("<li />", {"class": 'active',"data-id":'1', "data-name":'skin-cyan'})
          .append("<a href='javascript:void(0);' data-skin='skin-cyan' style='display: block; box-shadow: 0 0 3px rgba(0,0,0,0.4)' class='clearfix  skin-cyan-hover '>"
          + "<div><span class='bg-cyan' style='display:block; width: 100%; float: left; height: 45px; '></span><span class='skin-select-active active'></span></div>"
          + "</a>"
          + "<p class='text-center color-name'>极简·青</p>");
  skins_list.append(skin_cyan);
  var skin_blue =
      $("<li />", {"class": '',"data-id":'2', "data-name":'skin-blue'})
          .append("<a href='javascript:void(0);' data-skin='skin-blue' style='display: block; box-shadow: 0 0 3px rgba(0,0,0,0.4)' class='clearfix skin-blue-hover'>"
          + "<div><span class='bg-blue' style='display:block; width: 100%; float: left; height: 45px; '></span><span class='skin-select-active'></span></div>"
          + "</a>"
          + "<p class='text-center color-name'>科技·蓝</p>");
  skins_list.append(skin_blue);
  var skin_green =
      $("<li />", {"class": '',"data-id":'3', "data-name":'skin-green'})
          .append("<a href='javascript:void(0);' data-skin='skin-green' style='display: block; box-shadow: 0 0 3px rgba(0,0,0,0.4)' class='clearfix skin-green-hover'>"
          + "<div><span class='bg-green' style='display:block; width: 100%; float: left; height: 45px;' ></span><span class='skin-select-active'></span></div>"
          + "</a>"
          + "<p class='text-center color-name'>生命·绿</p>");
  skins_list.append(skin_green);



  //demo_settings.append("<h4 class='control-sidebar-heading'>皮肤选项</h4>");
  demo_settings.append(skins_list);

  tab_pane.append(demo_settings);
  $("#control-sidebar-home-tab").after(tab_pane);

  setup();
  // 当前皮肤
   //  $('.list-unstyled li').click(function() { 
   //    Mycookie(this.id)
   //  });
   //  var cookie=$.cookie("cookie");         
   //  //var name=$(this).data("name");
   //  if(cookie){
   //    Mycookie(cookie);
   //  }
   // function Mycookie(thiscookie){
   //  $("#"+thiscookie).addClass('active').siblings().removeClass('active');
   //  //$("#colortable").attr("href",thiscookie+".css");

   //  $.cookie("cookie",thiscookie,{
   //    "path":"/",
   //    "expires":10
   //  })
   // }

      // if($.cookie("Skin")!=null){
      //   $.cookie("Skin", "name");
      // }else{

      //   $.cookie("Skin") == name;
      // }


        // if(不存在cookie){
        //   获取当前点击li的"data-key"
        //   创建cookie，并把当前点击li的"data-key"值给cookie
      
        // }else{  
        //   获取对应的"data-key"
        //   修改cookie值，把当前点击li的"data-key"值给cookie
        // }
        
        // 页面加载 的时候，依据cookie值里面的"data-key" 来给对应的li加class 


  /**
   * Toggles layout classes
   *
   * @param String cls the layout class to toggle
   * @returns void
   */
  function change_layout(cls) {
    $("body").toggleClass(cls);
    AdminLTE.layout.fixSidebar();
    //Fix the problem with right sidebar and layout boxed
    if (cls == "layout-boxed")
      AdminLTE.controlSidebar._fix($(".control-sidebar-bg"));
    if ($('body').hasClass('fixed') && cls == 'fixed') {
      AdminLTE.pushMenu.expandOnHover();
      AdminLTE.layout.activate();
    }
    AdminLTE.controlSidebar._fix($(".control-sidebar-bg"));
    AdminLTE.controlSidebar._fix($(".control-sidebar"));
  }

  /**
   * Replaces the old skin with the new skin
   * @param String cls the new skin class
   * @returns Boolean false to prevent link's default action
   */
  function change_skin(cls) {
    $.each(my_skins, function (i) {
      $("body").removeClass(my_skins[i]);      
    });
    
    $("body").addClass(cls);    
    store('skin', cls);   
    return false;
  }


  /**
   * Store a new settings in the browser
   *
   * @param String name Name of the setting
   * @param String val Value of the setting
   * @returns void
   */
  function store(name, val) {
    if (typeof (Storage) !== "undefined") {
      localStorage.setItem(name, val);
    } else {
      window.alert('Please use a modern browser to properly view this template!');
    }
  }

  /**
   * Get a prestored setting
   *
   * @param String name Name of of the setting
   * @returns String The value of the setting | null
   */
  function get(name) {
    if (typeof (Storage) !== "undefined") {
      return localStorage.getItem(name);
    } else {
      window.alert('Please use a modern browser to properly view this template!');
    }
  }

  /**
   * Retrieve default settings and apply them to the template
   *
   * @returns void
   */
  function setup() {
    var tmp = get('skin');
    if (tmp && $.inArray(tmp, my_skins))
      change_skin(tmp);

    //Add the change skin listener
    $("[data-skin]").on('click', function (e) {
      if($(this).hasClass('knob'))              
        return;      
      e.preventDefault();
      change_skin($(this).data('skin'));
      $(this).parents().addClass('active').siblings().removeClass('active');
    });



  }
})(jQuery, $.AdminLTE);
