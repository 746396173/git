package com.ximalaya.sdk4j.model.dto.album;

import com.ximalaya.sdk4j.model.dto.AbstractPageResult;

import java.io.Serializable;
import java.util.List;


public class ReletiveAlbumList extends AbstractPageResult implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private List<ReletiveAlbum> reletiveAlbum;    // 推荐专辑列表

	public List<ReletiveAlbum> getReletiveAlbum() {
		return reletiveAlbum;
	}

	public void setReletiveAlbum(List<ReletiveAlbum> reletiveAlbum) {
		this.reletiveAlbum = reletiveAlbum;
	}
}
