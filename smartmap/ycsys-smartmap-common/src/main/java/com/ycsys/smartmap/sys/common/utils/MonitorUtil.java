package com.ycsys.smartmap.sys.common.utils;

import com.ycsys.smartmap.sys.common.config.parseobject.tomcat.TomcatStatusObject;
import com.ycsys.smartmap.sys.common.snmp.SnmpBase;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

/**
 * 监控工具类
 * Created by lixiaoxin on 2017/1/11.
 */
public class MonitorUtil {

    private static final String windowOid = ".1.3.6.1.2.1.1.1.0";
    /**
     * 测试TomcatStatus链接是否连通，是否能返回数据
     * **/
    public static boolean testTomcatStatus(String url,String username,String password){
        BasicCredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        AuthScope scope = new AuthScope(AuthScope.ANY_HOST, AuthScope.ANY_PORT, AuthScope.ANY_REALM);
        UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(username, password);
        credentialsProvider.setCredentials(scope, credentials);
        CloseableHttpClient httpClient = HttpClientBuilder.create().setDefaultCredentialsProvider(credentialsProvider).build();
        HttpGet httpGet = null;
        HttpResponse httpResponse = null;
        HttpEntity entity = null;
        httpGet = new HttpGet(url);
        try {
            httpResponse = httpClient.execute(httpGet);
            entity = httpResponse.getEntity();
            if (entity != null) {
                //判断格式是否正确
                String content_type = entity.getContentType().getValue();
                String[] contents = content_type.split(";");
                String type = contents[0];
                if (type != null) {
                    if (type.trim().equals("text/html")) {
                        return false;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 测试Snmp是否连通
     * **/
    public static boolean testSnmpStatus(String ip,String port,String communicate){
        try {
            SnmpBase base = new SnmpBase(ip, port, communicate);
            base.snmpGet(windowOid);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public static TomcatStatusObject getTomcatStatusInfo(String url,String username,String password){
        BasicCredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        AuthScope scope = new AuthScope(AuthScope.ANY_HOST, AuthScope.ANY_PORT, AuthScope.ANY_REALM);
        UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(username, password);
        credentialsProvider.setCredentials(scope, credentials);
        CloseableHttpClient httpClient = HttpClientBuilder.create().setDefaultCredentialsProvider(credentialsProvider).build();
        String result = "";
        HttpGet httpGet = null;
        HttpResponse httpResponse = null;
        HttpEntity entity = null;
        httpGet = new HttpGet(url);
        try {
            httpResponse = httpClient.execute(httpGet);
            entity = httpResponse.getEntity();
            if (entity != null) {
                //判断格式是否正确
                String content_type = entity.getContentType().getValue();
                String[] contents = content_type.split(";");
                String type = contents[0];
                if (type != null) {
                    if (type.trim().equals("text/html")) {
                        //System.out.println("验证失败");
                        return null;
                    }
                }
                result = EntityUtils.toString(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;//System.out.println("连接失败！");
        }
        TomcatStatusObject tomcatStatusObject = JaxbMapper.fromXml(result, TomcatStatusObject.class);
        return tomcatStatusObject;
    }
}
