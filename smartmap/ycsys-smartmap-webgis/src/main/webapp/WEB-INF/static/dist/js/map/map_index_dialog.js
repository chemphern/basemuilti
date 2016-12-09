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
         $("#Sqdwtoolbar").on('click','.btn_add', function (e) { 
            e.preventDefault();  
            var url=path+'/locateService/toAdd.do';
            var dialog = $.Layer.iframe(
                {
                    title: '添加书签',
                    id:'bookmarkadd',
                    url:url,
                    width: 400,
                    height: 300
                });
        });
         //编辑
        $("#Sqdwtoolbar").on('click','.btn_edit', function (e) { 
            e.preventDefault();  
            var bookmarkId=$('#tableSqdw').bootstrapTable('getSelections')[0].id;
            var dialog = $.Layer.iframe(
                {
                    title: '编辑书签',
                    id:'bookmarkedit',
                    url:path+'/locateService/toEdit.do?id='+bookmarkId,
                    width: 400,
                    height: 300
                });
        });

        /******三维漫游 路径管理******/
        //添加
         $(".pathbox").on('click','.btn_add,btn_edit', function () { 
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