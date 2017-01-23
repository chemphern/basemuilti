/**
 * Created by ChenLong on 2017/1/18.
 */

function videoView3D(feature) {
    var name = feature.FeatureAttributes.GetFeatureAttribute("名称").Value;
    var link = mapConfig.realIpPort + feature.FeatureAttributes.GetFeatureAttribute("链接").Value;
    // var desp = feature.FeatureAttributes.GetFeatureAttribute("简介").Value;
    var popupUrl = path + "/static/popup/map_videojk_fullscreen3d.html?name=" + name + "&&link=" + link;
    var popup = createPopupWindow("视频监控-" + name,popupUrl,673,543,-1);
    popup.ShowCaption = false;
    popup.AllowResize = false;
    popup.AllowDrag = true;
    YcMap3D.Window.ShowPopup(popup);
}