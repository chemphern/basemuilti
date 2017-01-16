/**
 * Created by ChenLong on 2017/1/11.
 */

function analysis3dSunlight() {
    if(mapOpt==3)
        AnalyseTool.activate(AnalyseTool.AnalyseType.SHADOW);
    else
        alert("请切换至三维地图模式下使用！");
}

function analysis3dFlood() {
    if(mapOpt==3)
        AnalyseTool.activate(AnalyseTool.AnalyseType.FLOOD);
    else
        alert("请切换至三维地图模式下使用！");
}

function analysis3dLineOfSight() {
    if(mapOpt==3)
        AnalyseTool.activate(AnalyseTool.AnalyseType.LINEOFSIGHT);
    else
        alert("请切换至三维地图模式下使用！");
}

function analysis3dViewShed() {
    if(mapOpt==3)
        AnalyseTool.activate(AnalyseTool.AnalyseType.VIEWSHED3D);
    else
        alert("请切换至三维地图模式下使用！");
}