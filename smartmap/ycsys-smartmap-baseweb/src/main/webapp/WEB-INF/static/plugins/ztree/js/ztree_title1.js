function BDtabTree(ObjID,JsonUrl,radioOrchecked){
    var setting = {
        view: {
            selectedMulti: false
        },
        check: {
            show:false,
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
    $.ajaxSettings.async = false;
    $.getJSON(JsonUrl,function(data){
        result = eval(data);
    }); 
    return result;
}
zNodes = NodeList();

$.fn.zTree.init($(ObjID), setting, zNodes);
//$("#autoCallbackTrigger").bind("change", {}, setAutoTrigger);

}