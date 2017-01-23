/**
******************************二维地图配置**************************
*/
var mapConfig={
		//API地址
		jsapi:null,		//JSAPI地址
		esriCss:null,	//JSAPI样式地址
		esriCss2:null,	//JSAPI样式地址
		ipPort:null,	//不带http的IP端口，用于JSAPI服务配置。如：172.16.10.50:8080
		realIpPort:null,//根据网络环境决定的使用的IP端口。如：http://172.16.10.50:8080
		
		//数据图层地址
		baseMapUrl:null, //地图底图
		imgMapUrl:null,  //卫星底图
		interestingPOI:null, //兴趣点
		interestingNaturalPOI:null, //自然地名
		
		//功能图层地址
		proxyUrl:null,	//	ESRI代理地址
		geoServiceUrl:null,
		printService:null,
		intersectService:null, //叠加分析服务(相交)
		bufferService:null, //缓冲分析服务
		
		//网络环境设置
		_innerJsIpPort:"http://172.16.10.50:8080", //JsAPI内网IP端口
		_outerJsIpPort:"http://183.62.251.45:11180", //JsAPI外网IP端口
		_gisServer:"http://172.16.10.52:6080", //ArcGIS Server IP端口
		_backStageProxy:"/ycsys-smartmap-baseweb", //后端监控代理
		
		//后端代理总开关，是否使用后端代理监控
		isBackstageProxy:true,

		//三维相关
		flyServerUrl:null,
		flyFileName:"GuanZhouN.FLY",
    	serviceUrl:null,
		
		init:function(isInnerNet){
			var target = ["_backStageProxy","_gisServer"];
			var targetIndex = this.isBackstageProxy ? 0 : 1;
			//JsAPI配置
			var jsPath="/arcgis_js_api/3.18/init.js";
			var mid=jsPath.substring(0,jsPath.indexOf('init.js'));
			var cssPath;
			if(isInnerNet){
				this.realIpPort=this._innerJsIpPort;
				cssPath=this._innerJsIpPort + mid;
			}else{
				this.realIpPort=this._outerJsIpPort;
				cssPath=this._outerJsIpPort + mid;
                this.flyFileName = "GuanZhouW.FLY";
			}
			this.ipPort = this.realIpPort.substr(7);
			this.jsapi = this.realIpPort + jsPath;
			this.esriCss = cssPath + "dijit/themes/claro/claro.css";
			this.esriCss2 = cssPath + "esri/css/esri.css";
			this._backStageProxy = this.realIpPort + this._backStageProxy;
			
			//数据图层
			this.baseMapUrl=this[target[targetIndex]] + "/arcgis/rest/services/%E5%B9%BF%E5%B7%9E%E5%B8%82_%E7%9F%A2%E9%87%8F%E5%9C%B0%E5%9B%BE/MapServer";
			this.imgMapUrl=this[target[targetIndex]] + "/arcgis/rest/services/%E5%B9%BF%E5%B7%9E%E5%B8%82%E5%BD%B1%E5%83%8F%E5%9C%B0%E5%9B%BE/MapServer";
			this.interestingPOI=this[target[targetIndex]] + "/arcgis/rest/services/%E5%B9%BF%E5%B7%9E%E5%B8%82%E5%85%B4%E8%B6%A3%E7%82%B9/FeatureServer/0";
			this.interestingNaturalPOI=this[target[targetIndex]] + "/arcgis/rest/services/%E5%B9%BF%E5%B7%9E%E5%B8%82%E5%85%B4%E8%B6%A3%E7%82%B9/FeatureServer/1";
			
			//功能图层
			this.geoServiceUrl = this[target[targetIndex]] + "/arcgis/rest/services/Utilities/Geometry/GeometryServer";
			this.printService = this[target[targetIndex]] + "/arcgis/rest/services/Utilities/PrintingTools/GPServer/Export%20Web%20Map%20Task";
			this.intersectService = this[target[targetIndex]] + "/arcgis/rest/services/CostomGPService/IntersectTool/GPServer/Intersect";
			this.bufferService = this[target[targetIndex]] + "/arcgis/rest/services/CostomGPService/BufferTool/GPServer/Buffer";
			this.proxyUrl = this.realIpPort + "/Java/proxy.jsp";
			//this.proxyUrl = path + "/GisProxy/proxy.jsp";//本地ARCGIS代理测试使用,用此代理亦可

			//三维相关服务
			this.flyServerUrl = this.realIpPort;
			this.serviceUrl = this[target[targetIndex]] + "/arcgis/rest/services/广东省行政区划/MapServer";
		},
		//直接替换原生server地址为后端代理地址
		replaceWithProxyUrl:function(url){
			var ipPort=url.substring(0,url.indexOf('/',7));
			var path=url.substr(ipPort.length);
			var proxyUrl=this._backStageProxy + path;
			return proxyUrl;
		},
		//相对地址转换为绝对地址
		preAddProxyUrl:function(relativeUrl){
			if(this.isBackstageProxy){
				return this._backStageProxy + relativeUrl;
			}else{
				return this._gisServer + relativeUrl;
			}
		}
}

/**********二维视频全景弹窗设置*/
var popWindConfig = {
	scene:{
		width:640,
		height:480
	},
	video:{
		width:667,
		height:500
	}
}