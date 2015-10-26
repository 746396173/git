package com.ximalaya.sdk4j.model.dto.search;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.ximalaya.sdk4j.http.HttpResponse;
import com.ximalaya.sdk4j.model.XimalayaException;
import com.ximalaya.sdk4j.model.XimalayaResponse;

public class HotWord extends XimalayaResponse {
	private static final long serialVersionUID = 4791604041419033940L;
	
	private String searchword;
	private int degree;
	private int count;
	
	public HotWord() {
	}
	
	public String getSearchword() {
		return searchword;
	}
	public void setSearchword(String searchword) {
		this.searchword = searchword;
	}
	public int getDegree() {
		return degree;
	}
	public void setDegree(int degree) {
		this.degree = degree;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	
	public HotWord(JSONObject json) throws XimalayaException {
		super();
		init(json);
	}
	
	public HotWord(HttpResponse response) throws XimalayaException {
		super(response);
		init(response.asJSONObject());
	}
	
	private void init(JSONObject json) throws XimalayaException {
		if(json != null) {
			searchword = json.getString("search_word");
			degree = json.getIntValue("degree");
			count = json.getIntValue("count");
		}
	}
	
	public static List<HotWord> constructHotWords(HttpResponse response) throws XimalayaException {
		List<HotWord> hotWords = new ArrayList<HotWord> ();
	 	try {
	 	 	JSONArray hotWordJsonArray = response.asJSONArray();
	 	 	for(int i = 0; i < hotWordJsonArray.size(); i++) {
	 	 		hotWords.add(new HotWord(hotWordJsonArray.getJSONObject(i)));
	 	 	}
		} catch(JSONException jsone) {
			throw new XimalayaException(jsone.getMessage() + ":" + jsone.toString(), jsone);
		}
		return hotWords;
	}
	
	public static HotWordList constructHotWordList(HttpResponse response) throws XimalayaException {
		HotWordList hotWordList = new HotWordList();
		JSONObject albumListJsonObject = response.asJSONObject();
	 	try {
	 	 	List<HotWord> hotWords = new ArrayList<HotWord> ();
	 	 	JSONArray hotWordJsonArray = albumListJsonObject.getJSONArray("hot_words");
	 	 	for(int i = 0; i < hotWordJsonArray.size(); i++) {
	 	 		hotWords.add(new HotWord(hotWordJsonArray.getJSONObject(i)));
	 	 	}
	 	 	hotWordList.setHotWords(hotWords);
		} catch(JSONException jsone) {
			throw new XimalayaException(jsone.getMessage() + ":" + jsone.toString(), jsone);
		}
		return hotWordList;
	}
	
	@Override
	public String toString() {
		return "HotWord {searchword=" + searchword + ", degree=" + degree
				+ ", count=" + count + "}";
	}
}
