/**
 * Created by ChenLong.
 * Description:实现三维漫游功能模块
 * version: 1.0.0
 */

//本地测试数据
var testFlyPaths = [
	{
		"PathName":"全球到局部",
		"PathPoints":[
	    {"PointName":"全球","PointX":"106.57524633487628","PointY":"22.079792669836457","PointZ":"20628531.641310155","PointYaw":"359.1399415227776","PointPitch":"270.10074682134893","PointRoll":"0","StopTime":"2"}, 
	    {"PointName":"省份","PointX":"112.86059780004086","PointY":"27.855707929349705","PointZ":"1404402.745411029","PointYaw":"1.574821171425583","PointPitch":"270.3495135967773","PointRoll":"0","StopTime":"1"},
	    {"PointName":"市区","PointX":"113.02718793489686","PointY":"28.264607747875967","PointZ":"166578.40457822382","PointYaw":"1.6524713743031043","PointPitch":"270.41565363129166","PointRoll":"0","StopTime":"1"},
	    {"PointName":"兴趣点位置","PointX":"112.95883803120568","PointY":"28.44033672983405","PointZ":"4419.136391908862","PointYaw":"5.120318586626809","PointPitch":"300.150585112467","PointRoll":"0","StopTime":"1"},
		{"PointName":"兴趣点详情","PointX":"112.96013688655625","PointY":"28.45682186709337","PointZ":"215.6497399136424","PointYaw":"3.1207958970703657","PointPitch":"352.6637455379839","PointRoll":"0","StopTime":"3"}]
	},
	{
		"PathName":"沿线导览",
		"PathPoints":[
	    {"PointName":"second","PointX":"112.9515379879295","PointY":"28.197335166137573","PointZ":"100.459525356069207","PointYaw":"3.117281509249665","PointPitch":"356.6726605160142","PointRoll":"0","StopTime":"0"},
	    {"PointName":"third","PointX":"112.95212089822576","PointY":"28.204227087377546","PointZ":"100.459525356069207","PointYaw":"18.11752145417222","PointPitch":"350.9235684722502","PointRoll":"0","StopTime":"0"},
	    {"PointName":"fourth","PointX":"112.952969389072","PointY":"28.206688603787743","PointZ":"100.459525356069207","PointYaw":"7.11804556555262","PointPitch":"350.6736009830493","PointRoll":"0","StopTime":"0"},
		{"PointName":"five","PointX":"112.95363700594532","PointY":"28.213910446864346","PointZ":"100.459525356069207","PointYaw":"21.118435234701792","PointPitch":"351.1735038192258","PointRoll":"0","StopTime":"0"},
		{"PointName":"six","PointX":"112.95848263209609","PointY":"28.22939644689036","PointZ":"100.459525356069207","PointYaw":"21.118435234701792","PointPitch":"353.1737374572142","PointRoll":"0","StopTime":"0"},
		{"PointName":"seven","PointX":"112.9720271313696","PointY":"28.22992725286615","PointZ":"100.459525356069207","PointYaw":"86.62667129689862","PointPitch":"353.1738011699607","PointRoll":"0","StopTime":"0"},
		{"PointName":"first","PointX":"112.94647968145136","PointY":"28.21742386053261","PointZ":"112749.92671234533","PointYaw":"1.6149968582311657","PointPitch":"270.1715769901334","PointRoll":"0","StopTime":"0"}]
	}
];

//初始化加载飞行路径数据到面板
function getFlys(){
	//1.初始化Table
    $('#tableFxmy').bootstrapTable({
        //url: '/Home/GetDepartment',         //请求后台的URL（*）
        //method: 'get',                      //请求方式（*）
        toolbar: '#toolbar',                  //工具按钮用哪个容器
        striped: true,                        //是否显示行间隔色
        cache: false,                         //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
        pagination: true,                     //是否显示分页（*）
        sortable: false,                      //是否启用排序
        sortOrder: "asc",                     //排序方式
        //queryParams: oTableInit.queryParams,//传递参数（*）
        sidePagination: "client",             //分页方式：client客户端分页，server服务端分页（*）
        pageNumber:1,                         //初始化加载第一页，默认第一页
        pageSize: 10,                         //每页的记录行数（*）
        pageList: [10, 25, 50, 100],          //可供选择的每页的行数（*）
        search: false,                        //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
        strictSearch: false,
        showColumns: false,                   //是否显示所有的列
        showRefresh: false,                   //是否显示刷新按钮
        minimumCountColumns: 2,               //最少允许的列数
        clickToSelect: true,                  //是否启用点击选中行
        height: 500,                          //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
        uniqueId: "id",                       //每一行的唯一标识，一般为主键列
        showToggle:false,                     //是否显示详细视图和列表视图的切换按钮
        cardView: false,                      //是否显示详细视图
        detailView: false,                    //是否显示父子表
        columns: [{
            radio: true
        }, {
            field: 'id',
            title: '编号'
        }, {
            field: 'pathName',
            title: '路径名称'
        }, {
            field: 'pathDataSource',
            title: '路径数据源'
        }]
    });
    
    $('#tableFxmy').bootstrapTable('removeAll');
	for(var i=0;i<testFlyPaths.length;i++){
		$('#tableFxmy').bootstrapTable('append', {'id':i+1,'pathName':testFlyPaths[i].PathName,'pathDataSource':"testFlyPaths"});
	}
}

function getPathPointsFromName(name){
	var i;
	for(i=0;i<testFlyPaths.length;i++){
		if(testFlyPaths[i].PathName == name)
			return testFlyPaths[i];
	}
	return null;
}

//飞行对象
var presentation=null;

//点击开始飞行方法
function beginFly(){
	if(presentation!=null&&presentation.PresentationStatus==2){
		presentation.Resume();
	}else{
		var selectFly = $('#tableFxmy').bootstrapTable('getSelections');
		if(selectFly.length==1){
			var selectFlyObject = getPathPointsFromName(selectFly[0].pathName);
			if(selectFlyObject!=null){
				flightTrip.FolderID = createFolder(flightTrip.Folder);
				presentation = YcMap3D.Creator.CreatePresentation(flightTrip.FolderID,flightTrip.Folder);
				presentation.PlayAlgorithm=1;
				var i;
				for(i=0;i<selectFlyObject.PathPoints.length;i++){
					var point = selectFlyObject.PathPoints[i]
					var position = YcMap3D.Creator.CreatePosition(point.PointX,point.PointY,point.PointZ,3,point.PointYaw,point.PointPitch,point.PointRoll);
					presentation.CreateLocationStep(1,point.StopTime,point.PointName,position);
				}
				presentation.Play(0);
			}
		}else{
			alert("请选择一条记录！");
		}
	}
}

//点击暂停飞行方法
function pauseFly(){
	if(presentation!=null){
		presentation.Pause();
	}
}

//点击停止飞行方法
function stopFly(){
	if(presentation!=null){
		presentation.Stop();
	}
}