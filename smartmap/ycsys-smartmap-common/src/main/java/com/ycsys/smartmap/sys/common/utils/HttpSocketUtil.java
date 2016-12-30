package com.ycsys.smartmap.sys.common.utils;

import org.apache.http.HttpHost;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.protocol.HTTP;

import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * socket发送http请求
 * Created by lixiaoxin on 2016/12/28.
 */
public class HttpSocketUtil {

    public static Map<String,Object> sendRequest(String url){
        Map<String,Object> res = new HashMap<String,Object>();
        URI uri = URI.create(url);
        HttpHost httpHost = URIUtils.extractHost(uri);
        Socket socket = new Socket();
        InetAddress address = httpHost.getAddress();
        String host = httpHost.getHostName();
        int port = httpHost.getPort();
        if (port <= 0) {
            String name = httpHost.getSchemeName();
            if (name.equalsIgnoreCase("http")) {
                port = 80;
            } else if (name.equalsIgnoreCase("https")) {
                port = 443;
            } else {
            }
        }
        InputStream is = null;
        OutputStream os = null;
        try {
            if(address == null){
                address = InetAddress.getByName(host);
            }
            InetSocketAddress remoteAddress = new InetSocketAddress(address, port);
            long prev = System.currentTimeMillis();
            socket.connect(remoteAddress,0);
            os = socket.getOutputStream();
            Writer out = new OutputStreamWriter(os, HTTP.DEF_CONTENT_CHARSET);
            out.write("GET " + uri.getPath() + " HTTP/1.1\r\n");
            out.write("Host: " + host + "\r\n");
            out.write("Agent: whatever\r\n");
            out.write("Connection: Keep-Alive\r\n");
            out.write("User-Agent: Apache-HttpClient/4.5.2 (Java/1.8.0_20)\r\n");
            out.write("Accept-Encoding: gzip,deflate\r\n");
            out.write("\r\n");

            out.flush();

            is = socket.getInputStream();
            long final_time = System.currentTimeMillis();
            //读取响应头
            String header = "";
            String status = "";
            int contentLength = 0;
            do {
                header = readLine(is, 0);
                if (header.startsWith("Content-Length")) {
                    contentLength = Integer.parseInt(header.split(":")[1].trim());
                }
                if(header.startsWith("HTTP/")){
                    status = header.split(" ")[1];
                }
            } while (!header.equals("\r\n"));
            //读取响应体
            String body = readLine(is, contentLength);
            res.put("body",body);
            res.put("header",header);
            res.put("status",status);
            res.put("resTime",final_time -prev);
            res.put("code","1");
        } catch (IOException e) {
            e.printStackTrace();
            res.put("code","0");
            res.put("msg",e.getMessage());
        } finally {
            try {
                if(os !=null) {
                    os.close();
                    is.close();
                    socket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return res;
    }
    /**处理http响应**/
    public static String readLine(InputStream is, int contentLe) throws IOException {
        ArrayList lineByteList = new ArrayList();
        byte readByte;
        int total = 0;
        if (contentLe != 0) {
            do {
                readByte = (byte) is.read();
                lineByteList.add(Byte.valueOf(readByte));
                total++;
            } while (total < contentLe);//消息体读还未读完
        } else {
            do {
                readByte = (byte) is.read();
                lineByteList.add(Byte.valueOf(readByte));
            } while (readByte != 10);
        }

        byte[] tmpByteArr = new byte[lineByteList.size()];
        for (int i = 0; i < lineByteList.size(); i++) {
            tmpByteArr[i] = ((Byte) lineByteList.get(i)).byteValue();
        }
        lineByteList.clear();
        return new String(tmpByteArr, HTTP.DEF_CONTENT_CHARSET);
    }
}
