package com.ximalaya.sdk4j.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class XimalayaConfig {
	private static Properties props = new Properties();
	private static final String CONFIG_FILE_NAME = "config.properties";
	static{
		InputStream configStream = ClassLoader.getSystemClassLoader().getResourceAsStream(CONFIG_FILE_NAME);
		if(configStream == null) {
			configStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(CONFIG_FILE_NAME);
		}
		try {
			props.load(configStream);
		} catch(IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static String getValue(String key){
		return props.getProperty(key);
	}
}
