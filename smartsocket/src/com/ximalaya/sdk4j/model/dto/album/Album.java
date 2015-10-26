package com.ximalaya.sdk4j.model.dto.album;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;

import com.ximalaya.sdk4j.http.HttpResponse;
import com.ximalaya.sdk4j.model.XimalayaException;
import com.ximalaya.sdk4j.model.XimalayaResponse;
import com.ximalaya.sdk4j.model.dto.profile.User;
import com.ximalaya.sdk4j.model.dto.track.LastUpTrack;
import com.ximalaya.sdk4j.model.dto.track.Track;

/**
 * 专辑DTO
 * @author will
 *
 */
public class Album extends XimalayaResponse {
	/**
	 * 
	 */
	private static final long serialVersionUID = 989057998224528965L;
	
	private Long id;                   // ID
	private String kind;               // DTO实体类型
	private String albumTitle;         // 专辑标题
	private String albumTags;          // 专辑标签字符串，以英文逗号分隔
	private String albumIntro;         // 专辑简介
	private String coverUrlSmall;      // 专辑封面小图
	private String coverUrlMiddle;     // 专辑封面中图
	private String coverUrlLarge;      // 专辑封面大图
	private User announcer;            // 专辑所属主播
	private Long playCount;            // 专辑播放次数
	private Long favoriteCount;        // 专辑被喜欢次数
	private Long includeTrackCount;    // 专辑包含声音数
	private LastUpTrack lastUpTrack;   // 专辑内最新上传声音
	private Long updatedAt;            // 更新时间
	private Long createdAt;            // 更新时间
	
	public Album() {
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
	public String getAlbumTitle() {
		return albumTitle;
	}
	public void setAlbumTitle(String albumTitle) {
		this.albumTitle = albumTitle;
	}
	public String getAlbumTags() {
		return albumTags;
	}
	public void setAlbumTags(String albumTags) {
		this.albumTags = albumTags;
	}
	public String getAlbumIntro() {
		return albumIntro;
	}
	public void setAlbumIntro(String albumIntro) {
		this.albumIntro = albumIntro;
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
	public User getAnnouncer() {
		return announcer;
	}
	public void setAnnouncer(User announcer) {
		this.announcer = announcer;
	}
	public Long getPlayCount() {
		return playCount;
	}
	public void setPlayCount(Long playCount) {
		this.playCount = playCount;
	}
	public Long getFavoriteCount() {
		return favoriteCount;
	}
	public void setFavoriteCount(Long favoriteCount) {
		this.favoriteCount = favoriteCount;
	}
	public Long getIncludeTrackCount() {
		return includeTrackCount;
	}
	public void setIncludeTrackCount(Long includeTrackCount) {
		this.includeTrackCount = includeTrackCount;
	}
	public LastUpTrack getLastUptrack() {
		return lastUpTrack;
	}
	public void setLastUptrack(LastUpTrack lastUptrack) {
		this.lastUpTrack = lastUptrack;
	}
	public Long getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(Long updatedAt) {
		this.updatedAt = updatedAt;
	}
	public Long getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Long createdAt) {
		this.createdAt = createdAt;
	}
	
	public Album(JSONObject json) throws XimalayaException {
		super();
		init(json);
	}
	
	public Album(HttpResponse response) throws XimalayaException {
		super(response);
		init(response.asJSONObject());
	}
	
	private void init(JSONObject json) throws XimalayaException {
		if(json != null) {
			id = json.getLong("id");
			kind = json.getString("kind");
			albumTitle = json.getString("album_title");
			albumTags = json.getString("album_tags");
			albumIntro = json.getString("album_intro");
			coverUrlSmall = json.getString("cover_url_small");
			coverUrlMiddle = json.getString("cover_url_middle");
			coverUrlLarge = json.getString("cover_url_large");
			announcer = new User(json.getJSONObject("announcer"));
			playCount = json.getLong("play_count");
			favoriteCount = json.getLong("favorite_count");
			includeTrackCount = json.getLong("include_track_count");
			lastUpTrack = new LastUpTrack(json.getJSONObject("last_uptrack"));
			updatedAt = json.getLong("updated_at");
			createdAt = json.getLong("created_at");
		}
	}
	
	public static List<Album> constructAlbums(HttpResponse response) throws XimalayaException {
		List<Album> albums = new ArrayList<Album> ();
		JSONArray albumsJsonArray = response.asJSONArray();
		try {
			for(int i = 0; i < albumsJsonArray.size(); i++) {
				albums.add(new Album(albumsJsonArray.getJSONObject(i)));
			}
		} catch (JSONException jsone) {
			throw new XimalayaException(jsone.getMessage() + ":" + jsone.toString(), jsone);
		}
		return albums;
	}
	
	public static AlbumList constructAlbumList(HttpResponse response) throws XimalayaException {
		AlbumList albumList = new AlbumList();
		JSONObject albumListJsonObject = response.asJSONObject();
 		try {
 			albumList.setTotalPage(albumListJsonObject.getIntValue("total_page"));
 	 		albumList.setTotalCount(albumListJsonObject.getIntValue("total_count"));
 	 		albumList.setCurrentPage(albumListJsonObject.getIntValue("current_page"));
 	 		albumList.setCategoryID(albumListJsonObject.getLong("category_id"));
 	 		albumList.setTagName(albumListJsonObject.getString("tag_name"));
 	 		
 	 		List<Album> albums = new ArrayList<Album> ();
 	 		JSONArray albumsJsonArray = albumListJsonObject.getJSONArray("albums");
 	 		for(int i = 0; i < albumsJsonArray.size(); i++) {
 	 			albums.add(new Album(albumsJsonArray.getJSONObject(i)));
 	 		}
 	 		albumList.setAlbums(albums);
		} catch(JSONException jsone) {
			throw new XimalayaException(jsone.getMessage() + ":" + jsone.toString(), jsone);
		}
		return albumList;
	}


	public static AlbumList constructAlbumList(JSONObject albumListJsonObject) throws XimalayaException {
		AlbumList albumList = new AlbumList();
		try {
			albumList.setTotalPage(albumListJsonObject.getIntValue("total_page"));
			albumList.setTotalCount(albumListJsonObject.getIntValue("total_count"));
			albumList.setCurrentPage(albumListJsonObject.getIntValue("current_page"));
			albumList.setCategoryID(albumListJsonObject.getLong("category_id"));
			albumList.setTagName(albumListJsonObject.getString("tag_name"));

			List<Album> albums = new ArrayList<Album> ();
			JSONArray albumsJsonArray = albumListJsonObject.getJSONArray("albums");
			for(int i = 0; i < albumsJsonArray.size(); i++) {
				albums.add(new Album(albumsJsonArray.getJSONObject(i)));
			}
			albumList.setAlbums(albums);
		} catch(JSONException jsone) {
			throw new XimalayaException(jsone.getMessage() + ":" + jsone.toString(), jsone);
		}
		return albumList;
	}

	public static List<AlbumList> constructAlbumListList(HttpResponse response) throws XimalayaException {
		List<AlbumList> albumLists=new ArrayList<AlbumList>();
		JSONArray albumListArray = response.asJSONArray();
		for(int i=0;i<albumListArray.size();i++){
			JSONObject albumListJsonObject= (JSONObject) albumListArray.get(i);
			albumLists.add(constructAlbumList(albumListJsonObject));
		}
		return albumLists;


	}
	
	public static AlbumTracks constructAlbumTracks(HttpResponse response) throws XimalayaException {
		AlbumTracks albumTracks = new AlbumTracks();
		JSONObject albumTracksJsonObject = response.asJSONObject();
		try {
			albumTracks.setAlbumID(albumTracksJsonObject.getLong("album_id"));
			albumTracks.setAlbumTitle(albumTracksJsonObject.getString("album_title"));
			albumTracks.setCategoryID(albumTracksJsonObject.getLong("category_id"));
			albumTracks.setCoverUrlSmall(albumTracksJsonObject.getString("cover_url_small"));
			albumTracks.setCoverUrlMiddle(albumTracksJsonObject.getString("cover_url_middle"));
			albumTracks.setCoverUrlLarge(albumTracksJsonObject.getString("cover_url_large"));
			albumTracks.setTotalCount(albumTracksJsonObject.getIntValue("total_count"));
			albumTracks.setTotalPage(albumTracksJsonObject.getIntValue("total_page"));
			albumTracks.setCurrentPage(albumTracksJsonObject.getIntValue("current_page"));
				
			List<Track> tracks = new ArrayList<Track> ();
			JSONArray tracksJsonArray = albumTracksJsonObject.getJSONArray("tracks");
			for(int i = 0; i < tracksJsonArray.size(); i++) {
				tracks.add(new Track(tracksJsonArray.getJSONObject(i)));
			}
			albumTracks.setTracks(tracks);
		} catch(JSONException jsone) {
			throw new XimalayaException(jsone.getMessage() + ":" + jsone.toString(), jsone);
		}
		return albumTracks;
	}

	public static List<HumanRecommendAlbumList> constructHumanRecommendAlbumList(HttpResponse httpResponse)
			throws XimalayaException {
		JSONArray albumsJsonArray = httpResponse.asJSONArray();
		List<HumanRecommendAlbumList> recommendAlbumList = 
			new ArrayList<HumanRecommendAlbumList>(albumsJsonArray.size());
		for(int i = 0; i < albumsJsonArray.size(); i++) {
			recommendAlbumList.add(new HumanRecommendAlbumList(albumsJsonArray.getJSONObject(i)));
		}
		return recommendAlbumList;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.intValue());
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
		
		Album other = (Album) obj;
		if((id == null && other.id != null) 
			|| !id.equals(other.id)) {
			return false;
		}
		
		return true;
	}
	
}
