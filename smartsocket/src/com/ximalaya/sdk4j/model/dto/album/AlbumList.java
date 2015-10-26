package com.ximalaya.sdk4j.model.dto.album;

import java.io.Serializable;
import java.util.List;

import com.ximalaya.sdk4j.model.dto.AbstractPageResult;
import com.ximalaya.sdk4j.util.StringUtil;

/***
 * 专辑列表DTO
 * @author will
 *
 */
public class AlbumList extends AbstractPageResult implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7355077855402965227L;
	
	private Long categoryID;       // 标签所属分类ID，可选
	private String tagName;        // 标签名，可选
	private List<Album> albums;    // 专辑列表
	
	public AlbumList() {
	}
	
	public Long getCategoryID() {
		return categoryID;
	}
	public void setCategoryID(Long categoryID) {
		this.categoryID = categoryID;
	}
	public String getTagName() {
		return tagName;
	}
	public void setTagName(String tagName) {
		this.tagName = tagName;
	}
	public List<Album> getAlbums() {
		return albums;
	}
	public void setAlbums(List<Album> albums) {
		this.albums = albums;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((categoryID == null) ? 0 : categoryID.intValue());
		result = prime * result + ((tagName == null) ? 0: StringUtil.getConsistentHashCodeForSameStr(tagName));
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
		
		AlbumList other = (AlbumList) obj;
		if((categoryID == null && other.categoryID != null) 
			|| !categoryID.equals(other.categoryID)) {
			return false;
		}
		if(tagName == null && other.tagName != null
			|| !tagName.equals(other.tagName)) {
			return false;
		}
		
		return true;
	}
}
