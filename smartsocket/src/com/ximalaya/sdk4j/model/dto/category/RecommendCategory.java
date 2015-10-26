package com.ximalaya.sdk4j.model.dto.category;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.ximalaya.sdk4j.http.HttpResponse;
import com.ximalaya.sdk4j.model.XimalayaException;

/**
 * 分类DTO
 * @author will
 *
 */
public class RecommendCategory extends Category implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8134137599995673738L;
	
	private Integer orderNum;		 // 排序值，值越小排序越靠前
	
	public RecommendCategory() {
	}
	
	public Integer getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}

	public RecommendCategory(JSONObject json) throws XimalayaException {
		super(json);
		init(json);
	}
	
	private void init(JSONObject json) throws XimalayaException {
		if(json != null) {
			orderNum = json.getIntValue("order_num");
		}
	}
	
	public static List<RecommendCategory> constructRecommendCategories(HttpResponse response) throws XimalayaException {
		List<RecommendCategory> categories = new ArrayList<RecommendCategory> ();
		JSONArray categoriesJsonArray = response.asJSONArray();
		try {
			for(int i = 0; i < categoriesJsonArray.size(); i++) {
				categories.add(new RecommendCategory(categoriesJsonArray.getJSONObject(i)));
			}
		} catch (JSONException jsone) {
			throw new XimalayaException(jsone.getMessage() + ":" + jsone.toString(), jsone);
		}
		return categories;
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
		
		RecommendCategory other = (RecommendCategory) obj;
		if((this.getId() == null && other.getId() != null) 
			|| !this.getId().equals(other.getId())) {
			return false;
		}
		
		return true;
	}
}
