package com.ximalaya.sdk4j.model.dto.tag;

import java.util.List;

/**
 * 标签列表DTO
 * @author will
 *
 */
public class TagList {
	private Long categoryID;      // 标签所属分类ID
	private String categoryName;   // 标签所属分类名称
	private List<Tag> tags;        // 标签列表
	
	public TagList() {
	}
	
	public Long getCategoryID() {
		return categoryID;
	}
	public void setCategoryID(Long categoryID) {
		this.categoryID = categoryID;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public List<Tag> getTags() {
		return tags;
	}
	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((categoryID == null) ? 0 : categoryID.intValue());
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
		
		TagList other = (TagList) obj;
		if((categoryID == null && other.categoryID != null) 
			|| !categoryID.equals(other.categoryID)) {
			return false;
		}
		
		return true;
	}
}
