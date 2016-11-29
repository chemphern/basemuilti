/***
 * 表单的一些封装
 * @author lixiaoxin 2016-11-16
 * */

function exec_validate(dom){
    var rule = exec_rules(dom);
    var obj = {
        rules:rule.rules,
        messages:rule.messages,
        errorClass: 'l-text-invalid',
        errorPlacement : function(lable,element) {
            if(element.next('div.l-exclamation').size() == 0) {
                var type = element.attr('type');
                var dise = element.css('display');
                if(lable.html() != '' && ((type != undefined && type.toLowerCase() == 'hidden') || (dise != undefined && dise.toLowerCase() == 'none'))) {
                    element = $('#' + element.attr('id') + '_txt_val').parent();
                    if(element.size() > 0) {
                        var ps = element.position();
                        var ewid = element.width();
                        element.after('<div style="left:' + (ps.left + ewid - 10) + 'px; top:' + ps.top + 'px;position:absolute" class="l-exclamation" title="' + lable.html() + '"></div>');
                    }
                    else {
                        $.ligerDialog.error(lable.html());
                    }
                }
                else {
                    //当提示不为空的时候才创建errorTip
                    if(lable.html() != ''){
                        var ps = element.position();
                        var ewid = element.width();
                        element.after('<div style="left:' + (ps.left + ewid - 10) + 'px; top:' + ps.top + 'px;position:absolute" class="l-exclamation" title="' + lable.html() + '"></div>');
                    }
                }
                //用于移除errorTip
            }else{
                //提示为空的时候,说明正确，移除errorTip
                if(lable.html() == ''){
                    //判断后面是否有errorTip,有则移除
                    if(element.next().attr("class") == "l-exclamation"){
                        element.next().remove();
                    }
                    //当提示不为空的时候，说明错误,实时更新错误提示
                }else{
                    element.next().attr("title",lable.html());
                }
            }
        },success : function(lable) {
            var element = $("#" + lable.attr("for"));
            element.nextAll('div.l-exclamation').remove();
        }
    };
    return obj;
}
//支持validate属性的规则认证
function exec_rules(dom){
    var doms = dom.find("[validate]");
    var res = {};
    var rules = {};
    var messages  = {};
    $.each(doms,function(){
        var ts = $(this);
        var name = ts.attr("name");
        var str_validate = ts.attr("validate");
        var data =(new Function("","return "+str_validate))();
        var ms = data["messages"];
        if(ms){
            messages[name] = ms;
            delete data.messages;
        }
        rules[name] = data;
    });
    res.rules = rules;
    res.messages = messages;
    return res;
}