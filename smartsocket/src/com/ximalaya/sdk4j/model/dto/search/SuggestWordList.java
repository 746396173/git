package com.ximalaya.sdk4j.model.dto.search;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ximalaya.sdk4j.http.HttpResponse;
import com.ximalaya.sdk4j.model.XimalayaException;
import com.ximalaya.sdk4j.model.XimalayaResponse;

public class SuggestWordList extends XimalayaResponse{
	private static final long serialVersionUID = 2721436489197473333L;
	
	private int albumTotalCount;
	private List<AlbumWord> albums;
	
	private int keywordTotalCount;
	private List<QueryWord> keywords;
	
	public SuggestWordList() {
	}
	
	public int getAlbumTotalCount() {
		return albumTotalCount;
	}
	public void setAlbumTotalCount(int albumTotalCount) {
		this.albumTotalCount = albumTotalCount;
	}
	public List<AlbumWord> getAlbums() {
		return albums;
	}
	public void setAlbums(List<AlbumWord> albums) {
		this.albums = albums;
	}
	public int getKeywordTotalCount() {
		return keywordTotalCount;
	}
	public void setKeywordTotalCount(int keywordTotalCount) {
		this.keywordTotalCount = keywordTotalCount;
	}
	public List<QueryWord> getKeywords() {
		return keywords;
	}
	public void setKeywords(List<QueryWord> keywords) {
		this.keywords = keywords;
	}

	public SuggestWordList(JSONObject json) throws XimalayaException {
		super();
		init(json);
	}

	public SuggestWordList(HttpResponse response) throws XimalayaException {
		init(response.asJSONObject());
	}
	
	private void init(JSONObject json) throws XimalayaException {
		if(json != null) {
			albumTotalCount = json.getIntValue("album_total_count");
			albums = parseAlbumWordList(json.getJSONArray("albums"));
			keywordTotalCount = json.getIntValue("keyword_total_count");
			keywords = parseQueryWordList(json.getJSONArray("keywords"));
		}
	}
	
	private List<AlbumWord> parseAlbumWordList(JSONArray jsonArray) 
			throws XimalayaException {
		List<AlbumWord> albumWords = new ArrayList<AlbumWord>();
		for(int i = 0; i < jsonArray.size(); i++) {
			albumWords.add(new AlbumWord(jsonArray.getJSONObject(i)));
 	 	}
		return albumWords;
	}
	
	private List<QueryWord> parseQueryWordList(JSONArray jsonArray) 
			throws XimalayaException {
		List<QueryWord> queryWords = new ArrayList<QueryWord>();
		for(int i = 0; i < jsonArray.size(); i++) {
			queryWords.add(new QueryWord(jsonArray.getJSONObject(i)));
 	 	}
		return queryWords;
	}
}
