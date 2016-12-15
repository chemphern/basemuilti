package com.ycsys.smartmap.sys.common.snmp;

import java.io.IOException;

import org.snmp4j.Snmp;
import org.snmp4j.TransportMapping;
import org.snmp4j.transport.DefaultUdpTransportMapping;


public class SnmpInstance {
	private Snmp snmp;
	private static SnmpInstance snmpInstance = new SnmpInstance();
	private SnmpInstance(){
		
	}
	public static SnmpInstance getInstance(){
		return snmpInstance;
	}
	public Snmp getSnmp() throws IOException{
		if(snmp == null){
			TransportMapping transport = new DefaultUdpTransportMapping();
	        snmp = new Snmp(transport);
	        transport.listen();
		}
		return snmp;
	}
	public void closeSnmp() throws IOException{
		snmp.close();
	}
}
