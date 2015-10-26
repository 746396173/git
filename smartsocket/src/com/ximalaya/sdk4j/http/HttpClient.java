package com.ximalaya.sdk4j.http;

import java.io.IOException;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.DeleteMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.httpclient.protocol.Protocol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import android.util.Log;

import com.alibaba.fastjson.JSONException;
import com.ximalaya.sdk4j.model.MySSLSocketFactory;
import com.ximalaya.sdk4j.model.XimalayaException;

/**
 * HTTP客户端，用于完成GET/POST请求
 * @author will
 */
public class HttpClient implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8406097812276555308L;
	
	public static final int HTTP_STATUS_OK = 200;                     // 请求成功
	public static final int HTTP_STATUS_CREATED = 201;                // 创建成功
	public static final int HTTP_STATUS_ACCEPTED = 202;               // 更新成功
	public static final int HTTP_STATUS_PERMANENT_REDIRECT = 301;    // 请求永久重定向
	public static final int HTTP_STATUS_TEMPORAL_REDIRECT = 302;     // 请求临时重定向
	public static final int HTTP_SATUS_NOT_MODIFIED = 304;            // 未更改
	public static final int HTTP_STATUS_BAD_REQUEST = 400;            // 请求的地址不存在或者包含非法或者不支持的参数
	public static final int HTTP_STATUS_UNAUTHORIZED = 401;           // 未授权，因为用户没有进行身份认证
	public static final int HTTP_STATUS_FORBIDDEN = 403;               // 被禁止访问，因为用户被认定为没有访问特定资源的权限
	public static final int HTTP_STATUS_NOT_FOUND = 404;               // 请求的资源不存在
	public static final int HTTP_STATUS_UNPROCESSABLE_ENTITY = 422;   // 服务器理解请求，但其中包含非法参数
	public static final int HTTP_STATUS_TOO_MANY_REQUESTS = 429;      // 请求频率超配
	public static final int HTTP_STATUS_SERVER_ERROR = 500;            // 服务器错误
	public static final int HTTP_STATUS_BAD_GATEWAY = 502;             // 网关错误，服务器宕机或者在升级中
	
	private static final Logger LOG = LoggerFactory.getLogger(HttpClient.class);
	private org.apache.commons.httpclient.HttpClient client = null;
	private MultiThreadedHttpConnectionManager connectionManager;
	private static final int DEFAULT_RETRY_TIMES = 3;
	private static final int DEFAULT_MAX_CON_PER_HOST = 20;
	private static final int DEFAULT_CONTIMEOUT_MS = 15000;
	private static final int DEFAULT_SOTIMEOUT_MS = 15000;
	
	private String proxyHost;
	private int proxyPort;
	private String proxyAuthUser;
	private String proxyAuthPassword;

	public String getProxyHost() {
		return proxyHost;
	}

	/**
	 * Sets proxy host
	 * 
	 * @param proxyHost
	 */
	public void setProxyHost(String proxyHost) {
		this.proxyHost = proxyHost;
	}

	public int getProxyPort() {
		return proxyPort;
	}

	/**
	 * Sets proxy port
	 * 
	 * @param proxyPort
	 */
	public void setProxyPort(int proxyPort) {
		this.proxyPort = proxyPort;
	}

	public String getProxyAuthUser() {
		return proxyAuthUser;
	}

	/**
	 * Sets proxy authentication user
	 * 
	 * @param proxyAuthUser
	 */
	public void setProxyAuthUser(String proxyAuthUser) {
		this.proxyAuthUser = proxyAuthUser;
	}

	public String getProxyAuthPassword() {
		return proxyAuthPassword;
	}

	/**
	 * Sets proxy authentication password
	 * 
	 * @param proxyAuthPassword
	 */
	public void setProxyAuthPassword(String proxyAuthPassword) {
		this.proxyAuthPassword = proxyAuthPassword;
	}
	
	public HttpClient() {
		this(DEFAULT_MAX_CON_PER_HOST, DEFAULT_CONTIMEOUT_MS, DEFAULT_SOTIMEOUT_MS);
	}
	
	public HttpClient(int maxConPerHost, int conTimeOutMs, int soTimeOutMs) {
		connectionManager = new MultiThreadedHttpConnectionManager();
		HttpConnectionManagerParams params = connectionManager.getParams();
		params.setDefaultMaxConnectionsPerHost(maxConPerHost);
		params.setConnectionTimeout(conTimeOutMs);
		params.setSoTimeout(soTimeOutMs);

		HttpClientParams clientParams = new HttpClientParams();
		clientParams.setCookiePolicy(CookiePolicy.IGNORE_COOKIES);   // 忽略cookie 避免 Cookie rejected 警告
		client = new org.apache.commons.httpclient.HttpClient(clientParams, connectionManager);
		Protocol httpsProtocol = new Protocol("https", new MySSLSocketFactory(), 443);
		Protocol.registerProtocol("https", httpsProtocol);
		
		/*
		 * 支持proxy
		 */
		if (proxyHost != null && !proxyHost.equals("")) {
			client.getHostConfiguration().setProxy(proxyHost, proxyPort);
			client.getParams().setAuthenticationPreemptive(true);
			if (proxyAuthUser != null && !proxyAuthUser.equals("")) {
				client.getState().setProxyCredentials(
						AuthScope.ANY,
						new UsernamePasswordCredentials(proxyAuthUser,
								proxyAuthPassword));
				LOG.debug("Proxy AuthUser: " + proxyAuthUser);
				LOG.debug("Proxy AuthPassword: " + proxyAuthPassword);
			}
		}
	}

	/**
	 * 处理HTTP GET请求
	 */
	public HttpResponse get(String url) throws XimalayaException {
		return get(url, new HttpParameter[0]);
	}
	
	public HttpResponse get(String url, HttpParameter[] params) 
			throws XimalayaException {
		if (null != params && params.length > 0) {
			String encodedParams = HttpParameter.encodeParameters(params);
			if (-1 == url.indexOf("?")) {
				url += "?" + encodedParams;
			} else {
				url += "&" + encodedParams;
			}
		}
		Log.i("HttpResponse", "HttpResponse_1"+url);
		LOG.debug("GET:" + url);
		GetMethod getmethod = new GetMethod(url);
		return httpRequest(getmethod);
	}

	/**
	 * 处理HTTP DELETE请求
	 */
	public HttpResponse delete(String url, HttpParameter[] params, String token)
			throws XimalayaException {
		if (0 != params.length) {
			String encodedParams = HttpParameter.encodeParameters(params);
			if (-1 == url.indexOf("?")) {
				url += "?" + encodedParams;
			} else {
				url += "&" + encodedParams;
			}
		}
		DeleteMethod deleteMethod = new DeleteMethod(url);
		return httpRequest(deleteMethod);
	}

	public HttpResponse post(String url, HttpParameter[] params) throws XimalayaException {
		LOG.debug("POST" + url);
		PostMethod postMethod = new PostMethod(url);
		for (int i = 0; i < params.length; i++) {
			postMethod.addParameter(params[i].getName(), params[i].getValue());
		}
		HttpMethodParams param = postMethod.getParams();
		param.setContentCharset("UTF-8");
		return httpRequest(postMethod);
	}

	public HttpResponse httpRequest(HttpMethod method)
			throws XimalayaException {
		int responseCode = -1;
		try {
			method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
					new DefaultHttpMethodRetryHandler(DEFAULT_RETRY_TIMES, false));
			client.executeMethod(method);
			Header[] resHeader = method.getResponseHeaders();
			responseCode = method.getStatusCode();
			LOG.debug("Response:");
			LOG.debug("https StatusCode:" + String.valueOf(responseCode));
			
			for (Header header : resHeader) {
				LOG.debug(header.getName() + ":" + header.getValue());
			}
			HttpResponse response = new HttpResponse();
			response.setResponseAsString(method.getResponseBodyAsString());
			LOG.debug(response.toString());

			if (responseCode != HTTP_STATUS_OK) {
				try {
					throw new XimalayaException(getCause(responseCode), response.asJSONObject(), method.getStatusCode());
				} catch (JSONException e) {
					throw new XimalayaException("parse response json failed", e);
				}
			}
			return response;
		} catch (IOException ioe) {
			throw new XimalayaException(ioe.getMessage(), ioe, responseCode);
		} finally {
			method.releaseConnection();
		}
	}
	
	private static String getCause(int statusCode) {
		String cause = null;
		switch (statusCode) {
		case HTTP_SATUS_NOT_MODIFIED:
			break;
		case HTTP_STATUS_BAD_REQUEST:
			cause = "The request was invalid because request param missing or illegal.";
			break;
		case HTTP_STATUS_UNAUTHORIZED:
			cause = "Authentication credentials were missing or incorrect.";
			break;
		case HTTP_STATUS_FORBIDDEN:
			cause = "The request is understood, but it has been refused due to lack of permission.";
			break;
		case HTTP_STATUS_NOT_FOUND:
			cause = "The URI requested is invalid or the resource requested, such as a user, does not exists.";
			break;
		case HTTP_STATUS_SERVER_ERROR:
			cause = "Something is broken within the ximalaya openapi server.";
			break;
		case HTTP_STATUS_BAD_GATEWAY:
			cause = "Ximalaya openapi server is down or being upgraded.";
			break;
		default:
			cause = "";
		}
		return statusCode + ":" + cause;
	}
	
}
