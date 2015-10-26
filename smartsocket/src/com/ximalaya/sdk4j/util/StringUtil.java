package com.ximalaya.sdk4j.util;

public class StringUtil {
	
	/**
	 * 分隔long数组里的各个元素，比如array为[1,2,3]，seperator为英文逗号，那么得到"1,2,3"
	 * @param array
	 * @param seperator
	 * @return
	 */
	public static String join(long[] array, String seperator) {
		if(array == null || array.length == 0 || seperator == null || seperator.isEmpty()) {
			throw new IllegalArgumentException("array should not empty, seperator should be specified");
		}
		
		StringBuilder strBuilder = new StringBuilder();
		for(long i: array) {
			strBuilder.append(i);
			strBuilder.append(seperator);
		}
		strBuilder.deleteCharAt(strBuilder.lastIndexOf(seperator));
		return strBuilder.toString();
	}
	
	/**
	 * 判断字符串是否为null或者长度为0
	 * @param s
	 * @return
	 */
	public static boolean isEmpty(String s) {
		return s == null || s.length() == 0;
	}
	
	/**
	 * 对于两个相同的字符串返回一致的hash code
	 * @param s
	 * @return
	 */
	public static int getConsistentHashCodeForSameStr(String s) {
		if(isEmpty(s)) {
			return 0;
		}
		
		final int prime = 31;
		int result = 1;
		int len = s.length();
		for(int i = 0; i < len; i++) {
			result += prime * result + s.codePointAt(i);
		}
		return result;
	}
}
