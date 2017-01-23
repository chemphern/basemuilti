/**
 * Created by ChenLong on 2017/1/4.
 */
$(function() {
    $('.city-title').on('click', quickLocationQueryProvince);//省级快速定位入口

    $('.shi span').on('click', quickLocationQueryCity);//市级快速定位入口

    $('.shi-list li').on('click', QuickLocationQueryCounty);//县级快速定位入口
});

//省级快速定位
function quickLocationQueryProvince(){
    var text = $(this).text().substring($(this).text().indexOf('：') + 1,$(this).text().length);
    var proviceUrl = quickLocationGlobal.ServiceUrl + "/" + quickLocationGlobal.ProvinceLayerIndex;
    QuickLocationQuery(proviceUrl,quickLocationGlobal.ProvinceField,text);
}

//市级快速定位
function quickLocationQueryCity(){
    var cityUrl = quickLocationGlobal.ServiceUrl + "/" + quickLocationGlobal.CityLayerIndex;
    QuickLocationQuery(cityUrl,quickLocationGlobal.CityField,$(this).text());
}

//县级快速定位
function QuickLocationQueryCounty(){
    var countyUrl = quickLocationGlobal.ServiceUrl + "/" + quickLocationGlobal.CountyLayerIndex;
    QuickLocationQuery(countyUrl,quickLocationGlobal.CountyField,$(this).text());
}

//快速定位查询
function QuickLocationQuery(url,field,val){
    $('.city-change-inner span').html(val);
    var query=new esri.tasks.Query();
    query.returnGeometry=true;
    query.outFields=["*"];
    query.where=field+" LIKE "+"'%"+val+"%'";
    var task=new esri.tasks.QueryTask(url);
    task.execute(query,QuickLocationSuccess,QuickLocationQueryFail);
}

//查询结果定位
function QuickLocationSuccess(featureSet) {
    if(featureSet!=null&&featureSet.features!=null&&featureSet.features.length>0){
        graphicsConvertor(featureSet.features,false);//转换到经纬度坐标
        var feature = featureSet.features[0];
        var geometry = feature.geometry;
        if(geometry.type=="polygon"){
            var extent = geometry.getExtent();
            if(mapOpt==3){
                setZoomInLevel(extent);
            }else if(mapOpt==2){
                map.setExtent(extent);
            }else{
                map.setExtent(extent);
            }
        }
    }
}

//快速定位查询出错
function QuickLocationQueryFail(message) {
    alert("无法获取位置信息，请联系管理员！详细：" + JSON.stringify(message));
}

//根据二维地图设置当前地图区域
function setQuickLocationMap2d() {
    var url = quickLocationGlobal.ServiceUrl;//声明请求服务地址
    var mapCenter = map.extent.getCenter();//point Geometry
    var mapLevel = map.getLevel();//num/省/市/县区
    var layerId = null;
    //判断对那一层级进行空间查询
    if(mapLevel<=quickLocationGlobal.ProvinceMapLevel){//省级空间查询
        layerId = quickLocationGlobal.ProvinceLayerIndex;
    }else if(mapLevel>quickLocationGlobal.ProvinceMapLevel&&mapLevel<=quickLocationGlobal.CityMapLevel){//市级空间查询
        layerId = quickLocationGlobal.CityLayerIndex;
    }else if(mapLevel>quickLocationGlobal.CityMapLevel&&mapLevel<=quickLocationGlobal.CountyMapLevel){//县区级空间查询
        layerId = quickLocationGlobal.CountyLayerIndex;
    }
    //执行空间查询
    if(layerId!=null)
        spatialSearchForQuickLocation(url,layerId,mapCenter,null);
}

//根据二维地图设置当前地图区域
function setQuickLocationMap3d() {
    var url = quickLocationGlobal.ServiceUrl;//声明请求服务地址
    //获取地图中心点
    var center = YcMap3D.Window.CenterPixelToWorld(-1);
    var sr = new esri.SpatialReference({ wkid: 4326 });
    var centerPoint = new esri.geometry.Point(center.Position.X,center.Position.Y,sr);
    var centerGeometry = {geometry:centerPoint};
    graphicsConvertor(centerGeometry,true);//经纬度转平面坐标
    //获取地图高度，用来判断图层号
    var mapHeight = YcMap3D.Navigate.GetPosition(0).Altitude;//height/省/市/县区
    var layerId = null;
    //判断对那一层级进行空间查询
    if(mapHeight>=quickLocationGlobal.ProvinceMapHeight){//省级空间查询
        layerId = quickLocationGlobal.ProvinceLayerIndex;
    }else if(mapHeight>=quickLocationGlobal.CityMapHeight && mapHeight<quickLocationGlobal.ProvinceMapHeight){//市级空间查询
        layerId = quickLocationGlobal.CityLayerIndex;
    }else if(mapHeight>=quickLocationGlobal.CountyMapHeight && mapHeight<=quickLocationGlobal.CityMapHeight){//县区级空间查询
        layerId = quickLocationGlobal.CountyLayerIndex;
    }
    //获取地图范围
    var extent3d = get3DMapExtent();
    var sr = new esri.SpatialReference(4326);
    var extent2d = new esri.geometry.Extent(extent3d.xmin,extent3d.ymin,extent3d.xmax,extent3d.ymax,sr);
    //执行空间查询
    if(layerId!=null)
        spatialSearchForQuickLocation(url,layerId,centerGeometry.geometry,extent2d);
}

//根据图层id执行空间查询
function spatialSearchForQuickLocation(url,layerId,geometry,mapExtent) {
    //进行空间查询
    var task=new esri.tasks.IdentifyTask(url);
    var params=new esri.tasks.IdentifyParameters();
    params.geometry = geometry;
    params.layerOption = esri.tasks.IdentifyParameters.LAYER_OPTION_ALL;
    if(mapExtent!=null)
        params.mapExtent = mapExtent;
    else
        params.mapExtent = map.extent;
    params.layerIds = [layerId];
    params.returnGeometry = true;
    params.tolerance = 3;
    var result = task.execute(params,setQuickLocationMapResults,setQuickLocationMapError);
}

function setQuickLocationMapResults(featureSet) {
    if(featureSet!=null&&featureSet.length>0){
        var feature = featureSet[0].feature;
        var locationName = feature.attributes[quickLocationGlobal.CityField];
        $('.city-change-inner span').html(locationName);
    }
}

function setQuickLocationMapError(message) {
    // alert("无法获取位置信息，请联系管理员！详细：" + JSON.stringify(message));
}