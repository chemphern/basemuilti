
function initPlot2d(){
	if (!DCI.Plot.isload)
        DCI.Plot.Init(map);
    if (DCI.Plot.dialog)
        DCI.Plot.dialog.close();
    DCI.Plot.dialog = $.dialog({
        title: '态势标绘',
        width: 370,
        height: 200,
        left: 450,
        top: 200,
        modal: false, // 非模态，即不显示遮罩层
        content: DCI.Plot.Html
    });
//    DCI.Plot.InitEvent();
}
function plotPoint2d(sid){
	var id=sid;
	
}
function plotPolyline2d(){
	
}
function plotPolygon2d(){
	
}
function setSymbolStyle(){
	
}