package com.ximalaya.sdk4j.model.dto.album;

import java.util.List;

import com.ximalaya.sdk4j.model.dto.AbstractPageResult;

/**
 * 增量专辑列表DTO
 * @author william
 *
 */
public class IncrementAlbumList extends AbstractPageResult {
	
	private Long categoryID;               // 标签所属分类ID
	private String tagName;                // 标签名
	private List<IncrementAlbum> albums;   // 专辑列表
	
	public IncrementAlbumList() {
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
	public List<IncrementAlbum> getAlbums() {
		return albums;
	}
	public void setAlbums(List<IncrementAlbum> albums) {
		this.albums = albums;
	}
}
