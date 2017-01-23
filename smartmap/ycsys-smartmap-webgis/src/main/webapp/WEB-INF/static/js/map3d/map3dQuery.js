/**
 * Created by ChenLong.
 * Description:实现三维场景地图查询相关操作
 * version: 1.0.0
 */

//------------------------------------------------------------------------------空间查询------------------------------------------------------------------------//
//声明当前点击对象
var currentSelectFeature=null;

//声明当前结果要素集范围
var currentFeatureSetPos=null;

/**
 * 三维空间点查询
 */
function queryPoint3d() {
    spatialFeatureReserch("point");
}

/**
 * 三维空间线查询
 */
function queryPolyline3d() {
    spatialFeatureReserch("line");
}

/**
 * 三维空间面查询
 */
function queryPolygon3d() {
    spatialFeatureReserch("area");
}

/**
 * 空间查询主函数
 * @param spatialReserchType 查询类型
 */
function spatialFeatureReserch(spatialReserchType) {
    if(spatialReserchType == "point"){
        YcMap3D.Window.SetInputMode(1);
        YcMap3D.AttachEvent("OnLButtonUp", SpatialPointReserchOnLButtonUp);
    }else if(spatialReserchType == "line"){
        //空间查询线查询绘制操作
        DrawTool.activate(DrawTool.DrawType.TERRAPOLYLINE);
        //空间查询线查询结果处理
        DrawTool.drawEndHandler = function(polyline) {
            featureSpatialQueryLine(polyline.Geometry);
        };
    }else if(spatialReserchType == "area"){
        //空间查询面查询绘制操作
        DrawTool.activate(DrawTool.DrawType.TERRAPOLYGON);
        //空间查询面查询结果处理
        DrawTool.drawEndHandler = function (polygon) {
            featureSpatialQueryArea(polygon.Geometry);
        };
    }
}

/**
 * 空间查询点要素空间查询
 * @param geometry Skyline面要素转换为ArcGIS面要素，并调用二维查询
 */
function SpatialPointReserchOnLButtonUp(Flags,X,Y) {
    var point = new esri.geometry.Point(X,Y);
    //定义二维空间查询参数对象
    var geometryObj={geometry:null}
    geometryObj.geometry = point;
    onDrawEnd(geometryObj);
}

/**
 * 空间查询线要素空间查询
 * @param geometry Skyline面要素转换为ArcGIS面要素，并调用二维查询
 */
function featureSpatialQueryLine(geometry) {
    if(geometry!=null&&geometry.Points.Count>0){
        var coords = convert3DTo2DCoord(geometry.Points);
        var polylin = new esri.geometry.Polyline(coords);
        var geometryObj={geometry:null}
        geometryObj.geometry = polylin;
        onDrawEnd(geometryObj);
    }
}

/**
 * 空间查询面要素空间查询
 * @param geometry Skyline面要素转换为ArcGIS面要素，并调用二维查询
 */
function featureSpatialQueryArea(geometry) {
    if(geometry!=null&&geometry.Rings.Count>0){
        var polygon = new esri.geometry.Polygon();
        for(var i=0;i<geometry.Rings.Count;i++){
            var ring = geometry.Rings.Item(i);
            var coords = convert3DTo2DCoord(ring.Points);
            polygon.addRing(coords);
        }
        var geometryObj={geometry:null}
        geometryObj.geometry = polygon;
        onDrawEnd(geometryObj);
    }
}

//------------------------------------------------------------------------------属性查询/逻辑查询------------------------------------------------------------------------//
/**
 * ArcGIS查询结果转变添加到三维场景中
 * @param featureSet ArcGIS查询结果要素集
 * @param ifIndex 标识结果集是否显示编号图标
 */
function fromArcgisTo3dScene(featureSet,ifIndex) {
    if(map3DInit==true){
        if(featureSet!=null&&featureSet.length>0){
            clearFeatureSearchFolder();
            var featureExtent3D = null;
            var featurePointArr = [];
            for(var i=0;i<featureSet.length;i++){
                var feature = featureSet[i];
                //绘制结果图标和要素到场景中
                var num;
                if(ifIndex)
                    num = i+1;
                else
                    num = -1;
                var featureExtent = addFeatureToScene3D(feature,num);
                //获取得到结果集区域信息
                if(feature.geometry.type=="point"){
                    featurePointArr.push(feature);
                }else if(feature.geometry.type=="polyline"||feature.geometry.type=="polygon"){
                    featureExtent3D = getPolygonPolylineFeatureSetExtent(featureExtent,featureExtent3D);
                }
            }
            //结束场景绘制，定位到结果集区域
            if(featureExtent3D!=null){
                navigateSceneToFeatures(featureExtent3D);
                currentFeatureSetPos = featureExtent3D;
            }else if(featurePointArr!=null&&featurePointArr.length>0){
                var extent = getPointFeatureSetExtent(featurePointArr)
                navigateSceneToFeatures(extent);
                currentFeatureSetPos = extent;
            }
        }
    }
}

/**
 * 根据不同情况，获取线、面要素集的坐标范围
 * @param featureExtent 当前Feature对应的范围
 * @param featureExtent 当前遍历过的要素集的范围
 * @returns
 */
function getPolygonPolylineFeatureSetExtent(featureExtent,featureExtent3D) {
    if(featureExtent!=null){
        if(featureExtent3D==null)
            featureExtent3D = featureExtent;
        else{
            if(featureExtent.xmax>featureExtent3D.xmax)
                featureExtent3D.xmax = featureExtent.xmax;
            if(featureExtent.ymax>featureExtent3D.ymax)
                featureExtent3D.ymax = featureExtent.ymax;
            if(featureExtent.xmin<featureExtent3D.xmin)
                featureExtent3D.xmin=featureExtent.xmin;
            if(featureExtent.ymin<featureExtent3D.ymin)
                featureExtent3D.ymin=featureExtent.ymin;
        }
    }
    return featureExtent3D;
}

/**
 * 根据不同情况，获取点要素集的坐标范围
 * @param featureExtent 当前Feature对应的范围
 * @param featureExtent 当前遍历过的要素集的范围
 * @returns
 */
function getPointFeatureSetExtent(featurePointArr) {
    var pointExtent = null;
    if(featurePointArr!=null&&featurePointArr.length<3){
        if(featurePointArr.length==1)
            pointExtent = new esri.geometry.Extent(featurePointArr[0].geometry.x-0.01, featurePointArr[0].geometry.y-0.01, featurePointArr[0].geometry.x+0.01, featurePointArr[0].geometry.y+0.01, featurePointArr[0].geometry.spatialReference)
        else if(featurePointArr.length==2) {
            var xmax = Math.max(featurePointArr[0].geometry.x,featurePointArr[1].geometry.x);
            var xmin = Math.min(featurePointArr[0].geometry.x,featurePointArr[1].geometry.x);
            var ymax = Math.max(featurePointArr[0].geometry.y,featurePointArr[1].geometry.y);
            var ymin = Math.min(featurePointArr[0].geometry.y,featurePointArr[1].geometry.y);
            pointExtent = new esri.geometry.Extent(xmin, ymin, xmax, ymax, featurePointArr[0].geometry.spatialReference)
        }
    }else if(featurePointArr!=null&&featurePointArr.length>=3){
        var paths = [];
        for(var i=0;i<featurePointArr.length;i++){
            var point = [];
            point.push(featurePointArr[i].geometry.x);
            point.push(featurePointArr[i].geometry.y);
            paths.push(point);
        }
        var polyline = new esri.geometry.Polyline(paths);
        pointExtent = polyline.getExtent();
    }
    return pointExtent;
}

/**
 * 将ArcGIS查询结果集中一个Feature展示到三维中
 * @param feature 查询结果要素
 * @param num 结果序号
 * @returns {*} 要素集范围
 */
function addFeatureToScene3D(feature,num) {
    var geometry = feature.geometry;
    if(geometry!=null){
        if(geometry.type=="point"){
            addFeatureImage(getPointFeaturePosition(feature),feature,num);
            return null;
        }else if(geometry.type=="polygon"){
            addFeaturePolygonToScene(feature,num);
            return geometry.getExtent();
        }else if(geometry.type=="polyline"){
            addFeaturePolylineToScene(feature,num);
            return geometry.getExtent();
        }
    }else
        return null;
}

/**
 * 为查询出来的要素在场景中添加位置图片
 * @param position 要素位置
 * @param feature 要素
 * @param num 结果序号
 */
function addFeatureImage(position,feature,num) {
    //创建场景中要素图标
    if(position!=null&&feature!=null&&num!=null){
        var imgPath;
        if(num==-1)
            imgPath = path+"/static/dist/img/map/icon_feature_0.png";
        else
            imgPath = getProjectPath() + "static/dist/img/map/icon_features_" + num.toString() + ".png";
        var LabelStyle = YcMap3D.Creator.CreateLabelStyle(0);
        var groupID = YcMap3D.ProjectTree.FindItem(configration.QueryIcoFolder);
        var featureImage = YcMap3D.Creator.CreateImageLabel(position,imgPath,LabelStyle,groupID,feature.attributes.OBJECTID);
        //创建图标点击信息弹窗
        var popup = null;
        if(businessType=="video"){
            var link = mapConfig.realIpPort + feature.attributes["Link"];
            var popupUrl = path + "/static/popup/map_videojk_fullscreen3d.html?name=" + feature.attributes["Name_CHN"] + "&&link=" + link;
            popup =  createPopupWindow("视频监控-" + feature.attributes["Name_CHN"],popupUrl,673,543,-1);
        }else if(businessType=="scene"){
            var linkurl = mapConfig.realIpPort + feature.attributes["Link"];
            var popupUrl = path + "/static/popup/map_dialog_panorama.html?name=" +  feature.attributes["Name_CHN"] + "&&linkurl=" + linkurl;
            popup =  createPopupWindow("全景-" + feature.attributes["Name_CHN"],popupUrl,643,527,-1);
        }else{
            var pagePath = path + "/static/popup/map_dialog_a1.html?id=" + featureImage.ID + "&&attributes=" + JSON.stringify(feature.attributes);
            popup = YcMap3D.Creator.CreatePopupMessage("要素属性",pagePath,10,10,352,350,-1);
        }
        popup.ShowCaption = false;
        popup.AllowResize = false;
        popup.AllowDrag = true;
        featureImage.Message.MessageID = popup.ID;
    }
}

/**
 * 面查询结果绘制到三维场景中并高亮
 * @param feature ArcGIS线要素
 * @param num 结果序号
 */
function addFeaturePolygonToScene(feature,num) {
    var rings = feature.geometry.rings;
    if(rings!=null&&rings.length>0){
        for(var i=0;i<rings.length;i++){
            var ring = rings[i];
            var points = convert2DTo3DCoord(ring);
            var ringGeometry = YcMap3D.Creator.GeometryCreator.CreateLinearRingGeometry(points);
            var groupID = YcMap3D.ProjectTree.FindItem(configration.QueryDrawFolder);
            var polygon = YcMap3D.Creator.CreatePolygon(ringGeometry,"#FF0000","#FF0000",2,groupID,feature.attributes.OBJECTID);
            var centerPosition = YcMap3D.Creator.CreatePosition(polygon.Geometry.Centroid.X,polygon.Geometry.Centroid.Y,0,0);
            addFeatureImage(centerPosition,feature,num);
        }
    }
}

/**
 * 线查询结果绘制到三维场景中并高亮
 * @param feature ArcGIS线要素
 * @param num 结果序号
 */
function addFeaturePolylineToScene(feature,num) {
    //获取路径
    var paths = feature.geometry.paths;
    if(paths!=null&&paths.length>0){
        for(var i=0;i<paths.length;i++){
            var points = convert2DTo3DCoord(paths[i]);
            var lineGeometry = YcMap3D.Creator.GeometryCreator.CreateLineStringGeometry(points);
            var groupID = YcMap3D.ProjectTree.FindItem(configration.QueryDrawFolder);
            var polyline = YcMap3D.Creator.CreatePolyline(lineGeometry,"#FF0000",2,groupID,feature.attributes.OBJECTID);
            var centerPointNum = Math.ceil(polyline.Geometry.Points.Count/2);
            var centerPoint = polyline.Geometry.Points.Item(centerPointNum);
            var centerPosition = YcMap3D.Creator.CreatePosition(centerPoint.X,centerPoint.Y,0,0);
            addFeatureImage(centerPosition,feature,num);
        }
    }
}

/**
 * 转换二维数组到三维坐标
 * @param coords 二维坐标数组
 * @returns {*} 三维坐标数组
 */
function convert2DTo3DCoord(coords) {
    if(coords!=null&&coords.length>0){
        var points = [];
        for(var j=0;j<coords.length;j++){
            points.push(coords[j][0]);
            points.push(coords[j][1]);
            points.push(0);
        }
        return points;
    }else
        return null;
}

/**
 * 转换三维数组到二维坐标
 * @param points 三维坐标数组
 * @returns {*} 二维坐标数组
 */
function convert3DTo2DCoord(points) {
    if(points!=null&&points.Count>0){
        var coords = [];
        for(var j=0;j<points.Count;j++){
            var coord = [];
            coord.push(points.Item(j).X);
            coord.push(points.Item(j).Y);
            coords.push(coord);
        }
        return coords;
    }else
        return null;
}

/**
 * 获取要素点的Position
 * @param feature 要素
 * @returns {*} Position
 */
function getPointFeaturePosition(feature) {
    var position;
    if(getRefernceType=="meter")
        position = YcMap3D.Creator.CreatePosition(feature.geometry.x,feature.geometry.y,0,0);
    else{
        var featureX = feature.geometry.getLongitude();
        var featureY = feature.geometry.getLatitude();
        position = YcMap3D.Creator.CreatePosition(featureX,featureY,0,0);
    }
    return position;
}

/**
 * 结果列表点击飞行到要素范围方法
 * @param featureExtent3D 要素范围
 */
function navigateSceneToFeatures(featureExtent3D) {
    if(featureExtent3D!=null&&featureExtent3D!=undefined){
        var centerx = (featureExtent3D.xmax + featureExtent3D.xmin) * 0.5;
        var centery = (featureExtent3D.ymax + featureExtent3D.ymin) * 0.5;
        var distanceY, distanceX;
        if (getRefernceType() == "meter") {
            distanceY = (featureExtent3D.ymax - featureExtent3D.ymin) * 0.5 / (Math.tan(10 / 180 * Math.PI));
            distanceX = (featureExtent3D.xmax - featureExtent3D.xmin) * 0.5 / (Math.tan(10 / 180 * Math.PI));
        } else {
            distanceY = (featureExtent3D.ymax - featureExtent3D.ymin) * 0.5 * 111111 / (Math.tan(10 / 180 * Math.PI));
            distanceX = (featureExtent3D.xmax - featureExtent3D.xmin) * 0.5 * 111111 / (Math.tan(10 / 180 * Math.PI));
        }
        var pos = YcMap3D.Creator.CreatePosition(centerx, centery, 0, 0, 0, -90, 0, 0);
        pos.distance = Math.max(distanceX, distanceY);
        YcMap3D.Navigate.FlyTo(pos, 14);
    }
}

/**
 * 结果列表点击飞行到要素范围方法
 * @param featureObjId 结果列表要素ID
 * @param sceneObjID 场景树中要素ID
 */
function navigateToSceneFeature(featureObjId,sceneObjID) {
    if(map3DInit&&mapOpt==3){//判断三维是否初始化及当前窗口是否是三维模式
        var isSceneClick = false;
        var selectFeature = null;
        if((sceneObjID==null||sceneObjID=="")&&featureObjId!=undefined){//判断是结果列表点击传入
            var selectID = findItemByNameInFolder(configration.QueryIcoFolder,featureObjId);
            selectFeature = YcMap3D.ProjectTree.GetObject(selectID);
        }
        else if(sceneObjID!=null&&sceneObjID!=""){//判断是场景点击传入
            selectFeature = YcMap3D.ProjectTree.GetObject(sceneObjID);
            isSceneClick = true;
        }
        //点击同一个对象直接返回
        if(selectFeature!=null&&currentSelectFeature!=null&&currentSelectFeature.ID==selectFeature.ID)
            return;

        //判断是否是专题图查询
        var ifThemeSearch = false;
        if(selectFeature!=null&&selectFeature.ObjectType==24){
            var imagePath = selectFeature.ImageFileName;
            if(imagePath.substring(imagePath.lastIndexOf("_")-7,imagePath.lastIndexOf("_"))=="feature")
                ifThemeSearch = true;
        }

        //判断是否存在已有高亮对象，去除原有高亮图标
        if(selectFeature!=null&&currentSelectFeature!=null&&currentSelectFeature.ID!=selectFeature.ID){
            var imagePath = currentSelectFeature.ImageFileName;
            currentSelectFeature.ImageFileName = imagePath.substr(0,imagePath.lastIndexOf(".")-1) + ".png";
        }
        if(selectFeature!=null&&selectFeature.ObjectType==24){//高亮点击对象
            var imagePath = selectFeature.ImageFileName;
            selectFeature.ImageFileName = imagePath.substr(0,imagePath.lastIndexOf(".")) + "h.png";

            if(isSceneClick&&!ifThemeSearch){//非专题图查询高亮结果列表图标
                var index = imagePath.substring(imagePath.lastIndexOf("_") + 1,imagePath.lastIndexOf("."));
                $('.no-'+index).parent().toggleClass('active').siblings().removeClass('active');
            }
            currentSelectFeature = selectFeature;
        }
        // flyToFeaturePositionAtHeight(selectFeature,1000);
        navigateSceneToFeatures(currentFeatureSetPos);
        selectFeature.Message.Activate();
    }
}

/**
 * 定位到要素的某一高度
 * @param feature 要素
 * @param height 高度
 */
function flyToFeaturePositionAtHeight(feature,height) {
    var yaw = YcMap3D.Navigate.GetPosition(0).Yaw;
    var flyPosition = YcMap3D.Creator.CreatePosition(feature.Position.X,feature.Position.Y,height,3,yaw,-90,0);
    YcMap3D.Navigate.FlyTo(flyPosition,14);
}

/**
 * 清空缓存文件夹进行下一次操作
 */
function clearFeatureSearchFolder() {
    deleteFolderObjects(configration.QueryIcoFolder);
    deleteFolderObjects(configration.QueryDrawFolder);
    //清除前一页要素相关记录
    currentSelectFeature = null;
    currentFeatureSetPos = null;
}

//------------------------------------------------------------------------------Identify查询------------------------------------------------------------------------//
//查询后，调用空间查询的结果展示

/**
 * 入口绘制查询区域
 */
function identifySpatialQuery3d() {
    clearFeatureSearchFolder();
    //空间查询面查询绘制操作
    DrawTool.activate(DrawTool.DrawType.TERRARECTANGLE);
    //空间查询面查询结果处理
    DrawTool.drawEndHandler = function (rectangel) {
        //矩形构建Ring
        var geoArr = new Array(rectangel.Left,rectangel.Top,0,rectangel.Left,rectangel.Bottom,0,rectangel.Right,rectangel.Bottom,0,rectangel.Right,rectangel.Top,0);
        var rectangelPolygon = YcMap3D.Creator.GeometryCreator.CreateLinearRingGeometry(geoArr);
        //删除绘制区域
        DrawTool.clear();
        //3d图形转2d图形，并做空间查询
        identifySpatialQuery3dArea(rectangelPolygon);
    };
}

/**
 * 将绘制区域传递回二维查询
 */
function identifySpatialQuery3dArea(geometry) {
    if(geometry!=null&&geometry.NumPoints>0){
        var polygon = new esri.geometry.Polygon();
        var coords = convert3DTo2DCoord(geometry.Points);
        polygon.addRing(coords);
        var geometryObj={geometry:null};
        geometryObj.geometry = polygon;
        IdentifyTool._onDrawEnd(geometryObj);
    }
}