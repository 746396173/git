package com.ximalaya.sdk4j.model.dto.banner;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.ximalaya.sdk4j.http.HttpResponse;
import com.ximalaya.sdk4j.model.XimalayaException;
import com.ximalaya.sdk4j.model.XimalayaResponse;

public class Banner extends XimalayaResponse{
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String kind;
	private String bannerTitle;
	private String bannerShortTitle;
	private String bannerUrl;
	private String bannerRedirectUrl;
	private String canShare;
	private String bannerContentType;
	private Long bannerUid;
	private Long trackId;
	private Long columnId;
	private String columnContentType;
	private Long albumId;
	private String thirdPartyUrl;
	private Boolean isExternalUrl;
	
	public Banner() {
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getKind() {
		return kind;
	}
	public void setKind(String kind) {
		this.kind = kind;
	}
	public String getBannerTitle() {
		return bannerTitle;
	}
	public void setBannerTitle(String bannerTitle) {
		this.bannerTitle = bannerTitle;
	}
	public String getBannerShortTitle() {
		return bannerShortTitle;
	}
	public void setBannerShortTitle(String bannerShortTitle) {
		this.bannerShortTitle = bannerShortTitle;
	}
	public String getBannerUrl() {
		return bannerUrl;
	}
	public void setBannerUrl(String bannerUrl) {
		this.bannerUrl = bannerUrl;
	}
	public String getBannerRedirectUrl() {
		return bannerRedirectUrl;
	}
	public void setBannerRedirectUrl(String bannerRedirectUrl) {
		this.bannerRedirectUrl = bannerRedirectUrl;
	}
	public String getCanShare() {
		return canShare;
	}
	public void setCanShare(String canShare) {
		this.canShare = canShare;
	}
	public String getBannerContentType() {
		return bannerContentType;
	}
	public void setBannerContentType(String bannerContentType) {
		this.bannerContentType = bannerContentType;
	}
	public Long getBannerUid() {
		return bannerUid;
	}
	public void setBannerUid(Long bannerUid) {
		this.bannerUid = bannerUid;
	}
	public Long getTrackId() {
		return trackId;
	}
	public void setTrackId(Long trackId) {
		this.trackId = trackId;
	}
	public Long getColumnId() {
		return columnId;
	}
	public void setColumnId(Long columnId) {
		this.columnId = columnId;
	}
	public String getColumnContentType() {
		return columnContentType;
	}
	public void setColumnContentType(String columnContentType) {
		this.columnContentType = columnContentType;
	}
	public Long getAlbumId() {
		return albumId;
	}
	public void setAlbumId(Long albumId) {
		this.albumId = albumId;
	}
	public String getThirdPartyUrl() {
		return thirdPartyUrl;
	}
	public void setThirdPartyUrl(String thirdPartyUrl) {
		this.thirdPartyUrl = thirdPartyUrl;
	}
	public Boolean getIsExternalUrl() {
		return isExternalUrl;
	}
	public void setIsExternalUrl(Boolean isExternalUrl) {
		this.isExternalUrl = isExternalUrl;
	}
	
	public Banner(JSONObject json) throws XimalayaException {
		super();
		init(json);
	}
	
	public Banner(HttpResponse response) throws XimalayaException {
		super(response);
		init(response.asJSONObject());
	}
	
	private void init(JSONObject json) throws XimalayaException {
		if(json != null) {
			id = json.getLong("id");
			kind = json.getString("kind");
			bannerTitle = json.getString("banner_title");
			bannerShortTitle = json.getString("banner_short_title");
			bannerUrl = json.getString("banner_url");
			bannerRedirectUrl = json.getString("banner_redirect_url");
			canShare = json.getString("can_share");
			bannerContentType = json.getString("banner_content_type");
			bannerUid = json.getLong("banner_uid");
			trackId  = json.getLong("track_id");
			columnId  = json.getLong("column_id");
			columnContentType = json.getString("column_content_type");
			albumId = json.getLong("album_id");
			thirdPartyUrl = json.getString("third_party_url");
			isExternalUrl = json.getBoolean("is_external_url");
		}
	}
	
	public static List<Banner> constructVehicleBanners(HttpResponse response) throws XimalayaException {
		List<Banner> albums = new ArrayList<Banner> ();
		JSONArray albumsJsonArray = response.asJSONArray();
		try {
			for(int i = 0; i < albumsJsonArray.size(); i++) {
				albums.add(new Banner(albumsJsonArray.getJSONObject(i)));
			}
		} catch (JSONException jsone) {
			throw new XimalayaException(jsone.getMessage() + ":" + jsone.toString(), jsone);
		}
		return albums;
	}
	
	public static List<Banner> constructBanners(HttpResponse response) throws XimalayaException {
		List<Banner> albums = new ArrayList<Banner> ();
		try {
			JSONArray albumsJsonArray = response.asJSONArray();
			for(int i = 0; i < albumsJsonArray.size(); i++) {
				albums.add(new Banner(albumsJsonArray.getJSONObject(i)));
			}
		} catch (JSONException jsone) {
			throw new XimalayaException(jsone.getMessage() + ":" + jsone.toString(), jsone);
		}
		return albums;
	}
}
