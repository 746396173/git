package com.ximalaya.sdk4j.model;

/**
 * 
 * @author will
 *
 */
public class SdkMetaInfo {
	private static final String TITLE = "喜马拉雅开放平台服务端Java SDK";
    private static final String VERSION = "1.1";
    private static final String AUTHOR_EMAIL = "will@ximalaya.com";
    
    public static final String getTitle() {
    	return TITLE;
    }
    
    public static final String getVersion() {
    	return VERSION;
    }
    
    public static final String getAuthorEmail() {
    	return AUTHOR_EMAIL;
    }
    
    public static final String getSdkMetaInfo() {
    	StringBuilder sdkMetaInfoBuilder = new StringBuilder();
    	sdkMetaInfoBuilder.append(TITLE);
    	sdkMetaInfoBuilder.append("{version: ");
    	sdkMetaInfoBuilder.append(VERSION);
    	sdkMetaInfoBuilder.append(", author_email: ");
    	sdkMetaInfoBuilder.append(AUTHOR_EMAIL);
    	sdkMetaInfoBuilder.append("}");
    	return sdkMetaInfoBuilder.toString();
    }
    
}
