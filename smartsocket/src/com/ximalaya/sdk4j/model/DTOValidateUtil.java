package com.ximalaya.sdk4j.model;

import com.ximalaya.sdk4j.util.StringUtil;

public class DTOValidateUtil {
	
	public static void validateCategoryID(long categoryID) {
		if(categoryID < 0) {
			throw new IllegalArgumentException("categoryID should >= 0");
		}
	}
	
	public static void validateCalcDimension(long calcDimension) {
		if(calcDimension < 0) {
			throw new IllegalArgumentException("calcDimension should >= 0");
		}
	}
	
	public static void validateRankType(int rankType) {
		if(!(rankType == 1 || rankType == 2)) {
			throw new IllegalArgumentException("This rankType is not supported");
		}
	}
	
	public static void validateRankKey(String rankKey) {
		if(StringUtil.isEmpty(rankKey)) {
			throw new IllegalArgumentException("RankKey should not be empty");
		}
	}
	
	public static void validateImageScale(int imageScale) {
		if(!(imageScale == 2 || imageScale == 3)) {
			throw new IllegalArgumentException("This imageScale is not supported");
		}
	}
	
	public static void validateChannel(String channel) {
		if(StringUtil.isEmpty(channel)) {
			throw new IllegalArgumentException("Channel should not be empty");
		}
	}
	
	public static void validateAppVersion(String appVersion) {
		if(StringUtil.isEmpty(appVersion)) {
			throw new IllegalArgumentException("AppVersion should not be empty");
		}
	}
	
	public static void validateColumnID(long columnID) {
		if(columnID < 0) {
			throw new IllegalArgumentException("ColumnID should >= 0");
		}
	}
	
	public static void validateRadioCount(int radioCount) {
		if(radioCount < 0) {
			throw new IllegalArgumentException("RadioCount should >= 0");
		}
	}
	
	public static void validateAlbumID(long albumID) {
		if(albumID <= 0) {
			throw new IllegalArgumentException("albumID should > 0");
		}
	}
	
	public static void validateRadioTypeAndProvinceCode(int radioType, String provinceCode) {
		if(radioType < 1 || radioType > 3) {
			throw new IllegalArgumentException("radioType should >= 1 and <= 3");
		}
		if(radioType == 2 && StringUtil.isEmpty(provinceCode)) {
			throw new IllegalArgumentException("provinceCode should be specified when radioType equals to 2");
		}
	}
	
	public static void validateRadioID(long radioID) {
		if(radioID <= 0) {
			throw new IllegalArgumentException("radio_id should > 0");
		}
	}
	
	public static void validateWeekday(int weekday) {
		if(weekday < 0 || weekday > 6) {
			throw new IllegalArgumentException("weekday should >= 0 and <= 6");
		}
	}

}
