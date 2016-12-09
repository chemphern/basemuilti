/**
 * Created by ChenLong.
 * Description:实现三维场景飞行定位、飞行漫游和飞行管理
 * version: 1.0.0
 */

function locate3d(x,y){
	var current = YcMap3D.Navigate.GetPosition(3);
	var position = YcMap3D.Creator.CreatePosition(x,y,configration.LocateAltitude,3,current.Yaw,-90,current.Roll,0);
	YcMap3D.Navigate.SetPosition(position);
//	YcMap3D.Navigate.FlyTo(position,0);
}