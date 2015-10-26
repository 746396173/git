package com.ximalaya.sdk4j.model.dto.track;

import java.util.List;

import com.ximalaya.sdk4j.model.dto.AbstractPageResult;

public class IncrementTrackList extends AbstractPageResult {
	private Long categoryID;               // 声音所属分类ID
	private String tagName;                // 选填的声音所属标签名，无则返回空
	private List<IncrementTrack> tracks;   // 声音列表
	
	public IncrementTrackList() {
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
	public List<IncrementTrack> getTracks() {
		return tracks;
	}
	public void setTracks(List<IncrementTrack> tracks) {
		this.tracks = tracks;
	}
}
