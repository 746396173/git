package com.ximalaya.sdk4j.model.dto.album;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ximalaya.sdk4j.http.HttpResponse;
import com.ximalaya.sdk4j.model.XimalayaException;

public class HumanRecommendAlbumList {
	private Long categoryID;       				 // 标签所属分类ID
	private String categoryName;				 // 分类名
	private String humanRecommendCategoryName;	 // 运营人工配置的分类名
	
	private List<Album> albums;    				 // 专辑列表

	public HumanRecommendAlbumList() {
	}
	
	public HumanRecommendAlbumList(JSONObject json) throws XimalayaException {
		super();
		init(json);
	}
	
	public HumanRecommendAlbumList(HttpResponse response) throws XimalayaException {
		init(response.asJSONObject());
	}
	
	private void init(JSONObject json) throws XimalayaException {
		if(json != null) {
			categoryID = json.getLong("category_id");
			categoryName = json.getString("category_name");
			humanRecommendCategoryName = json.getString("human_recommend_category_name");
			albums = parseAlbumList(json.getJSONArray("albums"));
		}
	}
	
	private List<Album> parseAlbumList(JSONArray albumJSONArray) throws XimalayaException {
		List<Album> albums = new ArrayList<Album>();
		if(albumJSONArray != null) {
			for(int i = 0; i < albumJSONArray.size(); i++) {
				albums.add(new Album(albumJSONArray.getJSONObject(i)));
			}
		}
		return albums;
	}

	public Long getCategoryID() {
		return categoryID;
	}
	public void setCategoryID(Long categoryID) {
		this.categoryID = categoryID;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public String getHumanRecommendCategoryName() {
		return humanRecommendCategoryName;
	}
	public void setHumanRecommendCategoryName(String humanRecommendCategoryName) {
		this.humanRecommendCategoryName = humanRecommendCategoryName;
	}
	public List<Album> getAlbums() {
		return albums;
	}
	public void setAlbums(List<Album> albums) {
		this.albums = albums;
	}
}
