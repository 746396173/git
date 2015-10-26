package com.ximalaya.sdk4j.model.dto.profile;

import com.alibaba.fastjson.JSONObject;
import com.ximalaya.sdk4j.http.HttpResponse;
import com.ximalaya.sdk4j.model.XimalayaException;

public class Editor extends User{
	private static final long serialVersionUID = 1L;
	
	private String personalSignature;  //小编签名
	
	public Editor() {
	}
	
	public String getPersonalSignature() {
		return personalSignature;
	}
	public void setPersonalSignature(String personalSignature) {
		this.personalSignature = personalSignature;
	}

	public Editor(HttpResponse response) throws XimalayaException {
		super(response);
		init(response.asJSONObject());
	}
	
	public Editor(JSONObject json) throws XimalayaException {
		super(json);
		init(json);
	}
	
	private void init(JSONObject json) throws XimalayaException {
		if(json != null) {
			personalSignature = json.getString("column_editor");
		}
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.getId() == null) ? 0 : this.getId().intValue());
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
		
		User other = (User) obj;
		if((this.getId() == null && other.getId() != null) 
			|| !this.getId().equals(other.getId())) {
			return false;
		}
		
		return true;
	}
}
