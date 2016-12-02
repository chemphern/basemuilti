;(function($){
    $(document).ready(function () {
        /******个人消息 资料******/
        //查看修改个人资料
         $(".map_personal").on('click','.btn_edit', function (e) { 
            e.preventDefault();  
            var dialog = $.Layer.iframe(
                {
                    title: '个人资料',
                    url:'map_personal.html',
                    width: 400,
                    height: 300
                });
        });
        //修改密码
         $(".map_password").on('click','.btn_edit', function (e) { 
            e.preventDefault();  
            var dialog = $.Layer.iframe(
                {
                    title: '修改密码',
                    url:'map_password.html',
                    width: 400,
                    height: 100
                });
        });
        //查看消息详情
         $(".msg-box-bd").on('click','a', function (e) { 
            e.preventDefault();  
            var dialog = $.Layer.iframe(
                {
                    title: '查看消息详情',
                    url:'map_msg_info.html',
                    width: 700,
                    height: 300
                });
        });         
        /******书签定位******/
        //添加
         $("#tableSqdw").on('click','.btn_add', function (e) { 
            e.preventDefault();  
            var dialog = $.Layer.iframe(
                {
                    title: '添加书签',
                    url:'map_add_sqdw.html',
                    width: 400,
                    height: 300
                });
        });
         //编辑
        $("#tableSqdw").on('click','.btn_edit', function (e) { 
            e.preventDefault();  
            var dialog = $.Layer.iframe(
                {
                    title: '编辑书签',
                    url:'map_add_sqdw.html',
                    width: 400,
                    height: 300
                });
        });
        //删除
        $("#tableSqdw").on('click', '.btn_del', function(e){ 
            e.preventDefault();         
            if ($('#tableSqdw input[name="checkItem"]:checked').length<1){ 
                $.Layer.confirm({
                    msg: '请选择数据？', fn: function () {        
                    }
                });
                return;
            }else{
                $.Layer.confirm({
                    msg: '是否要删除数据？', fn: function () {
                        
                    }
                });
            }
        });
        /******三维漫游 路径管理******/
        //添加
         $(".pathbox").on('click','.btn_add', function () { 
            if ($('.pathbox').is(":visible")) {
                $('.pathbox').hide();
                $('.pathbox-add').show();
            };
        });

         //编辑
         $(".pathbox-add").on('click','.btn_save,.btn_cancel', function () { 
            if ($('.pathbox').is(":hidden")) {
                $('.pathbox').show();
                $('.pathbox-add').hide();
            };
        });

    });
})(jQuery);