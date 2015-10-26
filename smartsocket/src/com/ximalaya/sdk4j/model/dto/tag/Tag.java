package com.ximalaya.sdk4j.model.dto.tag;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.ximalaya.sdk4j.http.HttpResponse;
import com.ximalaya.sdk4j.model.XimalayaException;
import com.ximalaya.sdk4j.util.StringUtil;

/**
 * 标签DTO
 * 
 * @author will
 *
 */
public class Tag implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3639856295998431047L;
	
	private String tagName;          // 标签名
	private String kind;             // DTO实体类型
	private String coverUrlSmall;    // 封面小图
	private String coverUrlMiddle;   // 封面中图
	private String coverUrlLarge;    // 封面大图
	
	public Tag() {
	}
	
	public String getTagName() {
		return tagName;
	}
	public void setTagName(String tagName) {
		this.tagName = tagName;
	}
	public String getKind() {
		return kind;
	}
	public void setKind(String kind) {
		this.kind = kind;
	}
	public String getCoverUrlSmall() {
		return coverUrlSmall;
	}
	public void setCoverUrlSmall(String coverUrlSmall) {
		this.coverUrlSmall = coverUrlSmall;
	}
	public String getCoverUrlMiddle() {
		return coverUrlMiddle;
	}
	public void setCoverUrlMiddle(String coverUrlMiddle) {
		this.coverUrlMiddle = coverUrlMiddle;
	}
	public String getCoverUrlLarge() {
		return coverUrlLarge;
	}
	public void setCoverUrlLarge(String coverUrlLarge) {
		this.coverUrlLarge = coverUrlLarge;
	}
	public Tag(JSONObject json) throws XimalayaException {
		init(json);
	}
	
	private void init(JSONObject json) throws XimalayaException {
		if(json != null) {
			tagName = json.getString("tag_name");
			kind = json.getString("kind");
			coverUrlSmall = json.getString("cover_url_small");
			coverUrlMiddle = json.getString("cover_url_middle");
			coverUrlLarge = json.getString("cover_url_large");
		}
	}
	
	public static List<Tag> constructTags(HttpResponse response) throws XimalayaException {
		List<Tag> tags = new ArrayList<Tag> ();
		JSONArray tagsJsonArray = response.asJSONArray();
		try {
			for(int i = 0; i < tagsJsonArray.size(); i++) {
				tags.add(new Tag(tagsJsonArray.getJSONObject(i)));
			}
		} catch (JSONException jsone) {
			throw new XimalayaException(jsone.getMessage() + ":" + jsone.toString(), jsone);
		}
		return tags;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((tagName == null) ? 0 : StringUtil.getConsistentHashCodeForSameStr(tagName));
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
		
		Tag other = (Tag) obj;
		if((tagName == null && other.tagName != null) 
			|| !tagName.equals(other.tagName)) {
			return false;
		}
		
		return true;
	}
}
