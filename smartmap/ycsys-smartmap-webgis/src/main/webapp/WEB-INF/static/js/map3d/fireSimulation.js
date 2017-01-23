/**
 * Created by ChenLong on 2017/1/12.
 */

//-------------------------------------------------------------林火蔓延相关界面实现-------------------------------------------------//

//林火模拟相关全局参数
var FireSimulationGlobe={
    "SimulationFolder":HiddenGroup+ "\\林火模拟文件夹",
    "SimulationFolderID":"",
    "StateFlag":"stoped",
    "FireEffect":"",
    "SmokeEffect":"",
    "LittleFireEffect":""
};

//声明林火蔓延对象
var fireSimulation = null;

/**
 * 开始林火蔓延分析
 */
function startSimulation(){
    if(mapOpt!=3){
        alert("请在三维地图下使用！");
        return;
    }
    //获取起火点对象ID
    var startFireID = YcMap3D.ProjectTree.FindItem(FireSimulationGlobe.SimulationFolder + "\\起火点");
    if(startFireID==""||startFireID==null||startFireID==undefined){
        alert("请先拾取起火点位置！");
        return
    }
    //判断上一次林火蔓延是否没有完成
    if(startFireID != "" && FireSimulationGlobe.StateFlag=="stoped"){
        //判断参数是否填写正确
        {
            var D = $("#fireGanHanMa").val();//干旱码（无因次量）
            if(D==null||D==""){
                alert("干旱码必须大于0且小于1");
                return;
            }
            var T = $("#fireWenDu").val();//温度
            if(T==null||T==""){
                alert("温度为数字类型，请正确填入");
                return;
            }
            var H = $("#fireShiDu").val();//湿度
            if(H==null||H==""||H<=0||H>=1){
                alert("湿度必须大于0且小于1");
                return;
            }
            var U = $("#fireFengSu").val();//风速
            if(U==null||U==""){
                alert("风速数字类型，请正确填入");
                return;
            }
            var WD = $("#fireFengXiang").val();//风向
            if(WD=="东"||WD=="南"||WD=="西"||WD=="北"||WD=="东南"||WD=="东北"||WD=="西南"||WD=="西北"){
                WD = WD;
            }
            else{
                alert("请正确填入风向（eg:东、南、西、北、东南、东北、西南、西北");
                return;
            }
            var MT = $("#fireMNTime").val();//模拟时间
            if(MT==null||MT==""){
                alert("模拟时间为小时数，请正确填入");
                return;
            }
        }
        //飞行至起火点区域
        var startFire = YcMap3D.ProjectTree.GetObject(startFireID);
        YcMap3D.Navigate.FlyTo(startFire,0);

        //标识开始模拟状态
        FireSimulationGlobe.StateFlag = "running";

        //开始模拟
        fireSimulation = new YcGis.FireSimulation(D,T,H,U,WD,MT,startFire.Position);
        FireSimulationGlobe.SimulationFolderID=createFolder(FireSimulationGlobe.SimulationFolder);
        fireSimulation.beginSimulation(FireSimulationGlobe.FireEffect,FireSimulationGlobe.LittleFireEffect,FireSimulationGlobe.SmokeEffect,FireSimulationGlobe.SimulationFolderID);
    }else if(FireSimulationGlobe.StateFlag == "pausing"){
        alert("请先继续暂停的模拟或停止暂停的模拟！");
    }else{
        alert("请先停止正在进行的林火蔓延模拟！");
    }
}

/**
 * 暂停林火蔓延模拟
 */
function pauseSimulation(){
    if(mapOpt!=3){
        alert("请在三维地图下使用！");
        return;
    }
    if(FireSimulationGlobe.StateFlag == "running"){
        FireSimulationGlobe.StateFlag = "pausing";
    }else if(FireSimulationGlobe.StateFlag == "pausing")
        FireSimulationGlobe.StateFlag = "running";
}

/**
 * 停止林火蔓延模拟
 */
function stopSimulation(){
    if(mapOpt!=3){
        alert("请在三维地图下使用！");
        return;
    }
    //清空相关变量
    {
        currentFireGroup = null;
        nextFireGroup = null;
    }
    //再初始化林火模拟状态
    FireSimulationGlobe.StateFlag = "stoped";
    //清除场景中对象
    deleteItemsByName(FireSimulationGlobe.SimulationFolder);
    fireSimulation = null;
    //清空起始坐标输入框
    $('#firePosition').val("");

    $(".rtime").css('display','none');

    $('#fireEast').val(0.0);
    $('#fireEastSouth').val(0.0);
    $('#fireSouth').val(0.0);
    $('#fireWestSouth').val(0.0);
    $('#fireWest').val(0.0);
    $('#fireWestNorth').val(0.0);
    $('#fireNorth').val(0.0);
    $('#fireEastNorth').val(0.0);
}

/**
 * 拾取起火点位置按钮点击事件
 */
function getFireClick(){
    if(mapOpt!=3){
        alert("请在三维地图下使用！");
        return;
    }
    YcMap3D.AttachEvent("OnLButtonUp", getFireLocation);
    YcMap3D.Window.SetInputMode(1);
}

/**
 * 拾取初始起火点位置方法
 */
function getFireLocation(){
    if(mapOpt!=3){
        alert("请在三维地图下使用！");
        return;
    }
    var position = getMousePosition(-1);
    deleteItemsByName(FireSimulationGlobe.SimulationFolder);
    FireSimulationGlobe.SimulationFolderID=createFolder(FireSimulationGlobe.SimulationFolder);
    var fire = YcMap3D.Creator.CreateEffect(position,FireSimulationGlobe.FireEffect,FireSimulationGlobe.SimulationFolderID,"起火点");
    YcMap3D.DetachEvent("OnLButtonUp", getFireLocation);
    YcMap3D.Window.SetInputMode(0);
    document.getElementById("firePosition").value = "X:" + position.X + "," + "Y:" + position.Y + "," + "Z:" + position.Altitude;
    YcMap3D.Navigate.FlyTo(fire,0);
}

// function getInitSpeedR(){
//     var lizi = YcMap3D.ProjectTree.FindItem("DyingFire");
//     alert(YcMap3D.ProjectTree.GetObject(lizi).EffectXML);
// }

function getSimulationPic() {
    // if(mapOpt!=3){
    //     alert("请在三维地图下使用！");
    //     return;
    // }
    // if(fireSimulation!=null&&complete==true){
    //     for(var i=resultArea.length;i>=1;i--){
    //         var geoAreaB = resultArea[i].Geometry;
    //         var geoAreaS = resultArea[i].Geometry;
    //         var geo = geoAreaB.SpatialOperator.Difference(geoAreaS);
    //         alert(geo.GeometryTypeStr);
    //     }
    // }else{
    //     alert("请开始或等待林火蔓延完成！");
    // }
}

// var simulationPicColors = ["#FF0000","#FF4000","#EF800E","#EF9A0E","#EFB00E","#EFE00E","#C1EF0E","#16E0D3","#16AFE0","#168CE0","#1668E0","#1631E0","#6116E0","#AB16E0","#E016D3","#E016A0","#E01651","","","","","","","#945939"];


//-------------------------------------------------------------林火蔓延相关算法和实现-------------------------------------------------//
//声明GIS主对象
YcGis = function () { };

var complete = false;

var resultArea = [];

/**
 *
 * @param {Object} D   干旱码
 * @param {Object} T   温度
 * @param {Object} H   湿度
 * @param {Object} U   风速
 * @param {Object} WD  风向
 * @param {Object} MT  模拟时间
 * @param {Object} P   起火点位置
 */
YcGis.FireSimulation = function(D,T,H,U,WD,MT,P){

    //林火点对象
    function firePoint(position,index,direction,fireEffect){
        this.position = position;
        this.index = index;
        this.direction = direction;
        this.fireEffect = fireEffect;
    }

    //判断林火蔓延是否完成
    complete=false;

    //林火蔓延面数组
    resultArea = [];

    //林火蔓延火点数组
    var firePointArra=[];

    //林火蔓延范围
    var fireArea=null;

    //林火蔓延范围Geometry
    var fireAreaGeometry;

    //总模拟时间
    var totalSimulationTime = MT*3600;

    //当前模拟时间
    var simulationTime = 0;

    var effectFire = null;

    var effectSmoke = null;

    var effectLittleFire = null;

    var groupId = "";
    
    function stopFireSimulation() {
        //林火蔓延火点数组
        firePointArra=[];

        //林火蔓延范围
        fireArea=null;

        //总模拟时间
        totalSimulationTime = 0;

        //当前模拟时间
        simulationTime = 0;

        effectFire = null;

        effectSmoke = null;

        effectLittleFire = null;

        groupId = "";
    }

    /**
     * 获取初始速度方法（m/s）
     */
    function getNormalSpeed(direction){
        if(direction==getReflectWindDrection(WD))
            return 2*0.13*Math.exp(-0.405 + 0.987*Math.LN10*D - 0.0345*H + 0.0384*T + 0.0234*U)*1000/3600;
        else
            return 2*0.13*Math.exp(-0.405 + 0.987*Math.LN10*D - 0.0345*H + 0.0384*T)*1000/3600;
    }

    /**
     * 获取风向反作用
     * @param WD 风向
     * @returns {*}作用方向
     */
    function getReflectWindDrection(WD) {
        if(WD=="东")
            return "西";
        else if(WD=="南")
            return "北";
        else if(WD=="西")
            return "东";
        else if(WD=="北")
            return "南";
        else if(WD=="东南")
            return "西北";
        else if(WD=="西南")
            return "东北";
        else if(WD=="东北")
            return "西南";
        else if(WD=="西北")
            return "东南";
    }

    function slopeAndHeightObj(slope,height){
        this.slope = slope;
        this.height = height;
    }

    /**
     * 从某位置获取某方向上的坡度
     * @param {Object} position 位置
     * @param {Object} direction 方向
     * @param {Object} speed 速度
     */
    function getSlopeDirection(position,direction) {
        var distance = getNormalSpeed(direction);
        var PositionDirection = null;
        if(direction=="东"){
            PositionDirection = YcMap3D.Creator.CreatePosition(position.X,position.Y,position.Altitude,3,90);
        }else if(direction=="东东南"){
            PositionDirection = YcMap3D.Creator.CreatePosition(position.X,position.Y,position.Altitude,3,112.5);
        }else if(direction=="东南"){
            PositionDirection = YcMap3D.Creator.CreatePosition(position.X,position.Y,position.Altitude,3,135);
        }else if(direction=="东南南"){
            PositionDirection = YcMap3D.Creator.CreatePosition(position.X,position.Y,position.Altitude,3,157.5);
        }else if(direction=="南"){
            PositionDirection = YcMap3D.Creator.CreatePosition(position.X,position.Y,position.Altitude,3,180);
        }else if(direction=="南南西"){
            PositionDirection = YcMap3D.Creator.CreatePosition(position.X,position.Y,position.Altitude,3,202.5);
        }else if(direction=="西南"){
            PositionDirection = YcMap3D.Creator.CreatePosition(position.X,position.Y,position.Altitude,3,225);
        }else if(direction=="西西南"){
            PositionDirection = YcMap3D.Creator.CreatePosition(position.X,position.Y,position.Altitude,3,247.5);
        }else if(direction=="西"){
            PositionDirection = YcMap3D.Creator.CreatePosition(position.X,position.Y,position.Altitude,3,270);
        }else if(direction=="西西北"){
            PositionDirection = YcMap3D.Creator.CreatePosition(position.X,position.Y,position.Altitude,3,292.5);
        }else if(direction=="西北"){
            PositionDirection = YcMap3D.Creator.CreatePosition(position.X,position.Y,position.Altitude,3,315);
        }else if(direction=="西北北"){
            PositionDirection = YcMap3D.Creator.CreatePosition(position.X,position.Y,position.Altitude,3,337.5);
        }else if(direction=="北"){
            PositionDirection = YcMap3D.Creator.CreatePosition(position.X,position.Y,position.Altitude,3,0);
        }else if(direction=="北北东"){
            PositionDirection = YcMap3D.Creator.CreatePosition(position.X,position.Y,position.Altitude,3,22.5);
        }else if(direction=="东北"){
            PositionDirection = YcMap3D.Creator.CreatePosition(position.X,position.Y,position.Altitude,3,45);
        }else if(direction=="东北北"){
            PositionDirection = YcMap3D.Creator.CreatePosition(position.X,position.Y,position.Altitude,3,67.5);
        }
        //坐标横向移动
        var positionNow = position.Copy();
        var positionDis = positionNow.Move(distance,PositionDirection.Yaw,0);
        //坐标纵向到地标
        var positionTerr = YcMap3D.Terrain.GetGroundHeightInfo(positionDis.X,positionDis.Y,2,false).Position;
        //坐标高差
        var dis = positionTerr.Altitude - positionDis.Altitude;
        var slopeAndHeight = new slopeAndHeightObj(dis/distance,positionTerr.Altitude);
        return slopeAndHeight;
    }

    /**
     * 获取某位置某方向上的斜率
     */
    function getSlopedDirectionSpeed(slope,direction){
        return (getNormalSpeed(direction)*Math.exp(1.69*slope))/3;
    }

    /**
     * 获取某位置某方向上下一秒的火点位置
     */
    function getDirectionPosition(position,direction){
        var positionNow = position.Copy();
        var positionDis = null;
        var slopeAndHeight = getSlopeDirection(position,direction);
        var distance = getSlopedDirectionSpeed(slopeAndHeight.slope,direction);

        var pitch = 180/Math.PI*Math.atan(slopeAndHeight.slope);

        if(direction=="东"){
            positionDis = positionNow.Move(distance,90,pitch);
            $('#fireEast').val(distance);
        }else if(direction=="东东南"){
            positionDis = positionNow.Move(distance,112.5,pitch);
        }else if(direction=="东南"){
            positionDis = positionNow.Move(distance,135,pitch);
            $('#fireEastSouth').val(distance);
        }else if(direction=="东南南"){
            positionDis = positionNow.Move(distance,157.5,pitch);
        }else if(direction=="南"){
            positionDis = positionNow.Move(distance,180,pitch);
            $('#fireSouth').val(distance);
        }else if(direction=="南南西"){
            positionDis = positionNow.Move(distance,202.5,pitch);
        }else if(direction=="西南"){
            positionDis = positionNow.Move(distance,225,pitch);
            $('#fireWestSouth').val(distance);
        }else if(direction=="西西南"){
            positionDis = positionNow.Move(distance,247.5,pitch);
        }else if(direction=="西"){
            positionDis = positionNow.Move(distance,270,pitch);
            $('#fireWest').val(distance);
        }else if(direction=="西西北"){
            positionDis = positionNow.Move(distance,292.5,pitch);
        }else if(direction=="西北"){
            positionDis = positionNow.Move(distance,315,pitch);
            $('#fireWestNorth').val(distance);
        }else if(direction=="西北北"){
            positionDis = positionNow.Move(distance,337.5,pitch);
        }else if(direction=="北"){
            positionDis = positionNow.Move(distance,0,pitch);
            $('#fireNorth').val(distance);
        }else if(direction=="北北东"){
            positionDis = positionNow.Move(distance,22.5,pitch);
        }else if(direction=="东北"){
            positionDis = positionNow.Move(distance,45,pitch);
            $('#fireEastNorth').val(distance);
        }else if(direction=="东北北"){
            positionDis = positionNow.Move(distance,67.5,pitch);
        }

        positionDis.Altitude = slopeAndHeight.height;
        return positionDis;
    }

    /**
     * 林火蔓延功能入口-开始林火蔓延分析
     * @param {Object} fireEffect 林火粒子效果
     * @param {Object} folder 林火模拟文件夹
     */
    this.beginSimulation = function(fireEffect,littleFireEffect,smokeEffect,groupID){
        groupId = groupID;
        effectFire = fireEffect;
        effectSmoke = smokeEffect;
        effectLittleFire = littleFireEffect;
        YcMap3D.AttachEvent("OnFrame",fireSimulationFrameHandler);
        $("#fireRemainingTime").text(totalSimulationTime + " 秒");
        $(".rtime").css('display','block');
    };

    /**
     * 林火蔓延每一帧事件
     */
    function fireSimulationFrameHandler(){
        if(simulationTime<=totalSimulationTime&&FireSimulationGlobe.StateFlag != "pausing"&&FireSimulationGlobe.StateFlag != "stoped"){
            if(firePointArra.length==0){
                firePointArra= getFirstFireArea(P);
                var geoArr = getGeoArrFromPositonArra(firePointArra);
                fireAreaGeometry = YcMap3D.Creator.GeometryCreator.CreateLinearRingGeometry(geoArr);
                fireArea = YcMap3D.Creator.CreatePolygon(fireAreaGeometry,"#FF0000","#945939",2,groupId,"火烧区域");
                fireArea.FillStyle.Color.SetAlpha(0.1);
                var lableStyle = YcMap3D.Creator.CreateLabelStyle(0);
                YcMap3D.Creator.CreateTextLabel(P,"起火点",lableStyle,groupId,"文字标识");
            }else{
                var addSmoke = false;
                var addLittle = false;
                //UpdateFirePointArray
                for(var i=0;i<firePointArra.length;i++){
                    var fire = firePointArra[i];
                    var lerpIndex = 0;
                    if(i+1<firePointArra.length){
                        lerpIndex = i+1;
                    }
                    if((simulationTime+i)%7==0&&i%10==simulationTime%10&&addSmoke==false){
                        var lerpPercent = getRandom(0,100)/100;
                        var position = fire.position.Lerp(firePointArra[lerpIndex].position,lerpPercent);
                        createSmokeFromPosition(position);
                        addSmoke = true;
                    }else if((simulationTime+i)%9==0&&i%10==simulationTime%10&&addLittle==false){
                        var lerpPercent = getRandom(0,100)/100;
                        var position = fire.position.Lerp(firePointArra[lerpIndex].position,lerpPercent);
                        createLittleFromPosition(position);
                        addSmoke = true;
                    }
                    var position = getDirectionPosition(fire.position,fire.direction);
                    if(position!=null&&position.X!=NaN&&position.Y!=NaN){
                        updateFireAreaGeometry(position,fire.index);
                        firePointArra[i].position = position;
                        firePointArra[i].fireEffect.position = YcMap3D.Terrain.GetGroundHeightInfo(position.X,position.Y,2,false).Position;
                    }
                }
                //CreateNewFireArea
                if(simulationTime%300==0){
                    var geoLineArr = getGeoArrFromPositonArra(firePointArra);
                    var fireLineGeometry = YcMap3D.Creator.GeometryCreator.CreateLinearRingGeometry(geoLineArr);
                    var fireLineArea = YcMap3D.Creator.CreatePolygon(fireLineGeometry,"#FF0000","#945939",2,groupId,"火烧范围");
                    fireLineArea.FillStyle.Color.SetAlpha(0.2);
                    var text = simulationTime/60 + "分钟";
                    var lableStyle = YcMap3D.Creator.CreateLabelStyle(0);
                    YcMap3D.Creator.CreateTextLabel(firePointArra[0].position,text,lableStyle,groupId,"文字标识");
                }
            }
            //时间增加
            simulationTime += 1;
            $("#fireRemainingTime").text(totalSimulationTime-simulationTime + " 秒");
        }else if(simulationTime>totalSimulationTime){
            //结束循环计算过程
            YcMap3D.DetachEvent("OnFrame",fireSimulationFrameHandler);
            //隐藏时间
            $(".rtime").css('display','none');
            //加密结束点林火
            createEndingFireLine(firePointArra);
            //计算过火面积
            var areaFired = YcMap3D.Analysis.MeasureTerrainSurface(fireArea.Geometry,0).toFixed(2);
            alert("林火模拟完成！总过火面积："+ areaFired + "平方米");
        }
    }

    /**
     * 生产随机数
     * @param min 最小界限
     * @param max 最大界限
     * @returns {number} 随机数
     */
    function getRandom(min, max){
        var r = Math.random() * (max - min);
        var re = Math.round(r + min);
        re = Math.max(Math.min(re, max), min)

        return re;
    }

    /**
     * 在蔓延终止线创建蔓延火点
     * @param firePointArra
     */
    function createEndingFireLine(firePointArra){
        for(var i=0;i<firePointArra.length-1;i++){
            var fireStart = firePointArra[i];
            var fireEnd = firePointArra[i+1];
            createFireFromTwoFirePoint(fireStart.position,fireEnd.position);
        }
        var fireStart = firePointArra[firePointArra.length-1];
        var fireEnd = firePointArra[0];
        createFireFromTwoFirePoint(fireStart.position,fireEnd.position);
    }

    /**
     * 在两个火电之间插入火点
     * @param positionStart 起始点
     * @param positionEnd 终止点
     */
    function createFireFromTwoFirePoint(positionStart,positionEnd){
        var distance = positionStart.DistanceTo(positionEnd);
        var n=0;
        if(distance<12){
            n=2;
        }else if(distance>=12&&distance<=17){
            n=3;
        }else if(distance>=18&&distance<=23){
            n=4;
        }else if(distance>=24){
            n=Math.floor(distance/6);
        }
        var step = 1/n;
        for(var i=0;i<n;i++){
            var positonNew = positionStart.Lerp(positionEnd,step*i);
            positonNew.Altitude = YcMap3D.Terrain.GetGroundHeightInfo(positonNew.X,positonNew.Y,2,false).Position.Altitude;
            createFireFromPosition(positonNew);
        }
    }

    /**
     * 初始化十六个方向火点位置数组
     * @param position 起火点
     * @returns {Array} 16个蔓延方向位置数组
     */
    function getFirstFireArea(position){
        var firePointArra = [];

        position.Altitude = YcMap3D.Terrain.GetGroundHeightInfo(position.X,position.Y,2,false).Position.Altitude;

        var Ve = getDirectionPosition(position,"东");
        firePointArra.push(new firePoint(Ve,0,"东",createFireFromPosition(Ve)));

        var Vees = getDirectionPosition(position,"东东南");
        firePointArra.push(new firePoint(Vees,1,"东东南",createFireFromPosition(Vees)));

        var Ves = getDirectionPosition(position,"东南");
        firePointArra.push(new firePoint(Ves,2,"东南",createFireFromPosition(Ves)));

        var Vess = getDirectionPosition(position,"东南南");
        firePointArra.push(new firePoint(Vess,3,"东南南",createFireFromPosition(Vess)));

        var Vs = getDirectionPosition(position,"南");
        firePointArra.push(new firePoint(Vs,4,"南",createFireFromPosition(Vs)));

        var Vsws = getDirectionPosition(position,"南南西");
        firePointArra.push(new firePoint(Vsws,5,"南南西",createFireFromPosition(Vsws)));

        var Vws = getDirectionPosition(position,"西南");
        firePointArra.push(new firePoint(Vws,6,"西南",createFireFromPosition(Vws)));

        var Vwws = getDirectionPosition(position,"西西南");
        firePointArra.push(new firePoint(Vwws,7,"西西南",createFireFromPosition(Vwws)));

        var Vw = getDirectionPosition(position,"西");
        firePointArra.push(new firePoint(Vw,8,"西",createFireFromPosition(Vw)));

        var Vwwn = getDirectionPosition(position,"西西北");
        firePointArra.push(new firePoint(Vwwn,9,"西西北",createFireFromPosition(Vwwn)));

        var Vwn = getDirectionPosition(position,"西北");
        firePointArra.push(new firePoint(Vwn,10,"西北",createFireFromPosition(Vwn)));

        var Vwnn = getDirectionPosition(position,"西北北");
        firePointArra.push(new firePoint(Vwnn,11,"西北北",createFireFromPosition(Vwnn)));

        var Vn = getDirectionPosition(position,"北");
        firePointArra.push(new firePoint(Vn,12,"北",createFireFromPosition(Vn)));

        var Vnen = getDirectionPosition(position,"北北东");
        firePointArra.push(new firePoint(Vnen,13,"北北东",createFireFromPosition(Vnen)));

        var Ven = getDirectionPosition(position,"东北");
        firePointArra.push(new firePoint(Ven,14,"东北",createFireFromPosition(Ven)));

        var Vene = getDirectionPosition(position,"东北北");
        firePointArra.push(new firePoint(Vene,15,"东北北",createFireFromPosition(Vene)));

        return firePointArra;
    }

    /**
     * 在某一位置创建火焰效果
     * @param position 创建位置
     * @returns {*} 火焰效果对象
     */
    function createFireFromPosition(position){
        return YcMap3D.Creator.CreateEffect(position,effectFire,groupId,"起火点");
    }

    /**
     * 在某一位置创建烟雾效果
     * @param position 创建位置
     * @returns {*} 烟雾效果对象
     */
    function createSmokeFromPosition(position){
        return YcMap3D.Creator.CreateEffect(position,effectSmoke,groupId,"过火点烟");
    }

    /**
     * 在某一位置创建余火效果
     * @param position 创建位置
     * @returns {*} 烟雾效果对象
     */
    function createLittleFromPosition(position) {
        return YcMap3D.Creator.CreateEffect(position,effectLittleFire,groupId,"过火点火");
    }

    /**
     * 从Position数组得到Geo数组
     * @param firePointArra Position数组
     * @returns {Array} Geo数组
     */
    function getGeoArrFromPositonArra(firePointArra){
        var geoArra = [];
        for(var i=0;i<firePointArra.length;i++){
            geoArra.push(firePointArra[i].position.X);
            geoArra.push(firePointArra[i].position.Y);
            geoArra.push(firePointArra[i].position.Altitude);
        }
        return geoArra;
    }

    /**
     * 更新过火范围面
     * @param position 火点
     * @param index 火点索引
     */
    function updateFireAreaGeometry(position,index){
        var geoBak=fireArea.Geometry.Clone();
        fireArea.Geometry.StartEdit();
        fireArea.Geometry.Rings.Item(0).Points.Item(index).X = position.X;
        fireArea.Geometry.Rings.Item(0).Points.Item(index).Y = position.Y;
        fireArea.Geometry.Rings.Item(0).Points.Item(index).Z = position.Altitude;
        fireArea.Geometry.EndEdit();
        if (fireArea.Geometry.GeometryType == 6) {
            fireArea.Geometry = geoBak;
        }
    }
};


FireSimulationGlobe.FireEffect = "$$PARTICLE$$UserDefine: \r\n" +
    "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n" +
    " <Particle ID='Custom'><ParticleEmitter ID='ring' NumParticles='130' Texture='BlackSmokeSmall.png'>\r\n" +
    "<Emitter Rate='38' Shape='Disc' SpeedShape='Disc' Scale='2,2,2' Speed='1,1,1' />\r\n" +
    "<Cycle Value='1' />\r\n" +
    "<Sort Value='1' />\r\n" +
    "<Rotation Speed='1' Time='1' Initial='1' />\r\n" +
    "<Render Value='Billboard' />\r\n" +
    "<Gravity Value='0, 1, 0' />\r\n" +
    "<Force Value='0' OverrideRotation='0' />\r\n" +
    "<Position Value='0, 0, 0' />\r\n" +
    "<Life Value='3.51' />\r\n" +
    "<Speed Value='0.06' />\r\n" +
    "<Color Value='20,255,255,255' />\r\n" +
    "<Size Value='1.3,1.3' />\r\n" +
    "<Drag Value='1' />\r\n" +
    "<Blend Type='' />\r\n" +
    "<Fade FadeIn='0.22' FadeOut='0.1' MaxFade='0.18' />\r\n" +
    "</ParticleEmitter>\r\n" +
    "<ParticleEmitter ID='ring' NumParticles='130' Texture='CampFire.png'>\r\n" +
    "<Emitter Rate='38' Shape='Disc' SpeedShape='Disc' Scale='2,2,2' Speed='1,1,1' />\r\n" +
    "<Cycle Value='1' />\r\n" +
    "<Sort Value='1' />\r\n" +
    "<Rotation Speed='1' Time='1' Initial='1' />\r\n" +
    "<Render Value='Billboard' />\r\n" +
    "<Gravity Value='0, 1, 0' />\r\n" +
    "<Force Value='0' OverrideRotation='0' />\r\n" +
    "<Position Value='0, 0, 0' />\r\n" +
    "<Life Value='3.51' />\r\n" +
    "<Speed Value='0.06' />\r\n" +
    "<Color Value='20,255,255,255' />\r\n" +
    "<Size Value='1,1' />\r\n" +
    "<Drag Value='3' />\r\n" +
    "<Blend Type='' />\r\n" +
    "<Fade FadeIn='0.22' FadeOut='0.1' MaxFade='0.37' />\r\n" +
    "</ParticleEmitter>\r\n" +
    "<ParticleEmitter ID='ring' NumParticles='100' Texture='CampFireBrightSmall.png'>\r\n" +
    "<Emitter Rate='18' Shape='Cone' SpeedShape='Cone' Scale='1,1,1' Speed='1,1,1' />\r\n" +
    "<Cycle Value='1' />\r\n" +
    "<Sort Value='1' />\r\n" +
    "<Render Value='Billboard' />\r\n" +
    "<Gravity Value='0, 1, 0' />\r\n" +
    "<Force Value='0' OverrideRotation='0' />\r\n" +
    "<Position Value='0, 0, 0' />\r\n" +
    "<Life Value='3.06' />\r\n" +
    "<Speed Value='1.12' />\r\n" +
    "<Color Value='20,32,32,32' />\r\n" +
    "<Size Value='2.2,2.2' />\r\n" +
    "<Drag Value='3' />\r\n" +
    "<Blend Type='Add' />\r\n" +
    "<Fade FadeIn='0.56' FadeOut='0.08' MaxFade='0.42' />\r\n" +
    "</ParticleEmitter>\r\n" +
    "</Particle>";


FireSimulationGlobe.LittleFireEffect = "$$PARTICLE$$UserDefine: \r\n " +
    "<?xml version='1.0' encoding='UTF-8'?> \r\n " +
    "<Particle ID='Custom'><ParticleEmitter ID='ring' NumParticles='192' Texture='CampFireBright.png'>\r\n" +
    "<Emitter Rate='18' Shape='Ring' SpeedShape='Ring' Scale='0,0,0' Speed='1,1,1' />\r\n" +
    "<Cycle Value='1' />\r\n" +
    "<Sort Value='1' />\r\n" +
    "<Rotation Speed='1' Time='1' Initial='1' />\r\n" +
    "<Render Value='Billboard' />\r\n" +
    "<Gravity Value='0, 1, 0' />\r\n" +
    "<Force Value='0' OverrideRotation='0' />\r\n" +
    "<Position Value='0, 0, 0' />\r\n" +
    "<Life Value='3.06' />\r\n" +
    "<Speed Value='0.39' />\r\n" +
    "<Color Value='20,255,255,255' />\r\n" +
    "<Size Value='2.2,2.2' />\r\n" +
    "<Drag Value='0' />\r\n" +
    "<Blend Type='' />\r\n" +
    "<Fade FadeIn='0.26' FadeOut='0.02' MaxFade='0.22' />\r\n" +
    "</ParticleEmitter>\r\n" +
    "<ParticleEmitter ID='ring' NumParticles='177' Texture='GraySmoke.png'>\r\n" +
    "<Emitter Rate='18' Shape='Ring' SpeedShape='Ring' Scale='0,0,0' Speed='1,1,1' />\r\n" +
    "<Cycle Value='1' />\r\n" +
    "<Sort Value='1' />\r\n" +
    "<Rotation Speed='2' Time='1' Initial='1' />\r\n" +
    "<Render Value='Billboard' />\r\n" +
    "<Gravity Value='0, 1, 0' />\r\n" +
    "<Force Value='0' OverrideRotation='0' />\r\n" +
    "<Position Value='0, 0, 0' />\r\n" +
    "<Life Value='3.51' />\r\n" +
    "<Speed Value='0.25' />\r\n" +
    "<Color Value='20,255,255,255' />\r\n" +
    "<Size Value='2.5,2.5' />\r\n" +
    "<Drag Value='0' />\r\n" +
    "<Blend Type='' />\r\n" +
    "<Fade FadeIn='0.49' FadeOut='0.56' MaxFade='0.22' />\r\n" +
    "</ParticleEmitter>\r\n" +
    "<ParticleEmitter ID='ring' NumParticles='317' Texture='CampFireBright.png'>\r\n" +
    "<Emitter Rate='23' Shape='Ring' SpeedShape='Ring' Scale='0,0,0' Speed='1,1,1' />\r\n" +
    "<Cycle Value='1' />\r\n" +
    "<Sort Value='1' />\r\n" +
    "<Render Value='Billboard' />\r\n" +
    "<Gravity Value='0, 1, 0' />\r\n" +
    "<Force Value='0' OverrideRotation='0' />\r\n" +
    "<Position Value='0, 0, 0' />\r\n" +
    "<Life Value='3.75' />\r\n" +
    "<Speed Value='0.39' />\r\n" +
    "<Color Value='20,255,255,255' />\r\n" +
    "<Size Value='1.9,1.9' />\r\n" +
    "<Drag Value='0' />\r\n" +
    "<Blend Type='Add' />\r\n" +
    "<Fade FadeIn='0.5' FadeOut='0.06' MaxFade='0.13' />\r\n" +
    "</ParticleEmitter>\r\n" +
    "</Particle>";

FireSimulationGlobe.SmokeEffect = "$$PARTICLE$$UserDefine: \r\n " +
    "<?xml version='1.0' encoding='UTF-8'?> \r\n " +
    "<Particle ID='Custom'><ParticleEmitter ID='ring' NumParticles='130' Texture='WhiteSmokeLight.png'>\r\n" +
    "<Emitter Rate='13' Shape='Ring' SpeedShape='Ring' Scale='0,0,0' Speed='1,1,1' />\r\n" +
    "<Cycle Value='1' />\r\n" +
    "<Sort Value='1' />\r\n" +
    "<Rotation Speed='1' Time='2' Initial='0' />\r\n" +
    "<Render Value='Billboard' />\r\n" +
    "<Gravity Value='0, 1, 0' />\r\n" +
    "<Force Value='0' OverrideRotation='0' />\r\n" +
    "<Position Value='0, 0, 0' />\r\n" +
    "<Life Value='3.06' />\r\n<Speed Value='1.41' />\r\n" +
    "<Color Value='20,255,255,255' />\r\n" +
    "<Size Value='2.4,2.4' />\r\n" +
    "<Drag Value='1' />\r\n" +
    "<Blend Type='' />\r\n" +
    "<Fade FadeIn='0.47' FadeOut='0.65' MaxFade='0.28' />\r\n" +
    "</ParticleEmitter>\r\n" +
    "<ParticleEmitter ID='ring' NumParticles='62' Texture='CampFireBrightSmall.png'>\r\n" +
    "<Emitter Rate='8' Shape='Disc' SpeedShape='Disc' Scale='0.6,0.7,0.6' Speed='1,1,1' />\r\n" +
    "<Cycle Value='1' />\r\n" +
    "<Sort Value='1' />\r\n" +
    "<Render Value='Billboard' />\r\n" +
    "<Gravity Value='0, 2, 0' />\r\n" +
    "<Force Value='0' OverrideRotation='0' />\r\n" +
    "<Position Value='0, 0, 0' />\r\n" +
    "<Life Value='1.12' />\r\n" +
    "<Speed Value='1' />\r\n" +
    "<Color Value='20,255,255,255' />\r\n" +
    "<Size Value='2.1,2.1' />\r\n" +
    "<Drag Value='0' />\r\n" +
    "<Blend Type='' />\r\n" +
    "<Fade FadeIn='0.16' FadeOut='0.15' MaxFade='0.07' />\r\n" +
    "</ParticleEmitter>\r\n" +
    "</Particle>";


