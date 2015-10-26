package com.ximalaya.sdk4j.model.dto.live;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.ximalaya.sdk4j.http.HttpResponse;
import com.ximalaya.sdk4j.model.XimalayaException;
import com.ximalaya.sdk4j.model.XimalayaResponse;

/**
 * 直播节目排期DTO
 * 
 * @author will
 *
 */
public class Schedule extends XimalayaResponse {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2863359065879699665L;
	
	private Long id;                  // 节目时间表ID
	private String kind;              // DTO实体类型
	private String startTime;         // 开始时间
	private String endTime;           // 结束时间
	private Long updatedAt;		      // 更新时间，Unix毫秒数时间戳
	private String listenBackUrl;	  // 节目回听地址
	private Integer playType;         // 播放类型，0-直播，1-重播，2-跨天，3-无流期
	private Program relatedProgram;   // 关联的直播节目
	
	public Schedule() {
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getKind() {
		return kind;
	}
	public void setKind(String kind) {
		this.kind = kind;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public Long getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(Long updatedAt) {
		this.updatedAt = updatedAt;
	}
	public String getListenBackUrl() {
		return listenBackUrl;
	}
	public void setListenBackUrl(String listenBackUrl) {
		this.listenBackUrl = listenBackUrl;
	}
	public Integer getPlayType() {
		return playType;
	}
	public void setPlayType(Integer playType) {
		this.playType = playType;
	}
	public Program getRelatedProgram() {
		return relatedProgram;
	}
	public void setRelatedProgram(Program relatedProgram) {
		this.relatedProgram = relatedProgram;
	}
	
	public Schedule(JSONObject json) throws XimalayaException {
		super();
		init(json);
	}
	
	private void init(JSONObject json) throws XimalayaException {
		if(json != null) {
			id = json.getLong("id");
			kind = json.getString("kind");
			startTime = json.getString("start_time");
			endTime = json.getString("end_time");
			updatedAt = json.getLong("updated_at");
			playType = json.getIntValue("play_type");
			listenBackUrl = json.getString("listen_back_url");
			relatedProgram = new Program(json.getJSONObject("related_program"));
		}
	}
	
	public static List<Schedule> constructSchedules(HttpResponse response) throws XimalayaException {
		JSONArray schedulesJsonArray = response.asJSONArray();
		List<Schedule> schedules = new ArrayList<Schedule> ();
		try {
			for(int i = 0; i < schedulesJsonArray.size(); i++) {
				schedules.add(new Schedule(schedulesJsonArray.getJSONObject(i)));
			}
		} catch(JSONException jsone) {
			throw new XimalayaException(jsone.getMessage() + ":" + jsone.toString(), jsone);
		}
		return schedules;
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
		
		Schedule other = (Schedule) obj;
		if((id == null && other.id != null) 
			|| !id.equals(other.id)) {
			return false;
		}
		
		return true;
	}
	
}
