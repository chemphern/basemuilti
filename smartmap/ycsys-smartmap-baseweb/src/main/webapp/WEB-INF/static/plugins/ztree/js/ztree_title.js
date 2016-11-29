function BDtabTree(ObjID,JsonUrl,radioOrchecked){
    var setting = {
        view: {
            selectedMulti: false
        },
        check: {
            enable: true,
            chkStyle: radioOrchecked
        },
        data: {
            simpleData: {
                enable: true    
            }
        }
    };


//luoxiaohong ADD
var zNodes = new Array();
var NodeList = function(){
    var result = '';
    var username= new Array();
    var userManage= new Array();
    
    $.ajaxSettings.async = false;
    $.getJSON(JsonUrl,function(data){
        var name = [];
        for(var key in data){
            userManage[key] = data[key].manage;
            username[key] =  data[key].name;
            if(userManage[key]==""){
                name[key] = username[key];
            }else{
                name[key] = username[key]+"-"+userManage[key];
            }
            data[key].name = name[key];
        }
        result = eval(data);
    }); 
    return result;
}
zNodes = NodeList();

$.fn.zTree.init($(ObjID), setting, zNodes);
//$("#autoCallbackTrigger").bind("change", {}, setAutoTrigger);

}