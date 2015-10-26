package com.ximalaya.sdk4j.model.dto.album;

import com.alibaba.fastjson.JSONObject;
import com.ximalaya.sdk4j.http.HttpResponse;
import com.ximalaya.sdk4j.model.XimalayaException;
import com.ximalaya.sdk4j.model.XimalayaResponse;


public class SubordinatedAlbum extends XimalayaResponse{
	private static final long serialVersionUID = 3258103693586173720L;
	
	private Long id;                   // 专辑ID
	private String albumTitle;         // 专辑标题
	private String coverUrlSmall;      // 专辑封面小图
	private String coverUrlMiddle;     // 专辑封面中图
	private String coverUrlLarge;      // 专辑封面大图
	
	public SubordinatedAlbum() {
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getAlbumTitle() {
		return albumTitle;
	}
	public void setAlbumTitle(String albumTitle) {
		this.albumTitle = albumTitle;
	}
	public String getCoverUrlSmall() {
		return coverUrlSmall;
	}
	public void setCoverUrlSmall(String coverUrlSmall) {
		this.coverUrlSmall = coverUrlSmall;
	}
	public String getCoverUrlMiddle() {
		return coverUrlMiddle;
	}
	public void setCoverUrlMiddle(String coverUrlMiddle) {
		this.coverUrlMiddle = coverUrlMiddle;
	}
	public String getCoverUrlLarge() {
		return coverUrlLarge;
	}
	public void setCoverUrlLarge(String coverUrlLarge) {
		this.coverUrlLarge = coverUrlLarge;
	}
	
	public SubordinatedAlbum(JSONObject json) throws XimalayaException {
		super();
		init(json);
	}
	
	public SubordinatedAlbum(HttpResponse response) throws XimalayaException {
		super(response);
		init(response.asJSONObject());
	}
	
	private void init(JSONObject json) throws XimalayaException {
		if(json != null) {
			id = json.getLong("id");
			albumTitle = json.getString("album_title");
			coverUrlSmall = json.getString("cover_url_small");
			coverUrlMiddle = json.getString("cover_url_middle");
			coverUrlLarge = json.getString("cover_url_large");
		}
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.intValue());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(this == obj) {
			return true;
		}
		if(obj == null) {
			return false;
		}
		if(getClass() != obj.getClass()) {
			return false;
		}
		
		SubordinatedAlbum other = (SubordinatedAlbum) obj;
		if((id == null && other.id != null) 
			|| !id.equals(other.id)) {
			return false;
		}
		
		return true;
	}
}
