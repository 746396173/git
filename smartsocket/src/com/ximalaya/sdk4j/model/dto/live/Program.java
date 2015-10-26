package com.ximalaya.sdk4j.model.dto.live;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ximalaya.sdk4j.http.HttpResponse;
import com.ximalaya.sdk4j.model.XimalayaException;
import com.ximalaya.sdk4j.model.XimalayaResponse;

/**
 * 直播节目DTO
 * 
 * @author will
 *
 */
public class Program extends XimalayaResponse {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6614882882187524681L;
	
	private Long id;                              // 节目ID
	private String kind;                          // DTO实体类型
	private String programName;                   // 节目名称
	private String backPicUrl;                    // 节目背景图URL
	private List<Integer> supportBitRates;        // 支持的码率列表，如[24, 64]
	private String rate24AacUrl;                  // 24码率电台在线播放地址，aac格式
	private String rate24TsUrl;                   // 24码率电台在线播放地址，ts格式
	private String rate64AacUrl;                  // 64码率电台在线播放地址，aac格式
	private String rate64TsUrl;                   // 64码率电台在线播放地址，t是格式
	private List<LiveAnnouncer> liveAnnouncers;   // 直播主播列表
	private Long updatedAt;					      // 更新时间，Unix毫秒数时间戳
	
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
	public String getProgramName() {
		return programName;
	}
	public void setProgramName(String programName) {
		this.programName = programName;
	}
	public String getBackPicUrl() {
		return backPicUrl;
	}
	public void setBackPicUrl(String backPicUrl) {
		this.backPicUrl = backPicUrl;
	}
	public List<Integer> getSupportBitRates() {
		return supportBitRates;
	}
	public void setSupportBitRates(List<Integer> supportBitRates) {
		this.supportBitRates = supportBitRates;
	}
	public String getRate24AacUrl() {
		return rate24AacUrl;
	}
	public void setRate24AacUrl(String rate24AacUrl) {
		this.rate24AacUrl = rate24AacUrl;
	}
	public String getRate24TsUrl() {
		return rate24TsUrl;
	}
	public void setRate24TsUrl(String rate24TsUrl) {
		this.rate24TsUrl = rate24TsUrl;
	}
	public String getRate64AacUrl() {
		return rate64AacUrl;
	}
	public void setRate64AacUrl(String rate64AacUrl) {
		this.rate64AacUrl = rate64AacUrl;
	}
	public String getRate64TsUrl() {
		return rate64TsUrl;
	}
	public void setRate64TsUrl(String rate64TsUrl) {
		this.rate64TsUrl = rate64TsUrl;
	}
	public List<LiveAnnouncer> getLiveAnnouncers() {
		return liveAnnouncers;
	}
	public void setLiveAnnouncers(List<LiveAnnouncer> liveAnnouncers) {
		this.liveAnnouncers = liveAnnouncers;
	}
	public Long getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(Long updatedAt) {
		this.updatedAt = updatedAt;
	}
	public Program(HttpResponse response) throws XimalayaException {
		super(response);
		init(response.asJSONObject());
	}
	
	public Program(JSONObject json) throws XimalayaException {
		super();
		init(json);
	}
	
	private void init(JSONObject json) throws XimalayaException {
		if(json != null) {
			id = json.getLong("id");
			kind = json.getString("kind");
			programName = json.getString("program_name");
			backPicUrl = json.getString("back_pic_url");
				
			supportBitRates = new ArrayList<Integer> ();
			JSONArray supportBitRatesJsonArray = json.getJSONArray("support_bitrates");
			for(int i = 0; i < supportBitRatesJsonArray.size(); i++) {
				supportBitRates.add(supportBitRatesJsonArray.getIntValue(i));
			}
			
			rate24AacUrl = json.getString("rate24_aac_url");
			rate24TsUrl = json.getString("rate24_ts_url");
			rate64AacUrl = json.getString("rate64_aac_url");
			rate64TsUrl = json.getString("rate64_ts_url");
			
			List<LiveAnnouncer> liveAnnouncers = new ArrayList<LiveAnnouncer> ();
			JSONArray liveAnnouncersJsonArray = json.getJSONArray("live_announcers");
			for(int i = 0; i < liveAnnouncersJsonArray.size(); i++) {
				liveAnnouncers.add(new LiveAnnouncer(liveAnnouncersJsonArray.getJSONObject(i)));
			}
			this.liveAnnouncers = liveAnnouncers;
			this.updatedAt = json.getLong("updated_at");
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
		
		Program other = (Program) obj;
		if((id == null && other.id != null) 
			|| !id.equals(other.id)) {
			return false;
		}
		
		return true;
	}
}
