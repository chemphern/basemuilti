/**
 * Created by ChenLong on 2017/1/18.
 */

function panoramaView3D(feature) {
    var name = feature.FeatureAttributes.GetFeatureAttribute("名称").Value;
    var linkurl = mapConfig.realIpPort + feature.FeatureAttributes.GetFeatureAttribute("Link").Value;
    var desp = feature.FeatureAttributes.GetFeatureAttribute("简介").Value;
    var popupUrl = path + "/static/popup/map_dialog_panorama.html?name=" + name + "&&linkurl=" + linkurl + "&&desp=" + desp;
    var popup = createPopupWindow("全景-" + name,popupUrl,643,527,-1);
    popup.ShowCaption = false;
    popup.AllowResize = false;
    popup.AllowDrag = true;
    YcMap3D.Window.ShowPopup(popup);
}