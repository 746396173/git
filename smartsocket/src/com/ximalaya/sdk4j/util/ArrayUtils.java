package com.ximalaya.sdk4j.util;

import java.util.Iterator;
import java.util.Map;

import com.ximalaya.sdk4j.http.HttpParameter;

public class ArrayUtils {

	public static HttpParameter[] mapToArray(Map<String, String> map) {
		HttpParameter[] paramList = new HttpParameter[map.size()];
		Iterator<String> iter = map.keySet().iterator();
		int i = 0;
		while (iter.hasNext()) {
			String key = iter.next();
			String value = map.get(key);
			paramList[i++] = new HttpParameter(key, value);
		}
		return paramList;
	}
	
}
