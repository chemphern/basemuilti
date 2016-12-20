$(function(){
	$("#btnPlotPoint").on('click',plotPoint);
	$("#btnPlotLine").on('click',plotPolyline);
	$("#btnPlotArea").on('click',plotPolygon);
	$("#menuPlot").on('click',initPlot);
})

function plotPoint(){
	if(mapOpt==2){
		plotPoint2d();
	}
}
function plotPolyline(){
	if(mapOpt==2){
		plotPolyline2d();
	}
}
function plotPolygon(){
	if(mapOpt==2){
		plotPolygon2d();
	}
}
function initPlot(){
	if(mapOpt==2){
		initPlot2d();
	}
}