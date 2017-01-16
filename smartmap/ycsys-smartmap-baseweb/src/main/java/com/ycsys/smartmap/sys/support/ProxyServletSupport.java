package com.ycsys.smartmap.sys.support;

import com.ycsys.smartmap.monitor.entity.ServiceRequest;
import com.ycsys.smartmap.monitor.service.ServiceRequestService;
import com.ycsys.smartmap.sys.common.config.Global;
import com.ycsys.smartmap.sys.common.utils.Base64Util;
import com.ycsys.smartmap.sys.entity.User;
import com.ycsys.smartmap.sys.util.NetWorkUtil;
import com.ycsys.smartmap.sys.util.SpringContextHolder;
import org.apache.http.*;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.AbortableHttpRequest;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.entity.BasicHttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicHttpEntityEnclosingRequest;
import org.apache.http.message.BasicHttpRequest;
import org.apache.http.message.HeaderGroup;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.Args;
import org.apache.http.util.CharArrayBuffer;
import org.apache.http.util.EntityUtils;
import org.mitre.dsmiley.httpproxy.ProxyServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.Constructor;
import java.net.HttpCookie;
import java.net.URI;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.util.*;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * http代理Servlet
 * Created by lixiaoxin on 2016/12/30.
 */
public class ProxyServletSupport extends HttpServlet {

    private static Logger log = LoggerFactory.getLogger(ProxyServletSupport.class);
      /* INIT PARAMETER NAME CONSTANTS */

    /** A boolean parameter name to enable logging of input and target URLs to the servlet log. */
    public static final String P_LOG = "log";

    /** A boolean parameter name to enable forwarding of the client IP  */
    public static final String P_FORWARDEDFOR = "forwardip";

    /** The parameter name for the target (destination) URI to proxy to. */
    /**默认的转发地址**/
    protected static final String P_TARGET_URI = "defaultTargetUri";
    protected static final String ATTR_TARGET_URI =
            ProxyServlet.class.getSimpleName() + ".targetUri";
    protected static final String ATTR_TARGET_HOST =
            ProxyServlet.class.getSimpleName() + ".targetHost";

  /* MISC */

    protected boolean doLog = false;
    protected boolean doForwardIP = true;
    /** User agents shouldn't send the url fragment but what if it does? */
    protected boolean doSendUrlFragment = true;

    //These next 3 are cached here, and should only be referred to in initialization logic. See the
    // ATTR_* parameters.
    /** From the configured parameter "targetUri". */
    protected String targetUri;
    protected URI targetUriObj;//new URI(targetUri)
    protected HttpHost targetHost;//URIUtils.extractHost(targetUriObj);

    private HttpClient proxyClient;

    @Override
    public String getServletInfo() {
        return "A proxy servlet by David Smiley, dsmiley@apache.org";
    }


    protected String getTargetUri(HttpServletRequest servletRequest) {
        return (String) servletRequest.getAttribute(ATTR_TARGET_URI);
    }

    private HttpHost getTargetHost(HttpServletRequest servletRequest) {
        return (HttpHost) servletRequest.getAttribute(ATTR_TARGET_HOST);
    }

    /**
     * Reads a configuration parameter. By default it reads servlet init parameters but
     * it can be overridden.
     */
    protected String getConfigParam(String key) {
        return getServletConfig().getInitParameter(key);
    }

    @Override
    public void init() throws ServletException {
        String doLogStr = getConfigParam(P_LOG);
        if (doLogStr != null) {
            this.doLog = Boolean.parseBoolean(doLogStr);
        }

        String doForwardIPString = getConfigParam(P_FORWARDEDFOR);
        if (doForwardIPString != null) {
            this.doForwardIP = Boolean.parseBoolean(doForwardIPString);
        }

        //initTarget();//sets target*

        HttpParams hcParams = new BasicHttpParams();
        hcParams.setParameter(ClientPNames.COOKIE_POLICY, CookiePolicy.IGNORE_COOKIES);
        readConfigParam(hcParams, ClientPNames.HANDLE_REDIRECTS, Boolean.class);
        proxyClient = createHttpClient(hcParams);
    }

    /**
     * 初始化目标
     * **/
//    protected void initTarget() throws ServletException {
//        targetUri = getConfigParam(P_TARGET_URI);
//        if (targetUri == null)
//            throw new ServletException(P_TARGET_URI+" is required.");
//        //test it's valid
//        try {
//            targetUriObj = new URI(targetUri);
//        } catch (Exception e) {
//            throw new ServletException("Trying to process targetUri init parameter: "+e,e);
//        }
//        targetHost = URIUtils.extractHost(targetUriObj);
//    }

    /**初始化目标地址**/
    private void initTargetUri(HttpServletRequest servletRequest) throws ServletException {
        String uri = servletRequest.getRequestURI();
        String[] urisplit =  uri.split("/");
        String place = urisplit[3];
        if(place.equals("rest") || place.equals("services")){
            targetUri = getConfigParam(P_TARGET_URI);
        }else{
            //该路径中使用了base64编码host和port以及contextPath
            String final_place = Base64Util.decode(place);
            targetUri = "http://" + final_place;//+"/" + urisplit[4];
        }
        try {
            targetUriObj = new URI(targetUri);
        } catch (Exception e) {
            throw new ServletException("初始化代理Uri发生异常！ "+e,e);
        }
        targetHost = URIUtils.extractHost(targetUriObj);
    }

    /** Called from {@link #init(javax.servlet.ServletConfig)}. HttpClient offers many opportunities
     * for customization. By default,
     * <a href="http://hc.apache.org/httpcomponents-client-ga/httpclient/apidocs/org/apache/http/impl/client/SystemDefaultHttpClient.html">
     *   SystemDefaultHttpClient</a> is used if available, otherwise it falls
     * back to:
     * <pre>new DefaultHttpClient(new ThreadSafeClientConnManager(),hcParams)</pre>
     * SystemDefaultHttpClient uses PoolingClientConnectionManager. In any case, it should be thread-safe. */
    @SuppressWarnings({"unchecked", "deprecation"})
    protected HttpClient createHttpClient(HttpParams hcParams) {
        try {
            //as of HttpComponents v4.2, this class is better since it uses System
            // Properties:
            Class clientClazz = Class.forName("org.apache.http.impl.client.SystemDefaultHttpClient");
            Constructor constructor = clientClazz.getConstructor(HttpParams.class);
            return (HttpClient) constructor.newInstance(hcParams);
        } catch (ClassNotFoundException e) {
            //no problem; use v4.1 below
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        //Fallback on using older client:
        return new DefaultHttpClient(new ThreadSafeClientConnManager(), hcParams);
    }

    /** The http client used.
     * @see #createHttpClient(HttpParams) */
    protected HttpClient getProxyClient() {
        return proxyClient;
    }

    /** Reads a servlet config parameter by the name {@code hcParamName} of type {@code type}, and
     * set it in {@code hcParams}.
     */
    protected void readConfigParam(HttpParams hcParams, String hcParamName, Class type) {
        String val_str = getConfigParam(hcParamName);
        if (val_str == null)
            return;
        Object val_obj;
        if (type == String.class) {
            val_obj = val_str;
        } else {
            try {
                //noinspection unchecked
                val_obj = type.getMethod("valueOf",String.class).invoke(type,val_str);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        hcParams.setParameter(hcParamName,val_obj);
    }

    @Override
    public void destroy() {
        //As of HttpComponents v4.3, clients implement closeable
        if (proxyClient instanceof Closeable) {//TODO AutoCloseable in Java 1.6
            try {
                ((Closeable) proxyClient).close();
            } catch (IOException e) {
                log("While destroying servlet, shutting down HttpClient: "+e, e);
            }
        } else {
            //Older releases require we do this:
            if (proxyClient != null)
                proxyClient.getConnectionManager().shutdown();
        }
        super.destroy();
    }

    @Override
    protected void service(HttpServletRequest servletRequest, HttpServletResponse servletResponse)
            throws ServletException, IOException {
        /**初始化转发地址**/
        initTargetUri(servletRequest);

        Date nowDate = new Date();

        long startTime = System.currentTimeMillis();

        //initialize request attributes from caches if unset by a subclass by this point
        if (servletRequest.getAttribute(ATTR_TARGET_URI) == null) {
            servletRequest.setAttribute(ATTR_TARGET_URI, targetUri);
        }
        if (servletRequest.getAttribute(ATTR_TARGET_HOST) == null) {
            servletRequest.setAttribute(ATTR_TARGET_HOST, targetHost);
        }

        // Make the Request
        //note: we won't transfer the protocol version because I'm not sure it would truly be compatible
        String method = servletRequest.getMethod();
        String proxyRequestUri = rewriteUrlFromRequest(servletRequest);
        HttpRequest proxyRequest;
        //spec: RFC 2616, sec 4.3: either of these two headers signal that there is a message body.
        if (servletRequest.getHeader(HttpHeaders.CONTENT_LENGTH) != null ||
                servletRequest.getHeader(HttpHeaders.TRANSFER_ENCODING) != null) {
            HttpEntityEnclosingRequest eProxyRequest = new BasicHttpEntityEnclosingRequest(method, proxyRequestUri);
            // Add the input entity (streamed)
            //  note: we don't bother ensuring we close the servletInputStream since the container handles it
            eProxyRequest.setEntity(new InputStreamEntity(servletRequest.getInputStream(), servletRequest.getContentLength()));
            proxyRequest = eProxyRequest;
        } else
            proxyRequest = new BasicHttpRequest(method, proxyRequestUri);

        copyRequestHeaders(servletRequest, proxyRequest);

        setXForwardedForHeader(servletRequest, proxyRequest);

        HttpResponse proxyResponse = null;
        try {
            // Execute the request
            if (doLog) {
                log("proxy " + method + " uri: " + servletRequest.getRequestURI() + " -- " + proxyRequest.getRequestLine().getUri());
            }
            proxyResponse = proxyClient.execute(getTargetHost(servletRequest), proxyRequest);

            // Process the response
            int statusCode = proxyResponse.getStatusLine().getStatusCode();

            // copying response headers to make sure SESSIONID or other Cookie which comes from remote server
            // will be saved in client when the proxied url was redirected to another one.
            // see issue [#51](https://github.com/mitre/HTTP-Proxy-Servlet/issues/51)

            //设置响应头 服务端名称
            proxyResponse.setHeader("Server","Ycsys Smartmap Proxy Server");
            //设置源
            String orgin = servletRequest.getHeader("Origin");
            if(orgin != null){
                proxyResponse.setHeader("Access-Control-Allow-Origin",orgin);
            }
            copyResponseHeaders(proxyResponse, servletRequest, servletResponse);

            if (doResponseRedirectOrNotModifiedLogic(servletRequest, servletResponse, proxyResponse, statusCode)) {
                //the response is already "committed" now without any body to send
                return;
            }

            // Pass the response code. This method with the "reason phrase" is deprecated but it's the only way to pass the
            //  reason along too.
            //noinspection deprecation
            servletResponse.setStatus(statusCode, proxyResponse.getStatusLine().getReasonPhrase());

            // Send the content to the client
            long endTime = System.currentTimeMillis();
            //自定义
            saveArgisRequest(servletRequest,endTime-startTime,statusCode,nowDate);
            copyResponseEntity(servletRequest,proxyResponse, servletResponse);

        } catch (Exception e) {
            //abort request, according to best practice with HttpClient
            if (proxyRequest instanceof AbortableHttpRequest) {
                AbortableHttpRequest abortableHttpRequest = (AbortableHttpRequest) proxyRequest;
                abortableHttpRequest.abort();
            }
            if (e instanceof RuntimeException)
                throw (RuntimeException)e;
            if (e instanceof ServletException)
                throw (ServletException)e;
            //noinspection ConstantConditions
            if (e instanceof IOException)
                throw (IOException) e;
            throw new RuntimeException(e);

        } finally {
            // make sure the entire entity was consumed, so the connection is released
            if (proxyResponse != null)
                consumeQuietly(proxyResponse.getEntity());
            //Note: Don't need to close servlet outputStream:
            // http://stackoverflow.com/questions/1159168/should-one-call-close-on-httpservletresponse-getoutputstream-getwriter
        }
    }

    private void saveArgisRequest(HttpServletRequest servletRequest,long time,int statusCode,Date date) {
        try {
            String uri = URLDecoder.decode(servletRequest.getRequestURI(), "utf-8");
            String requestIp = NetWorkUtil.getIpAddress(servletRequest);
            int port = servletRequest.getLocalPort();
            String ip = servletRequest.getLocalAddr();
            User user = (User) servletRequest.getSession().getAttribute(Global.SESSION_USER);
            //服务rest api
            int index = uri.indexOf("/services/");
            String serviceName = null;
            String serviceType = null;
            String serviceMethod = "view";
            if (index > -1) {
                String[] serviceArr = uri.substring(index + 10, uri.length()).split("/");
                //确保有两截
                if (serviceArr.length > 1) {
                    serviceName = serviceArr[0];
                    serviceType = serviceArr[1];
                    if (serviceArr.length > 2) {
                        String method = serviceArr[2];
                        if (!method.matches("[0-9]+")) {
                            serviceMethod = method;
                        }
                        ;
                    }
                }
            }
            ServiceRequestService serviceRequestService = SpringContextHolder.getBean("serviceRequestService");
            ServiceRequest sq = new ServiceRequest();
            sq.setCreateDate(new Date());
            sq.setRequestUser(user);
            sq.setRequestDate(date);
            sq.setServerIp(ip);
            sq.setRequestUrl(uri);
            sq.setRequestIp(requestIp);
            sq.setReturnStatus(String.valueOf(statusCode));
            sq.setServerPort(port);
            sq.setServiceName(serviceName);
            sq.setServiceType(serviceType);
            sq.setServiceMethod(serviceMethod);
            sq.setReturnStatus(String.valueOf(statusCode));
            sq.setVisitTime(time);
            serviceRequestService.save(sq);
        }catch (Exception e){
            e.printStackTrace();
            log.error("请求列表异常：" + e.getMessage());
        }
    }

    protected boolean doResponseRedirectOrNotModifiedLogic(
            HttpServletRequest servletRequest, HttpServletResponse servletResponse,
            HttpResponse proxyResponse, int statusCode)
            throws ServletException, IOException {
        // Check if the proxy response is a redirect
        // The following code is adapted from org.tigris.noodle.filters.CheckForRedirect
        if (statusCode >= HttpServletResponse.SC_MULTIPLE_CHOICES /* 300 */
                && statusCode < HttpServletResponse.SC_NOT_MODIFIED /* 304 */) {
            Header locationHeader = proxyResponse.getLastHeader(HttpHeaders.LOCATION);
            if (locationHeader == null) {
                throw new ServletException("Received status code: " + statusCode
                        + " but no " + HttpHeaders.LOCATION + " header was found in the response");
            }
            // Modify the redirect to go to this proxy servlet rather that the proxied host
            String locStr = rewriteUrlFromResponse(servletRequest, locationHeader.getValue());

            servletResponse.sendRedirect(locStr);
            return true;
        }
        // 304 needs special handling.  See:
        // http://www.ics.uci.edu/pub/ietf/http/rfc1945.html#Code304
        // We get a 304 whenever passed an 'If-Modified-Since'
        // header and the data on disk has not changed; server
        // responds w/ a 304 saying I'm not going to send the
        // body because the file has not changed.
        if (statusCode == HttpServletResponse.SC_NOT_MODIFIED) {
            servletResponse.setIntHeader(HttpHeaders.CONTENT_LENGTH, 0);
            servletResponse.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
            return true;
        }
        return false;
    }

    protected void closeQuietly(Closeable closeable) {
        try {
            closeable.close();
        } catch (IOException e) {
            log(e.getMessage(), e);
        }
    }

    /** HttpClient v4.1 doesn't have the
     * {@link org.apache.http.util.EntityUtils#consumeQuietly(org.apache.http.HttpEntity)} method. */
    protected void consumeQuietly(HttpEntity entity) {
        try {
            EntityUtils.consume(entity);
        } catch (IOException e) {//ignore
            log(e.getMessage(), e);
        }
    }

    /** These are the "hop-by-hop" headers that should not be copied.
     * http://www.w3.org/Protocols/rfc2616/rfc2616-sec13.html
     * I use an HttpClient HeaderGroup class instead of Set<String> because this
     * approach does case insensitive lookup faster.
     */
    protected static final HeaderGroup hopByHopHeaders;
    static {
        hopByHopHeaders = new HeaderGroup();
        String[] headers = new String[] {
                "Connection", "Keep-Alive", "Proxy-Authenticate", "Proxy-Authorization",
                "TE", "Trailers", "Transfer-Encoding", "Upgrade" };
        for (String header : headers) {
            hopByHopHeaders.addHeader(new BasicHeader(header, null));
        }
    }

    /** Copy request headers from the servlet client to the proxy request. */
    protected void copyRequestHeaders(HttpServletRequest servletRequest, HttpRequest proxyRequest) {
        // Get an Enumeration of all of the header names sent by the client
        Enumeration enumerationOfHeaderNames = servletRequest.getHeaderNames();
        while (enumerationOfHeaderNames.hasMoreElements()) {
            String headerName = (String) enumerationOfHeaderNames.nextElement();
            //Instead the content-length is effectively set via InputStreamEntity
            if (headerName.equalsIgnoreCase(HttpHeaders.CONTENT_LENGTH))
                continue;
            if (hopByHopHeaders.containsHeader(headerName))
                continue;

            Enumeration headers = servletRequest.getHeaders(headerName);
            while (headers.hasMoreElements()) {//sometimes more than one value
                String headerValue = (String) headers.nextElement();
                // In case the proxy host is running multiple virtual servers,
                // rewrite the Host header to ensure that we get content from
                // the correct virtual server
                if (headerName.equalsIgnoreCase(HttpHeaders.HOST)) {
                    HttpHost host = getTargetHost(servletRequest);
                    headerValue = host.getHostName();
                    if (host.getPort() != -1)
                        headerValue += ":"+host.getPort();
                } else if (headerName.equalsIgnoreCase(org.apache.http.cookie.SM.COOKIE)) {
                    headerValue = getRealCookie(headerValue);
                }
                proxyRequest.addHeader(headerName, headerValue);
            }
        }
    }

    private void setXForwardedForHeader(HttpServletRequest servletRequest,
                                        HttpRequest proxyRequest) {
        String headerName = "X-Forwarded-For";
        if (doForwardIP) {
            String newHeader = servletRequest.getRemoteAddr();
            String existingHeader = servletRequest.getHeader(headerName);
            if (existingHeader != null) {
                newHeader = existingHeader + ", " + newHeader;
            }
            proxyRequest.setHeader(headerName, newHeader);
        }
    }

    /** Copy proxied response headers back to the servlet client. */
    protected void copyResponseHeaders(HttpResponse proxyResponse, HttpServletRequest servletRequest,
                                       HttpServletResponse servletResponse) {
        for (Header header : proxyResponse.getAllHeaders()) {
            if (hopByHopHeaders.containsHeader(header.getName()))
                continue;
            if (header.getName().equalsIgnoreCase(org.apache.http.cookie.SM.SET_COOKIE) ||
                    header.getName().equalsIgnoreCase(org.apache.http.cookie.SM.SET_COOKIE2)) {
                copyProxyCookie(servletRequest, servletResponse, header);
            } else {
                servletResponse.addHeader(header.getName(), header.getValue());
            }
        }
    }

    /** Copy cookie from the proxy to the servlet client.
     *  Replaces cookie path to local path and renames cookie to avoid collisions.
     */
    protected void copyProxyCookie(HttpServletRequest servletRequest,
                                   HttpServletResponse servletResponse, Header header) {
        List<HttpCookie> cookies = HttpCookie.parse(header.getValue());
        String path = servletRequest.getContextPath(); // path starts with / or is empty string
        path += servletRequest.getServletPath(); // servlet path starts with / or is empty string

        for (HttpCookie cookie : cookies) {
            //set cookie name prefixed w/ a proxy value so it won't collide w/ other cookies
            String proxyCookieName = getCookieNamePrefix() + cookie.getName();
            Cookie servletCookie = new Cookie(proxyCookieName, cookie.getValue());
            servletCookie.setComment(cookie.getComment());
            servletCookie.setMaxAge((int) cookie.getMaxAge());
            servletCookie.setPath(path); //set to the path of the proxy servlet
            // don't set cookie domain
            servletCookie.setSecure(cookie.getSecure());
            servletCookie.setVersion(cookie.getVersion());
            servletResponse.addCookie(servletCookie);
        }
    }

    /** Take any client cookies that were originally from the proxy and prepare them to send to the
     * proxy.  This relies on cookie headers being set correctly according to RFC 6265 Sec 5.4.
     * This also blocks any local cookies from being sent to the proxy.
     */
    protected String getRealCookie(String cookieValue) {
        StringBuilder escapedCookie = new StringBuilder();
        String cookies[] = cookieValue.split("; ");
        for (String cookie : cookies) {
            String cookieSplit[] = cookie.split("=");
            if (cookieSplit.length == 2) {
                String cookieName = cookieSplit[0];
                if (cookieName.startsWith(getCookieNamePrefix())) {
                    cookieName = cookieName.substring(getCookieNamePrefix().length());
                    if (escapedCookie.length() > 0) {
                        escapedCookie.append("; ");
                    }
                    escapedCookie.append(cookieName).append("=").append(cookieSplit[1]);
                }
            }

            cookieValue = escapedCookie.toString();
        }
        return cookieValue;
    }

    /** The string prefixing rewritten cookies. */
    protected String getCookieNamePrefix() {
        return "!Proxy!" + getServletConfig().getServletName();
    }

    /** Copy response body data (the entity) from the proxy to the servlet client. */
    protected void copyResponseEntity(HttpServletRequest request,HttpResponse proxyResponse, HttpServletResponse servletResponse) throws IOException {
        HttpEntity entity = proxyResponse.getEntity();
        //默认编码
        if(entity != null) {
            //当响应结果为gzip压缩，并且响应格式为xml时，进行特殊处理，替换里面的地址
            if (entity.getContentEncoding() != null && entity.getContentEncoding().getValue().toLowerCase().indexOf("gzip") > -1 && entity.getContentType().getValue().toLowerCase().indexOf("text/xml") > -1) {
                String content = null;
                //ByteArrayInputStream temp = new ByteArrayInputStream(data);
                Charset defaultCharset = Consts.UTF_8;
                GZIPInputStream zin = new GZIPInputStream(entity.getContent());
                try {
                    Args.check(entity.getContentLength() <= Integer.MAX_VALUE,
                            "HTTP entity too large to be buffered in memory");
                    int i = (int) entity.getContentLength();
                    if (i < 0) {
                        i = 4096;
                    }
                    Charset charset = null;
                    try {
                        final ContentType contentType = ContentType.get(entity);
                        if (contentType != null) {
                            charset = contentType.getCharset();
                        }
                    } catch (final UnsupportedCharsetException ex) {
                        if (defaultCharset == null) {
                            throw new UnsupportedEncodingException(ex.getMessage());
                        }
                    }
                    if (charset == null) {
                        charset = defaultCharset;
                    }
                    if (charset == null) {
                        charset = HTTP.DEF_CONTENT_CHARSET;
                    }
                    //读取为字符串
                    defaultCharset = charset;
                    final Reader reader = new InputStreamReader(zin, charset);
                    final CharArrayBuffer buffer = new CharArrayBuffer(i);
                    final char[] tmp = new char[1024];
                    int l;
                    while ((l = reader.read(tmp)) != -1) {
                        buffer.append(tmp, 0, l);
                    }
                    content = buffer.toString();
                } finally {
                    zin.close();
                }
                //替换里面的内容
                if (content != null) {
                    if (content.indexOf(targetUri) > -1) {
                        String baseUri = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/arcgis";
                        content = content.replaceAll(targetUri, baseUri);
                        BasicHttpEntity bentity = new BasicHttpEntity();
                        ByteArrayOutputStream out = new ByteArrayOutputStream();
                        GZIPOutputStream gzip = new GZIPOutputStream(out);
                        gzip.write(content.getBytes(defaultCharset));
                        gzip.close();
                        String gziplast = out.toString("ISO-8859-1");
                        InputStream in = new ByteArrayInputStream(gziplast.getBytes("ISO-8859-1"));
                        bentity.setContentLength(entity.getContentLength());
                        bentity.setContent(in);
                        bentity.setContentType(entity.getContentType());
                        bentity.setContentEncoding(entity.getContentEncoding());
                        OutputStream servletOutputStream = servletResponse.getOutputStream();
                        bentity.writeTo(servletOutputStream);
                    }
                }else {
                    OutputStream servletOutputStream = servletResponse.getOutputStream();
                    entity.writeTo(servletOutputStream);
                }
                //其他格式
            }else{
                OutputStream servletOutputStream = servletResponse.getOutputStream();
                entity.writeTo(servletOutputStream);
            }
        }
    }

    /** Reads the request URI from {@code servletRequest} and rewrites it, considering targetUri.
     * It's used to make the new request.
     * 重写URI
     */
    protected String rewriteUrlFromRequest(HttpServletRequest servletRequest) {
        StringBuilder uri = new StringBuilder(500);
        uri.append(getTargetUri(servletRequest));
        // Handle the path given to the servlet
        ///bWFwLmdlb3EuY24=/ArcGIS/rest/services/ChinaOnlineCommunity/MapServer
        ///rest/services/广州市_矢量地图/MapServer/legend
        //分两种情况重写地址
        String uris = servletRequest.getRequestURI();
        String[] urisplit =  uris.split("/");
        String place = urisplit[3];
        if(place.equals("rest") || place.equals("services")){
            if (servletRequest.getPathInfo() != null) {
                uri.append(encodeUriQuery(servletRequest.getPathInfo()));
            }
        }else{
            if (servletRequest.getPathInfo() != null) {
                String path = servletRequest.getPathInfo();
                StringBuilder sb = new StringBuilder();
                String [] paths =  path.split("/");
                for(int x = 2;x<paths.length;x++){
                    sb.append("/").append(paths[x]);
                }
                uri.append(encodeUriQuery(sb.toString()));
            }
        }
        // Handle the query string & fragment
        String queryString = servletRequest.getQueryString();//ex:(following '?'): name=value&foo=bar#fragment
        String fragment = null;
        //split off fragment from queryString, updating queryString if found
        if (queryString != null) {
            int fragIdx = queryString.indexOf('#');
            if (fragIdx >= 0) {
                fragment = queryString.substring(fragIdx + 1);
                queryString = queryString.substring(0,fragIdx);
            }
        }

        queryString = rewriteQueryStringFromRequest(servletRequest, queryString);
        if (queryString != null && queryString.length() > 0) {
            uri.append('?');
            uri.append(encodeUriQuery(queryString));
        }

        if (doSendUrlFragment && fragment != null) {
            uri.append('#');
            uri.append(encodeUriQuery(fragment));
        }
        return uri.toString();
    }

    protected String rewriteQueryStringFromRequest(HttpServletRequest servletRequest, String queryString) {
        return queryString;
    }

    /** For a redirect response from the target server, this translates {@code theUrl} to redirect to
     * and translates it to one the original client can use. */
    protected String rewriteUrlFromResponse(HttpServletRequest servletRequest, String theUrl) {
        //TODO document example paths
        final String targetUri = getTargetUri(servletRequest);
        if (theUrl.startsWith(targetUri)) {
            String curUrl = servletRequest.getRequestURL().toString();//no query
            String pathInfo = servletRequest.getPathInfo();
            if (pathInfo != null) {
                assert curUrl.endsWith(pathInfo);
                curUrl = curUrl.substring(0,curUrl.length()-pathInfo.length());//take pathInfo off
            }
            theUrl = curUrl+theUrl.substring(targetUri.length());
        }
        return theUrl;
    }

    /** The target URI as configured. Not null. */
    public String getTargetUri() { return targetUri; }

    /**
     * Encodes characters in the query or fragment part of the URI.
     *
     * <p>Unfortunately, an incoming URI sometimes has characters disallowed by the spec.  HttpClient
     * insists that the outgoing proxied request has a valid URI because it uses Java's {@link URI}.
     * To be more forgiving, we must escape the problematic characters.  See the URI class for the
     * spec.
     *
     * @param in example: name=value&foo=bar#fragment
     */
    protected static CharSequence encodeUriQuery(CharSequence in) {
        //Note that I can't simply use URI.java to encode because it will escape pre-existing escaped things.
        StringBuilder outBuf = null;
        Formatter formatter = null;
        for(int i = 0; i < in.length(); i++) {
            char c = in.charAt(i);
            boolean escape = true;
            if (c < 128) {
                if (asciiQueryChars.get((int)c)) {
                    escape = false;
                }
            } else if (!Character.isISOControl(c) && !Character.isSpaceChar(c)) {//not-ascii
                escape = false;
            }
            if (!escape) {
                if (outBuf != null)
                    outBuf.append(c);
            } else {
                //escape
                if (outBuf == null) {
                    outBuf = new StringBuilder(in.length() + 5*3);
                    outBuf.append(in,0,i);
                    formatter = new Formatter(outBuf);
                }
                //leading %, 0 padded, width 2, capital hex
                formatter.format("%%%02X",(int)c);//TODO
            }
        }
        return outBuf != null ? outBuf : in;
    }

    protected static final BitSet asciiQueryChars;
    static {
        char[] c_unreserved = "_-!.~'()*".toCharArray();//plus alphanum
        char[] c_punct = ",;:$&+=".toCharArray();
        char[] c_reserved = "?/[]@".toCharArray();//plus punct

        asciiQueryChars = new BitSet(128);
        for(char c = 'a'; c <= 'z'; c++) asciiQueryChars.set((int)c);
        for(char c = 'A'; c <= 'Z'; c++) asciiQueryChars.set((int)c);
        for(char c = '0'; c <= '9'; c++) asciiQueryChars.set((int)c);
        for(char c : c_unreserved) asciiQueryChars.set((int)c);
        for(char c : c_punct) asciiQueryChars.set((int)c);
        for(char c : c_reserved) asciiQueryChars.set((int)c);

        asciiQueryChars.set((int)'%');//leave existing percent escapes in place
    }
}
