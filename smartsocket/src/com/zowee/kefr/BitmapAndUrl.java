package com.zowee.kefr;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.graphics.Bitmap;

public class BitmapAndUrl {
	
	private Bitmap bitmap;
	private String url;
	private Long radioPlayCount;               // 电台累计被收听次数
	private Long updatedAt;                     // 电台更新时间
	private String programName;                 // 正在直播的节目名称
	private String radioName;                   // 电台名称
	
	
	public BitmapAndUrl(Bitmap bitmap, 
			String url,
			Long radioPlayCount, 
			Long updatedAt,
			String programName,
			String radioName){
		
		this.bitmap = bitmap;
		this.url    = url;
		this.radioPlayCount = radioPlayCount;
		this.updatedAt = updatedAt;
		this.programName = programName;
		this.radioName = radioName;
	}
	
	public Bitmap getBitmap() {
		return bitmap;
	}
	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}

	public Long getRadioPlayCount() {
		return radioPlayCount;
	}

	public void setRadioPlayCount(Long radioPlayCount) {
		this.radioPlayCount = radioPlayCount;
	}

	public Long getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Long updatedAt) {
		this.updatedAt = updatedAt;
	}

	public String getProgramName() {
		return programName;
	}

	public void setProgramName(String programName) {
		this.programName = programName;
	}

	public String getRadioName() {
		return radioName;
	}

	public void setRadioName(String radioName) {
		this.radioName = radioName;
	}
	
	
}
