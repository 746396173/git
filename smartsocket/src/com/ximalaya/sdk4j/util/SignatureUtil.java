package com.ximalaya.sdk4j.util;

import java.util.Map;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ximalaya.sdk4j.http.HttpParameter;

/**
 * 签名计算工具类
 * 
 * @author will
 *
 */
public class SignatureUtil {
	
	private static final Logger LOG = LoggerFactory.getLogger(SignatureUtil.class);
	
	public static String caculateSignature(HttpParameter[] httpParams, String seed) {
		if(httpParams == null || httpParams.length == 0 || seed == null || seed.isEmpty()) {
			throw new IllegalArgumentException("httpParams and seed should not empty");
		}
		
		Map<String, String> paramsMap = new TreeMap<String, String> ();
		int len = httpParams.length - 1;		//排除sig参数
		for(int i = 0; i < len; i++) {
			HttpParameter curParam = httpParams[i];
			paramsMap.put(curParam.getName(), curParam.getValue());
		}
		
		StringBuilder paramsBuilder = new StringBuilder();
		for(Map.Entry<String, String> entry: paramsMap.entrySet()) {
			paramsBuilder.append(entry.getKey());
			paramsBuilder.append("=");
			paramsBuilder.append(entry.getValue());
			paramsBuilder.append("&");
		}
		paramsBuilder.deleteCharAt(paramsBuilder.length()-1);
		String sig = null;
		try {
			String paramsEncodedStr = BASE64Encoder.encode(paramsBuilder.toString());
			sig = CrypterUtil.hmacSHA1ToStr(paramsEncodedStr, seed);
			LOG.debug(String.format("caculateSignature, paramsStr: %s, paramsEncodedStr: %s, seed: %s, sig: %s", paramsBuilder.toString(), paramsEncodedStr, 
					seed, sig));
		} catch(Exception e) {
			String errorMsg = "exception occurs when caculate signature";
			LOG.error(errorMsg, e);
			throw new RuntimeException(errorMsg, e);
		}
		return sig;
	}

}
