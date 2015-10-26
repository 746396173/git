package com.ximalaya.sdk4j.model.dto.track;

import com.alibaba.fastjson.JSONObject;
import com.ximalaya.sdk4j.model.XimalayaException;
import com.ximalaya.sdk4j.model.XimalayaResponse;

/**
 * 专辑内最新上传的声音
 * @author will
 *
 */
public class LastUpTrack extends XimalayaResponse {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6240285894359727004L;
	
	private Long trackID;        // 声音ID
	private String trackTitle;   // 声音标题
	private Double duration;     // 声音时长
	private Long createdAt;      // 声音创建时间
	private Long updatedAt;      // 声音更新时间
	
	public LastUpTrack() {
	}
	
	public Long getTrackID() {
		return trackID;
	}
	public void setTrackID(Long trackID) {
		this.trackID = trackID;
	}
	public String getTrackTitle() {
		return trackTitle;
	}
	public void setTrackTitle(String trackTitle) {
		this.trackTitle = trackTitle;
	}
	public Double getDuration() {
		return duration;
	}
	public void setDuration(Double duration) {
		this.duration = duration;
	}
	public Long getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Long createdAt) {
		this.createdAt = createdAt;
	}
	public Long getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(Long updatedAt) {
		this.updatedAt = updatedAt;
	}
	
	public LastUpTrack(JSONObject json) throws XimalayaException {
		init(json);
	}
	
	private void init(JSONObject json) throws XimalayaException {
		if(json != null) {
			trackID = json.getLong("track_id");
			trackTitle = json.getString("track_title");
			duration = json.getDouble("duration");
			createdAt = json.getLong("created_at");
			updatedAt = json.getLong("updated_at");
		}
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((trackID == null) ? 0 : trackID.intValue());
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
		
		LastUpTrack other = (LastUpTrack) obj;
		if((trackID == null && other.trackID != null) 
			|| !trackID.equals(other.trackID)) {
			return false;
		}
		
		return true;
	}
}