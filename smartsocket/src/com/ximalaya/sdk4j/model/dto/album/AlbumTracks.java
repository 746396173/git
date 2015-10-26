package com.ximalaya.sdk4j.model.dto.album;

import java.io.Serializable;
import java.util.List;

import com.ximalaya.sdk4j.model.dto.AbstractPageResult;
import com.ximalaya.sdk4j.model.dto.track.Track;

/**
 * 专辑内的声音DTO
 * @author will
 *
 */
public class AlbumTracks extends AbstractPageResult implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5651248836985634066L;
	
	private Long albumID;            // 专辑ID
	private String albumTitle;       // 专辑名称
	private Long categoryID;         // 专辑分类ID
	private String coverUrlSmall;    // 专辑封面小图
	private String coverUrlMiddle;   // 专辑封面中图
	private String coverUrlLarge;    // 专辑封面大图
	private List<Track> tracks;      // 专辑下的声音（一页）
	
	public AlbumTracks() {
	}
	
	public Long getAlbumID() {
		return albumID;
	}
	public void setAlbumID(Long albumID) {
		this.albumID = albumID;
	}
	public String getAlbumTitle() {
		return albumTitle;
	}
	public void setAlbumTitle(String albumTitle) {
		this.albumTitle = albumTitle;
	}
	public Long getCategoryID() {
		return categoryID;
	}
	public void setCategoryID(Long categoryID) {
		this.categoryID = categoryID;
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
	public List<Track> getTracks() {
		return tracks;
	}
	public void setTracks(List<Track> tracks) {
		this.tracks = tracks;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((albumID == null) ? 0 : albumID.intValue());
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
		
		AlbumTracks other = (AlbumTracks) obj;
		if((albumID == null && other.albumID != null) 
			|| !albumID.equals(other.albumID)) {
			return false;
		}
		
		return true;
	}
}
