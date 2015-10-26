package com.ximalaya.sdk4j.model.dto.live;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.ximalaya.sdk4j.http.HttpResponse;
import com.ximalaya.sdk4j.model.XimalayaException;

public class IncrementRadio extends Radio {
	private static final long serialVersionUID = -7390863817476337868L;
	
	private boolean isOnline;   // 上线/下线(1表示新增或修改，0表示删除)
	
	public IncrementRadio() {
	}
	
	public IncrementRadio(JSONObject json) throws XimalayaException {
		super(json);
		init(json);
	}
	public IncrementRadio(HttpResponse response) throws XimalayaException {
		super(response);
		init(response.asJSONObject());
	}
	
	private void init(JSONObject json) throws XimalayaException{
		if(json != null) {
			isOnline = json.getBoolean("is_online");
		}
	}
	
	public static IncrementRadioList constructIncrementRadioList(HttpResponse response) throws XimalayaException {
		IncrementRadioList radioList = new IncrementRadioList();
		JSONObject radioListJsonObject = response.asJSONObject();
		try {
			radioList.setTotalPage(radioListJsonObject.getIntValue("total_page"));
			radioList.setTotalCount(radioListJsonObject.getIntValue("total_count"));
			radioList.setCurrentPage(radioListJsonObject.getIntValue("current_page"));
			
			List<IncrementRadio> radios = new ArrayList<IncrementRadio> ();
			JSONArray radiosJsonArray = radioListJsonObject.getJSONArray("radios");
			for(int i = 0; i < radiosJsonArray.size(); i++) {
				radios.add(new IncrementRadio(radiosJsonArray.getJSONObject(i)));
			}
			radioList.setRadios(radios);
		} catch(JSONException jsone) {
			throw new XimalayaException(jsone.getMessage() + ":" + jsone.toString(), jsone);
		}
		return radioList;
	}
	
	public boolean isOnline() {
		return isOnline;
	}
	public void setOnline(boolean isOnline) {
		this.isOnline = isOnline;
	}

}
