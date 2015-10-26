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
 * 直播电台DTO
 * 
 * @author will
 *
 */
public class Radio extends XimalayaResponse {
	private static final long serialVersionUID = 8588679252699226797L;
	
	private Long id;                            // 电台ID
	private String kind;                        // DTO实体类型
	private String radioName;                   // 电台名称
	private String radioDesc;                   // 电台简介
	private String programName;                 // 正在直播的节目名称
	private Long scheduleID;                    // 正在直播的节目时间表ID
	private List<Integer> supportBitRates;      // 支持的码率列表，如[24, 64]
	private String rate24AacUrl;                // 24码率电台在线播放地址，aac格式
	private String rate24TsUrl;                 // 24码率电台在线播放地址，ts格式
	private String rate64AacUrl;                // 64码率电台在线播放地址，aac格式
	private String rate64TsUrl;                 // 64码率电台在线播放地址，t是格式
	private Long  radioPlayCount;               // 电台累计被收听次数
	private String coverUrlSmall;               // 电台封面小图
	private String coverUrlLarge;               // 电台封面大图
	private Long updatedAt;                     // 电台更新时间
	
	public Radio() {
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
	public String getRadioName() {
		return radioName;
	}
	public void setRadioName(String radioName) {
		this.radioName = radioName;
	}
	public String getRadioDesc() {
		return radioDesc;
	}
	public void setRadioDesc(String radioDesc) {
		this.radioDesc = radioDesc;
	}
	public String getProgramName() {
		return programName;
	}
	public void setProgramName(String programName) {
		this.programName = programName;
	}
	public Long getScheduleID() {
		return scheduleID;
	}
	public void setScheduleID(Long scheduleID) {
		this.scheduleID = scheduleID;
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
	public Long getRadioPlayCount() {
		return radioPlayCount;
	}
	public void setRadioPlayCount(Long radioPlayCount) {
		this.radioPlayCount = radioPlayCount;
	}
	public String getCoverUrlSmall() {
		return coverUrlSmall;
	}
	public void setCoverUrlSmall(String coverUrlSmall) {
		this.coverUrlSmall = coverUrlSmall;
	}
	public String getCoverUrlLarge() {
		return coverUrlLarge;
	}
	public void setCoverUrlLarge(String coverUrlLarge) {
		this.coverUrlLarge = coverUrlLarge;
	}
	public Long getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(Long updatedAt) {
		this.updatedAt = updatedAt;
	}
	public Radio(HttpResponse response) throws XimalayaException {
		super(response);
		init(response.asJSONObject());
	}
	
	public Radio(JSONObject json) throws XimalayaException {
		super();
		init(json);
	}
	
	private void init(JSONObject json) throws XimalayaException {
		if(json != null) {
			id = json.getLong("id");
			kind = json.getString("kind");
			radioName = json.getString("radio_name");
			radioDesc = json.getString("radio_desc");       // 电台简介可能为空
			programName = json.getString("program_name");   // 节目名称可能为空
			scheduleID = json.getLong("schedule_id");
			
			supportBitRates = new ArrayList<Integer> ();
			JSONArray supportBitRatesJsonArray = json.getJSONArray("support_bitrates");
			for(int i = 0; i < supportBitRatesJsonArray.size(); i++) {
				supportBitRates.add(supportBitRatesJsonArray.getIntValue(i));
			}
			
			rate24AacUrl = json.getString("rate24_aac_url");
			rate24TsUrl = json.getString("rate24_ts_url");
			rate64AacUrl = json.getString("rate64_aac_url");
			rate64TsUrl = json.getString("rate64_ts_url");
			
			radioPlayCount = json.getLong("radio_play_count");
			coverUrlSmall = json.getString("cover_url_small");
			coverUrlLarge = json.getString("cover_url_large");
			updatedAt = json.getLong("updated_at");
		}
	}
	
	public static RadioList constructRadioList(HttpResponse response) throws XimalayaException {
		RadioList radioList = new RadioList();
		JSONObject radioListJsonObject = response.asJSONObject();
		try {
			radioList.setTotalPage(radioListJsonObject.getIntValue("total_page"));
			radioList.setTotalCount(radioListJsonObject.getIntValue("total_count"));
			radioList.setCurrentPage(radioListJsonObject.getIntValue("current_page"));
			
			List<Radio> radios = new ArrayList<Radio> ();
			JSONArray radiosJsonArray = radioListJsonObject.getJSONArray("radios");
			for(int i = 0; i < radiosJsonArray.size(); i++) {
				radios.add(new Radio(radiosJsonArray.getJSONObject(i)));
			}
			radioList.setRadios(radios);
		} catch(JSONException jsone) {
			throw new XimalayaException(jsone.getMessage() + ":" + jsone.toString(), jsone);
		}
		return radioList;
	}
	
	public static List<Radio> constructRadios(HttpResponse response) throws XimalayaException {
		JSONArray radiosJsonArray = response.asJSONArray();
		List<Radio> radios = new ArrayList<Radio> ();
		try {
			for(int i = 0; i < radiosJsonArray.size(); i++) {
				radios.add(new Radio(radiosJsonArray.getJSONObject(i)));
			}
		} catch(JSONException jsone) {
			throw new XimalayaException(jsone.getMessage() + ":" + jsone.toString(), jsone);
		}
		return radios;
	}

	public static RadioList constructRadioList(JSONObject radioListJsonObject) throws XimalayaException {
		RadioList radioList = new RadioList();
		try {
			radioList.setTotalPage(radioListJsonObject.getIntValue("total_page"));
			radioList.setTotalCount(radioListJsonObject.getIntValue("total_count"));
			radioList.setCurrentPage(radioListJsonObject.getIntValue("current_page"));
			List<Radio> radios = new ArrayList<Radio> ();
			JSONArray radiosJsonArray = radioListJsonObject.getJSONArray("radios");
			for(int i = 0; i < radiosJsonArray.size(); i++) {
				radios.add(new Radio(radiosJsonArray.getJSONObject(i)));
			}
			radioList.setRadios(radios);
		} catch(JSONException jsone) {
			throw new XimalayaException(jsone.getMessage() + ":" + jsone.toString(), jsone);
		}
		return radioList;
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
		
		Radio other = (Radio) obj;
		if((id == null && other.id != null) 
			|| !id.equals(other.id)) {
			return false;
		}
		
		return true;
	}
}
