;(function($){
    $(document).ready(function () {
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
         $("#tableLjgl").on('click','.btn_add', function (e) { 
            e.preventDefault();  
            var dialog = $.Layer.iframe(
                {
                    title: '添加飞行路径',
                    url:'map_add_fxljd.html',
                    width: 400,
                    height: 200
                });
        });
         //编辑
        $("#tableLjgl").on('click','.btn_edit', function (e) { 
            e.preventDefault();  
            var dialog = $.Layer.iframe(
                {
                    title: '编辑飞行路径',
                    url:'map_add_fxljd.html',
                    width: 400,
                    height: 200
                });
        });
        //删除
        $("#tableLjgl").on('click', '.btn_del', function(e){ 
            e.preventDefault();         
            if ($('#tableLjgl input[name="checkItem"]:checked').length<1){ 
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
    });
})(jQuery);