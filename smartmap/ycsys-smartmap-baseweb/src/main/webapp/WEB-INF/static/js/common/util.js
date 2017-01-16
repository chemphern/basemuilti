/**
 * 工具类js
 * Created by lixiaoxin on 2017/1/13.
 */
function parseDataToTree(obj){
    var idKey = obj.idKey?obj.idKey:"id";
    var pidKey = obj.pidKey?obj.pidKey:"pid";
    var childrenKey = obj.childrenKey?obj.childrenKey:"children";
    var copyData = obj.data.slice(0);
    var orm = {};
    for(var x = 0;x<obj.data.length;x++){
        for(var y = 0;y<obj.data.length;y++){
            var _temp_data = obj.data;
            if(_temp_data[y][pidKey] == obj.data[x][idKey]){
                delete copyData[y];
                var ts_id = _temp_data[x][idKey];
                if(typeof orm[ts_id] == "undefined"){
                    orm[ts_id] = [];
                }
                orm[ts_id].push(_temp_data[y]);
            }
        }
    }
    var delete_none_temp;
    var delete_none = [];
    for(var x = 0;x<copyData.length;x++){
        delete_none_temp = copyData[x];
        if(typeof delete_none_temp != "undefined"){
            delete_none.push(delete_none_temp);
        }
    }
    var final = [];
    var doOrm= function(obj,orm,idKey){
        if(orm[obj[idKey]]){
            var _temp_o = orm[obj[idKey]];
            for(var x = 0;x<_temp_o.length;x++){
                doOrm(_temp_o[x],orm,idKey);
            }
            obj[childrenKey] = _temp_o;
        }
    };
    for(var index in delete_none){
        var _object = delete_none[index];
        doOrm(_object,orm,idKey);
        final.push(_object);
    }
    return final;
}
Date.prototype.Format = function (fmt) { //author: meizz
    var o = {
        "M+": this.getMonth() + 1,                 //月份
        "d+": this.getDate(),                    //日
        "h+": this.getHours(),                   //小时
        "m+": this.getMinutes(),                 //分
        "s+": this.getSeconds(),                 //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds()             //毫秒
    };
    if (/(y+)/.test(fmt))
        fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt))
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
};
function toDecimal(x) {
    var f = parseFloat(x);
    if (isNaN(f)) {
        return;
    }
    f = Math.round(f * 100) / 100;
    return f;
}
function toGB(x) {
    return toDecimal(x) + " GB";
}
function mBToGB(x) {
    var f = parseFloat(x) / 1024;
    if (isNaN(f)) {
        return;
    }
    f = Math.round(f * 100) / 100;
    return f + " GB";
}
function toPercentage(x) {
    return toDecimal(x) + " %";
}
