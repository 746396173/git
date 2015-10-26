package com.ximalaya.sdk4j.model;

import com.alibaba.fastjson.JSONObject;
import com.ximalaya.sdk4j.http.HttpResponse;
import com.ximalaya.sdk4j.model.dto.album.Album;
import com.ximalaya.sdk4j.model.dto.album.AlbumList;
import com.ximalaya.sdk4j.model.dto.live.Radio;
import com.ximalaya.sdk4j.model.dto.live.RadioList;
import com.ximalaya.sdk4j.model.dto.track.Track;
import com.ximalaya.sdk4j.model.dto.track.TrackList;

public class AllList extends XimalayaResponse {

	private static final long serialVersionUID = 2721436489197473333L;
	
	private AlbumList albumList;
		
	private RadioList radioList;
	
	private TrackList trackList;

	public AlbumList getAlbumList() {
		return albumList;
	}

	public void setAlbumList(AlbumList albumList) {
		this.albumList = albumList;
	}

	public RadioList getRadioList() {
		return radioList;
	}

	public void setRadioList(RadioList radioList) {
		this.radioList = radioList;
	}

	public TrackList getTrackList() {
		return trackList;
	}

	public void setTrackList(TrackList trackList) {
		this.trackList = trackList;
	}

	public AllList() {
	}

	public AllList(HttpResponse response) throws XimalayaException {
		super(response);
		init(response.asJSONObject());
	}
	
	private void init(JSONObject jsonObject) throws XimalayaException {
		albumList=	Album.constructAlbumList(jsonObject.getJSONObject("album_list"));
		radioList=	Radio.constructRadioList(jsonObject.getJSONObject("radio_list"));
		trackList= Track.constructTrackList(jsonObject.getJSONObject("track_list"));
	}
}
