package com.ycsys.smartmap.sys.util;

import com.ycsys.smartmap.sys.common.utils.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @description 获取客户端ip
 * @author lixiaoxin
 * @date 2017年1月3日
 */
public class NetWorkUtil {

	/**获取ip地址**/
	public static String getIpAddress(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("http_client_ip");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip != null && ip.indexOf(",") != -1) {
			ip = ip.substring(ip.lastIndexOf(",") + 1, ip.length()).trim();
		}
		if ("0:0:0:0:0:0:0:1".equals(ip)) {
			ip = "127.0.0.1";
		}
		return ip;
	}

	/**获取本机Mac地址**/
	public static String getLocalMacAddr(){
		String mac = null;
		try {
			NetworkInterface netInterface = NetworkInterface.getByInetAddress(InetAddress.getLocalHost());
			byte[] macAddr = netInterface.getHardwareAddress();
			List<String> list = new ArrayList<>();
			for (byte b : macAddr) {
				String str = Integer.toHexString((int) (b & 0xff));
				if (str.length() == 1) {
					str = "0" + str;
				}
				list.add(str);
			}
			mac = StringUtils.join(list,"-");
		}catch (Exception e){
			e.printStackTrace();
			return null;
		}
		return mac;
	}

	/**
	 * 获取本机ip地址
	 * 已通过：windows linux unix系统的测试
	 * **/
	public static String getLocalIp() {
		String sIP = "";
		InetAddress ip = null;
		try {
			boolean bFindIP = false;
			Enumeration<NetworkInterface> netInterfaces = (Enumeration<NetworkInterface>) NetworkInterface
					.getNetworkInterfaces();
			while (netInterfaces.hasMoreElements()) {
				if (bFindIP) {
					break;
				}
				NetworkInterface ni = (NetworkInterface) netInterfaces
						.nextElement();
				Enumeration<InetAddress> ips = ni.getInetAddresses();
				while (ips.hasMoreElements()) {
					ip = (InetAddress) ips.nextElement();
					if (!ip.isLoopbackAddress()
							&& ip.getHostAddress().matches(
							"(\\d{1,3}\\.){3}\\d{1,3}")) {
						bFindIP = true;
						break;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ip.getHostAddress();
		}
		if (null != ip) {
			sIP = ip.getHostAddress();
		}
		return sIP;
	}
	
	/**
	 * 判断内外网环境
	 * @param ip
	 * @return
	 */
	public static Boolean isInnerNet(String ip) {
		Boolean status=false;
		try {
			if(ip.equals("0:0:0:0:0:0:0:1") || ip.equals("127.0.0.1")){
				status=true;
			}else{
				//内网正则
				String reg = "(10|172|192)\\.([0-1][0-9]{0,2}|[2][0-5]{0,2}|[3-9][0-9]{0,1})\\.([0-1][0-9]{0,2}|[2][0-5]{0,2}|[3-9][0-9]{0,1})\\.([0-1][0-9]{0,2}|[2][0-5]{0,2}|[3-9][0-9]{0,1})";
			    Pattern p = Pattern.compile(reg);
			    Matcher matcher = p.matcher(ip);
			    status= matcher.find();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}
}
