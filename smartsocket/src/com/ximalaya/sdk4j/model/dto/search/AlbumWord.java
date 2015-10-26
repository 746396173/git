package com.ximalaya.sdk4j.model.dto.search;

import com.alibaba.fastjson.JSONObject;
import com.ximalaya.sdk4j.http.HttpResponse;
import com.ximalaya.sdk4j.model.XimalayaException;
import com.ximalaya.sdk4j.model.XimalayaResponse;


public class AlbumWord extends XimalayaResponse{
	private static final long serialVersionUID = -8121832465530680886L;
	
	private long id;
	private String albumTitle;
	private String highlightAlbumTitle;
	private String categoryName;
	private String coverUrlSmall;
	
	public AlbumWord() {
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getAlbumTitle() {
		return albumTitle;
	}
	public void setAlbumTitle(String albumTitle) {
		this.albumTitle = albumTitle;
	}
	public String getHighlightAlbumTitle() {
		return highlightAlbumTitle;
	}
	public void setHighlightAlbumTitle(String highlightAlbumTitle) {
		this.highlightAlbumTitle = highlightAlbumTitle;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public String getCoverUrlSmall() {
		return coverUrlSmall;
	}
	public void setCoverUrlSmall(String coverUrlSmall) {
		this.coverUrlSmall = coverUrlSmall;
	}

	public AlbumWord(JSONObject json) throws XimalayaException {
		super();
		init(json);
	}
	
	public AlbumWord(HttpResponse response) throws XimalayaException {
		super(response);
		init(response.asJSONObject());
	}
	
	private void init(JSONObject json) throws XimalayaException {
		if(json != null) {
			id = json.getLong("id");
			albumTitle = json.getString("album_title");
			highlightAlbumTitle = json.getString("highlight_album_title");
			categoryName = json.getString("category_name");
			coverUrlSmall = json.getString("cover_url_small");
		}
	}
	
	@Override
	public String toString() {
		return "AlbumWord {id=" + id + ", albumTitle=" + albumTitle
				+ ", highlightAlbumTitle=" + highlightAlbumTitle + ", categoryName="
				+ categoryName + ", coverUrlSmall=" + coverUrlSmall + "}";
	}
}
