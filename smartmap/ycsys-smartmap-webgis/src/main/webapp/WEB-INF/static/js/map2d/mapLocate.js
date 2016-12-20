
/**
 * 经纬度定位
 * @param lng 精度
 * @param lat 纬度
 * @returns
 */
function locateLngLat2d(lng,lat){
	map.graphics.clear();
	var point=new esri.geometry.Point(lng,lat);
	renderPoint(point);
}
/**
 * 平面坐标定位
 * @param x 地图x坐标
 * @param y 地图y坐标
 * @returns
 */
function locateXY2d(x,y){
	map.graphics.clear();
	var point=new esri.geometry.Point(x,y,new esri.SpatialReference({wkid:3857}));
	renderPoint(point);
}
/**
 * 地名定位
 * @param address 地名
 * @returns
 */
function locateAddress2d(address){
	map.graphics.clear();
	search.on('search-results',searchBack);
	search.clear();
	search.search(address);
}
function searchBack(result){
	console.log(result);
	clear2dMap();
	if(result.numResults<1){
		showAlertDialog("没有搜索到结果");
	}else{
		var pointLyr=new esri.layers.GraphicsLayer({id:"iPointLyr"});
		var polylineLyr=new esri.layers.GraphicsLayer({id:"iPolylineLyr"});
		var polygonLyr=new esri.layers.GraphicsLayer({id:"iPolygonLyr"});
		map.addLayers([polygonLyr,polylineLyr,pointLyr]);
		arrFeaturesTemp=[];
		$.each(result.results,function(k,arr){
			$.each(arr,function(indx,val){
				var feature=val.feature;
				var infoTemplate=new esri.InfoTemplate("属性","${name}");
				feature.setInfoTemplate(infoTemplate);
				feature.attributes.name=val.name;
				arrFeaturesTemp.push(feature);
			})
		})
		//构造属性页数组
		listLocateInfos(arrFeaturesTemp);
		//初始化分页
		var opt={
				pageId:"#PaginationLocate",
				queryNumId:"#locateNum",
				backId:"#btnLocateBack",
				headerId:"#locateHeader",
				resultId:"#locateResult"
		}
		$(opt.headerId).css('display','none');
		$(opt.resultId).css('display','block');
		initPager(arrQueryInfoItem,opt,function(page_index){
			var new_content=createPageContentSpatial(page_index);
			$("#locateItem").empty().append(new_content);
		});
	}
}
function listLocateInfos(arrFeatures){
	nameField="name";
	summaryFields="";
	arrQueryInfoItem=[];
	for(var i=0,p=1;i<arrFeatures.length;i++,p++){
		var feature=arrFeatures[i];
		var li=createItemSpatial(feature,p);
		arrQueryInfoItem.push(li);
		if(p >= pageSize){
			p=0;
		}
	}
}
function renderPoint(point){
	var symbol=getPointSymbol();
	var graphic=new esri.Graphic(point,symbol);
	map.graphics.add(graphic);
	map.centerAt(point);
}
function getPointSymbol(){
	var color=new esri.Color([0, 255, 255, 1 ]);
	return new esri.symbol.SimpleMarkerSymbol(esri.symbol.SimpleMarkerSymbol.STYLE_DIAMOND,10,
			new esri.symbol.SimpleLineSymbol(esri.symbol.SimpleLineSymbol.STYLE_SOLID,color,1),color);
}
function bookmarkSearch2d(){
	$('#tableSqdw').bootstrapTable('refresh');
}
function bookmarkAdd2d(e){
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
}
function bookmarkEdit2d(e){
    var bookmarkId=$('#tableSqdw').bootstrapTable('getSelections')[0].id;
    var dialog = $.Layer.iframe(
        {
            title: '编辑书签',
            id:'bookmarkedit',
            url:path+'/locateService/toEdit.do?id='+bookmarkId,
            width: 400,
            height: 300
        });
}
function bookmarkDel2d(e){
	e.preventDefault();  
	$.Layer.confirm({
		msg:"确认删除",
		fn:function(){
        	var bookmarkId=$('#tableSqdw').bootstrapTable('getSelections')[0].id;
			var url=path+'/locateService/delete.do?id='+bookmarkId;
			$.get(url,function(data){
				if(data.retCode==1){
					$('#tableSqdw').bootstrapTable('refresh');
				}
			},'json')
		}
	})
}
function locateBookmark2d(e, row, field){
	var extent=new esri.geometry.Extent(row.xmin,row.ymin,row.xmax,row.ymax,esri.SpatialReference(4326));
	extent=esri.geometry.geographicToWebMercator(extent);
	map.setExtent(extent,true);
}
function queryParams(params){
	var queryParams=$("#bookName").serialize();
	queryParams.limit=params.limit;
	queryParams.offset=params.offset;
	return queryParams;
}