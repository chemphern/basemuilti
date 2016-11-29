/**
 *
 * 地址联动生成
 * 依赖jQuery进行dom生成
 * Author lixiaoxin 2016-11-16
 *
 */
function multi_select(obj){
    var target = $("#" + obj.id);
    //如果没有隐藏表单则创建一次隐藏表单
    var names = obj.showName.split(",");
    var hidden_input = target.find("[name='" + names[0] + "']");
    if(hidden_input.length < 1){
        for(var temp_index in names){
            target.append($("<input />",{
                name:names[temp_index],
                style:"display:none"
            }));
        }
    }
    //控制层级
    var level = obj.index?obj.index:0;
    if(++level > obj.level){
        return;
    }
    //异步请求
    if(obj.url){
        $.ajax({
            url:obj.url,
            type:"get",
            dataType:"json",
            data:obj.data?obj.data:{},
            success:function(d){
                var retCode = d.retCode;
                if(retCode > 0){
                    //层级副本
                    var select = $("<select />",{
                        "yc_level":level
                    });
                    //映射参数
                    var hidden_arr = [];
                    var res = d.retData;
                    for(var res_obj_name in res){
                        var res_obj = res[res_obj_name];
                        var hidden_obj = {};
                        for(var pram_name in obj.pram){
                            var pram_value = obj.pram[pram_name];
                            if(pram_name == obj.key){
                                hidden_obj["value"] = res_obj[pram_value];
                            }else if(pram_name == obj["text"]) {
                                hidden_obj["text"] = res_obj[pram_value];
                            }
                            hidden_obj["yc_" + pram_name] = res_obj[pram_value];

                        }
                        hidden_arr.push(hidden_obj);
                    }
                    //请选择的处理
                    var optional = $("<option/>",{text:"请选择"});
                    select.append(optional);
                    select.change(function(){
                        var $s = $(this);
                        if($s.val().trim() == "请选择"){
                            var $opt = $($s.children()[0]);
                            var $opt_select = $opt.parent();
                            var $next = $opt_select.nextAll("select");
                            var $prev = $opt_select.prev("select");
                            $next.remove();
                            for(var temp_index in names){
                                var temp_name = names[temp_index];
                                var temp_input = target.find("[name='" + temp_name + "']");
                                if($prev.length > 0){
                                    temp_input.val($prev.attr("yc_" + temp_name));
                                }else {
                                    temp_input.val("");
                                }
                                $opt_select.attr("yc_" + temp_name,"");
                            }
                        }else {
                            var $val = $s.val();
                            var $opt = $s.find("[value='" + $val + "']");
                            var $opt_select = $opt.parent();
                            var $next = $opt_select.nextAll("select");
                            $next.remove();
                            //对隐藏表单进行赋值
                            for(var temp_index in names){
                                var temp_name = names[temp_index];
                                var temp_input = target.find("[name='" + temp_name + "']");
                                var final_val = $opt.attr("yc_" + temp_name);
                                temp_input.val(final_val);
                                //当前选择框存储一个副本
                                $opt_select.attr("yc_" + temp_name,final_val);
                            }
                            //根据映射对请求参数进行处理
                            var send_data ={};
                            var send_param_arr = obj.params.split(",");
                            for(var send_param_index in send_param_arr){
                                var send_name = send_param_arr[send_param_index];
                                if(obj.pram[send_name]){
                                    send_data[obj.pram[send_name]] = $opt.attr("yc_" + send_name);
                                }
                            }
                            var index = $opt_select.attr("yc_level");
                            var tar = $.extend(obj,{data:send_data,index:index});
                            multi_select(tar);
                        }
                    });
                    //根据映射后的参数创建option
                    for(var hidden_index in hidden_arr){
                        var hidden_obj = hidden_arr[hidden_index];
                        var opt = $("<option />",hidden_obj);
                        select.append(opt);
                    }
                    target.append(select);
                }
            }
        });
    }
}