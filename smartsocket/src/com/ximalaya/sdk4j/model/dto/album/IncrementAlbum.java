package com.ximalaya.sdk4j.model.dto.album;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.ximalaya.sdk4j.http.HttpResponse;
import com.ximalaya.sdk4j.model.XimalayaException;

/**
 * 增量专辑DTO模型
 * @author william
 *
 */
public class IncrementAlbum extends Album {
	private static final long serialVersionUID = -697459276083288173L;

	private boolean isOnline;   // 上线/下线
	
	public IncrementAlbum() {
	}
	
	public IncrementAlbum(HttpResponse response) throws XimalayaException {
		super(response);
		init(response.asJSONObject());
	}
	
	public IncrementAlbum(JSONObject json) throws XimalayaException {
		super(json);
		init(json);
	}
	
	private void init(JSONObject json) throws XimalayaException{
		if(json != null) {
			isOnline = json.getBoolean("is_online");
		}
	}
	
	public static IncrementAlbumList constructIncrementAlbumList(HttpResponse response) throws XimalayaException {
		IncrementAlbumList albumList = new IncrementAlbumList();
		JSONObject albumListJsonObject = response.asJSONObject();
 		try {
 			albumList.setTotalPage(albumListJsonObject.getIntValue("total_page"));
 	 		albumList.setTotalCount(albumListJsonObject.getIntValue("total_count"));
 	 		albumList.setCurrentPage(albumListJsonObject.getIntValue("current_page"));
 	 		albumList.setCategoryID(albumListJsonObject.getLong("category_id"));
 	 		albumList.setTagName(albumListJsonObject.getString("tag_name"));
 	 			
 	 		List<IncrementAlbum> albums = new ArrayList<IncrementAlbum>();
 	 		JSONArray albumsJsonArray = albumListJsonObject.getJSONArray("albums");
 	 		for(int i = 0; i < albumsJsonArray.size(); i++) {
 	 			albums.add(new IncrementAlbum(albumsJsonArray.getJSONObject(i)));
 	 		}
 	 		albumList.setAlbums(albums);
		} catch(JSONException jsone) {
			throw new XimalayaException(jsone.getMessage() + ":" + jsone.toString(), jsone);
		}
		return albumList;
	}
	
	public boolean isOnline() {
		return isOnline;
	}
	public void setOnline(boolean isOnline) {
		this.isOnline = isOnline;
	}
}
