package com.ycsys.smartmap.sys.service;

/**
 *	 发送短信相关服务
 * @author Administrator
 *
 */
public interface MessageService {
	/*
	 * 发送短信
	 */
	void sendMessage(String phoneNumber);
}
