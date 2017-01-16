var Map3DPlugDownoadPath = mapConfig.flyServerUrl + "/webgisResources/Skyline/Plugins/setup.exe";//插件下载地址

//三维场景相关配置对象
var configration = {
	"FlyPath":mapConfig.flyServerUrl + "/webgisResources/Skyline/Flys/" + mapConfig.flyFileName,                  //工程文件地址
	"QueryIcoFolder":"QueryFeatureIco",                                                                           //查询操作图标文件夹
    "QueryDrawFolder":"QueryDrawFolder",                                                                          //查询操作图形文件夹
    "WMSServiceFolder":"WMSService",                                                                              //场景中wms服务文件夹
	"WFSServiceFolder":"WFSService",                                                                              //场景中wfs服务文件夹
	"LocateAltitude":2000                                                                                         //场景默认定位高度
};

//行政区划快速定位相关配置
var quickLocationGlobal = {
    "ServiceUrl":mapConfig.serviceUrl,
    //省、市、区县服务图层号
    "ProvinceLayerIndex":1,
    "CityLayerIndex":0,
    "CountyLayerIndex":2,
    //省、市、区县中文名称字段
    "ProvinceField":"NAME",
    "CityField":"NAME",
    "CountyField":"NAME",
    //省、市、区县区域最大级数(用于二维定位操作)
    "ProvinceMapLevel":8,
    "CityMapLevel":10,
    "CountyMapLevel":19,
    //省、市、区县区域最大高度(用于三维定位操作)
    "ProvinceMapHeight":200000,
    "CityMapHeight":70000,
    "CountyMapHeight":0
};
