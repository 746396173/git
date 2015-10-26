package com.ximalaya.sdk4j.model.dto.album;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.ximalaya.sdk4j.http.HttpResponse;
import com.ximalaya.sdk4j.model.XimalayaException;
import com.ximalaya.sdk4j.model.XimalayaResponse;

public class UpdatedAlbum extends XimalayaResponse{

	private static final long serialVersionUID = -1452707668527135558L;
	
	private long id;
	private String kind;
	private long lastUptrackId;
	private String lastUptrackTitle;
	private String lastUptrackCoverPath;
	private long lastUptrackAt;
	
	public UpdatedAlbum() {
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getKind() {
		return kind;
	}
	public void setKind(String kind) {
		this.kind = kind;
	}
	public long getLastUptrackId() {
		return lastUptrackId;
	}
	public void setLastUptrackId(long lastUptrackId) {
		this.lastUptrackId = lastUptrackId;
	}
	public String getLastUptrackTitle() {
		return lastUptrackTitle;
	}
	public void setLastUptrackTitle(String lastUptrackTitle) {
		this.lastUptrackTitle = lastUptrackTitle;
	}
	public String getLastUptrackCoverPath() {
		return lastUptrackCoverPath;
	}
	public void setLastUptrackCoverPath(String lastUptrackCoverPath) {
		this.lastUptrackCoverPath = lastUptrackCoverPath;
	}
	public long getLastUptrackAt() {
		return lastUptrackAt;
	}
	public void setLastUptrackAt(long lastUptrackAt) {
		this.lastUptrackAt = lastUptrackAt;
	}
	
	public UpdatedAlbum(JSONObject json) throws XimalayaException {
		super();
		init(json);
	}
	
	public UpdatedAlbum(HttpResponse response) throws XimalayaException {
		super(response);
		init(response.asJSONObject());
	}
	
	private void init(JSONObject json) throws XimalayaException {
		if(json != null) {
			id = json.getLong("id");
			kind = json.getString("kind");
			lastUptrackId = json.getLong("last_up_track_id");
			lastUptrackTitle = json.getString("last_up_track_title");
			lastUptrackCoverPath = json.getString("last_up_track_cover_path");
			lastUptrackAt = json.getLong("last_up_track_at");
		}
	}
	
	public static List<UpdatedAlbum> constructUpdatedAlbums(HttpResponse response)
			throws XimalayaException {
		List<UpdatedAlbum> albums = new ArrayList<UpdatedAlbum> ();
		JSONArray albumsJsonArray = response.asJSONArray();
		try {
			for(int i = 0; i < albumsJsonArray.size(); i++) {
				albums.add(new UpdatedAlbum(albumsJsonArray.getJSONObject(i)));
			}
		} catch (JSONException jsone) {
			throw new XimalayaException(jsone.getMessage() + ":" + jsone.toString(), jsone);
		}
		return albums;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int)id;
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
		
		Album other = (Album) obj;
		if(this.id != other.getId()) {
			return false;
		}
		
		return true;
	}
}
