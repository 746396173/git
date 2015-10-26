package com.ximalaya.sdk4j.model.dto.track;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.ximalaya.sdk4j.http.HttpResponse;
import com.ximalaya.sdk4j.model.XimalayaException;

/**
 * 增量声音DTO
 * @author william
 *
 */
public class IncrementTrack extends Track {
	private static final long serialVersionUID = -8390101848254471252L;

	private boolean isOnline;   // 上线/下线
	
	public IncrementTrack() {
	}
	
	public IncrementTrack(JSONObject json) throws XimalayaException {
		super(json);
		init(json);
	}
	
	public IncrementTrack(HttpResponse response) throws XimalayaException {
		super(response);
		init(response.asJSONObject());
	}
	
	private void init(JSONObject json) throws XimalayaException{
		if(json != null) {
			isOnline = json.getBoolean("is_online");
		}
	}
	
	public static IncrementTrackList constructIncrementTracks(HttpResponse response) throws XimalayaException {
		IncrementTrackList incrementTracks = new IncrementTrackList();
		JSONObject incrementTracksJsonObject = response.asJSONObject();
		try {
			incrementTracks.setCategoryID(incrementTracksJsonObject.getLong("category_id"));
			incrementTracks.setTagName(incrementTracksJsonObject.getString("tag_name"));
			incrementTracks.setTotalCount(incrementTracksJsonObject.getIntValue("total_count"));
			incrementTracks.setTotalPage(incrementTracksJsonObject.getIntValue("total_page"));
			incrementTracks.setCurrentPage(incrementTracksJsonObject.getIntValue("current_page"));
				
			List<IncrementTrack> tracks = new ArrayList<IncrementTrack> ();
			JSONArray tracksJsonArray = incrementTracksJsonObject.getJSONArray("tracks");
			for(int i = 0; i < tracksJsonArray.size(); i++) {
				tracks.add(new IncrementTrack(tracksJsonArray.getJSONObject(i)));
			}
			incrementTracks.setTracks(tracks);
		} catch(JSONException jsone) {
			throw new XimalayaException(jsone.getMessage() + ":" + jsone.toString(), jsone);
		}
		return incrementTracks;
	}
	
	public boolean isOnline() {
		return isOnline;
	}
	public void setOnline(boolean isOnline) {
		this.isOnline = isOnline;
	}
}
