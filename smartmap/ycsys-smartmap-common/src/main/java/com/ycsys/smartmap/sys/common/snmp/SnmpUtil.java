package com.ycsys.smartmap.sys.common.snmp;

import org.snmp4j.CommunityTarget;
import org.snmp4j.PDU;
import org.snmp4j.Snmp;
import org.snmp4j.TransportMapping;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.*;
import org.snmp4j.transport.DefaultUdpTransportMapping;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;

/**
 * Created by lixiaoxin on 16-12-14.
 * SNMP的基础类
 */
public class SnmpUtil {
    public static final int DEFAULT_VERSION = SnmpConstants.version2c;
    public static final String DEFAULT_PROTOCOL = "udp";
    public static final int DEFAULT_PORT = 161;
    public static final long DEFAULT_TIMEOUT = 3 * 1000L;
    public static final int DEFAULT_RETRY = 3;

    protected String ip;
    protected String community;

    protected static SnmpProperties props = SnmpProperties.loadProperties();


    public CommunityTarget createDefault(String ip, String community) {
        Address targetAddress = GenericAddress.parse(DEFAULT_PROTOCOL + ":" + ip + "/" + DEFAULT_PORT);
        CommunityTarget target = new CommunityTarget();
        target.setCommunity(new OctetString(community));
        target.setAddress(targetAddress);
        target.setVersion(DEFAULT_VERSION);
        target.setTimeout(DEFAULT_TIMEOUT);
        target.setRetries(DEFAULT_RETRY);
        return target;
    }

    public SnmpUtil(String ip, String community) {
        this.ip = ip;
        this.community = community;
    }

    @SuppressWarnings("rawtypes")
    public String snmpGet(String oid) throws IOException {
        CommunityTarget target = this.createDefault(ip, community);
        Snmp snmp= SnmpInstance.getInstance().getSnmp();
        // get PDU
        PDU pdu = new PDU();
        pdu.add(new VariableBinding(new OID(oid)));// pcName
        pdu.setType(PDU.GET);
        return readResponse(snmp.send(pdu, target));
    }

    @SuppressWarnings("rawtypes")
    public String readResponse(ResponseEvent respEvnt) {
        // 解析Response
        if (respEvnt != null && respEvnt.getResponse() != null) {
            Vector recVBs = respEvnt.getResponse().getVariableBindings();
            if (recVBs.size() > 0) {
                VariableBinding recVB = (VariableBinding) recVBs.elementAt(0);
                return recVB.getVariable().toString();
            }
        }
        return null;

    }

    @SuppressWarnings("rawtypes")
    public ArrayList<String> snmpWalk(String oid) {
        ArrayList<String> result = new ArrayList<String>();
        TransportMapping transport = null;
        Snmp snmp = null;

        try {
            CommunityTarget target = this.createDefault(ip, community);
            transport = new DefaultUdpTransportMapping();
            snmp = new Snmp(transport);
            transport.listen();

            PDU pdu = new PDU();
            OID targetOID = new OID(oid);
            pdu.add(new VariableBinding(targetOID));
            boolean finished = false;

            while (!finished) {
                VariableBinding vb = null;
                ResponseEvent respEvent = snmp.getNext(pdu, target);
                PDU response = respEvent.getResponse();
                if (null == response) {
                    finished = true;
                    break;
                } else {
                    vb = response.get(0);
                }
                // check finish
                finished = checkWalkFinished(targetOID, pdu, vb);
                if (!finished) {
                    result.add(vb.getVariable().toString());

                    pdu.setRequestID(new Integer32(0));
                    pdu.set(0, vb);
                } else {
                    //System.out.println("SNMP walk OID 结束.");
                    snmp.close();
                }
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            //System.out.println("SNMP walk Exception: " + e);
        } finally {
            if (snmp != null) {
                try {
                    snmp.close();
                } catch (IOException ex1) {
                    snmp = null;
                }
            }
        }
        return null;
    }


    public static boolean checkWalkFinished(OID targetOID, PDU pdu,
                                            VariableBinding vb) {
        boolean finished = false;
        if (pdu.getErrorStatus() != 0) {
            //System.out.println("[true] responsePDU.getErrorStatus() != 0 ");
            //System.out.println(pdu.getErrorStatusText());
            finished = true;
        } else if (vb.getOid() == null) {
            //System.out.println("[true] vb.getOid() == null");
            finished = true;
        } else if (vb.getOid().size() < targetOID.size()) {
            //System.out.println("[true] vb.getOid().size() < targetOID.size()");
            finished = true;
        } else if (targetOID.leftMostCompare(targetOID.size(), vb.getOid()) != 0) {
            //System.out.println("[true] targetOID.leftMostCompare() != 0");
            finished = true;
        } else if (Null.isExceptionSyntax(vb.getVariable().getSyntax())) {
            //          System.out
            //                  .println("[true] Null.isExceptionSyntax(vb.getVariable().getSyntax())");
            finished = true;
        } else if (vb.getOid().compareTo(targetOID) <= 0) {
            //          System.out.println("[true] Variable received is not "
            //                  + "lexicographic successor of requested " + "one:");
            //          System.out.println(vb.toString() + " <= " + targetOID);
            finished = true;
        }
        return finished;

    }
}